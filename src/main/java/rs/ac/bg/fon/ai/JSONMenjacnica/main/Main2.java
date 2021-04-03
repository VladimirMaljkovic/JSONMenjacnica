package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main2 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String DATE = "2020-12-10";

	public static void main(String[] args) {

		LinkedList<String> valute = new LinkedList<String>();
		valute.add("EUR");
		valute.add("CHF");
		valute.add("CAD");

		LinkedList<Transakcija> transakcije = new LinkedList<Transakcija>();

		for (String valuta : valute) {
			Transakcija t = new Transakcija();
			t.setIzvornaValuta("USD");
			t.setKrajnjaValuta(valuta);
			t.setPocetniIznos(100);

			Gson gson = new Gson();

			try {
				URL url = new URL(BASE_URL + "/historical?date=" + DATE + "&access_key=" + API_KEY);

				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("GET");

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				JsonObject result = gson.fromJson(reader, JsonObject.class);

				if (result.get("success").getAsBoolean()) {

					// naci timestamp i mapirati u Date
					long timestamp = result.get("timestamp").getAsLong();
					
					//Date ne podrzava vremenske zone, a epoch je 1h iza nase zone, najlakse mi je da smanjim timestamp za 1 sat
					timestamp = timestamp - 3600;  //smanjeno za 1h = 60 min = 3600 sekundi
					
					
					Date time = new Date(timestamp * 1000);
					t.setDatumTransakcije(time);

					String currencies = t.getIzvornaValuta() + t.getKrajnjaValuta();

					// naci odnos valuta u quotes
					double exchangeRate = result.get("quotes").getAsJsonObject().get(currencies).getAsDouble();

					t.setKonvertovaniIznos(t.getPocetniIznos() * exchangeRate);
					
					System.out.println("timestamp je " + timestamp + " a datum je " + t.getDatumTransakcije());

					transakcije.add(t);

				} else {
					System.out.println("Greska kod konekcije");
				}

			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		File output = new File("output");
		if (!output.exists())
			output.mkdir();

		Transakcija.serializeMultipleToJson(transakcije);

	}

}
