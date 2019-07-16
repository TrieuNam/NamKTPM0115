package graphImp;

public class Edge {

	GraphNode end;
	int label;
	Object info;
	public Edge(GraphNode endNode, int edgeLabel, Object in){
		end = endNode;
		label = edgeLabel;
		info = in;
	}
	
	public int getLabel(){
		return label;
	}
	
	
	public GraphNode getEnd(){
		return end;
	}
	
	public Object getInfo(){
		return info;
	}
	
	public boolean identical(Edge e){
		if (e.label == this.label && e.getEnd() == this.getEnd())
			return true;
		return false;
	}
	
	public void displayContent(){
		System.out.print(" -->(" + label +") -->");
	}
	
}
