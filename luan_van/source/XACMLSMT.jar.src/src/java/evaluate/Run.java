package evaluate;

import java.awt.Desktop;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experiments.Evaluate;
import experiments.Writer;
import pn_xacm.ReadXML;

public class Run {

	public static void main(String[] args) throws Exception {
		boolean exit = false;
		do {
			doMainMenu();
			int number = 0;

			Scanner scanner = new Scanner(System.in);
			do {
				printInputMessage();
				String input = scanner.nextLine();
				while (input.length() == 0) {
					printInputMessage();
					input = scanner.nextLine();
				}

				Pattern pattern = Pattern.compile("\\d*");
				Matcher matcher = pattern.matcher(input);

				/* Check input is a number ? */
				if (matcher.matches()) {
					number = Integer.parseInt(input);
				} 
				if (number > 7 || number <= 0) {
					printWarningMessage();
					printBreakLine();
				}
			} while (number <= 0 || number > 7);

			switch (number) {
			case 1: // Create Policy
				String filePath_2 = "src/ALFA/";
				System.out.print("\n\t Input Policy Name: ");
				String fileName_2 = scanner.nextLine();

				File alfa_2 = Writer.createAlfaFile(filePath_2, fileName_2);
				Desktop desktop = Desktop.getDesktop();
				desktop.open(alfa_2);
				printCreateSuccessMessage();
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 2: // Create Request
				Scanner scannerCase2_1 = new Scanner(System.in);
				String dirPath = "requests/";
				System.out.print("\n\t Input Request Name: ");
				String fileName2 = scannerCase2_1.nextLine();
				Writer.createRequestFile(dirPath, fileName2);
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 3: // Query analysis
				ReadXML.QueryAnalysis();
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 4: // Config Database
				System.out.print("\t Input Domain: ");
				String domain = scanner.nextLine();
				System.out.print("\t Input Port: ");
				String portString = scanner.nextLine();
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 5: // Evaluate Classic Policy
				try {
					ReadXML.testAnalysisPolicy(ReadXML.QueryAnalysis());
				} catch (Exception e) {
					e.printStackTrace();
				}
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 6:
//				String directory = "requests";
//				String inputPath = Reader.getLastestFilefromDir(directory);
				String inputPath = "requests/requestValue.txt";
				long startTime = System.currentTimeMillis();
				Evaluate.evaluate(inputPath, '=');
				long endTime = System.currentTimeMillis();
				long evaluateTime = endTime - startTime;
				System.out.println("\n\t - Evaluate Time: " + evaluateTime + " ms");
				// check continue to do loop
				exit = doContinueLoop(scanner);
				printBreakLine();
				break;

			case 7:
				printSystemStopped();
				printBreakLine();
				exit = true;
				break;

			default:
				printInputMessage();
				break;
			}
		} while (!exit);
	}

	private static void doMenuCreateRequest() {
		System.out.println("\n\t\t Create request \n");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 1. Create request \t\t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 2. Import request \t\t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 3. Exit \t|");
		System.out.println("\t ----------------------------------------");
	}

	private static void doMainMenu() {
		System.out.println("\n\t\t Evaluation System Policy \n");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 1. Create Policy \t\t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 2. Create Request \t\t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 3. Query analysis \t\t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 4. Config Database  \t\t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 5. Evaluate Security Policy \t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 6. Evaluate Privacy Policy \t\t|");
		System.out.println("\t ------------------------------------------------");
		System.out.println("\t |\t 7. Exit \t\t\t\t|");
		System.out.println("\t ------------------------------------------------");
	}

	private static void doMenuCreatePolicy() {
		System.out.println("\n\t\t Create Policy \n");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 1. Create alfa follow form \t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 2. Create alfa on file \t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 3. Exit \t\t\t|");
		System.out.println("\t ----------------------------------------");
	}

	private static void doMenuGeneratePolicyAndRequest() {
		System.out.println("\n\t\t Generate Policy and Request \n");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 1. Gen policy \t\t\t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 2. Gen request \t\t|");
		System.out.println("\t ----------------------------------------");
		System.out.println("\t |\t 3. Exit \t\t\t|");
		System.out.println("\t ----------------------------------------");
	}

	private static void printInputMessage() {
		System.out.print("\n\t - Please input your choice: ");
	}

	private static void printCreateSuccessMessage() {
		System.out.println("\t *****************************************************************");
		System.out.println("\t                        File is created !!!                       ");
		System.out.println("\t *****************************************************************");
	}

	private static void printWarningMessage() {
		System.out.println("\n\t Your choice is NOT SUITABLE. Please input your choice again !!!");
	}

	private static void printSystemStopped() {
		System.out.println("\n\t ----------------------------------------");
		System.out.println("\t |         System has stopped !!!       |");
		System.out.println("\t ----------------------------------------");
	}

	private static boolean doContinueLoop(Scanner scanner) {
		boolean exit = false;
		System.out.print("\n\t Do you want to continue ? (Y/N) -> ");
		String choice = scanner.nextLine();
		if (choice.equals("y") || choice.equals("Y")) {
			exit = false;
		} else {
			exit = true;
			printSystemStopped();
		}
		return exit;
	}

	private static void printBreakLine() {
		System.out.println("\n **************************************************************");
	}
}
