package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class EqCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testEq1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testEq2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testEq3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testEq4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testEq5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testEqError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$eq/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$eq/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}

}
