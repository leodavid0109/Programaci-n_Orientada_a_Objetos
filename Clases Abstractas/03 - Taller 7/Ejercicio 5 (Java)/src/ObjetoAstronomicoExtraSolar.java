abstract class ObjetoAstronomicoExtraSolar {
	private int ID;
	
	//public ObjetoAstronomicoExtraSolar() {
		//this.ID = 4;
		//this.tipoCuerpo2();
	//}
	
	abstract void tipoCuerpo1();
	abstract void descripcion();
	
	public void tipoCuerpo2() {
		System.out.println("Extrasolar");
	}
	
	public int getID() {
		return this.ID;
	}
}