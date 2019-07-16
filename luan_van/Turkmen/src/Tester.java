import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.sat4j.specs.ContradictionException;


public class Tester {

	private static HashMap<RBACUser,Vector<RBACSession>> createDeepClone(HashMap<RBACUser, Vector<RBACSession>> object){
		HashMap<RBACUser, Vector<RBACSession>> u2sessions = new HashMap<RBACUser, Vector<RBACSession>>();
		for (Map.Entry entry : object.entrySet()){
			RBACUser user = (RBACUser) entry.getKey();
			Vector<RBACSession> sessions = (Vector<RBACSession>)entry.getValue();
			Vector<RBACSession> sessionsNew = new Vector<RBACSession>();
			for (Iterator it = sessions.iterator(); it.hasNext();){
				RBACSession original = (RBACSession) it.next();
				RBACRole[] rset = new RBACRole[original.getActiveRoles().size()];
				RBACSession newSession = new RBACSession(original.getID(),original.user,original.getActiveRoles().toArray(rset));
				sessionsNew.add(newSession);
			}
			u2sessions.put(user, sessionsNew);
		}
		return u2sessions;
	}
	
	
	
	
	
	private RBAC testerApp(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("Pre1")};
		RBACOperation[] operations = {new RBACOperation("Prescribe"),new RBACOperation("Validate"),
									  new RBACOperation("Dispense"), new RBACOperation("Audit")};
		
		int k = 0;
		RBACPermission[] permissions = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				permissions[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		RBACRole role0 = new RBACRole("role0",new RBACPermission[]{permissions[0],permissions[1], permissions[2], permissions[3]}, null, distributePerms);
		RBACRole role1 = new RBACRole("role1",new RBACPermission[]{permissions[0]}, null, distributePerms);
		RBACRole role2 = new RBACRole("role2",new RBACPermission[]{permissions[3],permissions[2],permissions[0]}, null,distributePerms);
		RBACRole role3 = new RBACRole("role3",new RBACPermission[]{permissions[2],permissions[1],permissions[0]}, null,distributePerms);
		
		RBACUser user0 = new RBACUser("user0", new RBACRole[]{role3, role2});
	
		RBACMERConstraint mer1 = new RBACMERConstraint("MS-DMER", new RBACRole[]{role0, role1, role2,role3}, 2, RBACMERConstraint.MS_DMER_CONST);
		RBACMERConstraint mer2 = new RBACMERConstraint("SS-DMER", new RBACRole[]{role0,role1, role2,role3}, 3, RBACMERConstraint.SS_DMER_CONST);
		
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		consts.put(RBACMERConstraint.SS_DMER_CONST, new RBACMERConstraint[]{mer1});
		consts.put(RBACMERConstraint.MS_DMER_CONST, new RBACMERConstraint[]{mer2});
		
		RBAC system = new RBAC(new RBACUser[]{user0}, new RBACRole[]{role0, role1, role2, role3},permissions, consts, null);
		
		HashMap<RBACUser,Vector<RBACSession>> u2sessions = new HashMap<RBACUser, Vector<RBACSession>>();
		RBACSession s1 = system.createSession("session0", "user0", new RBACRole[]{/*doctor*/});
		RBACSession s2 = system.createSession("session1", "user0", new RBACRole[]{/*auditor, doctor*/});
		Vector<RBACSession> sessionList = new Vector<RBACSession>();
		sessionList.add(s1);
		sessionList.add(s2);
		u2sessions.put(system.findUser("user0"), sessionList);
		system.user2sessions = u2sessions;
		
		RBACState state0 = new RBACState("state0", u2sessions);
		system.addPastState(state0, null);
		return system;
	}
	

	
	public static void main(String[] args) throws ContradictionException{
		
		int choice = 6;
		int[][] hardTheories;
		int[][] softTheories;
		int [][] clauses;
		Translator rs;
		MiniSATReasoner miniSATsolver = null;
		MaxSATReasoner maxSATsolver = null;
			switch(choice){
				case 1: RBAC system = RBACInstantiations.generateRBACForCARDTest(true);
						system.setupVarIDs();
				
						system.print(false);
						RBACPermission[] perms = system.getPermissions();
						RBACPermission[] lowerbound = new RBACPermission[]{perms[0],perms[2]};
						RBACPermission[] upperbound = system.getPermissions();
						
					    rs = new Translator(system);
					    RBACSession selectedSession = system.user2sessions.get(system.users[1]).get(0);
					    rs.generateClausesForOurs(system, selectedSession, 
					    						  upperbound, lowerbound, 0, -1, false);
					    hardTheories = rs.formatClauses(rs.hardClauses);
					    softTheories = rs.formatClauses(rs.softClauses);
					    System.out.println("CLAUSES ... OURS");
					    rs.printClauses(hardTheories);
					    rs.printClauses(softTheories);
					    
						rs.toMaxSATCNF(hardTheories,null,rs.varIds.size(), "tests//CARDTest.cnf");
						System.out.println("Session is " + selectedSession.getID());
						MaxSATReasoner  satr = new MaxSATReasoner(true);	
						ResultContainer queryResult = satr.runSATSolverFromConsole("tests//CARDTest.cnf");	
						if (queryResult.result)System.out.println("Satisfiable!!!");
						else System.out.println("Unsatisfiable!!!");
						break;
				case 2: /*This is with hierarchy and may return different results compared to SACMAT 09...*/
						system = RBACInstantiations.generateRBACForComparison(true); 
						system.setupVarIDs();
						
						system.print(false);
						//system.listPermissions();
						perms = system.getPermissions();
						//RBACPermission[] requestedPermissions = new RBACPermission[]{perms[0]/*,perms[1],perms[2]*/};
						lowerbound = new RBACPermission[]{perms[0]/*,perms[1]/*,perms[2]*/};
						upperbound = system.getPermissions();//new RBACPermission[]{perms[0]};
						SACMAT09 cl = new SACMAT09();
						system.print(false);
						//cl.clearClauseSets();
					    cl.generateClauses(system, system.users[0], upperbound, lowerbound, 0); 
					    hardTheories  = cl.formatClauses(cl.hardClauses);
					    softTheories = cl.formatClauses(cl.softClauses);
					    System.out.println("CLAUSES ... SACMAT09");
					    cl.printClauses(hardTheories);
					    cl.printClauses(softTheories);
					    //cl.printArray(hardTheories);
					    maxSATsolver = new MaxSATReasoner(false);
					    //System.out.println("NUMBER OF VARIABLES " + cl.varIds.size());
					    cl.toMaxSATCNF(hardTheories, softTheories,  cl.varIds.size(), "tests//MaxSATSACMAT09.wcnf");
					    maxSATsolver.runSATSolverFromConsole("tests//MaxSATSACMAT09.wcnf");
					    //maxSATsolver.runSATSolverWithClauses(hardTheories, softTheories, cl.varIds.size());
					    
					    //system = RBACInstantiations.generateRBACForComparison(true); 
					    //system.setupVarIDs();
						perms = system.getPermissions();
						lowerbound = new RBACPermission[]{perms[0]/*,perms[1]/*,perms[2]*/};
						upperbound = system.getPermissions();//new RBACPermission[]{perms[0]};
					    System.out.println();
						maxSATsolver.resetSolver();
					    rs = new Translator(system);
					    rs.generateClausesForOurs(system, system.user2sessions.get(system.users[0]).get(0), 
					    						  upperbound, lowerbound, 0, -1, false);
					    hardTheories = rs.formatClauses(rs.hardClauses);
					    softTheories = rs.formatClauses(rs.softClauses);
					    System.out.println("CLAUSES ... OURS");
					    rs.printClauses(hardTheories);
					    rs.printClauses(softTheories);
					    //maxSATsolver.runSATSolverWithClauses(hardTheories, softTheories, rs.varIds.size());
					    
					    rs.toMaxSATCNF(hardTheories, softTheories,  rs.varIds.size(), "tests//MaxSAT.wcnf");
					    maxSATsolver.runSATSolverFromConsole("tests//MaxSAT.wcnf");
					    break;
				case 4: 
						//system = RBACInstantiations.generateRBACWithoutHierarchy(false);
						system = RBACInstantiations.generateForSACMAT09Comparison(false);
						system.setupVarIDs();
						
						system.print(false);
						//system.listPermissions();
						perms = system.getPermissions();
						//requestedPermissions = new RBACPermission[]{perms[0], perms[5]};
						lowerbound = new RBACPermission[]{perms[0],perms[1],perms[2],perms[6],perms[8],perms[9]};
						upperbound = perms;//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
						
						System.out.println();
						RBACUser user = system.users[0];
						System.out.println("Session is " + ((RBACSession)system.user2sessions.get(user).get(0)).getID());
						rs = new Translator(system);
						rs.generateClausesForOurs(system, ((RBACSession)system.user2sessions.get(user).get(0)),upperbound, lowerbound, 0, -1, false);	
						
						
						hardTheories  = rs.formatClauses(rs.hardClauses);
						softTheories = rs.formatClauses(rs.softClauses);
						System.out.println("CLAUSES ... FOR OURS ....");
						//rs.printArray(hardTheories);
						
						rs.printClauses(hardTheories);
						rs.printClauses(softTheories);
						rs.toMaxSATCNF(hardTheories,softTheories,rs.varIds.size(), "tests//MaxSAT.wcnf");
						satr = new MaxSATReasoner(true);	
						ResultContainer resultSolver = satr.runSATSolverFromConsole("tests//MaxSAT.wcnf");
						if (resultSolver.result) System.out.println("SAT");
						else System.out.println("UnSAT");
						
						
						System.out.println();
						
						System.out.println("User is " + user.getID());
						SACMAT09 sac = new SACMAT09();
						sac.generateClauses(system,user, upperbound, lowerbound, 0);	
						
						hardTheories  = sac.formatClauses(sac.hardClauses);
						softTheories = sac.formatClauses(sac.softClauses);
						
						//sac.printArray(hardTheories);
						System.out.println("CLAUSES ... FOR SACMAT09 ....");
						sac.printClauses(hardTheories);
						sac.printClauses(softTheories);
						sac.toMaxSATCNF(hardTheories,softTheories,sac.varIds.size(), "tests//MaxSATSACMAT09.wcnf");
						satr = new MaxSATReasoner(true);	
						resultSolver = satr.runSATSolverFromConsole("tests//MaxSATSACMAT09.wcnf");
						if (resultSolver.result) System.out.println("SAT");
						else System.out.println("UnSAT");
						
						
						//miniSATsolver = new MiniSATReasoner(false);
						//satr.runSATSolverWithClauses(theories, 100000);
						//satr.findAllSolutions(theories, 100000);
						//miniSATsolver.findAllSolutionsWithNext(hardTheories, rs.varIds.size());
						
						break;
				case 5: //TEST CASE ... For testing use this....
						system = RBACInstantiations.generateRBACWithoutHierarchy(false); 
						system.setupVarIDs();
						
						perms = system.getPermissions();
						//requestedPermissions = new RBACPermission[]{perms[0], perms[5]};
						System.err.println("THE BELOW IS THE SYSTEM ");
						system.print(false);
						
						MiniSATReasoner minSatr = new MiniSATReasoner(false);

						
						lowerbound = new RBACPermission[]{perms[1],perms[2]};
						upperbound = system.getPermissions();//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
						
					
						rs = new Translator(system);
						System.out.println("Session is " + system.user2sessions.get(system.users[2]).get(1).getID());
						if (rs.generateClausesForOurs(system, system.user2sessions.get(system.users[2]).get(1),
													  upperbound, lowerbound, 0, -1, false).getResult()){
							System.out.println("Translation worked");
						}else{
							System.out.println("Translation returned false");
						}
						
						
						hardTheories  = rs.formatClauses(rs.hardClauses);
						//rs.printArray(hardTheories);
						System.out.println("HARD CLAUSES ...");
						rs.printClauses(hardTheories);
						system.print(false);
						
						/*minSatr.addClauses(hardTheories, rs.varIds.size());
					    sol = minSatr.findTheNextSolution(1);
						minSatr.printModel(sol);

						sol = minSatr.findASolutionWithNegation(sol, 1);
						minSatr.printModel(sol);*/
						minSatr.resetSolver();
						minSatr.findAllSolutionsWithNext(hardTheories, rs.varIds.size());
						//rs.toMaxSATCNF(hardTheories,null,rs.varIds.size(), "tests//MERTest.cnf");
						//satr = new MaxSATReasoner(true);	
						//ResultContainer resultTemp = satr.runSATSolverFromConsole("tests//MERTest.cnf");	
						//if (resultTemp.result) System.out.println("Satisfiable!!");
						//else System.out.println("Unsatisfiable!!");
						System.exit(0);
						
						lowerbound = new RBACPermission[]{/*,perms[1],perms[2]*/};
						upperbound = new RBACPermission[]{perms[0],perms[2]};
						
						
						System.out.println("Session is " + system.user2sessions.get(system.users[0]).get(1).getID());
						if (rs.generateClausesForOurs(system, system.user2sessions.get(system.users[0]).get(1),
													  upperbound, lowerbound, 3, -1, false).getResult()){
							System.out.println("Translation worked");
						}else{
							System.out.println("Translation returned false");
						}
						
						hardTheories  = rs.formatClauses(rs.hardClauses);
						System.out.println("HARD CLAUSES ...");
						rs.printClauses(hardTheories);
						softTheories  = rs.formatClauses(rs.softClauses);
						System.out.println("SOFT CLAUSES ...");
						rs.printClauses(softTheories);
						
						//MaxSATReasoner mSatr = new MaxSATReasoner();
					
						/*minSatr.addClauses(hardTheories, rs.varIds.size());
					    int[] sol = minSatr.findTheNextSolution(1);
						minSatr.printModel(sol);*/
						//sol = minSatr.findASolutionWithNegation(sol, 1);
						//minSatr.printModel(sol);
						minSatr.findAllSolutionsWithNext(hardTheories, rs.varIds.size());
						
						break;
				case 6: 
						boolean withMean = false;
						/*objective 0: min, 1: max*/
						Experiments objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//constraint-test.cfg" , 1,false,-1);
						//objExperiments.testCase("tests//configs//userDRSoD-test.cfg" , 1,false, -1, withMean);
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//userHRSoD-test.cfg" , 1,false, -1, withMean);
						//System.out.println("------------------ USER TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//role-test-MER.cfg" , 1,false,-1, withMean);
						//System.out.println("------------------ ROLE TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//state-test-MER.cfg" , 1,false, -1,withMean);
						//System.out.println("------------------ STATE TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//session-test-MER.cfg" , 1, false, -1, withMean);
						//System.out.println("------------------ SESSION TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						objExperiments.testCase("tests//configs//permission-test-MER.cfg" , 1, false, -1, withMean);
						System.out.println("------------------ PERMISSION TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//roleDRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ ROLE DRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//roleHRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ ROLE HRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//sessionDRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ SESSION DRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//sessionHRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ SESSION HRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//stateDRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ STATE DRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//stateHRSoD-test.cfg" , 1,false,-1,withMean);
						//System.out.println("------------------ STATE HRSOD TEST COMPLETED --------------------");
						//objExperiments = new Experiments();
						//objExperiments.testCase("tests//configs//sacmat-test.cfg" , 1, true, -1,withMean);
						break;
				case 8:
						maxSATsolver = new MaxSATReasoner(true);
						//maxSATsolver.runSATSolverWithDIMACSSAT4J("SAT Tools//testPMaxSAT.cnf");
						maxSATsolver.runSATSolverFromConsole("SAT_Tools//testPMaxSAT.cnf");
						//System.exit(0);
						rs = new Translator(null);
						try{
							//for (int i = 0; i < 100000; i++){
								Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
								BufferedReader input = new BufferedReader(new FileReader("SAT_Tools//testPMaxSAT.cnf"));
								String line=null;
								String var;
						        while((line=input.readLine()) != null) {
						        	 if (!line.startsWith("p")){
						        		 var = line.substring(line.indexOf(" ") + 1, line.length() - 1).trim(); 
						        		 result.add(getVectorOfNumbers(var));
						        	 }
						         }
						        hardTheories = rs.formatClauses(result);
						        System.out.println("RESULT ....");
						        rs.printArray(hardTheories);
						        
						        maxSATsolver.runSATSolverWithClauses(hardTheories, null, 64);
						        System.out.println("FINISHED ");
							//}
						} catch (Exception e){
							System.out.println("Exception " + e.getMessage());
						}
						break;
						//RSoD TESTS ARE PERFORMED HERE!!!!!!!!!!!!!
				case 9:
						system = RBACInstantiations.generateRBACForSoDTest(false); 
						perms = system.getPermissions();
						//requestedPermissions = new RBACPermission[]{perms[0], perms[5]};
						System.err.println("THE BELOW IS THE SYSTEM ");
						system.print(false);
						
						//System.out.println("Generated externally ...");
						for (Map.Entry<Integer, RSoDConstraint[]> entry : system.rSODConstraints.entrySet()){
							RSoDConstraint[] rsodcons = entry.getValue();
							for (int i = 0; i < rsodcons.length; i++){
								RSoDConstraint rsod = rsodcons[i];
								rsod.findUserCombinations(system.users, system.user2sessions);
								//rsod.generateClauses(/*system.roles, */system.users, system.user2sessions);
								//rsod.printClauses(rsod.formatClauses(rsod.clausesAndTimes.clauses));
							}
						}
						system.setupVarIDs();
						
						
						//System.exit(0);
						
						minSatr = new MiniSATReasoner(false);
						
						lowerbound = new RBACPermission[]{/*perms[0],*/perms[5]/*,perms[1],perms[2]*/};
						upperbound = system.getPermissions();//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
					
						rs = new Translator(system);
						System.out.println("Session is " + system.user2sessions.get(system.users[2]).get(1).getID());
						System.out.print("Lower bound :{");
						for (int i = 0; i < lowerbound.length; i++) System.out.print(lowerbound[i].getID() +
																			(i < (lowerbound.length - 1) ?",":""));
						System.out.println("}");
						System.out.print("Upper bound :{");
						for (int i = 0; i < upperbound.length; i++) System.out.print(upperbound[i].getID() + 
																			(i < (upperbound.length - 1) ?",":""));
						System.out.println("}");
						if (rs.generateClausesForOurs(system, system.user2sessions.get(system.users[2]).get(1),
													  upperbound, lowerbound, 0, -1, false).getResult()){
							System.out.println("Translation worked");
						}else{
							System.out.println("Translation returned false");
						}
						
						
						hardTheories  = rs.formatClauses(rs.hardClauses);
						//rs.printArray(hardTheories);
						System.out.println("HARD CLAUSES ...");
						rs.printClauses(hardTheories);
						//system.print(false);
						
						/*minSatr.addClauses(hardTheories, rs.varIds.size());
					    sol = minSatr.findTheNextSolution(1);
						minSatr.printModel(sol);
	
						sol = minSatr.findASolutionWithNegation(sol, 1);
						minSatr.printModel(sol);*/
						//minSatr.resetSolver();
						
						ResultContainer rc = minSatr.runSATSolverWithClauses(hardTheories, rs.varIds.size());
						if (rc.getResult()) System.out.println("SATISFIABLE!!!");
						else System.out.println("UNSATISFIABLE!!!");
						minSatr.findAllSolutionsWithNext(hardTheories, rs.varIds.size());
						//rs.toMaxSATCNF(hardTheories,null,rs.varIds.size(), "tests//SoDTest.cnf");
						//satr = new MaxSATReasoner(true);	
						//ResultContainer result = satr.runSATSolverFromConsole("tests//SoDTest.cnf");	
						System.exit(0);
						break;
			}
			
	}
	
	
	public static Vector getVectorOfNumbers(String line){
		Vector result = new Vector();
		String[] values = line.split(" ");
		for (int i = 0; i < values.length; i++)
			result.add(Integer.parseInt(values[i]));
		return result;
	}
}
