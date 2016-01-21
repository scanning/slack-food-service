package ng.i.cann.s.food.messages;

public interface Messages {

	public static final String NO_MENU_SET = "No menu currently set for today.";
	public static final String NO_ORDER_SET = "No order currently set for today.";

	public static final String UNABLE_TO_FIND_MENU_ITEM = "Unable to find the specified item on the menu.";
	public static final String NO_ORDER_PLACED_YET = "You haven't placed your order yet.";

	public static final String NO_ORDERS_PLACED_YET = "No orders have been placed yet.";

	public static final String ORDER_CLEARED = "Order cleared.";
	public static final String YOU_HAVE_ORDERED = " You have placed an order for ";

	public static final String INVALID_ORDER_COMMAND = "Invalid command. Usage is: /order <[item-id] | show | clear | set [item-id]>";
	public static final String INVALID_ORDERS_COMMAND = "Invalid command. Usage is: /orders <show | clear>";

	public static final String ORDERS_CLEARED = "All orders cleared.";
	
	public static final String FINAL_PRICE = "All orders add up to ";
	
	public static final String USAGE = "This is the usage message.";

}
