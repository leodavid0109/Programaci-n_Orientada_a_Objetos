import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaPrincipal {

	private Stage window;
	
	public VentanaPrincipal(Stage window) {
		this.window = window;
	}
	
	public void mostrar() {
		VBox root = new VBox();
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Ventanas");
		
		MenuItem menuVentana1 = new MenuItem("Ventana 1");
		menuVentana1.setOnAction(e -> abrirVentana(new Ventana1(this)));
		
		MenuItem menuVentana2 = new MenuItem("Ventana 2");
		menuVentana2.setOnAction(e -> abrirVentana(new Ventana2(this)));
		
		menu.getItems().addAll(menuVentana1, menuVentana2);
		menuBar.getMenus().add(menu);
		
		root.getChildren().add(menuBar);
		
		Scene scene = new Scene(root, 300, 200);
		window.setScene(scene);
		window.setTitle("Ventana Principal");
		window.show();
	}
	
	private void abrirVentana(Ventana ventana) {
		ventana.mostrar(window);
	}
	
	public void mostrarVentanaPrincipal() {
		window.show();
	}
}
