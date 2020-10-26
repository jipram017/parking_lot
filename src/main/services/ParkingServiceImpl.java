/**
 * 
 */
package main.services;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import main.constant.Constants;
import main.dao.ParkingManager;
import main.dao.ParkingManagerImpl;
import main.models.Allocation;
import main.models.AllocationImpl;
import main.models.Vehicle;

/**
 * @author ajipramono
 *
 */
public class ParkingServiceImpl implements ParkingService {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ParkingManager<Vehicle> manager = null;
	
	@Override
	public void createParkingLot(int capacity) {
		if(manager != null) {
			System.out.println("Parking lot already exist");
		}
		Allocation allot = new AllocationImpl();
		this.manager = ParkingManagerImpl.getInstance(capacity, allot);
		System.out.println("Succesfully created parking lot with " + capacity + " slots");
	}

	@Override
	public Optional<Integer> park(Vehicle vehicle) {
		Optional<Integer> value = Optional.empty();
		lock.writeLock().lock();
		try {
			value = Optional.of(manager.parkCar(vehicle));
			if(value.get() == Constants.NOT_AVAILABLE) {
				System.out.println("Sorry, parking lot is full");
			} else if(value.get() == Constants.ALREADY_EXIST) {
				System.out.println("Sorry, vehicle is already parked");
			} else {
				System.out.println("Allocated slot number is: " + value.get());
			}
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}

	@Override
	public void unPark(int slot) {
		lock.writeLock().lock();
		try {
			if(manager.unParkCar(slot)) {
				System.out.println("Slot number " + slot + " is free");
			} else {
				System.out.println("Slot " + slot + " is already empty");
			}
		}
		catch(Exception e){
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Optional<Integer> getAvailableSlot() {
		lock.writeLock().lock();
		Optional<Integer> value = Optional.empty();
		try {
			value = Optional.of(manager.getAvailableSlot());
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}

	@Override
	public void getParkingStatus() {
		lock.writeLock().lock();
		try {
			System.out.println("Slot No.\tRegistration No.\tColor");
			List<String> statusList = manager.getParkingStatus();
			if(statusList.size() == 0) {
				System.out.println("Sorry parking lot is empty");
			} else {
				for(String status : statusList) {
					System.out.println(status);
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void getRegNumsFromColor(String color) {
		lock.readLock().lock();
		try {
			List<String> regNums = manager.getRegNumsFromColor(color);
			if(regNums.size() == 0) {
				System.out.println("Not found");
			} else {
				System.out.println(String.join(",", regNums));
			}
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void getSlotNumsFromColor(String color) {
		lock.readLock().lock();
		try {
			List<Integer> slotNums = manager.getSlotNumsFromColor(color);
			if(slotNums.size() == 0) {
				System.out.println("Not found");
			} else {
				StringJoiner joiner = new StringJoiner(",");
				for (Integer slot : slotNums)
				{
					joiner.add(slot + "");
				}
				System.out.println(joiner.toString());
			}
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public int getSlotNumFromRegNum(String regNum) {
		int value = -1;
		lock.writeLock().lock();
		try {
			value = manager.getSlotNumFromRegNum(regNum);
			System.out.println(value == -1? "Not found" : value);
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}

}
