package ng.i.cann.s.food.command;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.menu.MenuItem;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.state.MenuStateReadOnly;
import ng.i.cann.s.food.state.OrdersState;

public class OrdersCommands extends Commands {

	private final MenuStateReadOnly menuState;
	private final OrdersState ordersState;
	private final List<Menu> menus;

	protected OrdersCommands(List<Menu> menus, MenuStateReadOnly menuState, OrdersState ordersState, String param) {
		super(param);
		this.menus = menus;
		this.menuState = menuState;
		this.ordersState = ordersState;
	}

	@Override
	public String doShow() {
		Menu currentMenu = menus.get(menuState.getCurrentMenuId() - 1);
		Map<String, String> orders = ordersState.getOrders();
		if (orders.size() > 0) {
			BigDecimal finalPrice = new BigDecimal(0);
			Map<String, Set<String>> orderIdToUserList = new HashMap<>();
			for (String user : orders.keySet()) {
				String orderId = orders.get(user);
				Set<String> users;
				if (orderIdToUserList.containsKey(orderId)) {
					users = orderIdToUserList.get(orderId);
				} else {
					users = new HashSet<>();
					orderIdToUserList.put(orderId, users);
				}
				users.add(user);
			}

			StringBuilder sb = new StringBuilder();
			for (String orderId : orderIdToUserList.keySet()) {
				Set<String> users = orderIdToUserList.get(orderId);
				MenuItem menuItem = currentMenu.getItemById(orderId);
				if (menuItem != null) {
					sb.append(users.size());
					sb.append(" X ");
					sb.append(menuItem.getFormattedString());
					sb.append(System.lineSeparator());
				}
			}

			sb.append(Messages.FINAL_PRICE);
			sb.append(NumberFormat.getCurrencyInstance().format(finalPrice));
			sb.append(System.lineSeparator());
			return sb.toString();
		} else {
			return Messages.NO_ORDERS_PLACED_YET;
		}
	}

	@Override
	public String doList() {
		Menu currentMenu = menus.get(menuState.getCurrentMenuId() - 1);
		Map<String, String> orders = ordersState.getOrders();
		if (orders.size() > 0) {
			BigDecimal finalPrice = new BigDecimal(0);
			StringBuilder sb = new StringBuilder();
			for (String user : orders.keySet()) {
				String orderId = orders.get(user);
				MenuItem menuItem = currentMenu.getItemById(orderId);
				if (menuItem != null) {
					sb.append(user + " ordered " + menuItem.getFormattedString());
					sb.append(System.lineSeparator());
					finalPrice = finalPrice.add(menuItem.getPrice());
				}
			}
			sb.append(Messages.FINAL_PRICE);
			sb.append(NumberFormat.getCurrencyInstance().format(finalPrice));
			sb.append(System.lineSeparator());
			return sb.toString();
		} else {
			return Messages.NO_ORDERS_PLACED_YET;
		}
	}

	@Override
	public String doSet() {
		throw new IllegalStateException();
	}

	@Override
	public String doClear() {
		ordersState.clearOrders();
		return Messages.ORDER_CLEARED;
	}

	@Override
	public String doHelp() {
		return "orders help";
	}

}
