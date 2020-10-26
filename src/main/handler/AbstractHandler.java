/**
 * 
 */
package main.handler;

import main.constant.InputConstants;

/**
 * @author ajipramono
 *
 */
public interface AbstractHandler {
	public default boolean validate(String str) {
		boolean valid = true;
		try {
			String[] inputs = str.split(" ");
			int params = InputConstants.getcommandsMap().get(inputs[0]);
			switch(inputs.length) {
			    case 1:
			    	if (params != 0) // e.g status -> inputs = 1
					   valid = false;
				    break;
			    case 2:
				    if (params != 1) // create_parking_lot 6 -> inputs = 2
					   valid = false;
				    break;
			    case 3:
				    if (params != 2) // park KA-01-P-333 White -> inputs = 3
					   valid = false;
				    break;
			    default:
				    valid = false; 	
			}
		}
		catch (Exception e) {
			valid = false;
		}
		return valid;
	}
}
