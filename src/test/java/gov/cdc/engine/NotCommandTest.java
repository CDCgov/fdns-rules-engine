package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class NotCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNot1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$not/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$not/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testNot2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$not/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$not/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test
	public void testNot3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$not/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$not/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testNot4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$not/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$not/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
