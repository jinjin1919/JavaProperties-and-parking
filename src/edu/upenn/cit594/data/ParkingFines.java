package edu.upenn.cit594.data;

public class ParkingFines {
	
	private String zipCode; 
	
	private int populations;
	
	private double totalFines; 
	
	
	public ParkingFines (String zipCode, double totalFines) {
		
		this.zipCode =  zipCode; 
		this.totalFines = totalFines; 
	}


	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}


	/**
	 * @return the populations
	 */
	public int getPopulations() {
		return populations;
	}


	/**
	 * @return the totalFines
	 */
	public double getTotalFines() {
		return totalFines;
	}
	
	public void aggregateFines(double fine) {
		
		this.totalFines += fine; 
	}


	/**
	 * @param populations the populations to set
	 */
	public void setPopulations(int populations) {
		this.populations = populations;
	}
	
	
	



}
