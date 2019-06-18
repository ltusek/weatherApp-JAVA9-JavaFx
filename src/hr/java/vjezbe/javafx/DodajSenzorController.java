package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVjetra;
import hr.java.vjezbe.entitet.SenzorVlage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajSenzorController {
	
	@FXML
	private TextField mjernaJedinicafield;
	
	@FXML
	private TextField preciznostTextfield;
	
	@FXML
	private TextField vrijednostTextfield;
	
	@FXML
	private ComboBox<String> vrstaSenzoraCombobox;
	
	@FXML
	private ComboBox<RadSenzora> radSenzoraCombobox;
	
	@FXML
	private ComboBox<MjernaPostaja> postajaCombobox;
	
	@FXML
	private Button spremiButton;
	
	private List<String> senzoriVrste = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(DodajMjestoController.class);
	
	
	@FXML
	public void initialize() {
		
		senzoriVrste = Main.dohvatiVrsteSenzora();
		
		List<MjernaPostaja> postaje = null;
		
		try {
			postaje = BazaPodataka.dohvatiMjernePostaje();
		}catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		vrstaSenzoraCombobox.getItems().addAll(senzoriVrste);
		vrstaSenzoraCombobox.getSelectionModel().selectFirst();
		
		//vrstaSenzoraCombobox.setOnShown(value);
		
		radSenzoraCombobox.getItems().addAll(RadSenzora.values());
		radSenzoraCombobox.getSelectionModel().selectFirst();
		
		postajaCombobox.getItems().addAll(postaje);
		postajaCombobox.getSelectionModel().selectFirst();
		
		PocetniEkranController.setUpValidation(mjernaJedinicafield, false);
		PocetniEkranController.setUpValidation(preciznostTextfield, true);
		PocetniEkranController.setUpValidation(vrijednostTextfield, true);

	}
	
	public void dodajSenzor() {
		
		String warning = "";
		
		if(mjernaJedinicafield.getText().trim().equals(""))
			warning += "Nije unesen naziv\n";
		
		if(preciznostTextfield.getText().trim().equals(""))
			warning += "Nije unesena preciznost\n";
		
		if(vrijednostTextfield.getText().trim().equals(""))
			warning += "Nije unesena vrijednost\n";
		
		if(mjernaJedinicafield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste brojeve u mjernoj jedinici\n";
		
		if(!preciznostTextfield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste nedopustene znakove u polju preciznosti\n";
		
		if(!vrijednostTextfield.getText().matches("\\d*\\.?\\d+"))
			warning += "Unijeli ste nedopustene znakove u polju vrijednosti\n";
	
		//string.matches(".*\\d+.*");
		if(warning.equals("") == false){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Input error");
			alert.setHeaderText(null);
			alert.setContentText(warning);

			alert.showAndWait();
			return;
		}

		String mjernaJedinica = mjernaJedinicafield.getText();	
		double preciznost = Double.parseDouble(preciznostTextfield.getText());
		
		BigDecimal vrijednost = new BigDecimal(vrijednostTextfield.getText());
		
		RadSenzora radSenzora = radSenzoraCombobox.getValue();
		
		MjernaPostaja postaja = postajaCombobox.getValue();
		
		Senzor senzor = null;
		
		
		
		if(vrstaSenzoraCombobox.getValue() == senzoriVrste.get(0))
			senzor = new SenzorTemperature(mjernaJedinica, preciznost, vrijednost, radSenzora, postaja);
		if(vrstaSenzoraCombobox.getValue() == senzoriVrste.get(1))
			senzor = new SenzorVjetra(mjernaJedinica, preciznost, vrijednost, radSenzora, postaja);
		if(vrstaSenzoraCombobox.getValue() == senzoriVrste.get(2))
			senzor = new SenzorVlage(mjernaJedinica, preciznost, vrijednost, radSenzora, postaja);

		try {
			BazaPodataka.spremiSenzor(senzor);
		} catch (SQLException | IOException e) {
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}
		
		
	     Alert alert = new Alert(AlertType.INFORMATION);

	     alert.setTitle("Uspješno spremanje Senzora!");
	     alert.setHeaderText("Uspješno spremanje Senzora!");
	     alert.setContentText("Uneseni podaci za senzor su uspješno spremljeni.");
	     alert.showAndWait();

		 Stage stage = (Stage) spremiButton.getScene().getWindow();

	     stage.close();

	     SenzoriController.dodajNoviSenzor(senzor);

	}
	
}
