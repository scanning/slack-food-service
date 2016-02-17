package ng.i.cann.s.food.state.mongo;

import io.dropwizard.lifecycle.Managed;

import com.mongodb.MongoClient;

public class MongoManaged implements Managed {

	private MongoClient mongoClient;

	public MongoManaged(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public void stop() throws Exception {
		mongoClient.close();
	}

}
