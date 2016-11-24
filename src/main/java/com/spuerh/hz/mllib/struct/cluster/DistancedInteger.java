package com.spuerh.hz.mllib.struct.cluster;


/**
 * @Describe:DistancedInteger
 */
public class DistancedInteger implements DistanceComparable {

	private Integer number;

	public DistancedInteger(Integer number) {
		this.number = number;
	}

	@Override
	public double distance(Object o) {
		return Math.abs(number.intValue() - ((DistancedInteger) o).getNumber().intValue());
	}

	@Override
	public DistanceComparable getCerter(Object o) {
		return new DistancedInteger((Math.abs(number.intValue() + ((DistancedInteger) o).getNumber().intValue())) / 2);
	}

	public String toString() {
		return String.valueOf(number);
	}

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

}
