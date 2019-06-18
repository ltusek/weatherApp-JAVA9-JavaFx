package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.MjernaPostaja;
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

public class MjernePostajeController {
	
	private List<MjernaPostaja> listaMjernihPostaja;
	public static ObservableList<MjernaPostaja> observableListaPostaja = FXCollections.observableArrayList();
	
	@FXML
	private TextField postajeFilterTextField;
	
	@FXML
	private TableView<MjernaPostaja> postajeTableView;
	
	@FXML
	private TableColumn<MjernaPostaja, String> nazivColumn;
	
	@FXML
	private TableColumn<MjernaPostaja, String> mjestoColumn;
	
	@FXML
	private TableColumn<MjernaPostaja, String> geoTockaColumn;
	
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<MjernaPostaja, String>("naziv"));
		mjestoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getMjesto().getNaziv());
			}});
		geoTockaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getGeografskaTocka().getX().toString() + " " + param.getValue().getGeografskaTocka().getY().toString());
			}});
		
		try {
			listaMjernihPostaja = BazaPodataka.dohvatiMjernePostaje();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		observableListaPostaja.addAll(listaMjernihPostaja);
	}
	
	public void prikaziPostaje() {
		List<MjernaPostaja> filtriranePostaje = new ArrayList<>();
		if (postajeFilterTextField.getText().isEmpty() == false) {
			filtriranePostaje = listaMjernihPostaja.stream().filter(m -> m.getNaziv().contains(postajeFilterTextField.getText()) || m.getMjesto().getNaziv().contains(postajeFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriranePostaje = listaMjernihPostaja;
		}
		observableListaPostaja = FXCollections.observableArrayList(filtriranePostaje);
		
		postajeTableView.setItems(observableListaPostaja);
	}

	public static void dodajNovuPostaju(MjernaPostaja postaja) {
		observableListaPostaja.add(postaja);
	}
}
