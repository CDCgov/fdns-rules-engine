package gov.cdc.engine.result;

import gov.cdc.engine.commands.Command;

public class Rule {

	private Command command;
	private Object parameters;

	public static Rule createRule(Command command, Object parameters) {
		Rule r = new Rule();
		r.setCommand(command);
		r.setParameters(parameters);
		return r;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Object getParameters() {
		return parameters;
	}

	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}

}
