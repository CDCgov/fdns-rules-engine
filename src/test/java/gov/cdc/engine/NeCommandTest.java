package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class NeCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNe1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$ne/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$ne/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNe2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$ne/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$ne/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNe3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$ne/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$ne/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testNe4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$ne/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$ne/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testNe5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$ne/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$ne/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
