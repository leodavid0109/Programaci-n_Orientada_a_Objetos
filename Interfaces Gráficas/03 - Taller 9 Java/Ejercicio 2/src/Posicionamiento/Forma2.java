package Posicionamiento;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Forma2 extends Application{
	public static void main(String args[]) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception{
		window.setTitle("Design preview");
		
		//Creación botones
		Button boton1 = new Button("Hilos Start");
		boton1.setMinSize(100, 50);
		Button boton2 = new Button("Hilos Stop");
		boton2.setMinSize(100, 50);
		
		VBox root = new VBox(boton1, boton2);
		root.setSpacing(15);
		root.setAlignment(Pos.CENTER);
		
		//Creación de la scene
		Scene scene = new Scene(root, 250, 200);
		window.setScene(scene);
				
		window.show();
	}
}
