package vehiculos;

public class Vehiculo {
	private String placa;
	private int puertas;
	private int velocidadMaxima;
	private String nombre;
	private int precio;
	private int peso;
	private String traccion;
	private Fabricante fabricante;
	private static int CantidadVehiculos = 0;
	
	public Vehiculo(String placa, int puertas, int velocidadMaxima, String nombre, int precio, int peso, String traccion, Fabricante fabricante) {
		this.setPlaca(placa);
		this.setPuertas(puertas);
		this.setVelocidadMaxima(velocidadMaxima);
		this.setNombre(nombre);
		this.setPrecio(precio);
		this.setPeso(peso);
		this.setTraccion(traccion);
		this.setFabricante(fabricante);
		setCantidadVehiculos(getCantidadVehiculos() + 1);
		
		if(Fabricante.fabricantes.containsKey(fabricante)) {
			Fabricante.fabricantes.put(fabricante, Fabricante.fabricantes.get(fabricante) + 1);
		}
		else {
			Fabricante.fabricantes_lista.add(fabricante);
			Fabricante.fabricantes.put(fabricante, 1);
		}
	}

	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getPuertas() {
		return puertas;
	}
	public void setPuertas(int puertas) {
		this.puertas = puertas;
	}

	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}
	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getTraccion() {
		return traccion;
	}
	public void setTraccion(String traccion) {
		this.traccion = traccion;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	public static int getCantidadVehiculos() {
		return CantidadVehiculos;
	}
	public static void setCantidadVehiculos(int cantidadVehiculos) {
		CantidadVehiculos = cantidadVehiculos;
	}

	public static String vehiculosPorTipo() {
		return "Automoviles: " + Automovil.CantidadAutomoviles + "\nCamionetas: " + Camioneta.CantidadCamionetas + "\nCamiones: " + Camion.CantidadCamiones;
	}
}
