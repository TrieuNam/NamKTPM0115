
public class SoDConstraint extends RBACElement{

	int cardinality = 0;
	RBACPermission[] pSet;
	
	
	public ClausesAndTime clausesAndTimes;
	
	
	public SoDConstraint(String i, RBACPermission[] pset, int card){
		id = i;
		pSet = pset;
		cardinality = card;
	}
	
	public int getCard(){
		return cardinality;
	}
	
	
	public String print(){
		String str = null;
		str += "({";
		System.out.print("({");
		for (int i = 0; i < pSet.length; i++){
			str += pSet[i].getID() + (i < pSet.length ? "," : "");
			System.out.print(pSet[i].getID() + (i < pSet.length ? "," : ""));
		}
		str += "}," + cardinality + ")";
		System.out.print("}," + cardinality + ")");
		return str;
	}
}
