package gov.cdc.engine.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import gov.cdc.engine.Validator;
import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.ValidationResult;
import net.minidev.json.JSONArray;

/**
 * 
 * Abstract class representing a command.
 * 
 * @author ben.chevallereau
 *
 */
public abstract class AbstractCommand implements Command {

	protected Validator engine;

	private static final Logger logger = Logger.getLogger(AbstractCommand.class);

	public static final String COMMENT_KEYWORD = "$comment";
	public static final String DESCRIPTION_KEYWORD = "$description";

	public void setEngine(Validator validator) {
		this.engine = validator;
	}

	public ValidationResult evaluate(Object ruleObj, JSONObject object) throws ValidatorException {

		logger.debug("Evaluating " + ruleObj + " on " + object);

		checkRule(ruleObj);

		ValidationResult vr = evaluateImpl(ruleObj, object);

		logger.debug("Is Valid? " + vr.isValid());

		return vr;
	}

	protected abstract ValidationResult evaluateImpl(Object ruleObj, JSONObject object) throws ValidatorException;

	/**
	 * Transforms a map to a JSON object
	 * 
	 * @param map
	 *            Map extracted via JSONPath
	 * @return A JSON Object
	 */
	protected JSONObject transform(Map<?, ?> map) {
		JSONObject json = new JSONObject();
		for (Entry<?, ?> entry : map.entrySet()) {
			if (entry.getKey() instanceof String) {
				if (entry.getValue() == null)
					json.put((String) entry.getKey(), JSONObject.NULL);
				else
					json.put((String) entry.getKey(), entry.getValue());
			}
		}
		return json;
	}

	protected List<ValidationResult> recurse(Object subrule, Object value) throws ValidatorException {

		List<ValidationResult> vrs = new ArrayList<ValidationResult>();

		// If the value is an object
		if (value instanceof JSONObject)
			// Evaluate the subrule on the retrieved object
			vrs.add(evaluate(subrule, (JSONObject) value));
		else if (value instanceof net.minidev.json.JSONArray) {
			// If the extracted value is an array
			net.minidev.json.JSONArray values = (net.minidev.json.JSONArray) value;
			for (int i = 0; i < values.size(); i++) {
				// Check all values inside the array
				Object subObject = values.get(i);
				// If the value is an object
				if (subObject instanceof net.minidev.json.JSONObject)
					// Evaluate the subrule on the retrieved object
					vrs.add(evaluate(subrule, new JSONObject(subObject.toString())));
				else if (subObject instanceof Map)
					// If the value is a map
					// Evaluate the subrule on the object created from the map
					vrs.add(evaluate(subrule, transform((Map<?, ?>) subObject)));
				else
					throw new ValidatorException("Can't reapply the rule on the following object: " + subObject);
			}
		} else
			throw new ValidatorException("Can't reapply the rule on the following object: " + value);

		return vrs;
	}

	protected boolean isBasicType(Object value) {
		boolean isNumber = (value instanceof Integer) || (value instanceof Double) || (value instanceof Float);
		return isNumber || (value instanceof String) || (value instanceof Boolean);
	}

	protected boolean isArray(Object value) {
		return value instanceof JSONArray;
	}

	protected boolean isArrayOfArray(Object value) {
		if (!isArray(value))
			return false;
		JSONArray array = (JSONArray) value;
		if (array.isEmpty())
			return false;
		boolean result = true;
		for (Object subItem : array) {
			result = result && isArray(subItem);
		}
		return result;
	}

	protected abstract void checkRule(Object rule) throws ValidatorException;

}
