package hr.java.vjezbe.baza.podataka;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVjetra;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;

public class BazaPodataka {
	
	private static String dataBaseLocation, dataBaseUser, dataBasePassword;
	
	private static Connection spajanjeNaBazuPodataka() {
		Connection konekcija = null;
		try {
			Properties svojstva = new Properties();
			svojstva.load(new FileReader("bazaPodataka.properties"));
			dataBaseLocation = svojstva.getProperty("bazaPodatakaUrl");
			dataBaseUser = svojstva.getProperty("korisnickoIme");
			dataBasePassword = svojstva.getProperty("lozinka");
			
			konekcija = DriverManager.getConnection(dataBaseLocation, dataBaseUser, dataBasePassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return konekcija;		
	}
	
	private static void zatvaranjeVezeNaBazuPodataka(Connection konekcija) {
		try {
			konekcija.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void izmjeniDrzavu(Drzava drzava) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        String state = "UPDATE POSTAJE.DRZAVA SET NAZIV = ? ," + "POVRSINA = ? " + "WHERE ID = ?";
        try {
        	
              PreparedStatement izmjeniDrzava = veza.prepareStatement(state);
              izmjeniDrzava.setString(1, drzava.getNaziv());
              izmjeniDrzava.setBigDecimal(2, drzava.getPovrsina());
              izmjeniDrzava.setInt(3, drzava.getId());
              
              izmjeniDrzava.executeUpdate();
              
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiDrzavu(Drzava drzava) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        try {

              PreparedStatement insertDrzava = veza.prepareStatement("INSERT INTO POSTAJE.DRZAVA (NAZIV, POVRSINA) VALUES (?, ?);");
              insertDrzava.setString(1, drzava.getNaziv());
              insertDrzava.setBigDecimal(2, drzava.getPovrsina());

              insertDrzava.executeUpdate();
              
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiZupaniju(Zupanija zupanija) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        try {

              PreparedStatement insertZupanija = veza.prepareStatement("INSERT INTO POSTAJE.ZUPANIJA (NAZIV, DRZAVA_ID) VALUES (?, ?);");
              
              insertZupanija.setString(1, zupanija.getNaziv());
    
              insertZupanija.setInt(2, zupanija.getDrzava().getId());
              
              insertZupanija.executeUpdate();
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiMjesto(Mjesto mjesto) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        try {
        	
              PreparedStatement insertMjesto = veza.prepareStatement("INSERT INTO POSTAJE.MJESTO (NAZIV, VRSTA, ZUPANIJA_ID) VALUES (?, ?, ?);");
              insertMjesto.setString(1, mjesto.getNaziv());
              insertMjesto.setString(2, mjesto.getVrstaMjesta().toString());
              insertMjesto.setInt(3, mjesto.getZupanija().getId()); 
             
              insertMjesto.executeUpdate();
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiPostaju(MjernaPostaja postaja) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        try {
        	
              PreparedStatement insertPostaja = veza.prepareStatement("INSERT INTO POSTAJE.MJERNA_POSTAJA (NAZIV, MJESTO_ID, LAT, LNG) VALUES (?, ?, ?, ?);");
              insertPostaja.setString(1, postaja.getNaziv());
              insertPostaja.setInt(2, postaja.getMjesto().getId()); 
              insertPostaja.setBigDecimal(3, postaja.getGeografskaTocka().getX());
              insertPostaja.setBigDecimal(4, postaja.getGeografskaTocka().getY());
             
              insertPostaja.executeUpdate(); 
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	
	public static void spremiSenzor(Senzor senzor) throws SQLException, IOException {

        Connection veza = spajanjeNaBazuPodataka();
        veza.setAutoCommit(false);
        try {
        	
              PreparedStatement insertSenzor = veza.prepareStatement("INSERT INTO POSTAJE.SENZOR (MJERNA_JEDINICA, PRECIZNOST, VRIJEDNOST, RAD_SENZORA, MJERNA_POSTAJA_ID) VALUES (?, ?, ?, ?, ?);");
              insertSenzor.setString(1, senzor.getMjernaJedinica());
              insertSenzor.setDouble(2, senzor.getPreciznost());
              insertSenzor.setBigDecimal(3, senzor.getVrijednost());
              insertSenzor.setString(4, senzor.getRadSenzora().toString());
              insertSenzor.setInt(5, senzor.getMjernaPostaja().getId());
             
              insertSenzor.executeUpdate(); 
              veza.commit();
              veza.setAutoCommit(true);
        }catch(Throwable ex) {

              veza.rollback();
              throw ex;
        }
        zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	
	public static List<Drzava> dohvatiDrzave() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementDrzave = veza.createStatement();

		ResultSet resultSetDrzave = statementDrzave.executeQuery("SELECT * FROM POSTAJE.DRZAVA");

		List<Drzava> listaDrzava = new ArrayList<>();
		while (resultSetDrzave.next()) {

			Integer drzavaId = resultSetDrzave.getInt("ID");

			String naziv = resultSetDrzave.getString("NAZIV");

			BigDecimal povrsina = resultSetDrzave.getBigDecimal("POVRSINA");
			Drzava drzava = new Drzava(naziv, povrsina);
             
			drzava.setId(drzavaId);
             
			listaDrzava.add(drzava);
        }
		zatvaranjeVezeNaBazuPodataka(veza);
      
		return listaDrzava;
	}
	
	public static List<Zupanija> dohvatiZupanije() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZupanije = veza.createStatement();

		ResultSet resultSetZupanije = statementZupanije.executeQuery("SELECT * FROM POSTAJE.ZUPANIJA");

		List<Zupanija> listaZupanija = new ArrayList<>();
		while (resultSetZupanije.next()) {

			Integer zupanijaID = resultSetZupanije.getInt("ID");

			String naziv = resultSetZupanije.getString("NAZIV");

			Integer drzavaID = resultSetZupanije.getInt("DRZAVA_ID");
			Drzava drzava = dohvatiDrzave().get(drzavaID-1);
			
			Zupanija zupanija = new Zupanija(naziv, drzava);
             
			zupanija.setId(zupanijaID);
             
			listaZupanija.add(zupanija);
        }
		zatvaranjeVezeNaBazuPodataka(veza);
      
		return listaZupanija;
	}
	
	public static List<Mjesto> dohvatiMjesta() throws SQLException, IOException{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementMjesta = veza.createStatement();
		ResultSet resultSetMjesta = statementMjesta.executeQuery("SELECT * FROM POSTAJE.MJESTO");
		
		List<Mjesto> listaMjesta = new ArrayList<>();
		
		while(resultSetMjesta.next()) {
			
			Integer mjestoID = resultSetMjesta.getInt("ID");
			String naziv = resultSetMjesta.getString("NAZIV");
			VrstaMjesta vrstaMjesta = VrstaMjesta.valueOf(resultSetMjesta.getString("VRSTA"));
			Integer zupanijaID = resultSetMjesta.getInt("ZUPANIJA_ID");
			
			Zupanija zupanija = dohvatiZupanije().get(zupanijaID-1);
			
			Mjesto mjesto = new Mjesto(naziv, zupanija, vrstaMjesta);
			mjesto.setId(mjestoID);
			
			listaMjesta.add(mjesto);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaMjesta;
	}
	
	public static List<MjernaPostaja> dohvatiMjernePostaje() throws SQLException, IOException{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementPostaje = veza.createStatement();
		ResultSet resultSetPostaje = statementPostaje.executeQuery("SELECT * FROM POSTAJE.MJERNA_POSTAJA");
		
		List<MjernaPostaja> listaPostaja = new ArrayList<>();
		
		while(resultSetPostaje.next()) {
			
			Integer postajaID = resultSetPostaje.getInt("ID");
			String naziv = resultSetPostaje.getString("NAZIV");
			Integer mjestoID = resultSetPostaje.getInt("MJESTO_ID");
			BigDecimal x = resultSetPostaje.getBigDecimal("LAT");
			BigDecimal y = resultSetPostaje.getBigDecimal("LNG");
			
			Mjesto mjesto = dohvatiMjesta().get(mjestoID-1);
			
			GeografskaTocka geoTocka = new GeografskaTocka(x, y);
			
			MjernaPostaja mjernaPostaja = new MjernaPostaja(naziv, mjesto, geoTocka);
			
			mjernaPostaja.setId(postajaID);
			
			listaPostaja.add(mjernaPostaja);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaPostaja;
	}
	
	public static List<Senzor> dohvatiSenzore() throws SQLException, IOException{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementSenzor = veza.createStatement();
		ResultSet resultSetSenzori = statementSenzor.executeQuery("SELECT * FROM POSTAJE.SENZOR");
		
		List<Senzor> listaSenzora = new ArrayList<>();
		
		while(resultSetSenzori.next()) {
			
			Integer senzorID = resultSetSenzori.getInt("ID");
			String mjernaJedinica = resultSetSenzori.getString("MJERNA_JEDINICA");
			double preciznost = resultSetSenzori.getDouble("PRECIZNOST");
			BigDecimal vrijednost = resultSetSenzori.getBigDecimal("VRIJEDNOST");
			RadSenzora radSenzora = RadSenzora.valueOf(resultSetSenzori.getString("RAD_SENZORA"));
			Integer postajaID = resultSetSenzori.getInt("MJERNA_POSTAJA_ID");
	
			MjernaPostaja mjernaPostaja = dohvatiMjernePostaje().get(postajaID-1);
			
			Senzor senzor = null;
			
			if((senzorID % 3) == 1)
				senzor = new SenzorTemperature(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			if((senzorID % 3) == 2)
				senzor = new SenzorVjetra(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			if((senzorID / 3) == 1)
				senzor = new SenzorVlage(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			
			listaSenzora.add(senzor);

		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaSenzora;
	}
	
	public static List<Senzor> dohvatiSenzoreNegativne() throws SQLException, IOException{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementSenzor = veza.createStatement();
		ResultSet resultSetSenzori = statementSenzor.executeQuery("SELECT COUNT(*) AS broj FROM POSTAJE.SENZOR WHERE VRIJEDNOST < 0;");
		
		List<Senzor> listaSenzora = new ArrayList<>();
		
		while(resultSetSenzori.next()) {
			
			Integer senzorID = resultSetSenzori.getInt("ID");
			String mjernaJedinica = resultSetSenzori.getString("MJERNA_JEDINICA");
			double preciznost = resultSetSenzori.getDouble("PRECIZNOST");
			BigDecimal vrijednost = resultSetSenzori.getBigDecimal("VRIJEDNOST");
			RadSenzora radSenzora = RadSenzora.valueOf(resultSetSenzori.getString("RAD_SENZORA"));
			Integer postajaID = resultSetSenzori.getInt("MJERNA_POSTAJA_ID");
	
			MjernaPostaja mjernaPostaja = dohvatiMjernePostaje().get(postajaID-1);
			
			Senzor senzor = null;
			
			if((senzorID % 3) == 1)
				senzor = new SenzorTemperature(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			if((senzorID % 3) == 2)
				senzor = new SenzorVjetra(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			if((senzorID / 3) == 1)
				senzor = new SenzorVlage(senzorID, mjernaJedinica, preciznost, vrijednost, radSenzora, mjernaPostaja);
			
			listaSenzora.add(senzor);

		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaSenzora;
	}
	
	
}
