package gov.cdc.engine.commands;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $in operator validates the objects where the value of a field equals any
 * value in the specified array.
 * 
 * @author Ben Chevallereau
 *
 */
public class InCommand extends SingleCommand {

	public String getKeyword() {
		return "$in";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONArray) {
			handleArray(value, rule, jsonPath, result);
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}

	private void handleArray(Object value, JSONObject rule, String jsonPath, CompoundValidationResult result) throws ValidatorException {
		JSONArray allowedValuesArr = (JSONArray) rule.get(jsonPath);
		List<Object> allowedValues = new ArrayList<Object>();
		for (int i = 0; i < allowedValuesArr.length(); i++)
			allowedValues.add(allowedValuesArr.get(i));

		// Look for the null value
		boolean valid;
		if (value == null) {
			valid = allowedValues.contains(JSONObject.NULL);
		} else {
			if (value instanceof net.minidev.json.JSONArray) {
				valid = handleArrayOfArray((net.minidev.json.JSONArray) value, allowedValues);
			} else {
				if (!isBasicType(value))
					throw new ValidatorException("The retrieved value is not a basic valid type: " + value.getClass());
				if (!(value instanceof Comparable))
					throw new ValidatorException("The retrieved value doesn't implement the interface comparable: " + value.getClass());
				valid = allowedValues.contains(value);
			}
		}
		result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
	}

	private boolean handleArrayOfArray(net.minidev.json.JSONArray arrayOfValues, List<Object> allowedValues) throws ValidatorException {
		boolean valid = true;
		// If the value is an array, check each value
		for (Object uniqueValue : arrayOfValues) {
			valid = valid && allowedValues.contains(uniqueValue);
		}
		return valid;
	}

}
