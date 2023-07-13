package futbol;

public class Portero extends Futbolista {
	public short golesRecibidos;
	public byte dorsal;
	
	public Portero(String nombre, int edad, short golesRecibidos, byte dorsal) {
		super(nombre, edad, "Portero");
		this.golesRecibidos = golesRecibidos;
		this.dorsal = dorsal;
	}
	
	public String toString() {
		return super.toString() + " con el dorsal " + this.dorsal + ". Le han marcado " + this.golesRecibidos;
	}

	public int compareTo(Object o) {
		if (this.golesRecibidos <= ((Portero) o).golesRecibidos) {
			return ((Portero) o).golesRecibidos - this.golesRecibidos;
		}
		else {
			return this.golesRecibidos - ((Portero) o).golesRecibidos;
		}
	}

	public boolean jugarConLasManos() {
		return true;
	}
}
