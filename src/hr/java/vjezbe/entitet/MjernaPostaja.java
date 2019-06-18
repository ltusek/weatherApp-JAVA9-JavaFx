package hr.java.vjezbe.entitet;


import java.util.List;

public class MjernaPostaja extends BazniEntitet{
	//private static final int BROJ_SENZORA = 3;
	
	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	private Senzori<Senzor> senzori = new Senzori<>();
	//private List<Senzor> senzori = new ArrayList<>();
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka) {
		super(0);
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
	}
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzori<Senzor> senzori) {
		super(0);
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.senzori = senzori;
	}

	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public Mjesto getMjesto() {
		return mjesto;
	}
	
	public void setMjesto(Mjesto mjesto) {
		this.mjesto = mjesto;
	}
	
	public GeografskaTocka getGeografskaTocka() {
		return geografskaTocka;
	}
	
	public void setGeografskaTocka(GeografskaTocka geografskaTocka) {
		this.geografskaTocka = geografskaTocka;
	}
	
	public List<Senzor> dohvatiSenzore() {
		return senzori.getSortedList();
	}
	
	@Override
	public String toString() {
		return naziv;
	}
	

	
	
}
