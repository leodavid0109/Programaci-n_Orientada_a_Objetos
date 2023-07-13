import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class Gestor {

	void gestionar() throws FileNotFoundException, IOException{
		FileInputStream fileInput = new FileInputStream("d:\\Users\\David\\Documents\\UNAL\\Semestre 2023-1\\Programación Orientada a Objetos 2023-01\\15 - Excepciones\\02 - Taller 11\\Ejercicio 2\\prueba.txt");
		Scanner sc = new Scanner(fileInput);
		System.out.println(sc.nextLine());
		sc.close();
	}
}
