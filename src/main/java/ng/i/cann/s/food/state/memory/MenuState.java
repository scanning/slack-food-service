package ng.i.cann.s.food.state.memory;

import ng.i.cann.s.food.state.IMenuState;
import ng.i.cann.s.food.state.IMenuStateReadOnly;

/**
 * Contains all the menu state. This could be backed by some kind of persistent
 * store but for now just use an in-memory representation.
 * 
 * @author scanning
 *
 */
public class MenuState implements IMenuState, IMenuStateReadOnly {

	private int currentMenuId = DEFAULT_MENU_ID;

	public void setCurrentMenuId(int currentMenuId) {
		this.currentMenuId = currentMenuId;
	}

	public void clearCurrentMenuId() {
		this.currentMenuId = DEFAULT_MENU_ID;
	}

	public int getCurrentMenuId() {
		return currentMenuId;
	}

}
