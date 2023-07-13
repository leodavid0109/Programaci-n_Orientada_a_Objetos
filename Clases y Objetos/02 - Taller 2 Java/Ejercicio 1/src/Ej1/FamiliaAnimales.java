package Ej1;

public class FamiliaAnimales {
	public static void main(String arg[]) {
		Animal animal1 = new Animal();
		Animal.totalAnimales++;
		Animal animal2 = new Animal();
		Animal.totalAnimales++;
		
		animal1.nombre = "Cebra";
		animal1.genero = "F";
		
		animal2.nombre = "Caballo";
		animal2.peso = 98.0;
		
		System.out.println(animal1.nombre + " se va a casar con " + animal2.nombre);
		animal1.setPareja(animal2);
		animal2.setPareja(animal1);
		
		Familia familia = animal1.procrear("Cebrallo");
		
		if(familia != null) {
			familia.imprimir();
			System.out.println(familia.hijo);
		} else {
			System.out.println("No se pudo formar familia");
		}
		System.out.println("Total de animales: " + Animal.totalAnimales);
		Animal.morir(animal2);
		System.out.println("Nuevo total de animales: " + Animal.totalAnimales);
	}
}