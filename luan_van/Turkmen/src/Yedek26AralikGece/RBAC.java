import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


class RBACElement{
	String id;
	public String getID(){return id;}
};
 
class RBACOperation extends RBACElement{
	//String id;
	public RBACOperation(String i){
		id = i;
	}
	
	/*public String getID(){
		return id;
	}*/
	
	public void print(){
		System.out.print(id);
	}
}

class RBACObject extends RBACElement{
	//String id;
	public RBACObject(String i){
		id = i;
	}
	
	/*public String getID(){
		return id;
	}*/
	
	public void print(){
		System.out.print(id);
	}
}

class RBACPermission extends RBACElement{
	//String id;
	public  RBACOperation op;
	public RBACObject obj;
	
	public RBACPermission(String i, RBACObject object, RBACOperation operation){
		id = i;
		obj = object;
		op = operation;
	}
	
	
	/*public String getID(){
		return id;
	}*/
	
	public void print(){
		System.out.print(id);
		op.print();
		obj.print();
	}
}

class RBACRole extends RBACElement{
	//String id;
	public RBACPermission[] perms;
	public RBACRole[] descendants;
 
	public RBACRole(String i,RBACPermission[] permissions, RBACRole[] hierarchyRoles, boolean distributePerms){
		id = i;
		descendants = hierarchyRoles;
	    setPermissions(permissions, distributePerms);
	}
	
	public void setPermissions(RBACPermission[] prms, boolean distributePerms){
		Vector<RBACPermission> allPermissions = new Vector<RBACPermission>();
		for (int i = 0; i < prms.length; i++){
			allPermissions.add(prms[i]);
		}
		
		if (descendants != null && distributePerms){
			for (int i = 0; i < descendants.length; i++){
				for (int j = 0; j < descendants[i].perms.length; j++){
					allPermissions.add(descendants[i].perms[j]);
				}
			}
		}
		perms = new RBACPermission[allPermissions.size()];
		allPermissions.toArray(perms);
	}
 
	public boolean addPermission(RBACPermission perm){
		if (!hasPermission(perm)){
			RBACPermission[] newPerms = new RBACPermission[perms.length + 1];
			System.arraycopy(perms, 0, newPerms,0, perms.length);
			newPerms[perms.length] = perm;
			return true;
		}
		return false;
	}
	
	public boolean hasPermission(RBACPermission perm){
	 for (int i = 0 ; i < perms.length; i++){
		 RBACPermission p = perms[i];
		 
		 if (p.obj.getID().equals(perm.obj.getID()) && p.op.getID().equals(perm.op.getID()))
			 return true;
	 }
	 
	 /* Because we modified the hierarchies as permission hierarchies we dont need the below any more...
	 if (descendants != null){
		 for (int i = 0 ; i < descendants.length; i ++){
		 RBACRole r = descendants[i];
		 if (r.hasPermission(perm)) return true;
	 	}
	 }	*/
	 return false;
	}
	
	/*public boolean isAssignedPermission(RBACPermission perm){
		 for (int i = 0 ; i < perms.length; i++){
			 RBACPermission p = perms[i];
			 if (p.obj.getID().equals(perm.obj.getID()) && p.op.getID().equals(perm.op.getID()))
				 return true;
		 }
		 return false;
	}*/
	
	/*public String getID(){
		return id;
	}*/
	
	public String print(boolean endOfLine){
		String str = id + " {";
		System.out.print(str);
		for (int i = 0; i < perms.length; i++) {
			String s = perms[i].getID() + ((i + 1)  < perms.length ? "," : "");
			str = str + s;
			System.out.print(s);
		}
		str = str + "} " + (descendants != null ? " + " : "");
		System.out.print("} " + (descendants != null ? " + " : ""));
		if (descendants != null) 
			for (int i = 0; i < descendants.length; i++) {
				String s = descendants[i].print(false);
				str = str + s;
			}
		if (endOfLine) {str = str + "\n"; System.out.println();}
		return str;
	}
}



class RBACUser extends RBACElement{
	RBACRole[] assignedRoles;
	//String id;
	public RBACUser(String userId, RBACRole[] roles){
		id = userId;
		assignedRoles = roles;
	}
	public boolean isAssignedRole(RBACRole r){
		for (int i=0; i < assignedRoles.length; i++){
			if (r.getID().equals(assignedRoles[i].getID())) return true;
		}
		return false;
	}
	/*public String getID(){
		return id;
	}*/
	
	public boolean assignANewRole(RBACRole r){
		if (!isAssignedRole(r)){
			RBACRole[] newRoles = new RBACRole[assignedRoles.length + 1];
			System.arraycopy(assignedRoles, 0, newRoles,0, assignedRoles.length);
			newRoles[assignedRoles.length] = r;
			assignedRoles = newRoles;
			return true;
		}
		return false;
	}
	
	/*TODO : Implement this ...*/
	public void deAssignRole(){
		
	}
	
	public boolean hasPermission(RBACPermission perm){
		for (int i = 0; i < assignedRoles.length; i++){
			if (assignedRoles[i].hasPermission(perm)) return true;
		}
		return false;
	}
	
	public String print(){
		String str = id + " {";
		System.out.print(str);
		for (int i = 0; i < assignedRoles.length; i++) {
			String s = assignedRoles[i].getID() + ((i + 1)  < assignedRoles.length ? "," : "");
			str = str + s;
			System.out.print(s);
		}
		str = str + "}" + "\n";
		System.out.println("}");
		return str;
	}
	
	private boolean alreadyAdded(Vector<RBACPermission> perSet, RBACPermission perm){
		for (Iterator it = perSet.iterator(); it.hasNext();){
			RBACPermission p = (RBACPermission) it.next();
			if (p.getID().equals(perm.getID())) return true;
		}
		return false;
	}
	
	public Vector<RBACPermission> getUserPermissions(){
		Vector<RBACPermission> permissions = new Vector<RBACPermission>();
		for (int i = 0; i< assignedRoles.length; i++){
			RBACRole role = assignedRoles[i];
			for (int j = 0; j < role.perms.length; j++){
				if (!alreadyAdded(permissions,role.perms[j])){
					permissions.add(role.perms[j]);
				}
			}
		}
		if (permissions.size() == 0) return null;
		return permissions;
	}
}







class RBACSession extends RBACElement{
	//String id; 
	public RBACUser user;
	public Vector<RBACRole> activeRoles;
	public RBACSession(String i, RBACUser u, RBACRole[] rSet){
		id = i;
		user = u;
		if (rSet != null){
			activeRoles = new Vector<RBACRole>(rSet.length);
			for (int k =0; k < rSet.length; k++)
				activeRoles.add(rSet[k]);
		} else activeRoles = new Vector<RBACRole>();
	}
	public boolean addActiveRole(RBACRole r){
		if (user.isAssignedRole(r)){
			for (Iterator<RBACRole> it = activeRoles.iterator(); it.hasNext();){
				RBACRole role = it.next();
				if (role.getID().equals(r.getID())) {
					System.out.println("Role is already active");
					return false;
				}
			}
			System.out.println("ROLE ADDED .....");
			activeRoles.add(r);
			return true;
		}
		return false;
	}
	
	/*THIS IS THE UPDATE CODE */
	/*Only MS-DMER constraints ... called only from dropActiveRole or dropAllActiveRoles methods ...*/
	public void handleRoleDropForConstraints(RBACConstraint[] multiSessionConstraints){
		try{
			if (multiSessionConstraints != null)
			for (int i = 0; i < multiSessionConstraints.length; i++){
				for (int j = 0; j < multiSessionConstraints[i].rSet.length; j++){
					if (isRoleActive(multiSessionConstraints[i].rSet[j])){
						String s = null;
						if (multiSessionConstraints[i].role2SessionMapping.get(multiSessionConstraints[i].rSet[j]) == null){
							System.out.println("Role is not in roleMapping ..." );
							print();
							System.out.println("Exited by force !!");
							System.exit(0);
						}else{
							for (Iterator it = multiSessionConstraints[i].role2SessionMapping.get(multiSessionConstraints[i].rSet[j]).
										   iterator(); it.hasNext();){
								s = (String) it.next();
								if (s.equals(this.getID())) break;
							}
						if (s != null) multiSessionConstraints[i].role2SessionMapping.
											get(multiSessionConstraints[i].rSet[j]).remove(s);
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println("Exception in handleConstraints " + e.getMessage());
			System.exit(0);
		}
	}
	
	public boolean dropActiveRole(RBACRole r, RBACConstraint[] multiSessionConstraints){
		handleRoleDropForConstraints(multiSessionConstraints);
		if (activeRoles != null){
			for (Iterator<RBACRole> it = activeRoles.iterator(); it.hasNext();){
				RBACRole role = it.next();
				if (role.getID().equals(r.getID())) {
					activeRoles.remove(role);
					System.out.println("ROLE DROPPED .....");
					return true;
				}
			}
		}
		return false;
	}
	

	public void dropAllActiveRoles(RBACConstraint[] multiSessionConstraints){
		handleRoleDropForConstraints(multiSessionConstraints);		
		if (activeRoles != null) activeRoles.clear();
	}
	
	private boolean alreadyAdded(Vector<RBACPermission> perSet, RBACPermission perm){
		for (Iterator it = perSet.iterator(); it.hasNext();){
			RBACPermission p = (RBACPermission) it.next();
			if (p.getID().equals(perm.getID())) return true;
		}
		return false;
	}
	
	public Vector<RBACPermission> getSessionPermissions(){
		Vector<RBACPermission> permissions = new Vector<RBACPermission>();
		for (Iterator it = activeRoles.iterator(); it.hasNext();){
			RBACRole role = (RBACRole) it.next();
			for (int i = 0; i < role.perms.length; i++){
				if (!alreadyAdded(permissions,role.perms[i])){
					permissions.add(role.perms[i]);
				}
			}
		}
		if (permissions.size() == 0) return null;
		return permissions;
	}
	
	public Vector<RBACRole> getActiveRoles(){
		return activeRoles;
	}
	
	public boolean isRoleActive(RBACRole r){
		for (int i = 0; i < activeRoles.size(); i++){
			if (activeRoles.get(i) == r || activeRoles.get(i).getID().equals(r.getID())) return true;	
		}
		return false;
	}
	public boolean checkPermission(RBACPermission p){
		for (int i = 0; i < activeRoles.size(); i++){
			if (activeRoles.get(i).hasPermission(p)) return true;
		}
		return false;
	}
	
	/*public String getID(){
		return id;
	}*/
	
	public String print(){
		String str = id + " (" + user.getID() + ") {";
		System.out.print(str);
		for (int i = 0; i < activeRoles.size(); i++) {
			String s = activeRoles.get(i).getID() + ((i + 1)  < activeRoles.size() ? "," : "");
			str = str + s;
			System.out.print(s);
		}
		str = str + "}" + "\n";
		System.out.println("}");
		return str;
	}
}





/*RBAC Runtime: RBACSession and RBACstate
 * History is a vector of states...*/
class RBACState extends RBACElement{
	private HashMap<RBACUser, Vector<RBACSession>> user2sessions;
	//String id;
	
	public RBACState(String i){
		id = i;
		user2sessions =  new HashMap<RBACUser, Vector<RBACSession>>();
	}
	
	public RBACState(String i, HashMap<RBACUser, Vector<RBACSession>> u2sessions){
		id = i;
		user2sessions =  createDeepCloneForU2S(u2sessions);
	}
	
	/*public String getID(){
		return id;
	}*/
	
	/*VERIFIED ....
	 * To copy the content of a session, deep clone not shallow...*/
	public HashMap<RBACUser,Vector<RBACSession>> createDeepCloneForU2S(HashMap<RBACUser, Vector<RBACSession>> object){
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
	
	public HashMap<RBACUser, Vector<RBACSession>> getSetOfSessions(){
		return user2sessions;
	}
	
	public RBACSession getSession(RBACSession session){
		for (Map.Entry entry : user2sessions.entrySet()){
			Vector<RBACSession> setOfSessions = (Vector<RBACSession>) entry.getValue();
			for (Iterator it = setOfSessions.iterator(); it.hasNext();){
				RBACSession s = (RBACSession) it.next();
				if (s.getID().equals(session.getID())){
					return s;
				}
			}
		}
		return null;
	}
	
	public String print(){
		String str = id + " ->" + "\n";
		System.out.println(str);
		for (Map.Entry entry : user2sessions.entrySet()){
			Vector<RBACSession> setOfSessions = (Vector<RBACSession>) entry.getValue();
			for (Iterator it = setOfSessions.iterator(); it.hasNext();){
				RBACSession ses = (RBACSession) it.next();
				str = str + ses.getID() + "("+ ses.user.getID() +")"+ " {";
				System.out.print(ses.getID() + "("+ ses.user.getID() +")" + " {");
				for (int k = 0; k < ses.activeRoles.size(); k++){
					String s = ses.activeRoles.get(k).getID() + ((k + 1)  < ses.activeRoles.size() ? "," : "");
					str = str + s;
					System.out.print(s);
				}
				str = str + "}" + "\n";
				System.out.println("}");
			}
		}
		return str;
	}
}



/*Core RBAC Implementation*/
public class RBAC{
		
	public RBACUser[] users;
	public RBACRole[] roles;
	
	/*Type of constraint and the constraints themselves */
	public HashMap<Integer, RBACMERConstraint[]> merConstraints;
	
	
	/* SOD SUPPORT
	 * 18.12.2011
	 * This is to support SoD constraints of the form ({r1,..,rn}, k)    NOT MER*/
	public HashMap<Integer,RSoDConstraint[]> rSODConstraints;
	
	/* SOD SUPPORT
	 * 17.12.2011
	 * This is to support SoD constraints of the form ({p1,..,pn}, k) */
	//public SoDConstraint[] sodConstraints;
	
	//public Vector<RBACSession> sessions;  
	
	HashMap<RBACUser, Vector<RBACSession>> user2sessions;
	private RBACPermission[] permissions;
	private LinkedHashMap<String, Integer> varIDs;
	/*HISTORY 
	 * We dont need to implement the activities 
	 * (CheckAccess is called then modify the history, or a new session has 
	 * been created modify the history) for our experiments,*/
	private Vector<RBACState> history;
	
	
	/*DELETE */
	BufferedWriter difference;
	
	/*set of users, roles, permissions, constraints and number of sessions ....*/
	public RBAC(RBACUser[] u, RBACRole[] r, RBACPermission[] prms, HashMap<Integer,RBACMERConstraint[]> mConsts, 
											HashMap<Integer,RSoDConstraint[]> rsodConsts){
		users = u;
		roles= r;
		permissions = prms;
		merConstraints = mConsts;
		rSODConstraints = rsodConsts;
		user2sessions = new HashMap<RBACUser,Vector<RBACSession>>();
		history = new Vector<RBACState>();
		
		try{
			difference = new BufferedWriter(new FileWriter("tests//diffForSACMAT09.txt",true));
		} catch(Exception e){
			System.out.println("Exception in Experiments constructor..." + e.getMessage());
		}
	}
	
	public void setupVarIDs(){
		setVarIDsForSoDAndRolesAndPermissions();
		if (merConstraints != null) adjustMERConstraintClauses();
	}
	
	protected void finalize() throws Throwable{
		  difference.close();
		  super.finalize(); //not necessary if extending Object.
	}
	
	public void addToFile(String str){
		try{
			//System.out.println("STR :" + str);
			difference.append(str);
		}catch(Exception e){
			System.out.println("Exception in xMatchRoleTest " + e.getMessage());
		}
	}
	
	
	/*Change : 24.03.2012, Added the initial load of varids from RSoD constraints ...*/
	public void setVarIDsForSoDAndRolesAndPermissions(){
		varIDs = new LinkedHashMap<String, Integer>();
		
		/*18.09.2012 trying new RSoD encoding*/
		/*if (rSODConstraints != null){ 
			for (int i = 0; i < rSODConstraints.length; i++){
				RSoDConstraint rsod = rSODConstraints[i];
				for (Map.Entry entry : rsod.getVarIDs().entrySet()){
					String name = (String) entry.getKey();
					int id = (Integer) entry.getValue();
					varIDs.put(name, id);
				}
			}
		}*/
		
		/*System.out.println("VAR ID size " + varIDs.size());
		for (Map.Entry<String, Integer> entry : varIDs.entrySet()){
			System.out.println(entry.getKey() + " --> " + entry.getValue());
		}*/
		
		for (int i = 0; i < roles.length; i++){
			varIDs.put(roles[i].getID(), varIDs.size() + 1);
		}
		//System.out.println("SIZE NOW" + varIDs.size());
		for (int i = 0; i < permissions.length; i++){
			varIDs.put(permissions[i].getID(), varIDs.size() + 1);
		}
		//System.out.println("SIZE NOW" + varIDs.size());
		//System.exit(0);
	}

	public LinkedHashMap<String, Integer> getVarIDs(){
		return varIDs;
	}
	
	private int returnVarIdForRBAC(String elemID){
		//System.out.println("ID "+ elem.getID() + " " + elem.getClass().toString());
		for ( String id : varIDs.keySet()){
			if (elemID.equals(id)){
				//System.out.println("Found " + id);
				return varIDs.get(id);
			}
		}
		
		int lastId = varIDs.size() + 1;
		varIDs.put(elemID, lastId);
		return lastId;
	}
	
	public Vector<Vector<Integer>> replaceIntermediateVariables(String constID,int varNum, int lengthExisting, 
																					Vector<Vector<Integer>> result){
		try{
			//System.out.println();
			//System.out.println("INPUT replaceIntermediateVariables...");
			//printClauses(formatClauses(result));
			Vector<Vector<Integer>> resultingVector = createDeepCloneClauses(result);
			for (int i = 0; i < (varNum - lengthExisting); i++){
				for (int j = 0; j < resultingVector.size(); j++){
					for (int k = 0; k < resultingVector.get(j).size(); k++){
						if ((Math.abs((Integer)resultingVector.get(j).get(k))) == (lengthExisting + i + 1)){ 
							int newValue = (Integer) resultingVector.get(j).get(k) < 0 ?
									-1 * returnVarIdForRBAC(constID+ "_" + String.valueOf((Math.abs((Integer)resultingVector.get(j).get(k))))) :
										returnVarIdForRBAC(constID + "_" + String.valueOf((Math.abs((Integer)resultingVector.get(j).get(k)))));
									resultingVector.get(j).set(k, newValue);
						}
					}
				}
			}
		//System.out.println("OUTPUT replaceIntermediateVariables...");
		//printClauses(formatClauses(resultingVector));
		//System.out.println();
		return resultingVector;
		}catch(Exception e){
			System.out.println("Exception in replaceIntermediateVariables " + e.getMessage());
		}
	return null;
	}	
	
	
	/*Send "" for a proper replacement. If a tagging is necessary such "SS-HMER" then the variable
	 * addVarStr must be set...*/
	public Vector<Vector<Integer>> replaceRBACVariables(String addVarStr, RBACRole[] roles, 
																		  Vector<Vector<Integer>> result/*, int top*/){
		try{
			//System.out.println();
			//System.out.println("INPUT replaceRBACVariables...");
			//printClauses(formatClauses(result));
			Vector<Vector<Integer>> resultingVector = createDeepCloneClauses(result);
			for (int j = 0; j < resultingVector.size(); j++){
				//System.out.println(" Result Size " + result.get(j).size());
				for (int k = 0; k < resultingVector.get(j).size(); k++){
					for (int i = 0; i < roles.length; i++){
						//System.out.println("Role " + roles[i].getID() + " Result " + (Integer)result.get(j).get(k));
						if ((Math.abs((Integer)resultingVector.get(j).get(k))) == (i + 1)){ 
							int newValue = (Integer)resultingVector.get(j).get(k) < 0 ? 
											-1 * returnVarIdForRBAC(roles[i].getID() + addVarStr) :
											returnVarIdForRBAC(roles[i].getID() + addVarStr);
							resultingVector.get(j).set(k, newValue);
							break;
						} 
					}
				}
			}
			//System.out.println("OUTPUT replaceRBACVariables...");
			//printClauses(formatClauses(resultingVector));
			//System.out.println();
			return resultingVector;
		}catch (Exception e){
			System.out.println("Exception in replaceRBACVariables " + e.getMessage());
		}
			//printClauses(formatClauses(result));
		return null;
	}
	
	
	public static Vector<Vector<Integer>> createDeepCloneClauses(Vector<Vector<Integer>> resultVector){
		Vector<Vector<Integer>> res = new Vector<Vector<Integer>>();
	
			for (Iterator it = resultVector.iterator(); it.hasNext();){
				Vector cl = (Vector) it.next();
				Vector newVector = new Vector();
				for (Iterator itCl = cl.iterator(); itCl.hasNext();){
					newVector.add((Integer) itCl.next());
				}
				res.add(newVector);
			}
		
		return res;
	}	
	
	public void adjustMERConstraintClauses(){
		for (Map.Entry entry : merConstraints.entrySet()){
			RBACMERConstraint[] cList = (RBACMERConstraint[]) entry.getValue();
			for (int i = 0; i < cList.length; i++){
				cList[i].clausesAndTimes.clauses = replaceIntermediateVariables(cList[i].getID(), cList[i].var, 
											 cList[i].rSet.length, cList[i].clausesAndTimes.clauses);
				cList[i].clausesAndTimes.clauses = replaceRBACVariables(""/*cList[i].getID()*/,
																/*findType(cList[i].type)*/ 
											 cList[i].rSet, cList[i].clausesAndTimes.clauses);
				/*cList[i].clausesAndTimes.clauses = replaceLiterals(cList[i].getID(), 
																   cList[i].clausesAndTimes.clauses,
																   cList[i].roles);*/
				
			}
		}
	}
	
	
	/*STANDARD RBAC FUNCTIONS ......................................................................*/
	public RBACSession createSession(String id, String user, RBACRole[] actRoles){
		RBACUser u = findUser(user);
		if (u == null) {System.out.println("User does not exist"); return null;}
		RBACSession session = new RBACSession(id, u, actRoles);
		Vector<RBACSession> sessionList = user2sessions.get(u);
		if (sessionList == null) {
			sessionList = new Vector<RBACSession>();
			sessionList.add(session);
		}
		else sessionList.add(session);
		user2sessions.put(u,sessionList);
		return session;
	}
	/*IMPLEMENT ....*/
	/*public RBACSession deleteSession(String id){
		RBACSession s = findUser(user);
		if (u == null) {System.out.println("User does not exist"); return null;}
		RBACSession session = new RBACSession(id, u, actRoles);
		sessions.add(session) ;
		return session;
	}*/
	
	
	public RBACPermission[] getPermissions(){ return permissions;}
	public void listPermissions(){
		for (int i = 0; i < permissions.length; i++){
			System.out.println(permissions[i].obj.getID() + " " + permissions[i].op.getID());
		}
	}
	
	
	private RBACObject findObject(String obj){
		for (int i = 0; i < permissions.length; i++){
			if (obj.equals(permissions[i].obj.getID())) return permissions[i].obj;
		}
		return null;
	}
	
	private RBACOperation findOperation(String op){
		for (int i = 0; i < permissions.length; i++){
			if (op.equals(permissions[i].op.getID())) return permissions[i].op;
		}
		return null;
	}
	
	public RBACUser findUser(String user){
		for (int i = 0; i < users.length; i++){
			if (user.equals(users[i].getID())) return users[i];
		}
		return null;
	}
	
	public boolean checkAccess(String user, String operation, String object){
		for (int i = 0; i < users.length; i++){
			RBACUser u = users[i];
			if (u.getID().equals(user)){
				for (int j = 0; j < u.assignedRoles.length; j++){
					RBACRole r = u.assignedRoles[j];
					RBACObject obj = findObject(object);
					RBACOperation op = findOperation(operation);
					if (op != null && obj != null){
						if (r.hasPermission(new RBACPermission("p" + i, obj , op))) return true;
					} else return false;
				}
			}
		}
		
		return false;
	}
	
	
	public RBACRole[] findRolesFromPermission(RBACPermission perm){
	  	Vector<RBACRole> foundRoles = new Vector<RBACRole>();
		int j = 0;
		for (int i = 0; i < roles.length; i++){
			if (roles[i].hasPermission(perm)) {
				foundRoles.add(roles[i]);
			}
		}
		RBACRole[] fRoles = new RBACRole[foundRoles.size()];
		return foundRoles.toArray(fRoles);
	}
	
	public Vector<RBACState> getHistory(){
		return history;
	}
	
	public void addPastState(RBACState state, RBACSession session){
		history.add(state); 
		user2sessions = state.getSetOfSessions();
		
		/*THIS IS THE UPDATE CODE */
		if (merConstraints != null || rSODConstraints != null) {
			setConstraintR2SMap(session);
		}
	}
	
	/*This method is called after a new state is added...
	 * What it does:*/
	public void setConstraintR2SMap(RBACSession changedSession){
		if (changedSession != null){ //This is for initial state...
			if (merConstraints != null){
				for (Map.Entry<Integer,RBACMERConstraint[]> entry : merConstraints.entrySet()){
					RBACMERConstraint[] cList = (RBACMERConstraint[]) entry.getValue();
					for (int i = 0; i < cList.length; i++){
						RBACMERConstraint cons = cList[i];
						
						for (int j = 0; j < cons.rSet.length; j++){
							boolean alreadyAdded = false;
							if (cons.type == RBACMERConstraint.MS_DMER_CONST || cons.type == RBACMERConstraint.MS_HMER_CONST){ 
								//MS-DMER + MS-HMER

								//for (Iterator it = user2sessions.get(changedSession.user).iterator(); it.hasNext();){
								//	RBACSession s = (RBACSession) it.next();
									alreadyAdded = adjustConstraintSessionRoleMapping(changedSession, cons, cons.rSet[j]);
									/*if (/*!s.getID().equals(changedSession.getID()) && *//*s.isRoleActive(cons.rSet[j])){
										/*Vector<String> sessions = cons.role2SessionMapping.get(cons.rSet[j]);
										if (sessions == null){
											sessions = new Vector<String>();
											cons.role2SessionMapping.put(cons.rSet[j], sessions);
											alreadyAdded = true;
										}
										sessions.add(changedSession.getID());
									}*/
								//}
							}
							if (cons.type == RBACMERConstraint.SS_HMER_CONST ||
												(!alreadyAdded && cons.type == RBACMERConstraint.MS_HMER_CONST)){ 
								// SS-HMER + MS-HMER
								adjustConstraintSessionRoleMapping(changedSession, cons, cons.rSet[j]);
								/*if (changedSession.isRoleActive(cons.rSet[j])){
									Vector<String> sessions = cons.role2SessionMapping.get(cons.rSet[j]);
									if (sessions == null){
										sessions = new Vector<String>();
										cons.role2SessionMapping.put(cons.rSet[j], sessions);
									}
									sessions.add(changedSession.getID());
								}*/
							}
						}
						
					}
				}
				for (Map.Entry<Integer,RBACMERConstraint[]> entry : merConstraints.entrySet()){
					RBACMERConstraint[] cList = entry.getValue();
					for (int i = 0; i < cList.length; i++){
						RBACMERConstraint cons = cList[i];
						for (int j = 0; j < cons.rSet.length; j++){
							Vector<String> sessions = cons.role2SessionMapping.get(cons.rSet[j]);
							if (sessions != null) System.out.println("For role " + cons.rSet[j].getID() + " --> " +
																						sessions.size() + " sessions");
						}
					}
				}
				System.out.println();
			}
			
			/* 30 September, added the propagation of role activations...
			 * Handling the history-based RSoD constraints... 
			 * By checking whether the role is active in the changed session, it sets the 
			 * roleMapping<Role,Set of Sessions> member of the constraint.
			 * */
			if (rSODConstraints != null){
				for (Map.Entry<Integer,RSoDConstraint[]> entry : rSODConstraints.entrySet()){
					RSoDConstraint[] cList = entry.getValue();
					for (int i = 0; i < cList.length; i++){
						RSoDConstraint cons = cList[i];
						for (int j = 0; j < cons.rSet.length; j++){
							///if (cons.type == RSoDConstraint.D_RSoD_CONST){
							//for (Iterator<RBACSession> it = user2sessions.get(changedSession.user).iterator(); it.hasNext();){
								//RBACSession s = it.next();
								adjustConstraintSessionRoleMapping(changedSession, cons,cons.rSet[j]);	
							//}
							//}
						}
					}
				}
				/*RSoDConstraint[] sodConstraints = rSODConstraints.get(RSoDConstraint.D_RSoD_CONST);
				for (int i = 0; i < sodConstraints.length; i++){
					for (int j = 0; j < sodConstraints[i].rSet.length; j++){
						adjustConstraintSessionRoleMapping(changedSession, sodConstraints[i],sodConstraints[i].rSet[j]);	
					}
				}
				sodConstraints = rSODConstraints.get(RSoDConstraint.H_RSoD_CONST);
				for (int i = 0; i < sodConstraints.length; i++){
					for (int j = 0; j < sodConstraints[i].rSet.length; j++){
						adjustConstraintSessionRoleMapping(changedSession, sodConstraints[i],sodConstraints[i].rSet[j]);
					}
				}*/
				for (Map.Entry<Integer,RSoDConstraint[]> entry : rSODConstraints.entrySet()){
					RSoDConstraint[] cList = entry.getValue();
					for (int i = 0; i < cList.length; i++){
						RSoDConstraint cons = cList[i];
						for (int j = 0; j < cons.rSet.length; j++){
							Vector<String> sessions = cons.role2SessionMapping.get(cons.rSet[j]);
							if (sessions != null) System.out.println("For role " + cons.rSet[j].getID() + " --> " +
																						sessions.size() + " sessions");
						}
					}
				}
				System.out.println();
			}

		}	
	}
	
	
	public boolean adjustConstraintSessionRoleMapping(RBACSession changedSession, RBACConstraint constraint, RBACRole role){
		boolean alreadyAdded = false;
		if (changedSession.isRoleActive(role)){
			Vector<String> sessions = constraint.role2SessionMapping.get(role);
			if (sessions == null){
				sessions = new Vector<String>();
				constraint.role2SessionMapping.put(role, sessions);
				alreadyAdded = true;
			}
			sessions.add(changedSession.getID());
			System.out.println("Here we are");
		}	
		return alreadyAdded;
	}
	
	public boolean dropState(RBACState state){
		for (Iterator it = history.iterator(); it.hasNext();){
			RBACState s = (RBACState) it.next();
			if (state.getID().equals(s.getID())){
				history.remove(s);
				return true;
			}
		}
		return false;
	}
	
	public void print(boolean toFile){
		
		if (toFile) addToFile("Users ..." + "\n");
		System.out.println("Users ...");
		for (int i = 0; i < users.length; i++){
			String str = users[i].print();
			if (toFile) addToFile(str);
		}
		
		if (toFile) addToFile("Roles ..." + "\n");
		System.out.println("Roles ...");	
		for (int i = 0; i < roles.length; i++){
			String str = roles[i].print(true);
			if (toFile) addToFile(str);
		}
		
		if (toFile) addToFile("MER Constraints ..." + "\n");
		System.out.println("MER Constraints ...");
		if (merConstraints != null)
		for (Map.Entry<Integer, RBACMERConstraint[]> entry : merConstraints.entrySet()){
			int type = (Integer) entry.getKey();
			RBACMERConstraint[] consts = entry.getValue();
			for (int i = 0; i < consts.length; i++){
				String str = consts[i].print();
				if (toFile) addToFile(str);
			}
		}
		if (toFile) addToFile("RSoD Constraints ..." + "\n");
		System.out.println("RSoD Constraints ...");
		if (rSODConstraints != null)
		for (Map.Entry<Integer, RSoDConstraint[]> entry : rSODConstraints.entrySet()){
			RSoDConstraint[] rsodCons = entry.getValue();
			for (int i = 0; i < rsodCons.length; i++){
				String str = rsodCons[i].print();
				if (toFile) addToFile(str);
			}
		}
		if (toFile) addToFile("Sessions ..."+ "\n");
		System.out.println("Sessions ...");
		if (user2sessions != null)
			for (Map.Entry entry : user2sessions.entrySet()){
				Vector<RBACSession> sessions = (Vector<RBACSession>) entry.getValue();
				for (Iterator it = sessions.iterator(); it.hasNext();){
					RBACSession ses = (RBACSession) it.next();
					String str = ses.print();
					if (toFile) addToFile(str);
				}
			}
		
		if (toFile) addToFile("States ..." + "\n");
		System.out.println("States ...");
		if (history != null)
		for (Iterator it = history.iterator(); it.hasNext();){
			RBACState st = (RBACState) it.next();
			String str = st.print();
			if (toFile) addToFile(str);
		}
		
		
	}
	

	
	
	/*THE FOLLOWING METHODS ARE DUPLICATES/REDUNDANT ....*/
	
	public String findIdFromVarID(int id){
		for ( Map.Entry<String, Integer> entry : varIDs.entrySet()){
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
	/*******************************************/
	
	
	
	
	
	public static void main(String[] args){
		
		RBAC system = RBACInstantiations.generateRBACWithoutHierarchy(false); 
		system.print(false);
		if (system.checkAccess("alice", "Report", "T2"))
			System.out.println("Permit");
		else System.out.println("Deny");
	}
}



