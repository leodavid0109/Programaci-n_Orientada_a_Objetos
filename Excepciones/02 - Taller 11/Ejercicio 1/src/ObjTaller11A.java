public class ObjTaller11A {

	public static void main(String[] args) {
		try {
			int[] v = new int[] {4, 5};
			System.out.println(v[0]);
			//Adición B
			//System.out.println(v[2]);
			
			String s = null;
			System.out.println(s.length());
			
		//Adición F
		/*
		} catch(Exception e) {
			System.out.println("Hubo una excepcion");
		*/
		//Adición E
		/*
		} catch(NullPointerException | IndexOutOfBoundsException ex) {
			System.out.println("Excepcion de puntero null o indices fuera de rango");
		*/
		///*
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Excepcion por indices fuera de rango");
		} catch(NullPointerException e) {
			System.out.println("Puntero nulo");
		//*/
		} finally {
			System.out.println("Lo ultimo");
		}
		System.out.println("Sigo normal");
		//Adición G
		//System.out.println("Sigo normal " + v[0]);
	}
}
