package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class AllCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testAll1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testAll2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testAll3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testAll4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testAll5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testAll6() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/006.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testAllError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testAllError2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$all/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$all/rules/invalid/002.json");
		v.initialize(rules);
		v.validate(object);
	}
}
