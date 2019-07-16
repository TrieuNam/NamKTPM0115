package graphs_RBACImp;

import graphImp.DirectedGraph;
import graphImp.Edge;
import graphImp.GraphNode;
import graphs_Constraints.Constraint;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;



public class GraphRBAC {

	DirectedGraph authGraph;
	Hashtable<Integer,Hashtable<Object, Integer>> values; //conversion of all values to integers
	HashMap<Integer,ArrayList<Integer>> user_role_mapping;
	
	Vector<Constraint> cList;
	
	HashMap<Object, GraphNode> operationVarList; 
	HashMap<Object, GraphNode> objectVarList; 
	HashMap<Object,GraphNode> roleVarList; 
	HashMap<Object,GraphNode> sessionVarList; 
	
	
	public GraphRBAC(int[] order){
		values = new Hashtable<Integer,Hashtable<Object, Integer>>();
		authGraph = new DirectedGraph(order); 
		
		objectVarList = new HashMap<Object,GraphNode>();
		operationVarList = new HashMap<Object,GraphNode>();
		roleVarList = new HashMap<Object,GraphNode>();
		sessionVarList = new HashMap<Object,GraphNode>();
		
		cList = new Vector<Constraint>();
		
		user_role_mapping = new HashMap<Integer,ArrayList<Integer>>();
		
	}
	
	
	/*public void order(){
		
		for (int i = 0; i < ord.length; i++){
			switch(ord[i]){
				case 1:  order.put(1, sessionVarList); break; //Sessions
				case 2:  order.put(2, roleVarList); break;
				case 3:  order.put(3 , objectVarList); break;
				case 4:  order.put(4,  operationVarList);
			}
		}
	}*/
	
	
	/*CONSTRAINTS ........................................................*/
	
	public void addConstraint(int[] scope, String[] vals){
		int[] intValues = new int[scope.length];
		for (int i = 0 ; i < vals.length; i++){
			intValues[i] = values.get(scope[i]).get(vals[i]);
		}
		Constraint c = new Constraint(scope,intValues);
		cList.add(c);
	}
	public void listConstraints(){
		System.out.println("CONSTRAINTS ...");
		for(Iterator<Constraint> it = cList.iterator(); it.hasNext();){
			Constraint c = it.next();
			c.displayContent();
		}
	}
	
	
	public DirectedGraph returnAuthGraph(){
		return authGraph;
	}
	
	
	
	
	
	
	
	
	/*RBAC STANDARD IMPLEMENTATION ......................................................*/
	
	
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
	public Integer addUser(String user){
		int intValue = addElement(user, 1);
		return intValue;
	}
	public Integer addRole(String role){//Adds node with type 2: role node
		Integer intValue = addElement(role, 2);
		return intValue;
	}
	public Integer addObject(String obj){ //adds node with type ??? 
		Integer intValue = addElement(obj, 5);
		return intValue;
	}
	public Integer addOperation(String op){//adds node with type ???
		Integer intValue = addElement(op, 3);
		return intValue;
	}
	
	/*Operation --> Object --> terminal*/
/*	public GraphNode operationOnObject(String obj,String op){
		Integer objLabel = values.get(5).get(obj);
		Integer opLabel = values.get(3).get(op);
		if (objLabel == null || opLabel == null) {
				System.out.println("No operation like that " + obj); 
				return null;
		}
		GraphNode operation = authGraph.addVertexWithAutoID(3);
		GraphNode object = authGraph.addVertexWithAutoID(5);
		GraphNode terminal = authGraph.returnTerminal();
		if (terminal == null)  terminal = authGraph.addTerminal();
		object.addEdge(terminal, objLabel);
		operation.addEdge(object,opLabel);
		return operation;
	}*/
	/*Object --> Operation --> terminal*/
/*	public GraphNode objectOnOperation(String obj,String op){
		Integer opLabel = values.get(3).get(op);
		Integer objLabel = values.get(5).get(obj);
		if (opLabel == null || objLabel == null) { 
				System.out.println("No object " + obj +" or operation like " + op); 
				return null; 
		}
		
		
		GraphNode object = authGraph.addVertexWithAutoID(5);
		
		GraphNode terminal = authGraph.returnTerminal();
		GraphNode operation = authGraph.addVertexWithAutoID(3);
		if (terminal == null) terminal = authGraph.addTerminal();
		operation.addEdge(terminal, opLabel);
		//GraphNode isoNode = authGraph.findIsomorphicNode(object);
		object.addEdge(operation,objLabel);
		return object;
	}*/
	/*Object --> Operation --> terminal*/
	public GraphNode createOperationVar(String op){
		Integer opLabel = values.get(3).get(op);
		if (opLabel == null ) { 
				System.out.println("No operation like " + op); 
				return null; 
		}
				
		GraphNode terminal = authGraph.returnTerminal();
		GraphNode operation = authGraph.addVertexWithAutoID(3);
		if (terminal == null) terminal = authGraph.addTerminal();
		operation.addEdge(terminal, opLabel,"Operation");
		operationVarList.put(op, operation);
		//GraphNode isoNode = authGraph.findIsomorphicNode(object);
		return operation;
	}
	public GraphNode grantPermission(String op,String obj, String r){
		//Integer roleLabel = ;
		if (values.get(2).get(r) == null) { 
				System.out.println("No role like " + r); 
				return null;
		}

		GraphNode objectVar = objectVarList.get(r);
		GraphNode operationVar;
		if (objectVar == null) {
			objectVar = authGraph.addVertexWithAutoID(5);
			objectVarList.put(r, objectVar);
			operationVar = createOperationVar(op);
			objectVar.addEdge(operationVar, values.get(5).get(obj), "Object");
		}else{
			operationVar = objectVar.returnEndWithEdgeLabel(values.get(5).get(obj)); 
			if (operationVar == null){
				operationVar = createOperationVar(op);
				objectVar.addEdge(operationVar, values.get(5).get(obj),"Object");
			}else{
			    GraphNode terminal = authGraph.returnTerminal();
				if (terminal == null) terminal = authGraph.addTerminal();
				operationVar.addEdge(terminal,values.get(3).get(op),"Operation");
			}
		}
		
		//GraphNode isoNode = authGraph.findIsomorphicNode(role);
		return objectVar;
	}
	
	//Membership of u to r.
	//IMPORTANT: Assign user is not so important for the run-time checking, it just constrains the possible active roles...
	public void assignUser(String u, String r){//--------------------------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS

		Integer userLabel = values.get(1).get(u);
		if (userLabel == null) { System.out.println("No user like " + u); return;}
		Integer roleLabel = values.get(2).get(r);
		if (roleLabel == null) {System.out.println("No role like " + r); return;}
		
		if (!user_role_mapping.containsKey(userLabel)){
			ArrayList<Integer> roles = new ArrayList<Integer>();
			roles.add(roleLabel);
			user_role_mapping.put(userLabel, roles);
		}else{
			ArrayList<Integer> roles = user_role_mapping.get(userLabel);
			roles.add(roleLabel);
			user_role_mapping.put(userLabel, roles);
		}

	}
	
	public GraphNode assignUserNoSession(String u, String r){
		Integer userLabel = values.get(1).get(u);
		if (userLabel == null) { System.out.println("No user like " + u); return null;}
		Integer roleLabel = values.get(2).get(r);
		if (roleLabel == null) {System.out.println("No role like " + r); return null;}
		GraphNode roleVar = roleVarList.get(u);
		if (roleVar == null){
			roleVar = authGraph.addVertexWithAutoID(2);
			roleVarList.put(u, roleVar);
			authGraph.getRoot().addEdge(roleVar,userLabel,"User");
		}
		GraphNode role = objectVarList.get(r);
		roleVar.addEdge(role, roleLabel,"User");
		
		return authGraph.getRoot();
		
	}
	
	/*We, in the following, add a horizontal connection between the roles*/
	public void addInheritance(String r_asc, String r_desc){ //--------------------------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		//Hashtable<Object,Integer> mapRoles = values.get(2);
		
		if (objectVarList.containsKey(r_asc) && objectVarList.containsKey(r_desc)){
			//if (authGraph.findNodesByLabel(mapRoles.get(r_asc),2).size() == 0) System.out.println("SIZE IS ZERO");
			GraphNode role_ascVar = objectVarList.get(r_asc); //  (authGraph.findNodesByLabel(mapRoles.get(r_asc),2)).get(0);
			GraphNode role_descVar = objectVarList.get(r_desc); //(authGraph.findNodesByLabel(mapRoles.get(r_desc),2)).get(0);
			role_ascVar.addEdge(role_descVar, -1, "Role");
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
	public boolean checkAccess(String u, String s, String op, String obj){
		
		

		
		/*Below order is not important...*/
		if ((u != null && values.get(1).get(u) == null) ||
				//values.get(2).get(role)	== null ||
				(op != null && values.get(3).get(op) == null) ||
				(s != null && values.get(4).get(s) == null) ||
				(obj !=null && values.get(5).get(obj) == null)) return false;
		
		HashMap<Integer,Integer> elements = new HashMap<Integer,Integer>();
		elements.put(1, u != null ? values.get(1).get(u) : null);
		//elements.put(2,role);
		elements.put(3, op != null ? values.get(3).get(op) : null);
		elements.put(4, s != null ? values.get(4).get(s) : null);
		elements.put(5, obj != null ? values.get(5).get(obj) : null);
		
		if (authGraph.dfsWalkEdges(authGraph.getRoot(), elements)) {
			System.out.println("PATH Followed : ");
			for (Map.Entry<Integer, Edge> entry : authGraph.returnPath().entrySet()){
				Edge e = entry.getValue();
				int type = entry.getKey();
				System.out.print("Type "+ type + "ID:" + e.getLabel() + " value ");
					for (Map.Entry<Object, Integer> entryValue : values.get(type).entrySet())
						if (entryValue.getValue() == e.getLabel()) System.out.print(entryValue.getKey());
					System.out.println(" edge info " + e.getInfo().toString());
			}
			return true;
		}
		else return false;
		
		//CHECK ACCESS MUST HANDLE THE INHERITANCE PROPERLY.........
		//MEANING IF THERE IS A SPECIAL EDGE BETWEEN TWO ROLES THEN PASS IT WITHOUT CONSUMING THE PATH VALUES....
	}
	
	/*The function represented by the decision diagram, you need to provide the necessary assignment values*/
	public boolean checkAuthorization(String user, String role, String op,String obj){
		
		/*Below order is not important...*/
		if (values.get(1).get(user) == null ||
				values.get(2).get(role)	== null ||
				values.get(3).get(op) == null ||
				values.get(5).get(obj) == null) return false;
		HashMap<Integer,Integer> elements = new HashMap<Integer,Integer>();
		elements.put(1,values.get(1).get(user));
		elements.put(2,values.get(2).get(role));
		elements.put(3,values.get(3).get(op));
		//elements.put(4,values.get(4).get(s));
		elements.put(5, values.get(5).get(obj));

		
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
	
	

	public boolean userIsAssigned(String user, String role){
		Integer userLabel = values.get(1).get(user);
		if (userLabel == null) { System.out.println("No user like " + user); return false;}
		Integer roleLabel = values.get(2).get(role);
		if (roleLabel == null) {System.out.println("No role like " + role); return false;}
		
		System.out.println("user " + user + " label "+userLabel + " role " + role + " label " + roleLabel + user_role_mapping.get(userLabel).contains(roleLabel));
		if (user_role_mapping.get(userLabel).contains(roleLabel)) return true;
		else return false;
	}
	
	
	/*Supporting Functions*/ 
	// We have to change this function in order to reflect the creation of an entity for the integer mapping...
	public GraphNode createSession(String user, String  sessionID, String[] activeRoles){//----------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		Integer sessionIntValue = addElement(sessionID, 4);
		
		Integer userLabel = values.get(1).get(user);
		if (userLabel == null) { System.out.println("No user like " + user); return null;}
		
		for (int i = 0; i<activeRoles.length;i++){
			if (!values.get(2).containsKey(activeRoles[i]) || !userIsAssigned(user, activeRoles[i])) { 
					System.out.println("User is not assigned or no role like ..." + activeRoles[i]); 
					return null;
			}
		}
		
		GraphNode sessionVar = sessionVarList.get(user);
		if (sessionVar == null) { 
			System.out.println("HERE WE ARE ...." + userLabel);
			sessionVar = authGraph.addVertexWithAutoID(4);
			sessionVarList.put(user, sessionVar);
			authGraph.getRoot().addEdge(sessionVar, userLabel, "User");
		}
		
		GraphNode roleVar = authGraph.addVertexWithAutoID(2);
		sessionVar.addEdge(roleVar, sessionIntValue, "Session");
		roleVarList.put(sessionID, roleVar);
		for (int i = 0; i < activeRoles.length;i++){
			roleVar.addEdge(objectVarList.get(activeRoles[i]), values.get(2).get(activeRoles[i]), "Role");
		}
		
		return sessionVar;
	}
	
	//A
	public boolean addActiveRole(GraphNode user, GraphNode session, GraphNode role){//------------------------> HAVE DIFFERENT VERSIONS FOR RBAC VERSIONS
		if (user.edgeExist(session, session.id) != null) {
			if (session.edgeExist(role, role.id) != null)
				session.addEdge(role, role.id,"Session"); return true;
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
	
	
	
	public void merge(){
		for(Map.Entry<Object, GraphNode> entrySession : sessionVarList.entrySet()){
			for(Map.Entry<Object, GraphNode> entryOp : operationVarList.entrySet()){
			
			}
		}

	
	}
	
	
	
	//OTHERS 
	/*public void createObject(DirectedGraph dg, GraphNode runtime, int id, GraphNode[] associatedUsers){
		GraphNode oVar = new GraphNode(id,5);
		dg.addVertex(oVar);
		runtime.addEdge(oVar, id);
	}*/
	
	

}
