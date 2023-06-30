package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyInfo;

public class LivableSpaceAttribute implements Attribute {

	@Override
	public double getValueAttribute(PropertyInfo home) {
		
		return home.getLivableSpace();
	}

	@Override
	public long getCountAttribute(PropertyInfo home) {
		
		return home.getLivableSpaceCount();
	}
}
