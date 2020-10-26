package main.models;

public abstract class Vehicle {
	private String regNum;
	private String color;
	
	public Vehicle(String registrationNumber, String color) {
		this.regNum = registrationNumber;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Vehicle [regNum=" + regNum + ", color=" + color + "]";
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
