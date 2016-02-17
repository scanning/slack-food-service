package ng.i.cann.s.food.state;

import java.util.Map;

public interface IOrderState {

	public String getOrderId(String username);

	public void setOrderForUser(String username, String orderId);

	public void clearOrderForUser(String username);

	public Map<String, String> getOrders();

}
