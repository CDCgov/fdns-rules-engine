package gov.cdc.engine.commands;

import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $and operator performs a logical AND operation on an array of two or more
 * <rules> and validates objects that satisfy all business rules.
 * 
 * @author Ben Chevallereau
 *
 */
public class AndCommand extends CompoundCommand {

	public String getKeyword() {
		return "$and";
	}

	@Override
	protected void initialize() {
		globalValid = true;
	}

	@Override
	protected boolean append(boolean rBool) {
		return rBool && globalValid;
	}

	@Override
	protected boolean execute(Object command, JSONObject object, CompoundValidationResult result) throws ValidatorException {
		boolean rBool = true;
		if (!(command instanceof JSONObject))
			throw new ValidatorException("Expected JSON Object but found: " + command.getClass());
		else {
			JSONObject cmdObj = (JSONObject) command;

			Set<String> cmds = cmdObj.keySet();
			for (String cmd : cmds) {
				Command commandItem = engine.getCommand(cmd);
				commandItem.setEngine(engine);
				ValidationResult vr = commandItem.evaluate(cmdObj.get(cmd), object);
				result.addResult(vr);
				rBool = rBool && vr.isValid();
			}
		}
		return rBool;
	}
}
