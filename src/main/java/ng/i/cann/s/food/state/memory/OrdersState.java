package ng.i.cann.s.food.state.memory;

import java.util.Collections;
import java.util.Map;

import ng.i.cann.s.food.state.IOrderState;
import ng.i.cann.s.food.state.IOrdersState;

public class OrdersState implements IOrdersState {

	private final IOrderState orderState;

	public OrdersState(IOrderState orderState) {
		this.orderState = orderState;
	}

	public Map<String, String> getOrders() {
		return Collections.unmodifiableMap(orderState.getOrders());
	}

	public void clearOrders() {
		orderState.getOrders().clear();
	}
}
