package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class ExistsCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testExists() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNotExists() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test
	public void testArray1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test
	public void testArray2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testArray3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test(expected = ValidatorException.class)
	public void testExistsError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$exists/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$exists/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}

}
