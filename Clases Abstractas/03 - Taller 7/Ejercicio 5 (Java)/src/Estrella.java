abstract class Estrella extends ObjetoAstronomicoExtraSolar {
	//abstract void explotar() {
		//System.out.println("Estrella explotar");
	//}
	int a = super.getID();
	abstract void explotar();
	
	public void tipoCuerpo1() {
		System.out.println("Simple " + a);
	}
}