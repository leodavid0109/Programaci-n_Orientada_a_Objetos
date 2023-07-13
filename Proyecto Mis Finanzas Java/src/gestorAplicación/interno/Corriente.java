package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;
import java.util.Date;
import java.lang.reflect.Field;
import java.time.Instant;

public class Corriente extends Cuenta implements Cloneable{
	//Atributos
	private static final long serialVersionUID = 8L;
	public static final String nombreD = "Corriente";
	private double cupo = 0.0;
	private double disponible;
	private Cuotas plazo_Pago = Cuotas.C1;
	//Tasa efectiva anual
	private double intereses = 28;
	//Atributo que decide pago de interes en primer mes
	private boolean primerMensualidad;
	private static ArrayList<Corriente> cuentasCorrienteTotales = new ArrayList<Corriente>();;
	
	
	//Constructores
	//Hacer chequeo, cupo viene por defecto según suscripción y banco asociado.
	public Corriente(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
		Corriente.getCuentasCorrienteTotales().add(this);
	}	
	
	public Corriente(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
		Corriente.getCuentasCorrienteTotales().add(this);
	}
	
	public Corriente() {
		super();
		Corriente.getCuentasCorrienteTotales().add(this);
	}
	
	//Métodos
	public Corriente crearCuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		return (new Corriente(banco, clave, divisa, nombre));
	}
	
	public Corriente crearCuenta(Banco banco, int clave, String nombre) {
		return (new Corriente(banco, clave, nombre));
	}
	
	public double[] retornoCuotaMensual(double DeudaActual) {
		double[] cuotaMensual = new double[3];
		double interes_nominal_mensual = Corriente.calculoInteresNominalMensual(this.getIntereses());
		double interes = DeudaActual * (interes_nominal_mensual / 100);
		cuotaMensual[0] = interes;
		double abono_capital = (this.getCupo() - this.getDisponible()) / this.getPlazo_Pago().getCantidad_Cuotas();
		cuotaMensual[1] = abono_capital;
		double cuotaMensualFinal = interes + abono_capital;
		cuotaMensual[2] = cuotaMensualFinal;
		return cuotaMensual;
	}
	
	public double[] retornoCuotaMensual(double DeudaActual, int mes) {
		double[] cuotaMensual = new double[3];
		double interes_nominal_mensual = Corriente.calculoInteresNominalMensual(this.getIntereses());
		if(mes == 1) {
			cuotaMensual[0] = 0;
			double abono_capital = (this.getCupo() - this.getDisponible()) / this.getPlazo_Pago().getCantidad_Cuotas();
			cuotaMensual[1] = abono_capital;
			double cuotaMensualFinal = abono_capital;
			cuotaMensual[2] = cuotaMensualFinal;
		}
		else if(mes == 2){
			double abono_capital = (this.getCupo() - this.getDisponible()) / this.getPlazo_Pago().getCantidad_Cuotas();
			double interes_mes1 = (interes_nominal_mensual / 100) * (abono_capital + DeudaActual);
			double interes_mes2 = DeudaActual * (interes_nominal_mensual / 100);
			double interes = interes_mes1 + interes_mes2;
			cuotaMensual[0] = interes;
			cuotaMensual[1] = abono_capital;
			double cuotaMensualFinal = interes + abono_capital;
			cuotaMensual[2] = cuotaMensualFinal;
		}
		else {
			double interes = DeudaActual * (interes_nominal_mensual / 100);
			cuotaMensual[0] = interes;
			double abono_capital = (this.getCupo() - this.getDisponible()) / this.getPlazo_Pago().getCantidad_Cuotas();
			cuotaMensual[1] = abono_capital;
			double cuotaMensualFinal = interes + abono_capital;
			cuotaMensual[2] = cuotaMensualFinal;
		}
		return cuotaMensual;
	}
	
	public static String imprimirCuotaMensual(double[] cuotaMensual) {
		return "Cuota: " + Corriente.redondeoDecimal(cuotaMensual[2], 2) +
				"\nIntereses: " + Corriente.redondeoDecimal(cuotaMensual[0], 2) +
				"\nAbono a capital: " + Corriente.redondeoDecimal(cuotaMensual[1], 2);
	}
	
	public static double calculoInteresNominalMensual(double interesEfectivoAnual) {
		double interes = Math.pow((1 + (interesEfectivoAnual / 100)), (30.0 / 360.0)) - 1;
		double interes_porcentaje = interes * 100;
		double interes_porcentaje_redondeado = Corriente.redondeoDecimal(interes_porcentaje, 2);
		return interes_porcentaje_redondeado;
	}
	
	// Funcionalidad asesor inversiones

	public void vaciarCuenta(Ahorros gota) {
		Movimientos movimiento = new Movimientos(this, gota, this.getDisponible(), Categoria.OTROS,
				Date.from(Instant.now()));
		this.getTitular().getMovimientosAsociados().add(movimiento);
		Movimientos.getMovimientosTotales().remove(movimiento);
	}
	
	//El throws CloneNotSupportedException es usado para evitar un error al clonar la cuenta
	
	//Funcionalidad Compra de Cartera
	public static Corriente vistaPreviaMovimiento(Corriente cuenta, Cuotas plazo, double Deuda_previa, double interes) throws CloneNotSupportedException {
		Corriente cuenta_aux = cuenta.clone();
		cuenta_aux.setDisponible(cuenta.getDisponible() - Deuda_previa);
		cuenta_aux.setIntereses(interes);
		cuenta_aux.setPlazo_Pago(plazo);
		return cuenta_aux;
	}
	
	//Método creado como calculadora de cuotas mensuales para pago de un préstamo
		//El atributo interes hace referencia a la tasa efectiva anual
	public static double[][] calculadoraCuotas(Cuotas cuotas, double deuda, double intereses) {
		int cuotasTotales = cuotas.getCantidad_Cuotas();
		double[][] cuota = new double[cuotasTotales][3];
		double interesMensual = Corriente.calculoInteresNominalMensual(intereses);
		double deudaActual = deuda;
		
		double abono_capital = deuda / cuotasTotales;
		
		for (int i = 0; i < cuotasTotales; i++) {
			double[] cuotaMes = new double[3];
			double interes = deudaActual * (interesMensual / 100);
			cuotaMes[0] = interes;
			double cuota_pagar = interes + abono_capital;
			cuotaMes[1] = cuota_pagar;
			double deudaTotal = deudaActual - (cuota_pagar - interes);
			cuotaMes[2] = deudaTotal;
			cuota[i] = cuotaMes;
			
			deudaActual = deudaTotal;
		}
		
		return cuota;
	}
	
	public static double[][] calculadoraCuotas(Cuotas cuotas, double deuda, double intereses, boolean auxiliar){
		int cuotasTotales = cuotas.getCantidad_Cuotas();
		double[][] cuota = new double[cuotasTotales][3];
		double interesMensual = Corriente.calculoInteresNominalMensual(intereses);
		double deudaActual = deuda;
		
		double abono_capital = deuda / cuotasTotales;
		
		double interesMes1 = deudaActual * (interesMensual / 100);
		double[] cuotaMes1 = new double[3];
		cuotaMes1[0] = 0;
		cuotaMes1[1] = abono_capital;
		cuotaMes1[2] = deudaActual - abono_capital;
		cuota[0] = cuotaMes1;
		
		deudaActual = deudaActual - abono_capital;
		
		for (int i = 1; i < cuotasTotales; i++) {
			double[] cuotaMes = new double[3];
			double interes = deudaActual * (interesMensual / 100);
			cuotaMes[0] = interes + interesMes1;
			double cuota_pagar = interes + abono_capital + interesMes1;
			cuotaMes[1] = cuota_pagar;
			double deudaTotal = deudaActual - (cuota_pagar - (interes + interesMes1));
			cuotaMes[2] = deudaTotal;
			cuota[i] = cuotaMes;
			
			interesMes1 = 0;
			deudaActual = deudaTotal;
		}
		
		return cuota;
	}
	
	public static double[] informacionAdicionalCalculadora (double[][] cuota, double deuda) {
		double[] infoAdicional = new double[3];
		double totalPagado = 0;
		
		for (int i = 0; i < cuota.length; i++) {
			totalPagado += cuota[i][1];
		}
		
		double interesesPagados = totalPagado - deuda;
		
		totalPagado = Corriente.redondeoDecimal(totalPagado, 2);
		interesesPagados = Corriente.redondeoDecimal(interesesPagados, 2);
		deuda = Corriente.redondeoDecimal(deuda, 2);
		
		infoAdicional[0] = totalPagado;
		infoAdicional[1] = interesesPagados;
		infoAdicional[2] = deuda;
		
		return infoAdicional;
	}
	
	public static void inicializarCupo(Corriente cuenta) {
		Banco banco = cuenta.getBanco();
		Suscripcion suscripcion = cuenta.getTitular().getSuscripcion();
		double cupo = Banco.decisionCupo(suscripcion, banco);
		cuenta.setCupo(cupo);
		cuenta.setDisponible(cupo);
	}
	
	//Método que evalúa la capacidad de hacer una compra
	public boolean capacidadDeuda(double cantidad) {
		if (this.getDisponible().compareTo(cantidad) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("cuentasCorrienteTotales");
	}
	
	public int compareTo(Corriente cuenta) {
		if(this.getDisponible() > cuenta.getDisponible()) {
			return 1;
		}
		else if(this.getDisponible() < cuenta.getDisponible()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public Corriente clone() throws CloneNotSupportedException{
		Corriente auxiliar = new Corriente();
		
		auxiliar.setCupo(this.getCupo());
		auxiliar.setDisponible(this.getDisponible());
		auxiliar.setPlazo_Pago(this.getPlazo_Pago());
		auxiliar.setIntereses(this.getIntereses());
		auxiliar.setPrimerMensualidad(this.getPrimerMensualidad());
		auxiliar.setClave(this.getClave());
		auxiliar.setDivisa(this.getDivisa());
		auxiliar.setNombre(this.getNombre());
		auxiliar.setId(this.getId());
		auxiliar.setBanco(this.getBanco());
		
		return auxiliar;
	}
	
	public Double getCupo() {
		return cupo;
	}
	
	public void setCupo(Double cupo) {
		this.cupo = cupo;
	}
	
	public Cuotas getPlazo_Pago() {
		return plazo_Pago;
	}
	
	public void setPlazo_Pago(Cuotas plazo_Pago) {
		this.plazo_Pago = plazo_Pago;
	}
	
	public Double getDisponible() {
		return disponible;
	}
	
	public void setDisponible(Double disponible) {
		this.disponible = disponible;
	}

	public double getIntereses() {
		return intereses;
	}

	public void setIntereses(double intereses) {
		this.intereses = intereses;
	}

	public boolean getPrimerMensualidad() {
		return primerMensualidad;
	}
	
	public void setPrimerMensualidad(boolean primerMensualidad) {
		this.primerMensualidad = primerMensualidad;
	}
	
	public String toString() { 
		return "Cuenta: " + this.nombre +
				"\nCuenta Corriente # " + this.id +
				"\nBanco: " + this.banco.getNombre() +
				"\nDivisa: " + this.divisa +
				"\nCupo: " + Cuenta.redondeoDecimal(this.cupo, 2) + " " + this.divisa +
				"\nCupo disponible: " + Cuenta.redondeoDecimal(this.disponible, 2) + " " + this.divisa +
				"\nCuotas: " + this.plazo_Pago +
				"\nIntereses: " + this.intereses;
	}
	
	public static ArrayList<Corriente> getCuentasCorrienteTotales() {
		return cuentasCorrienteTotales;
	}

	public static void setCuentasCorrienteTotales(ArrayList<Corriente> cuentasCorrienteTotales) {
		Corriente.cuentasCorrienteTotales = cuentasCorrienteTotales;
	}
}
