public interface A {
	public static int i = 3;
	void m();
	void m1();
	
	static void m2() {
		System.out.println(i - 2);
	}
	
	default void m3() {
		m4();
		System.out.println(i + 1);
	}
	
	private void m4() {
		System.out.println(i + " privado");
	}
}