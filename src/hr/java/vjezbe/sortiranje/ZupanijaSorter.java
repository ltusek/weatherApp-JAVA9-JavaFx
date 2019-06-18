package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Zupanija;

public class ZupanijaSorter implements Comparator<Zupanija> {

	@Override
	public int compare(Zupanija zup1, Zupanija zup2) {
		if(zup1.getNaziv().compareTo(zup2.getNaziv()) > 0)
			return 1;
		else if(zup1.getNaziv().compareTo(zup2.getNaziv()) < 0)
			return -1;
		else
			return 0;
			
			
	}
}

/*
the value 0 if the argument string is equal to this string; a value less than 0 if this string is lexicographically less than the string argument; 
and a value greater than 0 if this string is lexicographically greater than the string argument.
*/