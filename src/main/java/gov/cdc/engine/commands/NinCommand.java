package gov.cdc.engine.commands;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * $nin validates the objects where: (a) the field value is not in the specified
 * array or (b) the field does not exist.
 * 
 * @author Ben Chevallereau
 *
 */
public class NinCommand extends SingleCommand {

	public String getKeyword() {
		return "$nin";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(true, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONArray) {
			isValid(rule, jsonPath, value, result);
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}

	private void isValid(JSONObject rule, String jsonPath, Object value, CompoundValidationResult result) throws ValidatorException {
		JSONArray forbiddenValuesArr = (JSONArray) rule.get(jsonPath);
		List<Object> forbiddenValues = new ArrayList<Object>();
		for (int i = 0; i < forbiddenValuesArr.length(); i++)
			forbiddenValues.add(forbiddenValuesArr.get(i));

		// Look for the null value
		boolean valid;
		if (value == null) {
			valid = !(forbiddenValues.contains(JSONObject.NULL));
		} else {
			if (value instanceof net.minidev.json.JSONArray) {
				valid = handleArrayOfArray((net.minidev.json.JSONArray) value, forbiddenValues);
			} else {
				if (!isBasicType(value))
					throw new ValidatorException("The retrieved value is not a basic valid type: " + value.getClass());
				if (!(value instanceof Comparable))
					throw new ValidatorException("The retrieved value doesn't implement the interface comparable: " + value.getClass());
				valid = !(forbiddenValues.contains(value));
			}
		}
		result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
	}

	private boolean handleArrayOfArray(net.minidev.json.JSONArray arrayOfValues, List<Object> forbiddenValues) {
		boolean valid = true;
		// If the value is an array, check each value
		for (Object uniqueValue : arrayOfValues) {
			valid = valid && !(forbiddenValues.contains(uniqueValue));
		}
		return valid;
	}

}
