package gov.cdc.engine.commands;

/**
 * $lte validates the objects where the value of the field is less than or equal
 * (i.e. <=) the specified value.
 * 
 * @author Ben Chevallereau
 *
 */
public class LteCommand extends ComparisonCommand {

	public String getKeyword() {
		return "$lte";
	}

	@Override
	protected boolean isValid(int comparisonResult) {
		return comparisonResult <= 0;
	}

}
