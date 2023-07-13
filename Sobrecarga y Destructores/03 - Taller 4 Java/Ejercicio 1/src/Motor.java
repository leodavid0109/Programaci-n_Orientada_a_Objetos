public enum Motor {
	
	ELECTRICO(100), GASOLINA(90), DIESEL(80), HIBRIDO(70);
	
	//velocidad maxima de cada tipo de motor
	
	private int velocidadMaxima;
	
	//Añadir un constructor
	private Motor(int s) {
		velocidadMaxima=s;
	}
	
	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}
	
	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}
}