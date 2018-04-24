package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class InCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testIn1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$in/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$in/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testIn2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$in/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$in/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testIn3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$in/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$in/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testIn4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$in/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$in/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testIn5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$in/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$in/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
