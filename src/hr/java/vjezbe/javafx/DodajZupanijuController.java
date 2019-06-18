package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;

import hr.java.vjezbe.entitet.Zupanija;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajZupanijuController {
	
	@FXML
	private TextField nazivTextfield;
	
	@FXML
	private ComboBox<Drzava> drzavaCombobox;
	
	@FXML
	private Button spremiButton;

	private static final Logger logger = LoggerFactory.getLogger(DodajZupanijuController.class);
	
	
	@FXML
	public void initialize() {
		
		List<Drzava> drzave = null;
		try {
			drzave = BazaPodataka.dohvatiDrzave();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		drzavaCombobox.getItems().addAll(drzave);
		drzavaCombobox.getSelectionModel().selectFirst();
		
		//zupanijaCombobox.getItems().addAll(zupanije);
		//vrstaCombobox.getItems().addAll(VrstaMjesta.values());
		
		PocetniEkranController.setUpValidation(nazivTextfield, false);
	}
	
	public void dodajZupaniju() {
		
		String warning = "";
		
		if(nazivTextfield.getText().trim().equals(""))
			warning += "Nije unesen naziv\n";
		
		if(nazivTextfield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste brojeve u nazivu\n";
		
		if(warning.equals("") == false){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input error");
			alert.setHeaderText(null);
			alert.setContentText(warning);

			alert.showAndWait();
			return;
		}

		String naziv = nazivTextfield.getText();

		Drzava drzava = drzavaCombobox.getValue();

		Zupanija zupanija = new Zupanija(naziv, drzava);

		try {
			BazaPodataka.spremiZupaniju(zupanija);
		} catch (SQLException | IOException e) {
			 logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}

		 Alert alert = new Alert(AlertType.INFORMATION);
		
		 alert.setTitle("Uspješno spremanje zupanije!");
		 alert.setHeaderText("Uspješno spremanje zupanije!");
		 alert.setContentText("Uneseni podaci za zupaniju su uspješno spremljeni.");
		 alert.showAndWait();
		
		 Stage stage = (Stage) spremiButton.getScene().getWindow();
		
		 stage.close();
		
		 ZupanijeController.dodajNovuZupaniju(zupanija);

		
	}
	
	
	
}
