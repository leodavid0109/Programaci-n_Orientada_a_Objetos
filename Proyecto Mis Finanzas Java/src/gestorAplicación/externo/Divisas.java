package gestorAplicación.externo;

import java.util.ArrayList;

public enum Divisas {
	EUR,
	USD,
	COP,;
	
	public static ArrayList<Divisas> getDivisas() {
		ArrayList<Divisas> listaDivisas = new ArrayList<Divisas>();
		listaDivisas.add(EUR);
		listaDivisas.add(USD);
		listaDivisas.add(COP);
		return(listaDivisas); 
	}
}
