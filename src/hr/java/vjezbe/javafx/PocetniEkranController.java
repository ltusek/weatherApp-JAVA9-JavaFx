package hr.java.vjezbe.javafx;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Mjesto;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PocetniEkranController {
	private List<Mjesto> listaMjesta;
	
	
	@FXML
	private TextField mjestaFilterTextField;
	
	@FXML
	private TableView<Mjesto> mjestaTableView;
	
	@FXML
	private TableColumn<Mjesto, String> nazivColumn;
	
	@FXML
	private TableColumn<Mjesto, String> tipColumn;
	
	@FXML
	private TableColumn<Mjesto, String> zupanijaColumn;
	
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Mjesto, String>("naziv"));
		tipColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getVrstaMjesta().toString());
			}});
		
		zupanijaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getZupanija().getNaziv());
			}});
		
		try {
			listaMjesta = BazaPodataka.dohvatiMjesta();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().contains(mjestaFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		
		mjestaTableView.setItems(listaMjesta);
	}
	
	public void prikaziEkranZupanije() {
		String fxmlFile = "src/hr/java/vjezbe/javafx/Zupanije.fxml";
		prikaziEkran(fxmlFile);
	}

	
	public void prikaziEkranMjesta() {
		String fxmlFile = "src/hr/java/vjezbe/javafx/Mjesta.fxml";
		prikaziEkran(fxmlFile);
	}
	
	public void prikaziEkranDrzavi() {
		String fxmlFile = "src/hr/java/vjezbe/javafx/Drzave.fxml";
		prikaziEkran(fxmlFile);
	}
	
	public void prikaziEkranPostaja() {
		String fxmlFile = "src/hr/java/vjezbe/javafx/MjernePostaje.fxml";
		prikaziEkran(fxmlFile);
	}
	
	public void prikaziEkranSenzora() {
		String fxmlFile = "src/hr/java/vjezbe/javafx/Senzori.fxml";
		prikaziEkran(fxmlFile);
	}
	
	public void prikaziEkran(String fxmlFile) {
		try {
			//BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));
			URI url = new File(fxmlFile).toURI();
			BorderPane ekranPane = FXMLLoader.load(url.toURL());
			Main.setCenterPane(ekranPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovoMjesto() {	   
		prikaziEkranZaNove("DodajMjesto.fxml");
	}
	
	public void prikaziEkranZaNovuZupaniju() {
		prikaziEkranZaNove("DodajZupaniju.fxml");
	}
	
	public void prikaziEkranZaNovuDrzavu() {
		prikaziEkranZaNove("DodajDrzavu.fxml");
	}
	
	public void prikaziEkranZaNovuPostaju() {
		prikaziEkranZaNove("DodajPostaju.fxml");
	}
	
	public void prikaziEkranZaNoviSenzor() {
		prikaziEkranZaNove("DodajSenzor.fxml");
	}
	
	public void prikaziEkranZaNove(String fxmlFile) {
		try {
	        BorderPane zaNovePane = FXMLLoader.load(Main.class.getResource(fxmlFile));
	        Scene scene = new Scene(zaNovePane);
	                  
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	 
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	   } catch (IOException e) {
	        e.printStackTrace();
	   }
	}
	
	
	public static void setUpValidation(final TextField tf, boolean numb) { 
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
            	validate(tf);
            	if(numb)
            		validateNumbers(tf);
            }

        });

        validate(tf);
        if(numb)
    		validateNumbers(tf);
    }
	

    public static void validate(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (tf.getText().trim().length()==0) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            styleClass.removeAll(Collections.singleton("error"));                    
        }
    }
    
    public static void validateNumbers(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (!tf.getText().matches("\\d*\\.?\\d+")) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            styleClass.removeAll(Collections.singleton("error"));                    
        }
    }
		
}
