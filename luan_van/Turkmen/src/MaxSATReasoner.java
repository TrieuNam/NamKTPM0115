import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Vector;

import org.sat4j.core.VecInt;

import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;
import org.sat4j.tools.OptToSatAdapter;
//import org.sat4j.maxsat.SolverFactory;

import com.jezhumble.javasysmon.JavaSysMon;
import com.jezhumble.javasysmon.ProcessInfo;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class MaxSATReasoner {

	final int RUN_TIMES = 1;
	private Vector<int[]> solutions;
	private Reader reader;
	WeightedMaxSatDecorator maxSATSolver;
		//ISolver mi;
	OptToSatAdapter opt;
	
	String RUN_CONSOLE_MACOSX_MSUNCORE = new String("./SAT_Tools/msuncore_MAC-20090525/bin/execute.sh ");
	String RUN_CONSOLE_LINUX_MSUNCORE = new String("./SAT_Tools/msuncore_Linux-20090525/bin/execute.sh ");
	String RUN_CONSOLE_LINUX_INCMAXSATZ = new String("./SAT_Tools/IncMaxsatz/execute.sh ");
	String RUN_CONSOLE_LINUX_AKMAXSAT = new String("./SAT_Tools/akmaxsat_1.1/execute.sh ");
	String RUN_CONSOLE_LINUX_QMAXSAT = new String("./SAT_Tools/QMaxSAT/core/execute.sh ");
	String RUN_CONSOLE_MACOSX_QMAXSAT = new String("./SAT_Tools/QMaxSAT0.11/core/execute.sh ");
	
	public MaxSATReasoner(boolean fromFile){
		 maxSATSolver = new WeightedMaxSatDecorator(org.sat4j.maxsat.SolverFactory.newDefault());
		 solutions = new Vector<int[]>(); 
		 maxSATSolver . setTimeout (3600);
		 opt = new OptToSatAdapter(maxSATSolver);
		 //mi = new ModelIterator(opt);
		 if (fromFile) reader = new InstanceReader (opt);
	}
		
		public void addClauses(int[][] hClauses, int[][] sClauses, int varNum) throws ContradictionException{
			resetSolver();
			//maxSATSolver.setTopWeight(BigInteger.valueOf(top));
			maxSATSolver . newVar ( varNum );
			// not mandatory for SAT solving . MANDATORY for MAXSAT solving
			maxSATSolver . setExpectedNumberOfClauses (hClauses.length + (sClauses == null ? 0 : sClauses.length));
			
			for (int i=0; i < hClauses.length ;i++) {
				int [] clause = hClauses[i]; 
				maxSATSolver.addHardClause(new VecInt ( clause ));
				//SATclauses.addAll(clauses[i]));
			}
			if(sClauses != null)
			for (int i=0; i < sClauses.length ;i++) {
				int [] clause = sClauses[i]; 
				maxSATSolver.addSoftClause(new VecInt ( clause ));
				//SATclauses.addAll(clauses[i]));
			}
		}
		
		public void resetSolver(){
			opt.reset();
			maxSATSolver.reset();
			//mi.reset();
		}
		
		public void printClauses(int[][] cl){
			for (int i = 0; i < cl.length; i++){
				for (int j = 0; j < cl[i].length; j++){
					if (cl[i][j] != 0) System.out.print(cl[i][j] + " ");
				}
				System.out.println();
			}
		}
		
		public void printModel(int[] model){
			for (int j = 0; j < model.length; j++){
				if (model[j] != 0) System.out.print(model[j] + " ");
			}	
			System.out.println();
		}

		
		/*It does not totally negate the solution ... a subset of it, for performance ...*/
		public int[] findNegationOfASolution(int[] solution){
			int size = solution.length / 2;
			int[] negSolution = new int[size];
			for (int i = 0; i < size; i++)
				negSolution[i] = -1 * solution[i];
			
			return negSolution;
		}
		

		
		/*Find a solution with the next operator of the SOLVER...*/
		public int[] findASolutionWithNegation(int[][] hardClauses, int[][] softClauses, int varNum,
											   int[] solution,int iterationNumber) throws ContradictionException{
			try{
				resetSolver();
				addClauses(hardClauses,softClauses,varNum);
				int[] negSolution = findNegationOfASolution(solution);
				maxSATSolver.addBlockingClause(new VecInt (negSolution)); 
				
				int[] model = null;
				boolean foundSolution = false;
				for (int k = 0; k < iterationNumber; k++){
					if (opt.isSatisfiable()){
						model = opt . model ();
						foundSolution = true;
						//printModel(model);
					} else {
						if (!foundSolution) return null;
						else break;
					}
				}
				return model;
				
			}catch(TimeoutException e){
				System.out.println("Timeout exception " + e.getMessage());
				return null;
			}
		}
		
		public ResultContainer runSATSolverWithClauses(int[][] hardClauses, int[][] softClauses, int varNumber){
			
			long begin = 0;
			DescriptiveStatistics ds = new DescriptiveStatistics();
			try{
				boolean unsat = true ;
				System.out.println("BEFORE VAR NUMBER (FN)" + maxSATSolver.nVars());
				System.out.println("BEFORE NUMBER OF CLAUSES (FN)" + maxSATSolver.nConstraints());
				addClauses(hardClauses, softClauses, varNumber);
				System.out.println("ADDED ALL VAR NUMBER (FN) " + maxSATSolver.nVars()); 
				System.out.println("ADDED NUMBER OF CLAUSES (FN)" + maxSATSolver.nConstraints());
				System.out.println("EXPECTED NUMBER OF CLAUSES " + (hardClauses.length + (softClauses == null ? 0 : softClauses.length)));
				boolean unSAT = true;
				System.out.println("Number of variables " + maxSATSolver.nVars() + " " + varNumber);
				
				begin = System.currentTimeMillis();
				if (opt.isSatisfiable()){
					long end = System.currentTimeMillis();
					//System.out.println("Time to Measure" + (end - begin));
					System . out . println (" Satisfiable  !");
					ds.addValue(end - begin);
					return new ResultContainer(ds, true);
				} else {
					long end = System.currentTimeMillis();
					//System.out.println("Time to Measure" + (end - begin));
					System . out . println (" Unsatisfiable  !");
					ds.addValue(end - begin);
					return new ResultContainer(ds, false);
				}
			}catch (ContradictionException e){
				long end = System.currentTimeMillis();
				System.err.println("Contradiction exception in runSATSolverWithClauses" + e.getMessage());
				ds.addValue(end - begin);
				return new ResultContainer(ds, false);
			}
			catch(TimeoutException e){
				System.err.println("Timeout exception " + e.getMessage());
				return null;
			}
		}
		
				
		
		
		/*Find a solution*/
		public  int[] findASolution(int[][] hardClauses, int[][] softClauses){
			try{				
				for (int i=0; i < hardClauses.length ;i++) {
					int [] clause = hardClauses[i]; 
					maxSATSolver.addHardClause(new VecInt ( clause ));
				}
				for (int i=0; i < softClauses.length ;i++) {
					int [] clause = softClauses[i]; 
					maxSATSolver.addSoftClause(new VecInt ( clause ));
				}
				boolean unSAT = true;
				//System.out.println("Number of variables " + maxSATSolver.nVars() + " " + varNumber);
				
				
				//printClauses(clauses);
				//System.out.println("Actual clauses added ...");
				//addNegationOfFoundSolutions(mi);
				
				while (opt.isSatisfiable()){
					int [] model = opt . model ();
					solutions.add(model);
					System.out.println("Satisfiable !!!");
					printModel(model);
					unSAT = false;
					return model;
					
				}
				if (unSAT) System.out.println("Unsatisfiable!");
				
			}catch (ContradictionException e){
				System.out.println("Contradiction exception " + e.getMessage());
			}
			catch(TimeoutException e){
				System.out.println("Timeout exception " + e.getMessage());
			}
			return null;
		}

		
		
		/*Find a solution with the next operator of the SOLVER...*/
		public int[] findTheNextSolution(int iterationNumber){
			try{
				int [] model = null;
				boolean foundSolution = false;
				//OptToSatAdapter optNew = new OptToSatAdapter(maxSATSolver);
				
				//ISolver slv = new ModelIterator(optNew); 
				for (int k = 0; k < iterationNumber; k++){
					 if (opt.isSatisfiable()){
						 model = opt. model ();
						 foundSolution = true;
						printModel(model);
					 } else {
						 if (!foundSolution) {
							 System.out.println("No Solution!!!");
							 return null;
						 }else break;
					 }
				}
				return model;
				 
			}catch(TimeoutException e){
				System.err.println("Timeout exception " + e.getMessage());
				return null;
			}catch(IllegalStateException e){
				System.err.println("Illegal State exception in FindTheNextSolution " + e.getMessage());
				return null;
			}
		}
		
		
		/*Find all solutions...*/
		public void findAllSolutionsWithNext(int[][] hardClauses, int[][] softClauses, int varNumber){
			try{
				resetSolver();
				//maxSATSolver.setTopWeight(BigInteger.valueOf(top));
				maxSATSolver . newVar ( varNumber );
				// not mandatory for SAT solving . MANDATORY for MAXSAT solving
				maxSATSolver . setExpectedNumberOfClauses ( hardClauses.length + (softClauses == null ? 0 : softClauses.length));
				
				for (int i=0; i < hardClauses.length ;i++) {
					int [] clause = hardClauses[i]; 
					maxSATSolver.addHardClause(new VecInt ( clause ));
				}
				if (softClauses != null)
				for (int i=0; i < softClauses.length ;i++) {
					int [] clause = softClauses[i]; 
					maxSATSolver.addSoftClause(new VecInt ( clause ));
				}
				boolean unSAT = true;
				System.out.println("Number of variables " + maxSATSolver.nVars() + " " + varNumber);
				//OptToSatAdapter opt = new OptToSatAdapter(maxSATSolver);
				
				//ISolver solver = new ModelIterator(opt); 
				while (opt.isSatisfiable()){
					int [] model = opt . model ();
					System.out.println("Satisfiable !!!");
					printModel(model);
					unSAT = false;
				}
				if (unSAT) System.out.println("Unsatisfiable!");
				
			}catch (ContradictionException e){
				System.out.println("Contradiction exception " + e.getMessage());
			}
			catch(TimeoutException e){
				System.out.println("Timeout exception " + e.getMessage());
			}
		}
		
		
		public ProcessInfo findProcess(){
			JavaSysMon monitor =   new JavaSysMon();
			ProcessInfo[] processes = monitor.processTable();
			for (int i = 0; i < processes.length; i++){ 
				if (processes[i].getPid() == monitor.currentPid()) return processes[i];
			}
			return null;
		}
			
		
		public ResultContainer runSATSolverWithDIMACSSAT4J(String dimacs){		
			System.out.println("HERE " + dimacs);
			long begin = 0;
			try {
				ProcessInfo p = findProcess();
				if (p == null) {System.out.println("Couldn't find the process."); System.exit(0);}
				begin = p.getUserMillis();
				IProblem problem = reader . parseInstance (dimacs);
				DescriptiveStatistics ds = new DescriptiveStatistics();
				//begin = System.currentTimeMillis();
				if ( problem . isSatisfiable ()) {	
					//p = findProcess();
					long end = p.getUserMillis();
					//long end = System.currentTimeMillis();
					System . out . println (" Satisfiable  ! Begin : " + begin + " End : " + end);
					ds.addValue((end - begin));
					return new ResultContainer(ds, true);
					//PrintWriter pw = new PrintWriter(System.out,true);
					//reader.decode (problem . model (),  pw);
					//System.out.println("number of variables " + maxSATSolver.nVars());
				} else {
					long end = p.getSystemMillis();
					//long end = System.currentTimeMillis();
					System . out . println (" Unsatisfiable  ! Begin : " + begin + " End : " + end);
					ds.addValue((end - begin));
					return new ResultContainer(ds, false);
				}
				} catch ( FileNotFoundException e) {
				    System.out.println("File Not Found exception in runSATSolverWithDIMACS");
				} catch ( ParseFormatException e) {
					System.out.println("Parse Format exception in runSATSolverWithDIMACS");
				} catch ( IOException e) {
					System.out.println("IO exception in runSATSolverWithDIMACS");
				} catch ( ContradictionException e) {
				System .out . println (" Unsatisfiable  ( trivial )!");
				} catch ( TimeoutException e) {
				System .out . println (" Timeout ,  sorry !");
				}
				
			return null;
		}
		
		public static void waitForInput(){
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			try{
				bf.readLine();
			}catch(Exception e){
				System.out.println("There is an exception in waitForInput ...");
			}
		}
		
	public ResultContainer runSATSolverFromConsole(String dimacs){
		try{
			DescriptiveStatistics ds = new DescriptiveStatistics();
			int res = -1;
			for (int i = 0; i < RUN_TIMES; i++){
				Process p = null;
				if (System.getProperty("os.name").contains("Mac"))
					p = Runtime.getRuntime().exec(RUN_CONSOLE_MACOSX_QMAXSAT + dimacs) ;
				else
					p = Runtime.getRuntime().exec(RUN_CONSOLE_LINUX_QMAXSAT + dimacs) ;
				//Process p = new ProcessBuilder("time ./SAT_Tools/msuncore_MAC-20090525/bin/msuncore " + dimacs).start();
				int exitValue = p.waitFor();
				InputStream err = p.getErrorStream(); 
		        OutputStream os = p.getOutputStream();
				InputStream is = p.getInputStream(); 
				BufferedReader inputTime = new BufferedReader(new InputStreamReader(err));
				BufferedReader inputResult = new BufferedReader(new InputStreamReader(is));
				
				String line=null;
				boolean optimumFound = false;
				long time = -1;
				String timeString = "";
				
		        while((line=inputResult.readLine()) != null) {
		        	//System.out.println(line);
		          //waitForInput();
		        	if (line.startsWith("s ")){
		        	  optimumFound = true;
		        	 if (line.contains("OPTIMUM FOUND")){ res = 1;
		        	 //System.out.println("LINE CONTAINS OPTIMUM " + line);
		        		 //int[] model = Integer.parseInt(line.substring(line.lastIndexOf(" "),line.length()).trim());
		        	 } else if (line.contains("UNSATISFIABLE")){ res = 0;} 
		        	 else{ System.out.println("LINE DOES NOT CONTAIN OPTIMUM OR UNSATISFIABLE " + line);}
		        	  
		          }else if (optimumFound && line.startsWith("c")) {
		        	  timeString = "";//line.substring(line.indexOf("Time") + 6, line.length());
		        	  //time = ((Double)(new Double(timeString) * 1000)).longValue();
		        	  //System.out.println("Time From tool " + timeString);
		          } 
		        }
		        while((line = inputTime.readLine()) != null){
		        	//System.out.println(line);
		        	if (line.contains("user")){
			        	  timeString = line.substring(line.indexOf("user") + 4, line.length()).trim();
			        	  //System.out.println(timeString);
			        	  time =  (Long.parseLong(timeString.substring(0, timeString.indexOf("m"))) * 60000) + 
			        	  		((Double)(new Double(timeString.substring(timeString.indexOf("m") + 1, timeString.indexOf("s"))) * 1000.0)).longValue();
			        	  //System.out.println("Output : " + timeString + " time:" + time);
			          }
		        }
		        //System.exit(0);
		        inputTime.close();
		        inputResult.close();
		        is.close();
		        err.close();
		        os.close();
		        ds.addValue(time);
			}
	        if (res == 1){
	        	System.out.println("SATISFIABLE "/* + time */);
	        	return new ResultContainer(ds/*timeString*/, true);
	        }else if (res == 0){
	        	System.out.println("UNSATISFIABLE "/* + time */);
	        	return new ResultContainer(ds/*timeString*/, false);
	        }
	       
		}catch(IOException exc){
				System.err.println("IOException in runSATSolverFromConsole " + exc.getMessage()); 
		}catch (InterruptedException exc){
				System.err.println("InterruptedException in runSATSolverFromConsole " + exc.getMessage()); 
		}catch(Exception e){
				System.out.println("Exception occurred " + e.getMessage());
		}
		System.err.println("No Exception but no result ... !!!");
		System.exit(0);
		return null;
	}

}
