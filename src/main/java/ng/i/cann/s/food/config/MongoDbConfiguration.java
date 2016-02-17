package ng.i.cann.s.food.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MongoDbConfiguration {

	public String getUsername() {
		return System.getenv("MONGO_DB_USERNAME");
	}

	public String getPassword() {
		return System.getenv("MONGO_DB_PASSWORD");
	}

	public String getHost() {
		return System.getenv("MONGO_DB_HOST");
	}

	public int getPort() {
		return Integer.parseInt(System.getenv("MONGO_DB_PORT"));
	}

	public String getDbName() {
		return System.getenv("MONGO_DB_NAME");
	}

	@JsonIgnore
	public String getURI() {
		StringBuilder sb = new StringBuilder();
		sb.append("mongodb://");
		sb.append(getUsername());
		sb.append(":");
		sb.append(getPassword());
		sb.append("@");
		sb.append(getHost());
		sb.append(":");
		sb.append(getPort());
		sb.append("/");
		sb.append(getDbName());
		
		String result = sb.toString();
		System.out.println("Database connection URI is: " + result);
		return result;
	}
}
