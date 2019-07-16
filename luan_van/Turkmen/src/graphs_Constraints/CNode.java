package graphs_Constraints;
import java.util.Vector;

public class CNode {

	Integer id;
	int type;
	
	
	Vector<CNode> children;
	Integer parent;
	
	
	/*Information on node type:
	 * Root = 1
	 * Session = 2
	 * Operation = 3
	 * Object = 4
	 * Role = 5
	 * */
	
	public CNode(int i, int t, int p){
		id = i;
		type = t;
		children = null;
		parent = p;
	}
	
/*	public void addChild(int c){
		children.add(c);
	}*/
	
	public void displayContent(){
		System.out.println("Id :" + id + " " + "Type :" + type + " Parent :" + parent);
		/*for(int i = 0; i < children.size();i++){
			System.out.print((CNode)children.get(i).displayContent() + " ");
		}*/
	}
	

	
}
