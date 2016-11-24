package com.spuerh.hz.mllib.struct.association.fptree;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.struct.association.apriori.Item;


/**
 * @Describe: FPtree节点
 */
public class FPNode<T> {

	private Item<T> item;
	private int appearTimes;
	private List<FPNode<T>> childNodes;
	private FPNode<T> parentNode;
	
	public FPNode(int appearTimes){
		this.setAppearTimes(appearTimes);
	}
	
	public FPNode(Item<T> item, int appearTimes){
		this.setItem(item);
		this.setAppearTimes(appearTimes);
	}
	
	public FPNode(Item<T> item, int appearTimes,FPNode<T> parentNode){
		this.setItem(item);
		this.setAppearTimes(appearTimes);
		this.parentNode = parentNode;
	}
	
	public FPNode(Item<T> item, int appearTimes,List<FPNode<T>> childNodes){
		this.setItem(item);
		this.setAppearTimes(appearTimes);
		this.setChildNodes(childNodes);
	}
	
	public void addChild(FPNode<T> childNode){
		
		if(childNodes == null){
			childNodes = new ArrayList<FPNode<T>>();
		}
		childNodes.add(childNode);
		
	}

	/*
	 * -------------------------------------------getter and setter
	 * --------------------------------------
	 */
	
	public Item<T> getItem() {
		return item;
	}

	public void setItem(Item<T> item) {
		this.item = item;
	}

	public int getAppearTimes() {
		return appearTimes;
	}

	public void setAppearTimes(int appearTimes) {
		this.appearTimes = appearTimes;
	}

	public List<FPNode<T>> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<FPNode<T>> childNodes) {
		this.childNodes = childNodes;
	}

	public FPNode<T> getParentNode() {
		return parentNode;
	}

	public void setParentNode(FPNode<T> parentNode) {
		this.parentNode = parentNode;
	}
	
	
}
