package edu.upenn.cit594.processor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingFines;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.datamanagement.ParkingReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.TXTFileReader;

public class ParkingProcessor {
	
	protected ParkingReader parkingReader;
	
	protected PopulationReader popuReader; 
	
	protected Map<String, ParkingFines> fines; 
	
	protected Map<String, Integer> populationMap; 
	
	
	public ParkingProcessor (ParkingReader parkingReader, PopulationReader pReader) {
		
		this.parkingReader = parkingReader; 
		
		fines = parkingReader.getParkingFinesPerZip(); 
		
		this.popuReader = pReader; 
		
		populationMap = pReader.downloadPopulationMaps(); 
		
	
	}
	
	public Map<String, Double> computeTotalFinesPerCapita(){
		
		Map<String, Double> finesPCapita = new TreeMap<>(); 
		
		for(Entry<String, ParkingFines> entry: fines.entrySet()) {
			
			String zip = entry.getKey(); 
			
			if (populationMap.containsKey(zip)) {
				
				int popula = populationMap.get(zip); 
				
				double totalFines = entry.getValue().getTotalFines();
				
				if (popula != 0 && totalFines != 0) {
					
					double value = totalFines / (double) popula; 
					
					finesPCapita.put(zip, value); 

				}
			}
			
		}
		return finesPCapita; 
	}
	
	
	public static void main(String[] args) {
		
		ParkingReader p = new CSVFileReader("parking.csv"); 
		
		PopulationReader pop = new TXTFileReader("population.txt"); 

		
		ParkingProcessor process = new ParkingProcessor(p, pop); 
	
		
		Map<String, Double> ret = process.computeTotalFinesPerCapita(); 
		
		
		for(Entry<String, Double> entry: ret.entrySet()) {
			
			System.out.printf("%s %.4f \n", entry.getKey(), entry.getValue());
			
		}
		
		System.out.println(ret.size());
	
	
		
	}
		
	
		
		
		
		

	
	
	
	
	

}
