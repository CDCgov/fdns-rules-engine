package gov.cdc.engine.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.commands.Command;
import gov.cdc.engine.commands.SingleCommand;

public class CompoundValidationResult extends ValidationResult {

	private List<ValidationResult> results = new ArrayList<ValidationResult>();

	public CompoundValidationResult() {
		setValid(true);
	}

	public CompoundValidationResult(String rule, String object, Command command) {
		setValid(true);
		setRule(rule);
		setObject(object);
		setCommand(command.getKeyword());
		if (command instanceof SingleCommand) {
			SingleCommand sc = (SingleCommand) command;
			setDescription(sc.getDescription());
			setComment(sc.getComment());
		}
	}

	public void addResult(ValidationResult vr) {
		if (!vr.isValid())
			setValid(false);
		results.add(vr);
	}

	public void addAllResults(List<ValidationResult> vrs) {
		for (ValidationResult validationResult : vrs) {
			addResult(validationResult);
		}
	}

	public List<ValidationResult> getValidationResults() {
		return results;
	}

	public List<ValidationResult> flatten() throws ValidatorException {
		List<ValidationResult> singleResults = new ArrayList<ValidationResult>();
		for (ValidationResult vr : results) {
			if (vr instanceof CompoundValidationResult) {
				if ("$or".equalsIgnoreCase(vr.getCommand()) ||
					"$and".equalsIgnoreCase(vr.getCommand()) ||
					((CompoundValidationResult) vr).results.size() > 1) {
					
					singleResults.addAll(((CompoundValidationResult) vr).flatten());
				} else {
					singleResults.add(vr);
				}
			} else {
				singleResults.add((ValidationResult) vr);
			}
		}
		return singleResults;
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		bld.append("Rule: ");
		bld.append(getRule());
		bld.append("\nObject: ");
		bld.append(getObject());
		bld.append("\nCommand: ");
		bld.append(getCommand());
		bld.append("\nComment: ");
		bld.append(getComment());
		bld.append("\nDescription: ");
		bld.append(getDescription());
		bld.append("\nIs Valid? ");
		bld.append(isValid());
		bld.append("\n");

		for (ValidationResult vr : results) {
			bld.append(vr.toString(1));
		}

		return bld.toString();
	}

	@Override
	public String toString(int index) {
		StringBuilder bld = new StringBuilder();
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Rule: ");
		bld.append(getRule());
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Object: ");
		bld.append(getObject());
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Command: ");
		bld.append(getCommand());
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Comment: ");
		bld.append(getComment());
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Description: ");
		bld.append(getDescription());
		bld.append("\n");
		bld.append(StringUtils.repeat(" ", index * 2));
		bld.append("Is Valid? ");
		bld.append(isValid());
		bld.append("\n");

		for (ValidationResult vr : results) {
			bld.append(vr.toString(index + 1));
			bld.append("\n");
		}

		return bld.toString();
	}

}
