package com.spuerh.hz.mllib.algrithm.classify;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.struct.classify.DecisionNode;

/**
 * @Describe:决策树分类
 */
public class DecisionTree {
	
	private DecisionNode rootNode;
	private List<DecisionNode> leafNodes;
	
	public DecisionTree(DecisionNode rootNode){
		this.rootNode = rootNode;
	}
	
	public DecisionNode decide(Object o){
		return findBelongNode(o,rootNode);
	}
	
	
	public DecisionNode findBelongNode(Object o,DecisionNode decisionNode){
		
		if(decisionNode.isLeaf()||decisionNode.isDecidedOver(o)){
			return decisionNode;
		}else{
			return findBelongNode(o,decisionNode.decideForward(o));
		}
		
	}
	
	public List<DecisionNode> getLeafNodes() {
		
		leafNodes = new ArrayList<DecisionNode>();
		
		if(rootNode.isLeaf()){
			leafNodes.add(rootNode);
			return leafNodes;
		}else{
			findLeafNodesInChildNode(rootNode);
		}
		
		return leafNodes;
	}
	
	private void findLeafNodesInChildNode(DecisionNode parentNode){
		
		List<DecisionNode> childNodes = parentNode.getChildNodes();
		
		for(DecisionNode node:childNodes){
			
			if(!node.isLeaf()){
				findLeafNodesInChildNode(node);
			}else{
				leafNodes.add(node);
			}
			
		}
		
	}

	public void setLeafNodes(List<DecisionNode> leafNodes) {
		this.leafNodes = leafNodes;
	}
	

	public DecisionNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(DecisionNode rootNode) {
		this.rootNode = rootNode;
	}


}
