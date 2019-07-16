package graphs_Constraints;
import java.util.Vector;




public class ConditionTree {

	public CNode root;
	
	public ConditionTree(){
		root = null;
	}
	
	
	
	/*INSERTION FUNCTION*/
	public void insertElement(int id, int type, int parentId){
		if (root == null){
			root = new CNode(id,type,-1);
			root.children = null;
		}else{
			   CNode walk = root;
			   Vector<CNode> parentList = root.children;
			   Vector<CNode> childList = parentList;
			   int i=0;
			   int k=0;
			   while (true){
				   if (walk.id == parentId){
					    CNode n = new CNode(id, type, parentId);
					    //n.parent = walk;
					    n.children = null;
					    if (walk.children == null) walk.children = new Vector<CNode>(); 
						walk.children.add(n);
						return;
				   }else{
					   if (childList != null && childList.size() > i){
						   walk = childList.get(i);
						   i++;
					   }else {
						   if (parentList !=null && parentList.size() > k){ //Here there was >=
							   childList = parentList.get(k).children;
							   i = 0;
							   k++;
						   } else {k = 0; parentList = childList;}
					   }
				   }
		        }
		}
		
	}
	
	
	
	/*TRAVERSALS -- 
	 * preOrder
	 * inOrder
	 * postOrder
	 * */
	
	public static void preOrder(CNode node){
		if(node != null){
	       node.displayContent();
	       if (node.children != null){
	    	   for (int i=0;i<node.children.size();i++){
	    		   preOrder(node.children.get(i));
	       		}
	       }
		}
	}

	public static void inOrder(CNode node){
		if(node != null){
	       node.displayContent();
	       if (node.children != null){
	    	   for (int i=0;i<node.children.size();i++){
	    		   inOrder(node.children.get(i));
	    	   }
	       }
		}
	}
	
	
	public static void postOrder(CNode node){
		if(node != null){
		   if (node.children != null){
			   for (int i=0;i<node.children.size();i++){
				   postOrder(node.children.get(i));
			   }
		   }
		   node.displayContent();
		}
	}
}
