package gov.cdc.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.commands.Command;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.Rule;
import gov.cdc.engine.result.ValidationResult;

public class SimpleValidator extends AbstractValidator {

	private List<Rule> businessRules;

	public void initialize(JSONObject rules) throws ValidatorException {

		try {
			loadLibrary();
		} catch (Exception e) {
			throw new ValidatorException(e);
		}

		businessRules = new ArrayList<Rule>();

		Set<String> commands = rules.keySet();
		for (String command : commands) {
			if (library.containsKey(command)) {
				businessRules.add(Rule.createRule(library.get(command), rules.get(command)));
			} else
				throw new ValidatorException("The command " + command + " doesn't exist.");
		}

	}

	public ValidationResult validate(JSONObject object) throws ValidatorException {
		CompoundValidationResult cvr = new CompoundValidationResult();
		cvr.setObject(object.toString());

		if (businessRules == null)
			throw new ValidatorException("The validator has not been initialized.");
		
		for (Rule rule : businessRules) {
			Command cmd = rule.getCommand();
			cmd.setEngine(this);
			cvr.addResult(cmd.evaluate(rule.getParameters(), object));
		}

		return cvr;
	}

	public Command getCommand(String keyword) throws ValidatorException {
		if (library.containsKey(keyword))
			return library.get(keyword);
		else
			throw new ValidatorException("The command " + keyword + " doesn't exist.");
	}

}
