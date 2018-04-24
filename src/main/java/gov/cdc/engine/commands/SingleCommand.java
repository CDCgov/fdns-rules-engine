package gov.cdc.engine.commands;

import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * 
 * Class representing an unitary command
 * 
 * @author ben.chevallereau
 *
 */
public abstract class SingleCommand extends AbstractCommand {

	private static final Logger logger = Logger.getLogger(SingleCommand.class);

	private String comment;
	private String description;

	@Override
	protected void checkRule(Object rule) throws ValidatorException {
		if (!(rule instanceof JSONObject))
			throw new ValidatorException("The rule parameter has to be a JSON Object.");
	}

	@Override
	protected ValidationResult evaluateImpl(Object ruleObj, JSONObject object) throws ValidatorException {

		JSONObject rule = (JSONObject) ruleObj;
		// Get keys
		Set<String> keys = rule.keySet();
		logger.debug("# of keys " + keys.size());

		if (keys.contains(AbstractCommand.COMMENT_KEYWORD)) {
			comment = rule.getString(AbstractCommand.COMMENT_KEYWORD);
			logger.debug("Comment = '" + comment + "'");
		}
		if (keys.contains(AbstractCommand.DESCRIPTION_KEYWORD)) {
			description = rule.getString(AbstractCommand.DESCRIPTION_KEYWORD);
			logger.debug("Description = '" + description + "'");
		}

		CompoundValidationResult result = new CompoundValidationResult(ruleObj.toString(), object != null ? object.toString() : null, this);

		// For each JSON path expression
		for (String key : keys) {
			if (!(AbstractCommand.COMMENT_KEYWORD.equalsIgnoreCase(key)) && !(AbstractCommand.DESCRIPTION_KEYWORD.equalsIgnoreCase(key))) {
				checkJSONPath(key, object, rule, result);
			}
		}

		return result;
	}

	private void checkJSONPath(String key, JSONObject object, JSONObject rule, CompoundValidationResult result) throws ValidatorException {
		// Try to get the value
		Object value = null;
		boolean found = true;
		try {
			if (object != null)
				value = JsonPath.read(object.toString(), key);
			else
				found = false;
		} catch (Exception e) {
			logger.debug(e);
			found = false;
		}

		logger.debug("JSONPath = '" + key + "'; Value = '" + value + "'");
		isValid(purify(rule, key), key, value, found, result);
	}

	protected abstract void isValid(JSONObject rule, String jsonPath, Object value, boolean found, CompoundValidationResult result) throws ValidatorException;

	/**
	 * Return a JSON object with a unique key
	 * 
	 * @param rule
	 *            The original JSON object
	 * @param key
	 *            The only key to keep
	 * @return A JSON object with one single field
	 */
	private JSONObject purify(JSONObject rule, String key) {
		JSONObject purifiedVersion = new JSONObject();
		purifiedVersion.put(key, rule.get(key));
		return purifiedVersion;
	}

	public String getComment() {
		return comment;
	}

	public String getDescription() {
		return description;
	}

}
