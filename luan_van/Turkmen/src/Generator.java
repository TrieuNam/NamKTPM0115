import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.sat4j.specs.ContradictionException;

class Session2Solutions{
	Vector<int[]> triedSolutions;
	RBACSession session;
	public Session2Solutions(RBACSession s){
		session = s;
		triedSolutions = new Vector<int[]>();
	}
	public Vector<int[]> getSolutions(){
		return triedSolutions;
	}
	public RBACSession getSession(){
		return session;
	}
	public void addSolution(int[] sol){
		triedSolutions.add(sol);
	}
	public int[] removeSolution(int[] sol){
		for (Iterator it = triedSolutions.iterator(); it.hasNext();){
			int[] solution = (int[]) it.next();
			boolean notFound = false;
			for (int i = 0; i < solution.length; i++){
				if (sol[i] != solution[i]) { notFound = true; break;} 
			}
			if (!notFound) {
				triedSolutions.remove(solution);
				return sol;
			}
		}
		return null;
	}
}

public class Generator {
	
	
	Vector<RBACElement[]> selectedElements = new Vector<RBACElement[]>();
	Vector<RBACMERConstraint[]> selectedConstraints = new Vector<RBACMERConstraint[]>();   
	
	HashMap<RBACState, Vector<Session2Solutions>> mapStateToSessions ;
	
	private final long sizeLimit = 147483647;

	//MaxSATReasoner satr;
	MiniSATReasoner satr;
	public Generator(){
		satr = new MiniSATReasoner(false);
		//satr = new MaxSATReasoner();
	}
	
	
	/*plus --> there will be one more: +1*/
	public static int randPosINT(int mod, boolean plus){
		int res ;
		Random gen = new Random();
		res = gen.nextInt();
		res = ((res < 0 ? -1 * res : res) % mod);
		if (plus)
			res = res + 1;
		
		return res;
	}
	
	private long fact(int num){
		long result = 1;
		for (int i = num; i > 1; i--) {
			result = result * i;
			if (result > 2147483647) return 1000000;
		}
		return result;
	}
	
	private boolean allTried(Vector<RBACElement[]> elements, int threshold, int permutation){
		long size = fact(threshold) / (fact(threshold - permutation) * fact(permutation));
		if (elements.size() == size) return true;
		return false;
	}
	
	/*VERIFIED ....
	 * Given array is equal to the other (their contents), they must be non empty!!!*/
	public boolean arrayEqual(RBACElement[] ar1, RBACElement[] ar2){
		if (ar1.length != ar2.length) return false;
		boolean found = false;
		for (int i = 0; i < ar1.length; i++){
			for (int j = 0; j < ar2.length; j++){
				if (ar1[i].getID().equals(ar2[j].getID())){found = true; break;}
				else found = false;
			}
			//System.out.print(ar1[i].getID() + " ");
			if (!found) {return false;}
		}
		return true;
	}
	
	/*VERIFIED....
	 * Given element is an element of the given set?? They must be non empty!!!*/
	private boolean isAMember(Vector<RBACElement[]> set, RBACElement[] member){
		for (Iterator it = set.iterator(); it.hasNext();){
			RBACElement[] element = (RBACElement[]) it.next();
			if (arrayEqual(member,element)) return true;
		}
		return false;
	}
	
	

	
	/* This function returns a subset of the given set based on the given max and minimum values...
	 * 1. It makes sure that the generated subset has not been generated before (based on selectedElements)
	 * 2. The input values must comply with: max > min
	 * 3. Factorial (max, min<rand()<max) must be bigger than 0
	 * 4. All possible random values in the range min and max are tried... 
	 * ....
	 * VERIFIED*/
	private RBACElement[] createEntropy(RBACElement[] elements, int max, int min, int type){
		Vector<Integer> sizes = new Vector<Integer>();
		
		if (max > elements.length) max = elements.length;
		while (sizes.size() < (max - min)){
			int num = randPosINT(max, true);
			while (num <= min) num = randPosINT(max, true);
			if (!sizes.contains(num)) sizes.add(num);
			
			if ((fact(max - num) * fact(num)) == 0) {
				System.out.println("Max : " + max + " Num : " + num + "fact(max - num)" + fact(max - num) + "fact(num)" + fact(num));
				System.exit(0);
			}
			//long fact1 = fact(elements.length);
			//long fact2 = fact(elements.length - num);
			//long fact3 = fact(num);
			//long sizeBigInt = fact1 / (fact2 * fact3) ; // The number of possible combinations!!!
			Vector<RBACElement[]> usedSequence = new Vector<RBACElement[]>();
			RBACElement[] newElements = null;
			Vector<Integer> genNumber = new Vector<Integer>();
			
			do{// Try all possible combinations (sequences)
				if (type == 1) newElements = new RBACPermission[num];
				else if (type == 2) newElements = new RBACRole[num];
				boolean nonEmpty = false;
				for (int i = 0; i < num; i++){
					int ranNum = randPosINT(elements.length, false);
					while(genNumber.contains(ranNum)){ranNum = randPosINT(elements.length, false);}
					//Dont select the same permission once more...
					newElements[i] = elements[ranNum];
					genNumber.add(ranNum);
					nonEmpty = true;
				}
				genNumber.clear();
				if (nonEmpty && !isAMember(selectedElements,newElements)){ 
					selectedElements.add(newElements);  
					return newElements;
				} else if (nonEmpty && !isAMember(usedSequence,newElements)) usedSequence.add(newElements);
			}while(usedSequence.size()  > sizeLimit);
		}	
		//if (usedSequence.size() >= size && !found) {System.out.println("NULL IS returned ...");return null;}
		System.out.println("NULL IS returned ...");
		
		return null;//newElements;
	}
	
/*	private RBACElement[] createEntropy(RBACElement[] elements, int max, int min, int type){
		Vector<Integer> sizes = new Vector<Integer>();
		while (sizes.size() < (max - min)){
			int num = randPosINT(max, true);
			while (num < min) num = randPosINT(max, true);
			if (!sizes.contains(num)) sizes.add(num);
			
			if ((fact(max - num) * fact(num)) == 0) {
				System.out.println("Max : " + max + " Num : " + num + "fact(max - num)" + fact(max - num) + "fact(num)" + fact(num));
				System.exit(0);
			}
			long size = fact(max) / (fact(max - num) * fact(num)); // The number of possible combinations!!!
			Vector<RBACElement[]> usedSequence = new Vector<RBACElement[]>();
			RBACElement[] newElements = null;
			Vector<Integer> genNumber = new Vector<Integer>();
		
			do{// Try all possible combinations (sequences)
				if (type == 1) newElements = new RBACPermission[num];
				else if (type == 2) newElements = new RBACRole[num];
				boolean nonEmpty = false;
				for (int i = 0; i < num; i++){
					int ranNum = randPosINT(max, false);
					while(genNumber.contains(ranNum)){ranNum = randPosINT(max, false);}
					//Dont select the same permission once more...
					newElements[i] = elements[ranNum];
					genNumber.add(ranNum);
					nonEmpty = true;
				}
				genNumber.clear();
				if (nonEmpty && !isAMember(selectedElements,newElements)){ 
					selectedElements.add(newElements);  
					return newElements;
				} else if (nonEmpty && !isAMember(usedSequence,newElements)) usedSequence.add(newElements);
			}while(size > usedSequence.size());
		}	
		//if (usedSequence.size() >= size && !found) {System.out.println("NULL IS returned ...");return null;}
		System.out.println("NULL IS returned ...");
		return null;//newElements;
	}*/

	

	

	
	
	/*This function randomly selects a set of roles and creates a given type of constraint from it*/
	private RBACMERConstraint[] constraintEntropy(RBACRole[] allRoles, int conNum,int rsetSize, int type){
		Vector<RBACMERConstraint> constraints = new Vector<RBACMERConstraint>();
		
		for (int i = 0; i < conNum; i++){
			RBACRole[] rset = (RBACRole[])createEntropy(allRoles, rsetSize, rsetSize - 1, 2); 
			
			if (rset != null){
				constraints.add(new RBACMERConstraint(type + "_MER" + i, rset, 1 + randPosINT(rset.length - 1, true), type)); 
			//we support only MER constraints...
			} else {
				System.out.println("Role generation was not possible for constraints...");
				break;
			}
		}
		selectedElements.clear();
		if (constraints.size() != 0){
			RBACMERConstraint[] consts = new RBACMERConstraint[constraints.size()];
			return constraints.toArray(consts);
		} else return null;
	}
	
	
	
	private boolean alreadyAddedRole(Vector<RBACRole> roleSet, RBACRole role){
		for (Iterator it = roleSet.iterator(); it.hasNext();){
			RBACRole r = (RBACRole) it.next();
			if (r.getID().equals(role.getID())) return true;
		}
		return false;
	}
	
	private boolean alreadyAddedUser(Vector<RBACUser> userSet, RBACUser user){
		for (Iterator it = userSet.iterator(); it.hasNext();){
			RBACUser u = (RBACUser) it.next();
			if (u.getID().equals(user.getID())) return true;
		}
		return false;
	}
	
	/*This method chooses a set of roles from given set of roles and constraints...
	 * Here I do iterations for the constraint types because sometimes I 
	 * just have two (or a different number) types of constraints and I want this 
	 * randomization works fine for that case without giving all the constraint types
	 * */
	/*We assign each user a set of roles that enables (gives the possibility) to 
	 * violate a MER constraints. Assignment happens so...*/
	public void chooseRolesWithMER(RBACRole[] roles, 
											  HashMap<Integer, RBACMERConstraint[]> merConstraints,
											  Vector<RBACRole> rolesSelected){
		//Vector<RBACRole> rolesChosen = new Vector<RBACRole>();
		RBACMERConstraint constraint = null;
		if (merConstraints != null){
			int type = randPosINT(merConstraints.size(), false); 
			int i = 0;
			for (Map.Entry entry : merConstraints.entrySet()){
				int consIndex = randPosINT(((RBACMERConstraint[])entry.getValue()).length, false);
				constraint = ((RBACMERConstraint[]) entry.getValue())[consIndex];
				if (i == type) break;
				i++;
			}
			for (i = 0; i < constraint.cardinality; i++){
				if (!alreadyAddedRole(rolesSelected, roles[i]))
					rolesSelected.add(constraint.rSet[i]);
			}
		}
		int moreRoles = constraint != null ? randPosINT(roles.length - constraint.cardinality, false) :
											 randPosINT(roles.length - 1, true);
		//Vector<RBACRole> rolesAdded = new Vector<RBACRole>();
		int i = 0;
		int k = 0;
		while (i < moreRoles && k < roles.length){
			if (!alreadyAddedRole(rolesSelected, roles[k])){
				rolesSelected.add(roles[k]);
				i++;
			}
			k++;
		}
		
		//RBACRole[] rolesSelected = new RBACRole[rolesChosen.size()];
		//return rolesChosen;
	}
	
	
	/*SOD support...*/
	/* 17.12.2011
	 * Used for choosing a set of roles from rset of SoD definition...
	 * */
/*	public RBACRole[] chooseRolesFromSoD(RBACRole[] roles){
		Vector<RBACRole> rolesSelected = new Vector<RBACRole>();
		int nOfRoles = randPosINT(roles.length - 1, true);
		int i, k = 0;
		while (k < nOfRoles){
			i = randPosINT(roles.length, false);
			if (!alreadyAddedRole(rolesSelected, roles[i])) { rolesSelected.add(roles[i]); k++;}
		}
		return rolesSelected.toArray(new RBACRole[rolesSelected.size()]);
	}*/
	
	/*SOD support...*/
	/* 17.12.2011
	 * This method must choose num users from the set users ...*/
	public RBACUser[] chooseRandomUsers(RBACUser[] users, int num){
		if (num > users.length) {
			System.out.println("It is not possible to generate " + num + " random users from " + users.length );
			return null;
		}
		Vector<RBACUser> usersChosen = new Vector<RBACUser>();
		//int nOfUsers = randPosINT(users.length - 1, true);
		int i = 0;
		while (usersChosen.size() < num){
			i = randPosINT(users.length, false);
			if (!alreadyAddedUser(usersChosen, users[i]))  usersChosen.add(users[i]);
		}
		
		RBACUser[] usersSelected = new RBACUser[usersChosen.size()];
		return usersChosen.toArray(usersSelected);
	}
	
	
	/*SOD Support*/
	/*This method chooses a set of permissions from teh given set for SoD generation*/
	public RSoDConstraint generateSoD(String id, RBACRole[] roles, int rsetsize, int card, int rsodType){
		Vector<RBACRole> roleSet = new Vector<RBACRole>();
		//Vector<Integer> roleList = new Vector<Integer>();
		int i = 0;
		while (i < rsetsize){
			int k = randPosINT(roles.length, false);
			while (roleSet.contains(roles[k]))
				k = randPosINT(roles.length, false);
			//roleList.add(k);
			roleSet.add(roles[k]);
			i++;
		}
		RBACRole[] pArray = new RBACRole[roleSet.size()];
		return new RSoDConstraint(id,roleSet.toArray(pArray), card,rsodType);
	}
	
	
	public void findAllSolutionsOfClauses(int[][] clauses, int size){
		System.out.println("ALL SOLUTIONS START ..Clause num:" + clauses.length);
		MiniSATReasoner satSolver = new MiniSATReasoner(false);
		//satr.printClauses(t.formatClauses(t.hardClauses));
		satSolver.findAllSolutionsWithNext(clauses, size);
		satSolver.resetSolver();
		System.out.println("Clear clauses called");
		//satr.printClauses(t.formatClauses(t.hardClauses));
		satSolver.findAllSolutionsWithNext(clauses, size);
		System.out.println("ALL SOLUTIONS END..Clause num:" + size);
	}
	
	/*HISTORY....*/
	
	/*State related functions ...
	 * We consider that initially all roles of a session are active. A change in a state can be done by two events:
	 * 1. Role activation 
	 * 2. Role deactivation
	 * 
	 * There are two strategies for the generation of state0, 
	 * 1. empty set of roles for each session, 
	 * 2. roles are selected randomly and the generated state verified
	 * */
	/*private RBACState initializeHistory(HashMap<RBACUser,Vector<RBACSession>> u2sessions){
		// All roles of a session are active at state s0....
		if (u2sessions == null || u2sessions.size() == 0) return null;
		RBACState initialState = new RBACState("State0",u2sessions);
		//history.add(initialState);
		return initialState;

	}*/
	
		
	
	
	private RBACRole[] findRolesFromSolution(int[] solution, RBACUser user, Translator t){
		Vector<RBACRole> roles = new Vector<RBACRole>();
		boolean roleAdded = false;
		//t.printClauses(t.formatClauses(t.hardClauses));
		
		//for (Map.Entry<String, Integer> entry: t.varIds.entrySet()) 
		//	System.out.println(entry.getKey() + " " + entry.getValue());
		
		//int[][] sol = new int[1][];
		//sol[0] = solution;
		//t.printArray(sol);
		//MaxSATReasoner.waitForInput();
		for (int i = 0; i < solution.length; i++){
			if (solution[i] > 0){
				// HERE how this works? Finds user's assigned roles and their match in the solution and adds to the roles...
				for (int k = 0; k < user.assignedRoles.length; k++){
					RBACRole r = user.assignedRoles[k];
					//System.out.println("Solution[i] " + solution[i]);
					if (t.findIdFromVarID(solution[i]).equals(r.getID())){
						roles.add(r);
						roleAdded = true;
					}
				}
			}
		}
		if (roles.size() == 0 && !(ALL_DROP_ACCEPTED && roleAdded)) return null;
		else{
			RBACRole[] roleArray = new RBACRole[roles.size()];
			return roles.toArray(roleArray);
		}
	}
	
	
	
	
	
	/* 
	 * This function gets an RBAC system, a session and a state (state is the k-1 th state)
	 * 
	 * */
	private Translator generateNewClauses(RBAC system, RBACSession session){
		//requestedPermissions = new RBACPermission[]{perms[0], perms[5]};
		RBACPermission[] lowerbound = new RBACPermission[]{}; //Empty set
		RBACPermission[] upperbound = system.getPermissions();
		Translator t = new Translator(system);
		//t.clearClauseSets();
		if (t.generateClausesForOurs(system, session, 
				   					upperbound, lowerbound,3, -1, true).getResult()){ 
			//t.printClauses(t.formatClauses(t.clauses)); System.exit(0);
			return t;
		}
		return null;

	}

	public int[] addASolutionToTheMap(RBACState state, RBACSession session, int[] solution){
		for (Map.Entry entry : mapStateToSessions.entrySet()){
			RBACState st = (RBACState) entry.getKey();
			if (st.getID().equals(state.getID())){
				Vector<Session2Solutions> sessions = (Vector<Session2Solutions>) entry.getValue();
				for (int j = 0; j < sessions.size(); j++){
					Session2Solutions s2s = sessions.get(j);
					if (session.getID().equals(s2s.getSession().getID())){
						s2s.addSolution(solution);
						return solution;
					}
				}
			}
		}
		return null;
	}
	
	public int[] removeSolutionFromTheMap(RBACState state, RBACSession session, int[] solution){
		for (Map.Entry entry : mapStateToSessions.entrySet()){
			RBACState st = (RBACState) entry.getKey();
			if (st.getID().equals(state.getID())){
				Vector<Session2Solutions> sessions = (Vector<Session2Solutions>) entry.getValue();
				for (int j = 0; j < sessions.size(); j++){
					Session2Solutions s2s = sessions.get(j);
					if (session.getID().equals(s2s.getSession().getID())){
						return s2s.removeSolution(solution);
					}
				}
			}
		}
		return null;
	}
	
	
	
	// This has to add to mapStateToSessions ...
	public int[] findASolutionWithRoles(RBAC system, RBACState state, RBACSession session,Translator t) 
																throws ContradictionException{
	try{
		if (session == null) return null;
		//SATReasoner satr = new SATReasoner(1);
		
		satr.resetSolver();
	
		//satr.addClauses(t.hardClauses, t.softClauses, t.varIds.size());
		//t.printClauses(t.formatClauses(t.hardClauses));
		//System.out.println("var number " + t.varIds.size());
		int[][] hardClauses = t == null ? null : t.formatClauses(t.hardClauses);
		int[][] softClauses = null;
		
		hardClauses = addPreviousSolutions(state, session, hardClauses, softClauses, t);
		System.out.println("Add previous solutions worked ...");
		
		//As soon as the clauses are added, the contradiction is thrown...
		//satr.addClauses(hardClauses,system.getVarIDs().size());
		if (!satr.addClauses(hardClauses,t.varIds.size())) return null;
		
		int solutionTry = 0;
		//satr.findAllSolutionsWithNext(t.formatClauses(t.hardClauses), t.varIds.size());
		int[] solution =  satr.findTheNextSolution(randPosINT(SOLUTION_ORDER, true));
													  //satr.findASolutionWithNegation(sessionPreSolution);
		while (solution != null && solutionTry < SOLUTION_THRESHOLD){
			//System.out.println("HERe WE GOT STUCK...active roles of session" + session.getActiveRoles().size());
			
			/*if (session.getActiveRoles().size() > 0) {
					System.out.println("solutionTry " + solutionTry + "session " + session.getID() 
														+ " has role " + session.getActiveRoles().get(0).getID()
														+ "active");
			}*/
			//t.printClauses(t.formatClauses(t.hardClauses));
			//System.out.println("var number " + t.varIds.size() + "Solution size :" + solution.length);
			RBACRole[] rolesInSolution = findRolesFromSolution(solution, session.user, t);
			solutionTry++;
			/*if ( rolesInSolution != null) {
				int[][] sol = new int[1][];
				sol[0] = solution;
				t.printClauses(sol);
			}*/
			//System.out.println("Solution found");
			//findAllSolutionsOfClauses(t.formatClauses(t.hardClauses), t.varIds.size());
			
			RBACRole[] tempRole = new RBACRole[session.getActiveRoles().size()];
			//System.out.println("Number of roles in session:" + session.getActiveRoles().size());
			//System.out.println("Number of roles in solution:" + rolesInSolution.length);
			if (rolesInSolution != null){	
				/*for (int z = 0; z < session.getActiveRoles().size();z++) 
						System.out.println(session.getActiveRoles().get(z).getID());
				for (int z = 0; z < rolesInSolution.length;z++) 
					System.out.println(rolesInSolution[z].getID());*/
				if (!arrayEqual(session.getActiveRoles().toArray(tempRole), rolesInSolution) &&
																	rolesInSolution.length >= 0) {
					//System.out.println("CHANGING THE ROLES IN SESSION ");
					RBACMERConstraint[] multiSessionConstraints = system.merConstraints != null ? 
														system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST) :
														null;
					session.dropAllActiveRoles(multiSessionConstraints);
					
					/*RSoD Addition, check this one too...*/
					RSoDConstraint[] drsodConstraints = system.rSODConstraints != null ?
											 system.rSODConstraints.get(RBACConstraint.D_RSoD_CONST) :
											 null;
					session.dropAllActiveRoles(drsodConstraints);
					for (int j = 0; j < rolesInSolution.length; j++)   session.addActiveRole(rolesInSolution[j]);
					if (rolesInSolution.length == 0) System.err.println("EMPTY SET OF ROLES ....");
					return addASolutionToTheMap(state,session,solution);
				} else{
					//findAllSolutionsOfClauses(t.formatClauses(t.hardClauses), t.varIds.size());
					//System.out.println("Session is not changed with the solution");
					//System.out.println("Session role size:" + tempRole.length + " roles:" + tempRole[0].getID() +
					//				   "roles in solution size : " + rolesInSolution.length + " roles:" + rolesInSolution[0].getID());
					//t.printClauses(t.formatClauses(t.hardClauses));
					if (solution != null) addASolutionToTheMap(state,session,solution);
					solution = satr.findASolutionWithNegation(hardClauses, t.varIds.size(), 
															  solution,randPosINT(SOLUTION_ORDER, true));
					if (solution == null) 
						System.out.println("Solution from the negation was NULL!!");
				}
			} else {
				//System.out.println("SOLUTION is in " + session.getID() + "Role length:" + tempRole.length);
				if (solution != null) addASolutionToTheMap(state,session,solution);
				solution = satr.findASolutionWithNegation(hardClauses, t.varIds.size(), 
														  solution,randPosINT(SOLUTION_ORDER, true));
				
			}
		}		
	}catch(ContradictionException e){
		System.err.println("Exception in findASolutionWithRoles .... --> SYSTEM.EXIT???" + e.getMessage());
		System.exit(0);
	}
	return null;
	
	}

	private Vector<RBACSession> findAllSessions(HashMap<RBACUser, Vector<RBACSession>> u2sessions){
		Vector<RBACSession> allSessions = new Vector<RBACSession>();
		for (Map.Entry entry : u2sessions.entrySet()){
			allSessions.addAll((Vector<RBACSession>) entry.getValue());
		}
		return allSessions;
	}

	/*This method chooses a session based on previously selected session-solutions pairs.
	 * It looks for a session that has no session-solutions pair...
	 * */
	public int chooseADifferentSession(RBACState state){
		boolean stateFound = false;
		Vector<RBACSession> allSessions = findAllSessions(state.getSetOfSessions());
		for(Map.Entry entry : mapStateToSessions.entrySet()){
			RBACState st = (RBACState) entry.getKey();
			if(st.getID().equals(state.getID())){
				stateFound = true;
				Vector<Session2Solutions> s2sList = (Vector<Session2Solutions>) entry.getValue();
				if(s2sList == null || s2sList.size() == 0){
					int selectedSession = randPosINT(state.getSetOfSessions().size(), false);
					s2sList = new Vector<Session2Solutions>();
					s2sList.add(new Session2Solutions(allSessions.get(selectedSession)));
					return selectedSession;
				}else{//try to choose a random session...
					for (int i = 0; i < state.getSetOfSessions().size(); i++){
						int index = randPosINT(state.getSetOfSessions().size(),false);
						boolean foundTheRightSession = true;
						for (int j = 0; j < s2sList.size(); j++){
							Session2Solutions s2s = s2sList.get(j);
							if (allSessions.get(index).getID().equals(s2s.getSession().getID())){
								foundTheRightSession = false;
							}
						}
						if (foundTheRightSession) {
							s2sList.add(new Session2Solutions(allSessions.get(index)));
							return index;
						}
					}
				}
				
			}
		}
		if (mapStateToSessions.size() == 0 || !stateFound){
			Vector<Session2Solutions> sessions = new Vector<Session2Solutions>();
			int selectedSession = randPosINT(allSessions.size(), false);
			sessions.add(new Session2Solutions(allSessions.get(selectedSession)));
			mapStateToSessions.put(state, sessions);
			return selectedSession;
		}
		return -1;
	}
	
	
	//This method adds previously tried solutions to the Map... 
	public int[][] addPreviousSolutions(RBACState state, RBACSession session, int[][] hardClauses, 
																			  int[][] softClauses, Translator t){
		Vector<int[]> resultingClauses = new Vector<int[]>(hardClauses.length + (softClauses == null ? 0 : softClauses.length));
		for (int i = 0; i < hardClauses.length; i++){
			resultingClauses.add(hardClauses[i]);
		}
		if(softClauses != null)
		for (int i = 0; i < softClauses.length; i++){
			resultingClauses.add(softClauses[i]);
		}
		//boolean test = false;
		for (Map.Entry entry : mapStateToSessions.entrySet()){
			RBACState st = (RBACState) entry.getKey();
			if (st.getID().equals(state.getID())){
				Vector<Session2Solutions> s2s = (Vector<Session2Solutions>) entry.getValue();
				for (int i = 0; i < s2s.size(); i++){
					RBACSession ses = s2s.get(i).getSession();
					if (ses.getID().equals(session.getID())){
						for (int k = 0; k < s2s.get(i).getSolutions().size();k++){
							int[] sol = s2s.get(i).getSolutions().get(k);
							int[] negSolution = new int[sol.length];
							//System.out.println("PREVIOUS SOLUTION ");
							for (int j = 0; j < sol.length; j++){
								negSolution[j] = -1 * sol[j];
								//System.out.print(negSolution[j] + " ");
							}
							//System.out.print("FINISHED ...");
							//test = true;
							resultingClauses.add(negSolution);
						}
					}
				}
			}
		}
		int[][] result = new int[resultingClauses.size()][];
		resultingClauses.toArray(result);
		if (resultingClauses.size() == (hardClauses.length + (softClauses == null ? 0 : softClauses.length))) {
			System.out.println("No New clauses ...");
		}
		else System.out.println("THERE WERE SOFT CLAUSES!!!");//t.printClauses(result);
		//if (test) System.exit(0);
		return result; 
		
	}
	
	/*This function recursively generates the given number of states*/
	private boolean generateHistory(RBACState stMinusOne, RBAC system, int num)	
																	throws ContradictionException{
		
		if (num == 1) return true;
		
		int sessionIndex = chooseADifferentSession(stMinusOne);
		if (sessionIndex == -1) {return false;}
		
		HashMap<RBACUser,Vector<RBACSession>> u2sessions = 
						stMinusOne.createDeepCloneForU2S(stMinusOne.getSetOfSessions());
		Vector<RBACSession> allSessions = findAllSessions(u2sessions);
		int [] solution;
		RBACSession session = allSessions.get(sessionIndex);
		//System.out.println("SELECTED SESSION " + session.getID());
		do{
			solution = null;
			Translator t = generateNewClauses(system, session);
			int[][] hardClauses = t == null ? null : t.formatClauses(t.hardClauses);
			int[][] softClauses = null; //t == null || t.softClauses == null ? null : t.formatClauses(t.softClauses);
			if (hardClauses != null) { 
				//System.out.println("Before the previous solutions ... ");
				//t.printClauses(hardClauses); 
				//hardClauses = addPreviousSolutions(stMinusOne, session, hardClauses, softClauses, t);
				//t.printClauses(hardClauses);
				//System.out.println("After previous solutions ...");
				solution = findASolutionWithRoles(system, stMinusOne, session, t);
				//else {System.out.println("SOLUTION WAS NULL!!!"); System.exit(0);}
			}
			if (solution == null){ System.out.println("Solution was nulll");
			//MaxSATReasoner.waitForInput();
				int index = chooseADifferentSession(stMinusOne);
				session = index == -1 ? null : allSessions.get(index);
			}//else System.out.println("Selected session helped ...");
		}while(session != null && solution == null);
		
		//if (solution == null) {System.out.println("SOLUTION WAS NULL!!!");}
		RBACState st = null;
		if (solution != null){
			//System.exit(0);
			st = new RBACState("State" + system.getHistory().size(),u2sessions);
			//removeSolutionFromTheMap(stMinusOne,session,solution);
			//addASolutionToTheMap(stMinusOne, session, solution);
			system.addPastState(st, session);
			System.out.println("State: " + st.getID() + "session: " + session.getID());
			return generateHistory(st,system, num - 1);
		} else{
			system.dropState(stMinusOne);
			if (system.getHistory().size() >= 1){
				RBACState stateBefore = system.getHistory().lastElement();
				system.user2sessions = stateBefore.getSetOfSessions();
				return generateHistory(stateBefore,system, num);
			}
			else return false;
		}
		//system.sessions = stMinusOne.getSetOfSessions();
		
	}
	
	

	public RBAC generateRunTime(RBAC rbacSystem, int stateNum) throws ContradictionException{
		
		satr.resetSolver();
		
		mapStateToSessions = new HashMap<RBACState,Vector<Session2Solutions>>();
		//The set of states and their corresponding set of sessions and their corresponding "tried" solutions...
		
		RBACState initial = new RBACState("State0", rbacSystem.user2sessions); // all roles of a session are considered to be active ...
		rbacSystem.addPastState(initial, null);
		
		//System.out.println("Initial state ...");
		//initial.print();

		boolean history = generateHistory(initial, rbacSystem, stateNum);
		if (history && rbacSystem.getHistory().size() == stateNum){
			selectedElements.clear();
			selectedConstraints.clear();
			System.out.println("Last STATE " + rbacSystem.getHistory().get(rbacSystem.getHistory().size() - 1).getID());
			System.out.println("History has been set ...");
		}else {
			System.out.println("Couldn't set the history!!");
			return null;
		}
		
		return rbacSystem;
	}
	
	
	/*This method takes the following arguments and works as: 
	 * 1. Numbers of objects, operations, users, roles, constraints, sessions
	 * 2. The size of the role set for constraint definitions, constraints are generated exactly with rsetSize elements.
	 * 3. The minimum size of role permissions in PA, exact number of permissions 
	 * can be obtained by max_perm_number = min_perm_number + 1 --> this will produce max_perm_number of permissions 
	 * 4. Types of constraints to be generated, if conTypeStart = 1 and consTypeEnd = 2, only SS-DMER and MS-DMER 
	 * constraints will be generated.
	 * */
	public RBAC generateRBAC(int obNum, int opNum, int userNum, int roleNum, int minPermission, int maxPermission,
							 int merConNum,int rsetSize, int sesNum, int consTypeStart, int consTypeEnd, 
							 int rsodRsetSize, int sodConNum, int consType, int rsodType){
		
		selectedElements.clear();
		selectedConstraints.clear();
		//satr.clearClauses();
		
		if (mapStateToSessions != null) mapStateToSessions.clear();
		//t = new Translator();
		
		/*1. Objects*/
		RBACObject[] objects = new RBACObject[obNum];
		for (int i = 0; i < obNum; i++)
			objects[i] = new RBACObject("obj"+ i);
			
		/*2. Operations*/
		RBACOperation[] operations = new RBACOperation[opNum];
		for (int i = 0; i < opNum; i++)
			operations[i] = new RBACOperation("op"+ i);
		
		
		
		/*3. Permissions*/
		int k = 0;
		RBACPermission[] permissions = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				permissions[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		
		/*4. Roles*/
		RBACRole[] roles = new RBACRole[roleNum];
		for (int i = 0; i < roleNum; i++){
			RBACPermission[] perms = (RBACPermission[]) createEntropy(permissions, maxPermission, minPermission,1);
			if (perms != null && perms.length != 0)
				roles[i] = new RBACRole("role" + i, perms, null, true);  //  role hierarchy must be addressed ...
			else System.out.println("Permissions couldn't be set, there is something wrong ...");
		}
		selectedElements.clear();


		/*6. Constraints*/
		/*We generate "conNum" constraints for each type of constraints
		 *A. consType = 1 --> MER, consType = 2 --> SoD, consType == 3 --> both (SoD + MER) coexist...
		 *B. MER Constraint types (consTypeStart and consTypeEnd parameters): 
		 *	 1: SS-DMER, 2: MS-DMER, 3: SS-HMER, 4: MS-HMER*/
		HashMap<Integer, RBACMERConstraint[]> merConsts = null;
		HashMap<Integer,RSoDConstraint[]> rsodConsts = null;
		/*MER or MER+RSoD*/
		if (consType == 1 || consType == 3){
			merConsts = new HashMap<Integer, RBACMERConstraint[]>();
			for (int type = consTypeStart; type < consTypeEnd; type++){
				//for (int i = 0; i < conNum; i++){
					RBACMERConstraint[] constraints = constraintEntropy(roles, merConNum, rsetSize, type);
					if (constraints != null)
						merConsts.put(type, constraints);
				//}
			}
			selectedElements.clear();
		}
		
		/*RSoD or MER+RSoD*/
		if (consType == 2 || consType == 3){
			/* SoD generation --> falls here and the next step... 
			 * we start from roles and then generate the constraints...
			 * 1. Choose psetSize permissions to build pset = {p1,..,pn}
			 * 2. Create SoD constraint ...
			 * */
			 rsodConsts = new HashMap<Integer,RSoDConstraint[]>();
			 /* 1. FOR SoD --> We have to make sure that the set of permissions (pset) in SoD definition
			  * is smaller than SoD cardinality (card): |pset| > card
			  * 2. FOR RSoD --> We have to make sure that the set of users (userNum)
			  * must be bigger than SoD cardinality (card): |userNum| > card 
			  * */
			 RSoDConstraint[] rsodcons = new RSoDConstraint[sodConNum];
			 for (int j = 0; j < sodConNum; j++){
				 int card = rsodRsetSize;//1 + randPosINT(rsodRsetSize - 1, true);
				 RSoDConstraint sod = generateSoD("SoD" + j, roles, rsodRsetSize, card, rsodType);
				 rsodcons[j] = sod;
				 sod.print();
			 }
			 rsodConsts.put(rsodType,rsodcons);
			
			 //System.exit(0);
		}
		System.out.println("Constraints have been set ...");
		
	
		
		/*5. Users, UA Relations...*/
		RBACUser[] users = new RBACUser[userNum];
		for (int i = 0; i < userNum; i++){
			Vector<RBACRole> rolesSelected = new Vector<RBACRole>();
			chooseRolesWithMER(roles, merConsts, rolesSelected);
			if (rolesSelected.size() != 0)
				users[i] = new RBACUser("user" + i, rolesSelected.toArray(new RBACRole[rolesSelected.size()]));
			else System.out.println("Roles couldn't be set, there is something wrong ...");
		}
		selectedElements.clear();
		
		
		
		/*SoD Specific stuff, UA Relations ...*/
		if (consType == 2 || consType == 3){
			 /* MY APPROACH -------
			  * 3. Choose k - 1 users randomly 
			  * 
			  * RSSoD ({r1,..,rn}, k) : make sure that there does not exist a set of fewer 
			  * than k users that cover n roles in RSSoD definition
			  * --> We have to make sure that there is at least one set of users, u s.t. |u| < k-1
			  * that together has n roles...
			  * 
			  * For RSSoD
			  * 4. Iterate through all roles and check if one of the chosen users has the role
			  * 5. If not then add that role to a randomly selected user...
			  * 
			  * For the Original SoD
			  * 4. Iterate through all permissions and check if one of the chosen users has the permission
			  * 5. If not then add a role that contains the permission to a randomly selected user...
			  * 6. If there is no role with the permission, then choose a random role, add that permission
			  * and assign that role to a randomly selected user.
			  * */
			RSoDConstraint[] rsodcons = rsodConsts.get(rsodType);
			for (int i = 0; i < rsodcons.length; i++){
				int nOfUsers = rsodcons[i].cardinality;
				
				//while (nOfUsers == 0){
				System.out.println("CARD : " + (rsodcons[i].cardinality - 1));
				nOfUsers = nOfUsers > 2 ? randPosINT(rsodcons[i].cardinality - 1, true) : 1;
				//}
				RBACUser[] selectedUsers = chooseRandomUsers(users, nOfUsers);
				System.out.println("nOfUsers= " + nOfUsers + " card= " + (rsodcons[i].cardinality - 1));
				
				for (int j = 0; j < rsodcons[i].rSet.length; j++){
					boolean userHasTheRole = false;
					for (int t = 0; t < selectedUsers.length && !userHasTheRole; t++){
						if (selectedUsers[t].isAssignedRole(rsodcons[i].rSet[j]))  userHasTheRole = true;
					}
					if (!userHasTheRole) {
						//System.out.println("ADDED ROLE + " + rsodConsts.get(i).rSet[j].getID());
						int uIndex = randPosINT(selectedUsers.length, false);
						selectedUsers[uIndex].assignANewRole(rsodcons[i].rSet[j]);
					}
				}
				
				/*for (int y = 0; y < selectedUsers.length; y++){
					selectedUsers[y].print();
				}
				System.exit(0);*/
			}
			
			/*
				 SoDConstraint sod = sodConsts.get(j);
				 Vector<RBACRole> selectedRoles = new Vector<RBACRole>();
				 for (int i = 0; i < sod.pSet.length; i++){
					 int rIndex = randPosINT(roles.length, false);
					 while (!roles[rIndex].hasPermission(sod.pSet[i]))
						 rIndex = randPosINT(roles.length, false);
					 if (!selectedRoles.contains(roles[rIndex])) selectedRoles.add(roles[rIndex]);
				 }
				 
			*/
			
		}
		
		System.out.println("Roles have been set ...");

		/*
		 * RBACUser[] u, RBACRole[] r, RBACPermission[] prms, HashMap<Integer,
											RBACMERConstraint[]> mConsts, 
											RSoDConstraint[] rsodConsts
		 * */

		
		
		RBAC rbacSystem = null;
		if (consType == 1){ 
			rbacSystem = new RBAC(users, roles,permissions, merConsts , null);
		} else if (consType == 2){
			rbacSystem = new RBAC(users, roles,permissions, null,rsodConsts);
		} else if (consType == 3){
			rbacSystem = new RBAC(users, roles,permissions, merConsts,rsodConsts);
		}
		
		
		int sesId = 0;
		for (int user = 0; user < users.length; user++){
			for (int i = 0; i < sesNum; i++){
				rbacSystem.createSession("session"+ sesId, users[user].getID(),null);
				sesId++;
			}
		}
		System.out.println("Sessions have been set ....");
		
		
		
		
		/*18.09.2012, trying the alternative encoding for RSoD constraints ....*/
		if (rbacSystem.rSODConstraints != null){
			for (Map.Entry<Integer, RSoDConstraint[]> entry : rbacSystem.rSODConstraints.entrySet()){
				RSoDConstraint[] rsodcons = entry.getValue();
				for (int i = 0; i < rsodcons.length;i++){
					rsodcons[i].findUserCombinations(rbacSystem.users,rbacSystem.user2sessions);
				}
			}
		}
		
		
		/*Setting the varids for all elements... This has to be done after the instantiation of sessions 
		 * because they use the session instances.*/
/*		if (rbacSystem.rSODConstraints != null){
			for (int i = 0; i < rbacSystem.rSODConstraints.length;i++){
				rbacSystem.rSODConstraints[i].generateClauses(rbacSystem.users, rbacSystem.user2sessions);
			}
		}*/
		rbacSystem.setupVarIDs();
		rbacSystem.print(false);
		
		return rbacSystem;
	}
	
	
	
	static int OBJ_NUM = 10;
	static int OP_NUM = 1;
	static int USER_NUM = 5;
	static int SESSION_NUM = 3;
	static int STATE_NUM = 10;
	static int ROLE_NUM = 14;
	static int MIN_ROLE_PERMS = 1;
	static int MAX_ROLE_PERMS = 4;
	static int MER_CONSTRAINT_NUMBER = 2;
	static int MER_CONSTRAINT_RSET_SIZE = 3;
	static int SOLUTION_THRESHOLD = 100;
	static int SOLUTION_ORDER = 50;
	static int CONS_TYPE_START = 1;
	static int CONS_TYPE_END = 5;
	/*19.12.2011
	 * SOD SUPPORT*/
	static int RSOD_RSET_SIZE = 4;
	static int RSOD_CON_NUM = 2;
	static int RSOD_CARD = 2;
	
	/* CONS_TYPE == 1 --> only MER 
	 * CONS_TYPE == 2 --> only RSoD
	 * CONS_TYPE == 3 --> RSoD + MER */
	static int CONS_TYPE = 2;
	
	static boolean ALL_DROP_ACCEPTED = true;
	
	public static void main(String[] args) throws ContradictionException{
		
		Generator gen = new Generator();
		/* int sodRsetSize, int sodConNum, int cardSoD, int consType
		 * */
		RBAC system = gen.generateRBAC(OBJ_NUM,OP_NUM,USER_NUM,ROLE_NUM,
													  MIN_ROLE_PERMS, 
													  MAX_ROLE_PERMS,
													  MER_CONSTRAINT_NUMBER,
													  MER_CONSTRAINT_RSET_SIZE,
													  SESSION_NUM, 
													  CONS_TYPE_START,
													  CONS_TYPE_END,
													  RSOD_RSET_SIZE,
													  RSOD_CON_NUM,
													  CONS_TYPE
																/*1, 4, 1, 4, 1, 2*/, 1);
		//RBACState initial = gen.initializeHistory(system.sessions);
		//system.addPastState(initial);
		RBAC result = gen.generateRunTime(system, STATE_NUM);
		if (result == null) {
			System.out.println("COULDN'T GENERATE THE HISTORY!!! FOR THE SYSTEM : ");
			system.print(false);
			return;
		}
		RBACPermission[] lowerbound = new RBACPermission[]{};
		RBACPermission[] upperbound = system.getPermissions();
		system.print(false);
		
		
		
		Vector<RBACSession> allSessions = gen.findAllSessions(system.user2sessions);
		
		//FIXME there is a problem here .....
		Translator rs = new Translator(system);
		rs.generateClausesForOurs(system, allSessions.get(0), upperbound, lowerbound, 3, -1, false);
		int[][] theories  = Translator.formatClauses(rs.hardClauses);
		MiniSATReasoner satr = new MiniSATReasoner(false);
		if (satr.findASolution(theories, rs.varIds.size()) == null) System.out.println("No solution!!");
		else System.out.println("There is a solution");
		System.out.println("THE RBAC SYSTEM");
		
	}
}	
	
