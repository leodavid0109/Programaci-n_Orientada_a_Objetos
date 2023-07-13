public class Persona {
	
	private String nombre;
	private Persona mejorAmigo;
	
	public Persona(String nombre) {
		this.nombre = nombre;
	}
	
	public void finalize() {
		System.out.println("Matando a; " + nombre);
		
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Persona getMejorAmigo() {
		return mejorAmigo;
	}
	
	void setMejorAmigo(Persona mejorAmigo) {
		this.mejorAmigo = mejorAmigo;
	}
	
	public String toString() {
		
		return "Soy "+nombre;
		
	}
}
