package gestorAplicación.externo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Deuda;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Usuario;

public interface Tablas {
	public static ArrayList<String> recibirPropiedadesObjeto(Object clase){
		ArrayList<String> propiedades = new ArrayList<String>();
		if(clase.getClass().getName() == "gestorAplicación.interno.Ahorros" || clase.getClass().getName() == "gestorAplicación.interno.Corriente" || clase.getClass().getName() == "gestorAplicación.interno.Deuda") {
			Field[] propiedadAux = clase.getClass().getSuperclass().getDeclaredFields();
			for(int i = 0 ; i < propiedadAux.length; i++) {
				propiedades.add(propiedadAux[i].getName());
			}
		}
		Field[] propiedad = clase.getClass().getDeclaredFields();
		for(int i = 0 ; i < propiedad.length; i++) {
			propiedades.add(propiedad[i].getName());
		}
		return propiedades;
	}
	
	public static void impresionBancos(ArrayList<Banco> bancos) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(bancos.get(0));
		
		Banco.limpiarPropiedades(cadena);
		
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %20s %10s %10s %15s %20s %20s %29s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(2), cadena.get(4), cadena.get(5), cadena.get(7), cadena.get(8));
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Banco banco: bancos) {
			System.out.printf("%4d %8d %20s %10s %10s %15s %20s %20s %29s", 
					i, banco.getId(), banco.getNombre(), (Cuenta.redondeoDecimal(banco.getComision(), 2)), banco.getDivisa(), banco.getEstadoAsociado().getNombre(),
					(Cuenta.redondeoDecimal(banco.getCupo_base(), 2) + " (" + Cuenta.redondeoDecimal(banco.getMultiplicador(), 2) + ")"), Cuenta.redondeoDecimal(banco.getDesc_suscripcion(), 2),
					(Cuenta.redondeoDecimal(banco.getDesc_movimientos_porcentaje(), 2) + " (" + banco.getDesc_movimientos_cantidad() + ")"));
			System.out.println();
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionEstados(ArrayList<Estado> estados) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(estados.get(0));
		
		Estado.limpiarPropiedades(cadena);
		
		System.out.println("-------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %10s %15s %29s %15s", 
				"#", cadena.get(1), cadena.get(0), cadena.get(3), cadena.get(2), cadena.get(4), cadena.get(5));
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Estado estado: estados) {
			System.out.printf("%4d %8d %15s %10s %15s %29s %15s", 
					i, estado.getId(), estado.getNombre(), estado.getDivisa(), estado.getDivisa(), (Cuenta.redondeoDecimal(estado.getTasa_impuestos(), 2) + " %"),
					(Cuenta.redondeoDecimal(estado.getInteres_bancario_corriente(), 2)+ " %"), (Cuenta.redondeoDecimal(estado.getTasas_usura(), 2) + " %"));
			System.out.println();
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionCuentasCorriente(ArrayList<Corriente> cuentas) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(cuentas.get(0));
		
		Corriente.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %15s %15s %10s %10s %20s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(6), cadena.get(7),
				cadena.get(8), cadena.get(9), cadena.get(10), cadena.get(5));
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Corriente cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %15s %15s %10d %10s %20s %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), (Cuenta.redondeoDecimal(cuenta.getCupo(), 2) + " " + cuenta.getDivisa()), (Cuenta.redondeoDecimal(cuenta.getDisponible(), 2) + " " + cuenta.getDivisa()),
					cuenta.getPlazo_Pago().getCantidad_Cuotas(), (Cuenta.redondeoDecimal(cuenta.getIntereses(), 2) + " %"), cuenta.getPrimerMensualidad(), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionCuentasCorrienteInteres(ArrayList<Corriente> cuentas, ArrayList<Double> intereses) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(cuentas.get(0));
		
		Corriente.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %15s %15s %10s %10s %20s %20s %15s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(6), cadena.get(7),
				cadena.get(8), cadena.get(9), cadena.get(10), cadena.get(5), "Interés Nuevo");
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Corriente cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %15s %15s %10d %10s %20s %20s %15s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), (Cuenta.redondeoDecimal(cuenta.getCupo(), 2) + " " + cuenta.getDivisa()), (Cuenta.redondeoDecimal(cuenta.getDisponible(), 2) + " " + cuenta.getDivisa()),
					cuenta.getPlazo_Pago().getCantidad_Cuotas(), (Cuenta.redondeoDecimal(cuenta.getIntereses(), 2) + " %"), cuenta.getPrimerMensualidad(), cuenta.getBanco().getNombre(), (intereses.get(i - 1) + " %"));
			System.out.println();
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionCuentasAhorros(ArrayList<Ahorros> cuentas) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(cuentas.get(0));
		
		Ahorros.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %15s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(6), cadena.get(5));
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------");
		int i = 1;
		for (Ahorros cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %15s %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(),
					(Cuenta.redondeoDecimal(cuenta.getSaldo(), 2)) + " " + cuenta.getDivisa(), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("----------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionCuentas(ArrayList<Cuenta> cuentas) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(cuentas.get(0));

		Cuenta.limpiarPropiedades(cadena);
		if (cuentas.get(0) instanceof Ahorros) {
			Ahorros.limpiarPropiedades(cadena);
		}
		else if (cuentas.get(0) instanceof Corriente) {
			Corriente.limpiarPropiedades(cadena);
		}
		System.out.println("------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(5));
		System.out.println();
		System.out.println("------------------------------------------------------------------------------");
		int i = 1;
		for (Cuenta cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionDeudas(ArrayList<Deuda> deudas) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(deudas.get(0));
		
		Deuda.limpiarPropiedades(cadena);
		Metas.limpiarPropiedades(cadena);
		

		
		System.out.println("------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %15s %15s %15s", 
				"#", cadena.get(3), cadena.get(4), cadena.get(1), cadena.get(6), cadena.get(5));
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------");
		int i = 1;
		for (Deuda deuda: deudas) {
			System.out.printf("%4d %8s %20s %10s %20s %15s", 
					i, deuda.getId(), deuda.getDueno().getNombre(), Cuenta.redondeoDecimal( deuda.getCantidad(), 2), deuda.getBanco().getNombre(),
					(deuda.getCuenta().getId() + ": " + deuda.getCuenta().getNombre()));
			System.out.println();
			i++;
		}
		System.out.println("------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionMetas(ArrayList<Metas> metas) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(metas.get(0));
		
		Metas.limpiarPropiedades(cadena);
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %20s %15s %12s %14s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(4), cadena.get(1), cadena.get(2));
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
		int i = 1;
		for (Metas meta: metas) {
			if (meta.getFecha() == null) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), Cuenta.redondeoDecimal(meta.getCantidad(), 2), "/");
				System.out.println();
				i++;
			}
			else if(meta.getCantidad() == 0) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), "/", meta.getFechaNormal());
				System.out.println();
				i++;
			}
			else if(meta.getNombre() == null) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), "/", meta.getDueno().getNombre(), Cuenta.redondeoDecimal(meta.getCantidad(), 2), meta.getFechaNormal());
				System.out.println();
				i++;
			}
			else {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
						i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), Cuenta.redondeoDecimal(meta.getCantidad(), 2), meta.getFechaNormal());
				System.out.println();
				i++;
			}
			
		}
		System.out.println("--------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionMovimientos(ArrayList<Movimientos> movimiento) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(movimiento.get(0));
		
		Movimientos.limpiarPropiedades(cadena);
		
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %15s %35s %20s %20s", 
				"#", cadena.get(0), cadena.get(1), cadena.get(2), cadena.get(3), cadena.get(5), cadena.get(4));
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Movimientos mov: movimiento) {
			if (mov.getOrigen() == null) {
				System.out.printf("%4d %8d %15f %15s %35s %20s %20s", 
					i, mov.getId(), Cuenta.redondeoDecimal(mov.getCantidad(), 2), mov.getCategoria(), mov.getFecha(),
					"/", (mov.getDestino().getId() + ": " + mov.getDestino().getNombre()));
			}
			else if(mov.getDestino() == null) {
				System.out.printf("%4d %8d %15s %15s %35s %20s %20s", 
					i, mov.getId(), Cuenta.redondeoDecimal(mov.getCantidad(), 2), mov.getCategoria(), mov.getFecha(),
					(mov.getOrigen().getId() + ": " + mov.getOrigen().getNombre()), "/");
			}
			else {
				System.out.printf("%4d %8d %15s %15s %35s %20s %20s", 
					i, mov.getId(), Cuenta.redondeoDecimal(mov.getCantidad(), 2), mov.getCategoria(), mov.getFecha(),
					(mov.getOrigen().getId() + ": " + mov.getOrigen().getNombre()),
					(mov.getDestino().getId() + ": " + mov.getDestino().getNombre()));
			}
			
			System.out.println();
			i++;
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionUsuarios(ArrayList<Usuario> usuarios) {
		ArrayList<String> cadena = Tablas.recibirPropiedadesObjeto(usuarios.get(0));
		
		Usuario.limpiarPropiedades(cadena);

		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %22s %30s %12s %15s %20s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(4), cadena.get(5), cadena.get(6));
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Usuario usuario: usuarios) {
			System.out.printf("%4s %8s %22s %30s %12s %15d %20d", 
					i, usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getSuscripcion(),
					usuario.getLimiteCuentas(), usuario.getContadorMovimientos());
			System.out.println();
			i++;
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void impresionCotizaciones(ArrayList<Movimientos> movimientos) {
		System.out.println("--------------------------------------------------------------------------------");
		System.out.printf("%4s %20s %20s %10s %20s", 
				"#", "CUENTA", "BANCO", "TASA", "CUOTA DE MANEJO");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
		int i = 1;
		for (Movimientos movimiento: movimientos) {
			System.out.printf("%4s %20s %20s %10s %20s", 
					i, (movimiento.getOrigen().getId() + ": " + movimiento.getOrigen().getNombre()),
					movimiento.getBanco().getNombre(), Cuenta.redondeoDecimal(movimiento.getCantidad(), 4), Cuenta.redondeoDecimal(movimiento.getCoutaManejo(), 4));
			System.out.println();
			i++;
		}
		System.out.println("--------------------------------------------------------------------------------");
		
		return;
	}
}
