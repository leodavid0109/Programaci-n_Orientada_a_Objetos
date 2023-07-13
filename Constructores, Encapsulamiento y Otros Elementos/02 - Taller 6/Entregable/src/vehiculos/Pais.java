package vehiculos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pais {
	private String nombre;
	private static Map<Pais, Integer> paises = new HashMap<>();
	
	public Pais(String nombre) {
		this.setNombre(nombre);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public static Pais paisMasVendedor() {
		Map<Fabricante, Integer> fabricantes = Fabricante.fabricantes;
		ArrayList<Fabricante> fabricantes_lista = Fabricante.fabricantes_lista;
		
		for (Fabricante fabricante : fabricantes_lista) {
			if(paises.containsKey(fabricante.getPais())) {
				paises.put(fabricante.getPais(), paises.get(fabricante.getPais()) + fabricantes.get(fabricante));
			}
			else {
				paises.put(fabricante.getPais(), fabricantes.get(fabricante));
			}
		}
		
		int mayor = 0;
		Pais pais = null;
		for(Map.Entry<Pais, Integer> entry: paises.entrySet()) {
			if (entry.getValue() >  mayor) {
				mayor = entry.getValue();
				pais = entry.getKey();
			}
		}
		return pais;
	}
}