package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Mjesto;

import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class DodajMjestoController {
	
	@FXML
	private TextField nazivTextfield;
	
	@FXML
	private ComboBox<Zupanija> zupanijaCombobox;
	
	@FXML
	private ComboBox<VrstaMjesta> vrstaCombobox;
	
	@FXML
	private Button spremiButton;

	private static final Logger logger = LoggerFactory.getLogger(DodajMjestoController.class);
	
	
	@FXML
	public void initialize() {
		
		PocetniEkranController.setUpValidation(nazivTextfield, false);
		
		List<Zupanija> zupanije = null;
		try {
			zupanije = BazaPodataka.dohvatiZupanije();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		zupanijaCombobox.getItems().addAll(zupanije);
		
		zupanijaCombobox.getSelectionModel().selectFirst();
		
		vrstaCombobox.getItems().addAll(VrstaMjesta.values());
		
		vrstaCombobox.getSelectionModel().selectFirst();
		
		//zupanijaCombobox.getItems().addAll(zupanije);
		//vrstaCombobox.getItems().addAll(VrstaMjesta.values());
		
	}
	
	public void dodajMjesto() {
		
		String warning = "";
		
		if(nazivTextfield.getText().trim().equals(""))
			warning += "Nije unesen naziv\n";
		
		if(nazivTextfield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste brojeve u nazivu\n";
		
	
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

		Zupanija zupanija = zupanijaCombobox.getValue();

		VrstaMjesta vrstaMjesta = vrstaCombobox.getValue();

		Mjesto mjesto = new Mjesto(naziv, zupanija, vrstaMjesta);
		
		try {
			BazaPodataka.spremiMjesto(mjesto);
		} catch (SQLException | IOException e) {
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}
		

		 Alert alert = new Alert(AlertType.INFORMATION);
		
		 alert.setTitle("Uspješno spremanje mjesta!");
		 alert.setHeaderText("Uspješno spremanje mjesta!");
		 alert.setContentText("Uneseni podaci za mjesto su uspješno spremljeni."); 
		 alert.showAndWait();
		
		 Stage stage = (Stage) spremiButton.getScene().getWindow();
		
		 stage.close();
		
		 MjestaController.dodajNovoMjesto(mjesto);

	}
	
	
}
