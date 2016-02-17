package ng.i.cann.s.food.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.messages.Messages;
import ng.i.cann.s.food.slack.SlackConstants;
import ng.i.cann.s.food.state.IMenuState;
import ng.i.cann.s.food.state.memory.MenuState;

import com.codahale.metrics.annotation.Timed;

/**
 * Manage today's menu.
 * 
 * @author scanning
 *
 */
@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

	private final List<Menu> menus;

	private final IMenuState menuState;

	public MenuResource(List<Menu> menus, IMenuState menuState) {
		this.menus = menus;
		this.menuState = menuState;
	}

	enum Command {
		LIST, SET, SHOW
	}

	// >'menu list' lists all the menus
	// >'menu set <id>' sets a current menu
	// >'menu show <id>' shows a menu for a particular restaurant
	// >'menu' shows the current menu
	// >'menu show' shows the current menu

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Timed
	public String getMenu(@FormParam(SlackConstants.TOKEN_FORM_PARAM) String token, @FormParam(SlackConstants.TEAM_ID_FORM_PARAM) String teamId,
			@FormParam(SlackConstants.TEAM_DOMAIN_FORM_PARAM) String teamDomain, @FormParam(SlackConstants.CHANNEL_ID_FORM_PARAM) String channelId,
			@FormParam(SlackConstants.CHANNEL_NAME_FORM_PARAM) String channelName, @FormParam(SlackConstants.USER_ID_FORM_PARAM) String userId,
			@FormParam(SlackConstants.USER_NAME_FORM_PARAM) String userName, @FormParam(SlackConstants.COMMAND_FORM_PARAM) String command,
			@FormParam(SlackConstants.TEXT_FORM_PARAM) String text, @FormParam(SlackConstants.RESPONSE_URL_FORM_PARAM) String responseUrl) {

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
			case LIST:
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

			case SHOW:
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

			case SET:
				if (param != null) {
					// Setting menu
					menuState.setCurrentMenuId(Integer.parseInt(param));
					if (menuState.getCurrentMenuId() > 0) {
						return "Set menu to " + menus.get(menuState.getCurrentMenuId() - 1).getRestaurant().getName();
					} else {
						menuState.clearCurrentMenuId();
						return "Cleared current menu.";
					}
				} else {
					// Need to specify a menu id to set one
					return "Please specify a menu id";
				}

			default:
				throw new IllegalStateException();
			}
		} else {
			return "Invalid command. Usage is: /menu <list | show | set [menu-id] | show [menu-id]>";
		}
	}

}
