package ng.i.cann.s.food.state.mongo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public abstract class MongoImplBase<T extends StateBase> {

	private static final String DAY_FIELD = "day";

	private final JacksonDBCollection<T, Date> collection;

	protected MongoImplBase(DB db, String collectionName, Class<T> clazz) {
		collection = JacksonDBCollection.wrap(db.getCollection(collectionName), clazz, Date.class);
		getCollection().createIndex(new BasicDBObject(DAY_FIELD, 1));
	}

	protected void removeOneByIndex(String index, String value) {
		collection.remove(DBQuery.is(DAY_FIELD, getToday()).and(DBQuery.is(index, value)));
	}

	protected void removeAll() {
		collection.remove(DBQuery.is(DAY_FIELD, getToday()));
	}

	protected T findOne() {
		return collection.findOne(DBQuery.is(DAY_FIELD, getToday()));
	}

	protected T findOneByIndex(String index, String value) {
		return collection.findOne(DBQuery.is(DAY_FIELD, getToday()).and(DBQuery.is(index, value)));
	}

	protected List<T> findAll() {
		List<T> result = new ArrayList<>();
		DBCursor<T> all = collection.find(DBQuery.is(DAY_FIELD, getToday()));
		if (all != null && all.size() > 0) {
			result = all.toArray();
		}
		return result;
	}

	protected void insert(T t) {
		collection.insert(t);
	}

	protected JacksonDBCollection<T, Date> getCollection() {
		return collection;
	}

	static Date getToday() {
		TimeZone tz = TimeZone.getTimeZone("Australia/Brisbane");
		Calendar cal = Calendar.getInstance(tz);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
