package hr.java.vjezbe.javafx;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;




import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajDrzavuController {
	
	@FXML
	private TextField nazivTextfield;
	
	@FXML
	private TextField povrsinaTextfield;
	
	
	@FXML
	private Button spremiButton;
	
	public static boolean OPENED = false;
		
	@FXML
	public void initialize() {
		
		PocetniEkranController.setUpValidation(nazivTextfield, false);
        PocetniEkranController.setUpValidation(povrsinaTextfield, true);
		
        if (DrzaveController.drzava != null){
			nazivTextfield.setText(DrzaveController.drzava.getNaziv() + "");
			povrsinaTextfield.setText(DrzaveController.drzava.getPovrsina() + "");
			OPENED = true;
    	}
	}

	
	public void dodajDrzavu() {
		
		String warning = "";
				
		if(nazivTextfield.getText().trim().equals(""))
			warning += "Nije unesen naziv\n";
					
		if(povrsinaTextfield.getText().trim().equals(""))
			warning += "Nije unesena povrsina\n";						
		
		if(nazivTextfield.getText().matches(".*\\d+.*"))
			warning += "Unijeli ste brojeve u nazivu\n";
								
		if(!povrsinaTextfield.getText().matches("\\d*\\.?\\d+"))
			warning += "Unijeli ste nedopustene znakove u polju povrsina\n";
	
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

		BigDecimal povrsina = new BigDecimal(povrsinaTextfield.getText());
		

		Drzava drzava = new Drzava(naziv, povrsina);
		
		drzava.setId(DrzaveController.drzava.getId());
		
		if(!OPENED) {

			try {
				BazaPodataka.spremiDrzavu(drzava);
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
				
			Alert alert = new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Uspješno spremanje države!");
			alert.setHeaderText("Uspješno spremanje države!");
			alert.setContentText("Uneseni podaci za državu su uspješno spremljeni.");
			 
			alert.showAndWait();
			
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			
			stage.close();
			
			DrzaveController.dodajNovuDrzavu(drzava);
		}

		else {
			try {
				BazaPodataka.izmjeniDrzavu(drzava);
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			
			alert.setTitle("Uspješno spremanje države!");
			alert.setHeaderText("Uspješno spremanje države!");
			alert.setContentText("Uneseni podaci za državu su uspješno spremljeni.");
			 
			alert.showAndWait();
			
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			
			stage.close();
		}
		
	}
	
}
