package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * The $size operator validates any array with the number of elements specified
 * by the argument.
 * 
 * @author Ben Chevallereau
 *
 */
public class SizeCommand extends SingleCommand {

	public String getKeyword() {
		return "$size";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof Integer) {
			int size = (Integer) rule.get(jsonPath);

			boolean valid;
			if (isArrayOfArray(value)) {
				valid = handleArrayOfArray((JSONArray) value, size);
			} else if (value instanceof net.minidev.json.JSONArray) {
				net.minidev.json.JSONArray v = (net.minidev.json.JSONArray) value;
				valid = v.size() == size;
			} else
				throw new ValidatorException("The retrieved value is not a JSON array: " + value);
			result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}
	
	private boolean handleArrayOfArray(net.minidev.json.JSONArray value, int size) {
		boolean valid = true;
		for (Object subArray : value) {
			valid = valid && ((net.minidev.json.JSONArray) subArray).size() == size;
		}
		return valid;
	}

}
