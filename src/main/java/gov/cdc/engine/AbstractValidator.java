package gov.cdc.engine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import gov.cdc.engine.commands.Command;
import gov.cdc.engine.result.ValidationResult;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public abstract class AbstractValidator implements Validator {

	private static final Logger logger = Logger.getLogger(AbstractValidator.class);

	protected Map<String, Command> library;

	public void initialize(InputStream rules) throws ValidatorException {
		String rulesStr;
		try {
			rulesStr = IOUtils.toString(rules, Charsets.UTF_8);
		} catch (IOException e) {
			throw new ValidatorException(e);
		}
		Object obj = new JSONTokener(rulesStr).nextValue();
		if (obj instanceof JSONObject) {
			initialize((JSONObject) obj);
		} else
			throw new ValidatorException("The rules needs to be specified as a JSON object.");
	}

	public ValidationResult validate(InputStream object) throws ValidatorException {
		String rulesStr;
		try {
			rulesStr = IOUtils.toString(object, Charsets.UTF_8);
		} catch (IOException e) {
			throw new ValidatorException(e);
		}
		Object obj = new JSONTokener(rulesStr).nextValue();
		if (obj instanceof JSONObject) {
			return validate((JSONObject) obj);
		} else
			throw new ValidatorException("The object needs to be a JSON object.");
	}

	protected void loadLibrary() throws InstantiationException, IllegalAccessException {
		logger.debug("Loading command library...");

		library = new HashMap<String, Command>();

		FastClasspathScanner scanner = new FastClasspathScanner();
		ScanResult scanResult = scanner.scan();
		List<String> classNames = scanResult.getNamesOfClassesImplementing(Command.class);
		logger.debug("# of commands: " + classNames.size());

		List<Class<?>> subClasses = scanResult.classNamesToClassRefs(classNames);
		for (Class<?> _class : subClasses) {
			if (!Modifier.isAbstract(_class.getModifiers())) {
				Command cmd = (Command) _class.newInstance();
				library.put(cmd.getKeyword(), cmd);
				logger.debug("  " + _class + " : " + cmd.getKeyword());
			}
		}

	}

}
