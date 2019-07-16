package graphImp;

//import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


public class GraphNode implements Comparable{
	

		public Integer id;
		public int type;
		public  Vector<Edge> edges;
		//Integer parent;
		
		public GraphNode(){ edges = null;}
		public GraphNode(int i){ id = i;  type = -1; edges = null;} //Terminal
		public GraphNode(int i, int t){
			id = i;
			type = t;
			edges = new Vector<Edge>();
		}
		

		public void displayContent(){
			System.out.print("{" + id + ","+ type + "}");
			/*for(int i = 0; i < children.size();i++){
				System.out.print((CNode)children.get(i).displayContent() + " ");
			}*/
		}
		
		public boolean isIsomorphic(GraphNode node){
			if (edges.size() != node.edges.size()) return false;
			for (Iterator<Edge> it = edges.iterator();it.hasNext();){
				Edge e = it.next();
				for (Iterator<Edge> itHost = node.edges.iterator(); itHost.hasNext();){
					Edge eHost = itHost.next();
					if (e.getLabel() != eHost.getLabel() || !eHost.getEnd().isIsomorphic(e.getEnd())) return false;
				}
			}
			return true;
		}
		
		public int compareTo(Object o){
			if (this.id == ((GraphNode) o).id && this.type == ((GraphNode) o).type){
				return 0;
			}else return 1;
			
		}
		
		/*Node id is the value of the variable ....*/
		public  boolean addEdge(GraphNode end, int label,Object info){
			if (end != null && edgeExist(end,label) == null){
				Edge e = new Edge(end,label,info);
				edges.add(e);
				return true;
			}
			System.out.println("Couldnt add the edge, label : " + label);
			return false;
		}
		
		//CHECK THIS FUNCTION ....
		public Edge edgeExist(GraphNode end, int label){
			for (int i = 0; i < edges.size(); i++){
				Edge e = edges.get(i);
				if (e.getEnd().equals(end) && label == e.getLabel() && label != -1) {return e;}
			}
			return null;
		}
		
		public GraphNode returnEndWithEdgeLabel(int label){
			for (int i = 0; i < edges.size(); i++){
				Edge e = edges.get(i);
				if (label == e.getLabel() /*&& label != -1*/) {return e.getEnd();}
			}
			return null;
		}
		
		public  boolean removeEdge(GraphNode end, int label){
			Edge e = edgeExist(end, label);
			if (e != null){
				edges.remove(e);
				return true;
			}
			return false;
		}
		
		public boolean isFinalNonTerminal(){
			for (Iterator<Edge> it = edges.iterator(); it.hasNext();){
				Edge e = it.next();
				if (e.getEnd().type == -1) return true;
			}
			return false;
		}
}
