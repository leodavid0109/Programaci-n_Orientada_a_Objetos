package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import gestorAplicación.externo.Estado;
import java.io.IOException;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Usuario;
import gestorAplicación.externo.Banco;

public class Serializador{	
	//Serializar ArrayLists con objetos
	@SuppressWarnings("unchecked")
	public static String serializar(Object objetos, String clase) {		
		switch(clase) {
			case "Usuarios":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Usuarios fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Usuarios no pudo ser guardada en el sistema: " + ex);
				}	
			case "Bancos":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Bancos fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Bancos no pudo ser guardada en el sistema: " + ex);
				}
			case "Estados":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Estados fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Estados no pudo ser guardada en el sistema: " + ex);
				}				
			case "Cuentas":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Cuentas fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Cuentas no pudo ser guardada en el sistema: " + ex);
				}			
			case "Movimientos":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Movimientos fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Movimientos no pudo ser guardada en el sistema: " + ex);
				}				
			case "Metas":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat")));
					Serializador.writeObject(streamSalida, clase);
					streamSalida.close();
					return("La lista de Metas fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Metas no pudo ser guardada en el sistema: " + ex);
				}		
			default:
				return("Error de guardado: El objeto debe estar definido en el sistema.");
		}
	}
	
	//Serializar atributos estáticos
	private static void writeObject(ObjectOutputStream out, String clase) throws IOException {
	    switch(clase) {
		    case "Usuarios":
		    	out.writeObject(Usuario.getUsuariosTotales());
		    	break;
		    case "Cuentas":
		    	out.writeObject(Cuenta.getCuentasTotales());
		    	break;
		    case "Bancos":
		    	out.writeObject(Banco.getBancosTotales());
		    	break;
		    case "Estados":
		    	out.writeObject(Estado.getEstadosTotales());
		    	break;
		    case "Movimientos":
		    	out.writeObject(Movimientos.getMovimientosTotales());
		    	break;
		    case "Metas":
		    	out.writeObject(Metas.getMetasTotales());
		    	break;
	    }
	}
}
