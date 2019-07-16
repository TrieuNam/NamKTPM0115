package graphImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.Set;



public class DirectedGraph {

	/*several items of the access control form a graph 
	 * G = (E,V)
	 * E = set of edges of the form (v, l, v') ==> v: begin vertex, v':end vertex, l: label (INT data) 
	 * V = set of vertices with INT data.
	 * 
	 * Data structure for a labeled directed graph
	 * HashMap for edges
	 * vertice names and edge labels are as vectors.
	 * 
	 * */
	public GraphNode root;
	private  Vector<GraphNode> vertices;
	private int[] order;
	public LinkedHashMap<Integer,Edge> path;
	/*public DirectedGraph(int id, int type){
		vertices = new Vector<GraphNode>();
		vertices.add(new GraphNode(id,type));
	}*/
	
	public DirectedGraph(int[] ord){	
		vertices = new Vector<GraphNode>();
		order = ord;
		root = new GraphNode(0,order[0]);
		path = new LinkedHashMap<Integer, Edge>();
		
		//vertices.add(root);
	}

	
	public GraphNode getRoot(){
		return root;
	}
	public LinkedHashMap<Integer,Edge> returnPath(){
		return path;
	}
	public void addVertex(GraphNode newNode){
		vertices.add(newNode);
	}
	public void removeVertex(GraphNode node){
		if (node ==  returnTerminal()) return;
		for (Iterator<Edge> eIt = node.edges.iterator(); eIt.hasNext();){
			Edge e = eIt.next();
			node.removeEdge(e.getEnd(), e.label);	
		}
		vertices.remove(node);
	}
	
	public GraphNode addVertex(int id, int type){
		GraphNode newNode = new GraphNode(id,type);	 
		vertices.add(newNode);
		return newNode;
	}
	
	public GraphNode addVertexWithAutoID(int type){
		int id = vertices.size();
		GraphNode newNode = new GraphNode(id + 1,type);
		vertices.add(newNode);
		return newNode;
	}
	
	public GraphNode addTerminal(){
		int id = vertices.size();
		GraphNode newNode = new GraphNode(id+1);
		vertices.add(newNode);
		return newNode;
	}
	
	public GraphNode returnTerminal(){
		for (Iterator<GraphNode> it = vertices.iterator(); it.hasNext();){
			GraphNode node = it.next();
			if (node.type == -1){
				return node;
			}
		}
		return null;
	}
	
	public GraphNode findIsomorphicNode(GraphNode node){
		for (Iterator<GraphNode> it = vertices.iterator();it.hasNext();){
			GraphNode gNode = it.next();
			if (gNode.isIsomorphic(node)){
				return gNode;
			}
		}
		return node;
	}
	
	/* 1. Finds the nodes based on their labels.
	 * 2. Returns a list of nodes that have the same node label*/
	public ArrayList<GraphNode> findNodesByLabel(Integer element, Integer type){
		ArrayList<GraphNode> nodeList = new ArrayList<GraphNode>();
		for (Iterator<GraphNode> it = vertices.iterator(); it.hasNext();){
			GraphNode node = it.next();
			if (node.id == element && node.type == type){
				nodeList.add(node);
			}
		}
		return nodeList;
	}
	
	
	/*Node Oriented Search*/
	public boolean bfsSearch(GraphNode cNode, Integer element){
		ArrayList<GraphNode> visit = new ArrayList<GraphNode>();
		visit.add(cNode);

		while(!visit.isEmpty()){ 
			GraphNode current = visit.get(0);
			//System.out.print("Node Visited : ");
			
			if (current.id == element) {System.out.println("FOUND"); return true;}
			if (current.edges == null) return true;
			for (Iterator<Edge> it = current.edges.iterator(); it.hasNext();){// This loop is for all nodes
				Edge e = it.next(); 
				GraphNode end = e.getEnd();
				current.displayContent(); e.displayContent();end.displayContent(); System.out.println();
				if (!visit.contains(end)) visit.add(end); 
			}
			visit.remove(current); 
		}
		return false;
	}
	
	public boolean dfsSearch(GraphNode startNode, Integer element){
		//System.out.print("Node Visited : ");
		
		if (startNode.id == element){
			System.out.println("FOUND..."); 
			return true;
		} else{
			//Set<GraphNode> endNodes = startNode.edges.keySet();
			for (Iterator<Edge> it = startNode.edges.iterator(); it.hasNext();){ 
				Edge e = it.next();
				startNode.displayContent();
				e.displayContent();
				GraphNode n = e.getEnd();
				//System.out.print("Node Visited : ");
				n.displayContent(); System.out.println();
				if (dfsSearch(n, element)) return true;		
			}
			return false;
		}	 
	}

	
	
	/*Function has to be resolved for the given values*/
	public boolean dfsWalkEdges(GraphNode startNode, HashMap<Integer,Integer> assignments){
		if (startNode.edges == null) {return true;}
		else{
			ArrayList<GraphNode> transit = new ArrayList<GraphNode>();
			for (Iterator<Edge> it = startNode.edges.iterator(); it.hasNext();){
				Edge e = it.next();
				GraphNode endNode = e.getEnd();
				startNode.displayContent();
				System.out.println("Comparison type :" + endNode.type + " value " + assignments.get(startNode.type) + " label: " + e.getLabel());
				if (e.getLabel() == -1) { path.put(startNode.type, e); transit.add(endNode);}
				if (assignments.get(startNode.type) == null || assignments.get(startNode.type) == e.getLabel()){
					System.out.println("MATCHED ..");
					path.put(startNode.type,e);
					if (dfsWalkEdges(endNode, assignments)) return true;
						//return true;
				} //else if (e.getLabel() == -1){result = walk(endNode, assignments);}
			}
			System.out.println("FINISHED " + transit.size());
			for (Iterator<GraphNode> itT = transit.iterator();itT.hasNext();) {
				System.out.println("INHERITANCE ..");
				return dfsWalkEdges(itT.next(), assignments);
			}
			return false;
		}
	}

	
	public boolean identical(GraphNode u1, GraphNode u2){
		if (u1.edges.size() != u2.edges.size() || u1.type != u2.type) return false;
		boolean flag = false;
		for (Iterator<Edge> it = u1.edges.iterator(); it.hasNext();){
			Edge e1 = it.next();
			flag = false;
			for (Iterator<Edge> it2 = u2.edges.iterator();it2.hasNext();){
				Edge e2 = it2.next();
				if (e1.identical(e2)) {flag = true; break;} 
			}
			if (!flag) return false;
		}
		return true;
	}
	
	
	/*
	public GraphNode merge(){
		LinkedHashMap<Integer,ArrayList<GraphNode>> varList = new LinkedHashMap<Integer,ArrayList<GraphNode>>(); 
		for (int i = vertices.size(); i > 0; i--){
			if (vertices.get(i) != returnTerminal() && !vertices.get(i).isFinalNonTerminal()){
				GraphNode u = vertices.get(i);
				ArrayList<GraphNode> list;
				if (varList.get(u.type) == null) list = new ArrayList<GraphNode>();
				else list = varList.get(u.type);
				list.add(u);
				varList.put(u.type, list);
			}
		}
		
		
		for (int i = order.length -2; i > 0; i--){
			ArrayList<GraphNode> nodesSameType = varList.get(order[i]);
			for (Iterator<GraphNode> it = nodesSameType.iterator(); it.hasNext();){
				GraphNode node = it.next();
				for (Iterator<Edge> eIt = node.edges.iterator(); eIt.hasNext();){
					
				}
			}
		}
		
		
			
			
			GraphNode u2 = vertices.get(j);
				if (u1.type == u2.type )
			}
		}
		
	}*/
	
	
	

}
