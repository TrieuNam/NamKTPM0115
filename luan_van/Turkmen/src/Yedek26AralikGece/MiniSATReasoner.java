import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

class ResultContainer{
	DescriptiveStatistics times/* = new DescriptiveStatistics()*/;
	String sTime = null;
	boolean result;
	public ResultContainer(DescriptiveStatistics t, boolean res){
		times = t;
		result = res;
	}
	public ResultContainer(String t, boolean res){
		sTime = t;
		result = res;
	}
	public DescriptiveStatistics getTime(){
		return times;
	}
	public String getsTime(){
		return sTime;
	}
	public boolean getResult(){
		return result;
	}
}


public class MiniSATReasoner {

/******************************** SAT STUFF **************************/
	
	private Vector<int[]> solutions;
	private ISolver mi;
	private Reader reader;
	private ISolver solver;
	
	//private Vector<Vector<Integer>> SATclauses;
	
	//private int solutionIndex = 0; 
	
	//final int VAR_NUM = 1000;
	//final int CLAUSE_NUM = 5000;
	
	public MiniSATReasoner(boolean fromFile){
		solver = org.sat4j.minisat.SolverFactory.newDefault();
		//solver.newVar(VAR_NUM);
		//solver.setExpectedNumberOfClauses(CLAUSE_NUM);
		solutions = new Vector<int[]>(); 
		mi = new ModelIterator (solver);
		mi . setTimeout (100000);
		if (fromFile) reader = new InstanceReader (mi);
	}
	
	
	
	//Add external clauses to the set of clauses...
	public boolean addClauses(int[][] clauses, int varNum) throws ContradictionException{
		try{
			//System.out.println("The number of variables before reset : " + mi.nVars());
			//resetSolver();
			//System.out.println("The number of variables after reset : " + mi.nVars());
			mi.newVar(varNum);
			//System.out.println("The number of variables after newVAR : " + mi.nVars() + " Clauses:" + clauses.length);
			mi.setExpectedNumberOfClauses(clauses.length);
			//printClauses(clauses);
			for (int i=0; i < clauses.length ;i++) {
				int [] clause = clauses[i]; 
				mi.addClause(new VecInt ( clause ));
				//SATclauses.addAll(clauses[i]));
			}
			return true;
		}catch(ContradictionException ce){
			System.err.println("Contradiction Exception in addClauses of MiniSATReasoner!!! ");
			return false;
		}
	}
	
	public void resetSolver(){
		solver.reset();
		mi.reset();
	}
		
	public void printArray(int[][] cl){
		for (int i = 0; i < cl.length; i++){
			for (int j = 0; j < cl[i].length; j++){
				System.out.print(cl[i][j] + " ");
			}
			System.out.println();
		}
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
	
	
	/*Find a solution with the next operator of the SOLVER...*/
	public int[] findTheNextSolution(int iterationNumber){
		try{
			int [] model = null;
			boolean foundSolution = false;
			for (int k = 0; k < iterationNumber; k++){
				 if (mi.isSatisfiable()){
					 //System.out.println("SATISFIABLE !!!");
					// MaxSATReasoner.waitForInput();
					 model = mi . model ();
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
	
	/*It does not totally negate the solution ... a subset of it, for performance ...*/
	public int[] findNegationOfASolution(int[] solution){
		int size = solution.length /*/ 2*/;
		int[] negSolution = new int[size];
		for (int i = 0; i < size; i++)
			negSolution[i] = -1 * solution[i];
		return negSolution;
	}
	
	
	
	
	/*Find a solution with the next operator of the SOLVER...*/
	public int[] findASolutionWithNegation(int[][] clauses, int varNum,
										   int[] solution,int iterationNumber) throws ContradictionException{
		try{
			resetSolver();
			if (!addClauses(clauses, varNum)) return null;
			int[] negSolution = findNegationOfASolution(solution);
			//System.out.println("Negation of a solution...");printModel(negSolution);
			mi.addBlockingClause(new VecInt (negSolution)); 
			
			int[] model = null;
			boolean foundSolution = false;
			for (int k = 0; k < iterationNumber; k++){
				if (mi.isSatisfiable()){
					model = mi . model ();
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
	
	public  ResultContainer runSATSolverWithClauses(int[][] clauses, int varNumber){
		resetSolver();

		long begin = 0;
		DescriptiveStatistics ds = new DescriptiveStatistics();
		try{
			boolean unsat = true ;
			begin = System.currentTimeMillis();
			if (!addClauses(clauses,varNumber)){
				DescriptiveStatistics dsRes = new DescriptiveStatistics();
				dsRes.addValue(System.currentTimeMillis() - begin);
				return new ResultContainer(dsRes, false);
			}
			
			if (mi.isSatisfiable()){
				long end = System.currentTimeMillis();
				//System.out.println("Time to Measure" + (end - begin));
				System . out . println (" Satisfiable  !");
				ds.addValue((end - begin));
				return new ResultContainer(ds, true);
			} else {
				long end = System.currentTimeMillis();
				//System.out.println("Time to Measure" + (end - begin));
				System . out . println (" Unsatisfiable  !");
				ds.addValue((end - begin));
				return new ResultContainer(ds, false);
			}
		}catch (ContradictionException e){
			long end = System.currentTimeMillis();
			System.err.println("Contradiction exception in runSATSolverWithClauses" + e.getMessage());
			ds.addValue((end - begin));
			return new ResultContainer(ds, false);
		}
		catch(TimeoutException e){
			System.err.println("Timeout exception " + e.getMessage());
			return null;
		}
	}
		
	

	

		
	
/*	Find all solutions...
	public Vector<int[]> findAllSolutions(int[][] clauses, int varNumber){
		int[] model = findASolution( clauses, varNumber);
		int i = 0;
		while (model != null){
			//System.out.print("Model " + i +" :");
			i++;
			//printModel(model);
			solutions.add(model);
			model = findASolution(clauses, varNumber);
		}
		return solutions;
	}*/
	
	
	/*Find a solution*/
	public  int[] findASolution( int[][] clauses, int varNumber){
		try{
			for (int i=0; i < clauses.length ;i++) {
				int [] clause = clauses[i]; 
				mi.addClause(new VecInt ( clause ));
			}
			//printClauses(clauses);
			//System.out.println("Actual clauses added ...");
			//addNegationOfFoundSolutions(mi);
			if (mi.isSatisfiable()){
				int [] model = mi . model ();
				solutions.add(model);
				return model;
			}
			
		}catch (ContradictionException e){
			System.out.println("Contradiction exception " + e.getMessage());
		}
		catch(TimeoutException e){
			System.out.println("Timeout exception " + e.getMessage());
		}
		return null;
	}
	

	
	/*Find all solutions...*/
	public void findAllSolutionsWithNext(int[][] clauses, int varNumber){
		try{
			resetSolver();
			// prepare the solver to accept MAXVAR variables . MANDATORY
			mi . newVar ( varNumber );
			// not mandatory for SAT solving . MANDATORY for MAXSAT solving
			mi . setExpectedNumberOfClauses ( clauses.length );
			//if (varNumber != mi.nVars()){
				System.out.println("Number of variables " + varNumber + " In the solver " + mi.nVars());
			//}
			for (int i=0; i < clauses.length ;i++) {
				int [] clause = clauses[i]; 
				mi.addClause(new VecInt ( clause ));
			}
			boolean unSAT = true;
			while (mi.isSatisfiable()){
				int [] model = mi . model ();
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
	
	
	
	public void runSATSolverWithDIMACS(String dimacs){		
		System.out.println("HERE " + dimacs);
		try {
			IProblem problem = reader . parseInstance (dimacs);
			if ( problem . isSatisfiable ()) {
			System . out . println (" Satisfiable  !");
			PrintWriter pw = new PrintWriter(System.out,true);
			reader.decode (problem . model (),  pw);
			System.out.println("number of variables " + mi.nVars());
			} else {
				System . out . println (" Unsatisfiable  !");
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
	}
}
