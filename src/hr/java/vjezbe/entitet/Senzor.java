package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Senzor extends BazniEntitet{
	private String mjernaJedinica;
	private double preciznost;
	private BigDecimal vrijednost;
	private RadSenzora radSenzora;
	private MjernaPostaja mjernaPostaja;
	

	public Senzor(int id, String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(id);
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
		this.vrijednost = vrijednost;
		this.radSenzora = radSenzora;
		this.mjernaPostaja = mjernaPostaja;
	}
	
	public Senzor(String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(0);
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
		this.vrijednost = vrijednost;
		this.radSenzora = radSenzora;
		this.mjernaPostaja = mjernaPostaja;
	}

	public String getMjernaJedinica() {
		return mjernaJedinica;
	}
	
	public void setMjernaJedinica(String mjernaJedinica) {
		this.mjernaJedinica = mjernaJedinica;
	}
	
	public double getPreciznost() {
		return preciznost;
	}
	
	public void setPreciznost(double preciznost) {
		this.preciznost = preciznost;
	}
	
	public BigDecimal getVrijednost() {
		return vrijednost;
	}
	
	public void setVrijednost(BigDecimal vrijednost) {
		this.vrijednost = vrijednost;
	}
	
	public RadSenzora getRadSenzora() {
		return radSenzora;
	}
	public void setRadSenzora(RadSenzora radSenzora) {
		this.radSenzora = radSenzora;
	}
	
	public MjernaPostaja getMjernaPostaja() {
		return mjernaPostaja;
	}

	public void setMjernaPostaja(MjernaPostaja mjernaPostaja) {
		this.mjernaPostaja = mjernaPostaja;
	}

	public abstract String dohvatiVrijednost();


	
	
}
