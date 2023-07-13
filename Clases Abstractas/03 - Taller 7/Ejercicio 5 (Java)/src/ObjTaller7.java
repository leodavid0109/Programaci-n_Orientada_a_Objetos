public class ObjTaller7 {
	public static void main (String [] args) {
		Galaxia g = new Galaxia();
		Nova nova = new Nova();
		nova.tipoCuerpo1();
		nova.explotar();
		((Estrella) nova).explotar();
		g.tipoCuerpo1();
		System.out.println(g.toString());
		
		ObjetoAstronomicoExtraSolar obN = (ObjetoAstronomicoExtraSolar) nova;
		System.out.println(obN instanceof ObjetoAstronomicoExtraSolar);
		System.out.println(obN instanceof Nova);
		System.out.println(obN instanceof Estrella);
		System.out.println(obN instanceof Object);
		System.out.println(obN instanceof SuperNova);
		System.out.println(obN instanceof Galaxia);
		
		ObjetoAstronomicoExtraSolar[] oa = new ObjetoAstronomicoExtraSolar[3];
		oa[0] = new Galaxia();
		oa[1] = new Nova();
		oa[2] = new SuperNova();
		
		for (int i = 0; i < oa.length; i++) {
			oa[i].descripcion();
		}
		
		oa[0] = oa[2];
		oa[0].descripcion();
		
		//ObjetoAstronomicoExtraSolar ob = new ObjetoAstronomicoExtraSolar();
		//ObjetoAstronomicoExtraSolar oa1 = nova;
		
		//ObjetoAstronomicoExtraSolar oa1 = nova;
		//oa1.explotar();
		//((Nova) oa1).explotar();
		
		//obN = null;
		//System.out.println(obN instanceof Object);
		//System.out.println("" + obN instanceof Object);
	}
}