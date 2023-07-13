package gestorAplicación.interno;

import java.util.Date;
import java.io.Serializable;
import java.lang.reflect.Field;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;
import java.time.Instant;

public class Movimientos implements Serializable{
	//	Atributos
	public static final String nombreD = "Movimientos";
	private static final long serialVersionUID = 5L;
	private static transient ArrayList<Movimientos> movimientosTotales = new ArrayList<>();
	private int id;
	private double cantidad;
	private Categoria categoria;
	private Date fecha;
	private Cuenta destino;
	private Cuenta origen;
	private Divisas divisa;
	private Divisas divisaAux;
	private Banco banco;
	private double coutaManejo;			
	

	// Funcionalidad de Asesor Inversiones
	private Usuario owner;
	private static String nombreCategoria;
	private static double cantidadCategoria;
	private static String recomendarFecha;

	//	CONSTRUCTORES
	//	Movimiento entre dos cuentas de ahorros
	public Movimientos(Ahorros origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setSaldo(origen.getSaldo() - cantidad);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	//	Movimiento a una cuenta de ahorros
	public Movimientos(Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(null);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	//	Movimiento entre dos cuentas corrientes
	public Movimientos(Corriente origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setDisponible(origen.getDisponible() - cantidad);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento a una cuenta corriente
	public Movimientos(Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(null);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento de una cuenta de ahorros a una cuenta corriente
	public Movimientos(Ahorros origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setSaldo(origen.getSaldo() - cantidad);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento de una cuenta corriente a una cuenta de ahorros
	public Movimientos(Corriente origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setDisponible(origen.getDisponible() - cantidad);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	//Intención de cambio de divisa
	public Movimientos(Divisas divisa, Divisas divisaAux, Usuario owner) {
		this.setDivisa(divisa);
		this.setDivisaAux(divisaAux);
		this.setOwner(owner);
	}
	
	//Cotización de cambio de divisa auxiliar
	public Movimientos(Banco banco, Ahorros origen, double cantidad, double coutaManejo) {
		this.setBanco(banco);
		this.setOrigen(origen);
		this.setCantidad(cantidad);
		this.setCoutaManejo(coutaManejo);
	}
	
	//Cotización de cambio de divisa con corriente
	public Movimientos(Banco banco, Cuenta origen, double cantidad, double coutaManejo) {
		this.setBanco(banco);
		this.setOrigen(origen);
		this.setCantidad(cantidad);
		this.setCoutaManejo(coutaManejo);
	}
	
	//Movimiento de cambio de divisa
	public Movimientos(Banco banco, Ahorros origen, Ahorros destino, Divisas divisa, Divisas divisasAux, double coutaManejo, double cantidad, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setBanco(banco);
		this.setOrigen(origen);
		this.setDestino(destino);
		this.setDivisa(divisa);
		this.setDivisaAux(divisaAux);
		this.setCoutaManejo(coutaManejo);
		this.setCantidad(cantidad);
		this.setFecha(fecha);
		this.setCategoria(Categoria.FINANZAS);
	}
	
	// Movimiento que emula una compra con una cuenta corriente
	public Movimientos(Corriente origen, double cantidad, Categoria categoria, Date fecha, boolean verificador) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setOrigen(origen);
		origen.setDisponible(origen.getDisponible() - cantidad);
	}
	
	//	MÉTODOS
	//	Funcionalidad de Suscripciones de Usuarios
	public static Object crearMovimiento(Ahorros origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(origen) && Cuenta.getCuentasTotales().contains(destino)){
			if (origen.getSaldo() < cantidad) {
				return("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + origen.getSaldo() + " por lo tanto no es posible realizar el movimiento");
			} else {
				return(new Movimientos(origen, destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), categoria, fecha));
			}
		}else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	//	Funcionalidad de Suscripciones de Usuarios
	public static Object crearMovimiento(Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(destino)){
			if(categoria == Categoria.PRESTAMO) {
				return(new Movimientos(destino, cantidad, categoria, fecha));
			}else {
				return(new Movimientos(destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), categoria, fecha));
			}
		}else {
			return("Debes verificar que la cuenta de destino exista");
		}
	}

	public static Object crearMovimiento(int origen, int destino, double cantidad, Categoria categoria, Date fecha) {
		Cuenta cuentaOrigen = null;
		Cuenta cuentaDestino = null;
		ArrayList<Cuenta> cuentasTotales = Cuenta.getCuentasTotales();
		for(int i = 0; i < cuentasTotales.size(); i++){
			if(cuentasTotales.get(i).getId() == origen){
				cuentaOrigen = cuentasTotales.get(i);
			}else if(cuentasTotales.get(i).getId() == destino){
				cuentaDestino = cuentasTotales.get(i);
			}
		}
		if(cuentaOrigen != null && cuentaDestino != null){
			if (cuentaOrigen instanceof Ahorros) {
				if (((Ahorros) cuentaOrigen).getSaldo() < cantidad) {
					return ("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + ((Ahorros) cuentaOrigen).getSaldo() + " por lo tanto no es posible realizar el movimiento");
				} 
				else {
					if (cuentaDestino instanceof Ahorros) {
						return (new Movimientos((Ahorros) cuentaOrigen, (Ahorros) cuentaDestino, cantidad, categoria, fecha));
					}
					else {//cuentaDestino será instancia de Corriente
						return (new Movimientos((Ahorros) cuentaOrigen, (Corriente) cuentaDestino, cantidad, categoria, fecha));
					}
				}
			}
			else {//cuentaOrigen será instancia de Corriente
				if (((Corriente) cuentaOrigen).getDisponible() < cantidad) {
					return ("¡Cupo Insuficiente! Su cuenta origen tiene un cupo disponible de: " + ((Corriente) cuentaOrigen).getDisponible() + " por lo tanto no es posible realizar el movimiento");
				} 
				else {
					if (cuentaDestino instanceof Ahorros) {
						return (new Movimientos((Corriente) cuentaOrigen, (Ahorros) cuentaDestino, cantidad, categoria, fecha));
					}
					else {
						return (new Movimientos((Corriente) cuentaOrigen, (Corriente) cuentaDestino, cantidad, categoria, fecha));
					}
				}	
			}
		}
		else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Corriente origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(origen) && Cuenta.getCuentasTotales().contains(destino)) {
			if(origen.getDisponible() < cantidad) {
				return("¡Cupo Insuficiente! Su cuenta origen tiene un cupo disponible de: " + origen.getDisponible() + " por lo tanto no es posible realizar el movimiento");
			}
			else {
				return(new Movimientos(origen, destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() + destino.getBanco().getComision()), categoria, fecha));
			}
		}
		else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(destino)) {
			return(new Movimientos(destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() + destino.getBanco().getComision()), categoria, fecha));
		}
		else {
			return("Debes verificar que la cuenta de destino exista");
		}
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public static Object modificarSaldo(Ahorros origen, Ahorros destino, double cantidad, Usuario usuario, Categoria categoria) {
		if (usuario.getCuentasAsociadas().contains(origen)) {
			Movimientos m = (Movimientos) Movimientos.crearMovimiento(origen, destino, cantidad, categoria, new Date());
			usuario.asociarMovimiento(m);
			return (m);

		} else {
			return ("La cuenta de destino debe estar asociada al usuario. Inténtelo de nuevo.");
		}
	}

	public String toString() {
		if(this.getOrigen() == null) {
			return("Movimiento creado \nFecha: " + getFecha() + "\nID: " + getId() + "\nDestino: " + getDestino().getId() + "\nCantidad: " +
					getCantidad() + "\nCategoria: " + getCategoria().name());
		}else if(this.getDestino() == null){
			return("Movimiento creado \nFecha: " + getFecha() + "\nID: " + getId() + "\nOrigen: " + getOrigen().getId() + "\nCantidad: " +
					getCantidad() + "\nCategoria: " + getCategoria().name());
		}
		else {
			return("Movimiento creado \nFecha: " + getFecha() + "\nID: " + getId() + "\nOrigen: " + getOrigen().getId() + "\nDestino: " + getDestino().getId() + "\nCantidad: " +
					getCantidad() + "\nCategoria: " + getCategoria().name());
		}
	}

	//	Funcionalidad Prestamos
	public static Object realizarPrestamo(Ahorros cuenta, double cantidad){
		Banco banco = cuenta.getBanco();
		Usuario titular = cuenta.getTitular();
		double maxCantidad = banco.getPrestamo() * titular.getSuscripcion().getPorcentajePrestamo();
		//	Comprueba que la cantidad si sea la adecuada
		if(cantidad > maxCantidad||cantidad<=0){
			return null;
		}else{
			// Creamos instancia de la clase deuda
			Deuda deuda = new Deuda(cantidad, cuenta, titular, banco);
			// Agrega el dinero a la cuenta
			return(Movimientos.crearMovimiento(cuenta, cantidad, Categoria.PRESTAMO,Date.from(Instant.now())));
		}
	}

	public static Object pagarDeuda(Usuario usuario, Metas deuda, Double cantidad){
		if (deuda.getCantidad() == cantidad){
//			Crear eliminar Deuda Ligadura Dinamica
			Ahorros cuenta = ((Deuda) deuda).getCuenta();
			Deuda.getDeudasTotales().remove(deuda);
			Metas.getMetasTotales().remove(deuda);
			deuda.setCantidad(0);
			cantidad= -cantidad;
//			cuenta.setSaldo(cuenta.getSaldo()-cantidad);
			return (Movimientos.crearMovimiento(cuenta,cantidad, Categoria.PRESTAMO, new Date()));
		}else{
			deuda.setCantidad(deuda.getCantidad() - cantidad);
			Ahorros cuenta = ((Deuda) deuda).getCuenta();
//			cuenta.setSaldo(cuenta.getSaldo()-cantidad);
			cantidad= -cantidad;
			return(Movimientos.crearMovimiento(cuenta,cantidad, Categoria.PRESTAMO, new Date()));
		}
	}
	
	//Métodos para funcionalidad cambio de divisa
	public static ArrayList<Banco> facilitarInformación(Movimientos mov) {
		//Usuario titular, Divisas divisaOrigen, Divisas divisaDevolucion
		for (int i = 0; i < mov.getOwner().getBancosAsociados().size() ; i++) {
			//int totalOrigen=0;
			mov.getOwner().getBancosAsociados().get(i).setAsociado(true);
		}
		String cadena= mov.getDivisa().name() + mov.getDivisaAux().name();
		ArrayList<Banco> existeCambio = new ArrayList<Banco>();
		for (int j = 0; j < Banco.getBancosTotales().size(); j++) {
			for (int k = 0; k< Banco.getBancosTotales().get(j).getDic().size(); k++ )
				if (cadena.equals(Banco.getBancosTotales().get(j).getDic().get(k))) {
					existeCambio.add(Banco.getBancosTotales().get(j));
			}
		}
		return existeCambio;
	}

	// Funcionalidad asesoramiento de inversión
	public static void analizarCategoria(Usuario u, String plazo) {
		int transporte = 0;
		int comida = 0;
		int educacion = 0;
		int finanzas = 0;
		int otros = 0;
		int regalos = 0;
		int salud = 0;
		int prestamos = 0;
		int big = 0;
		int posicion = 0;
		ArrayList<Integer> mayor = new ArrayList<Integer>();

		// Buscar la categoría en la que más dinero ha gastado el usuario
		for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
			Categoria categoria = u.getMovimientosAsociados().get(i).getCategoria();
			if (categoria == Categoria.TRANSPORTE) {
				transporte++;
			} else if (categoria == Categoria.COMIDA) {
				comida++;
			} else if (categoria == Categoria.EDUCACION) {
				educacion++;
			} else if (categoria == Categoria.SALUD) {
				salud++;
			} else if (categoria == Categoria.REGALOS) {
				regalos++;
			} else if (categoria == Categoria.FINANZAS) {
				finanzas++;
			} else if (categoria == Categoria.OTROS) {
				otros++;
			}else if (categoria == Categoria.PRESTAMO) {
				prestamos++;
			}
		}

		mayor.add(transporte);
		mayor.add(comida);
		mayor.add(educacion);
		mayor.add(salud);
		mayor.add(regalos);
		mayor.add(finanzas);
		mayor.add(otros);
		mayor.add(prestamos);

		for (int e = 0; e < mayor.size(); e++) {
			if (mayor.get(e) > big) {
				big = mayor.get(e);
				posicion = e;
			}
		}

		if (posicion == 0) {
			setNombreCategoria("Transporte");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.TRANSPORTE == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 1) {
			setNombreCategoria("Comida");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.COMIDA == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 2) {
			setNombreCategoria("Educacion");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.EDUCACION == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 3) {
			setNombreCategoria("Salud");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.SALUD == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 4) {
			setNombreCategoria("Regalos");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.REGALOS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 5) {
			setNombreCategoria("Finanzas");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.FINANZAS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 6) {
			setNombreCategoria("Otros");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.OTROS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}

		}
		
		else if (posicion == 7) {
			setNombreCategoria("Prestamos");
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.OTROS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}

		}

		// Recomendadar fecha
		if (plazo == "Corto") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				setRecomendarFecha("01/01/2024");
			} else {
				setRecomendarFecha("01/06/2025");
			}
		} else if (plazo == "Mediano") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				setRecomendarFecha("01/01/2026");
			} else {
				setRecomendarFecha("01/06/2027");
			}
		} else if (plazo == "Largo") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				setRecomendarFecha("01/01/2028");
			} else {
				setRecomendarFecha("01/06/2029");
			}
		} else {
			setRecomendarFecha(null);
		}
	}

	public boolean impuestosMovimiento(double interes) {
		Ahorros impuestosBanco = new Ahorros(this.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0);
		if (this.getOrigen().getBanco() == this.getDestino().getBanco()) {
			if (this.getOrigen() instanceof Corriente) {
				Movimientos movimiento1 = new Movimientos((Corriente) this.getOrigen(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento1);
			} else {
				Movimientos movimiento1 = new Movimientos((Ahorros) this.getOrigen(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento1);
			}
			Ahorros.getCuentasAhorroTotales().remove(impuestosBanco);
			Cuenta.getCuentasTotales().remove(impuestosBanco);
			return true;
		} else {
			if (this.getOrigen() instanceof Corriente) {
				Movimientos movimiento1 = new Movimientos((Corriente) this.getOrigen(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento1);
			} else {;
				Movimientos movimiento1 = new Movimientos((Ahorros) this.getOrigen(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos.getMovimientosTotales().remove(movimiento1);
			}
			Ahorros.getCuentasAhorroTotales().remove(impuestosBanco);
			Cuenta.getCuentasTotales().remove(impuestosBanco);
			return false;
		}
	}
	
	//Funcionalidad Compra de Cartera
	//Método que retorna un arreglo con los movimientos que salgan de una cuenta específica
	public static ArrayList<Movimientos> verificarOrigenMovimientos(ArrayList<Movimientos> movimientosAsociados, Cuenta cuenta){
		ArrayList<Movimientos> movimientosOriginariosCuenta = new ArrayList<Movimientos>();
		for (Movimientos movimiento: movimientosAsociados) {
			if(movimiento.origen == cuenta) {
				movimientosOriginariosCuenta.add(movimiento);
			}
		}
		return movimientosOriginariosCuenta;
	}
	
	//Método que retorna un arreglo con los movimientos que entren a una cuenta específica
	public static ArrayList<Movimientos> verificarDestinoMovimientos(ArrayList<Movimientos> movimientosAsociados, Cuenta cuenta){
		ArrayList<Movimientos> movimientosDestinoCuenta = new ArrayList<Movimientos>();
		for (Movimientos movimiento: movimientosAsociados) {
			if(movimiento.destino == cuenta) {
				movimientosDestinoCuenta.add(movimiento);
			}
		}
		return movimientosDestinoCuenta;
	}
	
	public static ArrayList<Movimientos> verificarMovimientosUsuario_Banco(Usuario usuario, Banco banco){
		ArrayList<Movimientos> movimientosAsociados = usuario.getMovimientosAsociados();
		ArrayList<Cuenta> cuentasAsociadas = usuario.getCuentasAsociadas();
		ArrayList<Cuenta> cuentasAsociadasaBanco = new ArrayList<Cuenta>();
		ArrayList<Movimientos> movimientosUsuario_Banco = new ArrayList<Movimientos>();
		for(Cuenta cuenta: cuentasAsociadas) {
			if(cuenta.getBanco() == banco) {
				cuentasAsociadasaBanco.add(cuenta);
			}
		}
		for(Cuenta cuenta: cuentasAsociadasaBanco) {
			ArrayList<Movimientos> movimientosAux = Movimientos.verificarOrigenMovimientos(movimientosAsociados, cuenta);
			for (Movimientos movimiento: movimientosAux) {
				movimientosUsuario_Banco.add(movimiento);
			}
		}
		return movimientosUsuario_Banco;
		
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("movimientosTotales");
		arreglo.remove("divisa");
		arreglo.remove("divisaAux");
		arreglo.remove("banco");
		arreglo.remove("cuotaManejo");
		arreglo.remove("owner");
		arreglo.remove("nombreCategoria");
		arreglo.remove("cantidadCategoria");
		arreglo.remove("recomensarFecha");
	}

	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Movimientos) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	
	//	GETS
	public static ArrayList<Movimientos> getMovimientosTotales() {
		return Movimientos.movimientosTotales;
	}

	public int getId() {
		return id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public Cuenta getOrigen() {
		return origen;
	}

	public Cuenta getDestino() {
		return destino;
	}

	//	SETS
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public static void setMovimientosTotales(ArrayList<Movimientos> movimientos) {
		Movimientos.movimientosTotales = movimientos;
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}
	
	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public Divisas getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}

	public Divisas getDivisaAux() {
		return divisaAux;
	}

	public void setDivisaAux(Divisas divisaAux) {
		this.divisaAux = divisaAux;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public double getCoutaManejo() {
		return coutaManejo;
	}

	public void setCoutaManejo(double coutaManejo) {
		this.coutaManejo = coutaManejo;
	}

	public static String getRecomendarFecha() {
		return recomendarFecha;
	}

	public static void setRecomendarFecha(String recomendarFecha) {
		Movimientos.recomendarFecha = recomendarFecha;
	}

	public static String getNombreCategoria() {
		return nombreCategoria;
	}

	public static void setNombreCategoria(String nombreCategoria) {
		Movimientos.nombreCategoria = nombreCategoria;
	}

	public static double getCantidadCategoria() {
		return cantidadCategoria;
	}

	public static void setCantidadCategoria(double cantidadCategoria) {
		Movimientos.cantidadCategoria = cantidadCategoria;
	}
}