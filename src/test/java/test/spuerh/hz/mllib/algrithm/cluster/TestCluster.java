package test.spuerh.hz.mllib.algrithm.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.spuerh.hz.mllib.algrithm.cluster.DensityCluster;
import com.spuerh.hz.mllib.algrithm.cluster.DistanceCluster;
import com.spuerh.hz.mllib.algrithm.cluster.KmeansCluster;
import com.spuerh.hz.mllib.algrithm.cluster.TransferCluster;
import com.spuerh.hz.mllib.struct.cluster.ClusterSet;
import com.spuerh.hz.mllib.struct.cluster.DistancedInteger;


/**
 * @Describe:测试聚类算法
 */
public class TestCluster {
	
	public static void main(String[] args) {
		
		//testKmeansCluster();
		//testDensityCluster();
		//testTransferCluster();
		testDistanceCluster();
		
	}
	
	public static void testKmeansCluster(){
		
		List<DistancedInteger> list = new ArrayList<DistancedInteger>();
		Integer[] array = new Integer[]{151, 193, 676, 223, 216, 360, 442, 938, 667, 252, 
				873, 218, 774, 965, 83, 815, 290, 539, 284, 359, 469, 675, 903, 785, 742, 857, 763, 781, 296, 540};
		for(int i = 0;i<30;i++){
			//list.add(new DistancedInteger(Integer.valueOf((int)(Math.random()*1000000))));
			list.add(new DistancedInteger(array[i]));
			//list.add(new DistancedInteger(Integer.valueOf(100-i)));
		}
		System.out.println(list);
		KmeansCluster<DistancedInteger> kmeansCluster = new KmeansCluster<DistancedInteger>(4);
		kmeansCluster.clustering(list);
		List<ClusterSet> clusterSets = kmeansCluster.getClusters();
		for(ClusterSet clusterSet :clusterSets){
			Set<Integer> memberIds = clusterSet.getMemberIds();
			for(int memberId:memberIds){
				System.out.print(list.get(memberId) + ",");
			}
			System.out.println();
		}
		
	}
	
	public static void testDensityCluster(){
		
		List<DistancedInteger> list = new ArrayList<DistancedInteger>();
		Integer[] array = new Integer[]{151, 193, 676, 223, 216, 360, 442, 938, 667, 252, 
				873, 218, 774, 965, 83, 815, 290, 539, 284, 359, 469, 675, 903, 785, 742, 857, 763, 781, 296, 540};
		for(int i = 0;i<30;i++){
			//list.add(new DistancedInteger(Integer.valueOf((int)(Math.random()*1000000))));
			list.add(new DistancedInteger(array[i]));
			//list.add(new DistancedInteger(Integer.valueOf(100-i)));
		}
		System.out.println(list);
		DensityCluster<DistancedInteger> desityCluster = new DensityCluster<DistancedInteger>(10,100,5,1);
		desityCluster.clustering(list);
		List<ClusterSet> clusterSets = desityCluster.getClusters();
		for(ClusterSet clusterSet :clusterSets){
			Set<Integer> memberIds = clusterSet.getMemberIds();
			for(int memberId:memberIds){
				System.out.print(list.get(memberId) + ",");
			}
			System.out.println();
		}
		
	}

	
	public static void testTransferCluster(){
		
		List<DistancedInteger> list = new ArrayList<DistancedInteger>();
		Integer[] array = new Integer[]{151, 193, 676, 223, 216, 360, 442, 938, 667, 252, 
				873, 218, 774, 965, 83, 815, 290, 539, 284, 359, 469, 675, 903, 785, 742, 857, 763, 781, 296, 540};
		for(int i = 0;i<30;i++){
			//list.add(new DistancedInteger(Integer.valueOf((int)(Math.random()*1000000))));
			list.add(new DistancedInteger(array[i]));
			//list.add(new DistancedInteger(Integer.valueOf(100-i)));
		}
		System.out.println(list);
		TransferCluster<DistancedInteger> transferCluster = new TransferCluster<DistancedInteger>(100);
		transferCluster.clustering(list);
		List<ClusterSet> clusterSets = transferCluster.getClusters();
		for(ClusterSet clusterSet :clusterSets){
			Set<Integer> memberIds = clusterSet.getMemberIds();
			for(int memberId:memberIds){
				System.out.print(list.get(memberId) + ",");
			}
			System.out.println();
		}
		
	}
	
	public static void testDistanceCluster(){
		
		List<DistancedInteger> list = new ArrayList<DistancedInteger>();
		Integer[] array = new Integer[]{151, 193, 676, 223, 216, 360, 442, 938, 667, 252, 
				873, 218, 774, 965, 83, 815, 290, 539, 284, 359, 469, 675, 903, 785, 742, 857, 763, 781, 296, 540};
		for(int i = 0;i<30;i++){
			//list.add(new DistancedInteger(Integer.valueOf((int)(Math.random()*1000000))));
			list.add(new DistancedInteger(array[i]));
			//list.add(new DistancedInteger(Integer.valueOf(100-i)));
		}
		System.out.println(list);
		DistanceCluster<DistancedInteger> transferCluster = new DistanceCluster<DistancedInteger>(100);
		transferCluster.clustering(list);
		List<ClusterSet> clusterSets = transferCluster.getClusters();
		for(ClusterSet clusterSet :clusterSets){
			Set<Integer> memberIds = clusterSet.getMemberIds();
			for(int memberId:memberIds){
				System.out.print(list.get(memberId) + ",");
			}
			System.out.println();
		}
		
	}
	
}
