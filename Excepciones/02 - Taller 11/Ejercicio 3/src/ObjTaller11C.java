public class ObjTaller11C {

	public static void main(String[] args) {
		try {
			m2();
		} catch(ExcepcionG ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		//Adición B
		/*
		} catch(ExcepcionP ex) {
			System.out.println(ex.getMessage());
		*/
		}
	}
	
	static void m1() throws ExcepcionG{
		throw new ExcepcionG();
	}
	
	static void m2() throws ExcepcionP{
		try {
			m1();
		} catch(ExcepcionG ex) {
			throw new ExcepcionP();
		}
	}
}
