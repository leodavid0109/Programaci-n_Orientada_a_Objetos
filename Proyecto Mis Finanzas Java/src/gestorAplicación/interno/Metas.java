package gestorAplicación.interno;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.time.Instant;

public class Metas implements Serializable {
	private static final long serialVersionUID = 5L;
	public static final String nombreD = "Metas";
	private String nombre; 
	protected double cantidad;
	private Date fecha;
	protected int id;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	private static transient ArrayList<Metas> metasTotales = new ArrayList<Metas>();
	protected Usuario dueno;

	// FUNCIONALIDAD
	private static Metas metaProxima;
	private static String plazo;

	// CONSTRUCTORES
	public Metas(String nombre, double cantidad, String fecha) throws ParseException {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		this.setId(Metas.getMetasTotales().size() + 1);
		Metas.getMetasTotales().add(this);
	}

	public Metas(String nombre, double cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.setId(Metas.getMetasTotales().size() + 1);
		Metas.getMetasTotales().add(this);
	}

	public Metas(String nombre, String fecha) throws ParseException {
		this.nombre = nombre;
		this.fecha = DATE_FORMAT.parse(fecha);
		this.setId(Metas.getMetasTotales().size() + 1);
		Metas.getMetasTotales().add(this);
	}

	public Metas(double cantidad, String fecha) throws ParseException {
		this.setId(id);
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		this.setId(Metas.getMetasTotales().size() + 1);
		Metas.getMetasTotales().add(this);
	}

	// Este constructor es el que hereda deuda
	public Metas(double cantidad, Usuario dueno) {
		this.cantidad = cantidad;
		this.dueno = dueno;
	}

	// Metodos de la funcionalidad asesoramiento de inversion.
	public static Metas revisionMetas(Usuario u) {

		Date proximaFecha = Date.from(Instant.now());
		Metas proximaMeta = null;

		if (u.getMetasAsociadas().size() == 1) {
			if (u.getMetasAsociadas().get(0).getFecha() == null) {
				proximaMeta = null;
				return proximaMeta;
			} else {
				return u.getMetasAsociadas().get(0);
			}
		} else {
			for (int i = 0; i < u.getMetasAsociadas().size(); i++) {
				if (u.getMetasAsociadas().get(i).getFecha() != null) {
					proximaMeta = u.getMetasAsociadas().get(i);
					break;
				} else {
					continue;
				}
			}
			// Debemos comparar cada fecha con todas las otras fechas, por eso hay dos
			// loops.
			for (int i = 0; i < u.getMetasAsociadas().size(); i++) {

				for (int e = 0; e < u.getMetasAsociadas().size(); e++) {
					if (u.getMetasAsociadas().get(e).getFecha() == null
							|| u.getMetasAsociadas().get(i).getFecha() == null) {
						continue;
					} else {
						if (u.getMetasAsociadas().get(e).getFecha()
								.compareTo(u.getMetasAsociadas().get(i).getFecha()) < 0) {
							if (u.getMetasAsociadas().get(e).getFecha().compareTo(proximaFecha) < 0) {
								proximaFecha = u.getMetasAsociadas().get(e).getFecha();
								proximaMeta = u.getMetasAsociadas().get(e);
							} else {
								continue;
							}
						} else {
							continue;

						}
					}

				}
			}
			return proximaMeta;
		}
	}

	public static Metas cambioFecha(Metas meta, String Fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date nuevaFecha = sdf.parse(Fecha);
			meta.setFecha(nuevaFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return meta;
	}

	public static void determinarPlazo(Metas me1) throws ParseException {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdformat.parse("2024-01-01");
		Date d2 = sdformat.parse("2026-01-01");
		if (me1.getFecha().compareTo(d1) < 0) {
			setPlazo("Corto");
		} else if (me1.getFecha().compareTo(d1) > 0 && me1.getFecha().compareTo(d2) < 0) {
			setPlazo("Mediano");
		} else {
			setPlazo("Largo");
		}
	}

	public static void prioridadMetas(Usuario u, Metas me) {
		u.getMetasAsociadas().remove(u.getMetasAsociadas().size() - 1);
		u.getMetasAsociadas().add(0, me);
	}

	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("metasTotales");
		arreglo.remove("DATE_FORMAT");
		arreglo.remove("metaProxima");
		arreglo.remove("plazo");
	}

	@Override
	protected void finalize() {
		String name = this.getNombre();
		double amount = this.getCantidad();
		Date date = this.getFecha();
		if (date == null) {
			System.out
					.print("La meta con nombre " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
		} else if (amount == 0) {
			System.out.print("La meta con nombre " + this.getNombre() + "para la fecha " + this.getFechaNormal()
					+ " fue eliminada satisfactoriamente del sistema.");
		} else if (name == null) {
			System.out.print("La meta para la fecha " + this.getFechaNormal()
					+ " fue eliminada satisfactoriamente del sistema.");
		} else {
			System.out.print("La meta con nombre " + this.getNombre() + "para la fecha " + this.getFechaNormal()
					+ " fue eliminada satisfactoriamente del sistema.");
		}
	}

	// GETTER Y SETTER
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getFechaNormal() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static ArrayList<Metas> getMetasTotales() {
		return metasTotales;
	}

	public static void setMetasTotales(ArrayList<Metas> metasTotales) {
		Metas.metasTotales = metasTotales;
	}

	public Usuario getDueno() {
		return dueno;
	}

	public void setDueno(Usuario dueno) {
		this.dueno = dueno;
	}

	public static String getPlazo() {
		return plazo;
	}

	public static void setPlazo(String plazo) {
		Metas.plazo = plazo;
	}

	public static Metas getMetaProxima() {
		return metaProxima;
	}

	public static void setMetaProxima(Metas metaProxima) {
		Metas.metaProxima = metaProxima;
	}
}