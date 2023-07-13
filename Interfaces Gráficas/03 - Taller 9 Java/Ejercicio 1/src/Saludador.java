import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.*;

public class Saludador extends Application {
	public static void main(String args[]) {
		launch(args);
	}
	
	public class ManejoEventos implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Object control = e.getSource();
			if (control instanceof Button) {
				if (control.equals(button1)) {
					lab1.setText("Hola " + textField1.getText());
				}
			}
		}
	}
	
	TextField textField1;
	Label lab1;
	Button button1;
	public void start(Stage window) throws Exception{
		window.setTitle("Saludador");
		
		//Creo label Nombre
		lab1 = new Label("Escribe un nombre para saludar");
		//Creo Text Filed para nombre
		textField1 = new TextField();
		//Creo Boton
		button1 = new Button("¡Saludar!");
		
		// Creo el Grid Pane
		GridPane root = new GridPane();
		// Defino el borde del gridpane
		root.setPadding(new Insets(10, 10, 10, 10));
		//Defino espacio vertical entre filas
		root.setVgap(20);
		
		//Defino alineamiento grid
		root.setAlignment(Pos.CENTER);
		GridPane.setHalignment(button1, HPos.CENTER);
		GridPane.setHalignment(lab1, HPos.CENTER);
		
		//Arreglo nodes en el grid
		root.add(lab1, 0, 0);
		root.add(textField1, 0, 1);
		root.add(button1, 0, 2);
		
		ManejoEventos evento = new ManejoEventos();
		button1.setOnAction(evento);
		
		//Creo un objeto scene
		Scene scene = new Scene(root, 225, 200);
		window.setScene(scene);
		
		
		
		window.show();
	}
}
