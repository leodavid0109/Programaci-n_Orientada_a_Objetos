package uiMain;

import baseDatos.Serializador;
import baseDatos.Deserializador;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;
import gestorAplicación.externo.Estado;
import gestorAplicación.externo.Tablas;
import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Categoria;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Suscripcion;
import gestorAplicación.interno.Usuario;
import gestorAplicación.interno.Deuda;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.time.Instant;

public final class Main {

	//FUNCIONALIDAD DE CAMBIO DE DIVISA
	private static boolean novato = true;
	private static void CambioDivisa(){
		System.out.println("¡Bienvenido al servicio de cambio de divisa!");
		if(novato) {
			System.out.println("Nota: El cambio de divisa se puede hacer de dos formas:" );
			System.out.println("La Convencional se da cuando se tiene una cantidad de dinero en una divisa y se desea convertirla a otra divisa,");
			System.out.println("y la Exacta se da cuando se desea obtener exactamente una cifra de dinero de una divisa a partir de otra divisa.");
		}
		System.out.println("¿Cuál tipo de cambio desea hacer?" + "\n1. Convencional" + "\n2. Exacta");
		opcion = Integer.parseInt(sc.nextLine());
		boolean exacta = false;
		if (opcion == 2) {
			exacta = true;
		}
		System.out.println("¿Desde qué divisa va a hacer el cambio?:");
		for(int i = 1;i < Divisas.getDivisas().size() + 1;i++ ) {
			System.out.println(i + ". " + Divisas.getDivisas().get(i-1).name());
		}
		opcion = Integer.parseInt(sc.nextLine());
		Divisas divisaA = Divisas.getDivisas().get(opcion-1);
		ArrayList<Ahorros> ahorrosPosibles = new ArrayList<Ahorros>();
		for (Ahorros ahorro : user.getCuentasAhorrosAsociadas()) {
			if (ahorro.getDivisa().equals(divisaA)) {
				ahorrosPosibles.add(ahorro);
			}
		}
		ArrayList<Divisas> divisasDevolucion = Divisas.getDivisas();
		divisasDevolucion.remove(opcion-1);
		System.out.println("¿A qué divisa desea hacer el cambio?");
		for(int i = 1;i < divisasDevolucion.size() + 1;i++ ) {
			System.out.println(i + ". " + divisasDevolucion.get(i-1).name());
		}
		opcion = Integer.parseInt(sc.nextLine());
		Divisas divisaB = divisasDevolucion.get(opcion - 1);
		String cadena = divisaA.name() + divisaB.name();
		if(exacta) {
			System.out.print("Usted hará un cambio de divisa exacto, ¿a qué monto desea llegar?: ");
		}
		else {
			System.out.print("Usted hará un cambio de divisa convencional, ¿qué monto desea cambiar?: ");		
		}
		double monto = Double.parseDouble(sc.nextLine());
		System.out.println("Buscando bancos para el cambio de divisa " + cadena + " ...");
		Movimientos cambioDiv = new Movimientos(divisaA , divisaB, user);
		ArrayList<Banco> existeCambio = Movimientos.facilitarInformación(cambioDiv);
		if (existeCambio.size()==0) {
			System.out.println("No existe banco que pueda hacer dicho cambio de divisa");
			return;
		}
		else if (existeCambio.size()==1) {
			System.out.println("Se ha encontrado un banco en donde realizar el cambio");
		}
		else {
			System.out.println("Se han encontrado " + existeCambio.size() + " bancos en donde realizar el cambio");
		}
		if (ahorrosPosibles.size() == 0) {
			System.out.print("Usted no posee ninguna cuenta con divisa " + divisaA.name());
			return;
		}
		System.out.println("A continuación todas las cotizaciones posibles para el cambio de divisa solicitado. Escoja una:");	
		ArrayList<Movimientos> imprimir = Banco.cotizarTaza(user, existeCambio, cadena, ahorrosPosibles);
		int j=0;
	//	if (imprimir.size() == 0) {
	//		System.out.println("No existen bancos que realicen el tipo de cambio pedido. Intente usar otra divisa como puente.");
	//		return;
	//	}
	//	for (Movimientos cotizacion : imprimir) {
	//		j=j+1;
	//		System.out.println(j + ". Por el banco " + cotizacion.getBanco().getNombre() + ", con su cuenta " + cotizacion.getOrigen().getNombre() + ", a una tasa de " + cotizacion.getCantidad() + " y una couta de manejo de " + cotizacion.getCoutaManejo());
	//	}
		Tablas.impresionCotizaciones(imprimir);
		opcion = Integer.parseInt(sc.nextLine());
		Movimientos escogencia = imprimir.get(opcion-1);
		System.out.print("¿Desea continuar con el proceso? (Y/N): ");
		String c = sc.nextLine();
		if (c.equals("N") || c.equals("n")) {
			opcion = 2;
			return;
		}
		System.out.print("La cuenta que recibe el dinero es mía (Y/N): ");
		String d= sc.nextLine();
		Usuario usuarioB = user;
		boolean confirmacion = false;
		if (d.equals("N") || d.equals("n")) {
			while (true) {
				System.out.print("Digite el nombre o correo electrónico del destinatario: ");
				String respuesta = sc.nextLine();
				for (Usuario usuario : Usuario.getUsuariosTotales()) {
					if (usuario.getNombre().equals(respuesta) || usuario.getCorreo().equals(respuesta)) {
						usuarioB = usuario;
						confirmacion = true;
						break;
						} 
					}if(!confirmacion) {
						System.out.print("Entrada no válida. Saliendo al menú anterior.");
						return;
					} break;
			}
		}
		Ahorros cuentaB = null;
		while(true) {
			System.out.println("Escoja la cuenta que va a recibir el dinero en " + divisaB.name() + ": ");
			ArrayList<Ahorros> cuentasB = Cuenta.obtenerCuentasDivisa(usuarioB, divisaB);
			int h = 1;
			for (Cuenta ahorro : cuentasB ) {
				System.out.println(h + ". " + ahorro.getNombre());
				h = h+1;
			}
			System.out.println(h + ". Salir");
			opcion = Integer.parseInt(sc.nextLine());
			if (opcion == h) {
				return;
			}
			else if ((opcion > 0) && (opcion < h)) {
				cuentaB =  cuentasB.get(opcion - 1);
				break;
			}
			else {
				System.out.println("No existe la opción " + opcion + ". Por favor digite su elección de nuevo");
			}
		}
		if (exacta == true) {
			if (!Cuenta.comprobarSaldo(escogencia, monto)) {
				System.out.println("Error. Usted no posee los fondos suficientes para realizar el cambio de divisa en la cuenta escogida." + "\nPuede realizar una transferencia a la cuenta que escogió en la cotización o utilizar alguna de las cuentas anteriormente mencionadas. Inténtelo de nuevo.");
				return; 
			}
			Cuenta.hacerCambio(escogencia, monto, cuentaB, exacta, user);
		}
		else {
			if (!Cuenta.comprobarSaldo(escogencia.getOrigen(), monto)) {
				System.out.println("Error. Usted no posee los fondos suficientes para realizar el cambio de divisa en la cuenta escogida." + "\nPuede realizar una transferencia a la cuenta que escogió en la cotización o utilizar alguna de las cuentas anteriormente mencionadas. Inténtelo de nuevo.");
				return; 
			}else {
				Cuenta.hacerCambio(escogencia, monto, cuentaB, user);
			}
		}
		System.out.println("Así quedan sus cuentas: ");
		ArrayList<Ahorros> imprimelo = new ArrayList<Ahorros>();
		Ahorros ahorroA = (Ahorros) escogencia.getOrigen();
		imprimelo.add(ahorroA);
		imprimelo.add(cuentaB);
		Tablas.impresionCuentasAhorros(imprimelo);
		novato=false;
	}

	// FUNCIONALIDAD DE PRESTAMO 
	private static void funcionalidadPrestamo() throws ParseException, CloneNotSupportedException{
		System.out.println("Bienvenido a Prestamos");
		System.out.println("1-Pedir Prestamo");
		System.out.println("2-Pagar Prestamo");
		System.out.println("3-Salir al menu");
		String c = sc.nextLine();
		switch (c) { 
		case "1":
			ArrayList<?> prestamo;
			prestamo = user.comprobarConfiabilidad();
			if (prestamo.get(0) instanceof Ahorros) {
				// Si tiene cuentas entonces vamos a comprar cuanto dieron prestan los bancos de las cuentas y mostrale al usuario
				prestamo = Ahorros.comprobarPrestamo(prestamo);
				if (prestamo.get(0) instanceof Ahorros) {
					System.out.println("Estas son las cuentas valida para hacer un prestamo y el valor maximo del prestamo");
					for (int i = 0; i < prestamo.size(); i++) {
						Ahorros cuenta = (Ahorros) prestamo.get(i);
						System.out.println((i+1)+ "-Cuenta: " + cuenta.getNombre() + " Maximo a prestar:" + cuenta.getBanco().getPrestamo() * user.getSuscripcion().getPorcentajePrestamo());
					}
					System.out.println(prestamo.size()+1 + "-Salir al Menú");
					System.out.println("Seleccione una:");
					//				recibe la opccion del usuario
					int opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
					while(opcion<1 || opcion >prestamo.size()+1) {
						System.out.println("¡Error! Seleccione una de las siguientes opciones");
						System.out.println("Estas son las cuentas valida para hacer un prestamo y el valor maximo del prestamo");
						for (int i = 0; i < prestamo.size(); i++) {
							Ahorros cuenta = (Ahorros) prestamo.get(i);
							System.out.println((i+1)+ "-Cuenta: " + cuenta.getNombre() + " Maximo a prestar:" + cuenta.getBanco().getPrestamo() * user.getSuscripcion().getPorcentajePrestamo());
						}
						System.out.println(prestamo.size()+1 + "-Salir al Menú");
						System.out.println("Seleccione una:");
						//					recibe la opccion del usuario
						opcion = Integer.parseInt(sc.nextLine());
						System.out.println("");
					}
					//				En caso de que desee salir se sale,
					if (opcion == prestamo.size()+1) {
						break;
					}else{
						//					en caso de que seleccione una de las cuentas
						Ahorros cuenta = (Ahorros) prestamo.get(opcion-1);
						double maxPrestamo = cuenta.getBanco().getPrestamo() * user.getSuscripcion().getPorcentajePrestamo();
						System.out.println("Ingrese el valor del prestamo, el valor de este debe ser menor de $" + maxPrestamo);
						maxPrestamo = Double.parseDouble(sc.nextLine());
						Object exito = Movimientos.realizarPrestamo(cuenta, maxPrestamo);
						if (exito instanceof Movimientos) {
							System.out.println("");
							System.out.println(exito);
							System.out.println("");
							System.out.println("|----------------------------------|\n\n    Prestamo Realizado con Exito\n\n|----------------------------------|");
							funcionalidadPrestamo();
						} else {
							System.out.println("Por favor seleccione una cantidad adecuada");
							funcionalidadPrestamo();
						}
					}


				} else {
					System.out.println("Los bancos de sus cuentas no realizan prestamos");
					break;
				}
			} else {
				for (int i = 0; i < prestamo.size(); i++) {
					System.out.println(prestamo.get(i));
				}
				break;
			}
			break;
			//PAGAR PRESTAMO
		case "2":
			ArrayList<Deuda> deudas = Deuda.conseguirDeudas(user);
			if (deudas.size() != 0) {
				Tablas.impresionDeudas(deudas);
				System.out.println("");
				System.out.print("Seleccione el número de la deuda que desea pagar: ");
				int seleccion = Integer.parseInt(sc.nextLine());
				while(true) {
					if(seleccion < 1 || seleccion > deudas.size()) {
						System.out.print("Has seleccionado un número de cuenta incorrecto. Inténtelo de nuevo: ");
						seleccion = Integer.parseInt(sc.nextLine());
					}else {
						break;
					}
				}
				System.out.print("Ingresa la cantidad que desea pagar: ");
				double cantidad = Double.parseDouble(sc.nextLine());
				while(true) {
					if(deudas.get(seleccion - 1).getCantidad() < cantidad) {
						System.out.println("Debes seleccionar una cantidad menor ó igual a la deuda, ésta es de " + deudas.get(seleccion - 1).getCantidad());
						System.out.println("Inténtelo de nuevo: ");
						cantidad = Double.parseDouble(sc.nextLine());
					}else if(cantidad == 0) {
						System.out.println("Debes consignar dinero para pagar la deuda, ésta es de " + deudas.get(seleccion - 1).getCantidad());
						System.out.println("Inténtelo de nuevo: ");
						cantidad = Double.parseDouble(sc.nextLine());
					}
					else {
						System.out.println();
						System.out.println(Movimientos.pagarDeuda(user, deudas.get(seleccion - 1), cantidad));
						if(deudas.get(seleccion - 1).getCantidad() == 0) {
							deudas.remove(deudas.get(seleccion - 1));
							System.out.println("|----------------------------------|\n\n Has pagado completamente tu deuda con éxito\n\n|----------------------------------|");
							//								System.gc();
						}else {
							System.out.println("Has pagado parcialmente tu deuda con éxito.\n");
						}
						break;
					}	
				}
			} else {
				System.out.println("Usted no tiene deudas por pagar");
			}
			break;
		default:

		}
	}

	// CREAR UNA META EN EL MAIN
	static void crearMeta() throws ParseException {

		int opcionMetas = 1;

		while (opcionMetas == 1) {
			// FORMATO EN EL QUE DESEA CREAR LA META
			System.out.println("¿En qué formato le gustaría crear su meta?: "
					+ "\n1. NombreMeta, CantidadMeta, FechaMeta" + "\n2. NombreMeta, CantidadMeta"
					+ "\n3. NombreMeta, FechaMeta" + "\n4. CantidadMeta, FechaMeta");

			int formato = Integer.parseInt(sc.nextLine());
			System.out.println("");

			if (formato == 1) {
				// PRIMERO SE PIDEN LOS DATOS
				System.out.println("Llene los siguientes campos para crear una meta");

				System.out.print("Nombre de la meta: ");
				String nombreMe = sc.nextLine();
				System.out.println("");

				System.out.print("Cantidad de ahorro: ");
				double cantidadMe = Double.parseDouble(sc.nextLine());
				System.out.println("");

				System.out.print("Fecha de la meta (formato dd/MM/yyyy): ");
				String fechaMe = sc.nextLine();
				System.out.println("");

				// Validar entradas
				if (nombreMe == null || cantidadMe == 0 || fechaMe == null) {
					System.out.println("Alguna de la entrada de los campos no es válida");
					System.out.println("");
					System.out.print("¿Desea volver a comenzar con el proceso?"
							+ "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
					String c = sc.nextLine();
					System.out.println("");

					if (c.equals("y") || c.equals("Y")) {
						opcionMetas = 1;
					}

					// Salir al menu de metas
					else if (c.equals("n") || c.equals("N")) {
						opcionMetas = 0;
					}

					// Validar entrada
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, cantidadMe, fechaMe);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.print(
						"¿Desea crear otra meta? (Y/N): ");
				String c = sc.nextLine();
				System.out.println("");

				if (c.equals("y") || c.equals("Y")) {
					opcionMetas = 1;
				}

				// Salir al menu de metas
				else if (c.equals("n") || c.equals("N")) {
					opcionMetas = 0;
				}

				// Validar entrada
				else {
					System.out.println("Entrada no valida");
					System.out.println("");
					opcionMetas = 0;
				}

			}

			else if (formato == 2) {
				// PRIMERO SE PIDEN LOS DATOS
				System.out.println("Llene los siguientes campos para crear una meta");

				System.out.print("Nombre de la meta: ");
				String nombreMe = sc.nextLine();
				System.out.println("");

				System.out.print("Cantidad de ahorro: ");
				double cantidadMe = Double.parseDouble(sc.nextLine());
				System.out.println("");

				// Validar entradas
				if (nombreMe == null || cantidadMe == 0) {
					System.out.println("Alguna de la entrada de los campos no es válida");
					System.out.println("");
					System.out.print("¿Desea volver a comenzar con el proceso?"
							+ "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
					String c = sc.nextLine();
					System.out.println("");

					if (c.equals("y") || c.equals("Y")) {
						opcionMetas = 1;
					}

					// Salir al menu de metas
					else if (c.equals("n") || c.equals("N")) {
						opcionMetas = 0;
					}

					// Validar entrada
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						opcionMetas = 1;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, cantidadMe);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.print(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
				String c = sc.nextLine();
				System.out.println("");

				if (c.equals("y") || c.equals("Y")) {
					opcionMetas = 1;
				}

				// Salir al menu de metas
				else if (c.equals("n") || c.equals("N")) {
					opcionMetas = 0;
				}

				// Validar entrada
				else {
					System.out.println("Entrada no valida");
					System.out.println("");
					opcionMetas = 0;
				}

			}

			else if (formato == 3) {
				// PRIMERO SE PIDEN LOS DATOS
				System.out.println("Llene los siguientes campos para crear una meta");

				System.out.print("Nombre de la meta: ");
				String nombreMe = sc.nextLine();
				System.out.println("");

				System.out.print("Fecha de la meta (formato dd/MM/yyyy): ");
				String fechaMe = sc.nextLine();
				System.out.println("");

				// Validar entradas
				if (nombreMe == null || fechaMe == null) {
					System.out.println("Alguna de la entrada de los campos no es válida");
					System.out.println("");
					System.out.print("¿Desea volver a comenzar con el proceso?"
							+ "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
					String c = sc.nextLine();
					System.out.println("");

					if (c.equals("y") || c.equals("Y")) {
						opcionMetas = 1;
					}

					// Salir al menu de metas
					else if (c.equals("n") || c.equals("N")) {
						opcionMetas = 0;
					}

					// Validar entrada
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, fechaMe);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.print(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
				String c = sc.nextLine();
				System.out.println("");

				if (c.equals("y") || c.equals("Y")) {
					opcionMetas = 1;
				}

				// Salir al menu de metas
				else if (c.equals("n") || c.equals("N")) {
					opcionMetas = 0;
				}

				// Validar entrada
				else {
					System.out.println("Entrada no valida");
					System.out.println("");
					opcionMetas = 0;
				}
			}

			else if (formato == 4) {
				// PRIMERO SE PIDEN LOS DATOS
				System.out.println("Llene los siguientes campos para crear una meta");

				System.out.print("Cantidad de ahorro: ");
				double cantidadMe = Double.parseDouble(sc.nextLine());
				System.out.println("");

				System.out.print("Fecha de la meta (formato dd/MM/yyyy): ");
				String fechaMe = sc.nextLine();
				System.out.println("");

				// Validar entradas
				if (cantidadMe == 0 || fechaMe == null) {
					System.out.println("Alguna de la entrada de los campos no es válida");
					System.out.println("");
					System.out.print("¿Desea volver a comenzar con el proceso?"
							+ "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
					String c = sc.nextLine();
					System.out.println("");

					if (c.equals("y") || c.equals("Y")) {
						opcionMetas = 1;
					}

					// Salir al menu de metas
					else if (c.equals("n") || c.equals("N")) {
						opcionMetas = 0;
					}

					// Validar entrada
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(cantidadMe, fechaMe);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.print(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas: ");
				String c = sc.nextLine();
				System.out.println("");

				if (c.equals("y") || c.equals("Y")) {
					opcionMetas = 1;
				}

				// Salir al menu de metas
				else if (c.equals("n") || c.equals("N")) {
					opcionMetas = 0;
				}

				// Validar entrada
				else {
					System.out.println("Entrada no valida");
					System.out.println("");
					opcionMetas = 0;
				}

			}

			else {
				System.out.println("Alguna de las entradas no es valida");
				opcionMetas = 0;
			}
		}
	}

	// ELIMINAR UNA META EN EL MAIN
	static void eliminarMeta() throws ParseException {
		int opcionEliminarMeta = 1;
		while (opcionEliminarMeta == 1) {

			System.out.println("¿Cual meta deseas eliminar?");

			// Opciones
			verMetas();

			int n = Integer.parseInt(sc.nextLine());
			System.out.println("");

			// llamamos la metodo eliminarMetas
			user.eliminarMetas(n - 1);

			// Terminar o continuar
			System.out
			.print("¿Desea eliminar otra meta? (Y/N): ");
			String c = sc.nextLine();
			System.out.println("");

			if (c.equals("y") || c.equals("Y")) {
				opcionEliminarMeta = 1;
			}

			// Salir al menu de metas
			else if (c.equals("n") || c.equals("N")) {
				opcionEliminarMeta = 0;
			}

			// Validar entrada
			else {
				System.out.println("Entrada no valida");
				System.out.println("");
				opcionEliminarMeta = 0;
			}
		}
	}

	// VER MIS METAS EN EL MAIN
	static void verMetas() throws ParseException {
		while (true) {
			if (user.getMetasAsociadas().size() == 0) {
				System.out.print("No tienes metas, ¿deseas crear una meta? (Y/N): ");
				String c = sc.nextLine();
				System.out.println("");
				if (c.equals("y") || c.equals("Y")) {
					Main.crearMeta();
					break;
				}

				// Salir al menu de metas
				else if (c.equals("n") || c.equals("N")) {
					break;
				}

				// Validar entrada
				else {
					System.out.println("Entrada no valida");
					System.out.println("");
					break;
				}
			} else {
				System.out.println("");
				Tablas.impresionMetas(user.getMetasAsociadas());
			}
			break;
		}
	}

	// CREAR MOVIMIENTO EN EL MAIN
	static void crearMovimiento() {
		System.out.println("Para realizar un MOVIMIENTO por favor ingresar los siguientes datos:");
		System.out.println("Ingrese el id de la cuenta origen:");
		int origen = Integer.parseInt(sc.nextLine());
		System.out.println("Ingrese el id la cuenta destino:");
		int destino = Integer.parseInt(sc.nextLine());
		System.out.print("Ingrese la cantidad a enviar:$");
		double cantidad = Double.parseDouble(sc.nextLine());
		System.out.println("selecione la categoria del movimiento");
		for (int i = 0; i < Categoria.values().length; i++) {
			System.out.println(i + "-" + Categoria.values()[i]);
		}
		int numCategoria = Integer.parseInt(sc.nextLine());
		Categoria categoria = Categoria.values()[numCategoria];
		System.out.println(Movimientos.crearMovimiento(origen, destino, cantidad, categoria, Date.from(Instant.now())));
	}

	static void compraCorriente() {
		if (user.getCuentasCorrienteAsociadas().size() == 0) {
			System.out.println("Para realizar una compra con una cuenta Corriente debes primero poseer una cuenta de este tipo.");
			return;
		}
		
		System.out.println("¿Qué tipo de compra desea realizar?");
		Main.verCategorias();
		
		int tipo_Compra = 0;
		//Atributo para validación entrada tipo_Compra
		boolean validacion_Tipo_Compra = true;
		while (validacion_Tipo_Compra) {
			System.out.print("Por favor, seleccione el tipo de compra: ");
			tipo_Compra = Integer.parseInt(sc.nextLine());

			if (tipo_Compra >= 1 && tipo_Compra < Categoria.getCategorias().size() + 1) {
				validacion_Tipo_Compra = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo.");
			}
		}
		
		System.out.print("Ingrese el valor de la compra que desea realizar: ");
		int valorCompra = Integer.parseInt(sc.nextLine());
		
		ArrayList<Corriente> cuentasCompra = new ArrayList<Corriente>();
		for (Corriente cuenta : user.getCuentasCorrienteAsociadas()) {
			if (cuenta.capacidadDeuda(valorCompra)) {
				cuentasCompra.add(cuenta);
			}
		}
		
		if (cuentasCompra.size() == 0) {
			System.out.println("No puedes hacer la compra con ninguna de tus cuentas corriente.");
			return;
		}
		
		System.out.println("Las cuentas a nombre de " + user.getNombre() + " capaces de recibir tu compra son:");
		Tablas.impresionCuentasCorriente(cuentasCompra);
		
		int eleccion_Cuenta = 0;
		//Atributo para validación entrada eleccion_Cuenta
		boolean validacion_eleccion_Cuenta = true;
		while(validacion_eleccion_Cuenta) {
			System.out.print("Elija por favor la cuenta con la que realizará la compra: ");
			eleccion_Cuenta = Integer.parseInt(sc.nextLine());
			
			if (eleccion_Cuenta >= 1 && eleccion_Cuenta <= cuentasCompra.size()) {
				validacion_eleccion_Cuenta = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo.");
			}
		}
		
		new Movimientos(cuentasCompra.get(eleccion_Cuenta - 1), valorCompra, Categoria.getCategorias().get(tipo_Compra - 1), Date.from(Instant.now()), true);
		System.out.println("Compra realizada con éxito.");
	}
	
	// FUNCIONALIDAD ASESORAMIENTO DE INVERSIONES
	static void asesorInversiones() throws ParseException, CloneNotSupportedException {
		int funcionalidad = 1;
		while (funcionalidad == 1) {
			// Se confirman que hayan ciertos requeriminetos para el buen funcionamiento de
			// la funcionalidad
			System.out.println("");
			if (user.getMetasAsociadas().size() == 0) {
				System.out.println("Primero debes crear una meta para acceder a esta funcionalidad");
			}

			else if (user.getCuentasAhorrosAsociadas().size() == 0 && user.getCuentasCorrienteAsociadas().size() == 0) {
				System.out.println(
						"Primero debes crear una cuenta asociada a tu usuario para acceder a esta funcionalidad");
			}

			else if (user.getMovimientosAsociados().size() == 0) {
				System.out.println("Primero debes realizar un movimiento para acceder a esta funcionalidad");
			}

			else if (Metas.revisionMetas(user) == null) {
				System.out.println("Primero debes crear una meta con fecha para acceder a esta funcionalidad");
				break;
			}

			else {
				System.out.println("¿Cuál es su tolerancia de riesgos?: " + "\n1. Baja" + "\n2. Media" + "\n3. Alta");
				int riesgo = Integer.parseInt(sc.nextLine());
				System.out.println("");

				// Revisar que las entradas sean correctas
				if (riesgo < 1 || riesgo > 3) {
					System.out.println("Alguna de las entradas no es válida");
					break;
				}

				else {

					System.out.print("¿Qué cantidad piensa invertir?: ");
					int invertir = Integer.parseInt(sc.nextLine());
					System.out.println("");
					if (Metas.revisionMetas(user).getNombre() == null) {
						System.out.println(
								"Tienes una meta para una fecha muy próxima: " + Metas.revisionMetas(user).getCantidad()
								+ ", " + Metas.revisionMetas(user).getFechaNormal());
					} else if (Metas.revisionMetas(user).getCantidad() == 0.0) {
						System.out.println(
								"Tienes una meta para una fecha muy próxima: " + Metas.revisionMetas(user).getNombre()
								+ ", " + Metas.revisionMetas(user).getFechaNormal());
					} else {
						System.out.println("Tienes una meta para una fecha muy próxima: "
								+ Metas.revisionMetas(user).getNombre() + ", " + Metas.revisionMetas(user).getCantidad()
								+ ", " + Metas.revisionMetas(user).getFechaNormal());
					}

					System.out.print("¿Desearías cambiar la fecha de esta meta para invertir ese dinero "
							+ "en tu portafolio? (Y/N): ");
					String cambiarFecha = sc.nextLine();
					System.out.println("");

					if (cambiarFecha.equals("y") || cambiarFecha.equals("Y")) {
						System.out.print("¿Para qué fecha desearías cambiar la meta? " + "(formato dd/MM/yyyy): ");
						String nuevaFecha = sc.nextLine();
						Metas.determinarPlazo(Metas.cambioFecha(Metas.revisionMetas(user), nuevaFecha));
						System.out.println("");
						System.out.println("La fecha ha sido modificada satisfactoriamente y "
								+ "su plazo de inversión es: Plazo " + Metas.getPlazo());
						System.out.println("");
					}

					else if (cambiarFecha.equals("n") || cambiarFecha.equals("N")) {
						Metas.determinarPlazo(Metas.revisionMetas(user));
						System.out.println("Su plazo de inversión es: Plazo " + Metas.getPlazo());
						System.out.println("");
					}

					else {
						System.out.println("Entrada no valida");
						break;
					}

					System.out.println("Advertencia: Con el fin hacer un buen asesoramiento analizaremos "
							+ "\nsus movimientos para encontrar la categoría en la que más dinero ha gastado.");
					System.out.println("" + "\nProcesando..." + "\n");

					// Buscamos la categoría en la que el usuario ha gastado más dinero
					Movimientos.analizarCategoria(user, Metas.getPlazo());

					System.out.println(
							"La categoría en la que más dinero ha gastado es en: " + Movimientos.getNombreCategoria()
							+ " que suma un total de " + Movimientos.getCantidadCategoria() + ".");
					System.out.print("¿Deseas crear una meta con el fin de ahorrar la misma "
							+ "cantidad que has gastado en esta categoría? (Y/N): ");
					String nuevaMeta = sc.nextLine();
					System.out.println("");

					// Crear una meta con los parametros preestablecidos
					if (nuevaMeta.equals("y") || nuevaMeta.equals("Y")) {
						System.out.println(
								"Usaremos tus datos para crear la meta. Luego vamos a priorizar esa meta respecto a las demás que tengas");

						Metas metaCategoria = new Metas(Movimientos.getNombreCategoria(), Movimientos.getCantidadCategoria(),
								Movimientos.getRecomendarFecha());

						user.asociarMeta(metaCategoria);

						// Priorizamos la meta
						System.out.println("La meta ha sido creada y puesta como prioridad en tu lista de metas");
						Metas.prioridadMetas(user, metaCategoria);
						verMetas();
						System.out.println("");

					} else if (nuevaMeta.equals("n") || nuevaMeta.equals("N")) {
					}

					else {
						System.out.println("Entrada no valida");
						break;
					}
					String bancoPortafolio = "Nota: Hay un banco asociado al portafolio: "
							+ Banco.bancoPortafolio(user).getNombre() + ", con una tasa de interes del: "
							+ Banco.interesesPortafolio(Banco.bancoPortafolio(user), user) + "%";

					if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 1) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Servicios de comunicación" + "\n-Consumo discrecional"
										+ "\n-Bienes raíces" + "\n" + bancoPortafolio);
					}
					// Portafolio 2
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 2) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Productos básicos de consumo\r\n" + "-Energía\r\n"
										+ "-Compañías de inteligencia artificial\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 3
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 3) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Finanzas\r\n" + "-Cuidado de la salud\r\n"
										+ "-Servicios de comunicación\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 4
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 4) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Oro\r\n" + "-Acciones industriales\r\n" + "-Información tecnológica\r\n"
										+ "\n" + bancoPortafolio);
					}
					// Portafolio 5
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 5) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Materiales de construcción\r\n" + "-Bienes raíces\r\n" + "-Finanzas\r\n"
										+ "\n" + bancoPortafolio);
					}
					// Portafolio 6
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 6) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Cuidado de la salud\r\n" + "-Utilidades\r\n" + "-Comodidades\r\n" + "\n"
										+ bancoPortafolio);
					}
					// Portafolio 7
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 7) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Oro\r\n" + "-Bonos gubernamentales a mediano plazo\r\n"
										+ "-Información tecnológica\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 8
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.getPlazo(), user) == 8) {
						System.out.println(
								"En base a los datos recolectados, deberías invertir tu dinero en estos sectores: "
										+ "\n-Compañías de inteligencia artificial\r\n"
										+ "-Bonos gubernamentales a largo plazo\r\n"
										+ "-Productos básicos de consumo\r\n" + "\n" + bancoPortafolio);
					}

					else {
						System.out.println("No tenemos portafolios para recomendarte");
					}

					System.out.println("");
					System.out.print("Finalmente, para mejorar aún más tu inversión te recomendamos "
							+ "hacer un préstamo. ¿Deseas hacer el préstamo? (Y/N): ");
					String prestamo = sc.nextLine();
					System.out.println("");
					
					if (prestamo.equals("n") || prestamo.equals("N")) {	
						System.out.println("Ha sido un placer asesorarte en este proceso, "
							+ "espero que nuestra recomendación haya sido de ayuda.");
						funcionalidad = 0;
					}
					else if (prestamo.equals("y") || prestamo.equals("Y")) {
						System.out.print("Las tasas de interés de los prestamos estan muy altas pero tenemos la solución perfecta para ti, "
									+ "\naunque no sea la más correcta... Vas a hacer un prestamo con el usuario gota a gota."
									+ "\nIngrese el monto que desea solicitar prestado: ");

							// Parte del gota a gota
							double cantidadPrestamo = Double.parseDouble(sc.nextLine());
							System.out.println("");

							// Métodos
							Ahorros gotaGota = Usuario.getUsuariosTotales().get(Usuario.hallarUsuariogotaGota())
									.getCuentasAhorrosAsociadas().get(0);
							Cuenta.gotaGota(cantidadPrestamo, user, gotaGota).vaciarCuenta(gotaGota);
							System.out.println("Era una trampa, ahora el usuario gota a gota vació tu cuenta");
							System.out.println("");
							System.out.println("Ha sido un placer asesorarte en este proceso, "
									+ "espero que nuestra recomendación haya sido de ayuda.");
							funcionalidad = 0;
						}

					else {
						System.out.println("Entrada no valida");
						break;
					}
				}
			}
		}
	}

	// Sobrecarga funcionalidad para chequeo en eliminación de cuenta
	static boolean compraCartera(Corriente cuenta) throws CloneNotSupportedException {
		Usuario usuario = cuenta.getTitular();

		//Arreglo que almacena las cuentas asociadas a un usuario
		ArrayList<Cuenta> cuentasAux = usuario.getCuentasAsociadas();
		Collections.sort(cuentasAux);

		cuentasAux.remove(cuenta);

		//Arreglo que almacena las cuentas capaces de recibir la deuda
		ArrayList<Corriente> cuentasCapacesDeuda = usuario.Capacidad_Endeudamiento(cuentasAux, cuenta);
		Collections.sort(cuentasCapacesDeuda);
		//Arreglo que almacena las tasas de intereses aplicables con orden del arreglo anterior
		ArrayList<Double> tasacionCuentas = Banco.verificarTasasdeInteres(usuario, cuentasCapacesDeuda);

		cuentasAux.add(cuenta);
		Collections.sort(cuentasAux);
		
		System.out.println("Las cuentas a su nombre que pueden recibir la deuda de la Cuenta a eliminar son: ");
		Tablas.impresionCuentasCorrienteInteres(cuentasCapacesDeuda, tasacionCuentas);

		//Atributo de validacion de la entrada Cuenta_Destino
		boolean validacion_Cuenta_Destino = true;
		//Atributo auxiliar que almacenará la cuenta destino de la deuda
		int Cuenta_Destino = 0;
		while (validacion_Cuenta_Destino) {
			System.out.println("Por favor escoga la cuenta destino de la deuda:");
			Cuenta_Destino = Integer.parseInt(sc.nextLine());
			if (Cuenta_Destino >= 1 && Cuenta_Destino <= cuentasCapacesDeuda.size()) {
				validacion_Cuenta_Destino = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}

		//Atributo de validacion de la entrada Periodicidad
		boolean validacion_Periodicidad = true;
		//Atributo auxiliar para almacenar decision de periodicidad
		int Periodicidad = 0;
		while (validacion_Periodicidad) {
			System.out.println("¿Desea mantener la periodicidad del pago de la deuda?"
					+ "\n1. Sí"
					+ "\n2. No");
			Periodicidad = Integer.parseInt(sc.nextLine());
			if (Periodicidad == 1 || Periodicidad == 2) {
				validacion_Periodicidad = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}

		if (Periodicidad == 1) {
			System.out.println("Perfecto, la deuda mantendrá un plazo de pago a " + cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago() + " cuotas.");
		}
		//Atributo de la periodicidad
		Cuotas eleccion_periodicidad = cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago();
		if (Periodicidad == 2) {
			//Atributo de validacion de la seleccion de periodicidad
			boolean validacion_Seleccion_Periodicidad = true;
			int seleccion_periodicidad = 0;

			while (validacion_Seleccion_Periodicidad) {
				System.out.println("Por favor seleccione la nueva periodicidad de la Deuda: "
						+ "\n1. 1 Cuota"
						+ "\n2. 6 Cuotas"
						+ "\n3. 12 Cuotas"
						+ "\n4. 18 Cuotas"
						+ "\n5. 24 Cuotas"
						+ "\n6. 36 Cuotas"
						+ "\n7. 48 Cuotas");
				seleccion_periodicidad = Integer.parseInt(sc.nextLine());
				if (seleccion_periodicidad < 1 || seleccion_periodicidad > 7) {
					System.out.println("Entrada no válida, intente de nuevo");
				}
				else {
					validacion_Seleccion_Periodicidad = false;
				}
			}
			switch(seleccion_periodicidad) {
			case 1:
				eleccion_periodicidad = Cuotas.C1;
				System.out.println("Deuda establecida a: " + Cuotas.C1.getCantidad_Cuotas() + "cuotas.");
				break;
			case 2:
				eleccion_periodicidad = Cuotas.C6;
				System.out.println("Deuda establecida a: " + Cuotas.C6.getCantidad_Cuotas() + "cuotas.");
				break;
			case 3:
				eleccion_periodicidad = Cuotas.C12;
				System.out.println("Deuda establecida a: " + Cuotas.C12.getCantidad_Cuotas() + "cuotas.");
				break;
			case 4:
				eleccion_periodicidad = Cuotas.C18;
				System.out.println("Deuda establecida a: " + Cuotas.C18.getCantidad_Cuotas() + "cuotas.");
				break;
			case 5:
				eleccion_periodicidad = Cuotas.C24;
				System.out.println("Deuda establecida a: " + Cuotas.C24.getCantidad_Cuotas() + "cuotas.");
				break;
			case 6:
				eleccion_periodicidad = Cuotas.C36;
				System.out.println("Deuda establecida a: " + Cuotas.C36.getCantidad_Cuotas() + "cuotas.");
				break;
			case 7:
				eleccion_periodicidad = Cuotas.C48;
				System.out.println("Deuda establecida a: " + Cuotas.C48.getCantidad_Cuotas() + "cuotas.");
				break;
			}
		}

		Corriente vistaPrevia = Corriente.vistaPreviaMovimiento(cuentasCapacesDeuda.get(Cuenta_Destino - 1), eleccion_periodicidad, cuenta.getDisponible(), tasacionCuentas.get(Cuenta_Destino - 1));
		
		int pagoPrimerMes = 1;
		
		if(eleccion_periodicidad.getCantidad_Cuotas() != 1) {
			boolean validacionPagoPrimerMes = true;
			while(validacionPagoPrimerMes) {
				System.out.println("¿Desea pagar intereses en el primer mes? Tenga en cuenta que de no hacerlo, en el segundo mes"
						+ "deberá pagar su valor correspondiente."
						+ "\n1. Sí"
						+ "\n2. No");
				pagoPrimerMes = Integer.parseInt(sc.nextLine());
				if (pagoPrimerMes == 1 || pagoPrimerMes == 2) {
					validacionPagoPrimerMes = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo");
				}
			}
		}

		double[] cuota;
		if (pagoPrimerMes == 1) {
			cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getDisponible());
			vistaPrevia.setPrimerMensualidad(true);
		}
		else {
			cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getDisponible(), 1);
		}

		//Vista Previa de los resultados del cambio
		System.out.println("Vista previa de como quedaría la cuenta escogida para recibir la deuda: ");
		System.out.println();
		System.out.println(vistaPrevia);
		String cuotaMensual = Corriente.imprimirCuotaMensual(cuota);

		System.out.println("Primer Cuota: ");
		System.out.println(cuotaMensual + " " + vistaPrevia.getDivisa());
		
		int decisionCalculadora = 2;
		if(eleccion_periodicidad.getCantidad_Cuotas() != 1) {
			boolean validacionCalculadora = true;
			while(validacionCalculadora) {
				System.out.println("¿Desea un resumen completo de las deudas a pagar?"
						+ "\n1. Sí"
						+ "\n2. No");
				decisionCalculadora = Integer.parseInt(sc.nextLine());
				if (decisionCalculadora == 1 || decisionCalculadora == 2) {
					validacionCalculadora = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo");
				}
			}
		}
	
		if(decisionCalculadora == 1) {
			double[][] cuotaCalculadora;
			if(vistaPrevia.getPrimerMensualidad()) {
				cuotaCalculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses());
			}
			else {
				cuotaCalculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses(), true);
			}
			double[] infoAdicional = Corriente.informacionAdicionalCalculadora(cuotaCalculadora, vistaPrevia.getCupo() - vistaPrevia.getDisponible());
			Main.calculadoraCuotas(cuotaCalculadora, infoAdicional);
		}

		//Atributo de validacion de la entrada confirmacion Movimiento
		boolean validacion_vistaPrevia = true;
		//Atributo auxiliar para almacenar la confirmación del movimiento
		int confirmacionMovimiento = 0;
		while (validacion_vistaPrevia) {
			System.out.println("¿Desea confirmar la realización del movimiento?"
					+ "\n1. Sí"
					+ "\n2. No");
			confirmacionMovimiento = Integer.parseInt(sc.nextLine());
			if (confirmacionMovimiento == 1 || confirmacionMovimiento == 2) {
				validacion_vistaPrevia = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		switch(confirmacionMovimiento) {
		case 1:
			//Cambios para la cuenta origen
			cuenta.setDisponible(cuenta.getCupo());
			cuenta.setPlazo_Pago(Cuotas.C1);
			
			//Cambios para la cuenta destino
			Cuenta.getCuentasTotales().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			usuario.getCuentasAsociadas().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			usuario.getCuentasCorrienteAsociadas().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			Corriente.getCuentasCorrienteTotales().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			
			user.asociarCuenta(vistaPrevia);
			
			System.out.println("Compra de cartera realizada con éxito");
			
			return true;

		case 2:
			Cuenta.getCuentasTotales().remove(vistaPrevia);
			Corriente.getCuentasCorrienteTotales().remove(vistaPrevia);
			vistaPrevia = null;
			System.out.println("Movimiento cancelado.");
			return false;
		}
		return false;
	}

	// FUNCIONALIDAD COMPRA DE CARTERA
	static void compraCartera() throws CloneNotSupportedException {
		
		//Arreglo que almacena las cuentas con deuda alguna 
		ArrayList<Corriente> cuentasEnDeuda = user.retornarDeudas();
		Collections.sort(cuentasEnDeuda);
		
		//Arreglo que almacena las cuentas asociadas a un usuario
		ArrayList<Cuenta> cuentasAux = user.getCuentasAsociadas();
		Collections.sort(cuentasAux);

		ArrayList<Corriente> cuentasAux1 = user.getCuentasCorrienteAsociadas();

		//Comprobación de existencia de Cuentas Corriente por parte del Usuario
		if(cuentasAux1.size() <= 1) {
			System.out.println("El usuario " + user.getNombre() + " no alcanza la cantidad de cuentas Corriente necesarias para "
					+ "desarrollar la funcionalidad, recuerda que para ejecutar una compra de cartera necesitas por lo menos dos cuentas "
					+ "Corriente, una de ellas con una Deuda.");
			return;
		}
		//Comprobación de existencia de Deudas por parte del Usuario
		if (cuentasEnDeuda.size() == 0) {
			System.out.println("El usuario " + user.getNombre() + " no tiene préstamos asociados, no es posible realizar la funcionalidad.");
			return;
		}

		//Atributo auxiliar que almacenará el índice de la cuenta escogida por el usuario
		int Cuenta_Compra = 0;
		//Booleano usado para repetir el proceso de seleccion de cuenta
		boolean seleccion_Cuenta = true;

		while (seleccion_Cuenta) {
			System.out.println("Cuentas a nombre de " + user.getNombre() + " con préstamos asociados: ");
			//Impresión Cuentas con Préstamo Asociado
			Tablas.impresionCuentasCorriente(cuentasEnDeuda);

			//Atributo para validación entrada Cuenta_Compra
			boolean validacion_Cuenta_Compra = true;
			while (validacion_Cuenta_Compra) {
				System.out.print("Por favor, seleccione la cuenta a la cual quiere aplicar la compra de cartera: ");
				Cuenta_Compra = Integer.parseInt(sc.nextLine());

				if (Cuenta_Compra >= 1 && Cuenta_Compra < cuentasEnDeuda.size() + 1) {
					validacion_Cuenta_Compra = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo.");
				}
			}

			//Retornar información de la Cuenta
			System.out.println("Información de la cuenta: ");
			System.out.println("");
			System.out.println(cuentasEnDeuda.get(Cuenta_Compra - 1));

			System.out.println("");
			System.out.print("Confirme por favor si esta es la cuenta a la cual desea aplicar este mecanismo financiero (y/n): ");
			String ConfirmacionI = sc.nextLine();


			if (ConfirmacionI.equals("y") || ConfirmacionI.equals("Y")) {
				seleccion_Cuenta = false;
			}
			else if (ConfirmacionI.equals("n") || ConfirmacionI.equals("N")) {
				boolean validacion_ConfirmacionII = true;
				while (validacion_ConfirmacionII) {
					System.out.println("¿Qué desea hacer?"
							+ "\n1. Reelegir cuenta."
							+ "\n2. Salir de la funcionalidad.");
					int ConfirmacionII = Integer.parseInt(sc.nextLine());
					if (ConfirmacionII == 1) {
						validacion_ConfirmacionII = false;
					}
					else if(ConfirmacionII == 2) {
						return;
					}
					else {
						System.out.println("Entrada no válida, intente de nuevo.");
					}
				}

			}else {
				System.out.println("Entrada no válida");
			}
		}

		cuentasAux.remove(cuentasEnDeuda.get(Cuenta_Compra - 1));

		//Arreglo que almacena las cuentas capaces de recibir la deuda
		ArrayList<Corriente> cuentasCapacesDeuda = user.Capacidad_Endeudamiento(cuentasAux, cuentasEnDeuda.get(Cuenta_Compra - 1));
		Collections.sort(cuentasCapacesDeuda);
		//Arreglo que almacena las tasas de intereses aplicables con orden del arreglo anterior
		ArrayList<Double> tasacionCuentas = Banco.verificarTasasdeInteres(user, cuentasCapacesDeuda);
		
		cuentasAux.add(cuentasEnDeuda.get(Cuenta_Compra - 1));
		Collections.sort(cuentasAux);
		
		if (cuentasCapacesDeuda.size() == 0) {
			System.out.println("Ninguna de las cuentas Corriente que posees tiene la capacidad de recibir la deuda de la cuenta escogida.");
			return;
		}
		
		System.out.println("Las cuentas a su nombre que pueden recibir la deuda de la Cuenta escogida son: ");
		Tablas.impresionCuentasCorrienteInteres(cuentasCapacesDeuda, tasacionCuentas);

		//Atributo de validacion de la entrada Cuenta_Destino
		boolean validacion_Cuenta_Destino = true;
		//Atributo auxiliar que almacenará la cuenta destino de la deuda
		int Cuenta_Destino = 0;
		while (validacion_Cuenta_Destino) {
			System.out.print("Por favor escoga la cuenta destino de la deuda: ");
			Cuenta_Destino = Integer.parseInt(sc.nextLine());
			if (Cuenta_Destino >= 1 && Cuenta_Destino <= cuentasCapacesDeuda.size()) {
				validacion_Cuenta_Destino = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		double deuda = Cuenta.DineroaTenerDisponible(cuentasCapacesDeuda.get(Cuenta_Destino - 1), cuentasEnDeuda.get(Cuenta_Compra - 1).getDivisa());

		//Atributo de validacion de la entrada Periodicidad
		boolean validacion_Periodicidad = true;
		//Atributo auxiliar para almacenar decision de periodicidad
		int Periodicidad = 0;
		while (validacion_Periodicidad) {
			System.out.println("¿Desea mantener la periodicidad del pago de la deuda?"
					+ "\n1. Sí"
					+ "\n2. No");
			Periodicidad = Integer.parseInt(sc.nextLine());
			if (Periodicidad == 1 || Periodicidad == 2) {
				validacion_Periodicidad = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		//Atributo de la periodicidad
		Cuotas eleccion_periodicidad = Cuotas.C1;
		if (Periodicidad == 1) {
			System.out.println("Perfecto, la deuda mantendrá un plazo de pago a " + cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago() + ".");
			eleccion_periodicidad = cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago();
		}
		else if (Periodicidad == 2) {
			//Atributo de validacion de la seleccion de periodicidad
			boolean validacion_Seleccion_Periodicidad = true;
			int seleccion_periodicidad = 0;

			while (validacion_Seleccion_Periodicidad) {
				System.out.println("Por favor seleccione la nueva periodicidad de la Deuda: "
						+ "\n1. 1 Cuota"
						+ "\n2. 6 Cuotas"
						+ "\n3. 12 Cuotas"
						+ "\n4. 18 Cuotas"
						+ "\n5. 24 Cuotas"
						+ "\n6. 36 Cuotas"
						+ "\n7. 48 Cuotas");
				seleccion_periodicidad = Integer.parseInt(sc.nextLine());
				if (seleccion_periodicidad < 1 || seleccion_periodicidad > 7) {
					System.out.println("Entrada no válida, intente de nuevo");
				}
				else {
					validacion_Seleccion_Periodicidad = false;
				}
			}
			switch(seleccion_periodicidad) {
			case 1:
				eleccion_periodicidad = Cuotas.C1;
				System.out.println("Deuda establecida a: " + Cuotas.C1 + ".");
				break;
			case 2:
				eleccion_periodicidad = Cuotas.C6;
				System.out.println("Deuda establecida a: " + Cuotas.C6 + ".");
				break;
			case 3:
				eleccion_periodicidad = Cuotas.C12;
				System.out.println("Deuda establecida a: " + Cuotas.C12 + ".");
				break;
			case 4:
				eleccion_periodicidad = Cuotas.C18;
				System.out.println("Deuda establecida a: " + Cuotas.C18 + ".");
				break;
			case 5:
				eleccion_periodicidad = Cuotas.C24;
				System.out.println("Deuda establecida a: " + Cuotas.C24 + ".");
				break;
			case 6:
				eleccion_periodicidad = Cuotas.C36;
				System.out.println("Deuda establecida a: " + Cuotas.C36 + ".");
				break;
			case 7:
				eleccion_periodicidad = Cuotas.C48;
				System.out.println("Deuda establecida a: " + Cuotas.C48 + ".");
				break;
			}
		}

		Corriente vistaPrevia = Corriente.vistaPreviaMovimiento(cuentasCapacesDeuda.get(Cuenta_Destino - 1), eleccion_periodicidad, 
				deuda, tasacionCuentas.get(Cuenta_Destino - 1));
		
		int pagoPrimerMes = 1;
		
		if(eleccion_periodicidad.getCantidad_Cuotas() != 1) {
			boolean validacionPagoPrimerMes = true;
			while(validacionPagoPrimerMes) {
				System.out.println("¿Desea pagar intereses en el primer mes? Tenga en cuenta que de no hacerlo, en el segundo mes"
						+ " deberá pagar su valor correspondiente."
						+ "\n1. Sí"
						+ "\n2. No");
				pagoPrimerMes = Integer.parseInt(sc.nextLine());
				if (pagoPrimerMes == 1 || pagoPrimerMes == 2) {
					validacionPagoPrimerMes = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo");
				}
			}
		}
		
		double[] cuota;
		if (pagoPrimerMes == 1) {
			cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getCupo() - vistaPrevia.getDisponible());
			vistaPrevia.setPrimerMensualidad(true);
		}
		else {
			cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getCupo() - vistaPrevia.getDisponible(), 1);
			vistaPrevia.setPrimerMensualidad(false);
		}

		//Vista Previa de los resultados del cambio
		System.out.println("Vista previa de como quedaría la cuenta escogida para recibir la deuda: ");
		System.out.println();
		System.out.println(vistaPrevia);
		String cuotaMensual = Corriente.imprimirCuotaMensual(cuota);

		System.out.println("Primer Cuota: ");
		System.out.println(cuotaMensual + " " + vistaPrevia.getDivisa());
		
		int decisionCalculadora = 2;
		if(eleccion_periodicidad.getCantidad_Cuotas() != 1) {
			boolean validacionCalculadora = true;
			while(validacionCalculadora) {
				System.out.println("¿Desea un resumen completo de las cuotas a pagar?"
						+ "\n1. Sí"
						+ "\n2. No");
				decisionCalculadora = Integer.parseInt(sc.nextLine());
				if (decisionCalculadora == 1 || decisionCalculadora == 2) {
					validacionCalculadora = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo");
				}
			}
		}

		if(decisionCalculadora == 1) {
			double[][] cuotaCalculadora;
			if(vistaPrevia.getPrimerMensualidad()) {
				cuotaCalculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses());
			}
			else {
				cuotaCalculadora = Corriente.calculadoraCuotas(vistaPrevia.getPlazo_Pago(), vistaPrevia.getCupo() - vistaPrevia.getDisponible(), vistaPrevia.getIntereses(), true);
			}
			double[] infoAdicional = Corriente.informacionAdicionalCalculadora(cuotaCalculadora, vistaPrevia.getCupo() - vistaPrevia.getDisponible());
			Main.calculadoraCuotas(cuotaCalculadora, infoAdicional, vistaPrevia.getDivisa());
		}

		//Atributo de validacion de la entrada confirmacion Movimiento
		boolean validacion_vistaPrevia = true;
		//Atributo auxiliar para almacenar la confirmación del movimiento
		int confirmacionMovimiento = 0;
		while (validacion_vistaPrevia) {
			System.out.println("¿Desea confirmar la realización del movimiento?"
					+ "\n1. Sí"
					+ "\n2. No");
			confirmacionMovimiento = Integer.parseInt(sc.nextLine());
			if (confirmacionMovimiento == 1 || confirmacionMovimiento == 2) {
				validacion_vistaPrevia = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		switch(confirmacionMovimiento) {
		case 1:
			//Cambios para la cuenta origen
			cuentasEnDeuda.get(Cuenta_Compra - 1).setDisponible(cuentasEnDeuda.get(Cuenta_Compra - 1).getCupo());
			cuentasEnDeuda.get(Cuenta_Compra - 1).setPlazo_Pago(Cuotas.C1);
			
			//Cambios para la cuenta destino
			Cuenta.getCuentasTotales().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			user.getCuentasAsociadas().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			user.getCuentasCorrienteAsociadas().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			Corriente.getCuentasCorrienteTotales().remove(cuentasCapacesDeuda.get(Cuenta_Destino - 1));
			
			user.asociarCuenta(vistaPrevia);
			
			System.out.println("Compra de cartera realizada con éxito");

			break;
		case 2:
			Cuenta.getCuentasTotales().remove(vistaPrevia);
			Corriente.getCuentasCorrienteTotales().remove(vistaPrevia);
			vistaPrevia = null;
			System.out.println("Movimiento cancelado.");
			break;
		}

		return;

	}

	// CALCULADORA DE CUOTAS
	static void calculadoraCuotas() {
		System.out.println("Esta funcionalidad te permitirá observar de antemano la información completa del pago de una deuda a un plazo de pago determinado.");
		System.out.println("Por favor ingrese la cantidad de la deuda: ");
		double deuda = Double.parseDouble(sc.nextLine());

		Cuotas cuotas = null;
		//Atributo de validacion de la seleccion de periodicidad
		boolean validacion_CantidadCuotas = true;
		int cantidadCuotas = 0;
		while (validacion_CantidadCuotas) {
			System.out.println("¿A cuantás cuotas tiene planeado pagar dicha deuda? "
					+ "\n1. 1 Cuota"
					+ "\n2. 6 Cuotas"
					+ "\n3. 12 Cuotas"
					+ "\n4. 18 Cuotas"
					+ "\n5. 24 Cuotas"
					+ "\n6. 36 Cuotas"
					+ "\n7. 48 Cuotas");
			cantidadCuotas = Integer.parseInt(sc.nextLine());
			if (cantidadCuotas < 1 || cantidadCuotas > 7) {
				System.out.println("Entrada no válida, intente de nuevo");
			}
			else {
				validacion_CantidadCuotas = false;
			}
		}
		switch(cantidadCuotas) {
		case 1:
			cuotas = Cuotas.C1;
			break;
		case 2:
			cuotas = Cuotas.C6;
			break;
		case 3:
			cuotas = Cuotas.C12;
			break;
		case 4:
			cuotas = Cuotas.C18;
			break;
		case 5:
			cuotas = Cuotas.C24;
			break;
		case 6:
			cuotas = Cuotas.C36;
			break;
		case 7:
			cuotas = Cuotas.C48;
			break;
		}

		System.out.println("¿Qué tasa efectiva anual te cobra tu entidad bancaria afiliada de intereses?");
		double intereses = Double.parseDouble(sc.nextLine());

		double[][] calculoCuotas = null;

		boolean validacion_ConfirmacionI = true;
		while(validacion_ConfirmacionI) {
			System.out.println("Finalmente... ¿deseas pagar en tu primer mes intereses? (y/n)");
			System.out.println("Ten en cuenta que de no hacerlo, deberás pagar el valor por dicho concepto en el segundo mes");
			String ConfirmacionI = sc.nextLine();

			if (ConfirmacionI.equals("y") || ConfirmacionI.equals("Y")) {
				validacion_ConfirmacionI = false;
				calculoCuotas = Corriente.calculadoraCuotas(cuotas, deuda, intereses);
			}
			else if (ConfirmacionI.equals("n") || ConfirmacionI.equals("N")) {
				validacion_ConfirmacionI = false;
				calculoCuotas = Corriente.calculadoraCuotas(cuotas, deuda, intereses, true);
			}
			else {
				System.out.println("Entrada no válida");
			}
		}

		double[] infoAdicional = Corriente.informacionAdicionalCalculadora(calculoCuotas, deuda);

		Main.calculadoraCuotas(calculoCuotas, infoAdicional);
	}

	// SOBRECARGA CALCULADORA DE CUOTAS (IMPRESIÓN)
	static void calculadoraCuotas(double[][] cuotaCalculadora, double[] infoAdicional) {
		System.out.println("Total pagado: $" + Corriente.redondeoDecimal(infoAdicional[0], 2));
		System.out.println("Intereses pagados: $" + Corriente.redondeoDecimal(infoAdicional[1], 2));
		System.out.println("Mes 1:");
		System.out.println("\tDeuda: $" + Corriente.redondeoDecimal(infoAdicional[2], 2));
		System.out.println("\tIntereses: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][0], 2));
		System.out.println("\tCuota a pagar: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][1], 2));
		System.out.println("\tSaldo restante: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][2], 2));

		for (int i = 2; i <= cuotaCalculadora.length; i++) {
			System.out.println("Mes " + i + ":");
			System.out.println("\tDeuda: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 2][2], 2));
			System.out.println("\tIntereses: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][0], 2));
			System.out.println("\tCuota a pagar: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][1], 2));
			System.out.println("\tSaldo restante: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][2], 2));
		}
	}

	// SOBRECARGA CALCULADORA DE CUOTAS (IMPRESIÓN CON DIVISA)
	static void calculadoraCuotas(double[][] cuotaCalculadora, double[] infoAdicional, Divisas divisa) {
		System.out.println("Total pagado: $" + Corriente.redondeoDecimal(infoAdicional[0], 2) + " " + divisa);
		System.out.println("Intereses pagados: $" + Corriente.redondeoDecimal(infoAdicional[1], 2) + " " + divisa);
		System.out.println("Mes 1:");
		System.out.println("\tDeuda: $" + Corriente.redondeoDecimal(infoAdicional[2], 2) + " " + divisa);
		System.out.println("\tIntereses: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][0], 2) + " " + divisa);
		System.out.println("\tCuota a pagar: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][1], 2) + " " + divisa);
		System.out.println("\tSaldo restante: $" + Corriente.redondeoDecimal(cuotaCalculadora[0][2], 2) + " " + divisa);

		for (int i = 2; i <= cuotaCalculadora.length; i++) {
			System.out.println("Mes " + i + ":");
			System.out.println("\tDeuda: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 2][2], 2) + " " + divisa);
			System.out.println("\tIntereses: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][0], 2) + " " + divisa);
			System.out.println("\tCuota a pagar: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][1], 2) + " " + divisa);
			System.out.println("\tSaldo restante: $" + Corriente.redondeoDecimal(cuotaCalculadora[i - 1][2], 2) + " " + divisa);
		}
	}

	// CREAR USUARIO DENTRO EN EL MAIN
	static void crearUsuario() {
		//Creación de un Usuario
		System.out.println("Para crear un usuario nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre: ");
		String nombreUsuario = sc.nextLine();

		System.out.print("Correo electrónico: ");
		String correoElectronico = sc.nextLine();

		/*System.out.println("Seleccione su nivel de suscripción: ");
		for(int i = 1; i < Suscripcion.getNivelesSuscripcion().size() + 1; i++) {
			System.out.println(i + ". " + Suscripcion.getNivelesSuscripcion().get(i-1));
		}

		int suscripcion_op = sc.nextInt();
		Suscripcion suscripcion = Suscripcion.getNivelesSuscripcion().get(suscripcion_op);*/

		System.out.print("Contraseña: ");
		String contrasena = sc.nextLine();

		System.out.print("Verificar contraseña: ");
		String verificacionContrasena = sc.nextLine();

		if(!(verificacionContrasena.equals(contrasena))) {
			System.out.println("Verifique que la contraseña esté correctamente ingresada. Inténtelo de nuevo");
			System.out.print("Contraseña: ");
			contrasena = sc.nextLine();

			System.out.print("Verificar contraseña: ");
			verificacionContrasena = sc.nextLine();
		}

		//user = Usuario.crearUsuario(nombreUsuario, correoElectronico, contrasena, suscripcion);
		System.out.println("Usuario creado con éxito");
		seguir = 0;
		user = new Usuario(nombreUsuario, correoElectronico, contrasena);
	}

	// INGRESAR USUARIO DENTRO EN EL MAIN
	static void ingresarUsuario() throws ParseException, CloneNotSupportedException {
		System.out.print("Ingrese nombre de usuario o correo electrónico: ");
		String usuario = sc.nextLine();
		System.out.print("Ingrese su contraseña: ");
		String contraseña = sc.nextLine();

		Object u = Usuario.verificarCredenciales(usuario, contraseña);
		if (u instanceof Usuario) {
			sesioniniciada = 1;
			seguir = 0;
			user = (Usuario) u;
		} else {
			System.out.println("No se encuentra un usuario con estos datos. Inténtalo de nuevo.");
			seguir = 0;
			Main.bienvenidaApp();
		}
	}

	// INVERTIR SALDO DE CUENTA EN EL MAIN - FUNCIONALIDAD DE SUSCRIPCIONES DE USUARIOS
	static void invertirSaldoUsuario(Usuario user) {
		if(user.getCuentasAhorrosAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas de ahorro. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta de ahorro asociada al usuario para realizar la inversión de saldo: ");
			int opcion_cuenta = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta < 1 && opcion_cuenta > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo:");
					opcion_cuenta = Integer.parseInt(sc.nextLine());
				}else if(user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1).getSaldo() <= 0){
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					System.out.println("Para invertir saldo debemos comprobar que el saldo de la cuenta sea diferente de cero." + " El saldo para la cuenta " + c.getNombre() + " es de " + c.getSaldo());
					System.out.println("Volviendo al menú anterior.");
					break;	
				}else {
					System.out.println("");
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					Object inversion = c.invertirSaldo();
					if(inversion instanceof Movimientos) {
						user.asociarMovimiento((Movimientos) inversion);
						System.out.println("La inversión de saldo ha sido exitosa: ");
						System.out.println(inversion);
						System.out.println("");
						System.out.println(user.verificarContadorMovimientos());
						break;
					}else {
						System.out.println(inversion);
						break;
					}
				}
			}		
		}
	}

	// CONSIGNAR COMO USUARIO EN SALDO DE CUENTA EN EL MAIN
	static void consignarSaldoCuenta(Usuario user) {
		if(user.getCuentasAhorrosAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas de ahorro. Volviendo al menú anterior");
			seccion = 1;
		}else {
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta de ahorro asociada al usuario para realizar la consignación de saldo: ");
			int opcion_cuenta = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta < 1 && opcion_cuenta > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo:");
					opcion_cuenta = Integer.parseInt(sc.nextLine());
				}else {
					System.out.println("");
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					System.out.print("Ingrese el monto de su consignación de saldo: ");
					double saldo_consignar = Double.parseDouble(sc.nextLine()); 
					while(true) {
						if(saldo_consignar <= 0) {
							System.out.print("El monto de su consignación de saldo debe ser mayor que cero. Inténtelo de nuevo: ");
							saldo_consignar = Double.parseDouble(sc.nextLine()); 
						}else {
							break;
						}
					}
					Object saldo_movimiento = Movimientos.crearMovimiento(c, saldo_consignar, Categoria.OTROS, new Date());
					if(saldo_movimiento instanceof Movimientos) {
						System.out.println("");
						user.asociarMovimiento((Movimientos) saldo_movimiento);
						System.out.println("La consignación de saldo ha sido exitosa: ");
						System.out.println(saldo_movimiento);
						System.out.println("");
						System.out.println(user.verificarContadorMovimientos());
						break;
					}else {
						System.out.println(saldo_movimiento);
						break;
					}
				}
			}		
		}
	}	

	// VER CATEGORIAS EN EL MAIN
	static void verCategorias() {
		for(int i = 1; i < Categoria.getCategorias().size() + 1; i++) {
			System.out.println(i + ". " + Categoria.getCategorias().get(i - 1));
		}
	}

	// TRANSFERIR SALDO ENTRE CUENTAS POR USUARIO EN EL MAIN
	static void transferirSaldoCuentasUsuario() {
		System.out.println("Por favor, elija el destino de la transferencia: ");
		System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
		System.out.println("1. Cuenta externa");
		System.out.println("2. Cuenta propia");
		int decision_saldo = Integer.parseInt(sc.nextLine());
		switch(decision_saldo) {
		case 1:
			System.out.println("");
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta origen asociada al usuario desde donde deseas transferir saldo: ");
			int opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta_origen < 1 || opcion_cuenta_origen > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo: ");
					opcion_cuenta_origen = Integer.parseInt(sc.nextLine());	
				}else if(user.getCuentasAhorrosAsociadas().get(opcion_cuenta_origen - 1).getSaldo() == 0) {
					System.out.println("La cuenta seleccionada tiene saldo cero");
					System.out.print("¿Desea volver al menú anterior? (Y/N): ");
					String confirmacion = sc.nextLine();
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						System.out.println("Volviendo al menú anterior");
						seccion = 1;
						break;
					}else {
						System.out.print("Inténtelo de nuevo: ");
						opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
					}
				}else {
					System.out.println("");
					Ahorros c_origen = user.getCuentasAhorrosAsociadas().get(opcion_cuenta_origen - 1);
					Ahorros.getCuentasAhorroTotales().removeAll(user.getCuentasAhorrosAsociadas());
					Main.verCuentasAhorroTotales();
					System.out.print("Seleccione el número de cuenta destino donde deseas transferir saldo: ");
					int opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
					Ahorros c_destino = null;
					while(true) {
						if(opcion_cuenta_destino < 1 || opcion_cuenta_destino > Ahorros.getCuentasAhorroTotales().size()) {
							System.out.print("Inténtelo de nuevo. Seleccione el número de cuenta destino donde deseas transferir saldo: ");
							opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
						}else {
							c_destino = Ahorros.getCuentasAhorroTotales().get(opcion_cuenta_destino - 1);
							break;
						}
					}
					System.out.println("");
					System.out.print("Inserte el monto de la transferencia: ");
					double monto_transferencia = Double.parseDouble(sc.nextLine());
					System.out.println("");
					Main.verCategorias();
					System.out.println("");
					System.out.print("Seleccione el número de categoría para la transferencia: ");
					int categoria_transferencia_op = Integer.parseInt(sc.nextLine());
					while(true) {
						if(categoria_transferencia_op < 1 || categoria_transferencia_op > Categoria.getCategorias().size()) {
							System.out.print("Inténtelo de nuevo. Seleccione el número de categoría para la transferencia: ");
							categoria_transferencia_op = Integer.parseInt(sc.nextLine());
						}else {
							break;
						}
					}
					Categoria categoria_transferencia = Categoria.getCategorias().get(categoria_transferencia_op - 1);
					Object modificar_saldo = Movimientos.modificarSaldo(c_origen, c_destino, monto_transferencia, user, categoria_transferencia);
					Ahorros.getCuentasAhorroTotales().addAll(user.getCuentasAhorrosAsociadas());
					if(modificar_saldo instanceof Movimientos) {
						System.out.println((Movimientos) modificar_saldo);
						System.out.println(user.verificarContadorMovimientos());
						break;
					}else {
						System.out.println(modificar_saldo);
						break;	
					}	
				}
			}break;
		case 2:
			if(user.getCuentasAhorrosAsociadas().size() < 2) {
				System.out.println("Debe asociar más de una (1) cuenta de ahorros. Volviendo al menú anterior.");
				break;
			}else {
				System.out.println("");
				Main.verCuentasAhorroAsociadas();
				System.out.print("Seleccione el número de cuenta origen asociada al usuario desde donde deseas transferir saldo: ");
				opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
				while(true) {
					if(opcion_cuenta_origen < 1 || opcion_cuenta_origen > user.getCuentasAhorrosAsociadas().size()) {
						System.out.print("Inténtelo de nuevo. Seleccione el número de cuenta origen asociada al usuario desde donde deseas transferir saldo: ");
						opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
					}else {
						break;
					}
				}

				ArrayList<Ahorros> cuentas = user.getCuentasAhorrosAsociadas();
				Ahorros c_origen = cuentas.get(opcion_cuenta_origen - 1);
				while(true) {
					c_origen = cuentas.get(opcion_cuenta_origen - 1);
					if(c_origen.getSaldo() == 0) {
						System.out.println("");
						System.out.println("La cuenta seleccionada tiene saldo cero");
						System.out.println("");
						System.out.print("¿Desea volver al menú anterior? (Y/N): ");
						String confirmacion = sc.nextLine();
						if(confirmacion.equals("Y") || confirmacion.equals("y")) {
							System.out.println("Volviendo al menú anterior");
							seccion = 1;
							break;
						}else {
							System.out.println("");
							System.out.print("Inténtelo de nuevo: ");
							opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
						}
					} else {
						System.out.println("");
						cuentas.remove(opcion_cuenta_origen - 1);
						Main.verCuentasAhorroAsociadas();
						System.out.print("A cual de sus cuentas desea transferir su saldo: ");
						int opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
						while(true) {
							if(opcion_cuenta_destino < 1 || opcion_cuenta_destino > cuentas.size()) {
								System.out.print("Inténtelo de nuevo. Seleccione el número de cuenta destino donde deseas transferir saldo: ");
								opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
							}else {
								break;
							}				
						}
						Ahorros c_destino = cuentas.get(opcion_cuenta_destino - 1);
						System.out.println("");
						System.out.print("Inserte el monto de la transferencia: ");
						double monto_transferencia = Double.parseDouble(sc.nextLine());
						System.out.println("");
						Main.verCategorias();
						System.out.println("");
						System.out.print("Seleccione el número de categoría para la transferencia: ");
						int categoria_transferencia_op = Integer.parseInt(sc.nextLine());
						Categoria categoria_transferencia = Categoria.getCategorias().get(categoria_transferencia_op - 1);
						Object modificar_saldo = Movimientos.modificarSaldo(c_origen, c_destino, monto_transferencia, user, categoria_transferencia);	
						cuentas.add(opcion_cuenta_origen - 1, c_origen);
						if(modificar_saldo instanceof Movimientos) {
							System.out.println((Movimientos) modificar_saldo);
							System.out.println(user.verificarContadorMovimientos());
							break;
						}else {
							System.out.println(modificar_saldo);
							break;	
						}

					}
				}break;
			}
		default:
			System.out.println("Opción no válida. Inténtelo de nuevo");
			System.out.println("Por favor, elija el destino de la transferencia: ");
			System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
			System.out.println("1. Cuenta externa");
			System.out.println("2. Cuenta propia");
			decision_saldo = Integer.parseInt(sc.nextLine());
		}

	}			

	// CREAR BANCO DENTRO EN EL MAIN
	static void crearBanco() {
		System.out.println("");
		//Creación de un Banco
		System.out.println("Para crear un banco nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre del banco: ");
		String nombreBanco = sc.nextLine();

		System.out.print("Comisión que va a cobrar el banco (En formato double): ");
		Double comision = Double.parseDouble(sc.nextLine());

		System.out.print("Cantidad de dinero que puede prestar el banco (En formato double): ");
		Double prestamo = Double.parseDouble(sc.nextLine());
		
		System.out.println("Una divisa central del banco. Seleccione una. La lista de divisas permitidas son: ");
		for(int i = 1; i < Divisas.getDivisas().size() + 1; i++) {
			System.out.println(i + ". " + Divisas.getDivisas().get(i-1).name());
		}
		int divisa_op = Integer.parseInt(sc.nextLine());
		Divisas divisa = Divisas.getDivisas().get(divisa_op - 1);
		
		System.out.println("Unas tasas de cambio entre divisas.");
		ArrayList<String> dic = new ArrayList<String>();
		ArrayList<Double> cionario = new ArrayList<Double>();
		
		while (true) { 
			System.out.println("Escoja la tasa de origen: ");
			double j=1;
			for (Divisas div : Divisas.getDivisas()) {
				System.out.println(j + ". " + div.name());
				j = j+1;
			}
			System.out.println((j) + ". No tengo más tasas de cambio por agregar");
			int div_op = Integer.parseInt(sc.nextLine());
			if (div_op == j) {
				break;
			}
			String div = Divisas.getDivisas().get(div_op-1).name();
			ArrayList<Divisas> sinEscoger = Divisas.getDivisas();
			sinEscoger.remove(div_op-1);
			j=1;
			System.out.println("Escoja la tasa de destino: ");
			for (Divisas isa : sinEscoger) {
				System.out.println(j + ". " + isa.name());
				j = j+1;
			}
			int isa_op = Integer.parseInt(sc.nextLine());
			String isa = sinEscoger.get(isa_op-1).name();
			if (dic.contains(div+isa)){
				System.out.println("Este tipo de cambio ya fue ingresado ya fue ingresado.");
			}
			else {
				System.out.print("¿Qué valor va a tener la tasa?: ");
				double tasa = Double.parseDouble(sc.nextLine());
				dic.add(div+isa);
				cionario.add(tasa);
				System.out.println("Se agrega la tasa " + (div+isa) +  " con valor de " + tasa + ".");
			}
			}
		for (String clave : dic) {
			System.out.println(clave);
		}
		for (double valor : cionario) {
			System.out.println(valor);
		}
		
		System.out.print("¿Desea asignar un cupo base específico?: (Y/N): ");
		String cupo_op = sc.nextLine();
		double cupo = 1000000;
		if (cupo_op.equals("y") && cupo_op.equalsIgnoreCase("y")) {
			System.out.print("¿");
			System.out.print("¿A qué valor? (Formato double): ");
			cupo = Double.parseDouble(sc.nextLine());
		}
		
		System.out.print("¿Desea asignar un multiplicador específico? (Y/N): ");
		String multi_op = sc.nextLine();
		int multi = 2; 
		if (multi_op.equals("y") && multi_op.equalsIgnoreCase("y")) {
			System.out.print("¿A qué valor? (Formato double): ");
			multi = Integer.parseInt(sc.nextLine());
		} 
		
		System.out.print("¿Desea asignar un desc_suscripcion base específico?: (Y/N): ");
		String dsus_op = sc.nextLine();
		double dsus = 0.2;
		if (dsus_op.equals("y") && dsus_op.equalsIgnoreCase("y")) {
			System.out.print("¿A qué valor? (Formato double): ");
			dsus = Double.parseDouble(sc.nextLine());
		}
		
		System.out.print("¿Desea asignar un desc_movimientos_porcentaje específico?: (Y/N): ");
		String dmovp_op = sc.nextLine();
		double dmovp = 0.2;
		if (dmovp_op.equals("y") && dmovp_op.equalsIgnoreCase("y")) {
			System.out.print("¿A qué valor? (Formato double): ");
			dmovp = Double.parseDouble(sc.nextLine());
		}
		
		System.out.print("¿Desea asignar un desc_movimientos_cantidad específico?: (Y/N): ");
		String dmovc_op = sc.nextLine();
		int dmovc = 5;
		if (dmovc_op.equals("y") && dmovc_op.equalsIgnoreCase("y")) {
			System.out.print("¿A qué valor? (Formato int): ");
			dmovc = Integer.parseInt(sc.nextLine());
		}

		while(true) {
			if(Estado.getEstadosTotales().size() == 0) {
				System.out.println("No hay estados registrados en el sistema. Primero debes crear un estado.");
				Main.crearEstado();
			}else {
				System.out.println("");
				System.out.println("Seleccione un estado para la operación del banco. La lista de Estados disponibles son: ");
				Tablas.impresionEstados(Estado.getEstadosTotales());
				int estado_op = Integer.parseInt(sc.nextLine());
				Estado estado_banco = Estado.getEstadosTotales().get(estado_op - 1);
				seguir = 0;
				new Banco(nombreBanco, comision, estado_banco, prestamo, divisa, dic, cionario, cupo, multi, dsus, dmovp, dmovc);	
				System.out.println("Banco creado con éxito");
				break;
			}
		}
	}

	// CREAR ESTADO DENTRO EN EL MAIN
	static void crearEstado() {
		System.out.println("");
		//Creación de un Estado
		System.out.println("Para crear un estado nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre del estado: ");
		String nombreEstado = sc.nextLine();

		System.out.print("Tasa de impuestos del estado (En formato double): ");
		Double tasaImpuestosEstado = Double.parseDouble(sc.nextLine());

		System.out.println("Seleccione una divisa para la operación del estado. La lista de divisas disponibles son: ");
		for(int i = 1; i < Divisas.getDivisas().size() + 1; i++) {
			System.out.println(i + ". " + Divisas.getDivisas().get(i-1));
		}

		int divisas_op = Integer.parseInt(sc.nextLine());
		Divisas divisa_estado = Divisas.getDivisas().get(divisas_op - 1);
		new Estado(nombreEstado, tasaImpuestosEstado, divisa_estado);
		System.out.println("Estado creado con éxito");
	}	

	// ACCESO ADMINISTRATIVO EN EL MAIN
	static void accesoAdministrativo() throws ParseException {
		if(contrasenaAdmin.equals("admin")) {
			for(Usuario u : Usuario.getUsuariosTotales()) {
				if(u.getNombre().equals("admin")) {
					user = u;
				}		
			}
		}else {
			System.out.print("Inserta la contraseña de administrador (Es admin): ");
			contrasenaAdmin = sc.nextLine();
			while(!contrasenaAdmin.equals("admin")) {
				System.out.print("Contraseña errada. Inténtelo de nuevo: ");
				contrasenaAdmin = sc.nextLine();	
			}
			System.out.println("");
			System.out.print("Ingresando al sistema como administrador...");
			System.out.print("");
			user = new Usuario("admin", "admin@admin.com", "admin", Suscripcion.DIAMANTE);
		}

		while(contrasenaAdmin.equals("admin")) {
			System.out.println("¿Qué deseas hacer?."
					+ "\n1. Crear Usuario"
					+ "\n2. Crear Banco"
					+ "\n3. Crear Estado"
					+ "\n4. Ver Usuarios"
					+ "\n5. Ver Bancos"
					+ "\n6. Ver Estados"
					+ "\n7. Ver Cuentas"
					+ "\n8. Ver Movimientos"
					+ "\n9. Ver Metas"
					+ "\n10. Iniciar Sesión"
					+ "\n11. Volver al menú anterior");
			
			int opcionAdmin = Integer.parseInt(sc.nextLine());
			if(opcionAdmin == 1) {
				String confirmacion = "Y";
				for(Usuario usu : Usuario.getUsuariosTotales()) {
					if(usu.getNombre().equals("Pepe Morales") && usu.getContrasena().equals("12345")) {
						System.out.println("El usuario por defecto ya ha sido creado.");
						confirmacion = "N";
						break;
					}
				}
				if(confirmacion.equals("Y")) {
					System.out.print("¿Deseas crear el usuario por defecto? (Y/N): ");
					confirmacion = sc.nextLine();
				}
				while(true) {
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						Usuario u = new Usuario();
						System.out.println("El usuario por defecto fue creado con éxito, éstas son las credenciales de ingreso: ");
						System.out.println("Nombre: " + u.getNombre());
						System.out.println("Contraseña: " + u.getContrasena());
						System.out.println("");
						break;
					}else if(confirmacion.equals("N") || confirmacion.equals("n")){
						Main.crearUsuario();
						break;
					}else {
						System.out.println("");
						System.out.print("Seleccione la opción correcta. ¿Deseas crear el usuario por defecto? (Y/N): ");
						confirmacion = sc.nextLine();
					}
				}			
			} else if(opcionAdmin == 2) {
				if(Estado.getEstadosTotales().size() == 0) {
					System.out.println("Para crear un banco debes crear un estado primero. Volviendo al menú anterior.");
				}else {
					String confirmacion = "Y";
					for(Banco ban : Banco.getBancosTotales()) {
						if(ban.getNombre().equals("Banco de Colombia") && ban.getEstadoAsociado().equals(Estado.getEstadosTotales().get(0))) {
							System.out.println("El banco por defecto ya ha sido creado.");
							confirmacion = "N";
							break;
						}
					}
					if(confirmacion.equals("Y")) {
						System.out.print("¿Deseas crear el banco por defecto? (Y/N): ");
						confirmacion = sc.nextLine();
					}	
					while(true) {
						if(confirmacion.equals("Y") || confirmacion.equals("y")) {
							Banco banco = new Banco();
							System.out.println("El banco por defecto fue creado con éxito, éstos son sus datos: ");
							System.out.println("Nombre: " + banco.getNombre());
							System.out.println("Comisión: " + banco.getComision());
							System.out.println("Prestamo: " + banco.getPrestamo());
							System.out.println("Estado asociado: " + banco.getEstadoAsociado().getNombre());
							System.out.println("");
							break;
						}else if(confirmacion.equals("N") || confirmacion.equals("n")){
							Main.crearBanco();
							break;
						}else {
							System.out.println("");
							System.out.print("Seleccione la opción correcta. ¿Deseas crear el banco por defecto? (Y/N): ");
							confirmacion = sc.nextLine();
						}
					}	
				}		
			} else if(opcionAdmin == 3) {
				String confirmacion = "Y";
				for(Estado est : Estado.getEstadosTotales()) {
					if(est.getNombre().equals("Colombia")) {
						System.out.println("El estado por defecto ya ha sido creado.");
						confirmacion = "N";
						break;
					}
				}
				if(confirmacion.equals("Y")) {
					System.out.print("¿Deseas crear el estado por defecto? (Y/N): ");
					confirmacion = sc.nextLine();
				}	
				while(true) {
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						Estado estado = new Estado();
						System.out.println("El estado por defecto fue creado con éxito, éstos son sus datos: ");
						System.out.println("Nombre: " + estado.getNombre());
						System.out.println("Tasa de impuestos: " + estado.getTasa_impuestos());
						System.out.println("Divisa: " + estado.getDivisa());
						System.out.println("");
						break;
					}else if(confirmacion.equals("N") || confirmacion.equals("n")){
						Main.crearEstado();
						break;
					}else {
						System.out.print("Seleccione la opción correcta. ¿Deseas crear el estado por defecto? (Y/N): ");
						confirmacion = sc.nextLine();
					}	
				}
			}
			else if(opcionAdmin == 4) {
				System.out.println("");
				Main.verUsuariosTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 5) {
				System.out.println("");
				Main.verBancosTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 6) {
				System.out.println("");
				Main.verEstadosTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 7) {
				System.out.println("");
				Main.verCuentasTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 8) {
				System.out.println("");
				Main.verMovimientosTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 9) {
				System.out.println("");
				Main.verMetasTotales();
				System.out.println("");
			}
			else if(opcionAdmin == 10) {
				sesioniniciada = 1;
				seguir = 0;
				break;		
			} else if(opcionAdmin == 11) {
				seguir = 0;
				break;			
			} else {
				System.out.println("");
				System.out.println("Entrada no valida");
				System.out.println("NOTA: Recuerde que debe ingresar el numeral de la opción que desea escoger.");
				System.out.println("¿Qué deseas hacer?."
						+ "\n1. Crear Usuario"
						+ "\n2. Crear Banco"
						+ "\n3. Crear Estado"
						+ "\n4. Iniciar Sesión"
						+ "\n5. Volver al menú anterior");
				opcionAdmin = Integer.parseInt(sc.nextLine());			
			}		
		}
	}	

	// CREAR CUENTA EN EL MAIN
	static void crearCuenta() {
		//PRIMERO DEBEMOS PEDIR LOS DATOS, COMO ALGUNOS SON OPCIONALES, SE PEDIRÁ QUE SI NO SE QUIERE INGRESAR
		//LA INFORMACIÓN SOLICITADA SE DA ENTER
		if(Banco.getBancosTotales().size() == 0) {
			System.out.println("Para crear una cuenta deben existir bancos. Volviendo al menú anterior");
			opcion = 0;
			seccion = 1;
		}else if(user.getCuentasAsociadas().size() >= user.getLimiteCuentas()){
			System.out.println("Debes verificar que no hayas alcanzado el máximo de cuentas que puede crear el usuario. El máximo de cuentas que puede asociar el usuario " + user.getNombre()  + " es " + user.getLimiteCuentas() + " y la cantidad de cuentas asociadas es " + user.getCuentasAsociadas().size());
			opcion = 0;
			seccion = 1;
		}else {
			System.out.println("Para crear una nueva cuenta, favor diligencie los siguientes datos: ");
			System.out.println("¿A que Banco desea afiliar su cuenta?: ");
			Main.verBancosTotales();
			int banco_op = Integer.parseInt(sc.nextLine());
			Banco banco_cuenta = Banco.getBancosTotales().get(banco_op - 1);
			user.asociarBanco(banco_cuenta);

			System.out.println("Cuál es el tipo que quiere seleccionar para su cuenta? La lista de Tipos disponibles son: "
					+ "\n1. Cuenta de Ahorros"
					+ "\n2. Cuenta Corriente");

			int tipo_op = Integer.parseInt(sc.nextLine());

			System.out.print("Clave de la cuenta (Recuerde que será una combinación de 4 números): ");
			int clave_cuenta = Integer.parseInt(sc.nextLine());
			while(Integer.toString(clave_cuenta).length() != 4) {
				System.out.print("");
				System.out.print("Recuerde que será una combinación de 4 números. Inténtelo de nuevo: ");
				clave_cuenta = Integer.parseInt(sc.nextLine());
			}

			System.out.println("");
			System.out.println("¿Cuál es la divisa que quiere seleccionar para su cuenta? La lista de Divisas disponibles son: ");
			for(int i = 1; i < Divisas.getDivisas().size() + 1; i++) {
				System.out.println(i + ". " + Divisas.getDivisas().get(i - 1));
			}
			int divisas_op = Integer.parseInt(sc.nextLine());
			Divisas divisas_cuenta = Divisas.getDivisas().get(divisas_op - 1);

			System.out.println("");
			System.out.print("Inserte el nombre de la cuenta: ");
			String nombre_cuenta = sc.nextLine();

			if (tipo_op == 1) {
				System.out.println(user.asociarCuenta(new Ahorros(banco_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta)));
			}else if (tipo_op == 2) {
				System.out.println(user.asociarCuenta(new Corriente(banco_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta)));
			}	
		}
	}

	// ELIMINAR CUENTA, SE REALIZA COMPROBACIÓN ENTRE CORRIENTE Y AHORROS
	static void eliminarCuenta(Cuenta cuenta) throws CloneNotSupportedException {
		if(cuenta instanceof Ahorros) {
			if (((Ahorros) cuenta).getSaldo() != 0.0d) {
				System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
				System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
				System.out.println("1. Cuenta externa");
				System.out.println("2. Cuenta propia");
				int decision_saldo = Integer.parseInt(sc.nextLine());
				switch(decision_saldo) {
				case 1:
					System.out.println("Ingrese los datos de la cuenta a la cual desea transferir su saldo. Recuerde que debe ser una cuenta de ahorros.");
					System.out.print("Id de la cuenta destino: ");
					int destino = Integer.parseInt(sc.nextLine()); 
					Cuenta dest = null;
					while(true) {
						for(int i = 0; i < Cuenta.getCuentasTotales().size(); i++) {
							dest = Cuenta.getCuentasTotales().get(i);
							if(dest.getId() == destino && dest instanceof Ahorros && dest.getId() != cuenta.getId()) {
								System.out.println(new Movimientos((Ahorros) dest, ((Ahorros) cuenta).getSaldo(), Categoria.OTROS, new Date()));
								Cuenta.getCuentasTotales().remove(cuenta);
								user.getCuentasAsociadas().remove(cuenta);
								user.getCuentasAhorrosAsociadas().remove(cuenta);
								Ahorros.getCuentasAhorroTotales().remove(cuenta);
								for(Movimientos m : Movimientos.getMovimientosTotales()) {
									if(m.getOrigen() != null && m.getOrigen().equals(cuenta)) {
										m.setOrigen(null);
									} if(m.getDestino() != null && m.getDestino().equals(cuenta)) {
										m.setDestino(null);
									}
								}
								cuenta = null;
								break;
							}
						} 
						if(cuenta == null || dest == null) {
							break;
						}else {
							if(dest.getId() == cuenta.getId()) {
								System.out.println("Recuerde que la cuenta debe ser de ahorros debe ser diferente. Inténtelo de nuevo.");
								System.out.print("Id de la cuenta destino: ");
								destino = Integer.parseInt(sc.nextLine());
							}else{
								System.out.println("Recuerde que la cuenta debe ser de ahorros. Inténtelo de nuevo.");
								System.out.print("Id de la cuenta destino: ");
								destino = Integer.parseInt(sc.nextLine()); 
							}
						}	
					}break;
				case 2:
					if(cuenta.getTitular().getCuentasAhorrosAsociadas().size() < 2) {
						System.out.println("Debe asociar más de una (1) cuenta de ahorros. Volviendo al menú anterior.");
						break;
					}else {
						ArrayList<Ahorros> cuentas = cuenta.getTitular().getCuentasAhorrosAsociadas();
						for (int i = 1; i == cuentas.size() + 1; i++) {
							System.out.println(i + ". " + cuentas.get(i - 1).getNombre());
						}
						System.out.print("A cual de sus cuentas desea transferir su saldo:");
						int decision_cuenta = Integer.parseInt(sc.nextLine());
						Movimientos.crearMovimiento((Ahorros) cuenta, (Ahorros) cuentas.get(decision_cuenta - 1), ((Ahorros) cuenta).getSaldo(), Categoria.OTROS, new Date());
						Cuenta.getCuentasTotales().remove(cuenta);
						user.getCuentasAsociadas().remove(cuenta);
						user.getCuentasAhorrosAsociadas().remove(cuenta);
						Ahorros.getCuentasAhorroTotales().remove(cuenta);
						cuenta = null;	
					}

				default:
					System.out.println("Opción no válida. Inténtelo de nuevo");
					System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
					System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
					System.out.println("1. Cuenta externa");
					System.out.println("2. Cuenta propia");
					decision_saldo = Integer.parseInt(sc.nextLine());
				}
			}else {
				Cuenta.getCuentasTotales().remove(cuenta);
				user.getCuentasAsociadas().remove(cuenta);
				user.getCuentasAhorrosAsociadas().remove(cuenta);
				Ahorros.getCuentasAhorroTotales().remove(cuenta);			
			}
		}else {
			if(((Corriente) cuenta).getDisponible().compareTo(((Corriente) cuenta).getCupo()) != 0){
				System.out.print("Tienes deudas pendientes. ¿Deseas pagarlas? (Y/N): ");
				String confirmacion = sc.nextLine();
				if(confirmacion.equals("Y") || confirmacion.equals("y")) {
					boolean validacion = Main.compraCartera(((Corriente) cuenta));
					if (validacion) {
						Cuenta.getCuentasTotales().remove(cuenta);
						user.getCuentasAsociadas().remove(cuenta);
						user.getCuentasCorrienteAsociadas().remove(cuenta);
						Corriente.getCuentasCorrienteTotales().remove(cuenta);
						cuenta = null;
					}
					else {
						System.out.println("Debes pagar las deudas para eliminar la cuenta.");
					}

				}else {
					System.out.println("Debes pagar las deudas para eliminar la cuenta.");
				}

			}else {
				Cuenta.getCuentasTotales().remove(cuenta);
				user.getCuentasAsociadas().remove(cuenta);
				user.getCuentasCorrienteAsociadas().remove(cuenta);
				Corriente.getCuentasCorrienteTotales().remove(cuenta);
				cuenta = null;	
			}
		}
	}

	// ELIMINAR CUENTA EN EL MAIN
	static void eliminarCuenta() throws CloneNotSupportedException {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de cuentas creadas por el usuario " + user.getNombre() + " son: ");
			Collections.sort(user.getCuentasAsociadas());
			Main.verCuentasAsociadas();
			System.out.println("");
			System.out.print("Inserte el numero de la cuenta que desea eliminar: ");
			int cuentaOp = Integer.parseInt(sc.nextLine());
			while(true) {
				if(cuentaOp < 1 || cuentaOp > user.getCuentasAsociadas().size()) {
					System.out.print("Inténtelo de nuevo. Inserte el numero de la cuenta que desea eliminar: ");
					cuentaOp = Integer.parseInt(sc.nextLine());
					continue;
				}else {
					for(int i = 0; i < user.getCuentasAsociadas().size(); i++) {
						if(user.getCuentasAsociadas().get(cuentaOp - 1) == user.getCuentasAsociadas().get(i)) {
							System.out.print("Inserte la contraseña de la cuenta: ");
							int claveCuenta = Integer.parseInt(sc.nextLine());
							while(true) {
								if(user.getCuentasAsociadas().get(i).getClave() == claveCuenta) {
									Main.eliminarCuenta(user.getCuentasAsociadas().get(i));
									System.gc();
									break;
								}else {
									while(true) {
										System.out.print("Contraseña errada. ¿Desea intentarlo de nuevo? (Y/N): ");
										String confirmacion = sc.nextLine();
										if(confirmacion.equals("Y") || confirmacion.equals("y")) {
											System.out.print("Inserte la contraseña de nuevo: ");
											claveCuenta = Integer.parseInt(sc.nextLine());
											break;
										}
										else if(confirmacion.equals("N") || confirmacion.equals("n")){
											System.out.println("Volviendo al menú anterior.");
											return;
										}
										else {
											System.out.println("Entrada no válida.");
										}
									}
									
								}	
							}
						}	
					} break;
				}
			}
			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Desea crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("y") || confirmacion.equals("Y")){
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}
		}
	}

	// VER CUENTAS DE AHORRO ASOCIADAS AL USER EN EL MAIN
	static void verCuentasAhorroAsociadas() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de Cuentas de ahorro creadas por el Usuario " + user.getNombre() + " son: ");
			Collections.sort(user.getCuentasAhorrosAsociadas());
			Tablas.impresionCuentasAhorros(user.getCuentasAhorrosAsociadas());

			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				seccion = 1;
			}	
		}
	}
	
	// VER CUENTAS CORRIENTE ASOCIADAS AL USER EN EL MAIN
	static void verCuentasCorrienteAsociadas() {
			//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
			if(user.getCuentasAsociadas().size() > 0) {
				System.out.println("La lista de Cuentas Corriente creadas por el Usuario " + user.getNombre() + " son: ");
				Collections.sort(user.getCuentasCorrienteAsociadas());
				Tablas.impresionCuentasCorriente(user.getCuentasCorrienteAsociadas());

				//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
			}else {
				System.out.print("No hay cuentas creadas para este usuario. ¿Deseas crear una? (Y/N): ");
				String confirmacion = sc.nextLine();
				if(confirmacion.equals("Y") || confirmacion.equals("y")) {
					Main.crearCuenta();
				}else {
					System.out.println("Volviendo al menú anterior");
					seccion = 1;
				}	
			}
		}

	// VER CUENTAS DE AHORRO Y CORRIENTES DEL USUARIO EN EL MAIN
	static void verCuentasAsociadas() {
		
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de Cuentas creadas por el Usuario " + user.getNombre() + " son: ");
			Collections.sort(user.getCuentasAsociadas());
			if(user.getCuentasAhorrosAsociadas().size() != 0) {
				System.out.println("CUENTAS DE AHORROS");
				Tablas.impresionCuentasAhorros(user.getCuentasAhorrosAsociadas());
			}
			if(user.getCuentasCorrienteAsociadas().size() != 0) {
				System.out.println("CUENTAS CORRIENTE");
				Tablas.impresionCuentasCorriente(user.getCuentasCorrienteAsociadas());
			}

			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				seccion = 1;
			}	
		}
	}

	// VER BANCOS ASOCIADAS AL USER EN EL MAIN
	static void verBancosAsociados() {
		//SE VERIFICA QUE EXISTAN BANCOS ASOCIADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LOS BANCOS ASOCIADOS AL USUARIO
		if(user.getBancosAsociados().size() > 0) {
			System.out.println("La lista de Bancos asociados por el Usuario " + user.getNombre() + " son: ");
			Tablas.impresionBancos(user.getBancosAsociados());

			//SE IMPRIME QUE NO EXISTEN BANCOS ASOCIADOS, SE LE PREGUNTA AL USUARIO SI DESEA ASOCIAR UNO	
		}else {
			System.out.print("No hay bancos asociados para este usuario. ¿Deseas asociar uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.verBancosTotales();
				if(opcion == 0) {

				}else {
					System.out.println("");
					Main.verBancosTotales();
					System.out.print("Seleccione el número de banco para asociar: ");
					int opcion_banco = Integer.parseInt(sc.nextLine());
					while(true) {
						if(opcion_banco < 1 && opcion_banco > Banco.getBancosTotales().size()) {
							System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
							opcion_banco = Integer.parseInt(sc.nextLine());
						}else {
							System.out.println(user.asociarBanco(Banco.getBancosTotales().get(opcion_banco - 1)));
							break;
						}	
					}	
				}	
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 2;
			}	
		}
	}

	// VER USUARIOS TOTALES EN EL MAIN
	static void verUsuariosTotales() {
		//SE VERIFICA QUE EXISTAN USUARIOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS USUARIOS CREADOS
		if(Usuario.getUsuariosTotales().size() > 0) {
			System.out.println("Todos los usuarios son: ");
			Tablas.impresionUsuarios(Usuario.getUsuariosTotales());

			//SE IMPRIME QUE NO EXISTEN USUARIOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay usuarios creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearUsuario();
				System.out.println("1. Usuario con nombre " + user.getNombre() + " creado");
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}

	// COMPROBAR SUSCRIPCION DE USUARIO EN EL MAIN - FUNCIONALIDAD DE SUSCRIPCIONES DE USUARIOS
	static void modificarSuscripcionUsuario() {
		if(user.getBancosAsociados().size() == 0) {
			System.out.println("Primero debes asociar bancos. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verBancosAsociados();
			System.out.print("Seleccione el número de banco asociado al usuario para comprobar suscripción: ");
			int opcion_banco = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_banco < 1 || opcion_banco > user.getBancosAsociados().size()) {
					System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
					opcion_banco = Integer.parseInt(sc.nextLine());
				}else {
					System.out.println("");
					System.out.println(Banco.getBancosTotales().get(opcion_banco - 1).comprobarSuscripción(user));
					break;
				}
			}
			System.out.print("¿Desea cambiar su nivel de suscripción? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				ArrayList<Suscripcion> listaSuscripcion = new ArrayList<Suscripcion>();
				listaSuscripcion.add(Suscripcion.DIAMANTE);
				listaSuscripcion.add(Suscripcion.ORO);
				listaSuscripcion.add(Suscripcion.PLATA);
				listaSuscripcion.add(Suscripcion.BRONCE);	
				int i = 1;
				for(Suscripcion suscripcion : listaSuscripcion) {
					if(suscripcion.equals(user.getSuscripcion())) {
					}else {
						System.out.println(i + ". " + suscripcion.name());
						i++;
					}
				}
				System.out.print("Seleccione el número de suscripción: ");
				int opcion_suscripcion = Integer.parseInt(sc.nextLine());
				while(true) {
					if(opcion_suscripcion < 1 || opcion_suscripcion > listaSuscripcion.size() - 1) {
						System.out.print("Debes seleccionar un nivel de suscripción válido. Inténtalo de nuevo:");
						opcion_suscripcion = Integer.parseInt(sc.nextLine());						
					}else {
						System.out.println("");
						if(listaSuscripcion.get(opcion_suscripcion - 1).getLimiteCuentas() < user.getCuentasAsociadas().size()) {
							System.out.println("El nivel de suscripción que escogiste tiene un limite de cuentas para asociar de " + listaSuscripcion.get(opcion_suscripcion - 1).getLimiteCuentas() + " y el número de cuentas que tienes asociadas actualmente es de " + user.getCuentasAsociadas().size() + ".");
							System.out.println("Debes eliminar cuentas para escoger este nivel de suscripción.");
							System.out.println("Volviendo al menú anterior.");
						}else {
							user.setSuscripcion(listaSuscripcion.get(opcion_suscripcion - 1));
							user.setLimiteCuentas(listaSuscripcion.get(opcion_suscripcion - 1).getLimiteCuentas());
							System.out.println("El nivel de suscripción del usuario " + user.getNombre() + " se ha actualizado a " + user.getSuscripcion().name());
						}	
						break;
					}
				}
			}else {
				System.out.println("Volviendo al menú anterior");
				seccion = 1;
			}	
		}
	}

	// VER BANCOS TOTALES EN EL MAIN
	static void verBancosTotales() {
		//SE VERIFICA QUE EXISTAN BANCOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS BANCOS CREADOS
		if(Banco.getBancosTotales().size() > 0) {
			System.out.println("La lista de Bancos son: ");
			Tablas.impresionBancos(Banco.getBancosTotales());
			for (Banco banco : Banco.getBancosTotales()) {
				System.out.println(banco.getNombre());
				for (int i=0; i< banco.getDic().size(); i++) {
					System.out.println(banco.getDic().get(i) + " : " + banco.getCionario().get(i));
				}
			}

			//SE IMPRIME QUE NO EXISTEN USUARIOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay bancos creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearBanco();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 2;
			}	
		}
	}

	// VER CUENTAS CORRIENTES TOTALES EN EL MAIN
	static void verCuentasCorrientesTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS CORRIENTES CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CORRIENTES CREADAS
		if(Corriente.getCuentasCorrienteTotales().size() > 0) {
			System.out.println("La lista de Cuentas Corrientes totales en el sistema son: ");
			Collections.sort(Corriente.getCuentasCorrienteTotales());
			Tablas.impresionCuentasCorriente(Corriente.getCuentasCorrienteTotales());

			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}	

	// VER CUENTAS DE AHORROS TOTALES EN EL MAIN
	static void verCuentasAhorroTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS DE AHORRO CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS DE AHORRO CREADAS
		if(Ahorros.getCuentasAhorroTotales().size() > 0) {
			System.out.println("La lista de Cuentas de Ahorro totales en el sistema son: ");
			Collections.sort(Ahorros.getCuentasAhorroTotales());
			Tablas.impresionCuentasAhorros(Ahorros.getCuentasAhorroTotales());

			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}	

	// VER CUENTAS TOTALES EN EL MAIN
	static void verCuentasTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS
		if(Cuenta.getCuentasTotales().size() > 0) {
			System.out.println("La lista de Cuentas totales en el sistema son: ");
			Collections.sort(Cuenta.getCuentasTotales());
			Tablas.impresionCuentas(Cuenta.getCuentasTotales());
//			if(Ahorros.getCuentasAhorroTotales().size() != 0) {
//				System.out.println("CUENTAS DE AHORROS");
//				user.impresionCuentasAhorros(Ahorros.getCuentasAhorroTotales());
//			}
//			if(Corriente.getCuentasCorrienteTotales().size() != 0) {
//				System.out.println("CUENTAS CORRIENTE");
//				user.impresionCuentasCorriente(Corriente.getCuentasCorrienteTotales());
//			}

			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}

	// VER MOVIMIENTOS TOTALES EN EL MAIN
	static void verMovimientosTotales() {
		//SE VERIFICA QUE EXISTAN MOVIMIENTOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS MOVIMIENTOS CREADOS
		if(Movimientos.getMovimientosTotales().size() > 0) {
			System.out.println("La lista de Movimientos son: ");
			Tablas.impresionMovimientos(Movimientos.getMovimientosTotales());

			//SE IMPRIME QUE NO EXISTEN MOVIMIENTOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay movimientos creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearMovimiento();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}

	// VER METAS TOTALES EN EL MAIN
	static void verMetasTotales() throws ParseException {
		//SE VERIFICA QUE EXISTAN METAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS METAS CREADAS
		if(Metas.getMetasTotales().size() > 0) {
			System.out.println("La lista de Metas son: ");
			Tablas.impresionMetas(Metas.getMetasTotales());

			//SE IMPRIME QUE NO EXISTEN METAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay metas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearMeta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}

	// VER ESTADOS TOTALES EN EL MAIN
	static void verEstadosTotales() {
		//SE VERIFICA QUE EXISTAN ESTADOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS ESTADOS CREADOS
		if(Estado.getEstadosTotales().size() > 0) {
			System.out.println("La lista de Estados son: ");
			Tablas.impresionEstados(Estado.getEstadosTotales());

			//SE IMPRIME QUE NO EXISTEN ESTADOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay estados creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearEstado();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}		

	// GUARDAR OBJETOS EN EL MAIN
	static void guardarObjetos() throws ParseException{
		while(true){
			if (Usuario.getUsuariosTotales().size() != 0) {
				Serializador.serializar(Usuario.getUsuariosTotales(), "Usuarios");}
			if (Banco.getBancosTotales().size() !=0 ) {
				Serializador.serializar(Banco.getBancosTotales(), "Bancos");}
			if (Estado.getEstadosTotales().size() !=0 ) {
				Serializador.serializar(Estado.getEstadosTotales(), "Estados");}
			if (Cuenta.getCuentasTotales().size()!=0) {
				Serializador.serializar(Cuenta.getCuentasTotales(), "Cuentas");}
			if (Movimientos.getMovimientosTotales().size()!=0) {
				Serializador.serializar(Movimientos.getMovimientosTotales(), "Movimientos");}
			if (Metas.getMetasTotales().size()!=0) {
				Serializador.serializar(Metas.getMetasTotales(), "Metas");} 
			if(Metas.getMetasTotales().size() == 0 && Movimientos.getMovimientosTotales().size() == 0 && Cuenta.getCuentasTotales().size() == 0 && Banco.getBancosTotales().size() == 0 && Usuario.getUsuariosTotales().size() == 0){
				System.out.println("Primero debes crear objetos para poder guardarlos.");
			}
			System.out.println();
			break;
		}
	}

	//CARGAR OBJETOS EN EL MAIN	
	static boolean existencia = false;
	static void cargarObjetos() throws ParseException{
		System.out.println("Comenzando ejecución del programa....");	
		while(true){
			File f = new File("");
			File fUsuario = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat");
			File fEstado = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat");
			File fBanco = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat");
			File fMovimientos = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat");
			File fMetas = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat");

			File fCuenta = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat");
			if (fUsuario.exists()) {
				Deserializador.deserializar_listas("Usuarios");
				existencia = true;
			}if (fEstado.exists()) {
				Deserializador.deserializar_listas("Estados");
				existencia = true;
			}if (fBanco.exists()) {
				Deserializador.deserializar_listas("Bancos");
				existencia = true;
			}if (fMovimientos.exists()) {
				Deserializador.deserializar_listas("Movimientos");
				existencia = true;
			}if (fMetas.exists()) {
				Deserializador.deserializar_listas("Metas");
				existencia = true;
			}if (fCuenta.exists()) {
				Deserializador.deserializar_listas("Cuentas");
				existencia = true;
			}if (!existencia) {
				System.out.println("No existe un estado previo del sistema guardado");
			}break;	

		}System.out.println("");
	}
	
	//CONSULTAR MIS MOVIMIENTOS
	public static void consultarMovimientos() {
		if (user.getMovimientosAsociados().size() == 0) {
			System.out.println("Aún no se han hecho movimientos desde este usuario.");
		}else {
			Tablas.impresionMovimientos(user.getMovimientosAsociados());
		}
	}

	// INTERFAZ DE BIENVENIDA EN EL MAIN - MÉTODO DE INICIO DE PROGRAMA
	static void bienvenidaApp() throws ParseException, CloneNotSupportedException {
		while(interfaz == 1) {
			// INTERFAZ DE BIENVENIDA
			System.out.println("Bienvenido al gestor de dinero."
					+ "\n1. Ingresar Usuario"
					+ "\n2. Crear Usuario"
					+ "\n3. Acceso Administrativo"
					+ "\n4. Cerrar Programa");

			seguir = 1;
			opcionUsuario = Integer.parseInt(sc.nextLine());

			while(seguir == 1) {
				System.out.println("");

				if (opcionUsuario == 1) {
					Main.ingresarUsuario();

				} else if(opcionUsuario == 2) {
					Main.crearUsuario();
					System.out.println("");

				} else if(opcionUsuario == 3){
					Main.accesoAdministrativo();
					System.out.println("");

				} else if(opcionUsuario == 4){
					Main.guardarObjetos();
					System.out.println("Finalizando programa. Esperamos verte de nuevo pronto");
					seguir = 0;
					interfaz = 0;	

				} else {
					System.out.println("Entrada no valida");
					System.out.println("NOTA: Recuerde que debe ingresar el numeral de la opción que desea escoger.");
					System.out.println("Bienvenido al gestor de dinero."
							+ "\n1. Ingresar Usuario"
							+ "\n2. Crear Usuario"
							+ "\n3. Acceso Administrativo"
							+ "\n4. Cerrar Programa");

					opcionUsuario = Integer.parseInt(sc.nextLine());
				}
			}
			// INICIO DE SESION COMO USUARIO
			while (sesioniniciada == 1) {
				System.out.println("");
				System.out.println("Bienvenido, "
						+ user.getNombre()
						+ " a tu gestor de dinero, ¿a qué sección deseas ingresar?"
						+ "\n1. Gestión económica" 
						+ "\n2. Mis productos"
						+ "\n3. Mis metas"
						+ "\n4. Mis movimientos"
						+ "\n5. Cerrar sesión");

				seccion = Integer.parseInt(sc.nextLine());

				// COMPROBAR QUE LA SECCION PUEDA EJECUTARSE
				if (seccion < 1 || seccion > 5) {
					System.out.println("Entrada no valida");
					continue;
				}
				// CLASE DE USUARIO
				while (seccion == 1) {
					// Contenido de Usuario
					System.out.println("");
					System.out.println("Bienvenido a Gestión Económica, ¿en que te podemos ayudar?"
							+ "\n1. Modificar mi suscripción"
							+ "\n2. Invertir saldo de mi cuenta"
							+ "\n3. Consignar saldo a mi cuenta"
							+ "\n4. Transferir saldo entre cuentas"
							+ "\n5. Compra con cuenta Corriente"
							+ "\n6. Gestionar prestamos"
							+ "\n7. Asesoramiento de inversiones"
							+ "\n8. Compra de cartera"
							+ "\n9. Calculadora financiera"
							+ "\n10. Salir al menú principal");

					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");

					if(opcion == 1) {
						Main.modificarSuscripcionUsuario();

					} else if(opcion == 2) {
						Main.invertirSaldoUsuario(user);
					}

					else if(opcion == 3) {
						Main.consignarSaldoCuenta(user);
					} 

					else if(opcion == 4) {
						Main.transferirSaldoCuentasUsuario();
					}
					
					//REALIZAR COMPRA CON CUENTA CORRIENTE
					else if(opcion == 5) {
						Main.compraCorriente();
					}
					
					// PEDIR PRESTAMO
					else if(opcion == 6){
						Main.funcionalidadPrestamo();
					}

					//ASESORAMIENTO DE INVERSIONES
					else if (opcion == 7){
						Main.asesorInversiones();
					}

					// COMPRA CARTERA
					else if(opcion == 8){
						Main.compraCartera();
					}

					// CALCULADORA FINANCIERA (ADICIONAL)
					else if(opcion == 9) {
						Main.calculadoraCuotas();
					}

					// Volver al menú anterior
					else if (opcion == 10) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// CLASE DE CUENTA
				while (seccion == 2) {
					// Contenido de Cuenta
					System.out.println("");
					System.out.println("Bienvenido a tus productos, ¿en que te podemos ayudar?"
							+ "\n1. Crear una cuenta"
							+ "\n2. Eliminar mis cuentas"
							+ "\n3. Ver mis cuentas"
							+ "\n4. Salir al menú principal");

					opcion = Integer.parseInt(sc.nextLine());

					// Crear una cuenta
					if(opcion == 1) {
						Main.crearCuenta();
					}
					// Eliminar una cuenta
					else if(opcion == 2) {
						Main.eliminarCuenta();
					}
					// Ver mis cuentas
					else if(opcion == 3) {
						Main.verCuentasAsociadas();
					}
					// Salir al menú principal
					else if (opcion == 4) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else if (opcion < 1 || opcion > 4) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// METAS
				while (seccion == 3) {
					// Contenido de Usuario
					System.out.println("");
					System.out.println("Bienvenido a Metas, ¿en que te podemos ayudar?" 
							+ "\n1. Crear una meta"
							+ "\n2. Eliminar mis metas" 
							+ "\n3. Ver mis metas" 
							+ "\n4. Salir al menú principal");

					opcionMetas = Integer.parseInt(sc.nextLine());

					// Crear una meta
					if (opcionMetas == 1) {
						Main.crearMeta();
					}
					// Eliminar una meta
					else if (opcionMetas == 2) {
						Main.eliminarMeta();
					}
					// Ver las metas
					else if (opcionMetas == 3) {
						Main.verMetas();
					}
					// Volver al menú anterior
					else if (opcionMetas == 4) {
						sesioniniciada = 1;
						seccion = 0;
						break;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else if (opcionMetas < 1 || opcionMetas > 4 ) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// CLASE DE MOVIMIENTOS
				while (seccion == 4) {
					// Contenido de Movimientos
					System.out.println("Bienvenido a Movimientos, ¿en que te podemos ayudar?"
							+ "\n1. Consultar mis movimientos"
							+ "\n2. Realizar un cambio de divisa"
							+ "\n3. Salir al menú principal");

					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
					//Entrada para funcionalidad de cambio de divisa
					if (opcion == 1) {
						Main.consultarMovimientos();
					}
					else if (opcion == 2) {
						Main.CambioDivisa();
					}
					// Volver al menú anterior
					else if (opcion == 3) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					if (opcion < 1 || opcion > 2 ) {
						System.out.println("Entrada no valida");
						continue;
					}
				}	
				// CERRAR SESIÓN COMO USUARIO
				if (seccion == 5) {
					System.out.println("¡Vuelve pronto " + user.getNombre() + "!");
					System.out.println("");
					sesioniniciada = 0;
				}
			}
		} sc.close();
	}

	//ATRIBUTOS DE CLASE PARA EL FUNCIONAMIENTO DE LA INTERFAZ
	static Usuario user = null;
	static int seguir = 1;
	static int opcionUsuario = 0;
	static int opcionMetas;
	static int sesioniniciada = 0;
	static int interfaz = 1;
	static int seccion = 0;
	static int opcion = 0;
	static String contrasenaAdmin = "";
	static boolean conf = true;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ParseException, CloneNotSupportedException{
		Main.cargarObjetos();
		Main.bienvenidaApp();
	}	

	public static void setContraseñaAdmin(String contrasenaAdmin) { Main.contrasenaAdmin = contrasenaAdmin; }
	public static boolean getConf() { return conf; }
	public static void setConf(boolean conf) { Main.conf = conf; }
}