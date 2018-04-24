package gov.cdc.engine.commands;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $noDuplicate operator validates any array that contains only unique
 * values.
 * 
 * @author Ben Chevallereau
 *
 */
public class NoDuplicateCommand extends SingleCommand {

	public String getKeyword() {
		return "$noDuplicate";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof Boolean) {
			Boolean parameter = (Boolean) rule.get(jsonPath);

			boolean valid = false;
			if (value instanceof net.minidev.json.JSONArray) {
				net.minidev.json.JSONArray v = (net.minidev.json.JSONArray) value;
				Set<Object> foundValues = new HashSet<Object>();
				for (int i = 0; i < v.size(); i++)
					foundValues.add(v.get(i));
				valid = foundValues.size() == v.size();
			} else
				throw new ValidatorException("The retrieved value is not a JSON array: " + value);

			if (!parameter)
				valid = !valid;

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
