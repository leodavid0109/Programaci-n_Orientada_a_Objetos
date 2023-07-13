package gestion;

import java.util.ArrayList;
	
public class Zoologico {
	private String nombre;
	private String ubicacion;
	private ArrayList<Zona> zonas = new ArrayList<Zona>();
	
	public Zoologico(String nombre, String ubicacion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}
	public Zoologico() {
		this(null, null);
	}
	
	public int cantidadTotalAnimales() {
		int cantidad1 = 0;
		for (Zona cantidad : zonas) {
			cantidad1 += cantidad.cantidadAnimales();
		}
		return cantidad1;
	}
	
	public void agregarZonas(Zona zona) {
		zonas.add(zona);
	}
	
	public String toString() {
		return nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public ArrayList<Zona> getZona(){
		return zonas;
	}
	public void setZona(ArrayList<Zona> zonas) {
		this.zonas = zonas;
	}
}
