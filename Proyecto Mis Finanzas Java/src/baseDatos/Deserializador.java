package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import gestorAplicación.externo.Estado;
import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Deuda;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Usuario;
import uiMain.Main;
import gestorAplicación.externo.Banco;

public class Deserializador {
	//Deserializar Arraylists con objetos
	@SuppressWarnings("unchecked")
	public static String deserializar_listas(String clase) {		
		switch(clase) {
			case "Usuarios":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Usuario.getUsuariosTotales().size() + " usuarios ha sido cargada con éxito");
			
				}catch(IOException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				}				
			case "Estados":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Estado.getEstadosTotales().size() + " estados ha sido cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Bancos":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Banco.getBancosTotales().size() + " bancos ha sido cargada con éxito");				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Cuentas":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Cuenta.getCuentasTotales().size() + " cuentas ha sido cargada con éxito");				
				}catch(IOException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Movimientos":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Movimientos.getMovimientosTotales().size() + " movimientos ha sido cargada con éxito");				
				}catch(IOException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				}			
			case "Metas":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Metas.getMetasTotales().size() + " metas ha sido cargada con éxito");				
				}catch(IOException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				}		
			default:
				return("Error de deserialización: El objeto debe estar definido en el sistema.");
		}
	}
	
	private static void readObject(ObjectInputStream in, String clase) throws IOException, ClassNotFoundException {
	    switch(clase) {
		    case "Usuarios":
		    	Usuario.setUsuariosTotales((ArrayList<Usuario>) in.readObject());
		    	for(Usuario u : Usuario.getUsuariosTotales()) {
		    		if(u.getNombre().equals("admin")) {
		    			Main.setContraseñaAdmin("admin");
		    		}	
		    	}
		    	break;
		    case "Cuentas":
		    	Cuenta.setCuentasTotales((ArrayList<Cuenta>) in.readObject());
		    	for(Cuenta c : Cuenta.getCuentasTotales()) {
		    		if(c instanceof Ahorros) {
		    			Ahorros.getCuentasAhorroTotales().add((Ahorros) c);
		    		}else {
		    			Corriente.getCuentasCorrienteTotales().add((Corriente) c);
		    		}
		    	}
		    	break;
		    case "Bancos":
		    	Banco.setBancosTotales((ArrayList<Banco>) in.readObject());
		    	break;
		    case "Estados":
		    	Estado.setEstadosTotales((ArrayList<Estado>) in.readObject());
		    	break;
		    case "Metas":
		    	Metas.setMetasTotales((ArrayList<Metas>) in.readObject());
		    	for(Metas m : Metas.getMetasTotales()) {
		    		if(m instanceof Deuda) {
		    			Deuda.getDeudasTotales().add((Deuda) m);
		    		}
		    	}
		    	break;
		    case "Movimientos":
		    	Movimientos.setMovimientosTotales((ArrayList<Movimientos>) in.readObject());
		    	break;
		}		
	}
}
