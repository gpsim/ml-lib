package com.spuerh.hz.mllib.algrithm.classify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spuerh.hz.mllib.struct.classify.DecisionNode;
import com.spuerh.hz.mllib.struct.classify.DefaultDecisionNode;
import com.spuerh.hz.mllib.struct.classify.Distinguishable;

/**
 * @Describe:ID3决策树训练器
 */
public class ID3Trainer<T extends Distinguishable> {

	@Deprecated
	private List<Integer> distinguishSigns;
	
	/**
	 * A 2-dimensional list contain all decision nodes used to build decision tree,
	 * exclude root node.
	 * Every decision level contains several decision nodes.
	 */
	private List<List<DecisionNode>> decisionLevels;
	
	/**
	 * The list of trained samples.
	 */
	private List<T> trainSampleList;
	private int trainSampleListSize;
	private DecisionTree decisionTree;
	private int treeLevel = 1;

	@Deprecated
	public ID3Trainer(List<Integer> distinguishSigns, List<List<DecisionNode>> decisionLevels,
			List<T> trainSampleList) {
		this.distinguishSigns = distinguishSigns;
		this.decisionLevels = decisionLevels;
		this.trainSampleList = trainSampleList;
		this.trainSampleListSize = trainSampleList.size();
		setDecisionTree(new DecisionTree(new DefaultDecisionNode("super root node")));
	}

	public ID3Trainer(List<List<DecisionNode>> decisionLevels, List<T> trainSampleList) {
		this.decisionLevels = decisionLevels;
		this.trainSampleList = trainSampleList;
		this.trainSampleListSize = trainSampleList.size();
		setDecisionTree(new DecisionTree(new DefaultDecisionNode("super root node")));
	}

	/**
	 * Training decision tree.
	 * The root node which is considered as the first level 
	 * contains whole trained sample list.
	 * The entropy of root node is bigger then other levels.
	 */
	public void train() {
		
		List<List<T>> firstLevelList = new ArrayList<List<T>>();
		double firstLevelEntropy = getEntropy(trainSampleList);
		firstLevelList.add(trainSampleList);
		increaseTreeDegree(firstLevelList, firstLevelEntropy);

	}

	/**
	 * Increase tree degree by recursion, every time recurse, the depth of the tree increases 1.
	 * In a recursion, a decision level would be picked out from decisionLevels,all nodes in this
	 * decision level are connected to tail of decision tree,as new leaf nodes.
	 * Recursion is terminated when decisionLevels is empty or entropy of one level is 0.
	 */
	public void increaseTreeDegree(List<List<T>> lastLevelLists, double lastLevelEntropy) {

		double maxGian = 0.0;
		double minEntropy = 0.0;
		List<DecisionNode> maxGainNodeList = null;
		List<List<T>> maxGainLevelLists = null;

		for (List<DecisionNode> decisionNodeList : decisionLevels) {

			double levelGian = 0.0;
			double levelEntropy = 0.0;
			List<List<T>> levelLists = new ArrayList<List<T>>();

			for (List<T> lastLevelList : lastLevelLists) {
				Map<DecisionNode, List<T>> levelMap = new HashMap<DecisionNode, List<T>>();

				for (DecisionNode decisionNode : decisionNodeList) {
					levelMap.put(decisionNode, new ArrayList<T>());
					for (T t : lastLevelList) {
						if (decisionNode.contains(t)) {
							levelMap.get(decisionNode).add(t);
						}
					}
				}

				for (DecisionNode decisionNode : levelMap.keySet()) {
					/*
					 * levelEntropy += (levelMap.get(decisionNode).size() * 1.0
					 * / lastLevelList.size()) (lastLevelList.size() * 1.0 /
					 * trainSampleListSize)
					 * getEntropy(levelMap.get(decisionNode));
					 */
					levelEntropy += (levelMap.get(decisionNode).size() * 1.0 / trainSampleListSize)
							* getEntropy(levelMap.get(decisionNode));
				}

				levelLists.addAll(levelMap.values());

			}

			levelGian = lastLevelEntropy - levelEntropy;
			if (levelGian > maxGian) {

				maxGainNodeList = decisionNodeList;
				maxGainLevelLists = levelLists;
				minEntropy = levelEntropy;
				maxGian = levelGian;
			}

		}

		decisionLevels.remove(maxGainNodeList);

		for (DecisionNode originLeafNode : decisionTree.getLeafNodes()) {
			originLeafNode.setChildNodes(maxGainNodeList);
		}
		System.out.println("decision tree level " + treeLevel + " build over.");
		treeLevel++;

		if (decisionLevels.size() != 0 && minEntropy != 0.0) {

			increaseTreeDegree(maxGainLevelLists, minEntropy);

		} else {
			return;
		}

	}

	public double getEntropy(List<T> list) {

		Map<Integer, Integer> distinguishSignMap = new HashMap<Integer, Integer>();

		for (T distinguishable : list) {
			int distinguishSign = distinguishable.getDistinguishSign();
			if (distinguishSignMap.containsKey(distinguishSign)) {
				int num = distinguishSignMap.get(distinguishSign);
				num++;
				distinguishSignMap.put(distinguishSign, num);
			} else {
				distinguishSignMap.put(distinguishSign, 1);
			}
		}

		int size = list.size();
		double entropy = 0.0;

		for (int sign : distinguishSignMap.keySet()) {
			int num = distinguishSignMap.get(sign);
			double p = 1.0 * num / size;
			entropy += -p * (Math.log(p) / Math.log(2));
		}

		return entropy;
	}

	/*
	 * ------------------------------------------- getter and setter
	 * --------------------------------------
	 */

	public DecisionTree getDecisionTree() {
		return decisionTree;
	}

	public void setDecisionTree(DecisionTree decisionTree) {
		this.decisionTree = decisionTree;
	}

}
