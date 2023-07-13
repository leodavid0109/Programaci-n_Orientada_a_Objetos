package test;

public class Asiento {
	public String color;
	public int precio;
	public int registro;
	
	public void cambiarColor(String color) {
		if (color == "rojo") {
			this.color = "rojo";
		}
		else if (color == "verde") {
			this.color = "verde";
		}
		else if (color == "amarillo") {
			this.color = "amarillo";
		}
		else if (color == "negro") {
			this.color = "negro";
		}
		else if (color == "blanco") {
			this.color = "blanco";
		}
	}
	
}