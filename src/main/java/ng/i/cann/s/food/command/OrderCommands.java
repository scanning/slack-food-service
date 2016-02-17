package ng.i.cann.s.food.command;

import java.util.List;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.menu.MenuItem;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.state.IMenuStateReadOnly;
import ng.i.cann.s.food.state.IOrderState;

public class OrderCommands extends Commands {

	private final IMenuStateReadOnly menuState;
	private final IOrderState orderState;
	private final List<Menu> menus;
	private final String username;

	protected OrderCommands(List<Menu> menus, IMenuStateReadOnly menuState, IOrderState orderState, String username, String param) {
		super(param);
		this.menuState = menuState;
		this.orderState = orderState;
		this.menus = menus;
		this.username = username;
	}

	@Override
	public String doShow() {
		Menu currentMenu = menus.get(menuState.getCurrentMenuId() - 1);
		if (orderState.getOrderId(username) != null) {
			MenuItem m = currentMenu.getItemById(orderState.getOrderId(username));
			if (m != null) {
				return Messages.YOU_HAVE_ORDERED + m.getFormattedString();
			} else {
				return Messages.UNABLE_TO_FIND_MENU_ITEM;
			}
		} else {
			return Messages.NO_ORDER_PLACED_YET;
		}
	}

	@Override
	public String doList() {
		throw new IllegalStateException();
	}

	@Override
	public String doSet() {
		MenuItem menuItem;
		String param = getParam();
		Menu currentMenu = menus.get(menuState.getCurrentMenuId() - 1);
		if ((menuItem = currentMenu.getItemById(param)) == null) {
			return Messages.UNABLE_TO_FIND_MENU_ITEM;
		} else {
			orderState.setOrderForUser(username, param);
			return Messages.YOU_HAVE_ORDERED + menuItem.getFormattedString();
		}
	}

	@Override
	public String doClear() {
		orderState.clearOrderForUser(username);
		return Messages.ORDER_CLEARED;
	}

	@Override
	public String doHelp() {
		return "order help";
	}

}
