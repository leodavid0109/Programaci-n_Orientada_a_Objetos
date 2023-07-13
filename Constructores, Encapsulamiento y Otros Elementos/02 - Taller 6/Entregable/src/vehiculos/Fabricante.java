package vehiculos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fabricante {
	private String nombre;
	private Pais pais;
	static ArrayList<Fabricante> fabricantes_lista = new ArrayList<Fabricante>();
	static Map<Fabricante, Integer> fabricantes = new HashMap<>();
	
	public Fabricante(String nombre, Pais pais) {
		this.nombre = nombre;
		this.pais = pais;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public static Fabricante fabricaMayorVentas() {
		int mayor = 0;
		Fabricante fabricante = null;
		for(Map.Entry<Fabricante, Integer> entry: fabricantes.entrySet()) {
			if (entry.getValue() >  mayor) {
				mayor = entry.getValue();
				fabricante = entry.getKey();
			}
		}
		return fabricante;
	}
}
