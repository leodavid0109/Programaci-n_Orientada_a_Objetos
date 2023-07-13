import java.util.*;
public class ObjTaller8 {
	public static void main (String[] args) {
		
		Vehiculo s = new Sedan();
		Vehiculo a = new Aerodeslizador();
		Vehiculo f = new Fragata();
		Vehiculo p = new PatrullaPolicia("Patrulla 001", 5, 200);
		
		((Sedan) s).agregarRuedas(4);
		((Sedan) p).agregarRuedas(4);
		((Aerodeslizador) a).agregarRuedas(2);
		
		//Defina el operador diamante del siguiente arreglo.
		ArrayList<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		
		listaVehiculos.add(s);
		listaVehiculos.add(a);
		listaVehiculos.add(f);
		listaVehiculos.add(p);
		
		for (int i  = 0; i < listaVehiculos.size(); i++) {
			//Obtiene cada elemento en el arreglo y lo almacena
			//en la variable v.
			Vehiculo v = listaVehiculos.get(i);
			
			System.out.println("Nombre = " + v.getNombre());
			System.out.println("Max pasajeros = " + v.getMaxPasajeros());
			System.out.println("Max velocidad = " + v.getMaxVelocidad());
			
			if (v instanceof VehiculoTierra) {
				System.out.println("Numero Ruedas = " + ((VehiculoTierra) v).getNumeroRuedas());
				System.out.println(((VehiculoTierra) v).conducir());
			}
		}
	}
}
