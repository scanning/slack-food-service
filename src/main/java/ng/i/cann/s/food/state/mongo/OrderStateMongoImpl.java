package ng.i.cann.s.food.state.mongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ng.i.cann.s.food.state.IOrderState;
import ng.i.cann.s.food.state.IOrdersState;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class OrderStateMongoImpl extends MongoImplBase<OrderState> implements IOrderState, IOrdersState {

	private static final String COLLECTION_NAME = "orderState";
	private static final String USERNAME_FIELD = "username";

	public OrderStateMongoImpl(DB db) {
		super(db, COLLECTION_NAME, OrderState.class);
		getCollection().createIndex(new BasicDBObject(USERNAME_FIELD, 1));
	}

	@Override
	public String getOrderId(String username) {
		String orderId = null;
		Map<String, String> orders = this.getOrders();
		if (orders != null && orders.size() > 0) {
			for (String user : orders.keySet()) {
				if (user != null && user.length() > 0 && user.equals(username)) {
					orderId = orders.get(user);
				}
			}
		}
		return orderId;
	}

	@Override
	public void setOrderForUser(String username, String orderId) {
		OrderState state = super.findOneByIndex(USERNAME_FIELD, username);
		if (state != null) {
			clearOrderForUser(username);
		}
		super.insert(new OrderState(username, orderId));
	}

	@Override
	public void clearOrderForUser(String username) {
		super.removeOneByIndex(USERNAME_FIELD, username);
	}

	@Override
	public Map<String, String> getOrders() {
		Map<String, String> ordersByUsername = new HashMap<>();
		List<OrderState> orderStates = super.findAll();
		if (orderStates != null) {
			for (OrderState state : orderStates) {
				ordersByUsername.put(state.getUsername(), state.getOrderId());
			}
		}
		return ordersByUsername;
	}

	@Override
	public void clearOrders() {
		super.removeAll();
	}

}
