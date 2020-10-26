/**
 * 
 */
package main.handler;

import main.constant.Constants;
import main.exception.ErrorCode;
import main.exception.ParkingException;
import main.models.Car;
import main.services.ParkingService;
import main.services.ParkingServiceImpl;

/**
 * @author ajipramono
 *
 */
public class InputHandler implements AbstractHandler{
	private ParkingService service = new ParkingServiceImpl();
	
	public void handle(String input) throws ParkingException {
		String[] inputs = input.split(" ");
		String key = inputs[0];
		switch(key) {
		    case Constants.CREATE :
		    	try {
		    		service.createParkingLot(Integer.parseInt(inputs[1]));
		    	}
		    	catch(NumberFormatException e) {
		    		throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
		    	}
			    break;
		    case Constants.PARK:
		    	service.park(new Car(inputs[0], inputs[1]));
			    break;
		    case Constants.LEAVE:
		    	try {
		    		service.unPark(Integer.parseInt(inputs[1]));
		    	}
		    	catch(NumberFormatException e) {
		    		throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
		    	}
		    	break;
		    case Constants.STATUS:
		    	service.getParkingStatus();
		    	break;
		    case Constants.REG_NUMBERS_BY_COLOR:
		    	service.getRegNumsFromColor(inputs[1]);
		    	break;
		    case Constants.SLOTS_NUMBER_BY_COLOR:
		    	service.getSlotNumsFromColor(inputs[1]);
		    	break;
		    case Constants.SLOT_NUMBER_BY_REG_NUMBER:
		    	service.getSlotNumFromRegNum(inputs[1]);
		    	break;
		    default:
		    	break;
		}
	}
}
