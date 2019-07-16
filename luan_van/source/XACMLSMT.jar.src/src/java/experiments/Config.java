package experiments;

import com.mongodb.MongoClient;

public class Config {

	public static void main(String[] args) {
		MongoClient mongoClient = GetDataMongoDB.connectDatabase(MyConstants.HOST, MyConstants.PORT);
		
	}

}
