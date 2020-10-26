/**
 * 
 */
package main.services;

import java.util.Optional;

import main.exception.ParkingException;
import main.models.Vehicle;

/**
 * @author ajipramono
 *
 */
public interface ParkingService {
	public void createParkingLot(int capacity) throws ParkingException;
	public Optional<Integer> park(Vehicle vehicle) throws ParkingException;
	public void unPark (int slot) throws ParkingException;
	public Optional<Integer> getAvailableSlot() throws ParkingException;
	public void getParkingStatus() throws ParkingException;
	public void getRegNumsFromColor(String color) throws ParkingException;
	public int getSlotNumFromRegNum(String regNum) throws ParkingException;
	public void getSlotNumsFromColor(String color) throws ParkingException;
	public void doCleanup() throws ParkingException;
}
