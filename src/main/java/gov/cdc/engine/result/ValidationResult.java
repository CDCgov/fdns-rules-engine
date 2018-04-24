package gov.cdc.engine.result;

import org.apache.commons.lang3.StringUtils;

import gov.cdc.engine.commands.Command;
import gov.cdc.engine.commands.SingleCommand;

public class ValidationResult {

	private boolean valid;
	private String rule;
	private String object;
	private String command;
	private String description;
	private String comment;

	public static ValidationResult create(boolean valid, String rule, String object, Command command) {
		ValidationResult r = new ValidationResult();
		r.setCommand(command.getKeyword());
		r.setObject(object);
		if (command instanceof SingleCommand) {
			SingleCommand sc = (SingleCommand) command;
			r.setDescription(sc.getDescription());
			r.setComment(sc.getComment());
		}
		r.setValid(valid);
		r.setRule(rule);
		return r;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getObject() {
		return object;
	}
	
	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		bld.append("Rule: ");
		bld.append(rule);
		bld.append("\nObject: ");
		bld.append(object);
		bld.append("\nCommand: ");
		bld.append(command);
		bld.append("\nComment: ");
		bld.append(comment);
		bld.append("\nDescription: ");
		bld.append(description);
		bld.append("\nIs Valid? ");
		bld.append(valid);
		return bld.toString();
	}

	public String toString(int index) {
		StringBuilder bld = new StringBuilder();
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Rule: ");
		bld.append(rule);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Object: ");
		bld.append(object);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Command: ");
		bld.append(command);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Comment: ");
		bld.append(comment);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Description: ");
		bld.append(description);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Is Valid? ");
		bld.append(valid);
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("---------------------");
		return bld.toString();
	}

}
