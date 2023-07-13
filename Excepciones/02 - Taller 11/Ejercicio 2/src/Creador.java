import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

class Creador extends Gestor {
	
	void gestionar() throws FileNotFoundException, IOException{
		FileOutputStream fileOut = new FileOutputStream("d:\\\\Users\\\\David\\\\Documents\\\\UNAL\\\\Semestre 2023-1\\\\Programación Orientada a Objetos 2023-01\\\\15 - Excepciones\\\\02 - Taller 11\\\\Ejercicio 2\\\\prueba.txt");
		OutputStreamWriter out = new OutputStreamWriter(fileOut);
		out.write("Hola");
		out.close();
	}
}
