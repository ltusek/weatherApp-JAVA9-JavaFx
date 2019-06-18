package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Senzor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SenzoriController {
	
	private List<Senzor> listaSenzora;
	public static ObservableList<Senzor> observableListaSenzora = FXCollections.observableArrayList();
	
	@FXML
	private TextField senzoriFilterTextField;
	
	@FXML
	private TableView<Senzor> senzoriTableView;
	
	@FXML
	private TableColumn<Senzor, String> mjernaJedinicaColumn;
	
	@FXML
	private TableColumn<Senzor, String> preciznostColumn;
	
	@FXML
	private TableColumn<Senzor, String> vrijednostColumn;
	
	@FXML
	private TableColumn<Senzor, String> radSenzoraColumn;
	
	@FXML
	private TableColumn<Senzor, String> mjernaPostajaColumn;

	
	
	
	@FXML
	public void initialize() {
		mjernaJedinicaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getMjernaJedinica());
			}});
		preciznostColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().getPreciznost()) + "");
			}});
		vrijednostColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getVrijednost().toString() + "");
			}});
		radSenzoraColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getRadSenzora().toString() + "");
			}});
		mjernaPostajaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getMjernaPostaja() + "");
			}});
		
		try {
			listaSenzora = BazaPodataka.dohvatiSenzore();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		observableListaSenzora.addAll(listaSenzora);
		
	}
	
	public void prikaziSenzore() {
		List<Senzor> filtriraniSenzori = new ArrayList<>();
		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(m -> m.getMjernaJedinica().contains(senzoriFilterTextField.getText()) || m.getMjernaPostaja().getNaziv().contains(senzoriFilterTextField.getText()) || m.getRadSenzora().toString().contains(senzoriFilterTextField.getText()) || m.getVrijednost().toString().contains(senzoriFilterTextField.getText()) || String.valueOf(m.getPreciznost()).contains(senzoriFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}
		observableListaSenzora = FXCollections.observableArrayList(filtriraniSenzori);
		
		senzoriTableView.setItems(observableListaSenzora);
	}
	
	public static void dodajNoviSenzor(Senzor senzor) {
		observableListaSenzora.add(senzor);
	}
}
