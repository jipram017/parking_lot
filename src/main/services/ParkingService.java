/**
 * 
 */
package main.services;

import java.util.Optional;

import main.models.Vehicle;

/**
 * @author ajipramono
 *
 */
public interface ParkingService {
	public void createParkingLot(int capacity);
	public Optional<Integer> park(Vehicle vehicle);
	public void unPark (int slot);
	public Optional<Integer> getAvailableSlot();
	public void getStatus();
	public int getRegNumFromColor(String color);
	public int getSlotNumFromRegNo(String regNo);
	public int getSlotNumFromColor(String color);
}
