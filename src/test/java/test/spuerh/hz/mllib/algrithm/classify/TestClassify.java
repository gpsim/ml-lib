package test.spuerh.hz.mllib.algrithm.classify;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.algrithm.classify.DecisionTree;
import com.spuerh.hz.mllib.algrithm.classify.ID3Trainer;
import com.spuerh.hz.mllib.algrithm.classify.KNNClassify;
import com.spuerh.hz.mllib.struct.classify.DecisionNode;


/**
 * @Describe:
 */
public class TestClassify {

	public static void main(String[] args) {
		List<RecommendUser> trainSampleList = new ArrayList<RecommendUser>();
		RecommendUser ru1 = new RecommendUser(20,0,101,2000,51000);
		ru1.setDistinguishSign(0);
		
		RecommendUser ru2 = new RecommendUser(21,1,124,2000,50000);
		ru2.setDistinguishSign(1);
		
		RecommendUser ru3 = new RecommendUser(22,1,150,2000,51000);
		ru3.setDistinguishSign(1);
		
		RecommendUser ru4 = new RecommendUser(23,1,135,2000,50000);
		ru4.setDistinguishSign(1);
		
		RecommendUser ru5 = new RecommendUser(24,1,210,2000,51000);
		ru5.setDistinguishSign(1);
		
		RecommendUser ru6 = new RecommendUser(25,1,298,2000,50000);
		ru6.setDistinguishSign(1);
		
		RecommendUser ru7 = new RecommendUser(26,0,155,2000,51000);
		ru7.setDistinguishSign(0);
		
		RecommendUser ru8 = new RecommendUser(27,1,169,2000,50000);
		ru8.setDistinguishSign(1);
		
		RecommendUser ru9 = new RecommendUser(28,0,142,2000,51000);
		ru9.setDistinguishSign(0);
		
		RecommendUser ru10 = new RecommendUser(29,0,89,2000,50000);
		ru10.setDistinguishSign(1);
		
		trainSampleList.add(ru1);
		trainSampleList.add(ru2);
		trainSampleList.add(ru3);
		trainSampleList.add(ru4);
		trainSampleList.add(ru5);
		trainSampleList.add(ru6);
		trainSampleList.add(ru7);
		trainSampleList.add(ru8);
		trainSampleList.add(ru9);
		trainSampleList.add(ru10);
		
		long startTime = System.currentTimeMillis();
		//testID3DecisionTree(trainSampleList);
		testKNNClassify(trainSampleList);
		long endTime = System.currentTimeMillis();
		System.out.println("total time is :" + (endTime-startTime));
		
	}
	
	public static void testID3DecisionTree(List<RecommendUser> trainSampleList){
		
		List<List<DecisionNode>> decisionLevels = new ArrayList<List<DecisionNode>>(); 
		
		List<DecisionNode> decisionlevel1 = new ArrayList<DecisionNode>();
		RecommendDecisionNode rdn1 = new RecommendDecisionNode("d-age-1","Age::20::24");
		RecommendDecisionNode rdn2 = new RecommendDecisionNode("d-age-2","Age::25::29");
		decisionlevel1.add(rdn1);
		decisionlevel1.add(rdn2);
		
		List<DecisionNode> decisionlevel2 = new ArrayList<DecisionNode>();
		RecommendDecisionNode rdn3 = new RecommendDecisionNode("d-consum-1","Consumption::50000::50000");
		RecommendDecisionNode rdn4 = new RecommendDecisionNode("d-consum-2","Consumption::51000::51000");
		decisionlevel2.add(rdn3);
		decisionlevel2.add(rdn4);
		
		List<DecisionNode> decisionlevel3 = new ArrayList<DecisionNode>();
		RecommendDecisionNode rdn5 = new RecommendDecisionNode("d-gender-1","Gender::0::0");
		RecommendDecisionNode rdn6 = new RecommendDecisionNode("d-gender-2","Gender::1::1");
		decisionlevel3.add(rdn5);
		decisionlevel3.add(rdn6);
		
		decisionLevels.add(decisionlevel1);
		decisionLevels.add(decisionlevel2);
		decisionLevels.add(decisionlevel3);
		
		ID3Trainer<RecommendUser> id3 = new ID3Trainer<RecommendUser>(decisionLevels,trainSampleList);
		id3.train();
		DecisionTree dt = id3.getDecisionTree();
		
		for(DecisionNode decisionNode:dt.getRootNode().getChildNodes()){
			System.out.println(decisionNode.getDecripation());
			
			for(DecisionNode decisionNode2:decisionNode.getChildNodes()){
				System.out.println("-----"+decisionNode2.getDecripation());
				if(!decisionNode2.isLeaf()){
					for(DecisionNode decisionNode3:decisionNode2.getChildNodes()){
						System.out.println("----------"+decisionNode3.getDecripation());
					}
				}
				
			}
			
		}
		
	}
	
	public static void testKNNClassify(List<RecommendUser> trainSampleList){
		KNNClassify<RecommendUser> knnClassify = new KNNClassify<RecommendUser>(trainSampleList,5);
		RecommendUser tr = new RecommendUser(20,0,137,2000,51000);
		knnClassify.classify(tr);
		System.out.println(tr.getDistinguishSign());
	}
	
}
