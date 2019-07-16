package experiments;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoServerException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GetDataMongoDB {

	public static void main(String[] args) throws MongoServerException, UnknownHostException {
		/* Get data value from MOCK_DATA Collection */
//		enableHideFunction();

		/* Get data value from SubjectDetail Collection */
//		getValueCollectionJoin();

		/* Function Interactive MongoDB */
//		Ping();
//		InsertData();
//		FindAllObjectInCollection();

//		String[] list_student = { "1143", "1", "559", "2", "409", "3", "733", "189" };
//		coverData1("AccessControl", "Student_data_layer", "idStu", list_student);

//		ArrayList<Integer> listIdStu = new ArrayList<Integer>();
//		listIdStu.add(1143);
//		listIdStu.add(1);
//		getValueWithId("AccessControl", "Student_data_layer", "idStu", listIdStu);

//		System.out.println(sArrayList.toString());
//		getCoverAllData();
//		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.add("idClasses");
//		arrayList.add("courseCode");
//		arrayList.add("LIST_Student");
//		arrayList.add("Lecturer_data_layer");

//		System.out.println(arrayList.toString());

	}

	public static ArrayList<Document> getValueWithId1(String DB_NAME, String Collection, ArrayList<String> field) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		/* Connect to Database */
		try (MongoClient client = new MongoClient("localhost", 27017)) {

			MongoDatabase database = client.getDatabase(DB_NAME);
			MongoCollection<Document> collection = database.getCollection(Collection);
			ArrayList<Document> arrayList = new ArrayList<>();

			Document query = new Document();
			Document projection = new Document();
			for (String string : field) {
				projection.append(string, "$" + string);
//				projection.append("idClasses", "$idClasses");
			}

			Block<Document> processBlock = new Block<Document>() {
				@Override
				public void apply(final Document document) {
//					String aString1 = document.getString("idClasses");
//					String aString = document.getString("idClasses");

					arrayList.add(document);
				}
			};
			collection.find(query).projection(projection).forEach(processBlock);
			return arrayList;
		} catch (MongoException e) {

		}
		return null;
	}

	/***
	 * 
	 * @param DB_Name
	 * @param Collection
	 * @param Field
	 * @param StringsValue
	 */
	public static ArrayList<Document> coverInfoStudentIfHaveId(String DB_Name, String Collection, String Field,
			int Id) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		try (MongoClient client = connectDatabase("localhost", 27017);) {

			MongoDatabase database = client.getDatabase(DB_Name);
			MongoCollection<Document> collection = database.getCollection(Collection);
			ArrayList<Document> arrayList = new ArrayList<>();

			Document query = new Document();
			query.append(Field, Id);

			Document projection = new Document();
			projection.append("idStu", "$idStu");
			projection.append("username", "$username");
			projection.append("password", "$password");
			projection.append("fullname", "$fullname");
			projection.append("role", "$role");
			projection.append("Department", "$department");
			projection.append("birthdate", "$birthdate");

			Block<Document> processBlock = new Block<Document>() {
				@Override
				public void apply(final Document document) {
					String fullname = document.getString("fullname");
					System.out.println("\n\t\t Full Name: " + fullname);

					String username = document.getString("username");
					username = CoverData.hide(username);
					System.out.println("\n\t\t Username: " + username);

					String password = document.getString("password");
					password = CoverData.hide(password);
					System.out.println("\n\t\t Password: " + password);

					String birthday = document.getString("birthdate");
					birthday = CoverData.displayYearBirthday(birthday);
					System.out.println("\n\t\t Birthday: " + birthday);

					String Department = document.getString("Department");
					System.out.println("\n\t\t Department: " + Department);
					Department = CoverData.hide(Department);

					String role = document.getString("role");
					System.out.println("\n\t\t Role: " + role);
					arrayList.add(document);
				}
			};
			collection.find(query).projection(projection).forEach(processBlock);
			client.close();
			return arrayList;
		} catch (MongoException e) {

		}
		return null;
	}

	/**
	 * 
	 * @param DB_Name
	 * @param Collection
	 * @param Field
	 * @param Id
	 * @return
	 */
	public static ArrayList<Document> showInfoStudentIfHaveId(String DB_Name, String Collection, String Field, int Id) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		try (MongoClient client = connectDatabase("localhost", 27017);) {

			MongoDatabase database = client.getDatabase(DB_Name);
			MongoCollection<Document> collection = database.getCollection(Collection);
			ArrayList<Document> arrayList = new ArrayList<>();

			Document query = new Document();
			query.append(Field, Id);

			Document projection = new Document();
			projection.append("idStu", "$idStu");
			projection.append("username", "$username");
			projection.append("password", "$password");
			projection.append("fullname", "$fullname");
			projection.append("role", "$role");
			projection.append("Department", "$department");
			projection.append("birthdate", "$birthdate");

			Block<Document> processBlock = new Block<Document>() {
				@Override
				public void apply(final Document document) {
					String fullname = document.getString("fullname");
					System.out.println("\n\t\t Full Name: " + fullname);

					String username = document.getString("username");
					System.out.println("\n\t\t Username: " + username);

					String password = document.getString("password");
					System.out.println("\n\t\t Password: " + password);

					String birthday = document.getString("birthdate");
					System.out.println("\n\t\t Birthday: " + birthday);

					String Department = document.getString("Department");
					System.out.println("\n\t\t Department: " + Department);
					Department = CoverData.hide(Department);

					String role = document.getString("role");
					System.out.println("\n\t\t Role: " + role);
					arrayList.add(document);
				}
			};
			collection.find(query).projection(projection).forEach(processBlock);
			client.close();
			return arrayList;
		} catch (MongoException e) {

		}
		return null;
	}

	/***
	 * 
	 * @param DB_NAME
	 * @param Collection
	 * @param fieldId
	 * @return
	 */
	public static ArrayList<Integer> getValueWithId(String DB_NAME, String Collection, String fieldId) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		/* Connect to Database */
		try (MongoClient client = new MongoClient("localhost", 27017)) {
			MongoDatabase database = client.getDatabase(DB_NAME);
			/* Get Collection */
			MongoCollection<Document> collection = database.getCollection(Collection);
			ArrayList<Integer> listValue = new ArrayList<Integer>();
			Document query = new Document();
			Document projection = new Document();
			projection.append(fieldId, "$" + fieldId);

			Block<Document> processBlock = new Block<Document>() {
				@Override
				public void apply(final Document document) {
					int idLec = document.getInteger(fieldId);
					listValue.add(idLec);
				}
			};
			collection.find(query).projection(projection).forEach(processBlock);
			return listValue;
		} catch (MongoException e) {
			System.out.println(e);
		}
		return null;
	}

	/***
	 * 
	 * @param DB_Name
	 * @param Collection
	 * @param Field
	 * @param StringsValue
	 */
	public static void getValueWithId(String DB_Name, String Collection, String Field,
			ArrayList<Integer> StringsValue) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		try (MongoClient client = new MongoClient("localhost", 27017)) {

			MongoDatabase database = client.getDatabase(DB_Name);
			MongoCollection<Document> collection = database.getCollection(Collection);

			for (Integer Value : StringsValue) {
				Document query = new Document();
				query.append(Field, Value);

				Block<Document> processBlock = new Block<Document>() {
					@Override
					public void apply(final Document document) {
						System.out.println(document);
					}
				};
				collection.find(query).forEach(processBlock);
			}
			client.close();
		} catch (MongoException e) {
			// handle MongoDB exception
		}
	}

	/***
	 * 
	 * @param DB_Name
	 * @param Collection
	 * @param Field
	 * @param idValue
	 * @return
	 */
	public static ArrayList<Document> getValueWithId(String DB_Name, String Collection, String Field, Integer idValue) {
		// Disable INFO message
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		try (MongoClient client = new MongoClient("localhost", 27017)) {
			MongoDatabase database = client.getDatabase(DB_Name);
			MongoCollection<Document> collection = database.getCollection(Collection);

			ArrayList<Document> listValue = new ArrayList<Document>();
			Document query = new Document();
			query.append(Field, idValue);

			Block<Document> processBlock = new Block<Document>() {

				@Override
				public void apply(final Document document) {
					listValue.add(document);
				}
			};
			collection.find(query).forEach(processBlock);
			return listValue;
		} catch (MongoException e) { // handle MongoDB exception }
			return null;
		}
	}

	/* =================== Connection =================== */

	/* Connect MongoDB not security */
	/***
	 * 
	 * @param HOST
	 * @param PORT
	 * @return
	 */
	public static MongoClient connectDatabase(String HOST, Integer PORT) {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		return mongoClient;
	}

}