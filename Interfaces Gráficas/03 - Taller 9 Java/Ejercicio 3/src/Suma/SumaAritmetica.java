package Suma;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SumaAritmetica extends Application{
	public static void main(String args[]) {
		launch(args);
	}
	
	//Creación atributos
	Button botonSuma;
	TextField Op1S;
	TextField Op2S;
	TextField ResS;
	
	public class ManejoEventos implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Object control = e.getSource();
			if (control instanceof Button) {
				if (control.equals(botonSuma)) {
					int Sum1 = Integer.parseInt(Op1S.getText());
					int Sum2 = Integer.parseInt(Op2S.getText());
					int resultado = Sum1 + Sum2;
					ResS.setText("" + resultado);
				}
			}
		}
	}
	
	public void start(Stage window) throws Exception{
		window.setTitle("Suma Aritmética Básica");
		
		//Creación de los botones
		botonSuma = new Button("Sumar");
		botonSuma.setAlignment(Pos.CENTER);
		
		//Creación enunciados
		Label Op1 = new Label("Operando 1");
		Label Op2 = new Label("Operando 2");
		Label Res = new Label("Resultado");
		
		//Creación Espacios Entrada
		Op1S = new TextField();
		Op2S = new TextField();
		ResS = new TextField();
		
		GridPane root = new GridPane();
		
		root.setAlignment(Pos.CENTER);
		root.setHgap(15);
		root.setVgap(15);
		
		root.add(Op1, 0, 0);
		root.add(Op1S, 1, 0);
		root.add(Op2, 0, 1);
		root.add(Op2S, 1, 1);
		root.add(botonSuma, 1, 2);
		root.add(Res, 0, 3);
		root.add(ResS, 1, 3);
		
		ManejoEventos evento = new ManejoEventos();
		botonSuma.setOnAction(evento);
		
		//Creación de la scene
		Scene scene = new Scene(root, 400, 300);
		window.setScene(scene);
		
		window.show();
	}
}
