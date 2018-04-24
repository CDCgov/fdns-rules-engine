package gov.cdc.engine.commands;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * 
 * Class representing a compound command
 * 
 * @author ben.chevallereau
 *
 */
public abstract class CompoundCommand extends AbstractCommand {

	private static final Logger logger = Logger.getLogger(CompoundCommand.class);
	
	protected boolean globalValid;

	@Override
	protected void checkRule(Object rule) throws ValidatorException {
		if (!(rule instanceof JSONArray))
			throw new ValidatorException("The rule parameter has to be a JSON Array.");
	}

	@Override
	protected ValidationResult evaluateImpl(Object ruleObj, JSONObject object) throws ValidatorException {

		CompoundValidationResult result = new CompoundValidationResult(ruleObj.toString(), object != null ? object.toString() : null, this);

		JSONArray commands = (JSONArray) ruleObj;

		logger.debug("# of commands " + commands.length());

		if (commands.length() > 0) {
			initialize();
			for (int i = 0; i < commands.length(); i++) {
				Object command = commands.get(i);
				globalValid = append( execute(command, object, result));
			}
			result.setValid(globalValid);
		}

		return result;
	}

	protected abstract boolean execute(Object command, JSONObject object, CompoundValidationResult result) throws ValidatorException;

	protected abstract void initialize();
	
	protected abstract boolean append(boolean rBool);
	
}
