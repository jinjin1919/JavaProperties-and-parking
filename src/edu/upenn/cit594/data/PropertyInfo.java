package edu.upenn.cit594.data;

public class PropertyInfo {
	
	private double marketValue; 
	
	private double livableSpace; 
	
	private String zipCode; 
	
	private int marketValCount; 
	private int livableSpaceCount;
	
	public PropertyInfo(String code, double value, double space, int valCt, int spaceCt) {
		
		this.zipCode = code; 
		this.marketValue = value; 
		this.livableSpace = space; 
		this.marketValCount = valCt; 
		this.livableSpaceCount = spaceCt; 
		 
	}

	/**
	 * @return the marketValue
	 */
	public double getMarketValue() {
		return marketValue;
	}


	/**
	 * @return the livableSpace
	 */
	public double getLivableSpace() {
		return livableSpace;
	}

	

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	
	
	public void addUpMarketValue(int val) {
		
		this.marketValue += val; 
	}
	
	public void addUplivableSpace(int space) {
		
		this.livableSpace += space;
	}
	

	/**
	 * @param marketValCount the marketValCount to set
	 */
	public void increMarketValCount(int value) {
		this.marketValCount += value;
	}

	/**
	 * @param livableSpaceCount the livableSpaceCount to set
	 */
	public void increLivableSpaceCount(int value) {
		this.livableSpaceCount += value;
	}

	/**
	 * @return the marketValCount
	 */
	public int getMarketValCount() {
		return marketValCount;
	}

	/**
	 * @return the livableSpaceCount
	 */
	public int getLivableSpaceCount() {
		return livableSpaceCount;
	}

}
