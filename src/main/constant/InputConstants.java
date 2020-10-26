/**
 * 
 */
package main.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ajipramono
 *
 */
public class InputConstants {
	private static volatile Map<String, Integer> commandsMap = new HashMap<String, Integer>();
	
	static
	{
		commandsMap.put(Constants.CREATE, 1);
		commandsMap.put(Constants.PARK, 2);
		commandsMap.put(Constants.LEAVE, 1);
		commandsMap.put(Constants.STATUS, 0);
		commandsMap.put(Constants.REG_NUMBERS_BY_COLOR, 1);
		commandsMap.put(Constants.SLOTS_NUMBER_BY_COLOR, 1);
		commandsMap.put(Constants.SLOT_NUMBER_BY_REG_NUMBER, 1);
	}
	
	public static Map<String, Integer> getcommandsMap()
	{
		return commandsMap;
	}
	
	public static void addCommand(String command, int count)
	{
		commandsMap.put(command, count);
	}

}
