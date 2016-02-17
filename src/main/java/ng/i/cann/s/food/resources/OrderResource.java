package ng.i.cann.s.food.resources;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.menu.MenuItem;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.slack.SlackConstants;
import ng.i.cann.s.food.state.IMenuStateReadOnly;
import ng.i.cann.s.food.state.IOrderState;

import com.codahale.metrics.annotation.Timed;

/**
 * Allows a user to place an order.
 * 
 * @author scanning
 *
 */
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

	private final IMenuStateReadOnly menuState;
	private final IOrderState orderState;
	private final List<Menu> menus;

	public OrderResource(List<Menu> menus, IOrderState orderState, IMenuStateReadOnly menuState) {
		this.menus = Collections.unmodifiableList(menus);
		this.orderState = orderState;
		this.menuState = menuState;
	}

	enum Command {
		SET, SHOW, CLEAR
	}

	// >'order' show the current order for the user
	// >'order show' show the current order for the user
	// >'order <id>' order an item from current menu for the user
	// >'order clear' clears the current order for the user

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String placeOrder(@FormParam(SlackConstants.TOKEN_FORM_PARAM) String token, @FormParam(SlackConstants.TEAM_ID_FORM_PARAM) String teamId,
			@FormParam(SlackConstants.TEAM_DOMAIN_FORM_PARAM) String teamDomain, @FormParam(SlackConstants.CHANNEL_ID_FORM_PARAM) String channelId,
			@FormParam(SlackConstants.CHANNEL_NAME_FORM_PARAM) String channelName, @FormParam(SlackConstants.USER_ID_FORM_PARAM) String userId,
			@FormParam(SlackConstants.USER_NAME_FORM_PARAM) String userName, @FormParam(SlackConstants.COMMAND_FORM_PARAM) String command,
			@FormParam(SlackConstants.TEXT_FORM_PARAM) String text, @FormParam(SlackConstants.RESPONSE_URL_FORM_PARAM) String responseUrl) {

		System.out.println(token);
		System.out.println(teamId);
		System.out.println(teamDomain);
		System.out.println(channelId);
		System.out.println(channelName);
		System.out.println(userId);
		System.out.println(userName);
		System.out.println(command);
		System.out.println(text);
		System.out.println(responseUrl);

		if (menuState.getCurrentMenuId() == IMenuStateReadOnly.DEFAULT_MENU_ID) {
			return Messages.NO_MENU_SET;
		}

		Menu currentMenu = menus.get(menuState.getCurrentMenuId() - 1);

		// Parse the text... get the command, then get the param[s]
		Command cmd = null;
		String param = null;
		try {
			if (text != null && text.length() > 0) {
				if (text.contains(" ")) {
					String[] texts = text.split(" ");
					if (texts.length == 2) {
						cmd = Command.valueOf(texts[0].toUpperCase());
						param = texts[1];
					}
				} else {
					cmd = Command.valueOf(text.toUpperCase());
				}
			} else {
				cmd = Command.SHOW;
			}
		} catch (IllegalArgumentException iae) {
			cmd = Command.SET;
			param = text;
		}

		if (cmd != null) {
			switch (cmd) {
			case SHOW:
				if (param != null) {
					return Messages.INVALID_ORDER_COMMAND;
				} else {
					if (orderState.getOrderId(userName) != null) {
						MenuItem m = currentMenu.getItemById(orderState.getOrderId(userName));
						if (m != null) {
							return Messages.YOU_HAVE_ORDERED + m.getFormattedString();
						} else {
							return Messages.UNABLE_TO_FIND_MENU_ITEM;
						}
					} else {
						return Messages.NO_ORDER_PLACED_YET;
					}
				}

			case SET:
				if (param != null) {
					MenuItem menuItem;
					if ((menuItem = currentMenu.getItemById(param)) == null) {
						return Messages.UNABLE_TO_FIND_MENU_ITEM;
					} else {
						orderState.setOrderForUser(userName, param);
						return Messages.YOU_HAVE_ORDERED + menuItem.getFormattedString();
					}
				} else {
					return Messages.INVALID_ORDER_COMMAND;
				}

			case CLEAR:
				if (param == null) {
					orderState.clearOrderForUser(userName);
					return Messages.ORDER_CLEARED;
				} else {
					return Messages.INVALID_ORDER_COMMAND;
				}

			default:
				throw new IllegalStateException();
			}
		} else {
			return Messages.INVALID_ORDER_COMMAND;
		}
	}

}
