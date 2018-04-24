package gov.cdc.engine.commands;

/**
 * $gte validates those objects where the value of the field is greater than or
 * equal to (i.e. >=) the specified value.
 * 
 * @author Ben Chevallereau
 *
 */
public class GteCommand extends ComparisonCommand {

	public String getKeyword() {
		return "$gte";
	}

	@Override
	protected boolean isValid(int comparisonResult) {
		return comparisonResult >= 0;
	}

}
