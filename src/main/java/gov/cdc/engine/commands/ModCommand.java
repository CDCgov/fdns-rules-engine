package gov.cdc.engine.commands;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * Validate objects where the value of a field divided by a divisor has the
 * specified remainder (i.e. perform a modulo operation to validate objects).
 * 
 * @author Ben Chevallereau
 *
 */
public class ModCommand extends SingleCommand {

	public String getKeyword() {
		return "$mod";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (found) {
			if (rule.get(jsonPath) instanceof JSONArray) {
				boolean valid = isValid((JSONArray) rule.get(jsonPath), value);
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

	private boolean isValid(JSONArray parameters, Object value) throws ValidatorException {
		if (parameters.length() != 2)
			throw new ValidatorException("The JSON array must contain exactly 2 integers: " + parameters);
		if (!(parameters.get(0) instanceof Integer))
			throw new ValidatorException("The modulo value must be an integer: " + parameters.get(0));
		if (!(parameters.get(1) instanceof Integer))
			throw new ValidatorException("The remainder value must be an integer: " + parameters.get(1));

		if (value instanceof net.minidev.json.JSONArray) {
			net.minidev.json.JSONArray arrayOfValues = (net.minidev.json.JSONArray) value;
			boolean valid = true;
			for (Object uniqueValue : arrayOfValues) {
				valid = valid && isValid(parameters.getInt(0), parameters.getInt(1), uniqueValue);
			}
			return valid;
		} else
			return isValid(parameters.getInt(0), parameters.getInt(1), value);
	}

	private boolean isValid(int mod, int remainder, Object value) throws ValidatorException {
		boolean valid = false;
		if (value != null) {
			if (!(value instanceof Integer))
				throw new ValidatorException("The found value must be an integer: " + value);
			int v = (Integer) value;
			valid = (v % mod) == remainder;
		}
		return valid;
	}

}
