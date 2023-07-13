package Ej1;

public class Familia {
	Animal papa;
	Animal mama;
	Animal hijo;
	
	void imprimir() {
		String genero;
		if(hijo.genero.equals("M"))
			genero = "masculino";
		else
			genero = "femenino";
		System.out.println(papa.nombre + " es el papá, " + mama.nombre + " es la mamá, y " + hijo.nombre + " es el hijo de genero " + genero);
	}
}