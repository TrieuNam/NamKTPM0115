package pn_xacm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class read_Request {
//===================================== hash map domain ===================================================
	public static HashMap<String, String> readRequestTXT() throws FileNotFoundException {

		File textF = new File("fileDomain.txt");
		Scanner scanner = new Scanner(textF);
		HashMap<String, String> value = new LinkedHashMap<>();
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine().toString();
				String[] arrStringAtributeDomains = temp.split("=>");// split string temp
				String getValueArry = arrStringAtributeDomains[1];
				String deletelast = getValueArry.substring(0, getValueArry.length() - 1);
				String deleteFirstly = deletelast.substring(1, deletelast.length());
				value.put(arrStringAtributeDomains[0].trim(), deleteFirstly.trim());
			}
			return value;	
	}
	//=====================================End hash map domain ===================================================
	
	//===================================== hash map request ===================================================
	public static HashMap<String, String> readerRequestXML(String string2)
			throws ParserConfigurationException, SAXException, IOException {
		// read xml requets
		try {
			//json
			File textF = new File(string2);
			Scanner scanner = new Scanner(textF);
			HashMap<String, String> value = new LinkedHashMap<>();
		
			while (scanner.hasNextLine()) {
				String temp = scanner.nextLine().toString();
				if (temp.contains("{") || temp.contains("}") || temp.contains("[") || temp.contains("]")) {
					continue;
				}
				String[] string = temp.split(":");
				for (String s : string) {

					value.put(string[0].trim(), string[1].trim().replace(",", ""));
					
				
					break;
				}
			}
			
			return value;
			
			
		} catch (Exception e) {
			// xml
		
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse("requests\\request_teacher.xml");
		NodeList rootNodeList = doc.getElementsByTagName("Attributes");
		NodeList rootNodeList2 = doc.getElementsByTagName("Subject");
		NodeList rootNodeList3 = doc.getElementsByTagName("Resource");
		NodeList rootNodeList4 = doc.getElementsByTagName("Action");
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
						mapRequest.put(idChildElement.trim(), valueChildElement.trim());
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
						String idChildElement = childElement.getAttribute("AttributeId");
						String valueChildElement = childNode.getTextContent().trim();
						mapRequest.put(idChildElement.trim(), valueChildElement.trim());
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
						String valueChildElement = childNode.getTextContent().trim();
						mapRequest.put(idChildElement.trim(), valueChildElement.trim());
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
						String idChildElement = childElement.getAttribute("AttributeId");
						String valueChildElement = childNode.getTextContent().trim();
						mapRequest.put(idChildElement.trim(), valueChildElement.trim());
					}
				}

			}

		}
		return mapRequest;
	}
	
	}
	
	//=====================================End hash map request ===================================================	
	
	
	
	/*
	 * private static List<String> createSetNew(Node rootNode, Element rootElement)
	 * { List<String> setNew = new ArrayList<String>(); if (rootNode.getNodeType()
	 * == Node.ELEMENT_NODE) { NodeList childNodeList = rootElement.getChildNodes();
	 * for (int j = 0; j < childNodeList.getLength(); j++) { Node childNode =
	 * childNodeList.item(j);
	 * 
	 * if (childNode.getNodeType() == Node.ELEMENT_NODE) { Element childElement =
	 * (Element) childNode; String idChildElement =
	 * childElement.getAttribute("AttributeId"); setNew.add(valueChildElement); } }
	 * 
	 * } return setNew; }
	 */

}
