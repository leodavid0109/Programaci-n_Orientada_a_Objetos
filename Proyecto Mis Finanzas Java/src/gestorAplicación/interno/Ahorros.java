package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;
import java.util.Date;
import java.lang.reflect.Field;
import java.time.Instant;

public class Ahorros extends Cuenta{
	//Atributos
	private static final long serialVersionUID = 7L;
	public static final String nombreD = "Ahorros";
	private static ArrayList<Ahorros> cuentasAhorroTotales = new ArrayList<Ahorros>();;
	private double saldo = 0.0d;

	//Constructores
	public Ahorros(Banco banco, int clave, Divisas divisa, String nombre, Double saldo) {
		super(banco, clave, divisa, nombre);
		this.saldo = saldo;
		Ahorros.getCuentasAhorroTotales().add(this);
	}
	
	public Ahorros(Banco banco, int clave, String nombre, Double saldo) {
		super(banco, clave, nombre);
		this.saldo = saldo;
		Ahorros.getCuentasAhorroTotales().add(this);
	}
	
	public Ahorros(Double saldo) {
		super();
		this.saldo = saldo;
		Ahorros.getCuentasAhorroTotales().add(this);
	}
	
	public Ahorros(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
		Ahorros.getCuentasAhorroTotales().add(this);
	}	
	
	public Ahorros(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
		Ahorros.getCuentasAhorroTotales().add(this);
	}
	
	public Ahorros() {
		super();
		Ahorros.getCuentasAhorroTotales().add(this);
	}
	
	//Métodos
	public Ahorros crearCuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		return (new Ahorros(banco, clave, divisa, nombre));
	}
	
	public Ahorros crearCuenta(Banco banco, int clave, String nombre) {
		return (new Ahorros(banco, clave, nombre));
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public Object invertirSaldo() {
		float probabilidad = this.getTitular().getSuscripcion().getProbabilidad_Inversion();
		double rand = (double)((Math.random()) + probabilidad);
		if(rand >= 1){
			Movimientos m = (Movimientos) Movimientos.crearMovimiento(this, this.getSaldo() + this.getSaldo() * probabilidad, Categoria.FINANZAS, new Date());
			return (m);
		}else {
			return("Su inversion ha fallado, inténtelo de nuevo. Considere subir de nivel para aumentar la probabilidad de tener inversiones exitosas");
		}
	}
	
	// Funcionalidad asesor inversiones
	public void vaciarCuenta(Ahorros gota) {
		Movimientos movimiento = new Movimientos(this, gota, this.getSaldo(), Categoria.OTROS,
				Date.from(Instant.now()));
		this.getTitular().getMovimientosAsociados().add(movimiento);
		Movimientos.getMovimientosTotales().remove(movimiento);
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("cuentasAhorroTotales");
	}
	
	public int CompareTo(Ahorros cuenta) {
		if(this.getSaldo() > cuenta.getSaldo()) {
			return 1;
		}
		else if(this.getSaldo() < cuenta.getSaldo()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public String toString() {
		return "Cuenta: " + super.nombre +
				"\nCuenta de Ahorros # " + this.id +
				"\nBanco: " + this.banco.getNombre() +
				"\nTitular: " + this.getTitular().getNombre() +
				"\nDivisa: " + this.divisa +
				"\nSaldo: " + this.saldo + " " + this.divisa;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public static ArrayList<Ahorros> getCuentasAhorroTotales() {
		return cuentasAhorroTotales;
	}

	public static void setCuentasAhorroTotales(ArrayList<Ahorros> cuentasAhorroTotales) {
		Ahorros.cuentasAhorroTotales = cuentasAhorroTotales;
	}

	//	Funcionalidad Prestamo
	public static ArrayList<?> comprobarPrestamo(ArrayList<?> cuentas){
		ArrayList<Ahorros> cuentasPrestamo = new ArrayList<Ahorros>();
		ArrayList<String> bancos = new ArrayList<String>();

		//Pasamos por todas las cuentas del usuario y comprobamos que el prestamo sea diferente de 0
		for(int i=0;i<cuentas.size();i++){
			Double prestamo = ((Cuenta) cuentas.get(i)).getBanco().getPrestamo();
			if(prestamo>0){
				cuentasPrestamo.add((Ahorros) cuentas.get(i));
			}else{
				bancos.add(((Cuenta) cuentas.get(i)).getBanco().getNombre());
			}
		}

		if(cuentasPrestamo.size()!=0){
			return cuentasPrestamo;
		}else{
			return bancos;
		}
	}



}
