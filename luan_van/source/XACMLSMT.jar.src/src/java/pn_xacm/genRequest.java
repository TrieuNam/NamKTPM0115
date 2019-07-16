package pn_xacm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

public class genRequest {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		long start2 = System.currentTimeMillis();
		//genRequest1();
		long end = System.currentTimeMillis();
		long t2 = end - start2;
		System.out.println("Read xml and write :" + t2);
		System.out.println("tb read xml and write "+t2/1000);
	}
	 public static HashMap<String, ArrayList<Integer>>  name() {
		 long start2 = System.currentTimeMillis();
    	 ArrayList<manager> listOfCustomer = new ArrayList<manager>();
    	 HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    	try {
	    	 FileInputStream excelFile = new FileInputStream(new File("policy.xlsx"));
	       
	    	 XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	      
	    	 Sheet datatypeSheet = workbook.getSheetAt(0);
	    	 DataFormatter fmt = new DataFormatter();
	         Iterator<Row> iterator = datatypeSheet.iterator();
	        
	         while (iterator.hasNext()) {
	           Row currentRow = iterator.next();
	           manager customer = new manager();
	           customer.setResouce1(currentRow.getCell(0).getStringCellValue());
	           customer.setResouce2((int) currentRow.getCell(1).getNumericCellValue());

	           customer.setAcction3((int) currentRow.getCell(2).getNumericCellValue());
	          customer.setReSource4((int) currentRow.getCell(3).getNumericCellValue());
	           listOfCustomer.add(customer);
	         }
	         //
	         workbook.close();
	     
	      
	      ArrayList<Integer> list = new ArrayList<>();
	    
	      
	      for (manager manager : listOfCustomer) {
			
	    	  list.add(manager.getResouce2());
	    	  list.add(manager.getAcction3());
	    	  list.add(manager.getReSource4());
	    	  map.put(manager.getResouce1(), list);
		}
	    
	      	long end = System.currentTimeMillis();
			long t2 = end - start2;
			System.out.println("Read xml and write :" + t2);
	       } catch (FileNotFoundException e) {
	         e.printStackTrace();
	       } catch (IOException e) {
	         e.printStackTrace();
	       }
		return map;
    	
	     
	}
	 
	 public static void genRequest1(String filename_03_2) throws IOException, ParserConfigurationException, SAXException, TransformerException {
	    	System.out.print("Nhap  extend :");
			
	    	Scanner scanner2 = new Scanner(System.in);
	    	  HashMap<String, ArrayList<Integer>> mapRequest = name();
			 int n = scanner2.nextInt();
			for (int i = 0; i < n ; i++) {
				File textF = new File("requests\\"+filename_03_2+".xml");
				 Scanner scanner = new Scanner(textF);
				 String name = "requests\\request\\"+filename_03_2+"_"+i+".xml";
				File file = new File(name);
		         
		         
		         PrintWriter outputStream = new PrintWriter(file);
		      
		         while (scanner.hasNextLine()) {
		         
		        	 String values = scanner.nextLine().trim();
		        	 
		        	 outputStream.println(values);
		         }
		         
		        
		         outputStream.close();
		       
		         updataRequest.readerRequestXML(name,mapRequest);
			}
	    	 
}
}
