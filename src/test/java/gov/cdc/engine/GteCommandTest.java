package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class GteCommandTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testGte1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gte/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gte/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testGte2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gte/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gte/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

	@Test
	public void testGte3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gte/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gte/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testGte4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gte/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gte/rules/004.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testGte5() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/$gte/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/$gte/rules/005.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}

}
