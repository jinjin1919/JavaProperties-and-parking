package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.upenn.cit594.data.ParkingFines;
import edu.upenn.cit594.data.PropertyInfo;

public class CSVFileReader implements ParkingReader, PropertiesReader{
	
	protected String filename; 
	
	public CSVFileReader (String filename) {
		
		this.filename = filename; 
	}

	@Override
	public Map<String, ParkingFines> getParkingFinesPerZip() {
		
		Map<String, ParkingFines> totalFines = new HashMap<String, ParkingFines>(); 
		
		File file = new File(filename); 
		
		FileReader fr = null;
		
		BufferedReader br = null; 
		
		
		try {
			
			fr = new FileReader(file); 
			
			br = new BufferedReader(fr); 
			
			String line = ""; 
			
			while ((line = br.readLine()) != null) {
				
				line = line.trim(); 
				
				String[] token = line.split(","); 
				
				String state = ""; 
				 
				String zipCode = ""; 
				
				if (token.length == 7) {
					
					state = token[4].trim().toUpperCase(); 
					zipCode = token[6].trim(); 
					
				}else {
					
					int lastIndex = token.length - 1; 
					
					zipCode = token[lastIndex].trim(); 
				}
					
				if (!state.equals("PA") || zipCode.isEmpty() || zipCode.length() != 5) {
					
					continue; 
					
				}else {
					
					double fine =  Double.parseDouble(token[1].trim()); 
					
					if(!totalFines.containsKey(zipCode)) {
						
						totalFines.put(zipCode, new ParkingFines(zipCode, fine));  
						
					}else {
						
						ParkingFines record = totalFines.get(zipCode); 
						
						record.aggregateFines(fine); 
					}
					
				}		
				
			}

		}
		catch (FileNotFoundException e) {
			
			System.out.println("Sorry, " + file.getName() + " not found.");
			System.exit(0);
	
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {

			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		

		return totalFines;
	}
	
	@Override
	public Set<PropertyInfo> getPropertyInfoPerZip() {
		
		Set<PropertyInfo> homeListings = new HashSet<>();
		
		File file = new File(filename); 
		
		FileReader fr = null;
		
		BufferedReader br = null; 
		
		
		try {
			
			fr = new FileReader(file); 
			
			br = new BufferedReader(fr); 
			
			String line = ""; 
			
			
			line = br.readLine(); 
			
			line = line.trim(); 
			
			int zipIdx = 0, marketIdx = 0, livableArea = 0; 
			
			String[] columnIndices = line.split(","); 
			
			for (int i=0; i < columnIndices.length; i++) {
				
				if (columnIndices[i].trim().equals("zip_code"))
					zipIdx = i; 
				
				if (columnIndices[i].trim().equals("market_value"))
					marketIdx = i; 
				
				if(columnIndices[i].trim().equals("total_livable_area"))
					livableArea = i;
	
			}
			
			
			while ((line = br.readLine()) != null) {
				
				line = line.trim(); 
				
				// have tried using positive lookahead, running much slower and provide similar result. ensures that this version is acceptable. 
				// String[] token = line.split(",\s*(?=([^"]*"[^"]*")*[^"]*$)");
				String[] token = line.split(",");
				
				token = this.buildUpTokensInQuotes(token);   
			                        
				String zipCode = token[zipIdx].trim(); 
				
				if (zipCode.matches("^[0-9-]*$") && zipCode.length() >= 5) {
					
					zipCode = zipCode.substring(0, 5); 
					
					//System.out.println(zipCode);
				}	
						
				String marketVal = token[marketIdx].trim();

				double val = 0.0;
				int valCount = 0; 

				if(marketVal.matches("^[0-9.]*$") && !marketVal.isEmpty()) {

					//System.out.println(marketVal);

					val = Double.parseDouble(marketVal); 
					valCount++; 

				}

				String livable = token[livableArea].trim(); 

				double space = 0.0; 
				int spaceCt = 0; 

				if(livable.matches("^[0-9.]*$") && !livable.isEmpty()) {

					space = Double.parseDouble(livable); 
					spaceCt++; 

				}

				homeListings.add(new PropertyInfo(zipCode, val, space, valCount, spaceCt)); 
						
						
					
					
					
			}		
			
	
		}
		catch (FileNotFoundException e) {
			
			System.out.println("Sorry, " + file.getName() + " not found.");
			System.exit(0);
	
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {

			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
		
		return homeListings;
	}
	
	
	public String[] buildUpTokensInQuotes(String[] token) {
		
		int start = -1;
		
		for(int i=0; i < token.length; i++) {
			
			if(token[i].contains("\"")) {
				start = i; 
				break; 
			}
		}
		
		if (start != -1) {
			
			
			if(token[start + 1].contains("\"")) {
				
				ArrayList<String> list = new ArrayList<>();
				Collections.addAll(list, token); 
				
				list.remove(start + 1); 
				
				token = new String[list.size()]; 
				token = list.toArray(token);
				
			}else if(token[start + 2].contains("\"")) {
				
				ArrayList<String> list = new ArrayList<>();
				Collections.addAll(list, token); 
				
				//System.out.println(list);
				
				list.remove(start + 1); 
				list.remove(start + 1); 
				
				token = new String[list.size()]; 
				token = list.toArray(token);
				
			}

		}
		
		return token;
		
	}
	
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		CSVFileReader csv = new CSVFileReader("properties.csv"); 
		
		Set<PropertyInfo> listing = csv.getPropertyInfoPerZip(); 
	
		
		for(PropertyInfo p: listing) {
			
			System.out.println(p.getZipCode() + " " + p.getLivableSpace());
		}
		
		System.out.println(listing.size());
		
		long end = System.currentTimeMillis(); 
		
		long timeMs = end - start; 
		
		System.out.println("Took " + timeMs + "ms to execute");
	
	
		
	}

	

}
