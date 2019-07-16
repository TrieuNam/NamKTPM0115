package pn_xacm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Attribute {
//	private static final String fileAttributes2 = "fileAttributes2.txt";
//	private static final String txtFile = "fileAttributes.txt";
//	private static final String fXmlFile = "";

	public static HashMap<String, String> readerRequest(String string)
			throws ParserConfigurationException, SAXException, IOException {		
//============================ XACML version 1 and 3 ==========================================
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(string);
			NodeList rootNodeList6 = doc.getElementsByTagName("Match");
			NodeList rootNodeList = doc.getElementsByTagName("xacml3:Match");
			NodeList rootNodeList2 = doc.getElementsByTagName("Apply");
			NodeList rootNodeList3 = doc.getElementsByTagName("SubjectMatch");
			NodeList rootNodeList4 = doc.getElementsByTagName("ResourceMatch");
			NodeList rootNodeList5 = doc.getElementsByTagName("ActionMatch");
			HashMap<String, String> mapRequest = new LinkedHashMap<>();
			String fileName = "fileAttributes.txt";
			PrintWriter outputStream = new PrintWriter(fileName);
			for (int i = 0; i < rootNodeList.getLength(); i++) {
				Node rootNode = rootNodeList.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId").trim();
							String valueChildElement = childNode.getTextContent().trim();
							outputStream.println(idChildElement + "\t" + valueChildElement.trim());
						}

					}

				}

			}
			for (int i = 0; i < rootNodeList6.getLength(); i++) {
				Node rootNode = rootNodeList6.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId").trim();
							String valueChildElement = childNode.getTextContent().trim();
							outputStream.println(idChildElement + "\t" + valueChildElement.trim());
						}

					}

				}

			}
			for (int i = 0; i < rootNodeList4.getLength(); i++) {
				Node rootNode = rootNodeList4.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId").trim();
							String valueChildElement = childNode.getTextContent().trim();
							outputStream.println(idChildElement + "\t" + valueChildElement.trim());
						}

					}

				}

			}
			for (int i = 0; i < rootNodeList3.getLength(); i++) {
				Node rootNode = rootNodeList3.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId").trim();
							String valueChildElement = childNode.getTextContent().trim();
							outputStream.println(idChildElement + "\t" + valueChildElement.trim());
						}

					}

				}

			}
			for (int i = 0; i < rootNodeList5.getLength(); i++) {
				Node rootNode = rootNodeList5.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId").trim();
							String valueChildElement = childNode.getTextContent().trim();
							outputStream.println(idChildElement + "\t" + valueChildElement.trim());
						}

					}

				}

			}
			for (int i = 0; i < rootNodeList2.getLength(); i++) {
				Node rootNode = rootNodeList2.item(i);
				Element rootElement = (Element) rootNode;
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = rootElement.getChildNodes();
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							Element childElement = (Element) childNode;
							String idChildElement = childElement.getAttribute("AttributeId");
							String valueChildElement = childNode.getTextContent();
							if (idChildElement != "") {
								String a = idChildElement.trim();
								outputStream.print(a);
							}
							if (valueChildElement != "") {
								outputStream.print(valueChildElement.trim() + "\n");
							}
						}

					}

				}

			}
			outputStream.close();
			return mapRequest;
		}

	//}
	//============================End XACML version 1 and 3 ==========================================
	/**
	 * 
	 * @throws Exception
	 */
//============================== parser file policy  ===========================================================	
	public static void readText() throws Exception {
		// read file attribute domains
		File textF = new File("fileAttributes.txt");
		Scanner scanner = new Scanner(textF);
		String fileName = "fileAttributes2.txt";
		PrintWriter outputStream = new PrintWriter(fileName);
		
		while (scanner.hasNextLine()) {

			String values = scanner.nextLine().toString().trim();

			if (values.equals("")) {
				continue;
			}
			try {
			String[] string = values.split(":");
			for (String s : string) {
	
				outputStream.println(string[0].trim() + "=>" + string[1].trim());
				break;
			
			}
			}catch (Exception e) {
				String keys = scanner.nextLine().trim();
				outputStream.print( keys + "=>"+ values + "\n");
			}
		

		}
		outputStream.close();
	}

	public static List<String[]> creatListAttributeValue(String textFile) throws IOException {
		File textF = new File(textFile);
		List<String[]> listarry = new ArrayList<String[]>();
		Scanner scanner = new Scanner(textF);
		while (scanner.hasNextLine()) {
			String temp = scanner.nextLine().toString();
			listarry.add(temp.split("=>"));
		}
		return listarry;
	}

	public static void createMapTotalValue() throws IOException {
		List<String[]> list = creatListAttributeValue("fileAttributes2.txt");
		HashMap<String, Set> mapNewDomain = new LinkedHashMap<String, Set>();
		String fileName = "fileDomain.txt";
		PrintWriter outputStream = new PrintWriter(fileName);
		for (int i = 0; i < list.size() - 1; i++) {
			mapNewDomain.put(list.get(i)[0].trim(), setNew(list, mapNewDomain, i));
		}
		for (Entry<String, Set> strings : mapNewDomain.entrySet()) {
			outputStream.println(strings.getKey().trim() + "=>" + strings.getValue());
		}
		outputStream.close();
	}

	/*
	 * 
	 */
	private static Set<String> setNew(List<String[]> list, HashMap<String, Set> mapNew1, int i) {
		String[] tempI;
		String[] tempJ;
		Set<String> setNewValue = new HashSet<String>();
		for (int j = 0; j < list.size(); j++) {
			tempI = list.get(i);
			tempJ = list.get(j);
			if (!tempI[0].equals(tempJ[0])) {
				String temp = tempI[1];
				setNewValue.add(temp.trim());
			} else {
				String temp = tempJ[1];
				setNewValue.add(temp.trim());
			}
		}
		return setNewValue;
	}

}
//==============================End parser file policy  ===========================================================	




// BufferedReader reader = new BufferedReader( read Line code file txt
// new FileReader("E:/Source/source/XACMLSMT.jar.src/fileAttributes2.txt"));
// int lines = 0;
/// while (reader.readLine() != null)
// lines++;
// reader.close();

//for (Entry<String, Set> string : mapNew1.entrySet()) {
// if (string.getKey().equals(list.get(i)[0])) {// code createMapTotalValue
// continue;
// } else {
// mapNew1.put(list.get(i)[0], setValue);
// }
// }
// for (j = i + 1; j < list.size(); j++) {
// if (!list.get(i)[0].equals(list.get(j)[0])) {
// String temp = tempI[1];
// setNew.add(temp);
// mapNew1.put(list.get(i)[0],string.setValue(setValue));
// } else {
// String temp = tempJ[1];
// setNew.add(temp);
// for (Entry<String, Set> string : mapNew1.entrySet()) {
// if (string.getKey().equals(list.get(i)[0])) {

// setValue(list, setValue, i);
// mapNew1.put(list.get(i)[0],string.setValue(setValue));
// }
// }
// }
/*
 * private static void Attribute() throws ParserConfigurationException,
 * SAXException, IOException { DocumentBuilderFactory dbFactory =
 * DocumentBuilderFactory.newInstance(); DocumentBuilder dBuilder =
 * dbFactory.newDocumentBuilder(); Document doc =
 * dBuilder.parse("policies\\KMarket2.xml"); NodeList rootNodeList =
 * doc.getElementsByTagName("Apply"); HashMap<String, String> mapRequest = new
 * LinkedHashMap<>(); String fileName = "fileAttributes.txtt"; PrintWriter
 * outputStream = new PrintWriter(fileName); for (int i = 0; i <
 * rootNodeList.getLength(); i++) { Node rootNode = rootNodeList.item(i);
 * Element rootElement = (Element) rootNode; if (rootNode.getNodeType() ==
 * Node.ELEMENT_NODE) { NodeList childNodeList = rootElement.getChildNodes();
 * for (int j = 0; j < childNodeList.getLength(); j++) { Node childNode =
 * childNodeList.item(j); if (childNode.getNodeType() == Node.ELEMENT_NODE) {
 * Element childElement = (Element) childNode; String idChildElement =
 * childElement.getAttribute("AttributeId"); String valueChildElement =
 * childNode.getTextContent(); String a =idChildElement.trim() + "\t" +
 * valueChildElement.trim().trim() + "\n"; outputStream.print(a);
 * try {
//			//json
//			File textF = new File("policies\\KMarket2.xml");
//			Scanner scanner = new Scanner(textF);
//			HashMap<String, String> value = new LinkedHashMap<>();
//			String fileName = "fileAttributes.txt";
//			PrintWriter outputStream = new PrintWriter(fileName);
//			while (scanner.hasNextLine()) {
//				String temp = scanner.nextLine().toString();
//				if (temp.contains("{") || temp.contains("}") || temp.contains("[") || temp.contains("]")) {
//					continue;
//				}
//				String[] string = temp.split(":");
//				for (String s : string) {
//
//					value.put(string[0].trim(), string[1].trim().replace(",", ""));
//					
//					outputStream.println(string[0].trim() + "\t:" + string[1].trim().replace(",", ""));
//					break;
//				}
//			}
//			outputStream.close();
//			
//			return value;
//		} catch (Exception e) {
 * }
 * 
 * }
 * 
 * }
 * 
 * }
 * 
 * }
 */