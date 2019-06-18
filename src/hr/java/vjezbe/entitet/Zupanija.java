package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Zupanija extends BazniEntitet{
	private String naziv;
	private Drzava drzava;
	private List<Mjesto> mjesta;
	
	public Zupanija(String naziv, Drzava drzava, int id) {
		super(id);
		this.naziv = naziv;
		this.drzava = drzava;
		mjesta = new ArrayList<>();
	}
	
	public Zupanija(String naziv, Drzava drzava) {
		super(0);
		this.naziv = naziv;
		this.drzava = drzava;
		mjesta = new ArrayList<>();
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public Drzava getDrzava() {
		return drzava;
	}
	
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	
	public List<Mjesto> getMjesta() {
		return mjesta;
	}
	
	
	@Override
	public String toString() {
		return naziv;
	}
}
