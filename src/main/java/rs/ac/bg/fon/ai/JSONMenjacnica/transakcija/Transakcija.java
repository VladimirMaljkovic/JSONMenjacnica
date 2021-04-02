package rs.ac.bg.fon.ai.JSONMenjacnica.transakcija;

import java.util.Date;

public class Transakcija {

	private String izvornaValuta;
	private String krajnjaValuta;
	private double pocetniIznos;
	private double konvertovaniIznos;
	private Date datumTransakcije;

	public String getIzvornaValuta() {
		return izvornaValuta;
	}

	public void setIzvornaValuta(String izvornaValuta) {
		this.izvornaValuta = izvornaValuta;
	}

	public String getKrajnjaValuta() {
		return krajnjaValuta;
	}

	public void setKrajnjaValuta(String krajnjaValuta) {
		this.krajnjaValuta = krajnjaValuta;
	}

	public double getPocetniIznos() {
		return pocetniIznos;
	}

	public void setPocetniIznos(double pocetniIznos) {
		this.pocetniIznos = pocetniIznos;
	}

	public double getKonvertovaniIznos() {
		return konvertovaniIznos;
	}

	public void setKonvertovaniIznos(double konvertovaniIznos) {
		this.konvertovaniIznos = konvertovaniIznos;
	}

	public Date getDatumTransakcije() {
		return datumTransakcije;
	}

	public void setDatumTransakcije(Date datumTransakcije) {
		this.datumTransakcije = datumTransakcije;
	}

}
