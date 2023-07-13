public class Fragata implements VehiculoAgua{
	private String nombre;
	private short maxPasajeros;
	private int maxVelocidad;
	
	public Fragata() {
		this("Mi Fragata", (short) 20, 100);
	}
	
	public Fragata(String nombre, short maxPasajeros, int maxVelocidad) {
		this.nombre = nombre;
		this.maxPasajeros = maxPasajeros;
		this.maxVelocidad = maxVelocidad;
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
