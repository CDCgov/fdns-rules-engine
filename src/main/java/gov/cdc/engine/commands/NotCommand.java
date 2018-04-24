package gov.cdc.engine.commands;

import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * $not performs a logical NOT operation on the specified <rule> and validates
 * the objects that do not match the <rule>.
 * 
 * @author Ben Chevallereau
 *
 */
public class NotCommand extends SingleCommand {

	public String getKeyword() {
		return "$not";
	}

	@Override
	protected ValidationResult evaluateImpl(Object ruleObj, JSONObject object) throws ValidatorException {
		CompoundValidationResult result = new CompoundValidationResult(ruleObj.toString(), object != null ? object.toString() : null, this);

		JSONObject rule = (JSONObject) ruleObj;
		Set<String> cmds = rule.keySet();
		boolean rBool = true;
		String comment = null;
		String description = null;
		for (String cmd : cmds) {
			if (AbstractCommand.COMMENT_KEYWORD.equalsIgnoreCase(cmd))
				comment = rule.getString(AbstractCommand.COMMENT_KEYWORD);
			else if (AbstractCommand.DESCRIPTION_KEYWORD.equalsIgnoreCase(cmd))
				description = rule.getString(AbstractCommand.DESCRIPTION_KEYWORD);
			else {
				Command commandItem = engine.getCommand(cmd);
				commandItem.setEngine(engine);
				ValidationResult vr = commandItem.evaluate(rule.get(cmd), object);
				result.addResult(vr);
				rBool = rBool && vr.isValid();
			}
		}
		result.setValid(!rBool);
		result.setComment(comment);
		result.setDescription(description);

		return result;
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// Not used
	}

}
