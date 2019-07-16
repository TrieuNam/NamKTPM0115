package graphImp;
import graphs_RBACImp.GraphRBAC;

import java.util.ArrayList;
import java.util.Map;

//import SparseMatrix.SparseMatrix;
import java.util.HashMap;
import java.util.LinkedHashMap;



public class RBACTests {

	public static void main(String args[]){
	
	    //testGraph1();
		
		/*1:user, 2:role, 3:operation, 4:session, 5:object*/
		int[] order = {1,4,2,5,3};
		//RBAC authGraph = createAuthorizationGraph(order);
		//verifyPolicy("bob","Employee","Create","T1",order);
		
		LinkedHashMap<String,LinkedHashMap<Integer,String[]>> setting = new LinkedHashMap<String,LinkedHashMap<Integer,String[]>>();
		LinkedHashMap<Integer,String[]> user1 = new LinkedHashMap<Integer, String[]>();
		LinkedHashMap<Integer,String[]> user2 = new LinkedHashMap<Integer, String[]>();
		LinkedHashMap<Integer,String[]> user3 = new LinkedHashMap<Integer, String[]>();
		LinkedHashMap<Integer,String[]> user4 = new LinkedHashMap<Integer, String[]>();
		LinkedHashMap<Integer,String[]> user5 = new LinkedHashMap<Integer, String[]>();
		
		/*The number of sessions (first parameter) and the roles assigned to the subject (the second parameter)*/
		user1.put(2,new String[]{"Manager"}); //alice
		user2.put(2,new String[]{"Employee","Accountant"}); //bob
		user3.put(1,new String[]{"Employee"}); //rosa
		user4.put(2,new String[]{"Manager"}); //seth
		user5.put(1,new String[]{"Cashier", "Clerk"}); //john
		
		/*Subjects are assigned to users...*/
		setting.put("alice", user1);
		setting.put("bob", user2);
		setting.put("rosa", user3);
		setting.put("seth", user4);
		setting.put("john", user5);
		
		 
		
		GraphRBAC auth = createEnforcementGraph(setting, order);
	    //checkAccess(auth, "bob","s8","Sign", "T2");
		auth.addConstraint(new int[]{1,5},new String[]{"bob","T1"});
		auth.addConstraint(new int[]{1,5},new String[]{"alice","T2"});
		auth.addConstraint(new int[]{1,2},new String[]{"bob","Employee"});
		auth.listConstraints();
		checkAccess(auth, "bob","s3","Create", "T2");
		auth.returnAuthGraph().bfsSearch(auth.returnAuthGraph().root, 111);
	    //DirectedGraph authGraph = createEnforcementGraph2();    
	}
	
	/*Order: object > user > session > role > operation*/
	public static GraphRBAC createEnforcementGraph(LinkedHashMap<String,LinkedHashMap<Integer,String[]>> setting, int[] order){
				
		/*Relations
		 * object - user
		 * user - session
		 * session - role
		 * role - operation
		 * */
		GraphRBAC auth = createAuthorizationGraph(order);
		
		//The first algorithm goes here
		//The connection between a user and a role will be converted to (user --> session --> role)
		int i = 1;
		for (Map.Entry<String, LinkedHashMap<Integer,String[]>> entry :  setting.entrySet()){
			for (Map.Entry<Integer, String[]> entryUser : entry.getValue().entrySet()){
				Integer count = entryUser.getKey();
				for (int j = 0; j < count; j++){
					auth.createSession(entry.getKey(), "s" + i++, entryUser.getValue());
					System.out.println("........ user " + entry.getKey() + " session s" + (i-1) + " "+entryUser.getValue()[0]);
				}
			}
		}
		
		return auth;
	}
	
	
	public static boolean checkAccess(GraphRBAC auth, String user, String session, String operation, String object){
		
		if (auth.checkAccess(user,session,operation, object)) { 
			System.out.println("Accepted"); 
			return true;
		}
		System.out.println("Denied"); 
		return false;
		
	}
	
	public static GraphRBAC createAuthorizationGraph(int[] order){
		GraphRBAC auth = new GraphRBAC(order); //root is user type
		
		//DOMAIN SETUP
		
		/*5 users: {alice,bob,rosa,john,seth}*/
		auth.addUser("alice"); 
		auth.addUser("bob");
		auth.addUser("rosa");
		auth.addUser("john");
		auth.addUser("seth");
		
		
		/*5 roles : {Manager,Employee,Accountant,Clerk,Cashier}*/
		auth.addRole("Manager");
		auth.addRole("Employee");
		auth.addRole("Accountant");
		auth.addRole("Clerk");
		auth.addRole("Cashier");
		
		/*7 operations: {Create,Sign,Cancel,Report,View,Checkout,Suspend}*/
		auth.addOperation("Create");
		auth.addOperation("Sign");
		auth.addOperation("Cancel");
		auth.addOperation("Report");
		auth.addOperation("View");
		auth.addOperation("Checkout");
		auth.addOperation("Suspend");
		
		/*Two objects: {T1, T2}*/
		auth.addObject("T1");
		auth.addObject("T2");
		
		/*Role - operation*/
		auth.grantPermission("Sign", "T1", "Manager");
		auth.grantPermission("Create", "T1", "Employee");
		auth.grantPermission("Cancel", "T1", "Employee");
		auth.grantPermission("Suspend", "T1", "Accountant");
		auth.grantPermission("View", "T1", "Clerk");
		auth.grantPermission("Report", "T1", "Clerk");
		auth.grantPermission("Checkout", "T1", "Cashier");
		
		auth.grantPermission("Sign", "T2", "Manager");
		auth.grantPermission("Create", "T2", "Employee");
		auth.grantPermission("Cancel", "T2", "Employee");
		auth.grantPermission("Suspend", "T2", "Accountant");
		auth.grantPermission("View", "T2", "Clerk");
		auth.grantPermission("Report", "T2", "Clerk");
		auth.grantPermission("Checkout", "T2", "Cashier");
		
		auth.addInheritance("Manager","Employee");
		auth.addInheritance("Manager", "Accountant");
		auth.addInheritance("Employee", "Clerk");
		auth.addInheritance("Accountant", "Clerk");
		//System.out.print("Inheritance done");

		auth.assignUser("alice", "Manager");
		auth.assignUser("seth", "Manager");
		auth.assignUser("rosa", "Employee");
		auth.assignUser("bob", "Employee");
		auth.assignUser("bob", "Accountant");
		auth.assignUser("john", "Cashier");
		auth.assignUser("john", "Clerk");
		
		System.out.print("Creation has been done");
		
		return auth;
		
	}
	
	
	
	
	public static void verifyPolicy(String user, String role, String operation, String object, int[] order){
		
		GraphRBAC auth = createAuthorizationGraph(order);
		
		auth.assignUserNoSession("alice", "Manager");
		auth.assignUserNoSession("seth", "Manager");
		auth.assignUserNoSession("rosa", "Employee");
		auth.assignUserNoSession("bob", "Employee");
		auth.assignUserNoSession("bob", "Accountant");
		auth.assignUserNoSession("john", "Cashier");
		auth.assignUserNoSession("john", "Clerk");
			
		if (auth.checkAuthorization(user,role,operation, object)) System.out.println("Accepted");
		else System.out.println("Denied");
		
		auth.returnAuthGraph().bfsSearch(auth.returnAuthGraph().root, 111);
	}
	
	
	/*public static DirectedGraph createAuthorizationGraph(){
		RBAC auth = new RBAC();
		
		/*5 users: {alice,bob,rosa,john,seth}*/
		/*GraphNode uvar1 = auth.addUser("alice"); 
		GraphNode uvar2 = auth.addUser("bob");
		GraphNode uvar3 = auth.addUser("rosa");
		GraphNode uvar4 = auth.addUser("john");
		GraphNode uvar5 = auth.addUser("seth");
		
		//System.out.print("Users done");
		
		/*5 roles : {Manager,Employee,Accountant,Clerk,Cashier}*/
		/*GraphNode rvar1 = auth.addRole("Manager");
		GraphNode rvar2 = auth.addRole("Employee");
		GraphNode rvar3 = auth.addRole("Accountant");
		GraphNode rvar4 = auth.addRole("Clerk");
		GraphNode rvar5 = auth.addRole("Cashier");
		
		//System.out.print("Roles done");
		
		/*7 operations: {Create,Sign,Cancel,Report,View,Checkout,Suspend}*/
		/*GraphNode opvar1 = auth.addOperation("Create");
		GraphNode opvar2 = auth.addOperation("Sign");
		GraphNode opvar3 = auth.addOperation("Cancel");
		GraphNode opvar4 = auth.addOperation("Report");
		GraphNode opvar5 = auth.addOperation("View");
		GraphNode opvar6 = auth.addOperation("Checkout");
		GraphNode opvar7 = auth.addOperation("Suspend");
		
		//System.out.print("Operations done");
		
		/*Two objects: {T1, T2}*/
		/*GraphNode objvar1 = auth.addObject("T1");
		GraphNode objvar2 = auth.addObject("T2");
		//System.out.print("Objects done");
		
		
		
		/*Role - operation*/
		/*auth.grantPermission("Sign", "T1", "Manager");
		auth.grantPermission("Create", "T1", "Employee");
		auth.grantPermission("Cancel", "T1", "Employee");
		auth.grantPermission("Suspend", "T1", "Accountant");
		auth.grantPermission("View", "T1", "Clerk");
		auth.grantPermission("Report", "T1", "Clerk");
		auth.grantPermission("Checkout", "T1", "Cashier");
		
		auth.grantPermission("Sign", "T2", "Manager");
		auth.grantPermission("Create", "T2", "Employee");
		auth.grantPermission("Cancel", "T2", "Employee");
		auth.grantPermission("Suspend", "T2", "Accountant");
		auth.grantPermission("View", "T2", "Clerk");
		auth.grantPermission("Report", "T2", "Clerk");
		auth.grantPermission("Checkout", "T2", "Cashier");

		//System.out.print("Permissions done");
		
		
		
		
		/* IGNORE INHERITANCE FOR NOW.....
		auth.addInheritance("Manager","Employee");
		auth.addInheritance("Manager", "Accountant");
		auth.addInheritance("Employee", "Clerk");
		auth.addInheritance("Accountant", "Clerk");*/
		//System.out.print("Inheritance done");

		/*auth.assignUser("alice", "Manager");
		auth.assignUser("seth", "Manager");
		auth.assignUser("rosa", "Employee");
		auth.assignUser("bob", "Employee");
		auth.assignUser("bob", "Accountant");
		auth.assignUser("john", "Cashier");
		auth.assignUser("john", "Clerk");
		
		System.out.print("Creation has been done");
		
		
		//String user, String role, String op, String obj
		if (auth.checkAccessTemp("john", "Cashier", "T1","VV")) System.out.println("Accepted");
		else System.out.println("Denied");
		
		return auth.returnAuthGraph();
	}*/
	
	
	

	
	
	/*Policy encoding ...*/
	/*Order: object > session > user > role > operation*/
	public static DirectedGraph createEnforcementGraph2(DirectedGraph dg){
		
		/*Relations
		 * run-time - object
		 * object - session
		 * session - user
		 * user - role
		 * role - operation
		 * */
		
		
		//GraphNode objvar2 = dg.addVertex(21, 5); 
		
		/*GraphNode svar1 = dg.addVertex(i, 4); i--; //s1
		GraphNode svar2 = dg.addVertex(i, 4); i--;//s2
		GraphNode svar3 = dg.addVertex(i, 4); i--;//s3
		GraphNode svar4 = dg.addVertex(i, 4); i--;//s4
		GraphNode svar5 = dg.addVertex(i, 4); i--;//s5
		GraphNode svar6 = dg.addVertex(i, 4); i--;//s5
		
		
		objvar1.addEdge(svar1, 1); 
		objvar1.addEdge(svar2, 2); 
		objvar1.addEdge(svar3, 3); 
		objvar1.addEdge(svar5, 5);
		objvar1.addEdge(svar6, 6);
		objvar2.addEdge(svar1, 1);
		objvar2.addEdge(svar2, 2); 
		objvar2.addEdge(svar4, 4); 
		objvar2.addEdge(svar6, 6); 
		
		GraphNode uvar1 = dg.addVertex(i, 1); i--; 
		GraphNode uvar2 = dg.addVertex(i, 1); i--;
		GraphNode uvar3 = dg.addVertex(i, 1); i--;
		GraphNode uvar4 = dg.addVertex(i, 1);i--;
		GraphNode uvar5 = dg.addVertex(i, 1); i--;
		
		svar1.addEdge(uvar1, 1); //alice
		svar2.addEdge(uvar2, 2); //bob
		svar3.addEdge(uvar3, 3); //rosa
		svar4.addEdge(uvar4, 4); //john
		svar5.addEdge(uvar2, 2); //bob
		svar6.addEdge(uvar5, 5);
		*/
	
		return dg;
	    
	    
	}

	/*TESTS .........*/
	public static void testGraph1(int[] order){
		
		
	    DirectedGraph dg = new DirectedGraph(order);
	    GraphNode g1 = dg.addVertex(1, 1);
	    GraphNode g2 = dg.addVertex(2, 1);
	    GraphNode g3 = dg.addVertex(3, 1);
	    GraphNode g4 = dg.addVertex(4, 1);
	    GraphNode g5 = dg.addVertex(5, 1);
	    GraphNode g6 = dg.addVertex(6, 1);
	    GraphNode g7 = dg.addVertex(100, 1);
	    
	    g1.addEdge(g2, 0, "AA");
	    g1.addEdge(g3, 1, "BB");
	    g1.addEdge(g3, 2, "CC");
	    g1.addEdge(g3, 3, "DD");
	    g2.addEdge(g4, 0, "DD");
	    g2.addEdge(g5, 1, "EE");
	    g2.addEdge(g5, 2, "FF");
	    g3.addEdge(g6, 1, "GG");
	    g3.addEdge(g6, 2, "HH");
	    g4.addEdge(g7, 0, "II");
	    g5.addEdge(g7, 0, "JJ");
	    g5.addEdge(g7, 1, "KK");
	    g6.addEdge(g7, 1, "LL"); 
	    
	    //dg.bfsTraverse(dg.vertices.firstElement());
	    System.out.println("DFS ..............................");
	    if (dg.dfsSearch(dg.getRoot(),7)) 
	    		System.out.println("Found it");
	    else System.out.println("NOT Found it");
	    
	    System.out.println("BFS ..............................");
	    
	    if (dg.bfsSearch(dg.getRoot(),7)) 
    		System.out.println("Found it");
	    else System.out.println("NOT Found it");	    
	}

	
	
	
	/*A POLICY IS GIVEN BY A SET OF SPARSE MATRICES (REPRESENTING THE RELATIONS) ......... */
	/*SparseMatrix  runtime_object = new SparseMatrix();
	SparseMatrix  object_session = new SparseMatrix();
	SparseMatrix  session_user = new SparseMatrix();
	SparseMatrix  user_role = new SparseMatrix();
	SparseMatrix  role_operation = new SparseMatrix();
	
	runtime_object.add(0, 0, 1);
	runtime_object.add(0, 1, 2);
	
	object_session.add(0,0, 1);
	object_session.add(0,1, 2);
	object_session.add(0,2, 3);
	object_session.add(0,4, 5);
	object_session.add(0,1, 2);
	object_session.add(0,3, 4);
	
	session_user.add(0, 0, 1);
	session_user.add(1, 0, 2);
	session_user.add(2, 0, 3);
	session_user.add(3, 0, 4);
	session_user.add(4, 0, 2);
	
	user_role.add(0, 0, 1);
	user_role.add(1, 1, 2);
	user_role.add(1, 2, 3);
	user_role.add(2, 1, 2);
	user_role.add(3, 3, 4);
	user_role.add(3, 4, 5);
	
	role_operation.add(0, 0, 0);
	role_operation.add(0, 1, 1);
	role_operation.add(0, 2, 2);
	role_operation.add(0, 3, 3);
	role_operation.add(0, 4, 4);
	role_operation.add(0, 5, 5);
	role_operation.add(0, 6, 6);
	role_operation.add(1, 1, 1);
	role_operation.add(1, 2, 2);
	role_operation.add(1, 3, 3);
	role_operation.add(1, 4, 4);
	
	role_operation.add(2, 0, 0);
	role_operation.add(2, 3, 3);
	role_operation.add(2, 4, 4);
	role_operation.add(2, 5, 5);  

	role_operation.add(3, 3, 3);
	role_operation.add(3, 4, 4);
	role_operation.add(4, 6, 6);*/
}


/*EXAMPLE AUTHORIZATION GRAPH*/
/*int i = 100;
DirectedGraph dg = new DirectedGraph();
GraphNode authDiagram = dg.addVertex(-1, 0);



authDiagram.addEdge(objvar1, 1);  //T1
authDiagram.addEdge(objvar2, 2); //T2


/*Object - user*/
/*objvar1.addEdge(uvar1, 1); 
objvar1.addEdge(uvar2, 2); 
objvar1.addEdge(uvar3, 3); 
objvar1.addEdge(uvar5, 5);
objvar2.addEdge(uvar2, 2); 
objvar2.addEdge(uvar4, 4);  
objvar2.addEdge(uvar1, 1); 
objvar2.addEdge(uvar5, 5);

/*User - role*/
/*uvar1.addEdge(rvar1, 1); 
uvar2.addEdge(rvar2, 2); 
uvar2.addEdge(rvar3, 3); 
uvar3.addEdge(rvar3, 3); 
uvar4.addEdge(rvar4, 4); 
uvar4.addEdge(rvar5, 5); 
uvar5.addEdge(rvar1, 1); */



 