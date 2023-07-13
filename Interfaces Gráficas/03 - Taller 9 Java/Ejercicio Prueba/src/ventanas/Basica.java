package ventanas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Basica extends Application {
	
	public static void main(String args[]){
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws Exception {
		window.setTitle("Ventana");
		
		//Configurar Layout
		FlowPane root = new FlowPane();
		root.setVgap(4);
		root.setHgap(4);
		
		//Crear un text field
		TextField t = new TextField("Campo");
		root.getChildren().add(t);
		
		//Generar Boton
		Button b1 = new Button("Boton enviar");
		root.getChildren().add(b1);
		
		//Agregar el layout y darle una dimension a nuestra ventana
		Scene scene = new Scene(root, 200, 200);
		window.setScene(scene);
		window.show();
	}
}