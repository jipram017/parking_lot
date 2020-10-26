/**
 * 
 */
package main.models;

import java.util.TreeSet;

/**
 * @author ajipramono
 *
 */
public class AllocationImpl implements Allocation {
	private TreeSet<Integer> slots;
	
	public AllocationImpl() {
		slots = new TreeSet<Integer>();
	}

	@Override
	public void addSlot(int slot) {
		slots.add(slot);
	}

	@Override
	public int getSlot() {
		return slots.first();
	}

	@Override
	public void removeSlot(int slot) {
		slots.remove(slot);
	}

}
