package ng.i.cann.s.food.state.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuState extends StateBase {

	@JsonProperty
	private int menuId;

	public MenuState(int menuId) {
		super();
		this.menuId = menuId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + menuId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuState other = (MenuState) obj;
		if (menuId != other.menuId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuState [menuId=" + menuId + ", toString()=" + super.toString() + "]";
	}
}
