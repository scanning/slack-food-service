package ng.i.cann.s.food.resources;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import ng.i.cann.s.food.state.IOrdersState;

import com.codahale.metrics.annotation.Timed;

/**
 * Displays all of the orders.
 * 
 * @author scanning
 *
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

	private final IMenuStateReadOnly menuState;
	private final IOrdersState ordersState;
	private final List<Menu> menus;

	public OrdersResource(List<Menu> menus, IOrdersState ordersState, IMenuStateReadOnly menuState) {
		this.menus = Collections.unmodifiableList(menus);
		this.ordersState = ordersState;
		this.menuState = menuState;
	}

	enum Command {
		SHOW, CLEAR
	}

	// >'orders' shows all of the current orders
	// >'orders clear' clears all of the current orders

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String manageOrders(@FormParam(SlackConstants.TOKEN_FORM_PARAM) String token, @FormParam(SlackConstants.TEAM_ID_FORM_PARAM) String teamId,
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
			cmd = null;
		}

		if (cmd != null) {
			switch (cmd) {
			case SHOW:
				if (param == null) {

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
				} else {
					return Messages.INVALID_ORDERS_COMMAND;
				}

			case CLEAR:
				if (param == null) {
					ordersState.clearOrders();
					return Messages.ORDER_CLEARED;
				} else {
					return Messages.INVALID_ORDERS_COMMAND;
				}

			default:
				throw new IllegalStateException();
			}
		} else {
			return Messages.INVALID_ORDERS_COMMAND;
		}
	}

}
