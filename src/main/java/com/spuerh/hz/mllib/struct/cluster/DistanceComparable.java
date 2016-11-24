package com.spuerh.hz.mllib.struct.cluster;

/**
 * 可比较的，可距离
 */
public interface DistanceComparable {

	public double distance(Object o);

	public DistanceComparable getCerter(Object o);

}
