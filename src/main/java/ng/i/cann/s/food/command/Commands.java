package ng.i.cann.s.food.command;

public abstract class Commands {

	private final String param;

	protected Commands(String param) {
		this.param = param;
	}

	protected String getParam() {
		return param;
	}

	public abstract String doShow();

	public abstract String doList();

	public abstract String doSet();

	public abstract String doClear();

	public abstract String doHelp();

}
