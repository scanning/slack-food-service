package ng.i.cann.s.food.state.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderState extends StateBase {

	@JsonProperty
	private String username;

	@JsonProperty
	private String orderId;

	public OrderState(String username, String orderId) {
		super();
		this.username = username;
		this.orderId = orderId;
	}

	public String getUsername() {
		return username;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderState other = (OrderState) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderState [username=" + username + ", orderId=" + orderId + "]";
	}

}
