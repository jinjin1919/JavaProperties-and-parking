package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingFines;

public class JSONFileReader implements ParkingReader {
	
	protected String filename; 
	
	
	public JSONFileReader(String JSONFileName) {
		
		filename = JSONFileName;
		
	}

	@Override
	public Map<String, ParkingFines> getParkingFinesPerZip() {
		
		Map<String, ParkingFines> totalFines = new TreeMap<String, ParkingFines>(); 
		
		JSONParser parser = new JSONParser(); 
		
		try {
			JSONArray lines = (JSONArray)parser.parse(new FileReader(filename));
			
			Iterator iter = lines.iterator();
			
			while (iter.hasNext()) {
				
				JSONObject ln = (JSONObject) iter.next();
				
				String state =  ln.get("state").toString();
				
				if(state.toUpperCase().equals("PA")) {
					
					String zipCode = ln.get("zip_code").toString(); 
					
					if (!zipCode.isEmpty() && !zipCode.matches("\\s+")) {
						
						String fineStr = ln.get("fine").toString().trim(); 
						
						double fine = Double.parseDouble(fineStr); 
						
						if(!totalFines.containsKey(zipCode)) {
							
							totalFines.put(zipCode, new ParkingFines(zipCode, fine));  
							
						}else {
							
							ParkingFines record = totalFines.get(zipCode); 
							
							record.aggregateFines(fine); 
						}

					}
				}
				
			}
		}catch (FileNotFoundException e) {
		
			System.out.println("Sorry, " + this.filename + " not found.");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return totalFines;
	}
	
	public static void main(String[] args) {
		
		String re = "^[0-9.]*$"; 
		
		String s = "abc123"; 

		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(s);
		
		
		System.out.println(s.matches(re));
		
		String line = "foo,bar,c;qual=\"baz,blurb\",d;junk=\"quux,syzygy\"";
        String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for(String t : tokens) {
            System.out.println("> "+t);
        }
		
		
	}

}
