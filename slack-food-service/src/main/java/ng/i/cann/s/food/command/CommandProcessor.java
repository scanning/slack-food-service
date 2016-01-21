package ng.i.cann.s.food.command;

import java.util.Arrays;
import java.util.List;

import ng.i.cann.s.food.menu.Menu;
import ng.i.cann.s.food.state.MenuState;
import ng.i.cann.s.food.state.OrderState;
import ng.i.cann.s.food.state.OrdersState;

public class CommandProcessor {

	enum Operation {
		CLEAR(false, false), LIST(false, false), SHOW(false, true), SET(true, true);

		boolean requiresParam = false;
		boolean allowsParam = false;

		Operation(boolean requiresParam, boolean allowsParam) {
			this.requiresParam = requiresParam;
			this.allowsParam = allowsParam;
		}

	}

	enum Target {
		MENU(Operation.SHOW, Operation.SET, Operation.CLEAR, Operation.LIST), ORDER(Operation.SET, Operation.CLEAR, Operation.SHOW), ORDERS(
				Operation.SHOW, Operation.CLEAR, Operation.LIST), HELP(Operation.SHOW);

		Target(Operation defaultOperation, Operation... operations) {
			this.defaultOperation = defaultOperation;
			if (operations == null) {
				operations = new Operation[] {};
			}
			this.supportedOperations = Arrays.asList(operations);
		}

		private final List<Operation> supportedOperations;
		private final Operation defaultOperation;

		boolean isSupportedOperation(Operation operation) {
			return supportedOperations.contains(operation) || defaultOperation.equals(operation);
		}
	}

	public static String process(String[] args, List<Menu> menus, MenuState menuState, OrderState orderState, OrdersState ordersState, String username)
			throws CommandProcessorException {

		if (args == null || args.length < 1 || args.length > 3) {
			throw new CommandProcessorException("Invalid usage.");
		}

		String result = null;
		try {
			Target target = Target.valueOf(args[0].toUpperCase());
			if (args.length > 1) {
				String param = null;
				Operation op = null;
				try {
					op = Operation.valueOf(args[1].toUpperCase());
					if (!target.isSupportedOperation(op)) {
						throw new CommandProcessorException("Unsupported operation " + op.name().toLowerCase() + " for "
								+ target.name().toLowerCase() + ".");
					}
				} catch (IllegalArgumentException e) {
					param = args[1];
					op = target.defaultOperation;
				}

				if (param != null && args.length > 2) {
					throw new CommandProcessorException("Invalid operation " + args[1] + ".");
				}

				if (op != null && param == null) {
					if (op.requiresParam && args.length == 3) {
						param = args[2];
					} else if (op.requiresParam && args.length == 2) {
						throw new CommandProcessorException("Missing required parameter for operation " + op.name().toLowerCase() + ".");
					} else if (!op.allowsParam && args.length == 3) {
						throw new CommandProcessorException("Parameter for operation " + op.name().toLowerCase() + " not allowed.");
					} else {

					}

				}

				Commands cmds = null;
				switch (target) {
				case MENU:
					cmds = new MenuCommands(menus, menuState, param);
					break;
				case ORDER:
					cmds = new OrderCommands(menus, menuState, orderState, username, param);
					break;
				case ORDERS:
					cmds = new OrdersCommands(menus, menuState, ordersState, param);
					break;
				default:
					cmds = new HelpCommands(param);
				}

				switch (op) {
				case CLEAR:
					result = cmds.doClear();
					break;
				case LIST:
					result = cmds.doList();
					break;
				case SHOW:
					result = cmds.doShow();
					break;
				case SET:
					result = cmds.doSet();
					break;
				default:
					result = cmds.doHelp();
				}
			} else {
				if (!(Target.HELP.equals(target) || Target.MENU.equals(target) || Target.ORDER.equals(target))) {
					throw new CommandProcessorException("Target " + target.name().toLowerCase() + " requires an operation.");
				}
			}
		} catch (IllegalArgumentException e) {
			throw new CommandProcessorException("Invalid target " + args[0].toLowerCase() + ".");
		}

		return result;
	}

	public static void main(String[] a) throws Exception {

		String[] cmds = new String[] { "", "help", "menu", "order", "orders", "menu clear", "menu list", "menu show", "menu set 1", "order 1",
				"order set 1", "order show", "order clear", "orders show", "orders list", "orders clear", "bad", "menu bad", "order bad",
				"orders bad", "menu set", "help clear", "order set 1 2", "order a a", "menu list 1" };

		for (String cmd : cmds) {
			try {
				String[] args = cmd.split(" ");
				String s = process(args, null, null, null, null, "scanning");
				System.out.println(cmd + ": " + s);
			} catch (CommandProcessorException e) {
				System.err.println(cmd + ": " + e.getMessage());
			}
		}
	}
}
