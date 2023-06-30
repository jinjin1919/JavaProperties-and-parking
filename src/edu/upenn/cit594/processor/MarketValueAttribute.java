package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyInfo;

public class MarketValueAttribute implements Attribute {

	@Override
	public double getValueAttribute(PropertyInfo home) {
		
		return home.getMarketValue(); 
	}

	@Override
	public long getCountAttribute(PropertyInfo home) {
		
		return home.getMarketValCount(); 
	}
	
	

}
