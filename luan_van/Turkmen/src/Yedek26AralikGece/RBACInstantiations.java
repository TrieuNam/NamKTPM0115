import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class RBACInstantiations {

	
	public static void waitForInput(){
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try{
			bf.readLine();
		}catch(Exception e){
			System.out.println("There is an exception in waitForInput ...");
		}
	}
	
	
	
	public static RBAC generateRBACWithoutHierarchy(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("Pre1"), new RBACObject("Pre2"), new RBACObject("Pre3")};
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
		
		RBACRole doctor = new RBACRole("doctor",
										new RBACPermission[]{permissions[0],permissions[1], permissions[2],
										                     permissions[4], permissions[5], permissions[6]}, 
										null, distributePerms);
		RBACRole auditor = new RBACRole("auditor",new RBACPermission[]{permissions[3],
																	   permissions[7]}, null, distributePerms);
		RBACRole nurse = new RBACRole("nurse",new RBACPermission[]{permissions[2],
		                                                           permissions[6]}, null,distributePerms);
				
		RBACMERConstraint mer1 = new RBACMERConstraint("SS-DMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.SS_DMER_CONST);
		//RBACMERConstraint mer2 = new RBACMERConstraint("MS-DMER", new RBACRole[]{doctor,auditor/*,nurse*/}, 2, RBACMERConstraint.MS_DMER_CONST); //--> correct this ...
		//RBACMERConstraint mer3 = new RBACMERConstraint("SS-HMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.SS_HMER_CONST); //--> correct this ...
		//RBACMERConstraint mer4 = new RBACMERConstraint("MS-HMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.MS_HMER_CONST); //--> correct this ...
		
		RBACUser alice = new RBACUser("alice", new RBACRole[]{doctor, nurse});
		RBACUser bob = new RBACUser("bob", new RBACRole[]{doctor});
		RBACUser seth = new RBACUser("seth", new RBACRole[]{doctor,auditor, nurse});
		RBACUser john = new RBACUser("john", new RBACRole[]{auditor,nurse});
		
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		consts.put(RBACMERConstraint.SS_DMER_CONST, new RBACMERConstraint[]{mer1});
		//consts.put(RBACMERConstraint.MS_DMER_CONST, new RBACMERConstraint[]{mer2});
		//consts.put(RBACMERConstraint.SS_HMER_CONST, new RBACMERConstraint[]{mer3});
		//consts.put(RBACMERConstraint.MS_HMER_CONST, new RBACMERConstraint[]{mer4});
		
		/*RSoDConstraint[] rsodConsts = new RSoDConstraint[]{
												new RSoDConstraint("sod1",new RBACRole[]{doctor,auditor, nurse}, 2)};
		*/										
		
		RBAC system = new RBAC(new RBACUser[]{alice, bob, seth, john}, 
							   new RBACRole[]{doctor, auditor,nurse},
							   permissions, 
							   consts,
							   null);
		//RBAC system = new RBAC(new RBACUser[]{alice, bob}, new RBACRole[]{cashier,clerk,employee,accountant,manager},permissions, new RBACMERConstraint[]{mer1}, 5);
		
		Vector<RBACSession> sessionsAlice = new Vector<RBACSession>();
		Vector<RBACSession> sessionsSeth = new Vector<RBACSession>();
		Vector<RBACSession> sessionsJohn = new Vector<RBACSession>();
		RBACSession s1 = system.createSession("s1", "alice", new RBACRole[]{/*doctor*/});
		RBACSession s2 = system.createSession("s2", "seth", new RBACRole[]{/*auditor, doctor*/});
		RBACSession s3 = system.createSession("s3", "alice", new RBACRole[]{/*doctor*/}); // this is strange ...
		RBACSession s4 = system.createSession("s4", "john", new RBACRole[]{/*doctor*/});
		RBACSession s5 = system.createSession("s5", "seth", new RBACRole[]{/*doctor*/});
		RBACSession s6 = system.createSession("s6", "john", new RBACRole[]{/*doctor*/});
		
		sessionsAlice.add(s1);
		sessionsSeth.add(s2);
		sessionsAlice.add(s3);
		sessionsJohn.add(s4);
		sessionsSeth.add(s5);
		sessionsJohn.add(s6);
	
		/*STATE RELATED STUFF*/
		/*TO BE VERIFIED .......*/
		RBACState state0 = new RBACState("state0");
		state0.getSetOfSessions().put(system.findUser("alice"), sessionsAlice);
		state0.getSetOfSessions().put(system.findUser("seth"), sessionsSeth);
		state0.getSetOfSessions().put(system.findUser("john"), sessionsJohn);
		
		system.addPastState(state0, null);
		//system.print(false);
		//waitForInput();
		RBACSession sessionChanged;
		
		RBACState state1 = new RBACState("state1",state0.createDeepCloneForU2S(state0.getSetOfSessions()));
		sessionChanged = state1.getSetOfSessions().get(system.findUser("seth")).get(0);
		sessionChanged.addActiveRole(auditor);
		system.addPastState(state1,sessionChanged);
		//system.print(false);
		//waitForInput();
	
		
		
		RBACState state2 = new RBACState("state2",state1.createDeepCloneForU2S(state1.getSetOfSessions()));
		sessionChanged = state2.getSetOfSessions().get(system.findUser("alice")).get(0);
		sessionChanged.addActiveRole(nurse);
		system.addPastState(state2, sessionChanged);
		//system.print(false);
		waitForInput();
		
	
		/*RBACState state3 = new RBACState("state3",state2.createDeepCloneForU2S(state2.getSetOfSessions()));
		sessionChanged = state3.getSetOfSessions().get(system.findUser("seth")).get(0);
		sessionChanged.dropActiveRole(auditor, system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST));
		system.addPastState(state3, sessionChanged);
		//system.print(false);
		waitForInput();
		
		/*RBACState state4 = new RBACState("state4",state2.createDeepCloneForU2S(state3.getSetOfSessions()));
		sessionChanged = state4.getSetOfSessions().get(system.findUser("seth")).get(0);
		sessionChanged.addActiveRole(doctor);
		system.addPastState(state4, sessionChanged);
		system.print(false);
		waitForInput();*/
		
		return system;
		
	}
	
	
	public static RBAC generateRBACForSoDTest(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("obj1"), new RBACObject("obj2"), new RBACObject("obj3")};
		RBACOperation[] operations = {new RBACOperation("op1"),new RBACOperation("op2")};
		int k = 0;
		RBACPermission[] permissions = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				permissions[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		RBACRole r1 = new RBACRole("r1", new RBACPermission[]{permissions[0],permissions[1], permissions[2],
										                     	  permissions[4]}, 
										                     	  null, distributePerms);
		RBACRole r2 = new RBACRole("r2",new RBACPermission[]{permissions[3], permissions[4]}, null, distributePerms);
		RBACRole r3 = new RBACRole("r3",new RBACPermission[]{permissions[2], permissions[5]}, null,distributePerms);
		RBACRole r4 = new RBACRole("r4",new RBACPermission[]{permissions[1], permissions[4]}, null,distributePerms);
		RBACRole r5 = new RBACRole("r5",new RBACPermission[]{permissions[2], permissions[3],
				   												  permissions[4]}, null,distributePerms);
		//RBACMERConstraint mer1 = new RBACMERConstraint("SS-DMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.SS_DMER_CONST);
		//RBACMERConstraint mer2 = new RBACMERConstraint("MS-DMER", new RBACRole[]{doctor,auditor,nurse}, 2, RBACMERConstraint.MS_DMER_CONST); //--> correct this ...
		//RBACMERConstraint mer3 = new RBACMERConstraint("SS-HMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.SS_HMER_CONST); //--> correct this ...
		//RBACMERConstraint mer4 = new RBACMERConstraint("MS-HMER", new RBACRole[]{doctor,auditor}, 2, RBACMERConstraint.MS_HMER_CONST); //--> correct this ...
		
		RBACUser alice = new RBACUser("alice", new RBACRole[]{r1,r2,r3,r4/*,r5*/});
		RBACUser john = new RBACUser("john", new RBACRole[]{r1,r2,r4,r5});
		RBACUser seth = new RBACUser("seth", new RBACRole[]{r1,r3,r4,r5});
		//RBACUser john = new RBACUser("john", new RBACRole[]{auditor,nurse});
		
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		//consts.put(1, new RBACMERConstraint[]{mer1});
		//consts.put(2, new RBACMERConstraint[]{mer2});
		//consts.put(3, new RBACMERConstraint[]{mer3});
		//consts.put(4, new RBACMERConstraint[]{mer4});
		
		RBACUser[] allUsers = new RBACUser[]{alice, john, seth };
		RSoDConstraint[] rsodcons = new RSoDConstraint[]{new RSoDConstraint("sod1",new RBACRole[]{r1,r2, r3,r4}, 3, RBACConstraint.D_RSoD_CONST)};
		HashMap<Integer, RSoDConstraint[]> rsodConsts = new HashMap<Integer, RSoDConstraint[]>();
		rsodConsts.put(RBACConstraint.D_RSoD_CONST, rsodcons);
		RBAC system = new RBAC(allUsers, 
							   new RBACRole[]{r1, r2, r3, r4, r5},
							   permissions, 
							   consts,
							   rsodConsts);
		
		Vector<RBACSession> sessionsAlice = new Vector<RBACSession>();
		Vector<RBACSession> sessionsSeth = new Vector<RBACSession>();
		Vector<RBACSession> sessionsJohn = new Vector<RBACSession>();
		RBACSession s1 = system.createSession("s1", "alice", new RBACRole[]{/*r1*/});
		RBACSession s2 = system.createSession("s2", "seth", new RBACRole[]{/*r4,r5*/});
		RBACSession s3 = system.createSession("s3", "alice", new RBACRole[]{/*r1*/}); // this is strange ...
		RBACSession s4 = system.createSession("s4", "john", new RBACRole[]{/*r2,r4*/});
		RBACSession s5 = system.createSession("s5", "seth", new RBACRole[]{/*r3,r5*/});
		//RBACSession s6 = system.createSession("s6", "john", new RBACRole[]{/*doctor*/});
		
		for (Map.Entry<Integer, RSoDConstraint[]> entry : rsodConsts.entrySet()){
			RSoDConstraint[] cons = entry.getValue();
			for (int i = 0; i < cons.length; i++){
				cons[i].findUserCombinations(system.users, system.user2sessions);
			}
		}
		
		
		sessionsAlice.add(s1);
		sessionsSeth.add(s2);
		sessionsAlice.add(s3);
		sessionsJohn.add(s4);
		sessionsSeth.add(s5);
		//sessionsJohn.add(s6);
	
		
		
		/*STATE RELATED STUFF*/
		/*TO BE VERIFIED .......*/
		RBACState state0 = new RBACState("state0");
		state0.getSetOfSessions().put(system.findUser("alice"), sessionsAlice);
		state0.getSetOfSessions().put(system.findUser("seth"), sessionsSeth);
		state0.getSetOfSessions().put(system.findUser("john"), sessionsJohn);
		system.addPastState(state0, null);
		
		//system.print(false);
		waitForInput();
		RBACSession sessionChanged;
		
		RBACState state1 = new RBACState("state1",state0.createDeepCloneForU2S(state0.getSetOfSessions()));
		sessionChanged = state1.getSetOfSessions().get(system.findUser("alice")).get(0);
		sessionChanged.addActiveRole(r1);
		system.addPastState(state1,sessionChanged);
		//system.print(false);
		waitForInput();
		
		
		RBACState state2 = new RBACState("state2",state1.createDeepCloneForU2S(state1.getSetOfSessions()));
		sessionChanged = state2.getSetOfSessions().get(system.findUser("seth")).get(0);
		sessionChanged.addActiveRole(r4);
		sessionChanged.addActiveRole(r5);
		system.addPastState(state2, sessionChanged);
		//system.print(false);
		waitForInput();
		
	
		RBACState state3 = new RBACState("state3",state2.createDeepCloneForU2S(state2.getSetOfSessions()));
		sessionChanged = state3.getSetOfSessions().get(system.findUser("alice")).get(1);
		//sessionChanged.dropActiveRole(doctor, system.rSODConstraints.get(RBACMERConstraint.D_RSoD_CONST));
		sessionChanged.addActiveRole(r1);
		system.addPastState(state3, sessionChanged);
		//system.print(false);
		waitForInput();
		
		RBACState state4 = new RBACState("state4",state3.createDeepCloneForU2S(state3.getSetOfSessions()));
		sessionChanged = state4.getSetOfSessions().get(system.findUser("john")).get(0);
		sessionChanged.addActiveRole(r2);
		sessionChanged.addActiveRole(r4);
		system.addPastState(state4, sessionChanged);
		//system.print(false);
		waitForInput();
		
		RBACState state5 = new RBACState("state5",state4.createDeepCloneForU2S(state4.getSetOfSessions()));
		sessionChanged = state5.getSetOfSessions().get(system.findUser("seth")).get(1);
		sessionChanged.addActiveRole(r5);
		system.addPastState(state5, sessionChanged);
		//system.print(false);
		waitForInput();
		
		return system;
		
	}
	
	
	public static RBAC generateRBACWithHierarchy(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("T1"), new RBACObject("T2")};
		RBACOperation[] operations = {new RBACOperation("Create"),new RBACOperation("Sign"),
									  new RBACOperation("View"), new RBACOperation("Cancel"),	
									  new RBACOperation("Suspend"), new RBACOperation("Report"),
									  new RBACOperation("Checkout")};
		int k = 0;
		RBACPermission[] permissions = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				permissions[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		RBACRole cashier = new RBACRole("cashier",new RBACPermission[]{permissions[6],permissions[13]}, null, distributePerms);
		RBACRole clerk = new RBACRole("clerk",new RBACPermission[]{permissions[2],permissions[5], permissions[9], permissions[12]}, null,distributePerms);
		RBACRole employee = new RBACRole("employee",new RBACPermission[]{permissions[0], permissions[3], permissions[7], permissions[10] }, new RBACRole[]{clerk}, distributePerms);
		RBACRole accountant = new RBACRole("accountant",new RBACPermission[]{permissions[4], permissions[6], permissions[11], permissions[13]}, new RBACRole[]{clerk},distributePerms);
		RBACRole manager = new RBACRole("manager",new RBACPermission[]{permissions[1], permissions[8]}, new RBACRole[]{employee, accountant},distributePerms);
				
		RBACMERConstraint mer1 = new RBACMERConstraint("SS-DMER", new RBACRole[]{employee,accountant}, 2, RBACMERConstraint.SS_DMER_CONST);
		RBACMERConstraint mer2 = new RBACMERConstraint("MS-DMER", new RBACRole[]{employee,accountant}, 3, RBACMERConstraint.MS_DMER_CONST); //the last parameter is the type of the constraint
		
		RBACUser bob = new RBACUser("bob", new RBACRole[]{manager});
		RBACUser alice = new RBACUser("alice", new RBACRole[]{employee,accountant});
		RBACUser seth = new RBACUser("seth", new RBACRole[]{clerk});
		
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		consts.put(RBACMERConstraint.SS_DMER_CONST, new RBACMERConstraint[]{mer1});
		consts.put(RBACMERConstraint.MS_DMER_CONST, new RBACMERConstraint[]{mer2});
		
		RBAC system = new RBAC(new RBACUser[]{alice, bob, seth}, new RBACRole[]{cashier,clerk,employee,accountant,manager},permissions, consts, null);
		//RBAC system = new RBAC(new RBACUser[]{alice, bob}, new RBACRole[]{cashier,clerk,employee,accountant,manager},permissions, new RBACMERConstraint[]{mer1}, 5);
		
		Vector<RBACSession> sessionsAlice = new Vector<RBACSession>();
		Vector<RBACSession> sessionsBob = new Vector<RBACSession>();
		Vector<RBACSession> sessionsSeth = new Vector<RBACSession>();
		RBACSession s1 = system.createSession("s1", "bob", new RBACRole[]{manager});
		RBACSession s2 = system.createSession("s2", "alice", new RBACRole[]{employee});
		RBACSession s3 = system.createSession("s3", "alice", new RBACRole[]{accountant});
		RBACSession s4 = system.createSession("s4", "bob", new RBACRole[]{manager});
		RBACSession s5 = system.createSession("s5", "seth", new RBACRole[]{clerk});
		sessionsBob.add(s1);
		sessionsAlice.add(s2);
		sessionsAlice.add(s3);
		sessionsBob.add(s4);
		sessionsSeth.add(s5);
		
		/*STATE RELATED STUFF ...*/
		/*TO BE VERIFIED .......*/
		RBACState state0 = new RBACState("state0");
		state0.getSetOfSessions().put(system.findUser("bob"), sessionsBob);
		state0.getSetOfSessions().put(system.findUser("alice"), sessionsAlice);
		state0.getSetOfSessions().put(system.findUser("seth"), sessionsSeth);
		system.addPastState(state0, null);
		
		RBACSession sessionChanged;
		
		RBACState state1 = new RBACState("state1",state0.createDeepCloneForU2S(state0.getSetOfSessions()));
		sessionChanged = state1.getSetOfSessions().get(system.findUser("alice")).get(0);
		sessionChanged.dropActiveRole(accountant, system.merConstraints.get(RBACMERConstraint.MS_DMER_CONST));
		system.addPastState(state1,sessionChanged);
		
		//THERE IS NO CHANGE OF STATE HERE, MODIFY IT TO REFLECT...!!!
		RBACState state2 = new RBACState("state2",state1.createDeepCloneForU2S(state1.getSetOfSessions()));
		sessionChanged = state2.getSetOfSessions().get(system.findUser("alice")).get(0);
		sessionChanged.addActiveRole(employee);
		system.addPastState(state2, sessionChanged);
		return system;
	}
	
	
	
	public static RBAC generateRBACForComparison(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("T1"), new RBACObject("T2")};
		RBACOperation[] operations = {new RBACOperation("Create"),new RBACOperation("Sign"),
									  new RBACOperation("View"), new RBACOperation("Cancel"),	
									  new RBACOperation("Suspend"), new RBACOperation("Report"),
									  new RBACOperation("Checkout")};
		int k = 0;
		RBACPermission[] permissions = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				permissions[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		RBACRole cashier = new RBACRole("cashier",new RBACPermission[]{permissions[6],permissions[13]}, null, distributePerms);
		RBACRole clerk = new RBACRole("clerk",new RBACPermission[]{permissions[2],permissions[5], permissions[9], permissions[12]}, null,distributePerms);
		RBACRole employee = new RBACRole("employee",new RBACPermission[]{permissions[0], permissions[3], permissions[7], permissions[10] }, new RBACRole[]{clerk},distributePerms);
		RBACRole accountant = new RBACRole("accountant",new RBACPermission[]{permissions[4], permissions[6], permissions[11], permissions[13]}, new RBACRole[]{clerk},distributePerms);
		RBACRole manager = new RBACRole("manager",new RBACPermission[]{permissions[1], permissions[8]}, new RBACRole[]{employee},distributePerms);
				
		RBACMERConstraint mer1 = new RBACMERConstraint("SS-DMER1", new RBACRole[]{employee,accountant}, 2, RBACMERConstraint.SS_DMER_CONST);
		//RBACMERConstraint mer2 = new RBACMERConstraint("MS-DMER", new RBACRole[]{employee,accountant}, 3, 2); //the last parameter is the type of the constraint
		
		RBACUser bob = new RBACUser("bob", new RBACRole[]{manager});
		RBACUser alice = new RBACUser("alice", new RBACRole[]{employee,accountant});
		RBACUser seth = new RBACUser("seth", new RBACRole[]{clerk});
		
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		consts.put(RBACMERConstraint.SS_DMER_CONST, new RBACMERConstraint[]{mer1});
		//consts.put(2, new RBACMERConstraint[]{mer2});
		
		RBAC system = new RBAC(new RBACUser[]{alice, bob, seth}, new RBACRole[]{cashier,clerk,employee,accountant,manager},permissions, consts, null);
		//RBAC system = new RBAC(new RBACUser[]{alice, bob}, new RBACRole[]{cashier,clerk,employee,accountant,manager},permissions, new RBACMERConstraint[]{mer1}, 5);
		
		//RBACSession s1 = system.createSession("s1", "bob", new RBACRole[]{manager});
		RBACSession s2 = system.createSession("s2", "alice", new RBACRole[]{employee});
		//RBACSession s3 = system.createSession("s3", "alice", new RBACRole[]{accountant});
		return system;
	}
	
	
	
	public static RBAC generateForSACMAT09Comparison(boolean distributePerms){
		RBACObject[] objects = {new RBACObject("Obj1"), new RBACObject("Obj2")};
		RBACOperation[] operations = {new RBACOperation("Op1"),new RBACOperation("Op2"),
									  new RBACOperation("Op3"), new RBACOperation("Op4"), new RBACOperation("Op5")};
		int k = 0;
		RBACPermission[] pSet = new RBACPermission[operations.length * objects.length];
		for (int i = 0; i < objects.length; i++){
			for (int j = 0; j < operations.length; j++){
				pSet[k] = new RBACPermission("p" + k,objects[i],operations[j]);
				k++;
			}
		}
		
		RBACRole role0 = new RBACRole("role0", 
				new RBACPermission[]{pSet[4],pSet[9],pSet[1],pSet[5],pSet[3],pSet[7],pSet[8],pSet[0],pSet[6],pSet[2]},  null, distributePerms);
		RBACRole role1 = new RBACRole("role1", 
				new RBACPermission[]{pSet[0],pSet[3],pSet[2],pSet[7],pSet[8]},  null, distributePerms);
		RBACRole role2 = new RBACRole("role2", 
				new RBACPermission[]{pSet[6],pSet[0],pSet[9],pSet[3],pSet[5],pSet[7],pSet[1]},  null, distributePerms);
		RBACRole role3 = new RBACRole("role3", 
				new RBACPermission[]{pSet[1],pSet[9],pSet[7],pSet[2],pSet[6]},  null, distributePerms);
		RBACRole role4 = new RBACRole("role4", 
				new RBACPermission[]{pSet[9],pSet[8],pSet[6],pSet[2],pSet[3],pSet[1],pSet[7],pSet[0],pSet[4]},  null, distributePerms);
		RBACRole role5 = new RBACRole("role5", 
				new RBACPermission[]{pSet[1],pSet[2],pSet[8],pSet[5],pSet[3],pSet[6]},  null, distributePerms);
		RBACRole role6 = new RBACRole("role6", 
				new RBACPermission[]{pSet[2],pSet[6],pSet[0],pSet[5],pSet[9],pSet[3]},  null, distributePerms);
		RBACRole role7 = new RBACRole("role7", 
				new RBACPermission[]{pSet[7],pSet[3],pSet[8],pSet[6],pSet[0],pSet[1],pSet[9],pSet[5],pSet[2]},  null, distributePerms);
		RBACRole role8 = new RBACRole("role8", 
				new RBACPermission[]{pSet[8],pSet[1],pSet[4],pSet[6],pSet[9],pSet[3],pSet[0]},  null, distributePerms);
		RBACRole role9 = new RBACRole("role9", 
				new RBACPermission[]{pSet[5],pSet[6],pSet[2],pSet[9],pSet[7],pSet[8]},  null, distributePerms);
		
		
		RBACMERConstraint mer1 = new RBACMERConstraint("1_MER0", new RBACRole[]{role7,role2,role9,role4,role1,role3}, 2, 
																						RBACMERConstraint.SS_DMER_CONST);
		RBACMERConstraint mer2 = new RBACMERConstraint("1_MER1", new RBACRole[]{role8,role3,role4,role6,role7,role5}, 6, 
																						RBACMERConstraint.SS_DMER_CONST);
		RBACMERConstraint mer3 = new RBACMERConstraint("1_MER2", new RBACRole[]{role4,role5,role9,role6,role7,role3}, 4, 
																						RBACMERConstraint.SS_DMER_CONST);
	
		HashMap<Integer, RBACMERConstraint[]> consts = new HashMap<Integer, RBACMERConstraint[]>();
		consts.put(1, new RBACMERConstraint[]{mer1});
		consts.put(2, new RBACMERConstraint[]{mer2});
		consts.put(3, new RBACMERConstraint[]{mer3});
		
		RBACUser user0 = new RBACUser("user0", new RBACRole[]{role7,role2,role0,role1,role3,role4,role5});
		RBACUser user1 = new RBACUser("user1", new RBACRole[]{role7,role2,role0,role1,role3,role4,role5});
		RBACUser user2 = new RBACUser("user2", new RBACRole[]{role7,role2,role0});
		RBACUser user3 = new RBACUser("user3", new RBACRole[]{role8,role3,role4,role5,role0,role1});
		RBACUser user4 = new RBACUser("user4", new RBACRole[]{role4,role5,role9,role6,role0,role1,role2,role3,role7});
		RBACUser user5 = new RBACUser("user5", new RBACRole[]{role7,role2,role0,role1,role3,role4});
		RBACUser user6 = new RBACUser("user6", new RBACRole[]{role4,role5,role9,role6,role0,role1,role2,role3});
		RBACUser user7 = new RBACUser("user7", new RBACRole[]{role4,role5,role9,role6,role0});
		RBACUser user8 = new RBACUser("user8", new RBACRole[]{role8,role3,role4,role5});
		RBACUser user9 = new RBACUser("user9", new RBACRole[]{role4,role5,role9,role6,role0,role1,role2,role3,role7});
	
		RBAC system = new RBAC(new RBACUser[]{user0, user1, user2, user3, user4, user5,user6,user7,user8,user9}, 
							   new RBACRole[]{role0, role1, role2, role3, role4, role5,role6,role7,role8,role9},
							   pSet, consts, null);
		
		RBACSession s0 = system.createSession("session0", "user0", new RBACRole[]{});
		return system;
	}
}
