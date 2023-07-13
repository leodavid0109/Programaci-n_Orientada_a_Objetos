public class Vehiculo {
	
	private String placa;
	private Persona dueno;
	private Motor motor;
	
	public Vehiculo(String placa, Motor motor, Persona persona) {
		this.dueno = persona;
		this.placa = placa;
		this.setMotor(motor);
	}
	
	public Vehiculo(String placa, Motor motor) {
		this.placa = placa;
		this.setMotor(motor);
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public Persona getDueno() {
		return dueno;
	}
	
	void setDueno(Persona persona) {
		this.dueno = persona;
	}
	
	public Motor getMotor() {
		return motor;
	}
	
	public void setMotor(Motor motor) {
		this.motor = motor;
	}
	
	public String toString() {
		return "Placa: " + placa + ", Dueño: " + dueno;
	}
	
}