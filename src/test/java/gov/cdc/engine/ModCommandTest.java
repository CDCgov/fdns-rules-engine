package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class ModCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testMod1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testMod2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testMod3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testMod4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testModError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testModError2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/invalid/002.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testModError3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$mod/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$mod/rules/invalid/003.json");
		v.initialize(rules);
		v.validate(object);
	}
}
