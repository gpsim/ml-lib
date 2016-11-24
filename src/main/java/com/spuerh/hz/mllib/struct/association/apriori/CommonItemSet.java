package com.spuerh.hz.mllib.struct.association.apriori;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @Describe: 一个项集包含多个项
 */
public class CommonItemSet<T> implements ItemSet<T>, Comparable<T> {

	/**
	 * the list of sequences that contain this CommonItemSet in the database
	 */
	private List<Integer> seqIds = new ArrayList<Integer>();

	/**
	 * the amount of sequences that contain this CommonItemSet in the database
	 */
	private int frequence;
	private Set<Item<T>> items;

	public CommonItemSet(Set<Item<T>> items) {
		this.items = items;
	}

	public CommonItemSet(Item<T> item) {
		this.items = new HashSet<Item<T>>();
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
		return items.equals(((CommonItemSet<T>) o).getItems());
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
		return this.frequence - ((CommonItemSet<T>) o).getFrequence();
	}
	
	/*
	 * -------------------------------------------getter and setter
	 * --------------------------------------
	 */

	public Set<Item<T>> getItems() {
		if (null == items) {
			items = new HashSet<Item<T>>();
		}
		return items;
	}

	@Override
	public void setItems(Collection<Item<T>> items) {
		this.items = (Set<Item<T>>) items;
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
