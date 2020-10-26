/**
 * 
 */
package main.dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import main.models.Allocation;
import main.models.AllocationImpl;
import main.models.Vehicle;

/**
 * @author ajipramono
 *
 */
public class ParkingManagerImpl<T extends Vehicle> implements ParkingManager<T> {
	private AtomicInteger capacity = new AtomicInteger();
	private AtomicInteger availability = new AtomicInteger();
	private Allocation allocation;
	
	@SuppressWarnings("rawtypes")
	private static ParkingManagerImpl instance = null;
	
	// Instantiate a singleton object to make sure only one parking manager instance get instantiated
	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> ParkingManagerImpl<T> getInstance(int capacity, Allocation allocation){
		if(instance == null) {
			synchronized(ParkingManager.class) {
				if(instance == null) {
					instance = new ParkingManagerImpl<T>(capacity, allocation);
				}
			}
		}
		return instance;
	}
	/**
	 * 
	 */
	
	public ParkingManagerImpl(int capacity, Allocation allocation) {
		this.capacity.set(capacity);
		this.availability.set(capacity);
		this.allocation = allocation;
		if(allocation == null) {
			allocation = new AllocationImpl();
		}
		for(int i=0; i<capacity; i++) {
			allocation.addSlot(i);
		}
	}
	
	public ParkingManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int parkCar(T Vehicle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean unParkCar(T Vehicle, String slot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRegNumsFromColor(String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getSlotNumsFromColor(String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSlotNumFromRegNum(String regNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAvailableSlot() {
		// TODO Auto-generated method stub
		return 0;
	}

}
