import javafx.stage.Stage;

public abstract class Ventana {

	public abstract void mostrar(Stage window);
	
	protected void cerrarVentana(Stage ventana) {
		ventana.close();
	}
}
