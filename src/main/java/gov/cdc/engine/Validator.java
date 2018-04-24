package gov.cdc.engine;

import java.io.InputStream;

import org.json.JSONObject;

import gov.cdc.engine.commands.Command;
import gov.cdc.engine.result.ValidationResult;

public interface Validator {

	public void initialize(InputStream rules) throws ValidatorException;

	public void initialize(JSONObject rules) throws ValidatorException;

	public ValidationResult validate(InputStream object) throws ValidatorException;

	public ValidationResult validate(JSONObject object) throws ValidatorException;
	
	public Command getCommand(String keyword) throws ValidatorException;

}
