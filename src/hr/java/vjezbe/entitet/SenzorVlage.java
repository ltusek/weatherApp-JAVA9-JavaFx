package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class SenzorVlage extends Senzor {
	
	
	public SenzorVlage(int id, String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(id, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}
	
	public SenzorVlage(String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}
	
	@Override
	public String dohvatiVrijednost() {
		String npr = "Vrijednost: " + super.getVrijednost() + " " + super.getMjernaJedinica() + " vlage zraka";
		return npr;
	}
	
	@Override
	public String toString() {
		return "\nSenzor vlage";
	}


}
