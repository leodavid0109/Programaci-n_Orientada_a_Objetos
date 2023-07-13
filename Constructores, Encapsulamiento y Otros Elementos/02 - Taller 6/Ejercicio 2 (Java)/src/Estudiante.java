public class Estudiante extends Persona{ //No hay constructor vacío en la clase Persona
	
	int codigo;
	
	public void combinarNombre(String n) {
		super.nombre += n; //El atributo nombre de la clase Persona es privado
	}
	
	public final int getEdad() { //No puedo sobrescribir un método con final
		return 20;
	}
}
