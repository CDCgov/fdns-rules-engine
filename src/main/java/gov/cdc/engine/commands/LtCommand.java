package gov.cdc.engine.commands;

/**
 * $lt validates the objects where the value of the field is less than (i.e. <)
 * the specified value.
 * 
 * @author Ben Chevallereau
 *
 */
public class LtCommand extends ComparisonCommand {

	public String getKeyword() {
		return "$lt";
	}

	@Override
	protected boolean isValid(int comparisonResult) {
		return comparisonResult < 0;
	}

}
