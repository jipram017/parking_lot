/**
 * 
 */
package main.dao;

import java.util.List;

import main.models.Vehicle;

/**
 * @author ajipramono
 *
 */
public interface ParkingDataManager<T extends Vehicle> {
	public int parkCar(T Vehicle);
	public boolean unParkCar(T Vehicle, String slot);
	public List<String> getRegNumsFromColor(String color);
	public List<Integer> getSlotNumsFromColor(String color);
	public int getSlotNumFromRegNum(String regNum);
	public int getAvailableSlot();
}
