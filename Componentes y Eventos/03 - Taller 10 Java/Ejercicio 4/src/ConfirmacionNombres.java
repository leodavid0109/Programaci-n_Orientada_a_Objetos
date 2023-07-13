import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConfirmacionNombres extends Application {
	
	public void start(Stage window) {
		Label label1 = new Label("Nombre");
		TextField ingreso = new TextField();
		Button boton = new Button("Enviar");
		
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Dialogo de Confirmación");
		
		EventHandler<ActionEvent> evento = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				alerta.setHeaderText(ingreso.getText());
				alerta.show();
			}
		};
		
		boton.setOnAction(evento);
		
		GridPane root = new GridPane();
		root.add(label1, 0, 0);
		root.add(ingreso, 1, 0);
		root.add(boton, 2, 0);
		root.setHgap(15);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 300, 250);
		window.setTitle("Titulo");
		window.setScene(scene);
		window.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
