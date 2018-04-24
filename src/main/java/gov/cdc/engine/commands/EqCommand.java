package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * The $eq operator validates objects where the value of a field equals the
 * specified value.
 * 
 * @author Ben Chevallereau
 *
 */
public class EqCommand extends SingleCommand {

	public String getKeyword() {
		return "$eq";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If we want to check an equality with Null
		// And we found the field
		if (rule.get(jsonPath) == null || JSONObject.NULL.equals(rule.get(jsonPath))) {
			if (value == null && found)
				result.addResult(ValidationResult.create(true, rule.toString(), null, this));
			else
				result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (value != null && isBasicType(value)) {
			// If the found value is a basic type
			// The rule is valid if the 2 values are equal
			boolean valid = value.equals(rule.get(jsonPath));
			result.addResult(ValidationResult.create(valid, rule.toString(), value.toString(), this));
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else if (value instanceof JSONArray) {
			handleArray(value, rule, jsonPath, result);
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}
	
	private void handleArray(Object value, JSONObject rule, String jsonPath, CompoundValidationResult result) {
		// If the value is an array, check each value
		JSONArray arrayOfValues = (JSONArray) value;
		boolean valid = true;
		for (Object uniqueValue : arrayOfValues) {
			valid = valid && uniqueValue.equals(rule.get(jsonPath));
		}
		result.addResult(ValidationResult.create(valid, rule.toString(), value.toString(), this));
	}
	

}
