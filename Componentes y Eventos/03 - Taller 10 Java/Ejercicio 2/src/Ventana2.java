import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ventana2 extends Ventana {

	private VentanaPrincipal ventanaPrincipal;
	
	public Ventana2(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}
	@Override
	public void mostrar(Stage window) {
		Stage ventana = new Stage();
		
		VBox root = new VBox();
		
		Button botonCerrar = new Button("Cerrar");
		botonCerrar.setOnAction(e -> {
			cerrarVentana(ventana);
			ventanaPrincipal.mostrarVentanaPrincipal();
		});
		
		root.getChildren().add(botonCerrar);
		
		Scene scene = new Scene(root, 300, 200);
		ventana.setScene(scene);
		ventana.setTitle("Ventana 2");
		ventana.setOnCloseRequest(e -> ventanaPrincipal.mostrarVentanaPrincipal());
		window.hide();
		ventana.show();
	}
	
	@Override
    protected void cerrarVentana(Stage ventana) {
        ventana.close();
    }
}
