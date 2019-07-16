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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

public class genPolicy {

	public static void main(String args[])
			throws IOException, ParserConfigurationException, SAXException, TransformerException {
		long start2 = System.currentTimeMillis();
		genPolicy1("teacherSee1");
		long end = System.currentTimeMillis();
		long t2 = end - start2;
		System.out.println("Read xml and write :" + t2);
		System.out.println("tb read xml and write " + t2 / 1000);
	}

	public static HashMap<String, ArrayList<Integer>> readExcel() {
		long start = System.currentTimeMillis();
		ArrayList<manager> listOfCustomer = new ArrayList<manager>();
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();
		try {
			FileInputStream excelFile = new FileInputStream(new File("policy.xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

			Sheet datatypeSheet = workbook.getSheetAt(0);
//			DataFormatter fmt = new DataFormatter();
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
			long end = System.currentTimeMillis();
			long t2 = end - start;
			System.out.println("Read excel :" + t2);

			ArrayList<Integer> list = new ArrayList<>();

			long start2 = System.currentTimeMillis();
			for (manager manager : listOfCustomer) {

				list.add(manager.getResouce2());
				list.add(manager.getAcction3());
				list.add(manager.getReSource4());
				map.put(manager.getResouce1(), list);
			}
			long end2 = System.currentTimeMillis();
			long t22 = end2 - start2;
			System.out.println("add hash map :" + t22);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;

	}

	public static void genPolicy1(String filename)
			throws IOException, ParserConfigurationException, SAXException, TransformerException {

		/* Check FileName has .xml ? */
		if (filename.contains(".xml")) {

		} else {
			filename = filename + ".xml";
		}

		HashMap<String, ArrayList<Integer>> mapRequest = readExcel();
		Scanner scanner2 = new Scanner(System.in);
		System.out.println("How many files do you want ?");
		System.out.print("Answer: ");
		int n = scanner2.nextInt();

		for (int i = 0; i < n; i++) {
			File textF = new File("src-gen/" + filename);
			Scanner scanner = new Scanner(textF);
			String name = "policies\\policy\\" + filename + "_" + i;
			File file = new File(name);

			PrintWriter outputStream = new PrintWriter(file);

			while (scanner.hasNextLine()) {

				String values = scanner.nextLine().trim();

				outputStream.println(values);
			}
			outputStream.close();
			updataPolicy.readerRequestXML(name, mapRequest);

		}

	}

}
