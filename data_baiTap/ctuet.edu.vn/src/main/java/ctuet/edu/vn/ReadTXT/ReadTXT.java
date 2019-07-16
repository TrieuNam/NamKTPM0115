package ctuet.edu.vn.ReadTXT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Sheet;

public class ReadTXT {

	static int j = 0;
	static int j2 = 0;
	static int k = 0;
	static Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
	static XSSFWorkbook workbook = new XSSFWorkbook();
	static XSSFSheet sheet = workbook.createSheet("data Details");
	static int cu = 1;
	static FileOutputStream out;

	public static void main(String[] args) throws IOException {
		// File filename = new File("");

		// This data needs to be written (Object[])

//		data.put(0, new Object[] { "ID", "Article title ", "Author", "address", "year" });
//		File folder = new File("src\\main\\resources");
//		File[] listOfFiles = folder.listFiles();
//
//		for (File file : listOfFiles) {
//			if (file.isFile()) {
//				System.out.println(file.getAbsolutePath());
//
//				InputStream is = new FileInputStream(file.getAbsolutePath());
//				BufferedReader buf = new BufferedReader(new InputStreamReader(is));
//				String line = buf.readLine();
//				StringBuilder sb = new StringBuilder();
//				while (line != null) {
//					sb.append(line).append("\n");
//					line = buf.readLine();
//				}
//				// ArrayList<ArrayList<String>> listC1 = new ArrayList<ArrayList<String>>();
//				// ArrayList<ArrayList<String>> listTI = new ArrayList<ArrayList<String>>();
//				// ArrayList<ArrayList<String>> listpy = new ArrayList<ArrayList<String>>();
//				ArrayList<String> listAF = new ArrayList<String>();
//				ArrayList<String> listC1 = new ArrayList<String>();
//				ArrayList<String> listTI = new ArrayList<String>();
//				ArrayList<String> listpy = new ArrayList<String>();
//				// HashMap< String, ArrayList<String>>mapC1ATi = new HashMap<String,
//				// ArrayList<String>>();
//				String fileAsString = sb.toString();
//				int count = (fileAsString.split("\nC1 ", -1).length) - 1;
//				int count2 = (fileAsString.split("\nRP ", -1).length) - 1;
//				String q = "";
//				String w = "";
//				String e = "";
//				String r = "";
//				int demerro = 1;
//				for (int i = 1; i <= count; i++) {
//
//					int ti = fileAsString.indexOf("\nTI ");
//					int so = fileAsString.indexOf("\nSO ");
//					e = fileAsString.substring(ti, so + 1);
//					listTI.add(e);
//					fileAsString = fileAsString.substring(0, ti) + fileAsString.substring(so + 3);
//
//				}
//
//				ArrayList<String> listTI_1 = new ArrayList<String>();
//				for (String ti : listTI) {
//					String ti_1 = ti.substring(3);
//					listTI_1.add(ti_1);
//				}
//				ArrayList<String> listC1_1 = new ArrayList<String>();
//				for (int i = 1; i <= count; i++) {
//
//					int c1 = fileAsString.indexOf("\nC1 ");
//					int rp = fileAsString.indexOf("\nRP ");
//					w = fileAsString.substring(c1, rp + 1);
//					listC1.add(w);
//					fileAsString = fileAsString.substring(0, c1) + fileAsString.substring(rp + 3);
//				}
//				String conCat = " ";
//				for (String string : listC1) {
//					conCat = "";
//					String[] listArry_1 = string.split(".\n   ");
//					for (int i = 0; i < listArry_1.length; i++) {
//						int soC1 = listArry_1[i].lastIndexOf(", ");
//						String getL = listArry_1[i].substring(soC1 + 2);
//						conCat = conCat.concat(getL + " ");
//
//					}
//					listC1_1.add(conCat);
//
//				}
//
//				ArrayList<String> listpy_1 = new ArrayList<String>();
//				for (int i = 1; i <= count; i++) {
//
//					int py = fileAsString.indexOf("\nPY ");
//					int nam = fileAsString.indexOf("\nnamdeptrai");
//					r = fileAsString.substring(py, nam + 1);
//					listpy.add(r);
//					fileAsString = fileAsString.substring(0, py) + fileAsString.substring(nam + 3);
//				}
//				for (String py : listpy) {
//					String py_1 = py.substring(3);
//					listpy_1.add(py_1);
//				}
//				// ArrayList<String> listAF_1 = new ArrayList<String>();
////				for (int i = 1; i <= count; i++) {
////
////					int af = fileAsString.indexOf("\nAF ");
////					int ti2 = fileAsString.indexOf("\nanhthuok");
////					q = fileAsString.substring(af, ti2 );
////					listAF.add(q);
////					fileAsString = fileAsString.substring(0, af) + fileAsString.substring(ti2 + 3);
////				}
//
////				for (String py : listAF) {
////					String py_1 = py.substring(3);
////					listAF_1.add(py_1);
////				}
//				out = new FileOutputStream(new File("src\\main\\re\\gfgcontribute" + cu + ".xlsx"));
//
//				writeExecl(listTI_1, listC1_1, listpy_1);
//				cu++;
//			}
//
//		}
//		out.close();
		name();
	}

	private static void writeExecl(ArrayList<String> listTI, ArrayList<String> listC1, ArrayList<String> listpy) {

		for (j = 1; j < listTI.size(); j++) {
			for (j2 = 1; j2 < listC1.size(); j2++) {
				for (k = 1; k < listpy.size(); k++) {
					if (j == j2 & j == k) {
						data.put(j, new Object[] { j, listTI.get(j), listC1.get(j2), listpy.get(k) });

					}
				}

			}

		}
		// Iterate over data and write to sheet
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// this Writes the workbook gfgcontribute

			workbook.write(out);

			System.out.println("gfgcontribute" + cu + ".xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<String> listStringC1(int c1, int rp, String fileAsString) {
		String c = "";
		ArrayList<String> listC1 = new ArrayList<String>();
		c = fileAsString.substring(c1, rp);
		listC1.add(c);
		// fileAsString =fileAsString.replace(c+"\nRP ", " "); khong biet nao nhanh hon

		return listC1;

	}

	// 1984
	// 1985-1986-1987-1988-1989
	// 1990-1991-1992-1993-1994-v
	// 1995-1996-1997-1998-1999-v
	// 2000-2001-2002-2003-2004-v
	// 2005-2006-2007-2008-2009-
	// 2010-2011-2012-2013-2014-
	// 2015-2016-2017-2018-2019
	static int demTIAYear18_19_17_16_15 = 0;
	static int demTIAYear14_13_12_11_10 = 0;
	static int demTIAYear09_08_07_06_05 = 0;
	static int demTIAYear04_03_02_01_00 = 0;
	static int demTIAYear99_98_97_96_95 = 0;
	static int demTIAYear94_93_92_91_90 = 0;
	static int demTIAYear89_88_87_86_85 = 0;
	static int demTIAYear84 = 0;
	// ==========================================

	static int demAdyearOther19_18_17_16_15 = 0;
	static int demAdyearVNOne19_18_17_16_15 = 0;
	static int demAdyearVN19_18_17_16_15 = 0;
	// ==============================================
	static int demAdyearOther14_13_12_11_10 = 0;
	static int demAdyearVN14_13_12_11_10 = 0;
	static int demAdyearVNOne14_13_12_11_10 = 0;
	// ==============================================
	static int demAdyearOther09_08_07_06_05 = 0;
	static int demAdyearVN09_08_07_06_05 = 0;
	static int demAdyearVNOne09_08_07_06_05 = 0;
	// ============================================================
	static int demAdyearVNOne04_03_02_01_00 = 0;
	static int demAdyearOther04_03_02_01_00 = 0;
	static int demAdyearVN04_03_02_01_00 = 0;
	// ==========================================
	static int demAdyearVNOne99_98_97_96_95 = 0;
	static int demAdyearOther99_98_97_96_95 = 0;
	static int demAdyearVN99_98_97_96_95 = 0;
	// ==========================================
	static int demAdyearVNOne89_88_87_86_85 = 0;
	static int demAdyearOther89_88_87_86_85 = 0;
	static int demAdyearVN89_88_87_86_85 = 0;
	// ==========================================
	static int demAdyearVNOne84 = 0;
	static int demAdyearOther84 = 0;
	static int demAdyearVN84 = 0;
	// ==========================================
	static int demAdyearVNOne94_93_92_91_90 = 0;
	static int demAdyearOther94_93_92_91_90 = 0;
	static int demAdyearVN94_93_92_91_90 = 0;
	// ==========================================
	static HashMap<String, Integer> mapT = new HashMap<String, Integer>();

	private static void name() throws IOException {
		ArrayList<manager> listOfCustomer = new ArrayList<manager>();
		HashMap<String, ArrayList<Integer>> map = new HashMap<>();//
		File folder = new File("src\\main\\re");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					FileInputStream excelFile = new FileInputStream(new File(file.getAbsolutePath()));

					XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

					Sheet datatypeSheet = workbook.getSheetAt(0);
//			DataFormatter fmt = new DataFormatter();
					Iterator<Row> iterator = datatypeSheet.iterator();

					while (iterator.hasNext()) {

						Row currentRow = iterator.next();
						manager customer = new manager();
						try {

							customer.setResouce1((int) currentRow.getCell(0).getNumericCellValue());
							customer.setResouce2(currentRow.getCell(1).getStringCellValue());

							customer.setAcction3(currentRow.getCell(2).getStringCellValue());
							customer.setReSource4(currentRow.getCell(3).getStringCellValue());

						} catch (Exception e) {
							customer.setResouce1_1(currentRow.getCell(0).getStringCellValue());
							customer.setResouce2_1(currentRow.getCell(1).getStringCellValue());

							customer.setAcction3_1(currentRow.getCell(2).getStringCellValue());
							customer.setReSource4_1(currentRow.getCell(3).getStringCellValue());

						}
						listOfCustomer.add(customer);
					}

					//
					workbook.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Set<String> setYear = new HashSet<String>();
		HashMap<String, String> mapTiAYear = new HashMap<String, String>();
		HashMap<String, String> mapTADAyear = new HashMap<String, String>();
		int demTI = 0;
		int demC1 = 0;
		for (manager manager : listOfCustomer) {
			mapTiAYear.put(manager.getResouce2() + demTI, manager.getReSource4());
			mapTADAyear.put(manager.getAcction3() + " " + demC1, manager.getReSource4());
			demC1++;
			demTI++;
		}

		for (Entry<String, String> a : mapTiAYear.entrySet()) {
			try {
				String _18 = a.getValue();
				if (_18.equals(" 2019\n") || _18.equals(" 2018\n") || _18.equals(" 2017\n") || _18.equals(" 2016\n")
						|| _18.equals(" 2015\n")) {
					demTIAYear18_19_17_16_15++;
					mapT.put(" 2019- 2018- 2017- 2016- 2015 ", demTIAYear18_19_17_16_15);
				}
				// ============================
				if (_18.equals(" 2014\n") || _18.equals(" 2013\n") || _18.equals(" 2012\n") || _18.equals(" 2011\n")
						|| _18.equals(" 2010\n")) {
					demTIAYear14_13_12_11_10++;
					mapT.put(" 2014- 2013- 2012- 2011- 2010 ", demTIAYear14_13_12_11_10);
				}
				// ======================================
				if (_18.equals(" 2009\n") || _18.equals(" 2008\n") || _18.equals(" 2007\n") || _18.equals(" 2006\n")
						|| _18.equals(" 2005\n")) {
					demTIAYear09_08_07_06_05++;
					mapT.put(" 2009- 2008- 2007- 2006- 2005 ", demTIAYear09_08_07_06_05);
				}
				// =================================
				if (_18.equals(" 2004\n") || _18.equals(" 2003\n") || _18.equals(" 2002\n") || _18.equals(" 2001\n")
						|| _18.equals(" 2000\n")) {
					demTIAYear04_03_02_01_00++;
					mapT.put(" 2004- 2003- 2002- 2001- 2000 ", demTIAYear04_03_02_01_00);
				}
				// =======================================
				if (_18.equals(" 1999\n") || _18.equals(" 1998\n") || _18.equals(" 1997\n") || _18.equals(" 1996\n")
						|| _18.equals(" 1995\n")) {
					demTIAYear99_98_97_96_95++;
					mapT.put(" 1999- 1998- 1997- 1996- 1995 ", demTIAYear99_98_97_96_95);
				}
				// ===========================================
				if (_18.equals(" 1994\n") || _18.equals(" 1993\n") || _18.equals(" 1992\n") || _18.equals(" 1991\n")
						|| _18.equals(" 1990\n")) {
					demTIAYear94_93_92_91_90++;
					mapT.put(" 1994- 1993- 1992- 1991- 1990 ", demTIAYear94_93_92_91_90);
				}
				// ============================================================
				if (_18.equals(" 1989\n") || _18.equals(" 1988\n") || _18.equals(" 1987\n") || _18.equals(" 1986\n")
						|| _18.equals(" 1985\n")) {
					demTIAYear89_88_87_86_85++;
					mapT.put(" 1989- 1988- 1987- 1986- 1985 ", demTIAYear89_88_87_86_85);
				}
				// ============================================================
				if (_18.equals(" 1984\n")) {
					demTIAYear84++;
					mapT.put(" 1984 ", demTIAYear84);
				}

			} catch (Exception e) {
				System.out.println("eroo");
			}
		}
		System.out.println(demTIAYear18_19_17_16_15);
		System.out.println(demTIAYear14_13_12_11_10);
		System.out.println(demTIAYear09_08_07_06_05);
		System.out.println(demTIAYear04_03_02_01_00);
		System.out.println(demTIAYear99_98_97_96_95);
		System.out.println(demTIAYear94_93_92_91_90);
		System.out.println(demTIAYear89_88_87_86_85);
		System.out.println(demTIAYear84);
		System.out.println("---------------------------");
		int demI = 0;
		HashMap<String, Integer> resultVN = new HashMap<String, Integer>();
		HashMap<String, Integer> resultOTh = new HashMap<String, Integer>();
		HashMap<String, Integer> resultVNOne = new HashMap<String, Integer>();
		for (Entry<String, String> string : mapTADAyear.entrySet()) {
			String _18 = string.getValue();
			try {
				if (_18.equals(" 2019\n") || _18.equals(" 2018\n") || _18.equals(" 2017\n") || _18.equals(" 2016\n")
						|| _18.equals(" 2015\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne19_18_17_16_15++;
							resultVNOne.put(" 2019- 2018- 2017- 2016- 2015" + " VNONE", demAdyearVNOne19_18_17_16_15);

						} else {
							demAdyearVN19_18_17_16_15++;
							resultVN.put(" 2019- 2018- 2017- 2016- 2015" + " VN", demAdyearVN19_18_17_16_15);

						}

					} else {
						demAdyearOther19_18_17_16_15++;
						resultOTh.put(" 2019- 2018- 2017- 2016- 2015" + " OTh", demAdyearOther19_18_17_16_15);

					}

				}
				if (_18.equals(" 2014\n") || _18.equals(" 2013\n") || _18.equals(" 2012\n") || _18.equals(" 2011\n")
						|| _18.equals(" 2010\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne14_13_12_11_10++;
							resultVNOne.put(" 2014- 2013- 2012- 2011- 2010" + " VNONE", demAdyearVNOne14_13_12_11_10);

						} else {
							demAdyearVN14_13_12_11_10++;
							resultVN.put(" 2014- 2013- 2012- 2011- 2010" + " VN", demAdyearVN14_13_12_11_10);

						}

					} else {
						demAdyearOther14_13_12_11_10++;
						resultOTh.put(" 2014- 2013- 2012- 2011- 2010" + " OTh", demAdyearOther14_13_12_11_10);

					}
				}
				if (_18.equals(" 2009\n") || _18.equals(" 2008\n") || _18.equals(" 2007\n") || _18.equals(" 2006\n")
						|| _18.equals(" 2005\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne09_08_07_06_05++;
							resultVNOne.put(" 2009- 2008- 2007- 2006- 2005" + " VNONE", demAdyearVNOne09_08_07_06_05);

						} else {
							demAdyearVN09_08_07_06_05++;
							resultVN.put(" 2009- 2008- 2007- 2006- 2005" + " VN", demAdyearVN09_08_07_06_05);

						}

					} else {
						demAdyearOther09_08_07_06_05++;
						resultOTh.put(" 2009- 2008- 2007- 2006- 2005" + " OTh", demAdyearOther09_08_07_06_05);

					}
				}
				if (_18.equals(" 2004\n") || _18.equals(" 2003\n") || _18.equals(" 2002\n") || _18.equals(" 2001\n")
						|| _18.equals(" 2000\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne04_03_02_01_00++;
							resultVNOne.put(" 2004- 2003- 2002- 2001- 2000" + " VNONE", demAdyearVNOne04_03_02_01_00);

						} else {
							demAdyearVN04_03_02_01_00++;
							resultVN.put(" 2004- 2003- 2002- 2001- 2000" + " VN", demAdyearVN04_03_02_01_00);

						}

					} else {
						demAdyearOther04_03_02_01_00++;
						resultOTh.put(" 2004- 2003- 2002- 2001- 2000" + " OTh", demAdyearOther04_03_02_01_00);

					}
				}
				// =========================================================================================
				if (_18.equals(" 1999\n") || _18.equals(" 1998\n") || _18.equals(" 1997\n") || _18.equals(" 1996\n")
						|| _18.equals(" 1995\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne99_98_97_96_95++;
							resultVNOne.put(" 1999- 1998- 1997- 1996- 1995" + " VNONE", demAdyearVNOne99_98_97_96_95);

						} else {
							demAdyearVN99_98_97_96_95++;
							resultVN.put(" 1999- 1998- 1997- 1996- 1995" + " VN", demAdyearVN99_98_97_96_95);

						}

					} else {
						demAdyearOther99_98_97_96_95++;
						resultOTh.put(" 1999- 1998- 1997- 1996- 1995" + " OTh", demAdyearOther99_98_97_96_95);

					}
				}
				// =========================================================================================
				if (_18.equals(" 1994\n") || _18.equals(" 1993\n") || _18.equals(" 1992\n") || _18.equals(" 1991\n")
						|| _18.equals(" 1990\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne94_93_92_91_90++;
							resultVNOne.put(" 1994- 1993- 1992- 1991- 1990" + " VNONE", demAdyearVNOne94_93_92_91_90);

						} else {
							demAdyearVN94_93_92_91_90++;
							resultVN.put(" 1994- 1993- 1992- 1991- 1990" + " VN", demAdyearVN94_93_92_91_90);

						}

					} else {
						demAdyearOther94_93_92_91_90++;
						resultOTh.put(" 1994- 1993- 1992- 1991- 1990" + " OTh", demAdyearOther94_93_92_91_90);

					}
				}
				// ====================================================================
				if (_18.equals(" 1989\n") || _18.equals(" 1988\n") || _18.equals(" 1987\n") || _18.equals(" 1986\n")
						|| _18.equals(" 1985\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne89_88_87_86_85++;
							resultVNOne.put(" 1989- 1988- 1987- 1986- 1985" + " VNONE", demAdyearVNOne89_88_87_86_85);

						} else {
							demAdyearVN89_88_87_86_85++;
							resultVN.put(" 1989- 1988- 1987- 1986- 1985" + " VN", demAdyearVN89_88_87_86_85);

						}

					} else {
						demAdyearOther89_88_87_86_85++;
						resultOTh.put(" 1989- 1988- 1987- 1986- 1985" + " OTh", demAdyearOther89_88_87_86_85);

					}
				}
				// ===========================================
				if (_18.equals(" 1984\n")) {
					String key = string.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey.contains("Vietnam")) {
						String[] a = EndKey.split(" ");
						int demVN = 0;
						for (int i = 0; i < a.length; i++) {

							if (a[i].equals("Vietnam") || a[i].equals("Vietnam.\n")) {
								demVN++;
							}
						}

						if (demVN == a.length) {
							demAdyearVNOne84++;
							resultVNOne.put(" 1984" + " VNONE", demAdyearVNOne84);

						} else {
							demAdyearVN84++;
							resultVN.put(" 1984" + " VN", demAdyearVN84);

						}

					} else {
						demAdyearOther84++;
						resultOTh.put(" 1984" + " OTh", demAdyearOther84);

					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		HashMap<String, Integer> resultVN_1 = new HashMap<String, Integer>();
		// ========================================
		if (mapT.size() != resultVN.size()) {
			for (Entry<String, Integer> string2 : mapT.entrySet()) {
				for (Entry<String, Integer> string3 : resultVN.entrySet()) {
					String key4 = string2.getKey();
					int soEndKey4 = key4.lastIndexOf(" ");
					String EndKey4 = key4.substring(0, soEndKey4);
					// ====================================
					String key = string3.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey4.equals(EndKey)) {
						resultVN_1.put(EndKey + " VN", string3.getValue());
						break;
					} else {
						resultVN_1.put(EndKey4 + " VN", 0);

					}

				}
			}
		}
		HashMap<String, Integer> resultVNOne_1 = new HashMap<String, Integer>();
		// ========================================
		if (mapT.size() != resultVNOne.size()) {
			for (Entry<String, Integer> string2 : mapT.entrySet()) {
				for (Entry<String, Integer> string3 : resultVNOne.entrySet()) {
					String key4 = string2.getKey();
					int soEndKey4 = key4.lastIndexOf(" ");
					String EndKey4 = key4.substring(0, soEndKey4);
					// ====================================
					String key = string3.getKey();
					int soEndKey = key.lastIndexOf(" ");
					String EndKey = key.substring(0, soEndKey);
					if (EndKey4.equals(EndKey)) {
						resultVNOne_1.put(EndKey + " VNONE", string3.getValue());
						break;
					} else {
						resultVNOne_1.put(EndKey4 + " VNONE", 0);

					}

				}
			}
		}
		// ========================================

		WriteEXCel(setYear, resultVN_1, resultVNOne_1, resultOTh);
	}

	private static void WriteEXCel(Set<String> setYear, HashMap<String, Integer> resultVN,
			HashMap<String, Integer> resultVNOne, HashMap<String, Integer> resultOTh) throws IOException {
		FileOutputStream out2 = new FileOutputStream(new File("src\\main\\result2.xlsx"));
		Map<Integer, Object[]> data2 = new TreeMap<Integer, Object[]>();
		XSSFWorkbook workbook2 = new XSSFWorkbook();

		XSSFSheet sheet2 = workbook2.createSheet("data Details");
		data2.put(0, new Object[] { "Period", "Number of Papers", "VN AND foreign", "VN", "foreign" });
		// Iterate over data and write to sheet
		int i = 1;
		ArrayList<Integer> arras = new ArrayList<Integer>();
		ArrayList<Integer> vn = new ArrayList<Integer>();
		ArrayList<Integer> vnone = new ArrayList<Integer>();
		ArrayList<Integer> oth = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.00");
		for (Entry<String, Integer> row3 : mapT.entrySet()) {
			for (Entry<String, Integer> row4 : resultOTh.entrySet()) {
				for (Entry<String, Integer> row5 : resultVNOne.entrySet()) {
					for (Entry<String, Integer> row6 : resultVN.entrySet()) {
						String key4 = row3.getKey();
						int soEndKey4 = key4.lastIndexOf(" ");
						String EndKey4 = key4.substring(0, soEndKey4);
						// ================================================
						String key = row4.getKey();
						int soEndKey = key.lastIndexOf(" ");
						String EndKey = key.substring(0, soEndKey);
						// ========================================
						String key2 = row5.getKey();
						int soEndKey2 = key2.lastIndexOf(" ");
						String EndKey2 = key2.substring(0, soEndKey2);
						// ===================================
						String key3 = row6.getKey();
						int soEndKey3 = key3.lastIndexOf(" ");
						String EndKey3 = key3.substring(0, soEndKey3);
						if (EndKey4.equals(EndKey) && EndKey4.equals(EndKey2) && EndKey4.equals(EndKey3)) {
							float a = (float) row6.getValue() / row3.getValue();
							float b = (float) row5.getValue() / row3.getValue();
							float c = (float) row4.getValue() / row3.getValue();
							
							data2.put(i,
									new Object[] { EndKey4, row3.getValue(),
											row6.getValue() + "(" + df.format(a*100) + " %)",
											row5.getValue() + "(" + df.format(b*100) + " %)",
											row4.getValue() + "(" + df.format(c*100) + " %)" });
						}

					}
				}
			}

			// ==============================

			i++;

		}

		int number = 0;

		for (Integer integer : arras) {
			number = number + integer;
		}
		// data2.put(j, new Object[] { j, listTI.get(j), listC1.get(j2), listpy.get(k)
		// });
		Set<Integer> keyset = data2.keySet();
		int rownum = 0;
		for (Integer key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet2.createRow(rownum++);
			Object[] objArr = data2.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}

//			Row rowTotal = sheet2.createRow(rownum + 2);
//	        Cell cellTotalText = rowTotal.createCell(0);
//	        cellTotalText.setCellValue("Total:");
//	         khong bit xai
//	        Cell cellTotal = rowTotal.createCell(3);
//	        cellTotal.setCellFormula("SUM(C2:C9)");

		try {
			// this Writes the workbook gfgcontribute

			workbook2.write(out2);

			System.out.println("result2.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out2.close();
	}

}
//if (EndKey4.equals(" 2019") || EndKey4.equals(" 2018")) {
//	if (EndKey4.equals(EndKey) || EndKey4.equals(EndKey2) || EndKey4.equals(EndKey3)) {
//		if (arr.getValue() > row.getValue() || arr.getValue() > row2.getValue()
//				|| row.getValue() > row2.getValue()) {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}else {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}
//	}

//}
//if (EndKey4.equals(" 2017") || EndKey4.equals(" 2015")) {
//	if (EndKey4.equals(EndKey) || EndKey4.equals(EndKey2) || EndKey4.equals(EndKey3)) {
//		if (arr.getValue() > row.getValue() || arr.getValue() > row2.getValue()
//				|| row.getValue() > row2.getValue()) {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}else {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}
//	}
//}
//if (EndKey4.equals(" 2014")) {
//	if (EndKey4.equals(EndKey) || EndKey4.equals(EndKey2) || EndKey4.equals(EndKey3)) {
//		if (arr.getValue() > row.getValue() || arr.getValue() > row2.getValue()
//				|| row.getValue() > row2.getValue()) {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}else {
//			data2.put(i, new Object[] { EndKey4, row3.getValue(), arr.getValue(),
//					row.getValue(), row2.getValue() });
//		}
//	}
//}
//i++;

// ==============================
//	String key = arr.getKey();
//	int soEndKey = key.lastIndexOf("\n");
//	String EndKey = key.substring(0, soEndKey);
//	// ========================================
//	String key2 = row.getKey();
//	int soEndKey2 = key2.lastIndexOf("\n");
//	String EndKey2 = key2.substring(0, soEndKey);
//	// ===================================
//	String key3 = row2.getKey();
//	int soEndKey3 = key3.lastIndexOf("\n");
//	String EndKey3 = key3.substring(0, soEndKey);
