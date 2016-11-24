package com.spuerh.hz.mllib.algrithm.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.spuerh.hz.mllib.struct.association.Database;
import com.spuerh.hz.mllib.struct.association.Item;
import com.spuerh.hz.mllib.struct.association.ItemSet;
import com.spuerh.hz.mllib.struct.association.Sequence;

/**
 * @Describe:前缀树算法
 */
public class PrefixSpan {

	
	private List<String> routeSet;

	private double minSupport;

	private Database db;
	/**
	 * Max gap between two items in a sequence
	 */
	private int maxGap = Integer.MAX_VALUE;
	/**
	 * Max pattern length
	 */
	private int maxPattern = Integer.MAX_VALUE;
	/**
	 * Frequent sequence and the corresponding id
	 */
	private List<Sequence> freqPattern;
	/**
	 * Mininmum coverage
	 */
	private double minCoverage = 0.0;
	
	/**
	 * @param routeSet
	 * @param minSup
	 */
	public PrefixSpan(List<String> routeSet, double minSup) {
		setRouteSet(routeSet);
		Item.cleanItem();//added by wangxingwu
		parseData();
		
			setMinSupport(minSup * getDatabase().getSequences().size());
			
	}

	/**
	 * Parse sequences from list
	 */
	private void parseData() {
		String line = "";
		db = new Database();
		int seqCount = -1;
		List<String> routeSet = this.getRouteSet();
		Iterator<String> routeIt = routeSet.iterator();
		while(routeIt.hasNext()){
			line = routeIt.next();
			seqCount ++;
			Sequence seq = Sequence.fromString(line, "#", ",");
			seq.addId(seqCount);
			for (int i = 0; i < seq.getItemSets().size(); i ++) {
				for (int j = 0; j < seq.getItemSet(i).size(); j ++) {
					seq.getItemSet(i).getItem(j).setOccurrence(seqCount, i, j);
				}
			}
			
			for (ItemSet iset : seq.getItemSets()) {
				for (Item i : iset.getItems()) {
					i.addSeqId(seqCount);
				}
			}
			db.addSequence(seq);
		}
	}

	public double getMinCoverage() {
		return minCoverage;
	}

	public void setMinCoverage(double minCoverage) {
		if (minCoverage < 0) {
			this.minCoverage = 0.0;
			return;
		}
		this.minCoverage = minCoverage;
	}

	public int getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(int maxGap) {
		if (-1 == maxGap) {
			this.maxGap = Integer.MAX_VALUE;
			return;
		}
		this.maxGap = maxGap;
	}

	public int getMaxPattern() {
		return maxPattern;
	}

	public void setMaxPattern(int maxPattern) {
		if (-1 == maxPattern) {
			this.maxPattern = Integer.MAX_VALUE;
			return;
		}
		this.maxPattern = maxPattern;
	}

	public List<String> getRouteSet() {
		return routeSet;
	}

	public void setRouteSet(List<String> routeSet) {
		this.routeSet = routeSet;
	}

	public double getMinSupport() {
		return minSupport;
	}

	public void setMinSupport(double minSupport) {
		this.minSupport = minSupport;
	}

	public Database getDatabase() {
		return db;
	}

	public List<Sequence> getFreqPattern() {
		if (null == freqPattern) {
			freqPattern = new ArrayList<Sequence>();
		}
		return freqPattern;
	}

	public void addFreqPattern(Sequence seq) {
		this.getFreqPattern().add(seq);
	}

	public String toString() {
		return "PrefixSpan [min_sup=" + getMinSupport() 
				+ "]\nDatabase: \n" + db.toString();
	}

	/**
	 * Project
	 * @param seq
	 * @param db
	 * @return an s-projected database
	 */
	public Database project(Sequence seq, Database db) {
		//		if (db.size() < this.getMinSupport())
		//			return null;
		Database projDb = new Database();

		for (Sequence s : db.getSequences()) {
			Sequence suffix = s.getSuffix(seq);
			if (null != suffix) projDb.addSequence(suffix);
		}
		Map<String, Integer> mapItems = new HashMap<String, Integer>();
		for (Item item : Item.allItems()) {
			for (Sequence s : projDb.getSequences()) {
				if (s.contains(item)) {
					if (mapItems.containsKey(item.getLabel())) {
						mapItems.put(item.getLabel(), mapItems.get(item.getLabel()) + 1);
					} else {
						mapItems.put(item.getLabel(), 1);
					}
				}
			}
		}
		for (String key : mapItems.keySet()) {
			int value = mapItems.get(key);
			if (value < this.getMinSupport()) {
				for (Sequence s : projDb.getSequences()) {
					s.removeItem(key);
				}
			}
		}
		return projDb;
	}

	/**
	 * 
	 */
	public void prefixSpan() {
		Set<Item> step1Items = new TreeSet<Item>();
		for (Item i : Item.allItems()) {
			if (i.getSeqIds().size() >= this.getMinSupport())  
				step1Items.add(i);
		}
		for (Item item : step1Items) {
			Sequence seq = new Sequence();
			seq.setIds(item.getSeqIds());
			ItemSet iset = new ItemSet();
			iset.addItem(item);
			seq.addItemSet(iset);
			prefixSpan(seq, project(seq, this.getDatabase()));
		}
	}
	/**
	 * 
	 * @param seq
	 * @param db
	 */
	public void prefixSpan(Sequence seq, Database db) {
		if (db.size() < this.getMinSupport()) {
			return;
		}

		// If the length is greater than the given maxPattern, return the method
		if (seq.numOfItems() != 0 
				&& seq.numOfItems() > this.getMaxPattern()) 
		{
			System.out.println(this.getMaxPattern());
			return;
		}
		this.addFreqPattern(seq);

		Set<String> step1Items = new HashSet<String>();
		for (Sequence s : db.getSequences()) {
			for (ItemSet iset : s.getItemSets()) {
				for (Item i : iset.getItems()) {
					step1Items.add(i.getLabel());
				}
			}
		}
		for (String label : step1Items) {
//System.out.println(label);
			Sequence iext = seq.iExtension(Item.getItem(label)); // i-extension
			int iextSup = 0;
			for (Sequence s : this.getDatabase().getSequences()) {
				Sequence iextSuffix = s.getSuffix(iext);
				if (null != iextSuffix) {
					for (int id : iextSuffix.getIds()) iext.addId(id);
					iextSup ++;
				}
			}
			if (iextSup >= this.getMinSupport()) {
				prefixSpan(iext, project(iext, this.getDatabase()));
			}
			Sequence sext = seq.sExtension(Item.getItem(label)); // s-extension
			int sextSup = 0;
			for (Sequence s : this.getDatabase().getSequences()) {
				Sequence sextSuffix = s.getSuffix(sext);
				if (null != sextSuffix) {
					for (int id : sextSuffix.getIds()) sext.addId(id);
					sextSup ++;
				}
			}
			if (sextSup >= this.getMinSupport()) {
				prefixSpan(sext, project(sext, this.getDatabase()));
			}
		}
	}

}
