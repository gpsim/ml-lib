package com.spuerh.hz.mllib.struct.association.apriori;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @Describe: 有序项集
 */
public class OrderItemSet<T> implements ItemSet<T>, Comparable<T> {

	/**
	 * the list of sequences that contain this OrderItemSet in the database
	 */
	private List<Integer> seqIds = new ArrayList<Integer>();

	/**
	 * the amount of sequences that contain this OrderItemSet in the database
	 */
	private int frequence;
	private List<Item<T>> items;

	public OrderItemSet(List<Item<T>> items) {
		this.items = items;
	}

	public OrderItemSet(Item<T> item) {
		this.items = new ArrayList<Item<T>>();
		items.add(item);
	}

	@Override
	public String toString() {
		String ret = "( ";
		for (Item<T> i : items) {
			ret += i.getContent().toString() + "||";
		}
		return ret + " )";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		return items.equals(((OrderItemSet<T>) o).getItems());
	}

	@Override
	public int hashCode() {
		return items.size();
	}

	public void addItem(Item<T> item) {
		items.add(item);
	}

	/**
	 * Number of items
	 * 
	 * @return
	 */
	public int size() {
		return items.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Object o) {
		return this.frequence - ((OrderItemSet<T>) o).getFrequence();
	}
	
	/*
	 * -------------------------------------------getter and setter
	 * --------------------------------------
	 */

	public List<Item<T>> getItems() {
		if (null == items) {
			items = new ArrayList<Item<T>>();
		}
		return items;
	}
	
	@Override
	public void setItems(Collection<Item<T>> items) {
		this.items = (List<Item<T>>) items;
	}
	
	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public void incrementFrequence() {
		this.frequence++;
	}

	public List<Integer> getSeqIds() {
		return seqIds;
	}

	public void setSeqIds(List<Integer> seqIds) {
		this.seqIds = seqIds;
	}

}
