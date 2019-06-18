package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zupanija;
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

public class ZupanijeController {
	
	private List<Zupanija> listaZupanija;
	
	public static ObservableList<Zupanija> observableListaZupanija = FXCollections.observableArrayList();
	
	@FXML
	private TextField zupanijeFilterTextField;
	
	@FXML
	private TableView<Zupanija> zupanijeTableView;
	
	@FXML
	private TableColumn<Zupanija, String> nazivColumn;
	
	@FXML
	private TableColumn<Zupanija, String> drzColumn;
	
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
		drzColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zupanija, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Zupanija, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getDrzava().getNaziv());
			}});
		
		try {
			listaZupanija = BazaPodataka.dohvatiZupanije();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		observableListaZupanija.addAll(listaZupanija);
	}
	
	public void prikaziZupanije() {
		List<Zupanija> filtriraneZupanije = new ArrayList<>();
		
		if (zupanijeFilterTextField.getText().isEmpty() == false) {
			filtriraneZupanije = listaZupanija.stream().filter(m -> m.getNaziv().contains(zupanijeFilterTextField.getText()) || m.getDrzava().getNaziv().contains(zupanijeFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraneZupanije = listaZupanija;
		}
		
		observableListaZupanija = FXCollections.observableArrayList(filtriraneZupanije);
		
		zupanijeTableView.setItems(observableListaZupanija);
	}
	
	public static void dodajNovuZupaniju(Zupanija novaZupanija) {
		observableListaZupanija.add(novaZupanija);
     }
}
