package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main1 {

	private static final String MY_URL = "http://api.currencylayer.com/live?access_key=2e4baadf5c5ae6ba436f53ae5558107f";

	public static void main(String[] args) {
		
		Transakcija t = new Transakcija();
		t.setIzvornaValuta("USD");
		
		t.setKrajnjaValuta("CAD");   //<----
		
		t.setPocetniIznos(248);
		

		Gson gson = new Gson();

		try {
			URL url = new URL(MY_URL);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JsonObject result = gson.fromJson(reader, JsonObject.class);

			if (result.get("success").getAsBoolean()) {

				
				//naci timestamp i mapirati u Date
				long timestamp = result.get("timestamp").getAsLong();
				Date time = new Date(timestamp * 1000);
				t.setDatumTransakcije(time);
				
				String currencies = t.getIzvornaValuta() +  t.getKrajnjaValuta();
				
				//naci odnos USDCAD u quotes
				double exchangeRate = result.get("quotes").getAsJsonObject().get(currencies).getAsDouble();
				
				t.setKonvertovaniIznos(t.getPocetniIznos() * exchangeRate);
				
				System.out.println(t.getPocetniIznos() + " " + t.getIzvornaValuta() + " je " + t.getKonvertovaniIznos() + " " + t.getKrajnjaValuta());
				
			} else {
				System.out.println("Greska kod konekcije");
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
