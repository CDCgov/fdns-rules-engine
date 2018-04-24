package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class GtCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testGt1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testGt2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testGt3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testGt4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testGt5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testGtError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testGtError2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/invalid/002.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testGtError3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/invalid/003.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testGtError4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gt/rules/invalid/004.json");
		v.initialize(rules);
		v.validate(object);
	}

}
