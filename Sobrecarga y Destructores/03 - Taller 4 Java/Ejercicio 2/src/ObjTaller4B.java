public class ObjTaller4B {	
	public static void main(String[] args) {
		
		char c = 'g';
		short s = 2;
		byte b = 1;
		long l = 999999999;
		int i = 51232;
		double d = 12.4;
		float f = 5.65f;
		
		System.out.println("char : " + funcion(c));
		System.out.println("short : " + funcion(s));
		System.out.println("byte : " + funcion(b));
		System.out.println("long : " + funcion(l));
		System.out.println("integer : " + funcion(i));
		System.out.println("double : " + funcion(d));
		System.out.println("float : " + funcion(f));
	}
	
//	static String funcion(short a) {
//	return "Entra a short: " + a;
//	}
//	static String funcion(int a) {
//		return "Entra a int: " + a;
//	}
	
	static String funcion(double a) {
		return "Entra a double: " + a;
	}
	
//	static String funcion(float a) {
//		return "Entra a float: " + a;
//	}
}
