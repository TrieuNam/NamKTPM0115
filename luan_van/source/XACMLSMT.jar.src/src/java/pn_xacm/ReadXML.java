package pn_xacm;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;

import experiments.AbstractExperiments;
import policy.SMTPolicy;
import policy.SMTPolicyElement.Decision;
import policy.SMTRule;
import query.QueryRunner;
import utils.PresentationUtils;

public class ReadXML extends AbstractExperiments {
	static long start = System.currentTimeMillis();
	static long start1;

	static long start2;
	static long start4;
	static long start5;

	public enum QueryMode {
		ShowAtPolicyOnly, ShowAtPolicyWithTargetCombination, ShowAtRule, ShowAtConstraint
	}

	private static Scanner scanner2;
	private static HashMap<String, String> mapAttributeNegValue2;

	public static void main(String[] args) throws Exception {
		// ====================Menu of evaluetion ============================
		boolean exit = false;
		do {
			int choice = menu();
			switch (choice) {
			case 1: // create policy
				File file = new File("requests\\textOpenFile.alfa");
				Desktop desktop = Desktop.getDesktop();
				if (file.exists())
					desktop.open(file);
			case 2: // Create HashMap create QueryAnalysis
				QueryAnalysis();
				break;
			case 3:// Evaluetion Policy

				testAnalysisPolicy(QueryAnalysis());

				break;
			case 4:

				result(QueryAnalysis());
				writeFileValue();

				break;
			case 5:
				exit = true;
				break;
			default:
				System.out.println("Xin hay Nhap lai");
				break;
			}
		} while (!exit);

		// ============================================================================================

		// testWithKMarket2Policy(queryAnaly);

	}

	public static ArrayList<String> QueryAnalysis() throws Exception {
		System.out.println(start);
		ArrayList<String> ListQueryAnalysis = new ArrayList<String>();
		File dir = new File("policies\\policy");
		ArrayList<HashMap<String, String>> listHashMap = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> listHashMapRequst = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> mapReadAttributeDomains = new HashMap<String, String>();
		HashMap<String, String> mapReadAttributeRequetsXML = new HashMap<String, String>();
		String queryAnaly = "";
		File[] children = dir.listFiles();

		for (File file : children) {
			System.out.println(file.getAbsolutePath());

			// =================Custom File Domain ==============================
			Attribute.readerRequest(file.getAbsolutePath());
			Attribute.readText();
			Attribute.creatListAttributeValue("fileAttributes2.txt");
			Attribute.createMapTotalValue();

			// ==================================================================
			long end2 = System.currentTimeMillis();
			start2 = (end2 - start);
			System.out.println("ket thuc domain " + start2 + " ms");
			// ================== Domain HashMap==========
			mapReadAttributeDomains = read_Request.readRequestTXT();
			listHashMap.add(mapReadAttributeDomains);
		}
		File dir2 = new File("requests\\request");
		File[] children2 = dir2.listFiles();

		for (File file : children2) {

			mapReadAttributeRequetsXML = read_Request.readerRequestXML(file.getAbsolutePath());
			listHashMapRequst.add(mapReadAttributeRequetsXML);
		}
		HashMap<String, String> mapAttributeNegValue = new LinkedHashMap<>();
		long end = System.currentTimeMillis();
		// ===========================================
		start1 = (end - start);
		System.out.println("ket thuc parse map " + start1+ " ms");
		// ===============
		// queryAnaly================================================================================================================
		for (int i = 0; i < listHashMap.size(); i++) {
			for (int j = 0; j < listHashMapRequst.size(); j++) {
				if (i == j) {
					queryAnaly = readAttributeDomains(listHashMap.get(i), listHashMapRequst.get(j),
							mapAttributeNegValue);
					ListQueryAnalysis.add(queryAnaly);
				}
			}
		}

		// =========================================================================================================================

		long end3 = System.currentTimeMillis();
		start4 = end3 - start;
		System.out.println("thoi gian xin Query :" + start4+ " ms");
		start5 = start1 + start2 + start4;
		System.out.println("Thoi gian " + start5+ " ms");

		return ListQueryAnalysis;

	}

	/***
	 * Menu of evaluetion for mongodb
	 * 
	 * @return
	 */

	public static int menu() {
		int selection;
		/***************************************************/
		System.out.println("Choose from these choices");
		System.out.println("-------------------------\n");
		System.out.println("1 - Create policy equals alfa");
		System.out.println("2 - Create HashMap create QueryAnalysis");
		System.out.println("3 - Evaluetion Policy");
		System.out.println("4 - file value for mongodb ");
		System.out.println("5 - exits ");
		Scanner input = new Scanner(System.in);
		selection = input.nextInt();
		return selection;
	}

	/***
	 * cutoms file txtValue remove last ","
	 * 
	 * @param file
	 * @param file
	 * 
	 * @return list2
	 * @throws FileNotFoundException
	 */
	private static List<String> removelast(File file) throws FileNotFoundException {

		List<String> list = new ArrayList<>();

		Scanner scanner = new Scanner(file);
		List<String> list2 = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String temp = scanner.nextLine().toString();
			list.add(temp);
		}
		for (String string : list) {

			if (string.endsWith(",")) {
				string = string.substring(0, string.length() - 1) + " ";
				list2.add(string);
			} else {
				list2.add(string);
			}

		}
		return list;
	}

	/***
	 * write txtValue lan 2
	 * 
	 * @throws FileNotFoundException
	 */
	private static void writeFileValue() throws FileNotFoundException {

		File dir = new File("XACMLSMT.jar.src\\dataRequest");
		File[] children = dir.listFiles();

		for (File file : children) {
			List<String> list = removelast(file.getAbsoluteFile());
			String fileName = file.getAbsolutePath();
			PrintWriter outputStream = new PrintWriter(fileName);
			for (String string : list) {
				outputStream.println(string);
			}
			outputStream.close();
		}
	}

	private static int[] anArray;

	private static void result(ArrayList<String> queryAnaly) throws FileNotFoundException {
		ArrayList<String> getvaule = testAnalysisPolicy(queryAnaly);
		int i = 0;
		for (String s : getvaule) {
			String fileName = "dataRequest\\txtvalue" + i + ".txt";
			PrintWriter outputStream = new PrintWriter(fileName);
			for (Entry<String, String> array : mapAttributeNegValue2.entrySet()) {
				outputStream.println(array.getKey() + "=" + array.getValue());
			}
			if (!s.equals("true")) {
				outputStream.println("result" + "=" + "IND");
			} else if (s.equals("false")) {
				outputStream.println("result" + "=" + s);
			} else {
				outputStream.println("result" + "=" + s);
			}
			Random random = new Random();
			scan2(outputStream, random);
			outputStream.close();
			i++;
		}

	}

	private static void scan2(PrintWriter outputStream, Random random) {
		scanner2 = new Scanner(System.in);
		System.out.println("Nhap so luong hoc sinh ");
		int intSo = scanner2.nextInt();
		Random rand = new Random();
		anArray = new int[intSo];
		for (int i = 0; i < intSo; i++) {
			anArray[i] = rand.nextInt(1500);
		}
		outputStream.print("idLec2" + "=" + random.nextInt(1500));
		outputStream.println();
		outputStream.println("idClasses2" + "=" + random.nextInt(1500));
		outputStream.print("LIST_Student2" + "=");
		for (int i : anArray) {
			outputStream.print(i + ",");
		}

	}
	// ShowAtPolicyWithTargetCombination domain 529
	// parse map 536
	// query :549
	// thoi gian 1737-1614 con traint= : 123 milili
	// 2463-123-1614=726
	// constraint domain 587
	// parse map 594
	// query ::608
	// thoi gian 1519-1789 ShowAtPolicyOnly= -270 milili
	// 2131--270-1789=612
	// ShowAtRule domain 572
	// parse map 580
	// query :::591
	// thoi gian 1860-1743 ShowAtRule=-: 117 milili
	// 2486-1743-117=626

	// ShowAtPolicyOnly domain 560

	// parse map 571
	// query ::::583
	// thoi gian 1528 -1714 ShowAtPolicyOnly= -186 milili
	// 2124-1714--186=596
//=============================== evaluetion policy ========================================================================
	public static ArrayList<String> testAnalysisPolicy(ArrayList<String> ListAnlayse) {
		ArrayList<String> ListAnlayse2 = new ArrayList<String>();
		File dirDefault = new File("policies\\policy");
		File[] children2 = dirDefault.listFiles();
		int i = 0;
		for (File file : children2) {
			for (int j = 0; j < ListAnlayse.size(); j++) {
				if (i == j) {
					QueryMode queryMode = QueryMode.ShowAtPolicyOnly;
					String policyPath = file.getAbsolutePath();

					ListAnlayse2.add(analyze(queryMode, policyPath, ListAnlayse.get(j), false));
				}
			}
			i++;
		}
		return ListAnlayse2;

	}

	private static String analyze(QueryMode queryMode, String policyPath, String analyzeQuery,
			boolean findApplicableModel) {
		ReadXML qt = new ReadXML();
		QueryRunner qr = qt.getQueryRunner();
		String value = null;
		QueryRunner.SHOW_AT_POLICY_ONLY = queryMode == QueryMode.ShowAtPolicyOnly;
		QueryRunner.SHOW_AT_POLICY_WITH_TARGET_COMBINATION = queryMode == QueryMode.ShowAtPolicyWithTargetCombination;
		QueryRunner.SHOW_AT_RULE = queryMode == QueryMode.ShowAtRule;

		SMTPolicy[] policyFormulas = qt.apply(qr, !(QueryRunner.SHOW_AT_POLICY_ONLY
				|| QueryRunner.SHOW_AT_POLICY_WITH_TARGET_COMBINATION || QueryRunner.SHOW_AT_RULE), policyPath);

		long end = System.currentTimeMillis();

		long t2 = (end - start);
		System.out.println(t2);
		long result2 = t2 - start5;
		System.out.println("thoi gian lv: " + result2+ " ms");

		if (analyzeQuery != null) {
			Expr queryExpression = qr.getQueryExpression(policyFormulas, analyzeQuery, System.out);// z3

			System.out
					.println("\n\n-------------------- Normal Simplification of Query Expression --------------------");
			System.out.println(PresentationUtils.normalUniform(queryExpression));
			value = PresentationUtils.normalUniform(queryExpression);
			System.out
					.println("\n\n-------------------- Solver Simplification of Query Expression --------------------");
			System.out.println(PresentationUtils.uniform(qr.getLoader().getContext(), queryExpression));

			if (findApplicableModel) {
				Map<String, Object> result = qr.findApplicableModel(queryExpression, System.out);
			}
			long end2 = System.currentTimeMillis();
			long t22 = (end2 - start);
			System.out.println(t22);
			long t23 = t22 - start5 - result2;
			System.out.println(result2);
			System.out.println(t23);

			// thoi gian nay la thoi doc z3 - start3 doc domain,map- t thoi gian leve

		}
		return value;

	}

	public SMTPolicy[] apply(QueryRunner qr, boolean showAtConstraint, String policyPath) {
		SMTPolicy[] policyFormulas = qr.loadPolicies(new String[] { policyPath }, true, QueryRunner.xacmlVersion,
				domainTest);

		System.out.println(
				"\n\n--------------------------------------- EXPERIMENTS -----------------------------------------");
		SMTRule smtRule = policyFormulas[0].getFinalElement();

		Context ctx = qr.getLoader().getContext();
		if (showAtConstraint) {
			System.out.println(Decision.Permit + " " + PresentationUtils.normalUniform(smtRule.getPermitDS()));
			System.out.println(Decision.Deny + " " + PresentationUtils.normalUniform(smtRule.getDenyDS()));
			System.out.println(Decision.Indet + " " + PresentationUtils.normalUniform(smtRule.getIndeterminateDS(ctx)));// ctx
																														// here
			System.out.println(Decision.Na + " " + PresentationUtils.normalUniform(smtRule.getNA_DS()));
		} else {
			System.out.println(
					Decision.Permit + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Permit, true)));
			System.out.println(
					Decision.Deny + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Deny, true)));
			System.out.println(
					Decision.Indet + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Indet, true)));
			System.out
					.println(Decision.Na + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Na, true)));

		}

		return policyFormulas;
	}

//===============================End evaluetion policy ========================================================================
	/**
	 * 
	 * @param mapReadAttributeRequetsXML read attribute requets xml
	 * @param mapReadAttributeDomains    read attribute domains in file txt
	 * @param mapAttributeNegValue       map attribute NegValue
	 */

//=============================== analytics Query  ========================================================================	
	public static String domainTest;

	private static String readAttributeDomains(HashMap<String, String> mapReadAttributeRequetsXML,
			HashMap<String, String> mapReadAttributeDomains, HashMap<String, String> mapAttributeNegValue)
			throws FileNotFoundException {
		HashMap<String, String> mapAttributegValue = new HashMap<>();
		for (Entry<String, String> entryAttributeDomains : mapReadAttributeDomains.entrySet()) {
			mapDomainNeg(mapReadAttributeRequetsXML, mapReadAttributeDomains, mapAttributeNegValue,
					entryAttributeDomains);
			mapValue(mapReadAttributeRequetsXML, mapReadAttributeDomains, mapAttributegValue, entryAttributeDomains);
		}
		// System.out.print("Nhap ");
		// scanner = new Scanner(System.in);
		String strQuery = "P_1";

		mapAttributeNegValue2 = mapAttributegValue;

		String stringQuery = space(mapReadAttributeRequetsXML, strQuery, mapAttributeNegValue, mapReadAttributeDomains);
		System.out.println(stringQuery);
		String stringQuery2 = space2(mapReadAttributeRequetsXML, mapAttributeNegValue, mapReadAttributeDomains);
		domainTest = stringQuery2.replace("\\WEDGE ()", "");
		System.out.println(domainTest);
		return stringQuery;

	}

	private static void mapValue(HashMap<String, String> mapReadAttributeRequetsXML,
			HashMap<String, String> mapReadAttributeDomains, HashMap<String, String> mapAttributegValue,
			Entry<String, String> entryAttributeDomains) {
		for (Entry<String, String> entryAttributeRequetsXML : mapReadAttributeRequetsXML.entrySet()) {
			if (entryAttributeDomains.getKey().equals(entryAttributeRequetsXML.getKey())) {
				mapAttributegValue.put(entryAttributeDomains.getKey(), entryAttributeRequetsXML.getValue());
			}
		}

	}

	private static void mapDomainNeg(HashMap<String, String> mapReadAttributeRequetsXML,
			HashMap<String, String> mapReadAttributeDomains, HashMap<String, String> mapAttributeNegValue,
			Entry<String, String> entryAttributeDomains) {
		for (Entry<String, String> entryAttributeRequetsXML : mapReadAttributeRequetsXML.entrySet()) {
			String[] arrayXml = entryAttributeDomains.getValue().trim().split(", ");
			if (arrayXml.length > 1) {
				for (String gValueAttributeDomains : arrayXml) {
					if (gValueAttributeDomains.trim().equals(entryAttributeRequetsXML.getValue())) {
						String gValueNeg = mapReadAttributeDomains
								.put(entryAttributeDomains.getKey(), entryAttributeDomains.getValue().trim())
								.replaceAll(entryAttributeRequetsXML.getValue() + ", ", "")
								.replaceAll(", " + entryAttributeRequetsXML.getValue().trim(), " ");
						mapAttributeNegValue.put(entryAttributeDomains.getKey().trim(), gValueNeg.trim());
					}
				}

			}

		}
	}

	/**
	 * 
	 * @param mapReadAttributeRequetsXML read attribute requets xml
	 * @param strQuery                   replaceOf(String currentExpr, String var,
	 *                                   String posValue, String... negValues,)
	 * @param mapAttributeNegValue       map attribute NegValue
	 * @return
	 */
	private static String space(HashMap<String, String> mapReadAttributeRequetsXML, String strQuery,
			HashMap<String, String> mapAttributeNegValue, HashMap<String, String> mapReadAttributeDomains) {
		for (Entry<String, String> entryAttributeRequetsXML : mapReadAttributeRequetsXML.entrySet()) {
			for (Entry<String, String> entryAttributeNegValue : mapAttributeNegValue.entrySet()) {
				if (entryAttributeRequetsXML.getKey().equals(entryAttributeNegValue.getKey())) {
					String[] arr = entryAttributeNegValue.getValue().trim().split("= ");
					strQuery = stringLastArry(strQuery, entryAttributeRequetsXML, arr);
				}

			}

			for (Entry<String, String> entrydomain : mapReadAttributeDomains.entrySet()) {

				if (entryAttributeRequetsXML.getKey().equals(entrydomain.getKey())) {
					strQuery = string(strQuery, entryAttributeRequetsXML.getKey(), entryAttributeRequetsXML.getValue());

				}
			}

		}
		return strQuery;
	}

	private static String string(String strQuery, String entryAttributeRequetsXMLKey,
			String entryAttributeRequetsXMLValue) {

		strQuery = replaceOf(strQuery, entryAttributeRequetsXMLKey, entryAttributeRequetsXMLValue);

		return strQuery;

	}

	private static String string2(String strQuery, String entryAttributeRequetsXMLKey,
			String entryAttributeRequetsXMLValue) {

		strQuery = replaceOfDomain(strQuery, entryAttributeRequetsXMLKey, entryAttributeRequetsXMLValue);

		return strQuery;

	}

	private static String stringLastArry(String strQuery, Entry<String, String> entryAttributeRequetsXML,
			String[] arr) {
		for (String attri : arr) {
			strQuery = replaceOf(strQuery, entryAttributeRequetsXML.getKey(),
					entryAttributeRequetsXML.getValue().trim(), attri.trim().split(", "));
		}
		return strQuery;
	}

	private static String space2(HashMap<String, String> mapReadAttributeRequetsXML,
			HashMap<String, String> mapAttributeNegValue, HashMap<String, String> mapReadAttributeDomains) {
		String strQuery = null;
		for (Entry<String, String> entryAttributeRequetsXML : mapReadAttributeRequetsXML.entrySet()) {

			for (Entry<String, String> entryAttributeNegValue : mapAttributeNegValue.entrySet()) {
				if (entryAttributeRequetsXML.getKey().equals(entryAttributeNegValue.getKey())) {
					String[] arr = entryAttributeNegValue.getValue().trim().split("= ");
					for (String attri : arr) {
						strQuery = replaceOfDomain(strQuery, entryAttributeRequetsXML.getKey(),
								entryAttributeRequetsXML.getValue().trim(), attri.trim().split(", "));
					}

				}
			}

			for (Entry<String, String> entrydomain : mapReadAttributeDomains.entrySet()) {

				if (entryAttributeRequetsXML.getKey().equals(entrydomain.getKey())) {
					strQuery = string2(strQuery, entryAttributeRequetsXML.getKey(),
							entryAttributeRequetsXML.getValue());

				}
			}
		}
		return strQuery;

	}

	private static String exprOf(String var, String value) {
		return var + " = " + value;
	}

	private static String andOf(String... exprs) {
		return StringUtils.join(exprs, " \\WEDGE ");
	}

	private static String orOf(String... exprs) {
		return StringUtils.join(exprs, " \\VEE ");
	}

	private static String notOf(String expr) {
		return "(\\NEG (" + expr + "))";
	}

	private static String applyOn(String newExpr, String currentExpr) {
		return "(" + newExpr + "(" + currentExpr + ")" + ")";
	}

	private static String applyOn2(String newExpr, String currentExpr) {
		return "(" + newExpr + " \\WEDGE " + "(" + andOf(currentExpr) + ")" + ")";
	}

	private static String[] multipleExprOf(String var, String[] negValues) {
		String[] ret = new String[negValues.length];
		for (int idx = 0; idx < negValues.length; idx++) {
			ret[idx] = exprOf(var, negValues[idx]);
		}
		return ret;
	}

	private static String replaceOfDomain(String currentExpr, String var, String posValue, String... negValues) {
		String posExpr = exprOf(var, posValue);

		String internalExpr;
		if (negValues != null && negValues.length > 0) {
			internalExpr = orOf(posExpr, orOf(multipleExprOf(var, negValues)));
		} else {
			internalExpr = exprOf(var, posValue);
		}
		return applyOn2("(" + internalExpr + ")", currentExpr);

	}

	private static String replaceOf(String currentExpr, String var, String posValue, String... negValues) {
		String posExpr = exprOf(var, posValue);
		String internalExpr;
		if (negValues != null && negValues.length > 0) {
			internalExpr = andOf(posExpr, notOf(andOf(multipleExprOf(var, negValues))));
		} else {
			internalExpr = exprOf(var, posValue);
		}
		return applyOn("\\REPLC " + "(" + internalExpr + ")", currentExpr);
	}
	// ===============================End analytics Query
	// ========================================================================

}
