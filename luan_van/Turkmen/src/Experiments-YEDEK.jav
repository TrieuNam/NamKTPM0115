import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.rank.Median;
import org.sat4j.specs.ContradictionException;


public class Experiments {
	
	//private Translator trans;
	//private SACMAT09 transSACMAT09;
	private Generator generator;
	MaxSATReasoner satr = new MaxSATReasoner(true);	
	

	final int OBJ_INTERVAL = 30;
	final int SESSION_INTERVAL = 2;  
	final int STATE_INTERVAL = 10;
	final int USER_INTERVAL = 10;
	
	
	static int SOLVING_TIMES = 1;
	static int RUN_TIMES =  30;//100;
	static int RBAC_GENERATION_TIMES =  10;//100;
	
	/*DELETE THIS .....*/
	ResultContainer resSACMATMin;
	ResultContainer resMin;
	/*DELETE THIS .....*/
	
	
	Vector<ResultContainer> exactMatchSolverResults = new Vector<ResultContainer>();
	Vector<ResultContainer> minMatchSolverResults = new Vector<ResultContainer>();
	Vector<ResultContainer> maxMatchSolverResults = new Vector<ResultContainer>();
	
	Vector<DescriptiveStatistics> exactMatchTranslatorResults = new Vector<DescriptiveStatistics>();
	Vector<DescriptiveStatistics> minMatchTranslatorResults = new Vector<DescriptiveStatistics>();
	Vector<DescriptiveStatistics> maxMatchTranslatorResults = new Vector<DescriptiveStatistics>();
	
	Vector<ResultContainer> exactSACMAT09SolverResults = new Vector<ResultContainer>();
	Vector<ResultContainer> minSACMAT09SolverResults = new Vector<ResultContainer>();
	Vector<ResultContainer> maxSACMAT09SolverResults = new Vector<ResultContainer>();
	
	Vector<DescriptiveStatistics> exactSACMAT09TranslatorResults = new Vector<DescriptiveStatistics>();
	Vector<DescriptiveStatistics> minSACMAT09TranslatorResults = new Vector<DescriptiveStatistics>();
	Vector<DescriptiveStatistics> maxSACMAT09TranslatorResults = new Vector<DescriptiveStatistics>();
	
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
	
	
	public Experiments(){
		//transSACMAT09 = new SACMAT09();
		generator = new Generator();
	}
	
	public static void waitForInput(){
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try{
			bf.readLine();
		}catch(Exception e){
			System.out.println("There is an exception in waitForInput ...");
		}
	}
	
	public void printToFiles(boolean compare, int objective, String testFile){
		if (compare) printSATResults(exactSACMAT09SolverResults, new PrintStream(System.out), false);
		try{
			printSATResults(exactMatchSolverResults, new PrintStream(new File("tests//testSAT_" + 
									testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			printTranslationResults(exactMatchTranslatorResults, new PrintStream(new File("tests//testTranslation_" + 
									testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
		if (compare) {
			printSATResults(exactSACMAT09SolverResults, new PrintStream(new File("tests//testSATSACMAT09_" + 
							testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			printTranslationResults(exactSACMAT09TranslatorResults, new PrintStream(new File("tests//testTranslationSACMAT09_" + 
							testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			if (objective == -1){
				printSATResults(minSACMAT09SolverResults, new PrintStream(new File("tests//testMinSATSACMAT09_" + 
						testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
				printTranslationResults(minSACMAT09TranslatorResults, new PrintStream(new File("tests//testMinTranslationSACMAT09_" + 
						testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
				printSATResults(maxSACMAT09SolverResults, new PrintStream(new File("tests//testMaxSATSACMAT09_" + 
						testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
				printTranslationResults(maxSACMAT09TranslatorResults, new PrintStream(new File("tests//testMaxTranslationSACMAT09_" + 
						testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			}
		
		}
		
		
		
		if (objective == -1){
			printSATResults(minMatchSolverResults, new PrintStream(new File("tests//testMinSAT_" + 
					testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			printTranslationResults(minMatchTranslatorResults, new PrintStream(new File("tests//testMinTranslation_" + 
					testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			
			printSATResults(maxMatchSolverResults, new PrintStream(new File("tests//testMaxSAT_" + 
					testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
			printTranslationResults(maxMatchTranslatorResults, new PrintStream(new File("tests//testMaxTranslation_" + 
					testFile.substring(testFile.lastIndexOf("//") + 2, testFile.indexOf("-")) + ".txt")), true);
		}
		
		
		
		}catch(Exception e){
			System.err.println("Exception in testCases " + e.getMessage());
		}
	}
	/*Parameters are thresholds ...
	 * DMER related scenario to be defined based on the followings: 
	 * 1. The ratio (R2P) between role/permission: How many permissions a role will have?  --> permissionEntropy method
	 * 2. The ratio (R2U) between user/role: How many roles will a user have? --> roleEntropy method 
	 * 3. The ratio (R2C) between role/constraint: How many roles will be in a constraint? --> constraintEntropy method
	 * CHANGING PARAMETER: sessions, roles, users???
	 * 
	 * History related scenario
	 * 1. DMER related scenario
	 * CHANGING PARAMETER: states, roles, users???
	 * 
	 * TO BE CONSIDERED:
	 * 1. The ratio (R2Req) between the requested permissions and all permissions of a role: 
	 * 			How many permissions of a role will be selected during the generation of the request?
	 * 
	 * */
	
	
	/*Here we have the testing scenarios ...*/
	public void testCase(String testFile, int type, boolean compare, int objective) throws ContradictionException{	
		//obNum, opNum, userNum, sesNum, stateNum, roleNum, conNum
				
		Properties configFile;
		configFile = new java.util.Properties();
		try {			
			 configFile.load(new FileInputStream(testFile));			
		}catch(Exception e){
			 //eta.printStackTrace();
			 System.out.println("Exception" + e.getMessage());
		}
		
		printProperties(configFile);
		
		RBAC sys = null;
		
		int min_perm_number_role = Integer.parseInt(configFile.getProperty("MIN_PERM_NUMBER_FOR_ROLE"));
		int max_perm_number_role = Integer.parseInt(configFile.getProperty("MAX_PERM_NUMBER_FOR_ROLE"));

		int min_role_num = Integer.parseInt(configFile.getProperty("INITIAL_ROLE_NUMBER"));
		int min_user_num = Integer.parseInt(configFile.getProperty("INITIAL_USER_NUMBER")); 
		int min_cons_num = Integer.parseInt(configFile.getProperty("INITIAL_CONSTRAINT_NUMBER"));
		int min_state_num = Integer.parseInt(configFile.getProperty("INITIAL_STATE_NUMBER"));
		int min_obj_num = Integer.parseInt(configFile.getProperty("INITIAL_OBJECT_NUMBER"));
		int min_ses_num = Integer.parseInt(configFile.getProperty("INITIAL_SESSION_NUMBER"));
		int min_sod_cons_num = Integer.parseInt(configFile.getProperty("INITIAL_RSOD_CONSTRAINT_NUMBER"));
		
		int role_num_threshold = Integer.parseInt(configFile.getProperty("ROLENUM_THRESHOLD"));
		int user_num_threshold = Integer.parseInt(configFile.getProperty("USERNUM_THRESHOLD"));
		int con_num_threshold = Integer.parseInt(configFile.getProperty("CONNUM_THRESHOLD"));
		int state_num_threshold = Integer.parseInt(configFile.getProperty("STATENUM_THRESHOLD"));
		int obj_num_threshold = Integer.parseInt(configFile.getProperty("OBJNUM_THRESHOLD"));
		int ses_num_threshold = Integer.parseInt(configFile.getProperty("SESNUM_THRESHOLD"));
		int rsod_con_num_threshold = Integer.parseInt(configFile.getProperty("RSOD_CONNUM_THRESHOLD"));
		
		int op_num = Integer.parseInt(configFile.getProperty("OP_NUM"));
		int rset_size = Integer.parseInt(configFile.getProperty("CONSTRAINT_RSET_SIZE"));
		int mer_cons_type_start = Integer.parseInt(configFile.getProperty("CONSTRAINT_TYPE_START"));
		int mer_cons_type_end = Integer.parseInt(configFile.getProperty("CONSTRAINT_TYPE_END"));
		int generation_tries = Integer.parseInt(configFile.getProperty("GENERATION_TRIES"));
		//RSoD
		int rsod_rset_size = Integer.parseInt(configFile.getProperty("RSOD_CONSTRAINT_RSET_SIZE"));
		//1: MER, 2: RSoD, 3: MER + RSoD
		int cons_type = Integer.parseInt(configFile.getProperty("CONSTRAINT_TYPE"));
		
		int role_num = min_role_num;
		int user_num = min_user_num;
		int mer_cons_num = min_cons_num;
		int sod_cons_num = min_sod_cons_num;
		int state_num = min_state_num;
		int obj_num = min_obj_num;
		int ses_num = min_ses_num;
		
		int role_interval = Integer.parseInt(configFile.getProperty("ROLE_INTERVAL"));
		
		int generatedSystems = (user_num_threshold - user_num) * 
		   (role_num_threshold - role_num) *
		   (con_num_threshold - mer_cons_num) * 
		   (state_num_threshold - state_num) *
		   (ses_num_threshold - ses_num) *
		   (obj_num_threshold - obj_num);
		int GCTime = 0;
		while(mer_cons_num < con_num_threshold){
			System.err.println("MER_CONS_NUM " + mer_cons_num);
			while(sod_cons_num < rsod_con_num_threshold){
				System.err.println("SOD_CONS_NUM " + mer_cons_num);
					while(user_num < user_num_threshold){
						System.err.println("USER_NUM " + user_num);
						while (role_num < role_num_threshold){
							System.err.println("ROLE_NUMBER " + role_num);
							while (ses_num < ses_num_threshold){
								System.err.println("SES_NUMBER " + ses_num);
								while (obj_num < obj_num_threshold){
									System.err.println("OBJ_NUMBER " + obj_num);
									/*
									 * Below one RBAC configuration is generated, many run-times (i.e. states)
									 * are generated... 
									 * */
									
									
									
									RBAC system = generator.generateRBAC(obj_num, 
																		 op_num, 
																		 user_num, role_num, min_perm_number_role,
																		 max_perm_number_role, mer_cons_num, 
																		 rset_size, 
																		 ses_num,
																		 mer_cons_type_start,
																		 mer_cons_type_end,
																		 rsod_rset_size,
																		 sod_cons_num,
																		 cons_type);
									System.out.println("INITIAL GENERATION DONE!!!");
									while (state_num < state_num_threshold){
										System.out.println("----- STATE NUMBER ----- " + state_num);
										sys = generator.generateRunTime(system, state_num);	
										GCTime++;
										for (int j = 0; j < generation_tries && sys == null;j++){
											System.out.println("TRY NO " + j);
											system = generator.generateRBAC(obj_num, 
																			op_num, user_num, 
																			role_num, min_perm_number_role, 
																			max_perm_number_role, mer_cons_num, 
																			rset_size, 
																			ses_num,
																			mer_cons_type_start,
																			mer_cons_type_end,
																			rsod_rset_size,
																			sod_cons_num,
																			cons_type);
											sys = generator.generateRunTime(system, state_num);
											GCTime++;
										}
										if (GCTime % 10 ==0)  System.gc();
										if (sys != null){
											solveTheProblem(sys, testFile, compare, objective);
											state_num = state_num + STATE_INTERVAL;
											System.out.println("Finished ...");
										}
										
										
									}
									
									
									
									state_num = min_state_num;
									obj_num = obj_num + OBJ_INTERVAL;
								}
								obj_num = min_obj_num;
								ses_num = ses_num + SESSION_INTERVAL;
							}
							ses_num = min_ses_num;
							role_num = role_num + role_interval;
							//System.out.println("ROLE NUMBER INCREASE !!");
							//waitForInput();
						}
						role_num = min_role_num;
						user_num = user_num + USER_INTERVAL;
					}
					user_num = min_user_num;
					sod_cons_num++;
				}
			mer_cons_num++;
		}
		
		
		
		printSATResults(exactMatchSolverResults, new PrintStream(System.out),false);
		printTranslationResults(maxMatchTranslatorResults, new PrintStream(System.out), false);
		printToFiles(compare, objective, testFile);
		
		System.out.println("EXPECTED SIZE: " + generatedSystems + " OBTAINED SIZE: " + exactMatchSolverResults.size());

		clearResults();
		satr.resetSolver();
	}
	
	public void clearResults(){
		exactMatchSolverResults.clear();
		minMatchSolverResults.clear();
		maxMatchSolverResults.clear();
		
		exactMatchTranslatorResults.clear();
		minMatchTranslatorResults.clear();
		maxMatchTranslatorResults.clear();
		
		exactSACMAT09TranslatorResults.clear();
		minSACMAT09TranslatorResults.clear();
		maxSACMAT09TranslatorResults.clear();
		
		exactSACMAT09SolverResults.clear();
		minSACMAT09SolverResults.clear();
		maxSACMAT09SolverResults.clear();
	}

	
	public void solveTheProblem(RBAC sys, String testFile, boolean compare, int objective){
		
		ResultContainer r = null;
		if (testFile.contains("constraint")){ 
			for (int i = 1; i < 5; i++){
			 xMatch(sys, objective, i,compare); 
			}
		}else{	
			for (int i = 0; i < RUN_TIMES; i++)
				xMatch(sys, objective, -1, compare); 	
		}
	}
	
	
	public RBACPermission[] choosePermissions(RBACUser user, RBACPermission[] permissions){
		Vector<RBACPermission> perms = new Vector<RBACPermission>();
		int i = 0;
		if (user.getUserPermissions() != null){
			for (Iterator it = user.getUserPermissions().iterator(); it.hasNext();){
				RBACPermission p = (RBACPermission) it.next();
				i++;
				if (randPosINT(2, false) == 1) perms.add(p);
			}
		}
		if (perms.size() == 0 && i == 0) {System.out.println("Permissions are null........"); return null;}
		System.out.println("Permissions are NOT null........");
		RBACPermission[] perSet = new RBACPermission[perms.size()];
		return perms.toArray(perSet);
	}
	
	private Vector<RBACSession> findAllSessions(HashMap<RBACUser, Vector<RBACSession>> u2sessions){
		Vector<RBACSession> allSessions = new Vector<RBACSession>();
		for (Map.Entry entry : u2sessions.entrySet()){
			allSessions.addAll((Vector<RBACSession>) entry.getValue());
		}
		return allSessions;
	}
	
	
	/*Min - Max - Exact match cases... Chooses the permissions...*/
	public void xMatch(RBAC system, int objective, int constType, boolean alsoSACMAT09){
		
		
		Vector<Integer> triedSessions = new Vector<Integer>();
		RBACPermission[] perms = null;
		RBACSession session = null;
		int i = randPosINT(system.getHistory().lastElement().getSetOfSessions().size(), false);
		triedSessions.add(i);
		Vector<RBACSession> allSessions = findAllSessions(system.getHistory().lastElement().getSetOfSessions());
		while (triedSessions.size() <= system.user2sessions.values().size()){
			session = allSessions.get(i);
			perms = choosePermissions(session.user, system.getPermissions());
			
			if (perms != null){
				aMatchCase(system,session, perms,objective, constType, alsoSACMAT09);
				return;
			}
				//return satr.runSATSolverWithDIMACSSAT4J("tests//MaxSAT.wcnf");
			else {
				i = randPosINT(system.user2sessions.values().size(), false);
				while (triedSessions.contains(i) && triedSessions.size() <= system.user2sessions.values().size())
					i = randPosINT(system.user2sessions.values().size(), false);
				triedSessions.add(i);
			}
		}
		System.out.println("Should not fall here ");
		System.exit(0);
	}
	
	
	
	public DescriptiveStatistics runOurTranslator(RBAC system, Translator trans, RBACSession session, 
																			  RBACPermission[] upperbound, 
																			  RBACPermission[] lowerbound,
																			  int objective, int constType){
		DescriptiveStatistics dsMatch = null;
		for (int j = 0; j < SOLVING_TIMES; j++){
			dsMatch = new DescriptiveStatistics();
			ResultContainer rc = trans.generateClausesForOurs(system,session, upperbound, 
																			  lowerbound, objective, constType, false);
			dsMatch.addValue(rc.getTime().getSum());
		}
		return dsMatch;
	}
	
	
	public ResultContainer translateAndSolve(RBAC system, RBACSession session, RBACPermission[] upperbound, 
														   			  RBACPermission[] lowerbound, 
														   			  int obj, int constType,
														   			  String CNFFileName){
		Translator trans = new Translator(system);
		
		DescriptiveStatistics dsMatch = runOurTranslator(system, trans, session, upperbound, lowerbound, obj/*0*/, constType);
		if (obj == 0) minMatchTranslatorResults.add(dsMatch);
		else if (obj == 1) maxMatchTranslatorResults.add(dsMatch);
		else if (obj == 2) exactMatchTranslatorResults.add(dsMatch);
		
		int[][] hardTheories  = trans.formatClauses(trans.hardClauses);
		int[][] softTheories  = trans.softClauses == null ? null : trans.formatClauses(trans.softClauses);
		//system.print(false);
		//System.err.println("THIS SHOULD BE THE REASON...");
		/*if (obj == 0){	
			System.out.println("OUR ENCODING ..........................");
			trans.printClauses(hardTheories);
			trans.printClauses(softTheories);
		}*/
		trans.toMaxSATCNF(hardTheories,softTheories,trans.varIds.size(), CNFFileName);
		ResultContainer result = satr.runSATSolverFromConsole(CNFFileName);		
		return result;
	}
	
	

	
	/*By using the permSet, plb and pub are set...*/
	public void aMatchCase(RBAC system, RBACSession session, RBACPermission[] permSet,
			 													int objective, int constType,
			 													boolean alsoSACMAT09){
		ResultContainer result = null;
		RBACPermission[] lowerbound = null; //new RBACPermission[]{}; //perms;
		RBACPermission[] upperbound = null; //new RBACPermission[session.user.getUserPermissions().size()];
			//session.user.getUserPermissions().toArray(upperbound);//system.getPermissions();//perms;//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
			
		System.out.print(session.getID() + " {");
		printPermissions(permSet);
		System.out.println("}");
		
				
		if (objective == -1 || objective == 0 || objective == 1){ /*objective = -1 --> All objectives are used and compared ...*/
			/*FIRST MIN ....*/
			if (objective != 1){
				lowerbound = new RBACPermission[permSet.length];
				System.arraycopy(permSet, 0, lowerbound, 0, permSet.length);
				upperbound = new RBACPermission[session.user.getUserPermissions().size()];
				session.user.getUserPermissions().toArray(upperbound);
				result = translateAndSolve(system,session,upperbound,lowerbound, 0, constType, "tests//MinSAT.wcnf");
				minMatchSolverResults.add(result);
				resMin = result;
			}
		
			/*THEN MAX ...*/
			if (objective != 0){
				lowerbound = new RBACPermission[]{};
				upperbound = new RBACPermission[permSet.length];
				System.arraycopy(permSet, 0, upperbound, 0 ,permSet.length);//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
	
				result = translateAndSolve(system,session,upperbound,lowerbound,1,constType,"tests//MaxSAT.wcnf");
				maxMatchSolverResults.add(result);
			}
		} 
		
		/*LATER EXACT MATCH*/
		lowerbound = new RBACPermission[permSet.length];
		System.arraycopy(permSet, 0, lowerbound, 0 ,permSet.length);
		upperbound = new RBACPermission[permSet.length];
		System.arraycopy(permSet, 0, upperbound, 0 ,permSet.length); 
		/*exact Match case follows ....*/
		result = translateAndSolve(system, session, upperbound, lowerbound, 2, constType, "tests//ExactSAT.wcnf");
		exactMatchSolverResults.add(result);
		
		if (alsoSACMAT09) {
			ResultContainer resSACMAT09 = xMatchSACMAT09(system,permSet, session, objective);
			if (resMin.getResult() != resSACMATMin.getResult()) {
				
				RBACPermission[] permissions = new RBACPermission[session.user.getUserPermissions().size()];
				session.user.getUserPermissions().toArray(permissions);
				System.out.print(session.getID() + "Plb = " + " {");
				printPermissions(permSet);
				System.out.println(" Pub = {");
				printPermissions(permissions);
				System.out.println("}");
				system.print(true);
				System.out.println("Results are different ");
				//System.exit(0);
			}
		}										
			
	}
	

	public DescriptiveStatistics runSACMAT09Translator(RBAC system, RBACUser user, SACMAT09 transSACMAT09,
																	RBACPermission[] upperbound, 
																	RBACPermission[] lowerbound,
																	int obj){
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		for (int j = 0; j < SOLVING_TIMES; j++){
			ResultContainer rc = transSACMAT09.generateClauses(system,user, upperbound, lowerbound, obj);
			ds.addValue(rc.getTime().getSum());
		}
		return ds;
	}
	
	public ResultContainer translateAndSolveSACMAT09(RBAC system, RBACUser user, RBACPermission[] upperbound, 
 			  											 RBACPermission[] lowerbound,
 			  											 int obj, String CNFFileName){
		SACMAT09 transSACMAT09 = new SACMAT09();
		DescriptiveStatistics dsMatch = runSACMAT09Translator(system, user, transSACMAT09, upperbound, lowerbound, 0);
		if (obj == 0) minSACMAT09TranslatorResults.add(dsMatch);
		else if (obj == 1) maxSACMAT09TranslatorResults.add(dsMatch);
		else if (obj == 2) exactSACMAT09TranslatorResults.add(dsMatch);
		
		int[][] hardTheories  = transSACMAT09.formatClauses(transSACMAT09.hardClauses);
		int[][] softTheories  = transSACMAT09.softClauses == null ? null : 
											  transSACMAT09.formatClauses(transSACMAT09.softClauses);
		/*if (obj == 0){
			System.out.println("SACMAT 09 ..........................");
			transSACMAT09.printClauses(hardTheories);
			transSACMAT09.printClauses(softTheories);
		}*/
		transSACMAT09.toMaxSATCNF(hardTheories,softTheories,transSACMAT09.varIds.size(), CNFFileName);
		ResultContainer res = satr.runSATSolverFromConsole(CNFFileName);
		return res;

	}
	
	public ResultContainer xMatchSACMAT09(RBAC system, RBACPermission[] permSet, RBACSession session, int objective){
		ResultContainer result = null;
		if (permSet != null){	
			if (objective == -1){
				
				/*FIRST MIN ...*/
				RBACPermission[] lowerboundOtherObj = new RBACPermission[permSet.length];
				System.arraycopy(permSet, 0, lowerboundOtherObj, 0, permSet.length);
				RBACPermission[] upperboundOtherObj = new RBACPermission[session.user.getUserPermissions().size()];
				session.user.getUserPermissions().toArray(upperboundOtherObj);
				result = translateAndSolveSACMAT09(system, session.user, upperboundOtherObj, 
																	lowerboundOtherObj, 0, "tests//MinSATSACMAT09.wcnf");
				minSACMAT09SolverResults.add(result);
				resSACMATMin = result;
				
				/*THEN MAX...*/
				lowerboundOtherObj = new RBACPermission[]{};
				upperboundOtherObj = new RBACPermission[permSet.length];
				System.arraycopy(permSet, 0, upperboundOtherObj, 0, permSet.length);
				result = translateAndSolveSACMAT09(system, session.user, upperboundOtherObj, 
																	lowerboundOtherObj, 1, "tests//MaxSATSACMAT09.wcnf");
				maxSACMAT09SolverResults.add(result);
			}
			
			//session.user.getUserPermissions().toArray(upperbound);//system.getPermissions();//perms;//new RBACPermission[]{perms[0],perms[1],perms[2], perms[3]};
			RBACPermission[] lowerboundOtherObj = new RBACPermission[permSet.length];
			System.arraycopy(permSet, 0, lowerboundOtherObj, 0, permSet.length);
			RBACPermission[] upperboundOtherObj = new RBACPermission[permSet.length];
			System.arraycopy(permSet, 0, upperboundOtherObj, 0, permSet.length);
			result = translateAndSolveSACMAT09(system, session.user, upperboundOtherObj, 
																	lowerboundOtherObj,2, "tests//ExactSATSACMAT09.wcnf");
			exactSACMAT09SolverResults.add(result);
			//return satr.runSATSolverWithDIMACSSAT4J("tests//MaxSAT.wcnf");
		} 
		return result;
	}
	
	
	
	public void printPermissions(RBACPermission[] prms){
		for (int i = 0; i < prms.length; i++){
			System.out.print(prms[i].getID() + " ");
		}
		System.out.println("}");
	}
	
	public void printSATResults(Vector<ResultContainer> solRes, PrintStream out, boolean close){
		Median m = new Median();
		
		out.println(("SAT SOLVING TIMES ..."));
		for (Iterator it = solRes.iterator(); it.hasNext();){
			ResultContainer res = (ResultContainer) it.next();
			out.println("Res:" + res.getResult() + 
					 // " sTime:" + res.getsTime() + 
					  " time:" +  m.evaluate(res.getTime().getValues()));
				//else System.out.println("Res:" + res.getResult() + " time:" + res.getTime());
		}
		int i = 1;
		m = new Median();
		DescriptiveStatistics dsNew = new DescriptiveStatistics();
		for (Iterator it = solRes.iterator(); it.hasNext();){
			ResultContainer res = (ResultContainer) it.next();
			dsNew.addValue(res.getTime().getMean());
			if ((i % RUN_TIMES) == 0){
				//out.println(m.evaluate(dsNew.getValues()));
				out.println(/*m.evaluate(dsNew.getValues()) == 0 ? */dsNew.getMean() /*: m.evaluate(dsNew.getValues())*/);
				dsNew.clear();
			}
			i++;
				//else System.out.println("Res:" + res.getResult() + " time:" + res.getTime());
		}

		//System.out.println("CLASS NAME " + out.getClass().getName());
/*		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try{
			bf.readLine();
		}catch(Exception e){}*/
		if (close) out.close();
	}
	public void printTranslationResults(Vector<DescriptiveStatistics> tranResults, PrintStream out, boolean close){
		out.println("TRANSLATION TIMES ...");
		Median m = new Median();
		
		DescriptiveStatistics dsNew = new DescriptiveStatistics();
		int i = 1;
		for (Iterator it = tranResults.iterator(); it.hasNext();){
			DescriptiveStatistics ds = (DescriptiveStatistics) it.next();
			dsNew.addValue(ds.getMean());
			if ((i % RUN_TIMES) == 0){
				//out.println(/*"Time : " + */ m.evaluate(dsNew.getValues()));
				out.println(/*"Time : " + */ /*m.evaluate(dsNew.getValues()) == 0 ? */dsNew.getMean() /*: m.evaluate(dsNew.getValues())*/);
				dsNew.clear();
			}
			i++;
		}
		if (close) out.close();
	}
	
	
	public void printProperties(Properties configFile){
		System.out.println("INITIAL_ROLE_NUMBER = " + configFile.getProperty("INITIAL_ROLE_NUMBER"));
		System.out.println("INITIAL_USER_NUMBER = " + configFile.getProperty("INITIAL_USER_NUMBER"));
		System.out.println("INITIAL_CONSTRAINT_NUMBER =" + configFile.getProperty("INITIAL_CONSTRAINT_NUMBER"));
		System.out.println("INITIAL_RSOD_CONSTRAINT_NUMBER =" + configFile.getProperty("INITIAL_RSOD_CONSTRAINT_NUMBER"));
		System.out.println("INITIAL_STATE_NUMBER = " + configFile.getProperty("INITIAL_STATE_NUMBER"));
		System.out.println("INITIAL_OBJECT_NUMBER = " + Integer.parseInt(configFile.getProperty("INITIAL_OBJECT_NUMBER")));
		System.out.println("INITIAL_SESSION_NUMBER = " + Integer.parseInt(configFile.getProperty("INITIAL_SESSION_NUMBER")));
		System.out.println("USERNUM_THRESHOLD = " + configFile.getProperty("USERNUM_THRESHOLD"));
		System.out.println("ROLENUM_THRESHOLD = " + configFile.getProperty("ROLENUM_THRESHOLD"));
		System.out.println("CONNUM_THRESHOLD = " + configFile.getProperty("CONNUM_THRESHOLD"));
		System.out.println("RSOD_CONNUM_THRESHOLD = " + configFile.getProperty("RSOD_CONNUM_THRESHOLD"));
		System.out.println("STATENUM_THRESHOLD = " + configFile.getProperty("STATENUM_THRESHOLD"));
		System.out.println("OBJNUM_THRESHOLD = " + Integer.parseInt(configFile.getProperty("OBJNUM_THRESHOLD")));
		System.out.println("SESNUM_THRESHOLD = " + Integer.parseInt(configFile.getProperty("SESNUM_THRESHOLD")));
		System.out.println("OP_NUM = " + configFile.getProperty("OP_NUM"));
		System.out.println("CONSTRAINT_RSET_SIZE = " + configFile.getProperty("CONSTRAINT_RSET_SIZE"));
		System.out.println("GENERATION_TRIES = " +configFile.getProperty("GENERATION_TRIES"));
		System.out.println("RSOD_CONSTRAINT_RSET_SIZE = " + configFile.getProperty("RSOD_CONSTRAINT_RSET_SIZE"));
		System.out.println("CONSTRAINT_TYPE = " + configFile.getProperty("CONSTRAINT_TYPE"));
	}
}
