import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class RSoDConstraint extends RBACConstraint{

	
	int cardinality = 0;
	
	public ClausesAndTime clausesAndTimes;
	public int var = 0;
	int combIdentifier = 0;
	
	private static LinkedHashMap<String, Integer> varIDs = new LinkedHashMap<String, Integer>();
	
	private int[][] user_combinations = null;
	//private int[][] session_combinations = null;
	private Vector<Integer[]> tempUserCombinations;
	
	/*This stores the mapping according to constraint type, i.e. DRSoD only k-1, or HRSoD <k ...*/

	public RSoDConstraint(String i, RBACRole[] rset, int card, int constraintType){
		id = i;
		rSet = rset;
		cardinality = card;
		type = constraintType;		
		/* This requires rectification of static in front of varIDs...
		varIDs = new LinkedHashMap<String, Integer>();*/
		role2SessionMapping = new HashMap<RBACRole, Vector<String>>();
	}
	
	
/*	public void setSessionCombinations(RBACUser[] allUsers, HashMap<RBACUser, Vector<RBACSession>> user2sessions){
		findUserCombinations(allUsers, user2sessions);
		Vector<Integer[]> tempSessionCombinations;
		if (user_combinations != null){
			tempSessionCombinations = new Vector<Integer[]>();
			for (int i = 0; i < user_combinations.length; i++){
				for (int j = 0; j < user_combinations[i].length; j++){
					RBACUser user = allUsers[j];
					Vector<RBACSession> sessions 
				}
			}
			
			session_combinations =
		}
	}*/
	
	public Integer[] findRelevantUserList(RBACUser[] allUsers){
		Vector<Integer> relevantUsers = new Vector<Integer>();
		
		for (int j = 0; j < allUsers.length; j++){
			boolean foundTheRole = false;
			for (int l = 0; l < rSet.length & !foundTheRole; l++){
				if (allUsers[j].isAssignedRole(rSet[l])){
					relevantUsers.add(j);
					foundTheRole = true;
				}
			}
		}
		Integer[] relevantUserList = new Integer[relevantUsers.size()];
		relevantUsers.toArray(relevantUserList);
		return relevantUserList;
	}
	
	
	public void findUserCombinations(RBACUser[] allUsers, HashMap<RBACUser, Vector<RBACSession>> user2sessions){
		/*18.09.2012, trying the alternative encoding for RSoD constraints ....*/
		if ((cardinality - 2) > 0){
			Integer[] relevantUsers = findRelevantUserList(allUsers);
			/*With the new encoding we have to compute the possible combinations ...*/
			//Integer[] userIntegerSet = new Integer[allUsers.length];
			//for (int i = 0; i < relevantUsers.length; i++) userIntegerSet[i] = relevantUsers[i];
			tempUserCombinations = new Vector<Integer[]>();
			computeUserCombinations(null, /*userIntegerSet*/relevantUsers, user2sessions, cardinality - 2, allUsers);
		}
		
		/*System.out.println("Here is the list ");
		for (int i = 0; i < user_combinations.length; i++){
			System.out.print("{");
			for (int j = 0; j < user_combinations[i].length; j++){
				System.out.print(user_combinations[i][j] + "-->" + allUsers[user_combinations[i][j]].getID() + ",");
			}
			System.out.println("}");
		}*/
	}
	
	/*From : http://comscigate.com/Books/IntroSedgewick/20elements/27recursion/Combinations.java.html
	 * Enumerates all subsets of size k on N elements.
	 * */
	public void computeUserCombinations(Integer[] usersUpToNow, Integer[] users, HashMap<RBACUser, Vector<RBACSession>> user2sessions, int k, RBACUser[] actualUsers) {
		if (k == 0){
			System.out.print("{");
			for (int z = 0; z < usersUpToNow.length; z++){
				System.out.print(usersUpToNow[z] + "-->" + actualUsers[usersUpToNow[z]].getID() + ",");
			}
			System.out.println("}");
			tempUserCombinations.add(usersUpToNow);
			
			return;
		}
		//System.out.println("SIZE users " + users.length + " k = " + k);
		for (int i = 0; i < users.length; i++){
			int size = usersUpToNow == null ? 1 : (usersUpToNow.length + 1);
			Integer[] userTemp = new Integer[size];
			if (usersUpToNow != null)
				for (int t = 0; t < usersUpToNow.length; t++) userTemp[t] = usersUpToNow[t];
			//userTemp.addAll(Arrays.asList(usersUpToNow));
			Integer[] newUsers = new Integer[users.length - i - 1];
			/*System.out.println("DOING ARRAY COPY");*/
			System.arraycopy(users, i + 1, newUsers, 0, users.length - i - 1);
			/*System.out.println("FINISHED");*/
			userTemp[usersUpToNow == null ? 0 : usersUpToNow.length] = users[i];
			computeUserCombinations(userTemp, newUsers, user2sessions, k - 1, actualUsers);
		}
		if (tempUserCombinations.size() > 0){
			user_combinations =  new int[tempUserCombinations.size()][];
			for (int j = 0; j < tempUserCombinations.size();j++){
				user_combinations[j] = new int[tempUserCombinations.get(j).length];
				for (int l = 0; l < tempUserCombinations.get(j).length;l++){
					user_combinations[j][l] = tempUserCombinations.get(j)[l];
				}
			}
		}
	} 
		
	public int[][] getUserCombinations(){
		return user_combinations;
	}
	
	public void generateClauses(HashMap<RBACUser, Vector<RBACSession>> user2sessions,RBACUser[] allUsers){
		/*The below method generates the set of clauses for the given RSoD constraint. It does this for 
		 * all sets of sessions. The generated set of clauses is stored in a file and modified at run-time. 
		 * The parameters are userset at the beginning, the total userset, set of sessions and the cardinality
		 * which t - 2 as we just consider U \ {user(s)}  */
		Vector<RBACUser> relevantUsers = new Vector<RBACUser>();
		
		for (int j = 0; j < allUsers.length; j++){
			boolean found = false;
			for (int k =0; k < rSet.length && !found;k++){
				if (allUsers[j].isAssignedRole(rSet[k])){
					relevantUsers.add(allUsers[j]);
					found = true;
				}
			}
		}
		System.out.println("USERS " + relevantUsers.size());
		RBACUser[] userSet = new RBACUser[relevantUsers.size()];
		relevantUsers.toArray(userSet);
		int[] userIntegerSet = new int[userSet.length];
		for (int i = 0; i < userSet.length; i++) userIntegerSet[i] = i; 
		System.out.println("Trying to generate for " + userIntegerSet.length + " users, cardinality " + (cardinality - 1));
		double start = System.currentTimeMillis();
		generate(null, userIntegerSet, user2sessions, cardinality - 1, userSet);
		double end = System.currentTimeMillis();
		System.out.println("Finished combinations ..." + (end - start));
		//System.exit(0);
	}
	
	private int returnVarIdForRBAC(String elemID){
		//System.out.println("ID "+ elem.getID() + " " + elem.getClass().toString());
		for ( String id : getVarIDs().keySet()){
			if (elemID.equals(id)){
				//System.out.println("Found " + id);
				return getVarIDs().get(id);
			}
		}
		
		int lastId = getVarIDs().size() + 1;
		getVarIDs().put(elemID, lastId);
		return lastId;
	}
	
	
	public int getCard(){
		return cardinality;
	}
	
	 // print all subsets that take k of the remaining elements, with given prefix 
    /*public static void generate(String prefix, String elements, int k) {
        if (k == 0) {
            System.out.println(prefix);
            return;
        }
        for (int i = 0; i < elements.length(); i++)
            generate(prefix + elements.charAt(i), elements.substring(i + 1), k - 1);
    }  

    // read in N and k from command line, and print all subsets of size k from N elements
    public static void main(String[] args) {
       int N = Integer.parseInt(args[0]);
       int k = Integer.parseInt(args[1]);
       String elements = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
       generate("", elements.substring(0, N), k);
    }/*
    
    
	
	/*From : http://comscigate.com/Books/IntroSedgewick/20elements/27recursion/Combinations.java.html
	 * Enumerates all subsets of size k on N elements.
   	 * Uses some String library functions.
   	 * IMPORTANT : Here the ClausesAndTime does not store the CNF formed clauses but DNF formed clauses
   	 * Specifically, (v1 /\ v2) \/ (v3 /\ v4 /\ v5) \/ (v6 /\ v7 /\ v8 /\ v9) ...
	 * */
	public void generate(/*RBACUser[] usersUpToNow, RBACUser[] users,*/ int[] usersUpToNow, int[] users, 
			   			HashMap<RBACUser, Vector<RBACSession>> user2sessions, int k, RBACUser[] actualUsers) {
		//NUMBER++;
		//System.out.println("NUMBER " + NUMBER);
		if (k == 0){ 
			if (clausesAndTimes == null) clausesAndTimes = new ClausesAndTime();
			//System.out.println("I AM HERE ............--->");
			System.out.print("{");
			for (int z = 0; z < usersUpToNow.length; z++){
				System.out.print(usersUpToNow[z] + "-->" + actualUsers[usersUpToNow[z]].getID() + ",");
			}
			System.out.print("}");
			clausesAndTimes.clauses.addAll(getCardConstraint(usersUpToNow,user2sessions, users, actualUsers));
			return;
		}
		//System.out.println("SIZE users " + users.length + " k = " + k);
		for (int i = 0; i < users.length; i++){
			int size = usersUpToNow == null ? 1 : (usersUpToNow.length + 1);
			int[] userTemp = new int[size];
			if (usersUpToNow != null)
				for (int t = 0; t < usersUpToNow.length; t++) userTemp[t] = usersUpToNow[t];
			//userTemp.addAll(Arrays.asList(usersUpToNow));
			int[] newUsers = new int[users.length - i - 1];
			/*System.out.println("DOING ARRAY COPY");*/
			System.arraycopy(users, i + 1, newUsers, 0, users.length - i - 1);
			/*System.out.println("FINISHED");*/
			userTemp[usersUpToNow == null ? 0 : usersUpToNow.length] = users[i];
			generate(userTemp, newUsers, user2sessions, k - 1, actualUsers);
		}
	} 
	
	
	/*public void generate(RBACUser[] usersUpToNow, RBACUser[] users, 
												   HashMap<RBACUser, Vector<RBACSession>> user2sessions,
												   int k) {
        if (k == 0){ 
        	if (clausesAndTimes == null) clausesAndTimes = new ClausesAndTime();
        	System.out.println("I AM HERE ...");
        	System.out.print("{");
        	for (int z = 0; z < usersUpToNow.length; z++){
        		System.out.print(usersUpToNow[z].getID() + ",");
        	}
        	System.out.print("}");
        	clausesAndTimes.clauses.addAll(getCardConstraint(usersUpToNow,user2sessions, users));
        	return;
        }
        System.out.println("SIZE users " + users.length + " k = " + k);
        for (int i = 0; i < users.length; i++){
        	Vector<RBACUser> userTemp = new Vector<RBACUser>();
        	if (usersUpToNow != null) userTemp.addAll(Arrays.asList(usersUpToNow));
        	RBACUser[] newUsers = new RBACUser[users.length - i - 1];
        	System.out.println("DOING ARRAY COPY");
        	System.arraycopy(users, i + 1, newUsers, 0, users.length - i - 1);
        	System.out.println("FINISHED");
        	userTemp.add(users[i]);
            generate(userTemp.toArray(new RBACUser[userTemp.size()]), newUsers, user2sessions, k - 1);
        }
    } */ 

	
	/*This method generates the clauses for a given X, userset (one combination) ...*/
	public Vector<Vector<Integer>> getCardConstraint(int[] oneUserCombination, 
													HashMap<RBACUser, Vector<RBACSession>> user2sessions, 
													int[] allUsers, RBACUser[] actualUsers){
		 
		 
			 Vector<Integer> finalTemp = new Vector<Integer>();
			 Vector<Vector<Integer>> clausesRSOD = new Vector<Vector<Integer>>();
			 for (int j = 0; j < rSet.length; j++){
				 /*This loop is for all Sx combinations --> /\Sx \subset S...*/
				 String combinationIdentifier = "sod^"+ combIdentifier + "_" + rSet[j].getID();
				 
				 combIdentifier++;
				 clausesRSOD.addAll(handleOneSxForRSoD(oneUserCombination, 
						 							   combinationIdentifier, 
						 						  	   rSet[j], 
						 						  	   user2sessions, actualUsers));
				 
				 finalTemp.add(/*-1 * */returnVarIdForRBAC(combinationIdentifier));
			 }
			 Vector<Vector<Integer>> forFinal = new Vector<Vector<Integer>>();
			 forFinal.add(finalTemp);
			 clausesRSOD.addAll(forFinal);
			 //System.err.println("ADDING ONE MORE ..." + clausesRSOD.size());
			 //printClauses(formatClauses(clausesRSOD));
			 return clausesRSOD;
	}

	
	/*This corresponds to sod^x_r --> (/\s \in Sx) and generates a set of clauses (not a single clause!!!)...*/
    public Vector<Vector<Integer>> handleOneSxForRSoD(int[] sx_users, String combinationIdentifier, 
    												   RBACRole roleToBeChecked,
    												   /*RSoDConstraint constraint*/
    												   HashMap<RBACUser, Vector<RBACSession>> user2sessions,
    												   RBACUser[] actualUsers){
    	
    	Vector<Vector<Integer>> result = null; 
    	//for (int z = 0; z < constraint.rSet.length; z++){
    	/*Below is one Sx combination ...*/
    	boolean isActiveInOneSession = true;
		if (user2sessions != null){
			result = new Vector<Vector<Integer>>();
			Vector<Integer> finalTemp = new Vector<Integer>();
		    for (int j = 0; j < sx_users.length; j++){	
		    	
				Vector<RBACSession> u_sessions = user2sessions.get(actualUsers[sx_users[j]]);
				
				/*for (int z = 0; z < u_sessions.size(); z++){
			  	  System.out.println("session --> " + u_sessions.get(z).getID() + 
		 			  			 	 "user -->" + u_sessions.get(z).user.getID());}*/
				if (u_sessions != null){ /*For each session of the user ...*/
					for (Iterator it = u_sessions.iterator(); it.hasNext();){
						Vector<Integer> temp = new Vector<Integer>();
						temp.add(-1 * returnVarIdForRBAC(combinationIdentifier));
						RBACSession s = (RBACSession) it.next();
						//isActiveInOneSession = true;
						temp.add(-1 * returnVarIdForRBAC(roleToBeChecked.getID() + "_" + s.getID()));
						result.add(temp);
						finalTemp.add(returnVarIdForRBAC(roleToBeChecked.getID() + "_" + s.getID()));
					}
				}
			}
		    if (finalTemp.size() > 0) {
		    	finalTemp.add(returnVarIdForRBAC(combinationIdentifier));
		    	result.add(finalTemp);
		    }
		    
		}
	    //if (!isActiveInOneSession) result = null;
	    /*if (result != null){
	    	Vector<Vector> tempCl = new Vector<Vector>();
	    	tempCl.add(result);
	    	result = pruneClausesAndEliminateLiterals(tempCl, currentSession, constraint.rSet).get(0);
	    }*/ 
		
		/*int forSession = -1 * returnVarIdForRBAC(constraint.getID() + "-" +
									currentSession.getID() + "-" + constraint.rSet[z].getID());*/
    	//}
    	/*if (result !=null){
    		for (Iterator it = result.iterator();it.hasNext();){
    			Vector cl = (Vector) it.next();
    			for (Iterator itCl = cl.iterator(); itCl.hasNext();){
    				int r = (Integer) itCl.next();
    				System.out.print(r + " ");
    			}
    		}
    		System.out.println("Finished ...");
    	}*/
	   /* System.out.println("HERE WE GO ..");
	    Vector<Vector<Integer>> zzz = new Vector<Vector<Integer>>();
	    zzz.add(result);
	    printClauses(formatClauses(zzz));
	    System.exit(0);*/
    	return result;
    }
    
 
	public String print(){
		String str = null;
		str += "ID:" + id + "({ ";
		System.out.print("ID:"+ id + "({ ");
		for (int i = 0; i < rSet.length; i++){
			str += rSet[i].getID() + ((i + 1) < rSet.length ? "," : "");
			System.out.print(rSet[i].getID() + ((i + 1) < rSet.length ? "," : ""));
		}
		str += "}," + cardinality + ")";
		System.out.println("}," + cardinality + ")");
		return str;
	}


	public LinkedHashMap<String, Integer> getVarIDs() {
		return varIDs;
	}
	
	public void printClausesOfThisConstraint(){
		printClauses(formatClauses(clausesAndTimes.clauses));
	}
	
	/*FOR TESTING PURPOSE COPIED FROM RBAC.java */
	public void printClauses(int[][] cl){
		for (int i = 0; i < cl.length; i++){
			for (int j = 0; j < cl[i].length; j++){
				if (cl[i][j] != 0) System.out.print(cl[i][j] + " (" + 
											findIdFromVarID(cl[i][j] > 0 ? cl[i][j] : -1 * cl[i][j]) + ") ");
			}
			System.out.println();
		}
	}
	
	public static int[][] formatClauses(Vector<Vector<Integer>> cls){
		
		int[][] result = new int[cls.size()][0] ;
		for (int i = 0; i < cls.size(); i++){
			int[] clause = new int[cls.get(i).size()];
			for (int j = 0; j < cls.get(i).size(); j++){
				clause[j] = (Integer)cls.get(i).get(j); 
			}
			result[i] = clause;
		}
		return result;
	}
	
	public String findIdFromVarID(int id){
		for ( Map.Entry<String, Integer> entry : varIDs.entrySet()){
			if (((Integer)entry.getValue()) == id){
				return (String)entry.getKey();
			}
		}
		return null;
	}
	/*******************************************/

	public static void main(String[] args){
		RBAC system = RBACInstantiations.generateRBACForSoDTest(false); 
		for (Map.Entry<Integer, RSoDConstraint[]> entry : system.rSODConstraints.entrySet()){
			RSoDConstraint[] cons = entry.getValue();
			for (int i = 0; i < cons.length; i++){
				RSoDConstraint rsod = cons[i];
				//rsod.generateClauses(/*system.roles, */system.users, system.user2sessions);
				rsod.findUserCombinations(system.users, system.user2sessions);
				//system.rSODConstraints[i].printClauses(formatClauses(rsod.clausesAndTimes.clauses));
			}
		}
		system.setupVarIDs();
		
	}
}
