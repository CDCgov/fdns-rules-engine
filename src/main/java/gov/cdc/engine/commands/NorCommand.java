package gov.cdc.engine.commands;

import org.json.JSONObject;

import gov.cdc.engine.ValidatorException;
import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

/**
 * $nor performs a logical NOR operation on an array of one or more business
 * rules and validates objects that fail all the rules in the array.
 * 
 * @author Ben Chevallereau
 *
 */
public class NorCommand extends OrCommand {

	@Override
	public String getKeyword() {
		return "$nor";
	}

	@Override
	protected ValidationResult evaluateImpl(Object ruleObj, JSONObject object) throws ValidatorException {
		ValidationResult vr = super.evaluateImpl(ruleObj, object);
		if (vr instanceof CompoundValidationResult) {
			CompoundValidationResult cvr = (CompoundValidationResult) vr;
			boolean v = false;
			for (ValidationResult vrItem : cvr.getValidationResults()) {
				v = v || vrItem.isValid();
			}
			vr.setValid(!v);
			return vr;
		} else {
			throw new ValidatorException("Should receive a CompoundValidationResult.");
		}
	}

}
