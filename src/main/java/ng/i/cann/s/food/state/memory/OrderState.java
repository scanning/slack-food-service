package ng.i.cann.s.food.state.memory;

import java.util.HashMap;
import java.util.Map;

import ng.i.cann.s.food.state.IOrderState;

public class OrderState implements IOrderState {

	private final Map<String, String> ordersByUsername = new HashMap<>();

	public String getOrderId(String username) {
		return ordersByUsername.get(username);
	}

	public void setOrderForUser(String username, String orderId) {
		ordersByUsername.put(username, orderId);
	}

	public void clearOrderForUser(String username) {
		ordersByUsername.remove(username);
	}

	public Map<String, String> getOrders() {
		return ordersByUsername;
	}

}
