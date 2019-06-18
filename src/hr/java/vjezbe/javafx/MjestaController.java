package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Mjesto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class MjestaController {
	private List<Mjesto> listaMjesta;
	
	public static ObservableList<Mjesto> observableListaMjesta = FXCollections.observableArrayList();
	
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
		observableListaMjesta.addAll(listaMjesta);
	}
	
	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<>();
		
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().contains(mjestaFilterTextField.getText()) || m.getVrstaMjesta().toString().contains(mjestaFilterTextField.getText()) || m.getZupanija().getNaziv().contains(mjestaFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		//ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		
		observableListaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		
		mjestaTableView.setItems(observableListaMjesta);
	}
	
	public static void dodajNovoMjesto(Mjesto novoMjesto) {
        observableListaMjesta.add(novoMjesto);
     }
}
