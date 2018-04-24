package gov.cdc.engine.commands;

import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $or operator performs a logical OR operation on an array of two or more
 * <rules> and validates the object if at least one rule is satisfied.
 * 
 * @author Ben Chevallereau
 *
 */
public class OrCommand extends CompoundCommand {

	public String getKeyword() {
		return "$or";
	}

	@Override
	protected void initialize() {
		globalValid = false;
	}

	@Override
	protected boolean append(boolean rBool) {
		return globalValid || rBool;
	}

	@Override
	protected boolean execute(Object command, JSONObject object, CompoundValidationResult result) throws ValidatorException {
		boolean rBool = false;
		if (!(command instanceof JSONObject))
			throw new ValidatorException("Expected JSON Object but found: " + command.getClass());
		else {
			JSONObject commandObj = (JSONObject) command;

			Set<String> cmds = commandObj.keySet();
			for (String cmd : cmds) {
				Command commandItem = engine.getCommand(cmd);
				commandItem.setEngine(engine);
				ValidationResult vr = commandItem.evaluate(commandObj.get(cmd), object);
				result.addResult(vr);
				if (vr.isValid())
					rBool = true;
			}
		}
		return rBool;
	}

}
