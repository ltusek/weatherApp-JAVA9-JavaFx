package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import java.util.HashMap;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.MjernePostaje;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVjetra;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Senzori;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;
import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.MojException;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.GeografskaTocka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glavna {
	
	private static final int BROJ_MJERNIH_POSTAJA = 3;
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	
	private static MjernePostaje<MjernaPostaja> mjernePostaje = new MjernePostaje<>();
	
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		for(int i = 0; i < BROJ_MJERNIH_POSTAJA; i++) {
			System.out.println("Unesite " + (i+1) + ". mjernu postaju:");
			logger.info("Unesite " + (i+1) + ". mjernu postaju:");
			if(i < 2)
				mjernePostaje.add(mjernaPostajaUnos(scanner));
			else
				mjernePostaje.add(mjernaPostajaRadioUnos(scanner));	
		}

		ispisPodataka();
		
		sorterIspisZupanija();
		
		ispisSenzoraMjesta();
		
		//generirajTemp();
		
		
		scanner.close();
		

	}
	
	public static MjernaPostaja mjernaPostajaRadioUnos(Scanner scanner) {
		
		String naziv = stringUnos("Unesite naziv radio sondažne mjerne postaje:", scanner);

		int visina = MojException.tryInt("Unesite visinu radio sondažne mjerne postaje:", scanner);
		
		Mjesto mjesto = mjeUnos(scanner);
		
		GeografskaTocka geoTocka = geoUnos(scanner);
		
		Senzori<Senzor> senzori = new Senzori<>();
		
		senzori.add(senzTempUnos(scanner));
		senzori.add(senzVlagaUnos(scanner));
		senzori.add(senzVjetarUnos(scanner));

		return new RadioSondaznaMjernaPostaja(naziv, mjesto, geoTocka, senzori, visina);
	}


	public static MjernaPostaja mjernaPostajaUnos(Scanner scanner) {

		String naziv = stringUnos("Unesite naziv mjerne postaje:", scanner);
		
		Mjesto mjesto = mjeUnos(scanner);
		
		GeografskaTocka geoTocka = geoUnos(scanner);
		
		Senzori<Senzor> senzori = new Senzori<>();
		
		senzori.add(senzTempUnos(scanner));
		senzori.add(senzVlagaUnos(scanner));
		senzori.add(senzVjetarUnos(scanner));

		return new MjernaPostaja(naziv, mjesto, geoTocka, senzori);
	}
	
	public static Mjesto mjeUnos(Scanner scanner) {
	
		Zupanija zupanija = zupUnos(scanner);

		String naziv = stringUnos("Unesite naziv mjesta:", scanner);
		
		VrstaMjesta vrstaMjesta = vrstaMjestaOdabir(scanner);
		
		Optional<MjernaPostaja> postaja = mjernePostaje.getSortedList().stream().filter(p -> p.getMjesto().getNaziv().equals(naziv)).findFirst();
		
		if(postaja.isPresent())
			 return postaja.get().getMjesto();
		
		Mjesto mjesto = new Mjesto(naziv, zupanija, vrstaMjesta);
		
		zupanija.getMjesta().add(mjesto);
		
		return mjesto;
	}
	
	public static Zupanija zupUnos(Scanner scanner) {
		
		Drzava drzava = drzUnos(scanner);
		
		String naziv = stringUnos("Unesite naziv županije:", scanner);
		
		Optional<MjernaPostaja> postaja = mjernePostaje.getSortedList().stream().filter(p -> p.getMjesto().getZupanija().getNaziv().equals(naziv)).findFirst();
		
		if(postaja.isPresent())
			 return postaja.get().getMjesto().getZupanija();
		 
		Zupanija zupanija = new Zupanija(naziv, drzava);
		 
		drzava.getZupanije().add(zupanija);
		
		return zupanija;
	}
	
	public static Drzava drzUnos(Scanner scanner) {
		
		String naziv = stringUnos("Unesite naziv države:", scanner);
		
		BigDecimal povrsina = MojException.tryBigDecimal("Unesite površinu države:", scanner);
		
		return new Drzava(naziv, povrsina);
	}
	
	public static GeografskaTocka geoUnos(Scanner scanner) {
		
		BigDecimal x = MojException.tryBigDecimal("Unesite Geo koordinatu X:", scanner);
		
		BigDecimal y = MojException.tryBigDecimal("Unesite Geo koordinatu Y:", scanner);
		
		return new GeografskaTocka(x, y);
	}
	
	public static Senzor senzTempUnos(Scanner scanner) {
		
		String naziv = stringUnos("Unesite elektronièku komponentu za senzor temperature:", scanner);
		
		BigDecimal vrijednost = MojException.tryBigDecimal("Unesite vrijednost senzora temperature:", scanner);
		
		RadSenzora radSenzora = radSenzorOdabir(scanner);
		return null;
		
//		return new SenzorTemperature(vrijednost, naziv, radSenzora);
	}
	
	public static Senzor senzVjetarUnos(Scanner scanner) {
	
		String naziv = stringUnos("Unesite velièinu senzora brzine vjetra:", scanner);
		
		BigDecimal vrijednost = MojException.tryBigDecimal("Unesite vrijednost senzora vjetra:", scanner);
		
		RadSenzora radSenzora = radSenzorOdabir(scanner);
		return null;
		
//		return new SenzorVjetra(vrijednost, naziv, radSenzora);
	}

	
	public static Senzor senzVlagaUnos(Scanner scanner) {
	
		BigDecimal vrijednost = MojException.tryBigDecimal("Unesite vrijednost senzora vlage:", scanner);
		
		RadSenzora radSenzora = radSenzorOdabir(scanner);
		return null;
		
//		return new SenzorVlage(vrijednost, radSenzora);
	}
	
	
	public static void generirajTemp() {
		while(true) {
			for(int i = 0; i < BROJ_MJERNIH_POSTAJA; i++) {
				
				try {
					((SenzorTemperature) mjernePostaje.get(i).dohvatiSenzore().get(2)).generirajVrijednost();
					
				} catch(VisokaTemperaturaException e) {
					System.out.println();
					System.out.println("Pogresna temperatura postaje " + mjernePostaje.get(i).getNaziv());
					logger.error("Pogresna temperatura postaje " + mjernePostaje.get(i).getNaziv(), e);
					System.out.println(e.getMessage());
					//logger.info(e.getMessage(), e);
					
					
				} catch(NiskaTemperaturaException e) {
					System.out.println();
					System.out.println("Pogresna temperatura postaje " + mjernePostaje.get(i).getNaziv());
					logger.error("Pogresna temperatura postaje " + mjernePostaje.get(i).getNaziv(), e);
					System.out.println(e.getMessage());
					//logger.info(e.getMessage(), e);
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static RadSenzora radSenzorOdabir(Scanner scanner) {
		for (int i = 0; i < RadSenzora.values().length - 1; i++) {
		     System.out.println((i + 1) + ". " + RadSenzora.values()[i]);
		}
		 
		Integer redniBrojSenzora = null;
		 
		while (true) {
			System.out.print("Odabir rada senzora >> ");
		     try {
		          redniBrojSenzora = scanner.nextInt();
		          scanner.nextLine();
		          break;
		     } catch (InputMismatchException ex) {
		          System.out.println("Neispravan unos!");
		          logger.error("Neispravan unos rada senzora!", ex);
		     }
		     scanner.nextLine();
		}
		 
		RadSenzora radSenzora;
		
		if (redniBrojSenzora >= 1 && redniBrojSenzora < RadSenzora.values().length) {
		     radSenzora = RadSenzora.values()[redniBrojSenzora - 1];
		} else {
		     radSenzora = RadSenzora.OSTALO;
		}
		
		return radSenzora;
	}

	public static VrstaMjesta vrstaMjestaOdabir(Scanner scanner) {
		for (int i = 0; i < VrstaMjesta.values().length - 1; i++) {
		     System.out.println((i + 1) + ". " + VrstaMjesta.values()[i]);
		}
		
		Integer redniBrojMjesta = null;
		 
		while (true) {
		     System.out.print("Odabir >> ");
		     try {
		          redniBrojMjesta = scanner.nextInt();
		          scanner.nextLine();
		          break;
		     } catch (InputMismatchException ex) {
		          System.out.println("Neispravan unos!");
		          logger.error("Neispravan unos rada senzora!", ex);
		     }
		     scanner.nextLine();
		}
		 
		VrstaMjesta vrstaMjesta;
		
		if (redniBrojMjesta >= 1 && redniBrojMjesta < VrstaMjesta.values().length) {
			vrstaMjesta = VrstaMjesta.values()[redniBrojMjesta - 1];
		} else {
			vrstaMjesta = VrstaMjesta.OSTALO;
		}
		
		return vrstaMjesta;
	}

	public static String stringUnos(String x, Scanner scanner) {
		System.out.println(x);
		x = scanner.nextLine();
		return x;
	}

	public static void ispisPodataka() {
				
		mjernePostaje.getSortedList().stream().forEach(obj -> {
			System.out.println("\n----------------------------------------------");
			System.out.println("Naziv mjerne postaje: " + obj.getNaziv());
				
			if(obj instanceof RadioSondaznaMjernaPostaja) {
				System.out.println("Postaja je radio sondažna");
				System.out.println("Visina radio sondažne mjerne postaje: " + ((RadioSondaznaMjernaPostaja)obj).povecajVisinu());
			}
			
			System.out.println("Postaja se nalazi u mjestu " + obj.getMjesto().getNaziv() + ", županiji " + obj.getMjesto().getZupanija().getNaziv() + ", državi " + obj.getMjesto().getZupanija().getDrzava().getNaziv());
			System.out.println("Toène koodridnate postaje su x:" + obj.getGeografskaTocka().getX() + " y:" + obj.getGeografskaTocka().getY());
			
			obj.dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost() + ", Naèin rada: " + senzor.getRadSenzora()));
		});
		
	}

	public static void sorterIspisZupanija() {
		List<Zupanija> listaZupanija = new ArrayList<>();
		
		for(int i = 0; i < BROJ_MJERNIH_POSTAJA; i++) {
			listaZupanija.add(mjernePostaje.get(i).getMjesto().getZupanija());
		}
		
		Collections.sort(listaZupanija, new ZupanijaSorter());
		
		List<String> listeZup = new ArrayList<>();
		
		for(int i = 0; i < listaZupanija.size(); i++) 
			listeZup.add(listaZupanija.get(i).getNaziv());
		
		System.out.println("\nIspis sortiranih županija:");
		
		for(int i = 0; i < listeZup.size(); i++) {
			int j = 0;
			String ispis1 = listeZup.get(i);
			for(String ispis2 : listeZup) {
				if(ispis1.equals(ispis2) == true && (j == 0)) {
					System.out.println(ispis1);
					j++;
				}
			}
		}
		
		/*
		System.out.println("PROBA:\n" + listaZupanija);
		
		Set<String> zup = new HashSet<>();
		
		for(int i = 0; i < listaZupanija.size(); i++) 
			zup.add(listaZupanija.get(i).getNaziv());
		
		System.out.println();
		System.out.println("Ispis sortiranih županija:");
		for(String ispis : zup) {
			System.out.println(ispis);
		}*/

	}

	public static void ispisSenzoraMjesta() {
		Map<Mjesto, List<Senzor>> map = new HashMap<>();
		
		
		for(int i = 0; i < BROJ_MJERNIH_POSTAJA; i++) {
			map.put(mjernePostaje.get(i).getMjesto(), mjernePostaje.get(i).dohvatiSenzore());
		}
		
		map.forEach((k,v)-> System.out.println("\nU mjestu " + k.getNaziv() + " su sljedeæi senzori:" + v.toString().replace("[", "").replace("]", "").replace(",", "")));
		
		
	}
}
