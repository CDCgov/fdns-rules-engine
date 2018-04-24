package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class NoDuplicateCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNoDuplicate1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNoDuplicate2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNoDuplicate3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/objects/003.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$noDuplicate/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
}
