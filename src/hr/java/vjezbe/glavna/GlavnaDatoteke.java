package hr.java.vjezbe.glavna;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.MjernePostaje;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVjetra;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Senzori;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;

public class GlavnaDatoteke {
	
	public static Map<Integer, Drzava> drzaveFromFile = new HashMap<>();
	public static Map<Integer, Zupanija> zupanijeFromFile = new HashMap<>();
	public static Map<Integer, Mjesto> mjestaFromFile = new HashMap<>();
	public static Map<Integer, Senzori<Senzor>> senzoriFromFile = new HashMap<>();
	public static Map<Integer, MjernaPostaja> postajeFromFile = new HashMap<>();
	public static Map<Integer, RadioSondaznaMjernaPostaja> radioPostajeFromFile = new HashMap<>();
	
	public static MjernePostaje<MjernaPostaja> mjernePostaje = new MjernePostaje<>();
	
	public static int BROJ_MJERNIH_POSTAJA = 2;
	public static int BROJ_RADIO_MJERNIH_POSTAJA = 1;


	public static void main(String[] args) {
		
		dohvatiDrzave();
		dohvatiZupanije();
		dohvatiMjesta();
		dohvatiSenzore();
		dohvatiMjernePostaje();
		dohvatiRadioSondazneMjernePostaje();
		
		mjernePostaje.getSortedList().stream().forEach(obj -> {
			System.out.println("\n----------------------------------------------");
			System.out.println("Naziv mjerne postaje: " + obj.getNaziv());
				
			if(obj instanceof RadioSondaznaMjernaPostaja) {
				System.out.println("Postaja je radio sondažna");
				System.out.println("Visina radio sondažne mjerne postaje: " + ((RadioSondaznaMjernaPostaja)obj).povecajVisinu());
			}
			
			System.out.println("Postaja se nalazi u mjestu " + obj.getMjesto().getNaziv() + ", županiji " + obj.getMjesto().getZupanija().getNaziv() + ", državi " + obj.getMjesto().getZupanija().getDrzava().getNaziv());
			System.out.println("Toène koodridnate postaje su x:" + obj.getGeografskaTocka().getX() + " y:" + obj.getGeografskaTocka().getY());
			System.out.println("Vrijednosti senzora postaje su:");
			obj.dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost() + ", Naèin rada: " + senzor.getRadSenzora()));
		});
		
		
		sorterIspisZupanija();
		ispisSenzoraMjesta();
		
	}
	
	public static void citanjeDatoteke(String FILE, List<String> list) {
		try(BufferedReader ulaz = new BufferedReader(new FileReader(FILE))){
			String unos = null;
			
			while((unos = ulaz.readLine()) != null) {
				list.add(unos);
			}		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void dohvatiDrzave() {
		
		Integer id = null;
		String naziv = null;
		BigDecimal povrsina;
		
		List<String> listaStringova = new ArrayList<>();
		String FILE_INPUT = "dat\\drzave.txt";
		
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 3;
		
		for(int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
				break;
				case 1: naziv = red;
				break;
				case 2: {
					povrsina = new BigDecimal(red);
					drzaveFromFile.put(id, new Drzava(naziv, povrsina, id));
				}
				break;
			}
		}	
	}
	
	public static void dohvatiZupanije() {
		
		Integer id = null;
		String naziv = null;		
		List<String> listaStringova = new ArrayList<>();
		
		String FILE_INPUT = "dat\\zupanije.txt";
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 3;
		
		for(int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
				break;
				case 1: naziv = red;
				break;
				case 2: {
					Integer dohvat = Integer.parseInt(red);
					Drzava novaDrzava = drzaveFromFile.get(dohvat);
					zupanijeFromFile.put(id, new Zupanija(naziv, novaDrzava, id));
				}
				break;
			}
		}	
	}
	
	public static void dohvatiMjesta() {
		
		Integer id = null, dohvat = null;
		String naziv = null;
		VrstaMjesta vrstaMj = null;
		
		List<String> listaStringova = new ArrayList<>();
		
		String FILE_INPUT = "dat\\mjesta.txt";
		
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 4;
		
		for(int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
				break;
				case 1: naziv = red;
				break;
				case 2: dohvat = Integer.parseInt(red);
				break;
				case 3:{
					
					String vrs = red;
					switch(vrs) {
						case "GRAD": vrstaMj = VrstaMjesta.GRAD; 
						break;
						case "SELO": vrstaMj = VrstaMjesta.SELO; 
						break;
						case "OSTALO": vrstaMj = VrstaMjesta.OSTALO; 
						break;
					}
					
					Zupanija zupanija = zupanijeFromFile.get(dohvat);
					mjestaFromFile.put(id, new Mjesto(naziv, zupanija, vrstaMj, id));
				}
					
			}
		}	
	}
	
	public static void dohvatiSenzore() {
		
		Integer id = null;
		
	//	Senzori<Senzor> senzori = new Senzori<>();
		
		String nazivTemp = null;
		String nazivVjetra = null;
		BigDecimal vrijednostTemp = null;
		BigDecimal vrijednostVlage = null;
		BigDecimal vrijednostVjetra = null;
		
		RadSenzora radSenzoraTemp = null;
		RadSenzora radSenzoraVlaga = null;
		RadSenzora radSenzoraVjetar = null;
		

		List<String> listaStringova = new ArrayList<>();
		
		String FILE_INPUT = "dat\\senzori.txt";
		
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 9;
		
		for(int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
					break;
				case 1: nazivTemp = red;
					break;
				case 2: vrijednostTemp = new BigDecimal(red);
					break;
				case 3: radSenzoraTemp = enumSwitchRadSenzora(red);		
					break;
				case 4: vrijednostVlage = new BigDecimal(red);
					break;
				case 5: radSenzoraVlaga = enumSwitchRadSenzora(red);			
					break;
				case 6: nazivVjetra = red;
					break;
				case 7: vrijednostVjetra = new BigDecimal(red);
					break;
				case 8: {
					radSenzoraVjetar = enumSwitchRadSenzora(red);
					
					Senzori<Senzor> senzori = new Senzori<>();
					
//					senzori.add(new SenzorTemperature(vrijednostTemp, nazivTemp, radSenzoraTemp, id));
//					senzori.add(new SenzorVlage(vrijednostVlage, radSenzoraVlaga, id));
//					senzori.add(new SenzorVjetra(vrijednostVjetra, nazivVjetra, radSenzoraVjetar, id));
					
					senzoriFromFile.put(id, senzori);
					break;
				}
			}
		}	
	}
	
	public static RadSenzora enumSwitchRadSenzora(String red) {
		String vrsta = red;
		RadSenzora radSenzora = null;
		switch(vrsta) {
			case "STREAMING": radSenzora = RadSenzora.STREAMING; 
				break;
			case "PING": radSenzora = RadSenzora.PING; 
				break;
			case "OSTALO": radSenzora = RadSenzora.OSTALO; 
				break;
		}
		return radSenzora;
	}
	
	public static void dohvatiMjernePostaje() {
		Integer id = null, idMjesta = null, idSenzora = null;
		String naziv = null;
		BigDecimal x = null;
		BigDecimal y = null;
		
		Mjesto mjesto = null;
		
		Senzori<Senzor> senzori = null;
		
		List<String> listaStringova = new ArrayList<>();
		
		String FILE_INPUT = "dat\\postaje.txt";
		
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 6;
		int velicinaPostaji = brojRedova * BROJ_MJERNIH_POSTAJA;
		
		for(int i = 0; i < velicinaPostaji; i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
					break;
				case 1: naziv = red;
					break;
				case 2: x = new BigDecimal(red);
					break;
				case 3: y = new BigDecimal(red);
					break;
				case 4: {
					idMjesta = Integer.parseInt(red);
					mjesto = mjestaFromFile.get(idMjesta);
					break;
				}
				case 5: {
					idSenzora = Integer.parseInt(red);
					senzori = senzoriFromFile.get(idSenzora);
//					mjernePostaje.add(new MjernaPostaja(naziv, mjesto, new GeografskaTocka(x, y), senzori, id));
					//postajeFromFile.put(id, new MjernaPostaja(naziv, mjesto, new GeografskaTocka(x, y), senzori, id));
					break;
				}		
			}
		}	
	}
	
	public static void dohvatiRadioSondazneMjernePostaje() {
		Integer id = null, idMjesta = null, idSenzora = null;
		String naziv = null;
		BigDecimal x = null;
		BigDecimal y = null;
		Integer visina;
		Mjesto mjesto = null;
		
		Senzori<Senzor> senzori = null;
		
		List<String> listaStringova = new ArrayList<>();
		
		String FILE_INPUT = "dat\\radioPostaje.txt";
		
		citanjeDatoteke(FILE_INPUT, listaStringova);
		
		int brojRedova = 7;
		int velicinaPostaji = brojRedova * BROJ_RADIO_MJERNIH_POSTAJA;
		
		for(int i = 0; i < velicinaPostaji; i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedova) {
				case 0: id = Integer.parseInt(red);
					break;
				case 1: naziv = red;
					break;
				case 2: x = new BigDecimal(red);
					break;
				case 3: y = new BigDecimal(red);
					break;
				case 4: {
					idMjesta = Integer.parseInt(red);
					mjesto = mjestaFromFile.get(idMjesta);
					break;
				}
				case 5: {
					idSenzora = Integer.parseInt(red);
					senzori = senzoriFromFile.get(idSenzora);
					break;
				}
				case 6:{
					visina = Integer.parseInt(red);
					mjernePostaje.add(new RadioSondaznaMjernaPostaja(naziv, mjesto, new GeografskaTocka(x, y), senzori, visina, id));
					//radioPostajeFromFile.put(id, new RadioSondaznaMjernaPostaja(naziv, mjesto, new GeografskaTocka(x, y), senzori, visina, id));
					break;
				}
			}
		}	
	}
	


	public static void sorterIspisZupanija() {
		List<Zupanija> listaZupanija = new ArrayList<>();
		
		for(int i = 0; i < BROJ_MJERNIH_POSTAJA + BROJ_RADIO_MJERNIH_POSTAJA; i++) {
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
	}
	
	public static void ispisSenzoraMjesta() {
		Map<Mjesto, List<Senzor>> map = new HashMap<>();

		for(int i = 0; i < BROJ_MJERNIH_POSTAJA + BROJ_RADIO_MJERNIH_POSTAJA; i++) {
			map.put(mjernePostaje.get(i).getMjesto(), mjernePostaje.get(i).dohvatiSenzore());
		}
		
		map.forEach((k,v)-> System.out.println("\nU mjestu " + k.getNaziv() + " su sljedeæi senzori:" + v.toString().replace("[", "").replace("]", "").replace(",", "")));
		
	}

}
