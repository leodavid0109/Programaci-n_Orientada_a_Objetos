package gestorAplicación.externo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Usuario;

public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String nombreD = "Estados";
	private static transient ArrayList<Estado> estadosTotales = new ArrayList<Estado>();
	private String nombre;
	private int id;
	private double tasa_impuestos;
	private Divisas divisa;
	
	//Funcionalidad Compra Cartera
	//Atributo que almacena el interes bancario corriente determinado por cada Estado
	private double interes_bancario_corriente;
	//Atributo que almacena el multiplicador máximo aplicable al interes_bancario_corriente
	private double tasas_usura;
	
	//Constructores
	public Estado(String nombre, double tasa_impuestos, Divisas divisa) {
		this.setNombre(nombre);
		this.setTasa_impuestos(tasa_impuestos);
		this.setDivisa(divisa);
		Estado.getEstadosTotales().add(this);
		this.setId(Estado.getEstadosTotales().size());
	}
	
	public Estado(){
		this("Colombia", 0.2, Divisas.COP);
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("estadosTotales");
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Estado) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	
	public String toString() {
		return this.getNombre() + " Divisa: " + this.getDivisa();
	}
	
	//Gets
	public String getNombre() {
		return nombre;
	}
	
	public double getTasa_impuestos() {
		return tasa_impuestos;
	}

	public Divisas getDivisa() {
		return divisa;
	}
	
	//Sets
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTasa_impuestos(double tasa_impuestos) {
		this.tasa_impuestos = tasa_impuestos;
	}

	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}

	public static ArrayList<Estado> getEstadosTotales() {
		return estadosTotales;
	}
	
	public static void setEstadosTotales(ArrayList<Estado> estadosTotales) {
		Estado.estadosTotales = estadosTotales;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getInteres_bancario_corriente() {
		return interes_bancario_corriente;
	}
	
	public void setInteres_bancario_corriente(double interes_bancario_corriente) {
		this.interes_bancario_corriente = interes_bancario_corriente;
	}
	
	public double getTasas_usura() {
		return tasas_usura;
	}
	
	public void setTasas_usura(double tasas_usura) {
			this.tasas_usura = tasas_usura;
		}
	
}
