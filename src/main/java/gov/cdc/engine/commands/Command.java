package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.Validator;
import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.ValidationResult;

/**
 * Interface representing a command.
 * 
 * @author Ben Chevallereau
 *
 */
public interface Command {

	/**
	 * Evaluate a rule against an object
	 * 
	 * @param rule
	 *            Object representing the rule
	 * @param object
	 *            JSON Object to evaluate
	 * @return Validation Result
	 * @throws Exception
	 */
	public ValidationResult evaluate(Object rule, JSONObject object) throws ValidatorException;

	/**
	 * Get the keyword representing the command
	 * 
	 * @return Command keyword
	 */
	public String getKeyword();

	/**
	 * Set the current validator
	 * 
	 * @param validator
	 */
	public void setEngine(Validator validator);


}
