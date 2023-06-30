package edu.upenn.cit594.datamanagement;

import java.util.Map;

import edu.upenn.cit594.data.ParkingFines;

public interface ParkingReader {
	
	public Map<String, ParkingFines> getParkingFinesPerZip(); 

}
