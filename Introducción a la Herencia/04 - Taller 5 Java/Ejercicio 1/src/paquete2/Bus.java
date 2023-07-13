package paquete2;

import paquete1.Auto;

public class Bus extends Auto {
	
	private String placa;
	private int capacidad;
	public int velocidad = 20;
	
	public Bus(String placa, int capacidad) {
		this.placa = placa;
		this.capacidad = capacidad;
	}
	
	public void acelerar() {
		System.out.println("Avanzo lento");
	}
	
	//public String getPlaca() {
		//return placa;
	//}
	
	//public int getCapacidad() {
		//return capacidad;
	//}
	
	//public String toString() {
		//return "Placa: " + placa + ", capacidad: " + capacidad;
	//}
}