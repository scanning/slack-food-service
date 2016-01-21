package ng.i.cann.s.food.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ng.i.cann.s.food.command.CommandProcessor;
import ng.i.cann.s.food.command.CommandProcessorException;
import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.slack.SlackConstants;
import ng.i.cann.s.food.state.MenuState;
import ng.i.cann.s.food.state.OrderState;
import ng.i.cann.s.food.state.OrdersState;

import com.codahale.metrics.annotation.Timed;

/**
 * Manage today's food.
 * 
 * @author scanning
 *
 */
@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

	private final List<Menu> menus;

	private final MenuState menuState;
	private final OrderState orderState;
	private final OrdersState ordersState;

	public FoodResource(List<Menu> menus, MenuState menuState, OrderState orderState, OrdersState ordersState) {
		this.menus = menus;
		this.menuState = menuState;
		this.orderState = orderState;
		this.ordersState = ordersState;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String getMenu(@FormParam(SlackConstants.TOKEN_FORM_PARAM) String token, @FormParam(SlackConstants.TEAM_ID_FORM_PARAM) String teamId,
			@FormParam(SlackConstants.TEAM_DOMAIN_FORM_PARAM) String teamDomain, @FormParam(SlackConstants.CHANNEL_ID_FORM_PARAM) String channelId,
			@FormParam(SlackConstants.CHANNEL_NAME_FORM_PARAM) String channelName, @FormParam(SlackConstants.USER_ID_FORM_PARAM) String userId,
			@FormParam(SlackConstants.USER_NAME_FORM_PARAM) String userName, @FormParam(SlackConstants.COMMAND_FORM_PARAM) String command,
			@FormParam(SlackConstants.TEXT_FORM_PARAM) String text, @FormParam(SlackConstants.RESPONSE_URL_FORM_PARAM) String responseUrl) {

		// TODO -- Do some validation and stuff...

		String[] args = text.split("\\s+");

		try {
			return CommandProcessor.process(args, menus, menuState, orderState, ordersState, userName);
		} catch (CommandProcessorException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(e.getMessage());
			sb.append(System.lineSeparator());
			sb.append(Messages.USAGE);
			return sb.toString();
		}
	}

}
