package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * 
 * Abstract class representing a comparison command.
 * 
 * @author Ben Chevallereau
 *
 */
public abstract class ComparisonCommand extends SingleCommand {

	@Override
	protected void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException {
		Object match = rule.get(jsonPath);
		if (match == null)
			throw new ValidatorException("A value to match has to be provided for the JSON path.");
		result.addResult(isValid(match, value, rule));
	}

	protected int compareTo(Object a, Object b) throws ValidatorException {
		if (!a.getClass().equals(b.getClass()))
			throw new ValidatorException("The two objects need to be of the same type.");
		if (!(a instanceof Comparable))
			throw new ValidatorException("The objects don't implement the interface Comparable.");
		if (a instanceof Integer) {
			return ((Integer) a).compareTo((Integer) b);
		} else if (a instanceof Double) {
			return ((Double) a).compareTo((Double) b);
		} else if (a instanceof Float) {
			return ((Float) a).compareTo((Float) b);
		} else if (a instanceof String) {
			return ((String) a).compareTo((String) b);
		} else if (a instanceof Boolean) {
			return ((Boolean) a).compareTo((Boolean) b);
		}
		throw new ValidatorException("Impossible to compare: `" + a + "` and `" + b + "`");
	}

	private ValidationResult isValid(Object match, Object value, JSONObject rule) throws ValidatorException {
		CompoundValidationResult result = new CompoundValidationResult(rule.toString(), value != null ? value.toString() : null, this);
		if (!isBasicType(match)) {
			handleNonBasicValue(value, result, match);
		} else if (value != null && value instanceof JSONArray) {
			handleArrayOfValues((JSONArray) value, result, match, rule);
		} else if (value == null || !isBasicType(value)) {
			throw new ValidatorException("Can't compare with the following value: " + match);
		} else if (!match.getClass().equals(value.getClass())) {
			throw new ValidatorException("The match and the value don't have the same time: " + match.getClass() + "; " + value.getClass());
		} else if (!(value instanceof Comparable<?>)) {
			throw new ValidatorException("The value class don't implement the interface Comparable: " + value.getClass());
		} else if (value instanceof Comparable<?>) {
			result.addResult(ValidationResult.create(isValid(compareTo(value, match)), rule.toString(), value.toString(), this));
		} else
			throw new ValidatorException("Don't understand the following parameter: " + match);
		return result;
	}

	private void handleArrayOfValues(JSONArray arrayOfValues, CompoundValidationResult result, Object match, JSONObject rule) throws ValidatorException {
		for (Object uniqueValue : arrayOfValues) {
			result.addResult(ValidationResult.create(isValid(compareTo(uniqueValue, match)), rule.toString(), uniqueValue != null ? uniqueValue.toString() : null, this));
		}
	}

	private void handleNonBasicValue(Object value, CompoundValidationResult result, Object match) throws ValidatorException {
		if (match instanceof JSONObject) {
			// If the parameter is a sub-rule
			JSONObject subrule = (JSONObject) match;
			// Recurse
			result.addAllResults(recurse(subrule, value));
		} else
			throw new ValidatorException("Can't compare with the following match: " + match);
	}

	abstract boolean isValid(int comparisonResult);
}
