package edu.upenn.cit594.processor;

import java.util.Map;
import java.util.Map.Entry;


import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.TXTFileReader;

public class PopulationProcessor {
	
	protected PopulationReader popuReader; 
	
	protected Map<String, Integer> populationMap;
	
	
	public PopulationProcessor(PopulationReader pReader) {
		
		popuReader = pReader; 
		
		populationMap = pReader.downloadPopulationMaps(); 
		
	}
	
	
	public int totalPopulationForAll() {
		
		int sum = 0; 
		
		for(Entry<String, Integer> entry: populationMap.entrySet()) {
			
			int val = entry.getValue(); 
			
			sum += val; 
		
		}
		
		return sum; 
	}
	
	
	
	public static void main(String[] args) {
		
		
		PopulationReader pop = new TXTFileReader("population.txt"); 

		
		PopulationProcessor process = new PopulationProcessor(pop); 
	
		
		int sum = process.totalPopulationForAll(); 
		
	
		System.out.println(sum); // 1526206
	
	
		
	}

}
