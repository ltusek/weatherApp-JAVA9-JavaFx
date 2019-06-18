package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class SenzorVjetra extends Senzor {

	
	public SenzorVjetra(int id, String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(id, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}
	
	public SenzorVjetra(String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}

	@Override
	public String dohvatiVrijednost() {
		String npr = "Velièina: " + super.getMjernaJedinica() + ", vrijednost: " + super.getVrijednost() + " " + super.getMjernaJedinica();
		return npr;
	}
	
	
	@Override
	public String toString() {
		return "\nSenzor brzine vjetra";
	}
	

}
