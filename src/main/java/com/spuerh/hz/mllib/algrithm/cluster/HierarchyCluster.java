package com.spuerh.hz.mllib.algrithm.cluster;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 *  @Describe:分层聚类
 */
public class HierarchyCluster<T extends DistanceComparable> implements Cluster<T> {

	/**
	 * Current-clustering-method of this HierarchyCluster
	 */
	private Cluster<T> cluster;
	
	/**
	 * Preposition-clustering-method of this HierarchyCluster
	 */
	private Cluster<T> hierarchyCluster;
	
	/**
	 * Result ClusterSet list
	 */
	private List<ClusterSet> clusters;
	
	public HierarchyCluster(Cluster<T> cluster){
		this.cluster = cluster;
	}
	
	public HierarchyCluster(Cluster<T> cluster,Cluster<T> hierarchyCluster){
		this.cluster = cluster;
		this.hierarchyCluster = hierarchyCluster;
	}
	
	/**
	 * If there is no preposition-clustering-method (hierarchyCluster is null),
	 * current-clustering-method work for input list.
	 * Else, preposition-clustering-method would been use to put list into several 
	 * ClusterSets,then current-clustering-method is use to work for every ClusterSet,
	 * make it into more fine-grained and accurate ClusterSets.
	 * 
	 */
	@Override
	public void clustering(List<T> list) {
		
		if(hierarchyCluster == null){
			cluster.clustering(list);
			clusters = cluster.getClusters();
		}else {
			clusters = new ArrayList<ClusterSet>();
			hierarchyCluster.clustering(list);
			List<ClusterSet> hierarchyClusters = hierarchyCluster.getClusters();
			
			for(ClusterSet clusterSet:hierarchyClusters){
				List<T> childlist = new ArrayList<T>();
				
				for(Integer id: clusterSet.getMemberIds()){
					childlist.add(list.get(id));
				}
				
				if(childlist.size()==0){
					continue;
				}else{
					cluster.clustering(childlist);
					clusters.addAll(cluster.getClusters());
				}
				
			}
		}
		
	}

	@Override
	public List<ClusterSet> getClusters() {
		return clusters;
	}

}
