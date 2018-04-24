package gov.cdc.engine.commands;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * The $type command validate objects where the value of the field is an
 * instance of the specified type.
 * 
 * @author Ben Chevallereau
 *
 */
public class TypeCommand extends SingleCommand {

	public String getKeyword() {
		return "$type";
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

			String expectedType = rule.getString(jsonPath);

			if ("Array".equalsIgnoreCase(expectedType) || !(value instanceof net.minidev.json.JSONArray)) {
				// ... And create the result
				result.addResult(ValidationResult.create(isValidObject(value, expectedType), rule.toString(), value != null ? value.toString() : null, this));
			} else if (value instanceof net.minidev.json.JSONArray) {
				handleArrayOfArray((net.minidev.json.JSONArray) value, result, rule, expectedType);
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

	private void handleArrayOfArray(net.minidev.json.JSONArray values, CompoundValidationResult result, JSONObject rule, String expectedType) throws ValidatorException {
		// Check each item in the array
		for (int i = 0; i < values.size(); i++)
			// ... And create the result
			result.addResult(ValidationResult.create(isValidObject(values.get(i), expectedType), rule.toString(), values.get(i) != null ? values.get(i).toString() : null, this));
	}

	private boolean isValidObject(Object value, String expectedType) throws ValidatorException {
		boolean valid;
		// Check the type
		if ("Number".equalsIgnoreCase(expectedType)) {
			valid = isValidNumber(value);
		} else if ("String".equalsIgnoreCase(expectedType)) {
			valid = isValidString(value);
		} else if ("Boolean".equalsIgnoreCase(expectedType)) {
			valid = isValidBoolean(value);
		} else if ("Array".equalsIgnoreCase(expectedType)) {
			valid = (value instanceof JSONArray) || (value instanceof net.minidev.json.JSONArray);
		} else if ("Object".equalsIgnoreCase(expectedType)) {
			valid = (value instanceof JSONObject) || (value instanceof net.minidev.json.JSONObject) || (value instanceof Map);
		} else if ("Null".equalsIgnoreCase(expectedType)) {
			valid = value == null;
		} else
			throw new ValidatorException("The following type is not valid: " + expectedType);
		return valid;
	}

	private boolean isValidNumber(Object value) {
		boolean valid;
		if (value instanceof net.minidev.json.JSONArray) {
			valid = true;
			net.minidev.json.JSONArray values = (net.minidev.json.JSONArray) value;
			for (int i = 0; i < values.size(); i++)
				valid = valid && ((values.get(i) instanceof Integer) || (values.get(i) instanceof Double) || (values.get(i) instanceof Float));
		} else
			valid = (value instanceof Integer) || (value instanceof Double) || (value instanceof Float);
		return valid;
	}

	private boolean isValidString(Object value) {
		boolean valid;
		if (value instanceof net.minidev.json.JSONArray) {
			valid = true;
			net.minidev.json.JSONArray values = (net.minidev.json.JSONArray) value;
			for (int i = 0; i < values.size(); i++)
				valid = valid && (values.get(i) instanceof String);
		} else
			valid = value instanceof String;
		return valid;
	}

	private boolean isValidBoolean(Object value) {
		boolean valid;
		if (value instanceof net.minidev.json.JSONArray) {
			valid = true;
			net.minidev.json.JSONArray values = (net.minidev.json.JSONArray) value;
			for (int i = 0; i < values.size(); i++)
				valid = valid && (values.get(i) instanceof Boolean);
		} else
			valid = value instanceof Boolean;
		return valid;
	}

}
