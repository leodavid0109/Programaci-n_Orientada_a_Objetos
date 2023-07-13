package gestorAplicación.interno;

import java.util.ArrayList;
import java.util.Date;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import gestorAplicación.externo.Estado;
import gestorAplicación.externo.Tablas;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.Instant;

public abstract class Cuenta implements Serializable, Comparable<Cuenta>{
	//Atributos
	private static final long serialVersionUID = 4L;
	public static final String nombreD = "Cuentas";
	private Usuario titular;
	private int clave;
	protected Divisas divisa;
	protected String nombre;
	protected int id;
	protected Banco banco;
	private static transient ArrayList<Cuenta> cuentasTotales = new ArrayList<Cuenta>();

	
	//Constructores
	protected Cuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		this.clave = clave;
		this.divisa = divisa;
		this.nombre = nombre;
		this.banco = banco;	
		cuentasTotales.add(this);
		this.setId(cuentasTotales.size());
	}	
	
	protected Cuenta(Banco banco, int clave, String nombre) {;
		this.clave = clave;
		//Acceder a la divisa definida como predeterminada por el banco
		this.divisa = banco.getEstadoAsociado().getDivisa();
		this.nombre = nombre;
		this.banco = banco;
		cuentasTotales.add(this);
		this.setId(cuentasTotales.size());
	}
	
	protected Cuenta() {
		cuentasTotales.add(this);
		this.setId(cuentasTotales.size());
	}
	
	//Métodos
	public abstract Cuenta crearCuenta(Banco banco, int clave, Divisas divisa, String nombre);
	
	public abstract Cuenta crearCuenta(Banco banco, int clave, String nombre);
	
	// Funcionalidad Asesor de Inversiones
	public static Cuenta gotaGota(double cantidadPrestamo, Usuario user, Ahorros gota) {

		double mayor = 0;
		int contador = 0;
		if (user.getCuentasAhorrosAsociadas().size() != 0) {
			for (int i = 0; i < user.getCuentasAhorrosAsociadas().size(); i++) {
				if (user.getCuentasAhorrosAsociadas().get(i).getSaldo() > mayor) {
					mayor = user.getCuentasAhorrosAsociadas().get(i).getSaldo();
					contador = i;
				}
				Movimientos movimiento = new Movimientos(gota, user.getCuentasAhorrosAsociadas().get(contador),
						cantidadPrestamo, Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento);
			}
			return user.getCuentasAhorrosAsociadas().get(contador);
		} else {
			for (int i = 0; i < user.getCuentasCorrienteAsociadas().size(); i++) {
				if (user.getCuentasCorrienteAsociadas().get(i).getCupo() > mayor) {
					mayor = user.getCuentasCorrienteAsociadas().get(i).getCupo();
					contador = i;
				}
				Movimientos movimiento = new Movimientos(gota, user.getCuentasCorrienteAsociadas().get(contador),
						cantidadPrestamo, Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento);
			}
			return user.getCuentasCorrienteAsociadas().get(contador);
		}
	}
	
	// Métodos para funcionlidad de cambio de divisa
	public static void hacerCambio(Movimientos escogencia, double monto, Ahorros destino, Usuario user) {
		Ahorros origen = (Ahorros) escogencia.getOrigen(); 
		double cambiado = monto*(1-escogencia.getBanco().getEstadoAsociado().getTasa_impuestos());
		cambiado = cambiado*(1-escogencia.getCoutaManejo());
		cambiado = cambiado*(escogencia.getCantidad());
		Movimientos m = new Movimientos(escogencia.getBanco(), origen, destino, escogencia.getDivisa(), escogencia.getDivisaAux(), escogencia.getCoutaManejo() , monto, Date.from(Instant.now()));
		user.asociarMovimiento(m);
		origen.setSaldo(origen.getSaldo()-monto);
		destino.setSaldo(destino.getSaldo()+cambiado);
		for (int i = 0; i < user.getBancosAsociados().size() ; i++) {
			user.getBancosAsociados().get(i).setAsociado(false);
		}
	}
	
	public static void hacerCambio(Movimientos escogencia, double monto, Ahorros destino, boolean exacto, Usuario user) {
		Ahorros origen = (Ahorros) escogencia.getOrigen(); 
		double pagar = monto/(1-escogencia.getBanco().getEstadoAsociado().getTasa_impuestos());
		pagar = pagar/(1-escogencia.getCoutaManejo());
		pagar = pagar/(escogencia.getCantidad());
		Movimientos m = new Movimientos(escogencia.getBanco(), origen, destino, escogencia.getDivisa(), escogencia.getDivisaAux(), escogencia.getCoutaManejo() , pagar, Date.from(Instant.now()));
		user.asociarMovimiento(m);
		origen.setSaldo(origen.getSaldo()-pagar);
		destino.setSaldo(destino.getSaldo()+monto);
		for (int i = 0; i < user.getBancosAsociados().size() ; i++) {
			user.getBancosAsociados().get(i).setAsociado(false);
		}
	}
	
	public static ArrayList<Ahorros> obtenerCuentasDivisa(Usuario usuario, Divisas divisa){
		ArrayList<Ahorros> cuentasB = new ArrayList<Ahorros>();
		for (Ahorros ahorro: usuario.getCuentasAhorrosAsociadas()) {
			if (ahorro.getDivisa().equals(divisa)) {
				cuentasB.add(ahorro);
			}
		}
		return cuentasB;
	}
	
	public static boolean comprobarSaldo(Cuenta cuenta, double monto) {
		Ahorros ahorro = (Ahorros) cuenta;
		if (ahorro.getSaldo() >= monto) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean comprobarSaldo(Movimientos escogencia, double monto ) {
		double pagar = monto/(1-escogencia.getBanco().getEstadoAsociado().getTasa_impuestos());
		pagar = pagar/(1-escogencia.getCoutaManejo());
		pagar = pagar/(escogencia.getCantidad());
		Ahorros ahorro = (Ahorros) escogencia.getOrigen();
		if (ahorro.getSaldo() >= monto) {
			return true;
		}
		else {
			return false;
		}
	}
		
//	public static ArrayList<Cuenta> cuentasConSaldo(ArrayList<Cuenta> cuentasDisponibes, double monto){
//		ArrayList<Cuenta> cuentasConSaldo = new ArrayList<Cuenta>(); 
//		for (Cuenta cuenta : ArrayList<Cuenta>; double monto) {
//			if (cuenta.getSaldo >= monto) {
//				cuentasConSaldo.add(cuenta);
//			}
//		}
//	}
	// Implementación métodos abstracto a redefinir
	public abstract void vaciarCuenta(Ahorros gota);
	
	public static double redondeoDecimal(double numero, int decimales) {
		double numRedondeado = Math.round(numero * Math.pow(10.0, decimales)) / Math.pow(10.0, decimales);
		return numRedondeado;
	}
	
	//Implementación de la interfaz Comparable
	public int compareTo(Cuenta cuenta) {
		if (this.getId() > cuenta.getId()) {
			return 1;
		}
		else if(this.getId() < cuenta.getId()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	//Para ordenar cualquier arreglo de tipo Cuenta, se ordenará según el id de la cuenta y se hará con Collections.sort(nombre_lista);

	//	Ligadura dinamica
	public Object invertirSaldo(){
		float probabilidad = this.getTitular().getSuscripcion().getProbabilidad_Inversion();
		double rand = (double)((Math.random()) + probabilidad);
		if(this instanceof Ahorros){
			if(rand < 1){
				Ahorros cuenta = (Ahorros) this;
				return(Movimientos.crearMovimiento(cuenta,-(cuenta.getSaldo() - cuenta.getSaldo()*0.25),Categoria.FINANZAS,new Date())+"" +
						"\nLastimosamente debido a una mala inversion usted ha pedido un 25% de su saldo");
			}
		}
		return ("Esta cuenta no puede hacer inversiones");

	}

	public double[] retornoCuotaMensual(double DeudaActual) {
		Banco banco = this.getBanco();
		double[] cuotaMensual = new double[3];
		if(banco.getComision()+ banco.getEstadoAsociado().getInteres_bancario_corriente() <1){
//			Cuota del estado y del banco
			double cuota1 = DeudaActual*banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()*DeudaActual;
			double cuota2 = (DeudaActual-cuota1)/2;
			cuotaMensual[0] = DeudaActual*banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()*DeudaActual;
			cuotaMensual[1] = cuota2;
			cuotaMensual[1] = cuota2;
		}else{
			cuotaMensual[0] = DeudaActual/3;
			cuotaMensual[1] = DeudaActual/3;
			cuotaMensual[2] = DeudaActual/3;
		}
		return cuotaMensual;
	}

	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Cuenta) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo){
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("cuentasTotales");
	}
	
	public static double DineroaTenerDisponible(Cuenta cuenta, Divisas divisaB) {
		//Tienes una cuenta de la que extraes su deuda (con el cupo y el disponible)
		//De la misma cuenta extraes la divisa en la que esta ese valor
		
		//Recibes por parámetro la divisa a la cual quiero pasar ese dinero
		
		//Haces tus cálculos y me devuelves cual es el valor que debo checar este disponible en la otra cuenta
		//El double debe resultar en la divisa que recibe el parámetro
		//Tu veras si cobras comisiones o no o si lo quieres traspasar a otro código
		Corriente corriente = (Corriente) cuenta;
		double deuda = (corriente.getCupo() - corriente.getDisponible());
		Movimientos cambioDiv = new Movimientos(cuenta.getDivisa() , divisaB, cuenta.getTitular());
		ArrayList<Banco> existeCambio = Movimientos.facilitarInformación(cambioDiv);
		if (existeCambio.size() == 0) {
			return 0;
		}
		ArrayList<Cuenta> cuentasPosibles = new ArrayList<Cuenta>();
		for (Cuenta conta : cuenta.getTitular().getCuentasCorrienteAsociadas()) {
			if (conta.getDivisa().equals(cuenta.getDivisa())) {
				cuentasPosibles.add(conta);
			}
		}
		String cadena = cuenta.getDivisa().name()+ divisaB.name();
		ArrayList<Movimientos> imprimir = Banco.cotizarTazaAux(cuenta.getTitular(), existeCambio, cadena, cuentasPosibles);
		double cambioMax = 0;
		double valor = 999999999;
		for (Movimientos cotizacion : imprimir) {
			valor = cotizacion.getCantidad()/((1-cotizacion.getCoutaManejo())*(1-cotizacion.getBanco().getEstadoAsociado().getTasa_impuestos()));
			if (valor > cambioMax) {
				cambioMax = valor;
			}
		}
		return cambioMax*deuda;
	}
	
	
	@Override	
	protected void finalize() {
		System.out.println("La cuenta " + this.getClass().getSimpleName() + " con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
	}

	public String toString() {
		return "Cuenta: " + this.nombre 
				+ "\n# " + this.id 
				+ "\nTitular: " + this.getTitular().getNombre() 
				+ "\nBanco: " + this.banco.getNombre() 
				+ "\nDivisa: " + this.divisa;
	}
	
	//Gets && Sets
	public static ArrayList<Cuenta> getCuentasTotales(){
		return Cuenta.cuentasTotales;
	}
	
	public static void setCuentasTotales(ArrayList<Cuenta> cuentasTotales){
		Cuenta.cuentasTotales = cuentasTotales;
	}
	
	public Usuario getTitular() {
		return titular;
	}
	
	public void setTitular(Usuario titular) {
		this.titular = titular;
	}
	
	public Divisas getDivisa() {
		return divisa;
	}
	
	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Banco getBanco() {
		return banco;
	}
	
	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public int getClave() {
		return clave;
	}
	
	public void setClave(int clave) {
		this.clave = clave;
	}
}
