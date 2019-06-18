package hr.java.vjezbe.javafx;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DrzaveController {

	private List<Drzava> listaDrzavi;
	public static ObservableList<Drzava> observableListaDrzava = FXCollections.observableArrayList();
	
	public static Drzava drzava = null;
	
	@FXML
	private TextField drzaveFilterTextField;
	
	@FXML
	private TableView<Drzava> drzaveTableView;
	
	@FXML
	private TableColumn<Drzava, String> nazivColumn;
	
	@FXML
	private TableColumn<Drzava, String> povrsinaColumn;
	

	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
		povrsinaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Drzava, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Drzava, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getPovrsina().toString());
			}});
		
		try {
			listaDrzavi = BazaPodataka.dohvatiDrzave();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		observableListaDrzava.addAll(listaDrzavi);
		
		drzaveTableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1) {
					Drzava drz = null;
					drz = drzaveTableView.getSelectionModel().getSelectedItem();
					drzava = drz;
					setCellValueFromTable();
				}
			}
		});
		
		
	}
	
	public void prikaziDrzave() {
		List<Drzava> filtriraneDrzave = new ArrayList<>();
		if (drzaveFilterTextField.getText().isEmpty() == false) {
			filtriraneDrzave = listaDrzavi.stream().filter(m -> m.getNaziv().contains(drzaveFilterTextField.getText()) || m.getPovrsina().toString().contains(drzaveFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraneDrzave = listaDrzavi;
		}
		observableListaDrzava = FXCollections.observableArrayList(filtriraneDrzave);
		
		drzaveTableView.setItems(observableListaDrzava);
	}
	
	public static void dodajNovuDrzavu(Drzava novadrzava) {
		observableListaDrzava.add(novadrzava);
     }
	
	private void setCellValueFromTable() {

		try {
			URI uriPocetni = new File("src/hr/java/vjezbe/javafx/Dodajdrzavu.fxml").toURI();
			BorderPane novoMjestoPane = FXMLLoader.load(uriPocetni.toURL());
			Scene scene = new Scene(novoMjestoPane,600,400);
			URI uriCss = new File("src/hr/java/vjezbe/javafx/application.css").toURI();
			scene.getStylesheets().add(uriCss.toURL().toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
	
}
