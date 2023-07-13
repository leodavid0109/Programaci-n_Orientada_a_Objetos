public class Sedan implements VehiculoTierra {
	private String nombre;
	private short maxPasajeros;
	private int maxVelocidad;
	private int numeroRuedas;
	
	public Sedan() {
		this("Mi Sedan", (short) 4, 200);
	}
	
	public Sedan(String nombre, short maxPasajeros, int maxVelocidad) {
		this.nombre = nombre;
		this.maxPasajeros = maxPasajeros;
		this.maxVelocidad = maxVelocidad;
		this.numeroRuedas = 0;
	}
	
	public void agregarRuedas(int numeroRuedas) {
		this.numeroRuedas = numeroRuedas;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getMaxPasajeros() {
		return this.maxPasajeros;
	}
	
	public int getMaxVelocidad() {
		return this.maxVelocidad;
	}
	
	public int getNumeroRuedas() {
		return this.numeroRuedas;
	}
	
	public String conducir() {
		return "Conduce al vehículo: " + nombre;
	}
	
	String sonarClaxon() {
		return "Claxon sonando...";
	}
	
	public String toString() {
		return super.toString();
	}
}
