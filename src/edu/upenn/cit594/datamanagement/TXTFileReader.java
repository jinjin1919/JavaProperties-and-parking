package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TXTFileReader implements PopulationReader{
	
	protected String txtFilename; 
	
	
	
	public TXTFileReader(String filename) {
		
		this.txtFilename = filename; 
	}
	
	
	@Override
	public Map<String, Integer> downloadPopulationMaps(){
		
		Map<String, Integer> capitaMap = new HashMap<>(); 
		
		File file = new File(txtFilename);

		FileReader fileReader = null;

		BufferedReader br = null;

		try {

			fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String line = "";

			while ((line = br.readLine()) != null) {
				
				line = line.trim(); 
				
				String[] token = line.split("\\s+"); 
				
				if(token.length == 2) {
					
					String zipCode = token[0].trim(); 
					
					String popu = token[1].trim(); 
					
					Integer populat = Integer.parseInt(popu); 
	
					if (!capitaMap.containsKey(zipCode)) {
						
						capitaMap.put(zipCode, populat);
						
					}else {
						
						capitaMap.put(zipCode, capitaMap.get(zipCode) + populat); 
						
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
				fileReader.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return capitaMap; 
		
	}
	
	public static void main(String[] args) {
		
		TXTFileReader txt = new TXTFileReader("population.txt"); 
		
		Map<String, Integer> ret = txt.downloadPopulationMaps();
		
		System.out.println(ret);
		
		System.out.println(ret.size());
		
		
		
	}
	
	
	

}
