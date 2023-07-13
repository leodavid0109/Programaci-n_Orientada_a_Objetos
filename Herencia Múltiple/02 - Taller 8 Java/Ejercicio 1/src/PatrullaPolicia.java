public class PatrullaPolicia extends Sedan implements Emergencia{
	//private int VOLUMEN = Emergencia.VOLUMEN;
	public PatrullaPolicia(String nombre, int maxPasajeros, int maxVelocidad) {
		super(nombre, (short) maxPasajeros, maxVelocidad);
	}

	public String sonarSirena() {
		return "Sonando sirena...";
	}

	public int getVolumen() {
		return VOLUMEN;
	}
	
	//Añadido punto 3.d)
	//public void setVolumen() {
		//Emergencia.VOLUMEN++;
	//}
	
	public String toString() {
		return super.toString();
	}
}
