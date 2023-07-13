package test;

public class Motor {
	public int numeroCilindros;
	public String tipo;
	public int registro;
	
	public void cambiarRegistro(int registro) {
		this.registro = registro;
	}
	public void asignarTipo(String tipo) {
		if (tipo == "electrico") {
			this.tipo = tipo;
		}
		else if (tipo == "gasolina") {
			this.tipo = tipo;
		}
	}
}