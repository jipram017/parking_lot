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
import main.exception.ErrorCode;
import main.exception.ParkingException;
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
	public void createParkingLot(int capacity) throws ParkingException{
		if(manager != null) {
			throw new ParkingException(ErrorCode.PARKING_ALREADY_EXIST.getMessage());
		}
		Allocation allot = new AllocationImpl();
		this.manager = ParkingManagerImpl.getInstance(capacity, allot);
		System.out.println("Created parking lot with " + capacity + " slots");
	}
	
	
	private void validateParkingLot() throws ParkingException
	{
		if (manager == null)
		{
			throw new ParkingException(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
		}
	}

	@Override
	public Optional<Integer> park(Vehicle vehicle) throws ParkingException{
		Optional<Integer> value = Optional.empty();
		lock.writeLock().lock();
		validateParkingLot();
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
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}

	@Override
	public void unPark(int slot) throws ParkingException{
		lock.writeLock().lock();
		validateParkingLot();
		try {
			if(manager.unParkCar(slot)) {
				System.out.println("Slot number " + slot + " is free");
			} else {
				System.out.println("Slot " + slot + " is already empty");
			}
		}
		catch(Exception e){
			throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Optional<Integer> getAvailableSlot() throws ParkingException{
		lock.writeLock().lock();
		validateParkingLot();
		Optional<Integer> value = Optional.empty();
		try {
			value = Optional.of(manager.getAvailableSlot());
		}
		catch(Exception e) {
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}

	@Override
	public void getParkingStatus() throws ParkingException {
		lock.writeLock().lock();
		validateParkingLot();
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
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void getRegNumsFromColor(String color) throws ParkingException{
		lock.readLock().lock();
		validateParkingLot();
		try {
			List<String> regNums = manager.getRegNumsFromColor(color);
			if(regNums.size() == 0) {
				System.out.println("Not found");
			} else {
				System.out.println(String.join(",", regNums));
			}
		}
		catch(Exception e) {
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void getSlotNumsFromColor(String color) throws ParkingException{
		lock.readLock().lock();
		validateParkingLot();
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
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public int getSlotNumFromRegNum(String regNum) throws ParkingException{
		int value = -1;
		lock.writeLock().lock();
		validateParkingLot();
		try {
			value = manager.getSlotNumFromRegNum(regNum);
			System.out.println(value == -1? "Not found" : value);
		}
		catch(Exception e) {
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
		return value;
	}
	
	@Override
	public void doCleanup()
	{
		if (manager != null)
			manager.doCleanup();
	}

}
