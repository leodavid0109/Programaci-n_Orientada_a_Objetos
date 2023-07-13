import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Transición extends Application{
	
	private Stage stage1;
	private Stage stage2;
	
	@Override
	public void start(Stage stage1) {
		this.stage1 = stage1;
		
		//Creo label de la scene
		Label lab1 = new Label("Bienvenido esta es la primer Scene!");
		
		//Creo el botón de transición
		Button boton1 = new Button("Ir a scene 2");
		boton1.setOnAction(e -> ventana2());
		
		//Diseño de la ventana 1
		GridPane root = new GridPane();
		root.add(lab1, 0, 0);
		root.add(boton1, 0, 1);
		root.setVgap(20);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 300, 200);
		 
		stage1.setTitle("Titulo");
		stage1.setScene(scene);
		stage1.show();
	}
	
	private void ventana2() {
		//Creo el botón retorno transición
		Button boton2 = new Button("Volvamos a el primer Scene");
		boton2.setOnAction(e -> retornoVentana1());
		
		//Diseño segunda ventana
		GridPane root = new GridPane();
		root.add(boton2, 0, 0);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 300, 200);
		
		this.stage2 = new Stage();
		stage2.setTitle("Titulo");
		stage2.setScene(scene);
		
		//Configuro botón "Cerrar" de la segunda ventana
		stage2.setOnCloseRequest(e -> {
			e.consume();
			retornoVentana1();
		});
		
		stage1.hide();
		//Mostrar ventana 2
		stage2.show();
	}
	
	private void retornoVentana1() {
		stage2.hide();
		stage1.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}