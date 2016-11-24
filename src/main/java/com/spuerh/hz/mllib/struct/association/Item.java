package com.spuerh.hz.mllib.struct.association;

import java.util.HashSet;
import java.util.Set;


public class Item implements Comparable<Item> {

	/**
	 * 项目的内容
	 */
	private String label;
	
	

	/**
	 * 项目的唯一标识
	 */
	private Occurrence occurrence;
	
	private class Occurrence {
		public int seqId;
		public int setId;
		public int itemId;
		private Occurrence(int seqId, int setId, int itemId) {
			this.itemId = itemId;
			this.seqId = seqId;
			this.setId = setId;
		}
	}

	public void setOccurrence(int seqId, int setId, int itemId){
		Occurrence o = new Occurrence(seqId, setId, itemId);
		this.occurrence = o;
	}
	
	public Occurrence getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
	}
	
	private Set<Occurrence> occurrences;
	/**
	 * Ids of sequences in which a item appears
	 */
	private Set<Integer> seqIds;

	static private Set<Item> items;
	
	// added by wangxingwu
		static public void cleanItem() {
			items = null;
		}

	public Set<Occurrence> getOccurrences() {
		if (null == occurrences) {
			occurrences = new HashSet<Occurrence>();
		}
		return occurrences;
	}

	public void addOccurrence(Occurrence occ) {
		this.getOccurrences().add(occ);
	}

	public void setOccurrences(Set<Occurrence> occurrences) {
		this.occurrences = occurrences;
	}

	public Set<Integer> getSeqIds() {
		if (null == seqIds) {
			seqIds = new HashSet<Integer>();
		}
		return seqIds;
	}

	public void addSeqId(int seqId) {
		this.getSeqIds().add(seqId);
	}

	public void setSeqIds(Set<Integer> occurrence) {
		this.seqIds = occurrence;
	}

	static public Set<Item> allItems() {
		if (null == items) {
			items = new HashSet<Item>();
		}
		return items;
	}

	static public Item getItem(String label) {
		for (Item i : allItems()) {
			if (i.label.equals(label))
				return i;
		}
		return null;
	}

	static public Item fromString(String l) {
		for (Item i : allItems()) {
			if (i.getLabel().equals(l))
				return i;
		}
		Item item = new Item();
		item.setLabel(l);
		allItems().add(item);
		return item;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean equals(Item i) {
		return this.equals(i.getLabel());
	}

	public boolean equals(String i) {
		if (this.getLabel().equals(i)) {
			return true;
		}
		return false;
	}

	public int compareTo(Item o) {
		return this.getLabel().compareTo(o.getLabel());
	}

	

}
