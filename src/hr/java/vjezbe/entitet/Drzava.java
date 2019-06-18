package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Drzava extends BazniEntitet {
	private String naziv;
	private BigDecimal povrsina;
	private List<Zupanija> zupanije;
	
	public Drzava(String naziv, BigDecimal povrsina, int id) {
		super(id);
		this.naziv = naziv;
		this.povrsina = povrsina;
		zupanije = new ArrayList<>();
	}
	
	public Drzava(String naziv, BigDecimal povrsina) {
		super(0);
		this.naziv = naziv;
		this.povrsina = povrsina;
		zupanije = new ArrayList<>();
	}
	

	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public BigDecimal getPovrsina() {
		return povrsina;
	}
	
	public void setPovrsina(BigDecimal povrsina) {
		this.povrsina = povrsina;
	}
	
	public List<Zupanija> getZupanije() {
		return zupanije;
	}
	
	@Override
	public String toString() {
		return naziv;
	}


	
	
}
