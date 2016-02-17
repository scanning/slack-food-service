package ng.i.cann.s.food.state.mongo;

import ng.i.cann.s.food.state.IMenuState;
import ng.i.cann.s.food.state.IMenuStateReadOnly;

import com.mongodb.DB;

public class MenuStateMongoImpl extends MongoImplBase<MenuState> implements IMenuState, IMenuStateReadOnly {

	private static final String COLLECTION_NAME = "menuState";

	public MenuStateMongoImpl(DB db) {
		super(db, COLLECTION_NAME, MenuState.class);
	}

	@Override
	public int getCurrentMenuId() {
		int menuId = IMenuState.DEFAULT_MENU_ID;
		MenuState state = findOne();
		if (state != null) {
			menuId = state.getMenuId();
		}
		return menuId;
	}

	@Override
	public void setCurrentMenuId(int menuId) {
		int currentId = getCurrentMenuId();
		if (currentId != IMenuState.DEFAULT_MENU_ID) {
			clearCurrentMenuId();
		}
		MenuState state = new MenuState(menuId);
		insert(state);
	}

	@Override
	public void clearCurrentMenuId() {
		removeAll();
	}

}
