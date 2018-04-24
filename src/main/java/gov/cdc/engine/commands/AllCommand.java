package gov.cdc.engine.commands;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $all operator validates the objects where the value of a field is an
 * array that contains all the specified elements.
 * 
 * @author Ben Chevallereau
 *
 */
public class AllCommand extends SingleCommand {

	public String getKeyword() {
		return "$all";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONArray) {
			JSONArray requiredValuesArr = (JSONArray) rule.get(jsonPath);
			boolean valid = isValid(requiredValuesArr, value);
			result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}

	private boolean isValid(JSONArray requiredValuesArr, Object value) throws ValidatorException {
		List<Object> requiredValues = new ArrayList<Object>();
		for (int i = 0; i < requiredValuesArr.length(); i++)
			requiredValues.add(requiredValuesArr.get(i));

		// Look for the null value
		boolean valid = false;
		if (isArrayOfArray(value)) {
			net.minidev.json.JSONArray arrayOfValues = (net.minidev.json.JSONArray) value;
			valid = true;
			for (Object uniqueArray : arrayOfValues) {
				valid = valid && check(requiredValues, (net.minidev.json.JSONArray) uniqueArray);
			}
		} else if (value instanceof net.minidev.json.JSONArray) {
			valid = check(requiredValues, (net.minidev.json.JSONArray) value);
		}

		return valid;
	}
	
	private boolean check(List<Object> requiredValues, net.minidev.json.JSONArray v) {
		int nbOfMatch = 0;
		for (int i = 0; i < v.size(); i++)
			if (requiredValues.contains(v.get(i)))
				nbOfMatch++;
		return nbOfMatch == requiredValues.size();
	}

}
