package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * $ne validates the objects where the value of the field is not equal (i.e. !=)
 * to the specified value. This validates objects that do not contain the field.
 * 
 * @author Ben Chevallereau
 *
 */
public class NeCommand extends SingleCommand {

	public String getKeyword() {
		return "$ne";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		CompoundValidationResult cvr = isValid(rule, jsonPath, value, found);
		result.addAllResults(cvr.getValidationResults());
	}

	private CompoundValidationResult isValid(JSONObject rule, String jsonPath, Object value, boolean found) throws ValidatorException {
		CompoundValidationResult result = new CompoundValidationResult(rule.toString(), value != null ? value.toString() : null, this);
		// If we want to check a non-equality with Null
		if (rule.get(jsonPath) == null || JSONObject.NULL.equals(rule.get(jsonPath))) {
			handleNullValue(value, rule, found, result);
		} else if (value == null) {
			result.addResult(ValidationResult.create(true, rule.toString(), null, this));
		} else if (isBasicType(value)) {
			handleBasicValue(value, rule, jsonPath, result);
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			handleObject(value, rule, jsonPath, result);
		} else if (value instanceof JSONArray) {
			handleArray(value, rule, jsonPath, result);
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
		return result;
	}

	private void handleNullValue(Object value, JSONObject rule, boolean found, CompoundValidationResult result) {
		// And we found the field
		if (value == null && found)
			result.addResult(ValidationResult.create(false, rule.toString(), null, this));
		else
			result.addResult(ValidationResult.create(true, rule.toString(), value != null ? value.toString() : null, this));
	}

	private void handleBasicValue(Object value, JSONObject rule, String jsonPath, CompoundValidationResult result) {
		// If the found value is a basic type
		// The rule is valid if the 2 values are equal
		boolean valid = !value.equals(rule.get(jsonPath));
		result.addResult(ValidationResult.create(valid, rule.toString(), value.toString(), this));
	}

	private void handleArray(Object value, JSONObject rule, String jsonPath, CompoundValidationResult result) {
		// If the value is an array, check each value
		JSONArray arrayOfValues = (JSONArray) value;
		boolean valid = true;
		for (Object uniqueValue : arrayOfValues) {
			valid = valid && !uniqueValue.equals(rule.get(jsonPath));
		}
		result.addResult(ValidationResult.create(valid, rule.toString(), value.toString(), this));
	}

	private void handleObject(Object value, JSONObject rule, String jsonPath, CompoundValidationResult result) throws ValidatorException {
		// If the parameter is a sub-rule
		JSONObject subrule = rule.getJSONObject(jsonPath);
		// Recurse
		result.addAllResults(recurse(subrule, value));
	}

}
