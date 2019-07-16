package experiments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Reader {


	public static void main(String[] args) throws Exception {
		ArrayList<String> listValue = new ArrayList<>();
		listValue = readTextFile("requests/requestValue.txt");
		System.out.println(cutValue(listValue, '='));

//		String mapDomain = Reader.domain("Policy.txt").toString();
//		System.out.println(mapDomain);
//		String mapRequest = Reader.request("requests/request.permit.xml").toString();
//		System.out.println(mapRequest);
//		String input = "requests";
//		String file = getLastestFilefromDir(input);
//		System.out.println(file.toString());
	}

	/***
	 * 
	 * @param inputFilePath
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> readTextFile(String inputFilePath) throws IOException {
		ArrayList<String> listValue = new ArrayList<>();
		FileInputStream fileInputStream = new FileInputStream(new File(inputFilePath));
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

		String line;
		/* Check empty line */
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			if (line != null && !line.isEmpty()) {
				listValue.add(line);
			}
		}
		return listValue;
	}

	/**
	 * Cut value with special character
	 * 
	 * @param textFileLocation
	 * @return
	 * @throws IOException
	 */
	public static HashMap<String, String> cutValue(ArrayList<String> listTextValue, Character specialCharacter)
			throws IOException {
		HashMap<String, String> mapKeyValue = new LinkedHashMap<>();
		for (String string : listTextValue) {
			String character = specialCharacter.toString();
			String[] arrValueTxt = string.split(character);
			mapKeyValue.put(arrValueTxt[0].trim(), arrValueTxt[1].trim());
		}
		return mapKeyValue;
	}

	/**
	 * @param textFileLocation
	 * @return
	 * @throws FileNotFoundException
	 */
	public static HashMap<String, String> domain(String textFileLocation) throws FileNotFoundException {
		File textFile = new File(textFileLocation);

		Scanner scanner = new Scanner(textFile);

		HashMap<String, String> mapDomain = new LinkedHashMap<>();

		while (scanner.hasNextLine()) {

			/* temp: save all value from text file */
			String temp = scanner.nextLine().toString();

			/* Cut value follow character */
			String[] arrayDomain = temp.split("=>");

			mapDomain.put(arrayDomain[0].trim(), arrayDomain[1].trim());

		}
		scanner.close();
		return mapDomain;
	}

	/**
	 * @param xmlFilePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static HashMap<String, String> request(String xmlFilePath)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFilePath);

		NodeList rootNodeList = document.getElementsByTagName("Attributes");

		HashMap<String, String> mapRequest = new LinkedHashMap<>();

		for (int i = 0; i < rootNodeList.getLength(); i++) {
			Node rootNode = rootNodeList.item(i);
			Element rootElement = (Element) rootNode;
			if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
				NodeList childNodeList = rootElement.getChildNodes();
				for (int j = 0; j < childNodeList.getLength(); j++) {
					Node childNode = childNodeList.item(j);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						String idChildElement = childElement.getAttribute("AttributeId");
						String valueChildElement = childNode.getTextContent().trim();
						/* Add to HashMap */
						mapRequest.put(idChildElement, valueChildElement);
					}
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 
	 * @param dirPath
	 * @return
	 */
	public static String getLastestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile.toString().trim();
	}

}
