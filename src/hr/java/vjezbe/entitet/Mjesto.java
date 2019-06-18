package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Mjesto extends BazniEntitet{
	private String naziv;
	private Zupanija zupanija;
	private VrstaMjesta vrstaMjesta;
	private List<MjernaPostaja> mjernePostaje;
	
	public Mjesto(String naziv, Zupanija zupanija, VrstaMjesta vrstaMjesta, int id) {
		super(id);
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.vrstaMjesta = vrstaMjesta;
		mjernePostaje = new ArrayList<>();
	}
	
	public Mjesto(String naziv, Zupanija zupanija, VrstaMjesta vrstaMjesta) {
		super(0);
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.vrstaMjesta = vrstaMjesta;
		mjernePostaje = new ArrayList<>();
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Zupanija getZupanija() {
		return zupanija;
	}

	public void setZupanija(Zupanija zupanija) {
		this.zupanija = zupanija;
	}
	
	
	public VrstaMjesta getVrstaMjesta() {
		return vrstaMjesta;
	}
	
	public void setVrstaMjesta(VrstaMjesta vrstaMjesta) {
		this.vrstaMjesta = vrstaMjesta;
	}
		
	public List<MjernaPostaja> getMjernePostaje() {
		return mjernePostaje;
	}
	
	@Override
	public String toString() {
		return naziv;
	}
	
	
}
