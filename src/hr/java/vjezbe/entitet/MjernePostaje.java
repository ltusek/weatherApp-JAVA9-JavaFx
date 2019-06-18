package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MjernePostaje<T extends MjernaPostaja> {
	
	private List<T> listaMjernihPostaja = new ArrayList<>();
	
	public MjernaPostaja get(Integer i) {
		return listaMjernihPostaja.get(i);
	}
	
	public void add(T obj1) {
		listaMjernihPostaja.add(obj1);
	}
	
	public List<T> getSortedList() {
		Collections.sort(listaMjernihPostaja, (p1, p2) -> p1.getNaziv().compareTo(p2.getNaziv()));
		return listaMjernihPostaja;
	}
}
