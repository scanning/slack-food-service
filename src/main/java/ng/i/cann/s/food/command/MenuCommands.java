package ng.i.cann.s.food.command;

import java.util.List;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.state.IMenuState;
import ng.i.cann.s.food.state.memory.MenuState;

public class MenuCommands extends Commands {

	private final List<Menu> menus;
	private final IMenuState menuState;

	public MenuCommands(List<Menu> menus, IMenuState menuState, String param) {
		super(param);
		this.menus = menus;
		this.menuState = menuState;
	}

	@Override
	public String doShow() {
		String param = getParam();
		if (param == null && menuState.getCurrentMenuId() > MenuState.DEFAULT_MENU_ID) {
			param = menuState.getCurrentMenuId() + "";
		}
		if (param != null) {
			Menu m = menus.get(Integer.parseInt(param) - 1);
			if (m != null) {
				return m.getFormattedString();
			} else {
				return "Invalid menu id.";
			}
		} else {
			return Messages.NO_MENU_SET;
		}
	}

	@Override
	public String doList() {
		StringBuilder sb = new StringBuilder();
		sb.append("Listing all of the menus");
		sb.append(System.lineSeparator());
		sb.append("------------------------");
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		int i = 0;
		for (Menu m : menus) {
			sb.append(++i);
			sb.append(" - ");
			sb.append(m.getRestaurant().getName());
			sb.append(System.lineSeparator());
		}
		sb.append(System.lineSeparator());
		sb.append("------------------------");
		sb.append(System.lineSeparator());
		return sb.toString();
	}

	@Override
	public String doSet() {
		String param = getParam();
		menuState.setCurrentMenuId(Integer.parseInt(param));
		if (menuState.getCurrentMenuId() > 0) {
			return "Set menu to " + menus.get(menuState.getCurrentMenuId() - 1).getRestaurant().getName();
		} else {
			menuState.clearCurrentMenuId();
			return "Cleared current menu.";
		}
	}

	@Override
	public String doClear() {
		menuState.clearCurrentMenuId();
		return "Cleared current menu.";
	}

	@Override
	public String doHelp() {
		return "menu help";
	}

}
