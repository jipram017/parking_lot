/**
 * 
 */
package main.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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
	
	// to map from a slot to a Vehicle
	private Map<Integer, Optional<T>> slotVehicleMap;
	
	private static ParkingManagerImpl instance = null;
	
	// Instantiate a singleton object to make sure only one parking manager instance get instantiated
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
		this.slotVehicleMap = new ConcurrentHashMap<>();
		for(int i=1; i<=capacity; i++) {
			slotVehicleMap.put(i, Optional.empty());
			allocation.addSlot(i);
		}
	}
	
	public ParkingManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int parkCar(T vehicle) {
		int availableSlot;
		if(availability.get() == 0) {
			return 0;
		}
		else {
			availableSlot = allocation.getSlot();
			if(slotVehicleMap.containsValue(Optional.of(vehicle))){
				return -1;
			}
			slotVehicleMap.put(availableSlot,  Optional.of(vehicle));
			availability.decrementAndGet();
			allocation.removeSlot(availableSlot);
		}
		return availableSlot;
	}

	@Override
	public boolean unParkCar(int slot) {
		if(!slotVehicleMap.get(slot).isPresent()) {
			return false;
		}
		availability.getAndIncrement();
		allocation.addSlot(slot);
		slotVehicleMap.put(slot, Optional.empty());
		return true;
	}

	@Override
	public List<String> getParkingStatus() {
		List<String> statusList = new ArrayList<String>();
		for(int i=1; i<=capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent()) {
				statusList.add(i + "\t\t" + vehicle.get().getRegNum() + "\t\t" + vehicle.get().getColor());
			}
		}
		return statusList;
	}

	@Override
	public List<String> getRegNumsFromColor(String color) {
		List<String> regNums = new ArrayList<String>();
		for(int i=1; i <= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && color.equalsIgnoreCase(vehicle.get().getColor())){
				regNums.add(vehicle.get().getRegNum());
			}
		}
		return regNums;
	}

	@Override
	public List<Integer> getSlotNumsFromColor(String color) {
		List<Integer> slotNums = new ArrayList<Integer>();
		for(int i=1; i<= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && color.equalsIgnoreCase(vehicle.get().getColor())){
				slotNums.add(i);
			}
		}
		return slotNums;
	}

	@Override
	public int getSlotNumFromRegNum(String regNum) {
		int slotNum = -1;
		for(int i=1; i<= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && regNum.equalsIgnoreCase(vehicle.get().getRegNum())){
				slotNum = i;
			}
		}
		return slotNum;
	}

	@Override
	public int getAvailableSlot() {
		return availability.get();
	}

}
