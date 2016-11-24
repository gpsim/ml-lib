package com.spuerh.hz.mllib.struct.classify;


/**
 * @Describe:决策树的默认根节点，包含了整个样本集合
 */
public class DefaultDecisionNode extends DecisionNode {

	public DefaultDecisionNode(String decripation) {
		super(decripation);
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return true;
	}

}
