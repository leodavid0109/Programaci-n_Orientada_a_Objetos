package gestorAplicación.interno;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Estado;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Deuda extends Metas{
//  Atributos
    public static final String nombreD = "Deudas";
    private static final long serialVersionUID = 6L;
    private Ahorros cuenta;
    private Banco banco;
    private static ArrayList<Deuda> deudasTotales = new ArrayList<>();

    //	Constructor
    public Deuda(double cantidad, Ahorros cuenta, Usuario dueno, Banco banco){
        super(cantidad,dueno);
        this.cuenta = cuenta;
        this.banco = banco;
        deudasTotales.add(this);
        Metas.getMetasTotales().add(this);
    }
    
    public static ArrayList<String> propiedadesCuenta() {
    	ArrayList<String> arreglos = new ArrayList<String>();
		Field[] arreglo = Deuda.class.getSuperclass().getDeclaredFields();
		for(int i = 0 ; i < arreglo.length; i++) {
			arreglos.add(arreglo[i].getName());
		}
		Field[] arregloAux = Deuda.class.getDeclaredFields();
		for(int i = 0 ; i < arregloAux.length; i++) {
			arreglos.add(arregloAux[i].getName());
		}
		return arreglos;
	}
    
    public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("deudasTotales");
	}

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Deuda> getDeudasTotales() {
        return deudasTotales;
    }

    public static void setDeudasTotales(ArrayList<Deuda> deudasTotales) {
        Deuda.deudasTotales = deudasTotales;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Ahorros getCuenta() {
        return cuenta;
    }

    public void setCuenta(Ahorros cuenta) {
        this.cuenta = cuenta;
    }

    public Usuario getTitular() {
        return dueno;
    }

    public void setTitular(Usuario titular) {
        this.dueno = titular;
    }

    //	Conseguir deudas
    public static ArrayList<Deuda> conseguirDeudas(Usuario usuario){
        ArrayList<Deuda> deudas = Deuda.getDeudasTotales();
        ArrayList<Deuda> deudasUsuario = new ArrayList<Deuda>();
        for(int i = 0; i < deudas.size(); i++){
            if(deudas.get(i).getTitular().equals(usuario)){
            	deudas.get(i).setTitular(usuario);
            	for(int i1=0;i1<usuario.getCuentasAhorrosAsociadas().size();i1++) {
            		if(usuario.getCuentasAhorrosAsociadas().get(i1).equals(deudas.get(i).getCuenta())) {
            			deudas.get(i).setCuenta(usuario.getCuentasAhorrosAsociadas().get(i1));
            		}
            	}
                deudasUsuario.add(deudas.get(i));
            }
        }
        return deudasUsuario;
    }

    @Override
    public void finalize(){
        System.out.println("La deuda con id " + this.getId() + " realizada con el banco " + this.getBanco().getNombre() + " ha sido PAGADA EXITOSAMENTE");
    }
    
    public String toString() {
		return "id: " + this.id +
				"\nTitular " + this.dueno+
				"\nCantidad: " + this.cantidad ;
	}

}
