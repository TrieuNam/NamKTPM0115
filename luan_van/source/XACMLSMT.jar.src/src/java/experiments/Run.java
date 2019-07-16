package experiments;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		
		System.out.println("\n\t\t       Evaluation System Policy \n");
		System.out.print("\t 1. Create Policy \t |");
		System.out.println("\t 2. Create Request");
		System.out.println("\t ---------------------------------------------------");
		System.out.print("\t 3. Connect Database \t |");
		System.out.println("\t 4. Create Database");
		System.out.println("\t ---------------------------------------------------");
		System.out.print("\t 5. Evaluate Policy \t |");
		System.out.println("\t 6. Exit \t");
		
		int number = 0;
		Scanner scanner = new Scanner(System.in);
		System.out.print("\n\t Type in your choice: ");
		number = scanner.nextInt();
		
		switch (number) {
			case 1:
				System.out.println("Case 1: Create Policy");
				
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			default:
				break;
		}
		scanner.close();
	}

}
