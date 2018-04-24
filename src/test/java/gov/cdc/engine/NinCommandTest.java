package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class NinCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testNin1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nin/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nin/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNin2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nin/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nin/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testNin3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nin/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nin/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testNin4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nin/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nin/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testNin5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$nin/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$nin/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
