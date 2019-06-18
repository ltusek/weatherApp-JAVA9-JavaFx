package hr.java.vjezbe.javafx;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajPostajuController {
	
	@FXML
	private TextField nazivTextfield;
	
	@FXML
	private TextField koordinataXTextfield;
	
	@FXML
	private TextField koordinataYTextfield;
	
	@FXML
	private ComboBox<Mjesto> mjestoCombobox;
	
	@FXML
	private Button spremiButton;

	private static final Logger logger = LoggerFactory.getLogger(DodajMjestoController.class);
	
	
	@FXML
	public void initialize() {
		
		List<Mjesto> mjesta = null;
		try {
			mjesta = BazaPodataka.dohvatiMjesta();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mjestoCombobox.getItems().addAll(mjesta);	
		mjestoCombobox.getSelectionModel().selectFirst();
		
		PocetniEkranController.setUpValidation(nazivTextfield, false);
		PocetniEkranController.setUpValidation(koordinataXTextfield, true);
		PocetniEkranController.setUpValidation(koordinataYTextfield, true);
	}
	
	public void dodajPostaju() {
		
		String warning = "";
		
		if(nazivTextfield.getText().trim().equals(""))
			warning += "Nije unesen naziv\n";
		
		if(koordinataXTextfield.getText().trim().equals(""))
			warning += "Nije unesena koordinata X\n";
		
		if(koordinataYTextfield.getText().trim().equals(""))
			warning += "Nije unesena koordinata Y\n";
		
		if(nazivTextfield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste brojeve u nazivu\n";
		
		if(!koordinataXTextfield.getText().matches("\\d*\\.?\\d+"))
			warning += "Unijeli ste nedopustene znakove u polju koordinate X\n";
		
		if(!koordinataYTextfield.getText().matches("\\d*\\.?\\d+"))
			warning += "Unijeli ste nedopustene znakove u polju koordinate Y\n";
	
		//string.matches(".*\\d+.*");
		if(warning.equals("") == false){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Input error");
			alert.setHeaderText(null);
			alert.setContentText(warning);

			alert.showAndWait();
			return;
		}
		

		String naziv = nazivTextfield.getText();
		
		BigDecimal x = new BigDecimal(koordinataXTextfield.getText());
		BigDecimal y = new BigDecimal(koordinataYTextfield.getText());
		
		Mjesto mjesto = mjestoCombobox.getValue();
		
		MjernaPostaja postaja = new MjernaPostaja(naziv, mjesto, new GeografskaTocka(x, y));
		
		try {
			BazaPodataka.spremiPostaju(postaja);
		} catch (SQLException | IOException e) {
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}

	     Alert alert = new Alert(AlertType.INFORMATION);

	     alert.setTitle("Uspješno spremanje mjerne postaje!");
	     alert.setHeaderText("Uspješno spremanje mjerne postaje!");
	     alert.setContentText("Uneseni podaci za mjernu postaju su uspješno spremljeni.");
	     alert.showAndWait();

		 Stage stage = (Stage) spremiButton.getScene().getWindow();

	     stage.close();

	     MjernePostajeController.dodajNovuPostaju(postaja);

	}
	
}
