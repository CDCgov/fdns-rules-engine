package gov.cdc.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNonInitializedValidator() throws ValidatorException {
		try {
			v.validate(new JSONObject());
			fail();
		} catch (ValidatorException e) {
			assertEquals("The validator has not been initialized.", e.getMessage()); 
		}
	}

}
