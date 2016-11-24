package com.spuerh.hz.mllib.algrithm.cluster;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 *  @Describe:K均值聚类
 */
public class KmeansCluster<T extends DistanceComparable> implements Cluster<T> {

	/**
	 * cluster target number
	 */
	private int clusterNumber = 10;

	/**
	 * cluster depth
	 */
	private int clusterDepth = Integer.MAX_VALUE;
	
	private List<ClusterSet> clusters;
	
	public KmeansCluster(int clusterNumber, int clusterDepth){
		this.clusterNumber = clusterNumber;
		this.clusterDepth = clusterDepth;
	}
	
	public KmeansCluster(int clusterNumber){
		this.clusterNumber = clusterNumber;
	}

	/**
	 * Excavate kmeans clusters from a list
	 */
	public void clustering(List<T> list) {

		int list_size = list.size();
		ClusterSet[] init_clusters = new ClusterSet[clusterNumber];
		
		for (int i = 0; i < clusterNumber; i++) {
			init_clusters[i] = new ClusterSet(list_size * i / clusterNumber);
		}
		
		int firstDepth = 1;
		clusters = Arrays.asList(findCluster(init_clusters, list, firstDepth));
	}
	

	/**
	 * Find cluster with recursion
	 * start from initial cluster-group,jump out from recursion when cluster center
	 * is fixed or cluster depth is reached.
	 */
	private ClusterSet[] findCluster(ClusterSet[] clusters,
			List<T> list,int depth) {

		for (ClusterSet kmeansCluster : clusters) {
			kmeansCluster.setMemberIds(new TreeSet<Integer>());
		}
		
		for (int k = 0; k < list.size(); k++) {
			DistanceComparable distanceComparable = list.get(k);
			int distanceComparable_cluster = 0;// tentative cluster sign
			double min_distance = distanceComparable.distance(list
					.get(clusters[0].getCerterId()));// tentative minimum distance

			for (int i = 1; i < clusterNumber; i++) {
				if(k==clusters[i].getCerterId()){
					distanceComparable_cluster = i;//the center id is belong this cluster and no need to adjust
					break;
				}else if (min_distance > distanceComparable.distance(list
						.get(clusters[i].getCerterId()))) {
					min_distance = distanceComparable.distance(list
							.get(clusters[i].getCerterId()));
					distanceComparable_cluster = i;
				}
			}
			// adjust cluster sign to [i],put the element index into this cluster
			clusters[distanceComparable_cluster].getMemberIds().add(k);
		}

		ClusterSet[] new_clusters = discoverCerter(clusters, list);
		int endmark = 0;
		
		for (int i = 0; i < clusterNumber; i++) {
			//if(new_clusters[i].get)
			/*System.out.println(i);
			System.out.println(new_clusters[i].getCenterId());
			System.out.println(clusters[i].getCenterId());*/
			endmark += ((new_clusters[i].getCerterId() == clusters[i]
					.getCerterId()) ? 0 : 1);
		}
		
		// recursion end when  cluster center is fixed or cluster depth is reached.
		if (endmark != 0 && depth<clusterDepth) {
			depth++;
			return findCluster(new_clusters, list, depth);
			// call recursion until new clusters satisfies the end condition 
		} else {
			return new_clusters;
		}

	}

	/**
	 * Find the new center of clusters to adjust clusters
	 * note:a new object in the new Cluster[] must be created,to compare the next step 
	 */
	private ClusterSet[] discoverCerter(
			ClusterSet[] clusters, List<T> list) {

		ClusterSet[] new_clusters = new ClusterSet[clusterNumber];
		
		for (int i = 0; i < clusterNumber; i++) {
			Iterator<Integer> iterator = clusters[i].getMemberIds()
					.iterator();
			// System.out.println(i);
			if (!iterator.hasNext()) {
				continue;
			} else {
				int first = iterator.next();
				DistanceComparable virtual_certer = list.get(first);
				
				while (iterator.hasNext()) {
					
					int current = iterator.next();
					virtual_certer = list.get(current)
							.getCerter(virtual_certer);
					
				}// defined virtual center is the center of two neighbour elements
				
				Iterator<Integer> iterator2 = clusters[i].getMemberIds()
						.iterator();

				int first2 = iterator2.next();
				double mindistance = virtual_certer.distance(list.get(first2));
				int certerId = first2;
				
				while (iterator2.hasNext()) {
					int currentId = iterator2.next();
					
					if (mindistance > virtual_certer.distance(list
							.get(currentId))) {
						mindistance = virtual_certer.distance(list
								.get(currentId));
						certerId = currentId;
					}
					
				}
				
				ClusterSet kmeansCluster = new ClusterSet(certerId);
				kmeansCluster.setMemberIds(clusters[i].getMemberIds());
				new_clusters[i] = kmeansCluster;
			}
		}

		return new_clusters;
	}

	public List<ClusterSet> getClusters() {
		return clusters;
	}

}
