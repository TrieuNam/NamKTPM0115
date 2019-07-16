import java.util.HashMap;
import java.util.Vector;


public abstract class RBACConstraint extends RBACElement{
	
	public static final int SS_DMER_CONST = 1;
	public static final int MS_DMER_CONST = 2;
	public static final int SS_HMER_CONST = 3;
	public static final int MS_HMER_CONST = 4;
	public static final int ACTIVATION_CONST = 5;
	
	public static final int D_RSoD_CONST = 6;
	public static final int H_RSoD_CONST = 7;
	
	/*This stores the mapping according to constraint type, i.e. DMER only k-1, or HMER <k ...*/
	HashMap<RBACRole, Vector<String>> role2SessionMapping;
	public RBACRole[] rSet;
	
	int type;
	
	public String findType(int type){
		switch(type){
			case SS_DMER_CONST: return "SS-DMER"; 
			case MS_DMER_CONST: return "MS-DMER"; 
			case SS_HMER_CONST: return "SS-HMER";
			case MS_HMER_CONST: return "MS-HMER"; 
			case ACTIVATION_CONST: return "Activation";
			case D_RSoD_CONST: return "D-RSoD"; 
			case H_RSoD_CONST: return "H-RSoD"; 
		}
		return "Not Known";
	}
	
}
