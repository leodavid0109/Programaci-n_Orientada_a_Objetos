package paquete2;

import paquete1.Auto;

final class Moto extends Auto {
	private String placa;
	private String modelo;
	public int velocidad = 30;
	
	public Moto(String placa, String modelo) {
		this.placa = placa;
		this.modelo = modelo;
		this.pitar();
		//Moto.pitar();
	}
	
	public void acelerar() {
		System.out.println("Avanzo muy rapido");
	}
	
	//public static void pitar() {
		//System.out.println("Las motos no pitan");
	//}
	
	//public void arrancar() {
		
		//System.out.println("Arrancando");
		
	//}
	
	//public String getPlaca() {
		//return placa;
	//}
	
	//public String getModelo() {
		//return modelo;
	//}
	
	//public String toString() {
		//return "Placa: " + placa + ", modelo: " + modelo;
	//}
	
}