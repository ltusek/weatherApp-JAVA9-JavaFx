package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Random;

import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;

public class SenzorTemperature extends Senzor {
	
	public SenzorTemperature(int id, String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(id, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}
	
	public SenzorTemperature(String mjernaJedinica, double preciznost, BigDecimal vrijednost, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		super(mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
	}

	@Override
	public String dohvatiVrijednost() {
		String npr = "Komponenta: " + super.getMjernaJedinica() + ", vrijednost: " + super.getVrijednost() + " " + super.getMjernaJedinica();
		return npr;
	}

	public void generirajVrijednost() throws VisokaTemperaturaException{
		Random rand = new Random();
		int max = 50;
		int min = -50;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		super.setVrijednost(BigDecimal.valueOf(randomNum));
		
		BigDecimal niskaGranica = new BigDecimal(-10);
		BigDecimal visokaGranica = new BigDecimal(40);
		
		if(super.getVrijednost().compareTo(visokaGranica) == 1)
			throw new VisokaTemperaturaException("Temperatura od " + super.getVrijednost() + "°C je previsoka");
		
		if(super.getVrijednost().compareTo(niskaGranica) == -1)
			throw new NiskaTemperaturaException("Temperatura od " + super.getVrijednost() + "°C je preniska");
	}
	
	@Override
	public String toString() {
		return "\nSenzor temperature";
	}

	
}
