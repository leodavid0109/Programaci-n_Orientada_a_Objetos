import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AnalisisEventos extends Application {

	VBox root;
	public void start(Stage window) {
		Label label1 = new Label("Eventos del ratón");
		//Cambio tamaño
		label1.setStyle("-fx-font-size: 20px;");
		label1.setOnMouseClicked(mouseHandler);
		
		root = new VBox(10);
		root.getChildren().add(label1);
		Scene scene = new Scene(root, 300, 250);
		window.setScene(scene);
		window.show();
	}
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			int cantidad_click = mouseEvent.getClickCount();
			Label label = new Label();
			if (cantidad_click == 1) {
				label.setText("Lo clickeo");
				
			} else {
				label.setText("Lo clickeo " + cantidad_click + " veces.");
			}
			label.setTextFill(Color.RED);
			root.getChildren().add(label);
			
		}
	};
	
	public static void main(String[] args) {
		launch(args);
	}
}
