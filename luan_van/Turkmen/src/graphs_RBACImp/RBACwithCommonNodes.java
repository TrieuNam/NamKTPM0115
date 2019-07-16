package graphs_RBACImp;

import graphImp.DirectedGraph;
import graphImp.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class RBACwithCommonNodes {

	DirectedGraph authGraph;
	Hashtable<Integer,Hashtable<Object, Integer>> values; //conversion of all values to integers
	
	public RBACwithCommonNodes(){
		values = new Hashtable<Integer,Hashtable<Object, Integer>>();
		int[] order = {1,4,2,5,3};
		authGraph = new DirectedGraph(order);
	}
	
	public DirectedGraph returnAuthGraph(){
		return authGraph;
	}
	public int addElement(String var, Integer type){
		Hashtable<Object,Integer> mapping = values.get(type);
		int intValue;
		if (mapping == null){
			intValue = 1;
			mapping = new Hashtable<Object,Integer>();
		}else{
			intValue = mapping.size() + 1;
		}
		mapping.put(var, intValue);
		values.put(type, mapping);
		return intValue;
	}
	
	/*Administrative Functions*/ //Adds node with type 1: user node
	public GraphNode addUser(String user){
		int intValue = addElement(user, 1);
		
		GraphNode userVar = new GraphNode(intValue, 1);
		authGraph.addVertex(userVar);
		//authGraph.addEdgeToRoot(userVar,intValue);
		
		return userVar;
	}
	public GraphNode addRole(String role){//Adds node with type 2: role node
		Integer intValue = addElement(role, 2);
		//System.out.println(role + " Added with : " + intValue);
		GraphNode roleVar = new GraphNode(intValue, 2);
		authGraph.addVertex(roleVar);
		return roleVar;
	}
	public GraphNode addObject(String obj){ //adds node with type ??? 
		GraphNode objVar = new GraphNode(addElement(obj, 5), 5);
		authGraph.addVertex(objVar);
		return objVar;
	}
	public GraphNode addOperation(String op){//adds node with type ???
		GraphNode opVar = new GraphNode(addElement(op, 3), 3);
		authGraph.addVertex(opVar);
		return opVar;
	}
	//Membership of u to r.
	public boolean assignUser(String u, String r){//--------------------------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		ArrayList<GraphNode> userNodes = authGraph.findNodesByLabel(values.get(1).get(u),1);
		ArrayList<GraphNode> roleNodes = authGraph.findNodesByLabel(values.get(2).get(r),2);
		boolean returnFlag = false;
		for (int i = 0; i < userNodes.size();i++){
			for (int j = 0; j < roleNodes.size(); j++){
				System.out.println("user "+ u + "assigned " + r + " with " + values.get(2).get(r));
				userNodes.get(i).addEdge(roleNodes.get(j), values.get(2).get(r),"Common"); returnFlag = true;
			}
		}
		return returnFlag;
	}

	/*Operation --> Object*/
	public GraphNode permitObjectOnOperation(String obj,String op){
		ArrayList<GraphNode> objNodes = authGraph.findNodesByLabel(values.get(5).get(obj),5);
		ArrayList<GraphNode> opNodes = authGraph.findNodesByLabel(values.get(3).get(op),3);
		GraphNode operation = null;
		for (int i = 0; i < opNodes.size(); i++){
			for (int j = 0; j < objNodes.size();j++){
				operation = opNodes.get(i);
				operation.addEdge(objNodes.get(j), values.get(5).get(obj),"Common");
			}
		}
		return operation;
	}
	
	public boolean grantPermission(String op,String obj, String role){
		Hashtable<Object,Integer> mapObjects = values.get(5);
		Hashtable<Object,Integer> mapOperations = values.get(3);
		Hashtable<Object,Integer> mapRoles = values.get(2);
		GraphNode operation;
		GraphNode object;
		if (mapObjects.containsKey(obj)){
			ArrayList<GraphNode> objects = authGraph.findNodesByLabel(mapObjects.get(obj),5);
			object = objects.get(0) ;
		} else object = addObject(obj);
		
		if (mapOperations.containsKey(op)){
			ArrayList<GraphNode> operations = authGraph.findNodesByLabel(mapOperations.get(op),3);
			operation = operations.get(0);
		}else operation = addOperation(op);
		
		/*Object --> Operation,  child of object*/
		if (object.edgeExist(operation, values.get(5).get(obj)) == null) object.addEdge(operation, values.get(3).get(op),"Common");
		else {System.out.println("The same object");}
		/*Alternative
		operation.addEdge(object, values.get(3).get(op)); 
		*/
		if (mapRoles.containsKey(role)){
			ArrayList<GraphNode> roles = authGraph.findNodesByLabel(mapRoles.get(role),2); 
			//System.out.println("Role to perm - " + role + "label : "+ mapRoles.get(role) + " obj " + obj + " op " + op);
			roles.get(0).addEdge(object, mapObjects.get(obj),"Common");
			return true;
		} return false;
	}
	
	/*We, in the following, add a horizontal connection between the roles*/
	public void addInheritance(String r_asc, String r_desc){ //--------------------------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		Hashtable<Object,Integer> mapRoles = values.get(2);
		if (mapRoles.containsKey(r_asc) && mapRoles.containsKey(r_desc)){
			GraphNode role_asc = (authGraph.findNodesByLabel(mapRoles.get(r_asc),2)).get(0);
			GraphNode role_desc = (authGraph.findNodesByLabel(mapRoles.get(r_desc),2)).get(0);
			role_asc.addEdge(role_desc, -1,"Common");
			//if (authGraph.bfsSearch(  mapRoles.get(r_asc), mapRoles))
		}
	}
	/*The following functions create a new role*/
	public void addAscendant(String r_asc, String r_desc){
		Hashtable<Object,Integer> mapRoles = values.get(2);
		addRole(r_asc);
		addInheritance(r_asc,r_desc);
	}
	public void addDescendant(String r_asc, String r_desc){
		Hashtable<Object,Integer> mapRoles = values.get(2);
		addRole(r_desc);
		addInheritance(r_asc,r_desc);
	}

	
	
	/*The function represented by the decision diagram, you need to provide the necessary assignment values*/
	public boolean checkAccess(String s, String op, String obj){
		
		/*Integer object =  values.get(5).get(obj);
		Integer operation =  values.get(3).get(op);
		Integer session =  values.get(4).get(s);*/
		
		HashMap<Integer,Integer> elements = new HashMap<Integer,Integer>();
		elements.put(5, values.get(5).get(obj));
		elements.put(3,values.get(3).get(op));
		elements.put(4, values.get(4).get(s));
		
		if (authGraph.dfsWalkEdges(authGraph.getRoot(), elements)) return true;
		else return false;
		
		//CHECK ACCESS MUST HANDLE THE INHERITANCE PROPERLY.........
		//MEANING IF THERE IS A SPECIAL EDGE BETWEEN TWO ROLES THEN PASS IT WITHOUT CONSUMING THE PATH VALUES....
	}
	
	/*The function represented by the decision diagram, you need to provide the necessary assignment values*/
	public boolean checkAccessTemp(String user, String role, String obj,String op){
		
		/*Integer object =  values.get(5).get(obj);
		Integer operation =  values.get(3).get(op);
		Integer session =  values.get(4).get(s);*/
		
		HashMap<Integer,Integer> elements = new HashMap<Integer,Integer>();
		elements.put(1,values.get(1).get(user));
		elements.put(2,values.get(2).get(role));
		elements.put(3,values.get(3).get(op));
		elements.put(5, values.get(5).get(obj));

		
		//elements.put(4, values.get(4).get(s));
		
		if (authGraph.dfsWalkEdges(authGraph.getRoot(), elements)) return true;
		else return false;
		
		//CHECK ACCESS MUST HANDLE THE INHERITANCE PROPERLY.........
		//MEANING IF THERE IS A SPECIAL EDGE BETWEEN TWO ROLES THEN PASS IT WITHOUT CONSUMING THE PATH VALUES....
	}
	
	/*To be defined ...*/
	public boolean deleteUser(String user){
		return false;
	}
	
	public boolean deleteRole(){
		return false;
	}
	

	
	public boolean deAssignUser(){
		return false;
	}
	
	
	//adds node with type 4: session node
	/*Supporting Functions*/ // We have to change this function in order to reflect the creation of an entity for the integer mapping...
	public void createSession(DirectedGraph dg, GraphNode user, int id, GraphNode[] activeRoles){//----------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		GraphNode sVar = new GraphNode(id,4);
		dg.addVertex(sVar);
		user.addEdge( sVar, 1,"Common"); 
		for (int i = 0; i < activeRoles.length; i++){
			sVar.addEdge(activeRoles[i], activeRoles[i].id,"Common");   // This edge represents the ownership...
		}
	}
	//A
	public boolean addActiveRole(GraphNode user, GraphNode session, GraphNode role){//------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		if (user.edgeExist(session, session.id) != null) {
			if (session.edgeExist(role, role.id) != null)
				session.addEdge(role, role.id,"Common"); return true;
		}	
		return false;
	}
	
	public boolean dropActiveRole(GraphNode user, GraphNode session, GraphNode role){
		if (user.edgeExist(session, session.id) != null) {
			if (session.edgeExist(role, role.id) != null)
				session.removeEdge(role, role.id); return true;
		}	
		return false;
	}
		
	
	public void deleteSession(GraphNode user, GraphNode session){
		if (user.edgeExist(session, session.id) != null) user.removeEdge(session, session.id);
	}
	

	
	
	
	
	
	//OTHERS 
	/*public void createObject(DirectedGraph dg, GraphNode runtime, int id, GraphNode[] associatedUsers){
		GraphNode oVar = new GraphNode(id,5);
		dg.addVertex(oVar);
		runtime.addEdge(oVar, id);
	}*/
	
	

}
