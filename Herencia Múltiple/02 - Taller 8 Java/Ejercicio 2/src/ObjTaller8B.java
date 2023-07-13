public class ObjTaller8B implements A {
	
	public void m() {
		System.out.println(i);
	}
	public void m1() {
		System.out.println(i + 3);
	}
	public static void main(String[] args) {
		A obj = new ObjTaller8B();
		obj.m();
		//ObjTaller8B.i = 4;
		obj.m1();
		obj.m3();
		A.m2();
	}
}