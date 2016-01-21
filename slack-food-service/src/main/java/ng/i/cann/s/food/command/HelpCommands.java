package ng.i.cann.s.food.command;

public class HelpCommands extends Commands {

	public HelpCommands(String param) {
		super(param);
	}

	@Override
	public String doShow() {
		throw new IllegalStateException();
	}

	@Override
	public String doList() {
		throw new IllegalStateException();
	}

	@Override
	public String doSet() {
		throw new IllegalStateException();
	}

	@Override
	public String doClear() {
		throw new IllegalStateException();
	}

	@Override
	public String doHelp() {
		return "Help message!";
	}

}
