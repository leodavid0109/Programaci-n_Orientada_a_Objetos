import java.io.IOException;

public class ObjTaller11B {

	public static void main(String[] args) {
		try {
			new Gestor().gestionar();
		} catch(IOException ex) {
			try {
				new Creador().gestionar();
			} catch(IOException ex1) {
				System.out.println("Excepcion tipo IO");
				System.out.println(ex.getMessage());
			}
		}
	}
}
