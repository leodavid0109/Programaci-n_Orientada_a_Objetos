package gestorAplicación.externo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import gestorAplicación.interno.Categoria;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Suscripcion;
import gestorAplicación.interno.Usuario;
import uiMain.Main;
import gestorAplicación.interno.Ahorros;

public class Banco implements Serializable {
	private static final long serialVersionUID = 2L;
	private static transient ArrayList<Banco> bancosTotales = new ArrayList<Banco>();
	public static final String nombreD = "Bancos";
	private String nombre;
	private double comision;
	private Divisas divisa;
	private int id;
	private Estado estadoAsociado;
	private double prestamo;
	private boolean asociado = false;
	private ArrayList<String> dic = new ArrayList<String>();
	private ArrayList<Double> cionario = new ArrayList<Double>();
	
	//Para la asignación del cupo de cada cuenta
	private double cupo_base = 1000000;
	private double multiplicador = 2;
	
	//Funcionalidad Compra de Cartera
	private double desc_suscripcion = 0.2;
	private double desc_movimientos_porcentaje = 0.2;
	private int desc_movimientos_cantidad = 5;
	
	//Constructores
	public Banco(String nombre, double comision, Estado estado, double desc_suscripcion, double desc_movimientos_porcentaje, int desc_movimientos_cantidad) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + estado.getTasa_impuestos();
		this.desc_suscripcion = desc_suscripcion;
		this.desc_movimientos_porcentaje = desc_movimientos_porcentaje;
		this.desc_movimientos_cantidad = desc_movimientos_cantidad;
		bancosTotales.add(this);
		this.setId(Banco.getBancosTotales().size());
	}
	
	public Banco(String nombre, double comision, Estado estado) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + estado.getTasa_impuestos();
		bancosTotales.add(this);
		this.setId(Banco.getBancosTotales().size());
	}
	
	public Banco(String nombre, double comision, Estado estado, double prestamo) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + estado.getTasa_impuestos();
		this.setPrestamo(prestamo);
		bancosTotales.add(this);
		this.setDivisa(estado.getDivisa());
		this.setId(Banco.getBancosTotales().size());
	}
	
	public Banco(String nombre, double comision, Estado estado, double prestamo, Divisas divisa, ArrayList<String> dic, ArrayList<Double> cionario, double cupo, int multiplicador, double desc_suscripcion, double desc_movimientos_porcentaje, int desc_movimientos_cantidad) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + estado.getTasa_impuestos();
		this.setPrestamo(prestamo);
		this.setDivisa(estado.getDivisa());
		this.setId(Banco.getBancosTotales().size());
		this.setDivisa(divisa);
		this.setDic(dic);
		this.setCionario(cionario);
		this.setCupo_base(cupo);
		this.setMultiplicador(multiplicador);
		this.setDesc_suscripcion(desc_suscripcion);
		this.setDesc_movimientos_porcentaje(desc_movimientos_porcentaje);
		this.setDesc_movimientos_cantidad(desc_movimientos_cantidad);
		bancosTotales.add(this);
	}
	
	public Banco() {
		this("Banco de Colombia", 0.3, Estado.getEstadosTotales().get(0), 200.0);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Banco) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	//Métodos
	
	public String mostrarBancosTotales() {
		if(Banco.bancosTotales.size() != 0) { 
			for (int i = 0; i < Banco.bancosTotales.size();) { 
				return(i + 1 + ". " + Banco.bancosTotales.get(i).getNombre()); 
				}
		}else { 
			return("No hay bancos en este momento, considere asociar bancos"); 
			}
		return ("");
	}
	
	public String mostrarDic(int k) {
		String clave= getDic().get(k);
		return clave;
	}
	
	public String mostrarDic() {
		
		return"";
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public Object comprobarSuscripción(Usuario usuario) {
		for(Usuario u : Usuario.getUsuariosTotales()) {
			if(usuario.getId() == u.getId()) {
				usuario.setLimiteCuentas(usuario.getSuscripcion().getLimiteCuentas());
				switch(usuario.getSuscripcion()) {
					case DIAMANTE:
						if(Main.getConf()) {
							this.setComision(this.getComision() * 0.50);
							Main.setConf(false);
						}
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() + " de comision");
					case ORO:
						if(Main.getConf()) {
							this.setComision(this.getComision() * 0.65);	
							Main.setConf(false);
						}
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision()+ " de comision");
					case PLATA:
						if(Main.getConf()) {
							this.setComision(this.getComision() * 0.85);
							Main.setConf(false);
						}
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() + " de comision");
					case BRONCE:
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() + " de comision");	
					default:
						return ("No encontramos tu grado de suscripción, considera registrarte en nuestro banco.");
				}
			}
		} return ("No encontramos tu ID registrado en este banco, considera registrarte en nuestro banco.");
	}
	// Métodos para funcionalidad de cambio de divisa
	public static ArrayList<Movimientos> cotizarTaza(Usuario user, ArrayList<Banco> existeCambio, String cadena, ArrayList<Ahorros> ahorrosPosibles) {
		ArrayList<Movimientos> imprimir = new ArrayList<Movimientos>();
		for (Ahorros ahorro : ahorrosPosibles) {
			for(Banco banco: existeCambio) {
				int indice = banco.getDic().indexOf(cadena);
				double valor = banco.getCionario().get(indice);
				if (ahorro.getBanco().equals(banco))
					valor = valor * 0.98;
				if (banco.isAsociado()) {
					valor = valor *0.97;
				}
				double cuotaManejo = Banco.divisaSuscripcion(user);
				Movimientos cotizacion = new Movimientos(banco, ahorro, valor, cuotaManejo);
				imprimir.add(cotizacion);
			}
		}
		return imprimir;
	}
	
	public static ArrayList<Movimientos> cotizarTazaAux(Usuario user, ArrayList<Banco> existeCambio, String cadena, ArrayList<Cuenta> cuentasPosibles) {
		ArrayList<Movimientos> imprimir = new ArrayList<Movimientos>();
		for (Cuenta conta : cuentasPosibles) {
			for(Banco banco: existeCambio) {
				int indice = banco.getDic().indexOf(cadena);
				double valor = banco.getCionario().get(indice);
				if (conta.getBanco().equals(banco))
					valor = valor * 0.98;
				if (banco.isAsociado()) {
					valor = valor *0.97;
				}
				double cuotaManejo = Banco.divisaSuscripcion(user);
				Movimientos cotizacion = new Movimientos(banco, conta, valor, cuotaManejo);
				imprimir.add(cotizacion);
			}
		}
		return imprimir;
	}
	
	public static double divisaSuscripcion(Usuario user) {
		switch(user.getSuscripcion()) {
		case BRONCE:
			return 0.01;
		case PLATA:
			return 0.008;
		case ORO:
			return 0.006;
		case DIAMANTE:
			return 0.004;
		}
		return 0.0;
	}
	
	// Método funcionalidad Asesoramiento de inversiones
	public static Integer retornoPortafolio(int riesgo, double invertir, String plazo, Usuario user) {

		double interes = Math.random() + riesgo;

		if (user.getCuentasAhorrosAsociadas().size() != 0
				&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
			double cobro = user.getCuentasAhorrosAsociadas().get(0).getSaldo()/ 32;
			Movimientos movimiento = new Movimientos(
					user.getCuentasAhorrosAsociadas().get(0), Usuario.getUsuariosTotales()
							.get(Usuario.hallarUsuarioImpuestosPortafolio()).getCuentasAhorrosAsociadas().get(0),
					cobro, Categoria.OTROS, Date.from(Instant.now()));
			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 1;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 2;
			}

			else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 3;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 4;
			}

		}

		else if (user.getCuentasAhorrosAsociadas().size() != 0
				&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
			double cobro = user.getCuentasAhorrosAsociadas().get(0).getSaldo()/ 32;
			Movimientos movimiento = new Movimientos(
					user.getCuentasAhorrosAsociadas().get(0), Usuario.getUsuariosTotales()
							.get(Usuario.hallarUsuarioImpuestosPortafolio()).getCuentasAhorrosAsociadas().get(0),
					cobro, Categoria.OTROS, Date.from(Instant.now()));
			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 5;
			} else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 6;
			} else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 7;
			} else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimiento);
				return 8;
			}
		}

		else if (user.getCuentasCorrienteAsociadas().size() != 0
				&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
			double cobroCorriente = user.getCuentasCorrienteAsociadas().get(0).getDisponible() / 32;
			Movimientos movimientoCorriente = new Movimientos(user.getCuentasCorrienteAsociadas().get(0),
					Usuario.getUsuariosTotales().get(Usuario.hallarUsuarioImpuestosPortafolio())
							.getCuentasCorrienteAsociadas().get(0),
					cobroCorriente, Categoria.OTROS, Date.from(Instant.now()));
			if (movimientoCorriente.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 1;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 2;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 3;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 4;
			}

		}

		else {
			double cobroCorriente = user.getCuentasCorrienteAsociadas().get(0).getDisponible() / 32;
			Movimientos movimientoCorriente = new Movimientos(user.getCuentasCorrienteAsociadas().get(0),
					Usuario.getUsuariosTotales().get(Usuario.hallarUsuarioImpuestosPortafolio())
							.getCuentasCorrienteAsociadas().get(0),
					cobroCorriente, Categoria.OTROS, Date.from(Instant.now()));
			if (movimientoCorriente.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 5;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 6;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 7;
			}

			else if (movimientoCorriente.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				Movimientos.getMovimientosTotales().remove(movimientoCorriente);
				return 8;
			}
		}
		return 0;
	}

	public static Banco bancoPortafolio(Usuario user) {
		Banco banco = null;
		if (user.getBancosAsociados().size() == 1) {
			banco = user.getBancosAsociados().get(0);
		} else {
			for (int i = 1; i <= user.getBancosAsociados().size() - 1; i++) {
				if (!user.getBancosAsociados().get(i - 1).equals(user.getBancosAsociados().get(i))) {
					banco = user.getBancosAsociados().get(i);
				} else {
					continue;
				}
			}
		}
		return banco;
	}

	public static double interesesPortafolio(Banco banco, Usuario user) {
		double interes = 0.0;

		for (int i = 0; i < user.getBancosAsociados().size(); i++) {
			if (user.getBancosAsociados().get(i) == banco) {
				interes = Math.round((interes + Math.random() + i) * 100.0) / 100.0;
			}
		}
		return interes;
	}
	
	public static Double verificarTasasdeInteres(Suscripcion suscripcion, Corriente cuenta){
		double interes = 0.0d;
		double descuento_movimientos = cuenta.getBanco().retornarDescuentosMovimientos(cuenta);
		double[] descuento_suscripcion = cuenta.getBanco().retornarDescuentosSuscripcion();
		double[] descuento_total = Banco.descuentoTotal(descuento_movimientos, descuento_suscripcion);
		switch(suscripcion) {
			case DIAMANTE:
				if (cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[3]) {
					interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[3];
				}
				else {
					interes = 0.0;
				}
			case ORO:
				if (cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[2]) {
					interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[2];
				}
				else {
					interes = 0.0;
				}
			case PLATA:
				if(cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[1]) {
					interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[1];
				}
				else {
					interes = 0.0;
				}
			case BRONCE:
				if(cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[0]) {
					interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[0];
				}
				else {
					interes = 0.0;
				}
		}
		return interes;
	}
	
	private double[] retornarDescuentosSuscripcion() {
		double[] descuento = new double[4];
		for(int i = 1; i < 5; i++) {
			descuento[i - 1] = this.desc_suscripcion * i;
		}
		return descuento;
	}
	
	//Método para recibir el descuento ocasionado por la cantidad de movimientos
	private double retornarDescuentosMovimientos(Corriente cuenta) {
		//Atributo que almacena todos los movimientos que están asociados del usuario con un Banco
		ArrayList<Movimientos> movimientosOriginariosconBanco = Movimientos.verificarMovimientosUsuario_Banco(cuenta.getTitular(), cuenta.getBanco());
		double descuento = Math.floorDiv(movimientosOriginariosconBanco.size(), this.desc_movimientos_cantidad) * this.desc_movimientos_porcentaje;
		return descuento;
	}
	
	public static ArrayList<Double> verificarTasasdeInteres(Usuario usuario, ArrayList<Corriente> cuentas){
		ArrayList<Double> tasasdeInteres = new ArrayList<Double>();
		Suscripcion suscripcion = usuario.getSuscripcion();
		for (Corriente cuenta : cuentas) {
			Double interes = verificarTasasdeInteres(suscripcion, cuenta);
			tasasdeInteres.add(interes);
		}
		return tasasdeInteres;
	}
	
	public static double[] descuentoTotal(double movimientos, double[] suscripcion) {
		double[] descuento_total = suscripcion;
		for (double descuento : descuento_total) {
			descuento = descuento + movimientos;
		}
		return descuento_total;
	}
	
	public static double decisionCupo(Suscripcion suscripcion, Banco banco) {
		double cupo;
		switch(suscripcion) {
			case DIAMANTE:
				cupo = banco.cupo_base * (banco.multiplicador * 3);
			case ORO:
				cupo = banco.cupo_base * (banco.multiplicador * 2);
			case PLATA:
				cupo = banco.cupo_base * (banco.multiplicador);
			default:
				cupo = banco.cupo_base;
		}
		return cupo;
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("bancosTotales");
		arreglo.remove("dic");
		arreglo.remove("cionario");
		arreglo.remove("prestamo");
		arreglo.remove("asociado");
		arreglo.remove("$SWITCH_TABLE$gestorAplicación$interno$Suscripcion");
	}
	
	//Gets
	public double getComision() {
		return comision;
	}
	
	public String getNombre() {
		return nombre;
	}

	public static ArrayList<Banco> getBancosTotales() {
		return bancosTotales;
	}
	
	public Estado getEstadoAsociado() {
		return estadoAsociado;
	}

	public double getPrestamo(){return prestamo;}
	
	public Divisas getDivisa() { return divisa; }
	
	public boolean isAsociado() {
		return asociado;
	}
	
	public ArrayList<String> getDic() {
		return dic;
	}
	
	public ArrayList<Double> getCionario() {
		return cionario;
	}

	public void setId(int id) { this.id = id; }
	
	public int getId() { return id; }
	
	public double getCupo_base() {
		return cupo_base;
	}

	public void setCupo_base(double cupo_base) {
		this.cupo_base = cupo_base;
	}

	public double getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(double multiplicador) {
		this.multiplicador = multiplicador;
	}
	
	public double getDesc_suscripcion() {
		return desc_suscripcion;
	}

	public void setDesc_suscripcion(double desc_suscripcion) {
		this.desc_suscripcion = desc_suscripcion;
	}

	public double getDesc_movimientos_porcentaje() {
		return desc_movimientos_porcentaje;
	}

	public void setDesc_movimientos_porcentaje(double desc_movimientos_porcentaje) {
		this.desc_movimientos_porcentaje = desc_movimientos_porcentaje;
	}
	
	public int getDesc_movimientos_cantidad() {
		return desc_movimientos_cantidad;
	}

	public void setDesc_movimientos_cantidad(int desc_movimientos_cantidad) {
		this.desc_movimientos_cantidad = desc_movimientos_cantidad;
	}

	//Sets
	public void setDivisa(Divisas divisa) { this.divisa = divisa; }
	
	public void setComision(double comision) {
		this.comision = comision;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static void setBancosTotales(ArrayList<Banco> bancosTotales) {
		Banco.bancosTotales = bancosTotales;
	}

	public void setEstadoAsociado(Estado estadoAsociado) {
		this.estadoAsociado = estadoAsociado;
	}

	public void setPrestamo(Double prestamo){this.prestamo = prestamo;}
	
	public void setAsociado(boolean asociado) {
		this.asociado = asociado;
	}
	
	public void setDic(ArrayList<String> dic) {
		this.dic = dic;
	}
	
	public void setCionario(ArrayList<Double> cionario) {
		this.cionario = cionario;
	}

	public String toString() {
		return "Nombre: " + this.nombre +
				"\nComision: " + this.comision +
				"\nDivisa: " + this.divisa +
				"\nId: " + this.id +
				"\nEstado Asociado: " + this.estadoAsociado.getNombre() +
				"\nCupo base: " + this.cupo_base +
				"\nMultiplicador: " + this.multiplicador +
				"\nDescuento por suscripción: " + this.desc_suscripcion +
				"\nDescuento por movimientos: " + this.desc_movimientos_porcentaje +
				" cada " + this.desc_movimientos_cantidad + " movimientos.";
	}
}
