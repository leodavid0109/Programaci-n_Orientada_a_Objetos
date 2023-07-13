public class Objtaller6 {
	
	public static void main(String[] args) {
		
		Persona p1 = new Persona("Pepe", 213);
		Estudiante e1 = new Estudiante(91, "Sara", 1431);//No existe constructor que reciba estos atributos.
		Profesor pf1 = new Profesor("Calculo", "Roger", 412);
		Persona p2 = new Estudiante(87, "Sofia", "4");//No existe constructor que reciba estos atributos.
		Persona e2 = new Profesor("Bases", "Adrian", 231);
	}
}
