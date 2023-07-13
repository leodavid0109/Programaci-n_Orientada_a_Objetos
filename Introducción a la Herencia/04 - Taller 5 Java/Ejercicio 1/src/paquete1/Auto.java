package paquete1;

public class Auto {

	protected static int num_autos = 0;
	public int velocidad = 10;
	public String pitar = "Piiiiii";
	//static
	
	public Auto() {
		num_autos++;
	}
	
	//public
	void adelantar() {
		System.out.println("Puedo adelantar autos");
	}
	
	//public static
	protected final void pitar() {
		System.out.println(this.pitar);
		//System.out.println(pitar);
	}
	
	final public void arrancar() {
		System.out.println("Encendido");
	}
	
	public void acelerar() {
		System.out.println("Avanzo constantemente");
	}
	
	public void frenar() {
		System.out.println("Me detengo");
	}
	
	public int getVelocidad() {
		return(this.velocidad);
	}
	
	public static int getNum_autos() {
		return(num_autos);
	}
}