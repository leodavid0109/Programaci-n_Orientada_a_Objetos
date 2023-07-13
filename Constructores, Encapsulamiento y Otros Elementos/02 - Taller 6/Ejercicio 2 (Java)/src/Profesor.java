public class Profesor extends Persona{
	
	String materia;
	private int cedula;
	
	public Profesor(String materia, String nombre, int cedula) {
		super(nombre, cedula);
		this.materia = materia;
	}
	
	public Profesor() {
		this("Fisica"); //No existe un constructor del mismo objeto que reciba sólo un parámetro.
		super("Juan", 20123); //super() debe ser la primer línea del constructor.
	}
	
	public int getCedula() {
		return cedula;
	}
	
	public long getCedulaS() {
		return super.cedula;
	}
}
