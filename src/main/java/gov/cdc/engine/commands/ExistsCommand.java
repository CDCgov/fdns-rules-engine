package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * The $exists command validates object that contain the field, including object
 * where the field value is null.
 * 
 * @author Ben Chevallereau
 *
 */
public class ExistsCommand extends SingleCommand {

	public String getKeyword() {
		return "$exists";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the parameter is a boolean
		if (rule.get(jsonPath) instanceof Boolean) {
			boolean valid = value != null;
			if (value != null && value instanceof JSONArray)
				valid = !((JSONArray) value).isEmpty();
			// Just check the existence of the field...
			if (!rule.getBoolean(jsonPath))
				valid = !valid;
			// ... And create the result
			result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);

			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}

}
