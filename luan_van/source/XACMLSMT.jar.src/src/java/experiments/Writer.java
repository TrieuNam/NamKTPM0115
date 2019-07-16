package experiments;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Writer {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input File Path: ");
		String filePath = scanner.nextLine();
		System.out.print("Input File Name: ");
		String fileName = scanner.nextLine();
		File file = null;
		file = createAlfaFile(filePath, fileName);
		writerPolicy(scanner, file);
		scanner.close();

//		System.out.println("Nhap ten File");
//		String aString = scanner.nextLine();
//		writeJson(aString);

	}
	public static String createPath ;
	public static String createPathRequest;
	/**
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static File createAlfaFile(String filePath, String fileName) {
		 createPath = null;

		/* Check fileName has Exist ? */
		while (fileName.length() == 0) {
			System.out.println("\n\t Please Input File Name !!!");
			System.out.println("\n\t *****************************************************************");
			Scanner scanner = new Scanner(System.in);
			System.out.print("\n\t Input File Name: ");
			fileName = scanner.nextLine();
			scanner.close();
		}
		
		/* Check File Name contain .alfa ? */
		if (fileName.contains(".alfa")) {
			createPath = filePath + fileName;
		} else {
			createPath = filePath + fileName + ".alfa";
		}
		File file = new File(createPath.trim());
		try {
			if (file.createNewFile()) {
				return file;
			} else {
				System.out.println("\t *****************************************************************");
				System.out.println("\t                      File already exists !!!                     ");
				System.out.println("\t *****************************************************************");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File createRequestFile(String filePath, String fileName) {
		 createPathRequest = null;
		/* Check fileName has Exist ? */
		while (fileName.length() == 0) {
			System.out.println("\n\t Please Input File Name !!!");
			System.out.println("\n\t *****************************************************************");
			Scanner scanner = new Scanner(System.in);
			System.out.print("\n\t Input File Name: ");
			fileName = scanner.nextLine();
			scanner.close();
		}
		
		/* Check FileName has .xml ? */
		if (fileName.contains(".xml")) {
			createPathRequest = filePath + fileName;
		} else {
			createPathRequest = filePath + fileName + ".xml";
		}
		File file = new File(createPathRequest.trim());
		try {
			if (file.createNewFile()) {
				Desktop desktop = Desktop.getDesktop();
				if (file.exists()) {
					desktop.open(file);
				}
				System.out.println("\t *****************************************************************");
				System.out.println("\t                        File is created !!!                       ");
				System.out.println("\t *****************************************************************");
				return file;
			} else {
				System.out.println("\t *****************************************************************");
				System.out.println("\t                      File already exists !!!                     ");
				System.out.println("\t *****************************************************************");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 * @param scanner
	 * @param file
	 * @throws IOException
	 */
	public static void writerPolicy(Scanner scanner, File file) throws IOException {
		String nameSpace, policySet, resourcePath, actionType, apply, rulePermit, policy, role, ruleDeny;
		System.out.print("namespace ");
//		System.out.print("\t Input namespace: ");
		nameSpace = scanner.nextLine();
		System.out.print("{ \n");
		System.out.println("\t import Attributes_colleges.*");
		System.out.println("\t import ObligationAdvice.*");
//		System.out.print("\t Input policyset: ");
		System.out.print("\t policyset ");
		policySet = scanner.nextLine();
		System.out.print("{ \n");
//		System.out.print("\t Input resourcePath: ");
		System.out.println("\t\t target ");
		System.out.print("\t\t\t clause resource_Student == ");
		resourcePath = scanner.nextLine();
		System.out.println("\t\t\t and resource_Lecturer == ");
//		System.out.print("\t Input actionType: ");
		actionType = scanner.nextLine();
		System.out.print("\t Input apply: ");
		apply = scanner.nextLine();
		System.out.print("\t Input policy: ");
		policy = scanner.nextLine();
		System.out.print("\t Input role: ");
		role = scanner.nextLine();
		System.out.print("\t Input rulePermit: ");
		rulePermit = scanner.nextLine();
		System.out.print("\t Input ruleDeny: ");
		ruleDeny = scanner.nextLine();

		FileWriter fileWriter = new FileWriter(file);

		fileWriter.write("namespace " + nameSpace + " {\r\n" + "	import Attributes.*\r\n" + "\r\n" + "	policyset "
				+ policySet + " {\r\n" + "		target\r\n" + "			clause resourcePath==\"" + resourcePath
				+ "\" and actionType==\"" + actionType + "\"\r\n" + "		apply " + apply + "\r\n" + "\r\n"
				+ "		policy " + policy + " {\r\n" + "			target\r\n" + "				clause role==\"" + role
				+ "\" and actionType==\"" + actionType + "\"\r\n" + "			apply " + apply + "\r\n" + "\r\n"
				+ "			rule " + rulePermit + "{\r\n"
				+ "condition (stringOneAndOnly(resourcePath) == \"8H-AM_8H-PM\") && (stringOneAndOnly(resourcePath) == \"> 16\") &&\r\n"
				+ "				(stringOneAndOnly(resourcePath) == \"Viet_Nam\")\r\n" + "permit\r\n" + "}\r\n"
				+ "			rule " + ruleDeny + "{\r\n" + "deny\r\n" + "}\r\n" + "		}\r\n" + "	}\r\n" + "}");

		fileWriter.close();
	}

	public static void writeJson(String dirPath) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Le Ngoc Tien Thanh");
		jsonObject.put("age", 12);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add("a");

		jsonObject.put("subject", jsonArray);
		try (FileWriter fileWriter = new FileWriter(dirPath)) {
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readJson(String dirPath) {
		JSONParser jsonParser = new JSONParser();
		try {
			Object object = jsonParser.parse(new FileReader(dirPath));

			JSONObject jsonObject = (JSONObject) object;
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("name");
			System.out.println(name);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
