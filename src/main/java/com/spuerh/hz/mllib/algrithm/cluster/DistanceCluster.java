package com.spuerh.hz.mllib.algrithm.cluster;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 *  @Describe:简单距离聚类
 */
public class DistanceCluster<T extends DistanceComparable> implements Cluster<T> {
	
	/**
	 * distance threshold to compare. 
	 */
	private double tcdis = 0;
	
	private List<ClusterSet> clusters;
	
	public DistanceCluster(double tcdis){
		this.tcdis = tcdis;
	}

	@Override
	public void clustering(List<T> list) {
		
		clusters = new ArrayList<ClusterSet>();
		List<Integer> scaned = new ArrayList<Integer>();
		
		for(int i=0;i<list.size();i++ ){
			ClusterSet clusterSet = null;
			if(scaned.contains(i)){
				continue;
				
			}else{
				clusterSet = new ClusterSet(i);
				clusters.add(clusterSet);
				clusterSet.getMemberIds().add(i);
			}
			//int temp = i;
			for(int j=i+1;j<list.size();j++){
				if(list.get(i).distance(list.get(j)) <= tcdis){
					clusterSet.getMemberIds().add(j);
					scaned.add(j);
				//	temp = j;
				}else{
					continue;
				}
			}
		}
		
	}
	
	@Override
	public List<ClusterSet> getClusters() {
		return clusters;
	}

	
	
}
