package hr.java.vjezbe.javafx;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Senzori;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static final int BROJ_RADIO_MJERNIH_POSTAJA = 0;
	public static final int BROJ_MJERNIH_POSTAJA = 3;
	
	public static Map<Integer, Senzori<Senzor>> senzoriFromFile = new HashMap<>();
	public static List<Senzori<Senzor>> senzoriFile = new ArrayList<>();
	
	private static BorderPane root;
	private Stage primaryStage;
	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void setCenterPane(BorderPane centerPane) {
		root.setCenter(centerPane);
		}
	
	public static void main(String[] args) {
		launch(args);
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
	
	
	public static List<String> dohvatiVrsteSenzora(){
		List<String> senzoriVrste = new ArrayList<>();
		senzoriVrste.add("Senzor temperature");
		senzoriVrste.add("Senzor brzine vjetra");
		senzoriVrste.add("Senzor vlage");
		return senzoriVrste;
	}

}
