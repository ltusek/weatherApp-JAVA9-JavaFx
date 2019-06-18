package hr.java.vjezbe.entitet;

public interface RadioSondazna {
	
	public void podesiVisinuPostaje(int visina);
	
	public int dohvatiVisinuPostaje();

	private void provjeriVisinu() {
		if(dohvatiVisinuPostaje() > 1000)
			podesiVisinuPostaje(1000);
	}

	default public int povecajVisinu() {
		int vis = dohvatiVisinuPostaje() + 1;
		podesiVisinuPostaje(vis);
		provjeriVisinu();
		return dohvatiVisinuPostaje();
	}
}
