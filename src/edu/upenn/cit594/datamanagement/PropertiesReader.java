package edu.upenn.cit594.datamanagement;


import java.util.List;
import java.util.Set;

import edu.upenn.cit594.data.PropertyInfo;

public interface PropertiesReader {
	
	public Set<PropertyInfo> getPropertyInfoPerZip(); 

}
