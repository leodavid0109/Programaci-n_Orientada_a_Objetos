package gestorAplicación.externo;

import java.util.ArrayList;

public enum Cuotas {
	C1(1),
	C6(6),
	C12(12),
	C18(18),
	C24(24),
	C36(36),
	C48(48);
	
	//Atributos
	private final int cantidad_Cuotas;
	
	//Constructor
	Cuotas(int cantidad_Cuotas){
		this.cantidad_Cuotas = cantidad_Cuotas;
	}
	
	public int getCantidad_Cuotas() {
		return cantidad_Cuotas;
	}
	
	public static ArrayList<Cuotas> getCuotas(){
		ArrayList<Cuotas> listaCuotas = new ArrayList<Cuotas>();
		listaCuotas.add(C1);
		listaCuotas.add(C6);
		listaCuotas.add(C12);
		listaCuotas.add(C18);
		listaCuotas.add(C24);
		listaCuotas.add(C36);
		listaCuotas.add(C48);
		return listaCuotas;
	}
	
	public String toString() {
		if (cantidad_Cuotas == 1) {
			return cantidad_Cuotas + " cuota";
		}
		else {
			return cantidad_Cuotas + " cuotas";
		}
	}
}
