package com.spuerh.hz.mllib.algrithm.cluster;

import java.util.List;

import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 *  @Describe: 聚类
 */
public interface Cluster<T extends DistanceComparable> {

	/**
	 * Put the list into different cluster sets.
	 */
	public void clustering(List<T> list);
	
	/**
	 * Returns the list of ClusterSets in this cluster process.
	 */
	public List<ClusterSet> getClusters();
	
}
