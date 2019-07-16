package experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class Evaluate {

	public static void main(String[] args) {
		String directory = "requests";
		String inputPath = Reader.getLastestFilefromDir(directory);
		long startTime = System.currentTimeMillis();
		evaluate(inputPath, '=');
		long endTime = System.currentTimeMillis();
		long evaluateTime = endTime - startTime;
		System.out.println("\n\t - Evaluate Time: " + evaluateTime + " ms");
	}

	/**
	 * + Evaluate Request extend
	 * 
	 * @param inputFilePath
	 * @param sCharacter
	 */
	@SuppressWarnings("unchecked")
	public static void evaluate(String inputFilePath, Character sCharacter) {
		try {
			/* Read Value From Request File */
			ArrayList<String> arrListReq = new ArrayList<String>();
			arrListReq = Reader.readTextFile(inputFilePath);

			/* Cut Value if Request contains special character */
			HashMap<String, String> mapReq = new LinkedHashMap<>();
			mapReq = Reader.cutValue(arrListReq, sCharacter);

			ArrayList<Integer> arrListStuReq = new ArrayList<Integer>();
			ArrayList<Integer> arrListLecReq = new ArrayList<Integer>();

			String strResultEvaluate = null;
			String strKeyLecTemp = null;
			Integer intClassIdReq = null;

			ArrayList<String> arrListValueReqToDb = new ArrayList<>();

			for (Map.Entry<String, String> entry : mapReq.entrySet()) {
				/* Get value from HashMap */
				String strKeyReq = entry.getKey();
				String strValueReq = entry.getValue();

				/* Check if resources contain '.' */
				if (strKeyReq.contains("resource") && strValueReq.contains(".")) {
					String[] strArrValueReq = strValueReq.split("\\.");
					if (strArrValueReq.length == 0) {
						throw new IllegalArgumentException(strValueReq + " - invalid format");
					} else {
						for (String string : strArrValueReq) {
							arrListValueReqToDb.add(string.trim());
						}
					}
				}

				if (strValueReq.contains(",")) {
					String[] arrStrValueReq = strValueReq.split("\\,");
					if (arrStrValueReq.length == 0) {
						throw new IllegalArgumentException(strValueReq + " - invalid format");
					} else {
						for (String string : arrStrValueReq) {
							string = string.trim();
							/* if keyReq equals keyValue */
							if (strKeyReq.equals(arrListValueReqToDb.get(5))) {
								strKeyLecTemp = strKeyReq;
								int idLec = Integer.parseInt(string);
								arrListLecReq.add(idLec);
							} else {
								int idStudent = Integer.parseInt(string);
								arrListStuReq.add(idStudent);
							}
						}
					}
				} else if (strKeyReq.equals("idLec")) {
					strKeyLecTemp = strKeyReq;
					int idLec = Integer.parseInt(strValueReq);
					arrListLecReq.add(idLec);
				} else if (strKeyReq.equals("LIST_Student")) {
					int idStudent = Integer.parseInt(strValueReq);
					arrListStuReq.add(idStudent);
				} else if (strKeyReq.equals("idClasses")) {
					intClassIdReq = Integer.parseInt(strValueReq);
				}

				if (strKeyReq.equals("result")) {
					strResultEvaluate = strValueReq.trim();
				}

			}

			/* Check status of result */
			if (strResultEvaluate.equals("permit") || strResultEvaluate.equals("PERMIT")) {

				/* Connect MongoDB to get data */
				ArrayList<Integer> arrListIdStuDB = new ArrayList<Integer>();
				arrListIdStuDB = GetDataMongoDB.getValueWithId(arrListValueReqToDb.get(0), arrListValueReqToDb.get(1),
						arrListValueReqToDb.get(2));

				ArrayList<Integer> arrListIdLecDB = new ArrayList<Integer>();
				arrListIdLecDB = GetDataMongoDB.getValueWithId(arrListValueReqToDb.get(3), arrListValueReqToDb.get(4),
						arrListValueReqToDb.get(5));

				ArrayList<Integer> arrListIdClassesDB = new ArrayList<Integer>();
				arrListIdClassesDB = GetDataMongoDB.getValueWithId(arrListValueReqToDb.get(6),
						arrListValueReqToDb.get(7), arrListValueReqToDb.get(8));

				/* Create ArrayList to save data */
				ArrayList<Document> arrayList = new ArrayList<Document>();
				ArrayList<Integer> arrListLecClassDB = new ArrayList<Integer>();
				ArrayList<Integer> arrListStuClassDB = new ArrayList<Integer>();

				if (arrListIdClassesDB.contains(intClassIdReq) == true) {
					/* Get value DB follow id Classes Request */
					arrayList = GetDataMongoDB.getValueWithId(arrListValueReqToDb.get(6), arrListValueReqToDb.get(7),
							arrListValueReqToDb.get(8), intClassIdReq);
//					System.out.println("\n==================== Class Information ====================");
//					System.out.println("\n\t Id Class: " + intClassIdReq);
					for (Document document : arrayList) {
						String strClassName = document.getString("className");
//						System.out.println("\n\t Class Name: " + strClassName);

						List<Document> listLec = (List<Document>) document.get("Lecturer_data_layer");
						for (Document lecturer : listLec) {
							arrListLecClassDB.add(lecturer.getInteger(arrListValueReqToDb.get(5)));
//							System.out.println(
//									"\n\t Id Lecturer Class: " + lecturer.getInteger(arrListValueReqToDb.get(5)));
						}
						List<Document> listStu = (List<Document>) document.get("LIST_Student");
//						System.out.println("\n\t ---- List Students ----");
						for (Document student : listStu) {
//							System.out.println(
//									"\n\t Id Student Class: " + student.getInteger(arrListValueReqToDb.get(2)));
							arrListStuClassDB.add(student.getInteger(arrListValueReqToDb.get(2)));
						}
					}
					System.out.println("\n     ============== Privacy Mechanism Access Control ==============\n");
					if (strKeyLecTemp.equals(arrListValueReqToDb.get(5))) {
						for (Integer idLecReq : arrListLecReq) {
							/* Check idLecturerRequest is in ListIdLecturerDB or not ? */
							if (arrListIdLecDB.contains(idLecReq) == true) {
								System.out.println("\n\t Id Lecturer Request: " + idLecReq + " EXIST ");
								if (arrListLecClassDB.contains(idLecReq)) {
									System.out.println("\n\t Teacher: " + idLecReq + " is teaching this class ");
									System.out.println(
											"\n\t Teacher: " + idLecReq + " can see all information of this student ");
									System.out.println("\t ------------------------------------------------------");
									for (Integer idStuReq : arrListStuReq) {
										/* Check idStudentRequest is in Database or not */
										if (arrListIdStuDB.contains(idStuReq) == true) {
											if (arrListStuClassDB.contains(idStuReq)) {
												System.out.println(
														"\n\t Student: " + idStuReq + " is studying this class ");
												// Show all info student
												GetDataMongoDB.showInfoStudentIfHaveId(arrListValueReqToDb.get(0),
														arrListValueReqToDb.get(1), arrListValueReqToDb.get(2),
														idStuReq);
												System.out.println("\t ---------------------------------------------");
											} else {
												System.out.println(
														"\n\t Student: " + idStuReq + " don't study this class !!!");
												// Cover info student
												GetDataMongoDB.coverInfoStudentIfHaveId(arrListValueReqToDb.get(0),
														arrListValueReqToDb.get(1), arrListValueReqToDb.get(2),
														idStuReq);
												System.out.println("\t ---------------------------------------------");
											}
										} else {
											System.out.println(
													"\n\t Id Student Request: " + idStuReq + " is NOT EXIST !!!");
											System.out.println("\t ---------------------------------------------");
										}
									}
								} else {
									System.out.println("\n\t Teacher: " + idLecReq + " don't teach this class !!!");
									System.out.println(
											"\n\t Teacher: " + idLecReq + " can see a part information of Student");
									System.out.println("\t ------------------------------------------------------");
									for (Integer idStuReq : arrListStuReq) {
										/* Check idStudentRequest is in Database or not */
										if (arrListIdStuDB.contains(idStuReq) == true) {
											if (arrListStuClassDB.contains(idStuReq)) {
												System.out.println(
														"\n\t Student: " + idStuReq + " is studying this class ");
												// Cover info student
												GetDataMongoDB.coverInfoStudentIfHaveId(arrListValueReqToDb.get(0),
														arrListValueReqToDb.get(1), arrListValueReqToDb.get(2),
														idStuReq);
											} else {
												System.out.println(
														"\n\t Student: " + idStuReq + " don't study this class !!!");
												// Cover info student
												GetDataMongoDB.coverInfoStudentIfHaveId(arrListValueReqToDb.get(0),
														arrListValueReqToDb.get(1), arrListValueReqToDb.get(2),
														idStuReq);
												System.out.println("\t ---------------------------------------------");
											}
										} else {
											System.out.println(
													"\n\t Id Student Request: " + idStuReq + " is NOT EXIST !!!");
											System.out.println("\t ---------------------------------------------");
										}
									}
								}
							} else {
								System.out.println("\n\t Id Lecturer Request: " + idLecReq + " NOT EXIST !!!");
								System.out.println("\n\t\t\t ACCESS DENY !!!\n");
								System.out.println("\t ---------------------------------------------");
							}
						}
					}
				} else {
					System.out.println("\n==================== Class does NOT EXIST ====================\n");
					System.out.println("\n\t\t\t ACCESS DENY !!!\n");
				}
			} else {
				System.out.println("\t -----------------------------------------------");
				System.out.println("\n\t\t\t ACCESS DENY !!!\n");
				System.out.println("\n\t\t Because Result is " + strResultEvaluate + " !!!\n");
				System.out.println("\t -----------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}