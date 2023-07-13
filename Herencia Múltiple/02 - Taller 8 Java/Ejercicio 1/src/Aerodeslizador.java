public class Aerodeslizador implements VehiculoTierra, VehiculoAgua {
	private int numeroRuedas;
	private String nombre;
	private short maxPasajeros;
	private int maxVelocidad;
	
	public Aerodeslizador() {
		this("Mi aerodeslizador", (short) 2, 80);
	}
	
	public Aerodeslizador(String nombre, short maxPasajeros, int maxVelocidad) {
		this.nombre = nombre;
		this.maxPasajeros = maxPasajeros;
		this.maxVelocidad = maxVelocidad;
		this.numeroRuedas = 0;
	}
	
	public int getNumeroRuedas() {
		return this.numeroRuedas;
	}
	
	public void agregarRuedas(int numeroRuedas) {
		this.numeroRuedas = numeroRuedas;
	}
	
	public String conducir() {
		return "Conduce al vehículo: " + nombre;
	}
	public String soltarAmarras() {
		return "Soltando amarras...";
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
	
	public String toString() {
		return super.toString();
	}
}
