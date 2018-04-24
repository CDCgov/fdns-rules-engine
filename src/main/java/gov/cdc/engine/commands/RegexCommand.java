package gov.cdc.engine.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * Provides regular expression capabilities for pattern matching strings in
 * object validations.
 * 
 * @author Ben Chevallereau
 *
 */
public class RegexCommand extends SingleCommand {

	public String getKeyword() {
		return "$regex";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		// If the field doesn't exist in the object
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof String) {
			handleStringvalue(rule, jsonPath, value, result);
		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
	}
	
	private void handleStringvalue(JSONObject rule, String jsonPath, Object value, CompoundValidationResult result) throws ValidatorException {
		String regex = (String) rule.get(jsonPath);

		// Look for the null value
		boolean valid = false;
		if (value != null) {
			Pattern p = Pattern.compile(regex);

			if (value instanceof JSONArray) {
				valid = handleArray(value, p);
			} else {
				if (!(value instanceof String))
					throw new ValidatorException("The found value must be a string: " + value);
				String v = (String) value;
				Matcher m = p.matcher(v);
				valid = m.matches();
			}

		}
		result.addResult(ValidationResult.create(valid, rule.toString(), value != null ? value.toString() : null, this));
	}

	private boolean handleArray(Object value, Pattern p) {
		boolean valid = true;
		JSONArray arrayOfValues = (JSONArray) value;
		for (Object uniqueValue : arrayOfValues) {
			if (uniqueValue instanceof String) {
				String v = (String) uniqueValue;
				Matcher m = p.matcher(v);
				valid = valid && m.matches();
			} else
				valid = false;
		}
		return valid;
	}

}
