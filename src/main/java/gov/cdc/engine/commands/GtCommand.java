package gov.cdc.engine.commands;

/**
 * $gt validates those objects where the value of the field is greater than
 * (i.e. >) the specified value.
 * 
 * @author Ben Chevallereau
 *
 */
public class GtCommand extends ComparisonCommand {

	public String getKeyword() {
		return "$gt";
	}

	@Override
	protected boolean isValid(int comparisonResult) {
		return comparisonResult > 0;
	}

}
