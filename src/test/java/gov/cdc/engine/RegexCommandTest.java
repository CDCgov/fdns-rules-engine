package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class RegexCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testRegex1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$regex/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$regex/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testRegex2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$regex/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$regex/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testRegex3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$regex/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$regex/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testRegex4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$regex/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$regex/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
}
