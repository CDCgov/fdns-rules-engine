package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.CompoundValidationResult;
import gov.cdc.engine.result.ValidationResult;

public class CommentTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testExists() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$comment/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$comment/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);

		if (vr instanceof CompoundValidationResult) {
			CompoundValidationResult cvr = (CompoundValidationResult) vr;
			List<ValidationResult> vrs = cvr.flatten();
			int i = 1;
			System.out.println("----------- FLATTEN RESULT -----------");
			for (ValidationResult singleResult : vrs) {
				String header = String.format("%d / %d", i, vrs.size());
				System.out.println(String.format("----------- %s -----------", StringUtils.center(header, 14)));
				System.out.println(singleResult);
				i++;
			}
		}

		assertEquals(vr.isValid(), true);
	}

}
