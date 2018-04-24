package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class LtCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testLt1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$lt/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$lt/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testLt2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$lt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$lt/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testLt3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$lt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$lt/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testLt4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$lt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$lt/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testLt5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$lt/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$lt/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
