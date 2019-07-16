package graphs_Constraints;

import java.util.ArrayList;


public class Constraint{

	/*Relation types:
	 * 1. unary
	 * 2. binary
	 * 3. ternary
	 * 4. quaternary
	 * 5. 5-ary
	 * */
	/*The first parameter is the variable type
	 * The second parameter is the value*/
	int[] scope;
	int[] values;
	int arity;
	
	public Constraint(int[] sc, int[] rels){
		arity = sc.length;
		scope = sc;
		values = rels;
	}
	
	public static boolean enforce(){
		return false;
	}
	
	public void displayContent(){
		System.out.print("Arity: " + arity);
		for(int i = 0; i < scope.length; i++){
			System.out.print("  varName:"+ i + " " + scope[i] + " value: " + values[i]);
		}
		System.out.println();
	}

}
