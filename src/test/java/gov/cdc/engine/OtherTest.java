package gov.cdc.engine;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.cdc.engine.result.ValidationResult;

public class OtherTest {

	private static Validator v;

	@BeforeClass
	public static void initialize() {
		v = new SimpleValidator();
	}

	@Test
	public void testOther1() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/other/objects/001.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/other/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testOther2() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/other/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/other/rules/001.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), false);
		System.out.println(vr);
	}
	
	@Test
	public void testOther3() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/other/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/other/rules/002.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		assertEquals(vr.isValid(), true);
		System.out.println(vr);
	}
	
	@Test
	public void testOther4() throws ValidatorException {
		InputStream object = getClass().getClassLoader().getResourceAsStream("junit/other/objects/002.json");
		InputStream rules = getClass().getClassLoader().getResourceAsStream("junit/other/rules/003.json");
		v.initialize(rules);
		ValidationResult vr = v.validate(object);
		System.out.println(vr);
		assertEquals(vr.isValid(), true);
	}

}
