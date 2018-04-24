package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class NorCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNor1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nor/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nor/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), false);
	}
	
	@Test
	public void testNor2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nor/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nor/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test
	public void testNor3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nor/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nor/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testNor4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nor/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nor/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
