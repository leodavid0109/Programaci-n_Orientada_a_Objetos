import javafx.application.Application;
import javafx.stage.Stage;

public class Transición extends Application{
	
	@Override
	public void start(Stage window) {
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(window);
		ventanaPrincipal.mostrar();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
