package com.spuerh.hz.mllib.struct.classify;

import java.util.ArrayList;
import java.util.List;


/**
 * @Describe:node in decision tree
 */
public abstract class DecisionNode {

	private String decripation;
	private List<Object> elements = new ArrayList<Object>();
	private List<DecisionNode> childNodes;

	@Deprecated
	private DecisionNode parentNode;

	public DecisionNode(String decripation) {
		this.decripation = decripation;
	}

	public DecisionNode(List<DecisionNode> childNodes) {
		this.childNodes = childNodes;
	}

	public DecisionNode(DecisionNode parentNode) {
		this.parentNode = parentNode;
	}

	@Deprecated
	public DecisionNode(DecisionNode parentNode, List<DecisionNode> childNodes) {
		this.parentNode = parentNode;
		this.childNodes = childNodes;
	}

	@Deprecated
	public boolean isRoot() {
		if (parentNode == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLeaf() {
		if (childNodes == null || childNodes.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public abstract boolean contains(Object o);

	public DecisionNode decideForward(Object o) {

		if (isLeaf()) {
			return null;
		} else {
			DecisionNode decisionNode = null;

			for (DecisionNode childNode : childNodes) {
				if (childNode.contains(o)) {
					childNode.elements.add(o);
					decisionNode = childNode;
					break;
				}
			}

			return decisionNode;
		}

	}

	/*
	 * -------------------------------------------getter and setter
	 * --------------------------------------
	 */

	public boolean isDecidedOver(Object o) {
		return false;
	}

	public String getDecripation() {
		return decripation;
	}

	public List<DecisionNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<DecisionNode> childNodes) {
		this.childNodes = childNodes;
	}

	@Deprecated
	public DecisionNode getParentNode() {
		return parentNode;
	}

	@Deprecated
	public void setParentNode(DecisionNode parentNode) {
		this.parentNode = parentNode;
	}

	public void setDecripation(String decripation) {
		this.decripation = decripation;
	}

	public List<Object> getElements() {
		return elements;
	}

	public void setElements(List<Object> elements) {
		this.elements = elements;
	}
}
