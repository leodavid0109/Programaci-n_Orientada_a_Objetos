package Posicionamiento;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PosicionamientoBotones extends Application{
	public static void main(String args[]) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception{
		window.setTitle("Design Preview");
		
		//Creación de los botones
		Button boton1 = new Button("Hilos Start");
		boton1.setMinSize(100, 50);
		Button boton2 = new Button("Hilos Stop");
		boton2.setMinSize(100, 50);
		
		GridPane root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setVgap(15);
		root.setAlignment(Pos.CENTER);
		
		root.add(boton1, 0, 0);
		root.add(boton2, 0, 1);
		
		//Creación de la scene
		Scene scene = new Scene(root, 250, 200);
		window.setScene(scene);
		
		window.show();
	}
}