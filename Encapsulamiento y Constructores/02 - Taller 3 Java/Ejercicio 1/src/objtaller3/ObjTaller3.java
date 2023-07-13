package objtaller3;

import java.util.ArrayList;

import compras.*;
import gestionHumana.Empleado;
import java.util.ArrayList;

public class ObjTaller3 {

	public static void main(String args[]) {
		Producto p1 = new Producto(1, "Escoba", "Aseo");
		Producto p2 = new Producto(2, "Camisa", "Ropa");
		Producto p3 = new Producto(3, "Trapera", "Aseo");
		Producto p4 = new Producto(4, "Pantalon", "Ropa");
		Producto p5 = new Producto(5, "Jabon", "Aseo");
		Empleado emp1 = new Empleado(405, "Juan", "Ingeniero");
		ArrayList<Producto> productos1 = new ArrayList<>();
		productos1.add(p1);
		productos1.add(p3);
		OrdenCompra orden1 = new OrdenCompra(101, "Aseo", emp1, productos1);
		System.out.println(Producto.getTotalProductosPedidos());
		orden1.agregarProducto(p4);
		System.out.println(Producto.getTotalProductosPedidos());
		orden1.agregarProducto(p5);
		System.out.println(Producto.getTotalProductosPedidos());
		System.out.println("Orden " + orden1.codigo + " creada");
		
		Empleado emp2 = new Empleado(128, "Susana", "Administradora de sucursal");
		ArrayList<Producto> productos2 = new ArrayList<>();
		productos2.add(p2);
		productos2.add(p4);
		OrdenCompra orden2 = new OrdenCompra(202, "Ropa", emp2, productos2);
		System.out.println(Producto.getTotalProductosPedidos());
		System.out.println(emp2.cedula + " va a retirar producto");
		orden2.retirarProducto(emp2, p4);
		System.out.println(Producto.getTotalProductosPedidos());
		orden2.retirarProducto(emp1, p2);
		System.out.println(Producto.getTotalProductosPedidos());
	}
}
