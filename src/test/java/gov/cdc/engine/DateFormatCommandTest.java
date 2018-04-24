package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class DateFormatCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}
	
	@Test
	public void test1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), true);
	}

	@Test
	public void test2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), false);
	}
	
	@Test
	public void test3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/003.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), false);
	}
	
	@Test
	public void test4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/004.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), false);
	}
	
	@Test
	public void test5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/005.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), true);
	}

	@Test(expected = ValidatorException.class)
	public void testInvalid1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$dateFormat/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}
}
