package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class OrCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testOr1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$or/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$or/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testOr2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$or/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$or/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testOr3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$or/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$or/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test
	public void testOr4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$or/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$or/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testOrError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$or/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$or/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}

}
