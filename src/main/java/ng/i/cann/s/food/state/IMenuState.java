package ng.i.cann.s.food.state;

public interface IMenuState extends IMenuStateReadOnly {

	public void setCurrentMenuId(int currentMenuId);

	public void clearCurrentMenuId();

}
