	import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.constraints.cnf.Clauses;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;
import org.sat4j.reader.InstanceReader;


class ClausesAndTime{
	long time;
	Vector<Vector<Integer>> clauses = null;
	public ClausesAndTime(Vector<Vector<Integer>> cl, long t){
		time = t;
		clauses = cl;
	}
	
	public ClausesAndTime(){
		clauses = new Vector<Vector<Integer>>();
	}
	public long getTime(){
		return time;
	}
	public Vector<Vector<Integer>> getClauses(){
		return clauses;
	}
}

/*Reasoning Services ...*/
public class Translator {

	Vector<Vector<Integer>> hardClauses;
	Vector<Vector<Integer>> softClauses;
	//int clauseNumber = 0;
	LinkedHashMap<String,Integer> varIds;
	//private int top = 0; 

	static int GCTime = 0;
	// The size of the array should be changed to a correct value.................
	/*public Translator(int numberOfClauses, int maxLength){
		hardClauses = new Vector(numberOfClauses);
		varIds= new LinkedHashMap<String, Integer>();
	}*/
	
	
	/*DELETE*/
	BufferedWriter output;
	
	public Translator(RBAC system){
		hardClauses = new Vector<Vector<Integer>>();
		softClauses = new Vector<Vector<Integer>>();
		
		initializeVarIDs(system);
		
		/*DELETE LATER*/
		try{
		output = new BufferedWriter(new FileWriter("tests//performance.txt",true));
		} catch(Exception e){
			System.err.println("Exception in constructor " + e.getMessage());
		}
	}
	
	public void writeThisToPerformanceFile(String str){
		try {
			output.write(str + "\n");
		} catch(Exception e){
			System.out.println("Exception in writeThisToPerformanceFile " + e.getMessage());
		}
	}
	
	
	protected void finalize() throws Throwable
	{
	  output.close();
	  super.finalize(); //not necessary if extending Object.
	}
	
	
	private int returnVarIdForRBAC(String elemID){
		//System.out.println("ID "+ elem.getID() + " " + elem.getClass().toString());
	
		Integer lastId = varIds.get(elemID);
		if (lastId != null) return lastId;
		/*for ( String id : varIds.keySet()){
			if (elemID.equals(id)){
				//System.out.println("Found " + id);
				return varIds.get(id);
			}
		}*/
		
		lastId = varIds.size() + 1;
		varIds.put(elemID, lastId);
		return lastId;
	}
	
	
	public String findIdFromVarID(int id){
		for ( Map.Entry<String, Integer> entry : varIds.entrySet()){
			if (((Integer)entry.getValue()) == id){
				return (String)entry.getKey();
			}
		}
		return null;
	}
	
	public void printClauses(int[][] cl){
		for (int i = 0; i < cl.length; i++){
			for (int j = 0; j < cl[i].length; j++){
				if (cl[i][j] != 0) System.out.print(cl[i][j] + " (" + findIdFromVarID(cl[i][j] > 0 ? cl[i][j] : -1 * cl[i][j]) + ") ");
			}
			System.out.println();
		}
	}
	
	public void printArray(int[][] cl){
		for (int i = 0; i < cl.length; i++){
			for (int j = 0; j < cl[i].length; j++){
				System.out.print(cl[i][j] + " ");
			}
			System.out.println();
		}
	}	
	
	
	public void initializeVarIDs(RBAC system){
		varIds = new LinkedHashMap<String, Integer>();
		for (Map.Entry entry : system.getVarIDs().entrySet()){
			String id = (String) entry.getKey();
			Integer value = (Integer) entry.getValue();
			varIds.put(id, value);
		}
	}
	
	public void clearClauseSets(RBAC system){
		hardClauses.clear();
		softClauses.clear();
		initializeVarIDs(system);
	}
	
	
	
	
	/*Collections.replaceAll(cl, returnVarIdForRBAC(role.getID() + varInitial), returnVarIdForRBAC(role.getID()));
	Collections.replaceAll(cl, (-1 * returnVarIdForRBAC(role.getID() + varInitial)), 
							   (-1 * returnVarIdForRBAC(role.getID())));*/
/*	public Vector<Vector> replaceLiterals(String varInitial, Vector<Vector> clauses, RBACRole role){
		
		Vector<Vector> result = RBAC.createDeepCloneClauses(clauses);
		//System.out.println();
		//System.out.println("INPUT replaceLiterals...");
		//printClauses(formatClauses(result));
		int index = 0;
		for (Iterator it = clauses.iterator(); it.hasNext();){
			Vector cl = (Vector) it.next();
			
			Vector newCl = new Vector();
			for (Iterator itCl = cl.iterator(); itCl.hasNext();){
				int id = (Integer)itCl.next();
				if (id == returnVarIdForRBAC(role.getID() + varInitial)) 
									newCl.add(returnVarIdForRBAC(role.getID()));
				else if(id == (-1 * returnVarIdForRBAC(role.getID() + varInitial))) 
									newCl.add(-1 * returnVarIdForRBAC(role.getID()));
				else newCl.add(id);
			}
			
			if (newCl.size() != 0) result.set(index, newCl);//cl = newCl;
			index++;
		}
		
		varIds.remove(role.getID() + varInitial);
		System.out.println("OUTPUT replaceLiterals...");
		printClauses(formatClauses(result));
		System.out.println();
		return result;
	}*/
	
	
	/*This 
	 *  1. Takes a clause and iterates through all literals in it 
	 *  1.2 Iterates through all roles of the constraint and calls checkTheRole method for each literal-role
	 * */
	public Vector<Integer> checkAClauseForMERConstraint(Vector<Integer> clause, RBACMERConstraint constraint, 
														 					   Vector<RBACSession> sessions){
		Vector<Integer> newCl = new Vector<Integer>();
		for (Iterator<Integer> itCl = clause.iterator(); itCl.hasNext();){
			int id = itCl.next();
			String roleId = findIdFromVarID(id < 0 ? (-1 * id) : id);
			if (roleId != null){
				boolean noInfo = true;
				for (int i = 0; i < constraint.rSet.length; i++){
					RBACRole r = constraint.rSet[i];
					Vector<String> consRole2Sessions = constraint.role2SessionMapping.get(r);
					if (consRole2Sessions != null){
						noInfo = false;
						int result = checkARoleInSessions(r, roleId, id,consRole2Sessions,sessions);
						if (result == 0) newCl.add(id);
						else if (result == 1){
								newCl.clear();
								return newCl;
						}
					} 
				}
				if (noInfo)newCl.add(id);
			}
			
		}
		return newCl;
	}
	
	/*
	 * 1. Iterates through all roles in constraint definition
	 *   1.2 Gets the role2session mapping of the constraint and checks if it is null or not
	 *   1.3 Checks if the role in the iteration is the one denoted by the literal (its var "ID" or "-1 * ID")
	 * 2. Iterates through all sessions (chosen according to constraint at hand) in the parameter 
	 * 3. Iterates through all role2session mappings of the constraint and checks if they 
	 * are equal to the session iterated in Step 2 (all sessions in the parameter, based on the constraint) 
	 * 
	 * Notes : If (id == 0) then it works for RSoD constraints ...
	 * 		   Otherwise the value of 
	 * */
	public int checkARoleInSessions(RBACRole roleAtHand, String roleIdAsLiteral, int id, Vector<String> consRole2Sessions,
														   Vector<RBACSession> sessions){
	
		if (id == 0 || id == returnVarIdForRBAC(roleAtHand.getID()) || id == (-1 * returnVarIdForRBAC(roleAtHand.getID()))){
			for (Iterator<RBACSession> itSession = sessions.iterator(); itSession.hasNext();){
				RBACSession sessionAtHand = itSession.next();
				for (Iterator<String> itSessionIds = consRole2Sessions.iterator();itSessionIds.hasNext();){
					String sessionIdToCheck = itSessionIds.next();
					if (sessionAtHand.getID().equals(sessionIdToCheck)){
						if ((id == 0 || id == returnVarIdForRBAC(roleAtHand.getID())) 
							&& roleIdAsLiteral.equals(roleAtHand.getID())) /*This means the role was active ... */
							return 1;
						else if (id == (-1 * returnVarIdForRBAC(roleAtHand.getID())))  
							return 2;
					}
				}
			}
		}
		return 0;
	}
	
	public Vector<RBACSession> getUserSessionsWithSessionExclusion(Vector<RBACSession> sessions, RBACSession session){
		Vector<RBACSession> resultingSessions = new Vector<RBACSession>();
		for (Iterator<RBACSession> it = sessions.iterator(); it.hasNext();){
			RBACSession s = it.next();
			if (!session.getID().equals(s.getID()))
			resultingSessions.add(s);
		}
		return resultingSessions;
	}
	
	/*What this method does?
	 * 1. Iterates through all clauses of a given theory
	 * 2. Drops all clauses that contains a literal representing a role active in the 
	 * session that is contained in the query
	 * 3. Replaces the negative literals in a clause that represent the role active in the session
	 * of the query.
	 * 
	 * It does a little bit of optimization:
	 * 1. Checks the clause based on the given session. If the clause has a variable representing
	 * a role that was/is already active in the session, drops the clause... Because it is supposed
	 * to be correct, meaning the activation of the role is already possible... as it was before...
	 * 2. If there is the negation of the variable representing a role that was/is already active
	 * in the session, then drops the literal. Because it is already false... --> doesnt effect...
	 * the solving process...
	 * */
	public Vector<Vector<Integer>> pruneClausesAndEliminateLiterals(/*String consID, */HashMap<RBACUser,Vector<RBACSession>> user2Sessions,
																					   RBACMERConstraint constraint, RBACSession querySession){
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		/*Each clause iteration ...*/
		for (Iterator<Vector<Integer>> it = constraint.clausesAndTimes.clauses.iterator(); it.hasNext();){
			Vector<Integer> cl = it.next();
			Vector<Integer> newCl = null;
			/*Each literal iteration ...*/
			Vector<RBACSession> sessions = null;
			if (constraint.type == RBACMERConstraint.MS_DMER_CONST){
				 sessions = getUserSessionsWithSessionExclusion(user2Sessions.get(querySession.user),querySession);
			}else if (constraint.type == RBACMERConstraint.SS_HMER_CONST){
				sessions = new Vector<RBACSession>();
				sessions.add(querySession);
			}else if (constraint.type == RBACMERConstraint.MS_HMER_CONST){
				sessions = user2Sessions.get(querySession.user);
			}
			newCl = checkAClauseForMERConstraint(cl, constraint, sessions);	
			if (newCl.size() != 0) result.add(newCl);
		}
		return result;
	}
	
	/*17.12.2011
	 * SOD SUPPORT
	 * This method eliminates all users which do not have the permissions: so
	 * they should not be considered in the support of SoD */
	public RBACUser[] findRelevantUsersBasedOnPermissions(RBACUser[] users, RBACPermission[] perms){
		Vector<RBACUser> relevantUsers = new Vector<RBACUser>();
		for (int i = 0; i < users.length; i++){
			boolean found = false;
			for (int j = 0; j < perms.length; j++){
				if (users[i].hasPermission(perms[j])) { found = true; break;}
			}
			if (found) relevantUsers.add(users[i]);
		}
		return relevantUsers.toArray(new RBACUser[relevantUsers.size()]);
	}
	
	/*17.12.2011
	 * SOD SUPPORT
	 * This method eliminates all users that do not have the roles that are in
	 * the definition of SoD */
	public RBACUser[] findRelevantUsersBasedOnRoles(RBACUser[] users, RBACRole[] roles){
		Vector<RBACUser> relevantUsers = new Vector<RBACUser>();
		for (int i = 0; i < users.length; i++){
			boolean found = false;
			for (int j = 0; j < roles.length; j++){
				if (users[i].isAssignedRole(roles[j])){ found = true; break;}
			}
			if (found) relevantUsers.add(users[i]);
		}
		return relevantUsers.toArray(new RBACUser[relevantUsers.size()]);
	}
	
	/*Calculating combinations ... : 
	 * http://stackoverflow.com/questions/127704/algorithm-to-return-all-combinations-of-k-elements-from-n
	 * */
	public static int[][] getCombinations(int num, int outOf)
    {
        int possibilities = get_nCr(outOf, num);
        int[][] combinations = new int[possibilities][num];
        int arrayPointer = 0;

        int[] counter = new int[num];
        for (int i = 0; i < num; i++) counter[i] = i;
       
        breakLoop: while (true)
        {
            // Initializing part
            for (int i = 1; i < num; i++)
            {
                if (counter[i] >= outOf - (num - 1 - i))
                    counter[i] = counter[i - 1] + 1;
            }

            for (int i = 0; i < num; i++)
            {
                if (counter[i] < outOf) continue;
                else  break breakLoop;
            }

            // Innermost part
            combinations[arrayPointer] = counter.clone();
            arrayPointer++;

            // Incrementing part
            counter[num - 1]++;
            for (int i = num - 1; i >= 1; i--)
            {
                if (counter[i] >= outOf - (num - 1 - i))
                    counter[i - 1]++;
            }
        }
        return combinations;
    }

	/*19.12.2011
	 * SOD SUPPORT
	 * Optimization for eliminating user combinations that does not involve the 
	 * session owner user.
	 * */
	private int[][] optimizeUserCombinations(RBACUser[] userList, int[][] combinations, RBACSession session){
		Vector<int[]> newCombinations = new Vector<int[]>();
		for (int i = 0; i < combinations.length; i++){
			boolean exist = false;
			for (int j = 0; j < combinations[i].length; j++){
				if (userList[combinations[i][j]].getID().equals(session.user.getID())){
					exist = true;
					//System.out.println("HERE WE ARE ");
					break;
				}
			}
			if (exist) newCombinations.add(combinations[i]);
		}
		return newCombinations.toArray(new int[newCombinations.size()][]);
	}
	
    private static int get_nCr(int n, int r)
    {
    	System.out.println("n = " + n + " r = " + r);
        if(r > n)
        {
            throw new ArithmeticException("r is greater then n");
        }
        BigInteger numerator = BigInteger.valueOf(1);
        BigInteger denominator = BigInteger.valueOf(1);
        System.out.println("Numerator " + numerator + " Denominator " + denominator);
        for (int i = n; i >= (r + 1); i--)
        {
            System.out.print(i + " ");
        	numerator = numerator.multiply(BigInteger.valueOf(i));
        }
        System.out.println("Numerator " + numerator + " Denominator " + denominator);
        for (int i = 2; i <= (n - r); i++)
        {	System.out.print(i + " ");
            denominator = denominator.multiply(BigInteger.valueOf(i));
        }
        System.out.println("Numerator " + numerator + " Denominator " + denominator);
        numerator = numerator.divide( denominator); 
        if (numerator.compareTo(BigInteger.valueOf(2147483647)) == 1) return 1000000;
        else return numerator.intValue();
    }
    
    
	/*Added for SoD support on 2nd of May 2012*/
    /*This method replaces the RSoD variables generated off-line with the new variables 
     * generated during the translation. Beware that it is session specific!!!! */
    public Vector<Integer> checkSessionForClauses(RBACSession s, Vector<Integer> clauses){
    	Vector<Integer> newClauses = new Vector<Integer>();
    	for (Iterator<Integer> it = clauses.iterator(); it.hasNext();){
    		Integer var = it.next();
    		
    		String str = findIdFromVarID(var < 0 ? (-1 * var) : var);
    		//System.out.println(var + " str "+ str);
    		if (str == null || s.getID() == null){
    			System.out.println("NULL!!!");
    		}
    		if (str.endsWith("_"+ s.getID())){
    			String roleText = str.substring(0,str.indexOf("_"));
    			//System.out.println("role Text  " + roleText);
    			int newVar = returnVarIdForRBAC(roleText);
    			newClauses.add(var < 0 ? -1 * newVar : newVar);
    			//System.out.println("new Var " + newVar);
    			//System.exit(0);
    		}else newClauses.add(var);
    	}
    	return newClauses;
    }
    
    
    public boolean checkCombinationForTheSessionUser(RBACUser[] users, int[] userCombination, RBACUser user){
    	for (int i = 0; i < userCombination.length; i++){
    		if (users[userCombination[i]].getID().equals(user.getID())) return true;
    	}
    	return false;
    }
    
    /**/
    public void setClausesForRSoDConstraints(RSoDConstraint[] constraints, RBACUser[] users, 
    										 HashMap<RBACUser,Vector<RBACSession>> user2sessions, 
    										 RBACSession querySession, boolean excludeQuerySession){
    	Vector<Integer> temp; 
		for (int k = 0; k < constraints.length; k++){
			RSoDConstraint rsod = constraints[k];
			for (int l= 0; l < rsod.getUserCombinations().length; l++){
				//Down to here, it is user combinations for RSoD
				int[] userListInCombination = rsod.getUserCombinations()[l]; // THIS WOULD BE Z = X \cup {user(s)} ...
				if (!checkCombinationForTheSessionUser(users,userListInCombination,querySession.user)){
					temp = new Vector<Integer>();
					for (int s = 0; s < rsod.rSet.length; s++){
						RBACRole r = rsod.rSet[s];
						boolean foundTheRole = false;
						Vector<String> cons2sessions = rsod.role2SessionMapping.get(r);
						if (cons2sessions != null){
							Vector<RBACSession> userSessions = excludeQuerySession ? 
									getUserSessionsWithSessionExclusion(user2sessions.get(querySession.user),querySession):
									user2sessions.get(querySession.user);
							int foundRoleInCombination = checkARoleInSessions(r, r.getID(), 0,cons2sessions,userSessions);
							if (foundRoleInCombination == 1) foundTheRole = true;
							for (int m = 0; m < userListInCombination.length & !foundTheRole; m++){
								userSessions = user2sessions.get(users[userListInCombination[m]]);
								foundRoleInCombination = checkARoleInSessions(r, r.getID(), 0,cons2sessions,userSessions);
								if (foundRoleInCombination == 1) foundTheRole = true;
							}
						}
						if (!foundTheRole) temp.add(-1 * returnVarIdForRBAC(r.getID()));
						/*
						Vector<RBACSession> userSessions = system.user2sessions.get(session.user);
						for (int n = 0; n < userSessions.size() & !foundTheRole; n++){
							if (!session.getID().equals(userSessions.get(n).getID()) && 
									userSessions.get(n).isRoleActive(r))  foundTheRole = true;								
						}
						for (int m = 0; m < userListInCombination.length & !foundTheRole; m++){
							userSessions = system.user2sessions.get(system.users[userListInCombination[m]]);
							for (int n = 0; n < userSessions.size() & !foundTheRole; n++){
								if (!session.getID().equals(userSessions.get(n).getID()) && 
									userSessions.get(n).isRoleActive(r))  foundTheRole = true;								
							}
						}*/
					}
				
					if (temp.size() > 0) hardClauses.add(temp);
				}
			}
		}
    }
    
	/********* Generate Clauses for our case ***********************************
	 * The relatedSessions parameter corresponds to S*, which is the set of active sessions.
	 * P-MAX case where soft clauses have weight 1, hard clauses have varNum + 1...
	 * 
	 * Parameters : 
	 * - system --> RBAC configuration
	 * - session --> Session in the UAQ
	 * - upperbound, lowerbound --> permission sets in the UAQ
	 * - objective --> min, max, exact cases of UAQ
	 * - singleConstraint --> Whether we are testing a single constraint or 
	 * multiple constraints are available at the same time, this is needed for constraint testing (constraint.cfg).
	 * - isGeneration --> if the translation is used for RBAC generation.
	 * */
	public ResultContainer generateClausesForOurs(RBAC system, RBACSession session,/*single session element*/
												  RBACPermission[] upperbound, 
												  RBACPermission[] lowerbound,
												  /*RBACState[] states, 
												  int stateNumber*/
												  int objective,
												  int singleConstraint, boolean isGeneration){
	
		Vector<Integer> temp; 
		ResultContainer timingResults; 
		DescriptiveStatistics ds = new DescriptiveStatistics();
	
		/*IMPORTANT : Propositional Variables:
		 * 1. p(s) to represent the statement p \in perms_k(s) for each p \n P and s \in S*... 
		 * 2. r(s) to represent the statement r \in roles_k(s) */
		clearClauseSets(system);
		
		long testStartTime = 0;
		long testEndtime = 0;
		RBACUser user = system.findUser(session.user.getID());
		RBACRole[] rolesUsed = system.roles; //isGeneration ? system.roles : user.assignedRoles;
		/*boolean isMaxSAT = false;
		if (objective == 0 || objective == 1){
			isMaxSAT = true;
		}*/
		
		
		
		/*Step 1: for all roles that are not ((u,r) \in UA), add a not(r) to the clauses
		 * Propositional variable r(s) is added as not(r(s)) */
		testStartTime = System.currentTimeMillis();
		//if (isGeneration){
			for (int i=0; i < rolesUsed.length; i++){
				temp = new Vector<Integer>();
				if (!user.isAssignedRole(rolesUsed[i])) {
					//if (isMaxSAT) temp.add(top);
					temp.add(-1 * returnVarIdForRBAC(rolesUsed[i].getID())); 
					hardClauses.add(temp); 
					//clauseNumber++;
				}
			}
		//}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 1 --> " + (testEndtime - testStartTime));
		
		/*printClauses(formatClauses(hardClauses));
		System.out.println("STEP 1 IS DONE ...");
		System.exit(0);*/
		
		testStartTime = System.currentTimeMillis();
		/*Step 2: For all permissions that are (p \in Plb), add (-1 * p)*/
		for (int m = 0; m < lowerbound.length; m++){
			temp = new Vector<Integer>();
			temp.add(returnVarIdForRBAC(lowerbound[m].getID()));
			hardClauses.add(temp);
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 2 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(clauses));
		System.out.println("STEP 2 IS DONE ...");
		System.exit(0);*/

		
		testStartTime = System.currentTimeMillis();
		/*Step 3: for all permissions not (p \in Pub) add not(p)*/
		for (int i = 0; i < system.getPermissions().length; i++){
			int j = 0;
			boolean inUpperBound = false;
			//if(session.checkPermission(system.getPermissions()[i])){     ==> Modification in step 2....
				for (int m = 0; m < upperbound.length && !inUpperBound; m++)
					if (system.getPermissions()[i] == upperbound[m])
						inUpperBound = true;
			//}
			temp = new Vector<Integer>();
			if (!inUpperBound) {
				//if (isMaxSAT) temp.add(top);
				temp.add(-1 * returnVarIdForRBAC(system.getPermissions()[i].getID()));
				hardClauses.add(temp);
				//clauseNumber++;
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 3 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(clauses));
		System.out.println("STEP 3 IS DONE ...");
		System.exit(0);*/
		
		
		testStartTime = System.currentTimeMillis();
		/*Step 4: for all (p,r) \in PA and all s \ in S*, (-1 * r(s) \/ p(s))...*/
		/*Role hierarchies are added....*/
		for (int i = 0; i < /*system.roles.length*/rolesUsed.length; i++){
			//if (session.isRoleActive(system.roles[i])){
					for (int k = 0; k < rolesUsed[i].perms.length; k++){
						temp = new Vector<Integer>();
						//if (isMaxSAT) temp.add(top);
						temp.add(-1 * returnVarIdForRBAC(rolesUsed[i].getID()));
						temp.add(returnVarIdForRBAC(rolesUsed[i].perms[k].getID()));
						hardClauses.add(temp);
						//clauseNumber++;
					}
			//}
					
					/*THIS IS NECESSARY FOR SACMAT09 COMPARISON ...*/
					/*temp = new Vector<Integer>();
					temp.add((returnVarIdForRBAC(rolesUsed[i].getID())));
					for (int j = 0; j < rolesUsed[i].perms.length; j++){
						temp.add(-1 * returnVarIdForRBAC(rolesUsed[i].perms[j].getID()));
					}
					hardClauses.add(temp);*/
					/*THIS IS NECESSARY FOR SACMAT09 COMPARISON ...*/
					
		}
		testEndtime = System.currentTimeMillis();
		//ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 4 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(clauses));
		System.out.println("STEP 4 IS DONE ...");
		System.exit(0);*/

		
		testStartTime = System.currentTimeMillis();
		int ssss = 0;
		/*Step 5: for all p \in P and s \in S*, not(p(s)) \/ OR(r1 \/ .. \/ rn)  */
		/*Role hierarchies are added ...*/
		for (int i = 0; i < system.getPermissions().length; i++){
			temp = new Vector<Integer>();
			//if(session.checkPermission(system.getPermissions()[i])){
			//if (isMaxSAT) temp.add(top);
			temp.add(-1 * returnVarIdForRBAC(system.getPermissions()[i].getID()));
			boolean roleAdded = false;
				//int c = 0;
			for (int k = 0; k < rolesUsed.length; k++){
					if (rolesUsed[k].hasPermission(system.getPermissions()[i])){ 
						temp.add(returnVarIdForRBAC(rolesUsed[k].getID()));
						roleAdded = true;
					}
			}
			if (temp.size() > 0 && roleAdded) {/*ssss++; */hardClauses.add(temp);}
				//clauseNumber++;
			//}
		}
		testEndtime = System.currentTimeMillis();
		//ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 5 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(clauses));
		System.out.println("STEP 5 IS DONE ...");
		System.exit(0);*/


		testStartTime = System.currentTimeMillis();
		/*Step 6: SS-DMER NEW VERSION*/
		//int consType = 1;//SSDMER
		if (system.merConstraints != null && 
			system.merConstraints.get(RBACMERConstraint.SS_DMER_CONST) != null && 
		    (singleConstraint == -1 || singleConstraint == RBACMERConstraint.SS_DMER_CONST)){	
			for (int i = 0; i < system.merConstraints.get(RBACMERConstraint.SS_DMER_CONST).length; i++){
				RBACMERConstraint constraint = system.merConstraints.get(RBACMERConstraint.SS_DMER_CONST)[i];
				if (constraint.clausesAndTimes != null){
			        //printClauses(formatClauses(result));
					//FIXME : Here there are two options (try first):
					//1. Instead of looping through all roles in here, change the pruneClausesAndLiterals
					//method in a way that you will traverse each clause for each role
			        
					/*pruneAndReplaceClausesLiterals(constraint.getID(), 
												   constraint.clausesAndTimes.clauses,
												   session,
												   constraint.roles);*/
					//System.out.println("Role Num:" + constraint.roles.length + " card: " + constraint.cardinality);
					//printClauses(formatClauses(constraint.clausesAndTimes.clauses));
				
					Vector<Vector<Integer>> newClauses= constraint.clausesAndTimes.clauses;//pruneClausesAndEliminateLiterals(/*constraint.getID(),*/ 
																			  /*constraint.clausesAndTimes.clauses, 
																			  session, 
																			  constraint.roles);*/
					
/*					for (int j = 0; j < constraint.roles.length; j++){
						if (session.isRoleActive(constraint.roles[j])){
							newClauses = pruneClausesAndLiterals(constraint.getID(),
																 constraint.clausesAndTimes.clauses, 
																 constraint.roles[j]);
						} //else{
					
						newClauses = replaceLiterals(constraint.getID(), 
													 newClauses == null ? constraint.clausesAndTimes.clauses : newClauses, 
													 constraint.roles[j]);
						//}
					}*/
					
					//printClauses(formatClauses(newClauses));
					//System.exit(0);
					
			       if (constraint.clausesAndTimes.clauses != null && constraint.clausesAndTimes.clauses.size() > 0) 
			    	   	hardClauses.addAll(newClauses);
				}else{
					System.err.println("Couldn't generate set of clauses for cardinality constraint in SS-DMER");
					system.print(false);
					System.exit(0);
					return null;
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 6 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(hardClauses));
		System.out.println("STEP 6 IS DONE ...");
		System.exit(0);*/

		
		
		/*Step 7: MS-DMER NEW VERSION: Use the encoding proposed by Sinz...
		 */
		testStartTime = System.currentTimeMillis();
		if (system.merConstraints != null &&
			system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST) != null && 
			(singleConstraint == -1 || singleConstraint == RBACMERConstraint.MS_DMER_CONST)){
			for (int i = 0; i < system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST).length; i++){
				RBACMERConstraint constraint = system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST)[i];
				if (constraint.clausesAndTimes != null){	
			        
					Vector<Vector<Integer>> newClauses=  pruneClausesAndEliminateLiterals(system.user2sessions, constraint,/*constraint.getID(),*/ session);
					
/*					for (int j = 0; j < constraint.roles.length; j++){
			        	if (constraint.roleMapping.get(constraint.roles[j]) != null && 
			        							constraint.roleMapping.get(constraint.roles[j]).contains(session.getID()))
			        		newClauses = pruneClausesAndLiterals(constraint.getID(), 
			        														  constraint.clausesAndTimes.clauses, 
			        														  constraint.roles[j]);
			        	//else {
			        		newClauses = replaceLiterals(constraint.getID(), 
			        								newClauses == null ? constraint.clausesAndTimes.clauses : newClauses, 
			        									 constraint.roles[j]);
			        	//}
			        }*/
			        
			        if (constraint.clausesAndTimes.clauses != null && constraint.clausesAndTimes.clauses.size() > 0) 
			        		hardClauses.addAll(newClauses);
				}else {						
						System.err.println("Couldn't generate set of clauses for cardinality constraint in MS-DMER");
						system.print(false);
						System.exit(0);
						return null;
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 7 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(hardClauses));
		System.out.println("STEP 7 IS DONE ...");
		System.exit(0);*/
		
		
		/*Step 8: SS-HMER NEW VERSION encoding ...*/
		testStartTime = System.currentTimeMillis();
		if (system.merConstraints != null &&
			system.merConstraints.get(RBACMERConstraint.SS_HMER_CONST) != null && 
			(singleConstraint == -1 || singleConstraint == RBACMERConstraint.SS_HMER_CONST)){
			for (int i = 0; i < system.merConstraints.get(RBACMERConstraint.SS_HMER_CONST).length; i++){
				RBACMERConstraint constraint = system.merConstraints.get(RBACMERConstraint.SS_HMER_CONST)[i];
				if (constraint.clausesAndTimes != null){
					Vector<Vector<Integer>> newClauses= pruneClausesAndEliminateLiterals(system.user2sessions,constraint,/*constraint.getID(),*/  session);
/*					for (int j = 0; j < constraint.roles.length; j++){
						if (constraint.roleMapping.get(constraint.roles[j]) != null && 
											constraint.roleMapping.get(constraint.roles[j]).contains(session.getID()))
							newClauses = pruneClausesAndLiterals(constraint.getID(), 
																 constraint.clausesAndTimes.clauses, 
																 constraint.roles[j]);
					//	else {
							newClauses  = replaceLiterals(constraint.getID(), 
														newClauses == null ? constraint.clausesAndTimes.clauses : newClauses,  
														  constraint.roles[j]);
						//}
					}*/
					
					if (constraint.clausesAndTimes.clauses  != null && constraint.clausesAndTimes.clauses .size() > 0) 
						hardClauses.addAll(newClauses );
					
					
				}else {						
						System.err.println("Couldn't generate set of clauses for cardinality constraint in SS-HMER");
						system.print(false);
						System.exit(0);
						return null;
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 8 --> " + (testEndtime - testStartTime));
		/*printClauses(formatClauses(hardClauses));
		System.out.println("STEP 8 IS DONE ...");
		System.exit(0);*/
		
		
		/*Step 9: MS-HMER NEW VERSION encoding ...*/
		testStartTime = System.currentTimeMillis();
		if (system.merConstraints != null &&
			system.merConstraints.get(RBACMERConstraint.MS_HMER_CONST) != null && 
			(singleConstraint == -1 || singleConstraint == RBACMERConstraint.MS_HMER_CONST)){
			for (int i = 0; i < system.merConstraints.get(RBACMERConstraint.MS_HMER_CONST).length; i++){
				RBACMERConstraint constraint = system.merConstraints.get(RBACMERConstraint.MS_HMER_CONST)[i];
				if (constraint.clausesAndTimes != null){
					Vector<Vector<Integer>> newClauses= pruneClausesAndEliminateLiterals(system.user2sessions,constraint,/*constraint.getID(),*/session);
/*			        for (int j = 0; j < constraint.roles.length; j++){
						if (constraint.roleMapping.get(constraint.roles[j]) != null && 
										constraint.roleMapping.get(constraint.roles[j]).contains(session.getID()))
							newClauses = pruneClausesAndLiterals(constraint.getID(), 
																			  constraint.clausesAndTimes.clauses, 
																			  constraint.roles[j]);
						
						//else{ 
							newClauses = replaceLiterals(constraint.getID(), 
													newClauses == null ? constraint.clausesAndTimes.clauses : newClauses,  
														 constraint.roles[j]);
					//	}
					}*/
			        
			        if (constraint.clausesAndTimes.clauses != null && constraint.clausesAndTimes.clauses.size() > 0) 
			        		hardClauses.addAll(newClauses);
				}else {						
						System.err.println("Couldn't generate set of clauses for cardinality constraint in MS-HMER");
						system.print(false);
						System.exit(0);
						return null;
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 9 --> " + (testEndtime - testStartTime));
		//printClauses(formatClauses(hardClauses));
		//System.out.println("FINAL ...");
		//System.exit(0);
		
		
		testStartTime = System.currentTimeMillis();
		/*TODO HERE we have to handle the constraints of card type --> role activations....*/	
		/*Step 10: ACTIVATION CONSTRAINTS ...*/
		if (system.merConstraints != null &&
			system.merConstraints.get(RBACMERConstraint.ACTIVATION_CONST) != null){
			for (int i = 0; i < system.merConstraints.get(RBACMERConstraint.ACTIVATION_CONST).length; i++){
				int counter = 0;
				RBACRole role = system.merConstraints.get(RBACMERConstraint.ACTIVATION_CONST)[i].rSet[0];
				//for (Iterator<RBACSession> sIt = system.user2sessions.get(user).iterator();sIt.hasNext(); ){
				for (Map.Entry<RBACUser, Vector<RBACSession>> entry : system.user2sessions.entrySet()){
					Vector<RBACSession> sessions = (Vector<RBACSession>)entry.getValue();
					for (Iterator<RBACSession> sIt = sessions.iterator(); sIt.hasNext();){
						RBACSession ses = sIt.next();
						RBACRole[] roleSet = new RBACRole[ses.getActiveRoles().size()];
						if (!ses.getID().equals(session.getID()) && roleSet.length > 0 && 
															inTheRoleSet(ses.getActiveRoles().toArray(roleSet), role)){
							counter++;
						}
					}
				}
				if (counter == (system.merConstraints.get(RBACMERConstraint.ACTIVATION_CONST)[i].cardinality - 1)){
					temp = new Vector<Integer>();
					//if (isMaxSAT) temp.add(top);
					temp.add(-1 * returnVarIdForRBAC(role.getID()));
					hardClauses.add(temp);
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 10 --> " + (testEndtime - testStartTime));
		

		
		
		/*Here we have the other encoding ....*/
		/*The idea is the following:
		 * /--for all X combinations of U--/ THIS PART CAN BE COMPUTED OFF-LINE 
      			Z = X \cup {user(s)}  
      			S_{comb} = Sz \ {s}   --> Sz is computed from Z ...
      			for each r \in rset of RSoD{
         			if (r is not active in S_{comb}) then \neg r    -
         				-> THE ONLY DIFFERENCE from History based RSoD is here (that you iterate through all states)...
      			}
		   /--}  --/ THIS PART CAN BE COMPUTED OFF-LINE 
		 * */
		
		
		/*DRSoD Constraints ... DYNAMIC ...*/
		testStartTime = System.currentTimeMillis();
		if (system.rSODConstraints != null && system.rSODConstraints.size() > 0){
			RSoDConstraint[] sodConstraints = system.rSODConstraints.get(RSoDConstraint.D_RSoD_CONST);
			if (sodConstraints != null) System.out.println("SIZE OF USER combinations " + sodConstraints[0].getUserCombinations().length);
			if (sodConstraints != null)
				setClausesForRSoDConstraints(sodConstraints, system.users, system.user2sessions,session, true);
			sodConstraints = system.rSODConstraints.get(RSoDConstraint.H_RSoD_CONST);
			if (sodConstraints != null)
				setClausesForRSoDConstraints(sodConstraints, system.users, system.user2sessions,session, false);
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 11 --> " + (testEndtime - testStartTime));
		
		
		
		/*HRSoD Constraints ... HISTORY ...*/
		/*testStartTime = System.currentTimeMillis();
		if (system.rSODConstraints != null && system.rSODConstraints.size() > 0){
			RSoDConstraint[] hrsodContraints = system.rSODConstraints.get(RSoDConstraint.H_RSoD_CONST);
			System.out.println("SIZE OF USER combinations " + hrsodContraints[0].getUserCombinations().length);
			for (int k = 0; k < hrsodContraints.length; k++){
				RSoDConstraint rsod = hrsodContraints[k];
				for (int l= 0; l < rsod.getUserCombinations().length; l++){
					int[] userListInCombination = rsod.getUserCombinations()[l]; // THIS WOULD BE Z = X \cup {user(s)} ...
					temp = new Vector<Integer>();
					for (int s = 0; s < rsod.rSet.length; s++){
						RBACRole r = rsod.rSet[s];
						boolean foundTheRole = false;
						Vector<RBACSession> userSessions = system.user2sessions.get(session.user);
						for (int n = 0; n < userSessions.size() & !foundTheRole; n++){
							if (!session.getID().equals(userSessions.get(n).getID()) && 
									userSessions.get(n).isRoleActive(r))  foundTheRole = true;								
						}
						for (int m = 0; m < userListInCombination.length & !foundTheRole; m++){
							userSessions = system.user2sessions.get(system.users[userListInCombination[m]]);
							for (int n = 0; n < userSessions.size() & !foundTheRole; n++){
								if (!session.getID().equals(userSessions.get(n).getID()) && 
									userSessions.get(n).isRoleActive(r))  foundTheRole = true;								
							}
						}
						
						
						if (!foundTheRole) temp.add(-1 * returnVarIdForRBAC(r.getID()));
					}
					if (temp.size() > 0) hardClauses.add(temp);
				}
			}
			
			
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 11 --> " + (testEndtime - testStartTime));
		*/
	
		
		
		
/*		testStartTime = System.currentTimeMillis();
		Step 11: SoD Constraints ...
		18.12.2012
		if (system.rSODConstraints != null && system.rSODConstraints.length > 0){
			for (int k = 0; k < system.rSODConstraints.length; k++){
				if (system.rSODConstraints[k].clausesAndTimes.clauses != null && system.rSODConstraints[k].clausesAndTimes.clauses.size() > 0) {
<<<<<<<<------------------Can we do something about this addAll!!!!!! ----------------------------------------->>>>>>>>>>>>>>>>>>>>>
					hardClauses.addAll(system.rSODConstraints[k].clausesAndTimes.clauses);
					for (int t = 0 ; t < system.rSODConstraints[k].rSet.length; t++){
						if (session.user.isAssignedRole(system.rSODConstraints[k].rSet[t])){
						   temp = new Vector<Integer>();
						   temp.add(-1 * returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID() + "_" +  session.getID()));
						   temp.add(returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID()));
						   hardClauses.add(temp);
						   temp = new Vector<Integer>();
						   temp.add(returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID() + "_" +  session.getID()));
						   temp.add(-1 * returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID()));
						   hardClauses.add(temp);
						}
					}
				}else {						
					System.err.println("Couldn't generate set of clauses for cardinality constraint in SoD");
					system.print(false);
					System.exit(0);
					return null;
				}					
			}		
	
			 RSSoD Enforcement: RSSoD ({r1,..,rn}, k) : make sure that there does not exist a 
			 * set of fewer than k users that cover n roles in RSSoD definition
			 * 
			 * OLD ...
			 * 1. We need to find all k-1 combinations (X) of users (U)
			 * 2. Find the union set of their sessions Sx
			 * 3. Apply the formula: for all i = 1 to n and 
			 * 						 for all s \in Sx \neg ri \in \roles(\rho_t(s))
			 * 
		
			 for (Map.Entry<RBACUser, Vector<RBACSession>> entry : system.user2sessions.entrySet()){
				 Vector<RBACSession> sList = (Vector<RBACSession>) entry.getValue();
				 for (Iterator<RBACSession> itSes = sList.iterator(); itSes.hasNext();){
			    	RBACSession s = (RBACSession) itSes.next();
			   		for (int k = 0; k < system.rSODConstraints.length; k++){
			   			for (int t = 0 ; t < system.rSODConstraints[k].rSet.length && 
			   													system.rSODConstraints[k].clausesAndTimes.clauses.size() > 0; t++){
			   				if (!session.getID().equals(s.getID())){
			   					temp = new Vector<Integer>();
		    					if (s.isRoleActive(system.rSODConstraints[k].rSet[t])){
		    						temp.add(returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID() + "_" + 
												s.getID()));
		    					}else{
			    					temp.add(-1 * returnVarIdForRBAC(system.rSODConstraints[k].rSet[t].getID() + "_" + 
												s.getID()));
			    				}
			    				hardClauses.add(temp);
			    			}
			    		}
			    	}
				 }
			 }
		    System.out.println("FINISHED !!!!");
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		writeThisToPerformanceFile("Step 11 --> " + (testEndtime - testStartTime));*/
		
		/*NOT NECESSARY FOR NOW, I tried this for a non-empty session start...*/
		/*for (Iterator it = session.activeRoles.iterator(); it.hasNext();){
			RBACRole r = (RBACRole) it.next();
			temp = new Vector();
			temp.add(returnVarIdForRBAC(r.getID()));
			clauses.add(temp);
		}*/
		
		testStartTime = System.currentTimeMillis();
			if (objective == 0){ //min
				//Add not(p) s.t. p \in Pub \ Plb
				for (int i = 0; i < upperbound.length; i++){
					boolean found = false;
					for (int j = 0; j < lowerbound.length; j++){
						if (upperbound[i].getID().equals(lowerbound[j].getID())){
							found = true;
							break;
						}
					}
					if (!found){
						temp = new Vector();
						//temp.add(1);
						temp.add(-1 * returnVarIdForRBAC(upperbound[i].getID()));
						softClauses.add(temp);
					}
				}
			}
			else if (objective == 1){ //max
				//Add p s.t. p \in Pub \ Plb
				for (int i = 0; i < upperbound.length; i++){
					boolean found = false;
					for (int j = 0; j < lowerbound.length; j++){
						if (upperbound[i].getID().equals(lowerbound[j].getID())){
							found = true;
							break;
						}
					}
					if (!found){
						temp = new Vector();
						//temp.add(1);
						temp.add(returnVarIdForRBAC(upperbound[i].getID()));
						softClauses.add(temp);
					}
				}
			}
			testEndtime = System.currentTimeMillis();
			ds.addValue(testEndtime - testStartTime);

			System.out.println("FINAL VALUE FOR TRANSLATION ------------------------ > " + ds.getSum());
			/*System.exit(0);*/
			
		/*printClauses(formatClauses(hardClauses));
		printClauses(formatClauses(softClauses));	
		System.exit(0);*/
		return new ResultContainer(ds, true);
	}
	
	private boolean inTheRoleSet(RBACRole[] rset, RBACRole role){
		for (int i = 0; i < rset.length; i++){
			if (rset[i].getID().equals(role.getID())) return true;
		}
		return false;
	}
	
	
	/*public RBACRole[] computeQSet(RBACRole[] rset, Vector<RBACState> previousStates, RBACSession session, boolean excludeSession){
		Vector<RBACRole> roles = new Vector<RBACRole>();
		Vector<RBACRole> actRoles = alreadyActiveRoles(previousStates, session, excludeSession);
		for (int i = 0; i < rset.length; i++){
			RBACRole roleOfRSET = rset[i];
			boolean found = false;
			for (Iterator it = actRoles.iterator(); it.hasNext();){
				RBACRole alreadyActRole = (RBACRole)it.next();
				if (alreadyActRole.getID().equals(roleOfRSET.getID())){
					found = true;
					break;
				}
			}
			if (!found) roles.add(rset[i]);
		}
		
		RBACRole[] fRoles = new RBACRole[roles.size()];
		roles.toArray(fRoles);
		return fRoles;
	}*/
	
	public void addSessionRoles(RBACSession sess, Vector<RBACRole> roles){
		Vector<RBACRole> activeRoles = (Vector<RBACRole>) sess.getActiveRoles();
		for (Iterator itRole = activeRoles.iterator(); itRole.hasNext();){
			RBACRole r = (RBACRole)itRole.next();
			RBACRole[] tempRole2 = new RBACRole[roles.size()];
			if (!inTheRoleSet(roles.toArray(tempRole2),r)) roles.add(r);
		}
	}
	
//	/*This method finds the set of already active roles...*/
//	public RBACRole[] alreadyActiveRoles(Vector<RBACState> previousStates, RBACSession session, 
//																		   boolean excludeSession, 
//																		   boolean sameSession){ 
//		//previousStates represents the k-1 states... 
//		Vector<RBACRole> roles = new Vector<RBACRole>();
//		
//		for (Iterator it = previousStates.iterator(); it.hasNext();){
//			RBACState state = (RBACState)it.next();
//			for (Iterator itSes = state.getSetOfSessions() .iterator(); itSes.hasNext();){
//				RBACSession sess = (RBACSession) itSes.next();
//				if (sameSession && sess.getID().equals(session.getID())){//SS-DMER and SS-HMER
//					addSessionRoles(sess,roles);
//					break;
//				}else if (!sameSession && sess.user.getID().equals(session.user.getID())){
//					if (excludeSession && sess.getID().equals(session.getID())){ //MS-DMER and MS-HMER
//						continue;
//					} else { 
//						addSessionRoles(sess,roles);
//					}
//				} else continue;
//			}
//		} 
//		
//		RBACRole[] fRoles = new RBACRole[roles.size()];
//		roles.toArray(fRoles);
//		//System.out.println("Q computed ... State " + previousStates.lastElement().getID() + " Session " + session.getID());
//		//for (int i = 0; i < fRoles.length; i++)
//			//System.out.print(fRoles[i].getID() + " ");
//		return fRoles;
//	}


	
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

	
	public void toMaxSATCNF(int[][] hClauses, int[][] sClauses, int varNum, String fileName){
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
			int top = varNum + 100;
			output.write("p wcnf " + varNum + " " +  
									(hClauses.length + (sClauses == null ? 0 : sClauses.length)) + " " + top + "\n");
			if (hClauses != null)
			for (int i = 0; i < hClauses.length; i++){
				String str = String.valueOf(top);
				for (int j = 0; j < hClauses[i].length; j++){
					str += " " + hClauses[i][j]; 	
				}
				str += " 0"; 
				output.write(str + "\n");
			}
			if (sClauses != null)
				for (int i = 0; i < sClauses.length; i++){
					String str = "1";
					for (int j = 0; j < sClauses[i].length; j++){
						str += " " + sClauses[i][j]; 	
					}
					str += " 0"; 
					output.write(str + "\n");
				}
			output.close();
		} catch(IOException e){
			System.err.println("IOException in to MaxSATCNF ...");
		}
	}
	
	
/*	generate RBAC state
	public RBACState generateRBACState(){
		
		
		return null; 
	}
		*/
	
	
	
}
