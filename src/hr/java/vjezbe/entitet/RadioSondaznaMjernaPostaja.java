package hr.java.vjezbe.entitet;

public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondazna {
	
	private int visina;
	
	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzori<Senzor> senzori, int visina, int id){
		super(naziv, mjesto, geografskaTocka, senzori);
		this.visina = visina;
	}
	
	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzori<Senzor> senzori, int visina){
		super(naziv, mjesto, geografskaTocka, senzori);
		this.visina = visina;
	}
	
	@Override
	public void podesiVisinuPostaje(int visina) {
		// TODO Auto-generated method stub
		this.visina = visina;
	}

	@Override
	public int dohvatiVisinuPostaje() {
		return visina;
	}

}
