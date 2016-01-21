package ng.i.cann.s.food.state;

import java.util.Collections;
import java.util.Map;

public class OrdersState {

	private final OrderState orderState;

	public OrdersState(OrderState orderState) {
		this.orderState = orderState;
	}

	public Map<String, String> getOrders() {
		return Collections.unmodifiableMap(orderState.getOrders());
	}

	public void clearOrders() {
		orderState.getOrders().clear();
	}
}
