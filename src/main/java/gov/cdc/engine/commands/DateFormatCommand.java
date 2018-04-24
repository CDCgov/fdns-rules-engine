package gov.cdc.engine.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.apache.log4j.Logger;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $emptyOrNull command validate objects where the value of the field
 * doesn't exist, is null or is an empty string
 * 
 * @author Ben Chevallereau
 *
 */
public class DateFormatCommand extends SingleCommand {
	
	private static final Logger logger = Logger.getLogger(DateFormatCommand.class);

	public String getKeyword() {
		return "$dateFormat";
	}

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		result.addAllResults(isValid(rule, jsonPath, value, found).getValidationResults());
	}

	private CompoundValidationResult isValid(JSONObject rule, String jsonPath, Object value, boolean found) throws ValidatorException {
		CompoundValidationResult result = new CompoundValidationResult(rule.toString(), value != null ? value.toString() : null, this);
		if (!found) {
			result.addResult(ValidationResult.create(false, rule.toString(), value != null ? value.toString() : null, this));
		} else if (rule.get(jsonPath) instanceof String) {
			// If the parameter is a string

			String expectedDateFormat = rule.getString(jsonPath);
			DateFormat formatter;
			try {
				formatter = new SimpleDateFormat(expectedDateFormat);
			} catch (Exception e) {
				throw new ValidatorException(e);
			}

			if (value instanceof net.minidev.json.JSONArray) {
				handleArrayOfArray((net.minidev.json.JSONArray) value, result, rule, formatter);
			} else {
				result.addResult(ValidationResult.create(isValid(formatter, value), rule.toString(), value != null ? value.toString() : null, this));
			}

		} else if (rule.get(jsonPath) instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = rule.getJSONObject(jsonPath);

			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + rule.get(jsonPath));
		return result;
	}

	private void handleArrayOfArray(net.minidev.json.JSONArray values, CompoundValidationResult result, JSONObject rule, DateFormat formatter) throws ValidatorException {
		// Check each item in the array
		for (int i = 0; i < values.size(); i++) {
			// ... And create the result
			result.addResult(ValidationResult.create(isValid(formatter, values.get(i)), rule.toString(), values.get(i) != null ? values.get(i).toString() : null, this));
		}
	}

	private boolean isValid(DateFormat formatter, Object value) {
		if (value instanceof String) {
			boolean valid = true;
			try {
				formatter.parse((String) value);
			} catch (Exception e) {
				logger.error("DateFormatCommand.isValid failed", e);
				valid = false;
			}
			return valid;
		} else
			return false;
	}

}
