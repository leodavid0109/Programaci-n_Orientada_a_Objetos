public class Persona {
	
	private String nombre;
	protected long cedula;
	
	public Persona(String nombre, long cedula) {
		this.nombre = nombre;
		this.cedula = cedula;
	}
	
	public final int getEdad() {
		return 18;
	}
}
