package gov.cdc.engine;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = 3638768641142369946L;

	public ValidatorException(String message) {
		super(message);
	}
	
	public ValidatorException(Exception e) {
		super(e);
	}
}
