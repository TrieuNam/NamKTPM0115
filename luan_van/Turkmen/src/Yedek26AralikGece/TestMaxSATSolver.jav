import java.util.Vector;

import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.reader.Reader;
import org.sat4j.tools.OptToSatAdapter;


import junit.framework.TestCase;


public class TestMaxSATSolver extends TestCase {
	private Reader reader;
	WeightedMaxSatDecorator maxSATSolver;
	//ISolver mi;
	OptToSatAdapter opt;
	
	protected void setup(){
		maxSATSolver = new WeightedMaxSatDecorator(org.sat4j.maxsat.SolverFactory.newDefault());
		maxSATSolver . setTimeout (3600);
		opt = new OptToSatAdapter(maxSATSolver);
	}
	
	public void testAddClauses(){
		
	}
	
	public void testRunSATSolverWithClauses(){
		
	}
}
