package vehiculos;



public class CasoPrueba{
	
	public static void main(String args[]) {
		Fabricante f1 = new Fabricante("FORD", new Pais("Colombia"));
		Automovil a1 = new Automovil("x", "y", 2, 5, f1, 7);
		Automovil a2 = new Automovil("x", "y", 2, 5, f1, 7);
		Automovil a3 = new Automovil("x", "y", 2, 5, f1, 7);
		Camion c1 = new Camion("x", "y", 5, 6, f1, 4);
		Camioneta d1 = new Camioneta("x", 5, "y", 6, 8, f1, true);
		Camioneta d2 = new Camioneta("x", 5, "y", 6, 8, f1, true);
		
		System.out.println(Vehiculo.vehiculosPorTipo());
}
}
