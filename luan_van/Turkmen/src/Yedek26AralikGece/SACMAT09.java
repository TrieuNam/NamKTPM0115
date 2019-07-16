import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class SACMAT09 {
	Vector<Vector> hardClauses;
	Vector<Vector> softClauses;
	
	int clauseNumber = 0;
	LinkedHashMap<String,Integer> varIds;
	
	public SACMAT09(){
		varIds= new LinkedHashMap<String, Integer>();
		hardClauses = new Vector<Vector>();
		softClauses = new Vector<Vector>();
	}
	
	public int returnVarIdForRBAC(RBACElement elem){
		//System.out.println("ID "+ elem.getID() + " " + elem.getClass().toString());
		for ( String id : varIds.keySet()){
			if (elem.getID() == id){
				return varIds.get(id);
			}
		}
		
		int lastId = varIds.size() + 1;
		varIds.put(elem.getID(), lastId);
		return lastId;
	}
	
	
	
	public String findIdFromVarID(int id){
		for ( Map.Entry<String, Integer> entry : varIds.entrySet()){
			if (entry.getValue() == id){
				return entry.getKey();
			}
		}
		return null;
	}
	
	
	public int[][] formatClauses(Vector<Vector> cls){
		
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
	
	public void findClausesDescendants(RBACRole originRole, RBACRole role){
		if (role.descendants != null){
			for(int j = 0; j < role.descendants.length; j++){
				Vector temp = new Vector();
				temp.add((-1 * returnVarIdForRBAC(originRole)));
				temp.add(returnVarIdForRBAC(role.descendants[j]));
				hardClauses.add(temp);
				findClausesDescendants(originRole, role.descendants[j]);
			}
		}
	}
	
	public void findLiteralsDescendants(Vector temp, RBACRole role){
		if (role.descendants != null){
			for (int j = 0; j < role.descendants.length; j++){
				temp.add(-1 * returnVarIdForRBAC(role.descendants[j]));
				findLiteralsDescendants(temp, role.descendants[j]);
			}
		}
	}
	
	public void clearClauseSets(){
		hardClauses.clear();
		softClauses.clear();
		varIds.clear();
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

    private static int get_nCr(int n, int r)
    {
        if(r > n)
        {
            throw new ArithmeticException("r is greater then n");
        }
        long numerator = 1;
        long denominator = 1;
        for (int i = n; i >= r + 1; i--)
        {
            numerator *= i;
        }
        for (int i = 2; i <= n - r; i++)
        {
            denominator *= i;
        }

        return (int) (numerator / denominator);
    }
	
	/********* Generate Clauses for SACMAT 2009 ***********************************/
	public ResultContainer generateClauses(RBAC system, RBACUser user, 
												RBACPermission[] upperbound, 
												RBACPermission[] lowerbound, 
												int obj){ 
		
		Vector temp;
		DescriptiveStatistics ds = new DescriptiveStatistics();
		
		clearClauseSets();
		
		
		long testStartTime = 0;
		long testEndtime = 0;
		
		testStartTime = System.currentTimeMillis();	
		for (int i=0; i < system.roles.length; i++){
			boolean userHasTheRole = false;
			temp = new Vector<Integer>();
			if (!user.isAssignedRole(system.roles[i])) {
				temp.add(-1 * returnVarIdForRBAC(system.roles[i])); 
				hardClauses.add(temp); 
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		
		/*For now I assumed that the requestedPermissions, upperbound and lowerbound are the same*/
		/*Define Permissions*/
		testStartTime = System.currentTimeMillis();	
		for (int i = 0; i < lowerbound.length; i++){
			temp = new Vector();
			temp.add(returnVarIdForRBAC(lowerbound[i]));
			hardClauses.add(temp);
			//clauseNumber++;
		}
		
		/*System.out.println("STEP 1 -->");
		printClauses(formatClauses(hardClauses));*/
		
		boolean alreadyAdded = false;
		//System.out.println("LENGTH " + system.getPermissions().length);
		for (int i=0; i < system.getPermissions().length; i++){
			for (int j = 0; j < upperbound.length && !alreadyAdded; j++){
				if ((system.getPermissions())[i].getID() == upperbound[j].getID()) {alreadyAdded = true;}
			}
			temp = new Vector();
			if (!alreadyAdded){
				temp.add(-1 * returnVarIdForRBAC((system.getPermissions())[i]));
				alreadyAdded = false;
				hardClauses.add(temp);
				//clauseNumber++;
			}
			alreadyAdded = false;
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);

		/*System.out.println("STEP 2 -->");
		printClauses(formatClauses(hardClauses));*/
		
		//System.out.println("Role number " + system.roles.length);
		/*Define roles*/
		testStartTime = System.currentTimeMillis();
		for (int i = 0; i < system.roles.length/*user.assignedRoles.length*/; i++){
			//RBACRole role = user.assignedRoles[i];
			RBACRole role =  system.roles[i];
			for (int j = 0; j < role.perms.length; j++){
				temp = new Vector();
				temp.add((-1 * returnVarIdForRBAC(role)));
				temp.add(returnVarIdForRBAC(role.perms[j]));
				hardClauses.add(temp);
				//clauseNumber++;
			}
			
			if(role.descendants != null){
				findClausesDescendants(role, role);
			}
			
			temp = new Vector();
			temp.add((returnVarIdForRBAC(role)));
			for (int j = 0; j < role.perms.length; j++){
				temp.add(-1 * returnVarIdForRBAC(role.perms[j]));
			}
			if(role.descendants != null){
				findLiteralsDescendants(temp,role);
			}
			hardClauses.add(temp);
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		
		/*System.out.println("STEP 3 --> ");
		printClauses(formatClauses(hardClauses));*/
		
		
		/*Define Permissions*/
		testStartTime = System.currentTimeMillis();
		for (int i = 0; i < system.getPermissions().length; i++){
			RBACPermission permission = system.getPermissions()[i];
			RBACRole[] rolesFound = system.findRolesFromPermission(permission);
			if (rolesFound == null) {System.out.println("Strange..., I couldnt find a role associated with the permission"); break;}
			//System.out.print("ID " + permission.getID() + "Object "+ permission.obj.getID() + " Operation " + permission.op.getID());
			temp = new Vector();
			temp.add(-1 * returnVarIdForRBAC(permission));
			//System.out.print(permission.getID() + " --> {");
			for (int j = 1; j <= rolesFound.length; j++){
				temp.add(returnVarIdForRBAC(rolesFound[j - 1]));
				//System.out.print(rolesFound[j -1].getID()+ ",");
			}
			//System.out.println("}");
			hardClauses.add(temp);
			//clauseNumber++;
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		
		/*System.out.println("STEP 4 -->");
		printClauses(formatClauses(hardClauses));*/
	
		
		/*Define Constraints*/
		testStartTime = System.currentTimeMillis();
		for (int i = 0; i < system.merConstraints.get(1).length; i++){
			RBACMERConstraint cons = system.merConstraints.get(1)[i];
			int[][] combinations = getCombinations(cons.cardinality, cons.rSet.length);
			for (int j = 0; j < combinations.length; j++){
				temp = new Vector();
				for (int k = 0; k < combinations[j].length; k++){
					temp.add(-1 * returnVarIdForRBAC(cons.rSet[combinations[j][k]]));
				}
				hardClauses.add(temp);
			}	
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		
		/*System.out.println("STEP 5 --> ");
		printClauses(formatClauses(hardClauses));*/
		
		
		
		testStartTime = System.currentTimeMillis();
		if (obj == 0 || obj == 1){
			/*for (int i = 0; i < lowerbound.length; i++){
				temp = new Vector();
				temp.add(returnVarIdForRBAC(lowerbound[i]));
				hardClauses.add(temp);
			}*/
			if (obj == 0){ //min
				for (int i = 0; i < upperbound.length; i++){
					boolean exist = false;
					RBACPermission perm = upperbound[i];
					for (int j = 0; j < lowerbound.length; j++){
						if (perm.getID().equals(lowerbound[j].getID())) { exist = true; break;}
					}
					if (!exist) {
						temp = new Vector();
						temp.add(-1 * returnVarIdForRBAC(perm));
						softClauses.add(temp);
					}
				}
			}
			
			if (obj == 1){ // max
				for (int i = 0; i < upperbound.length; i++){
					boolean exist = false;
					RBACPermission perm = upperbound[i];
					for (int j = 0; j < lowerbound.length; j++){
						if (perm.getID().equals(lowerbound[j].getID())) { exist = true; break;}
					}
					if (!exist) {
						temp = new Vector();
						temp.add(returnVarIdForRBAC(perm));
						softClauses.add(temp);
					}
				}
			}
		}
		testEndtime = System.currentTimeMillis();
		ds.addValue(testEndtime - testStartTime);
		
		/*System.out.println("STEP 6 --> ");
		printClauses(formatClauses(hardClauses));*/
		
		
		
		return new ResultContainer(ds, true);
	}
}
