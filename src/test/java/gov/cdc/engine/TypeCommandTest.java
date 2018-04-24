package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class TypeCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testType() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testArray() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testSubObject() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNull1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/003.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNull2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/003.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}

	@Test(expected = ValidatorException.class)
	public void testTypeError1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/invalid/001.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test(expected = ValidatorException.class)
	public void testTypeError2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/invalid/002.json");
		v.initialize(rules);
		v.validate(object);
	}
	
	@Test
	public void testType2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$type/objects/004.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$type/rules/006.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
}
