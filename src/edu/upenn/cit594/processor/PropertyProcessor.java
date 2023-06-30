package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.upenn.cit594.data.PropertyInfo;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertiesReader;
import edu.upenn.cit594.datamanagement.TXTFileReader;

public class PropertyProcessor {
	
	protected PropertiesReader homeReader;
	
	protected PopulationReader popReader; 
	
	protected Map<String, Integer> populationMap; 
	
	protected Set<PropertyInfo> homeListings; 

	
	public PropertyProcessor(PropertiesReader hReader, PopulationReader pReader) {
		
		homeReader = hReader; 
		
		popReader = pReader; 
		
		populationMap = pReader.downloadPopulationMaps(); 
		
		homeListings = hReader.getPropertyInfoPerZip(); 
		
		
	}
	
	
	public long getAvgMarketVal(String zip) {
		
		return getAvgValue(zip, new MarketValueAttribute());
		
	}
	
	public long getAvgLivableSpace(String zip) {
		
		return getAvgValue(zip, new LivableSpaceAttribute());
		
	}
	
	
	public long getAvgValue(String zipCode, Attribute attribute) {
		
		long ret = 0; 
		double sum = 0.0;
		long count = 0; 
		
		for(PropertyInfo home: homeListings) {
			
			if(home.getZipCode().equals(zipCode)) {
				
				sum += attribute.getValueAttribute(home);
				count += attribute.getCountAttribute(home); 
			}
			
		}
		
		System.out.println("Sum is " + sum);
		System.out.println("Count is " + count);
		
		if (count != 0)
			ret = (long)(sum / count); 
		
		return ret; 
		
		
	}
	
	
	public int getMarketValPerCapita(String zipCode) {
		
		int ret = 0; 
		double sum = 0.0; 
		int populat = 0; 
		
		if (populationMap.get(zipCode) != null) {
			populat = populationMap.get(zipCode); 
		}
		
		for(PropertyInfo home: homeListings) {
			
			if(home.getZipCode().equals(zipCode)) {
				
				sum += home.getMarketValue(); 
				
			}
			
		}
		
		if(populat != 0)
			ret = (int)(sum / populat);
		
		return ret; 
	}
	
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		CSVFileReader csv = new CSVFileReader("properties.csv"); 
		
		PopulationReader psv = new TXTFileReader("population.txt"); 
	
		PropertyProcessor proc = new PropertyProcessor(csv, psv); 
		
	
		
		System.out.println("The average market value result: " + proc.getAvgMarketVal("19148"));
		System.out.println("The average marekt value per capita result: " + proc.getMarketValPerCapita("19148"));
		
		long end = System.currentTimeMillis(); 
		
		long timeMs = end - start; 
		
		System.out.println("Took " + timeMs + "ms to execute");
	
	
		
	}
	

}
