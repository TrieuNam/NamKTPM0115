import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class RBACMERConstraint extends RBACConstraint{
		
	//String id; 
	public int cardinality;
	
	public ClausesAndTime clausesAndTimes;
	public int var = 0;
	
	
	public RBACMERConstraint(String i, RBACRole[] r, int card, int t){
		id = i;
		cardinality = card;
		rSet = r;
		type = t;
		clausesAndTimes = getBooleanCardConstraint();
		role2SessionMapping = new HashMap<RBACRole, Vector<String>>();
	}
	
	/*Prints the current (not the one to be found by the query!!!) settings of the MER elements*/
	public String print(){
		String str = "ID:" + id + " ({";
		System.out.print(str);
		for (int i = 0; i < rSet.length; i++){ 
			str = str + rSet[i].getID();
			System.out.print(rSet[i].getID());
			/*The below can be considered as k-1!!!*/
			Vector<String> sessions = role2SessionMapping.get(rSet[i]);
			if (sessions != null && sessions.size() > 0){
				str = str + "[";
				System.out.print("[");
				for (Iterator it = sessions.iterator(); it.hasNext();){
					String s = ((String)it.next()) + ",";
					str = str + s;
					System.out.print(s);
				}
				str = str + "]";
				System.out.print("]");
			}
			str = str + ((i + 1)  < rSet.length ? "," : "");
			System.out.print(((i + 1)  < rSet.length ? "," : ""));
		}
		str = str + "}, " + cardinality + ") " + findType(type) + "\n";
		System.out.println("}, " + cardinality + ") " + findType(type));
		return str;
	}
	
	
	
	/*01.03.2012 : Changed signature getCardConstraint(RBACMERConstraint constraint) to 
	 * getCardConstraint() so that it uses "this" object...*/
	public ClausesAndTime getBooleanCardConstraint(){
		long timeStart = 0;
		long timeEnd = 0;
		long timeForTheTool = 0;
		DescriptiveStatistics ds = new DescriptiveStatistics();
		timeStart = System.currentTimeMillis();
		try{
			 Vector<Vector<Integer>> result = null;
			 
			 //System.out.println("CARD constraint CNF generation ...");
			 if (cardinality > 0 && ((cardinality - 1) <= rSet.length)){
				 timeEnd = System.currentTimeMillis(); 
				 ds.addValue(timeEnd - timeStart);
				 //System.out.println("Constraint to be generated:" + Integer.toString(constraint.cardinality - 1) + "<" + Integer.toString(roles.length));
				 Process p = new ProcessBuilder("./Cardinality/ParCounter", Integer.toString(cardinality - 1), Integer.toString(rSet.length)).start();
				 //Runtime rt = Runtime.getRuntime() ;
				 //Process p = rt.exec("./Cardinality/ParCounter " + (constraint.cardinality -1) + " " + roles.length, new String[]{"-XX:+HeapDumpOnOutOfMemoryError"}) ;
				 int exitValue = p.waitFor();
				 
				 timeStart = System.currentTimeMillis();
				 
				 //System.err.println("WAIT VALUE " + exitValue);
				 
				 /*CommandLine cmdLine = CommandLine.parse("./Cardinality/ParCounter " + Integer.toString(constraint.cardinality -1) + " " + Integer.toString(roles.length));
				 DefaultExecutor executor = new DefaultExecutor();
				 int exitValue = executor.execute(cmdLine);
				 
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 executor.setStreamHandler(new PumpStreamHandler(baos, baos));*/
				 
				 InputStream err = p.getErrorStream(); 
				 
				 /*BufferedReader input1 = new BufferedReader(new InputStreamReader(err));
				 String line1=null;
		         while((line1=input1.readLine()) != null) {
		             System.err.println(line1);
		         }*/
		         OutputStream os = p.getOutputStream();
				 InputStream is = p.getInputStream();
				 BufferedReader input = new BufferedReader(new InputStreamReader(is));
				 String line=null;
				 boolean start = false;
				// System.out.println("BEFORE LOOPP,... Parameter " + Integer.toString(constraint.cardinality - 1) + " " + Integer.toString(roles.length));
		         while((line=input.readLine()) != null) {
		        	 //System.out.println("LOOPP,...");
		        	 if (line.startsWith("p")){
		        		 var = Integer.parseInt(line.substring(line.indexOf("cnf") + 3, line.lastIndexOf(" ")).trim()); 
		        		 int clauses = Integer.parseInt(line.substring(line.lastIndexOf(" "),line.length()).trim());
		        		 result = new Vector<Vector<Integer>>(clauses);
		        		 start = true;
		        	 } else if (start) result.add(getVectorOfNumbers(line));
		             //System.out.println("LINE " + line);
		        	 if (line.startsWith("user")){
		        		 timeForTheTool = Long.parseLong(line.substring(line.indexOf('.'), line.indexOf('s'))); 
		        	 }
		         }
		         /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		            String read = reader.readLine();*/
		         //os.write('\u0003');
		         input.close();
		         is.close();
		         err.close();
		         os.close();
		         //System.out.println("RSET size " + constraint.roles.length + " Card " + constraint.cardinality);
		         if (result == null) System.out.println("RESULT IS NULL");
		         //printArray(formatClauses(result)); System.exit(0);
				 p.destroy();
				 
				 timeEnd = System.currentTimeMillis(); 
				 ds.addValue(timeEnd - timeStart);
				 
				 /*GCTime++;
				 if (GCTime % 20 == 0) System.gc();*/
				 
				 //rt.freeMemory();
				 //printArray(formatClauses(result));
			 }else{
				 System.err.println("Either constraint.cardinality - 1 > roles.length : " + 
						 						!((cardinality - 1) <= rSet.length) +
						 						" Or constraint.cardinality > 0 :" + !(cardinality > 0));
			 }
			 return new ClausesAndTime(result, (new Double(ds.getSum()).longValue()) + timeForTheTool);
		}catch(IOException exc){
			System.err.println("IOException in getCardConstraint " + exc.getMessage()); 
			return null;
		}catch (InterruptedException exc){
			System.err.println("InterruptedException in getCardConstraint " + exc.getMessage()); 
			return null;
		}
	}
	
	/*public void printArray(int[][] cl){
		for (int i = 0; i < cl.length; i++){
			for (int j = 0; j < cl[i].length; j++){
				System.out.print(cl[i][j] + " ");
			}
			System.out.println();
		}
	}	
	
	public static int[][] formatClauses(Vector<Vector> cls){
		
		int[][] result = new int[cls.size()][0] ;
		for (int i = 0; i < cls.size(); i++){
			int[] clause = new int[cls.get(i).size()];
			for (int j = 0; j < cls.get(i).size(); j++){
				clause[j] = (Integer)cls.get(i).get(j); 
			}
			result[i] = clause;
		}
		return result;
	}*/
	
	public Vector getVectorOfNumbers(String line){
		Vector result = new Vector();
		String[] values = line.split(" ");
		for (int i = 0; i < (values.length - 1); i++){
			result.add(Integer.parseInt(values[i]));
		}
		//System.out.println("Line " + line);
		return result;
	}
}