package com.spuerh.hz.mllib.algrithm.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.spuerh.hz.mllib.struct.base.matrix.BooleanSparseMatrix;
import com.spuerh.hz.mllib.struct.base.matrix.DoubleSparseMatrix;
import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistanceComparable;


/**
 * @Describe:密度聚类
 */
public class DensityCluster<T extends DistanceComparable> implements Cluster<T> {
	
	private int topK = 0;
	private double densityR;
	private int minPts;
	private int pathLen;
	
	private List<ClusterSet> clusters;
	
	public DensityCluster(double densityR,int minPts, int pathLen){
		this.densityR = densityR;
		this.minPts = minPts;
		this.pathLen = pathLen;
	}
	
	public DensityCluster(int topK,double densityR,int minPts, int pathLen){
		this.topK = topK;
		this.densityR = densityR;
		this.minPts = minPts;
		this.pathLen = pathLen;
	}

	/**
	 * 
	 * @param list
	 * @param topK
	 * @param densityR
	 * @param minPts
	 * @return
	 */
	public void clustering(List<T> list) {

		int dataSize = list.size();

		DoubleSparseMatrix disMatrix = new DoubleSparseMatrix(dataSize,
				dataSize, 1.0);

		BooleanSparseMatrix reachableMatrix = new BooleanSparseMatrix(dataSize,
				dataSize, false);

		DistanceComparable[] comparables = list
				.toArray(new DistanceComparable[] {});
		for (int i = 0; i < comparables.length; i++) {
			
			for (int j = 0; j < comparables.length; j++) {
				
				if (i != j) {
					double s = comparables[i].distance(comparables[j]);
					if (s < densityR) {
						disMatrix.set(i, j, s);
						disMatrix.set(j, i, s);
						reachableMatrix.set(i, j, true);
						reachableMatrix.set(j, i, true);
					}
				} else {
					disMatrix.set(i, j, 0);
					reachableMatrix.set(i, j, true);
					
				}
			}
		}

		boolean[] isCentroid = new boolean[dataSize];
		Arrays.fill(isCentroid, false);

		List<ClusterSet> clusterList = new ArrayList<ClusterSet>();

		for (int i = 0; i < dataSize; i++) {
			isCentroid[i] = isCentroid(reachableMatrix, i, densityR, minPts);
			if (isCentroid[i]) {
				// find cluster
				Set<Integer> members = findCluster(reachableMatrix, i, pathLen);
				double radius = getMax(disMatrix, i, new ArrayList<Integer>(
						members));
				clusterList.add(new ClusterSet(radius, i, members));
			}
		}

		// find topK
		Collections.sort(clusterList);
		Collections.reverse(clusterList);

		if(topK == 0){
			clusters = clusterList;
		}else{
			boolean[] inCluster = new boolean[dataSize];
			Arrays.fill(inCluster, false);

			List<ClusterSet> topKClusters = new ArrayList<ClusterSet>();
			int i = 0;
			for (ClusterSet cluster : clusterList) {
				if (inCluster[cluster.getCerterId()] == false) {
					topKClusters.add(cluster);
					for (Integer memberId : cluster.getMemberIds()) {
						inCluster[memberId] = true;
					}
					i++;
					if (i >= topK)
						break;
				}
			}
			clusters = topKClusters;
		}
	}

	/**
	 * 
	 * @param disMatrix
	 * @param i
	 * @param densityR
	 * @param minPts
	 * @return
	 */
	private static boolean isCentroid(BooleanSparseMatrix reachableMatrix, int i,
			double densityR, int minPts) {
		// count
		int c = 0;
		for (int j = 0; j < reachableMatrix.getRows(); j++) {
			if (reachableMatrix.get(i, j))
				c++;
		}
		
		if (c >= minPts) {
			return true;
		} else {
			return false;
		}
	}

	private static double getMax(DoubleSparseMatrix disMatrix, int certerId,
			List<Integer> members) {
		
		assert (certerId > 0 && members.size() > 0);
		double max = disMatrix.get(certerId, members.get(0));
		
		for (Integer member : members) {
			double dis = disMatrix.get(certerId, member);
			if (max < dis) {
				max = dis;
			}
		}
		return max;
	}

	/**
	 * 
	 * @param reachableMatrix
	 * @param certerId
	 * @param pathLen
	 * @return
	 */
	private static Set<Integer> findCluster(
			BooleanSparseMatrix reachableMatrix, int certerId, int pathLen) {

		Set<Integer> result = new TreeSet<Integer>();

		if (certerId < 0 || pathLen <= 0) {
			result.add(certerId);
			return result;
		}

		Set<Integer> neigborIdList = new TreeSet<Integer>();
		neigborIdList.add(certerId);
		for (int i = 0; i < pathLen; i++) {
			Set<Integer> tmp = new TreeSet<Integer>();
			for (Integer neigborId : neigborIdList) {
				for (int j = 0; j < reachableMatrix.getColumns(); j++) {
					if (reachableMatrix.get(neigborId, j)
							&& result.contains(j) == false) {
						tmp.add(j);
					}
				}
			}
			result.addAll(tmp);
			neigborIdList = tmp;
		}

		return result;
	}
	
	public List<ClusterSet> getClusters() {
		return clusters;
	}

}
