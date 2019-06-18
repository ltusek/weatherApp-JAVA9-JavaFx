package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Senzori<T extends Senzor> extends BazniEntitet{
	public Senzori(int id) {
		super(id);
	}
	
	public Senzori() {
		super(0);
	}

	private List<T> listaSenzora = new ArrayList<>();
	
	public Senzor get(Integer i) {
		return listaSenzora.get(i);
	}
	
	public void add(T x) {
		listaSenzora.add(x);
	}
	
	public List<T> getSortedList() {
		Collections.sort(listaSenzora, (p1, p2) -> ((Senzor) p1).getMjernaJedinica().compareTo(((Senzor) p2).getMjernaJedinica()));
		return listaSenzora;
	}
	
	public Optional<T> getSenzor(int id){
		return listaSenzora.stream().filter(senz -> senz.getId()==id).findAny();
	}
}

