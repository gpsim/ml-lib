package com.spuerh.hz.mllib.algrithm.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.spuerh.hz.mllib.struct.association.apriori.Database;
import com.spuerh.hz.mllib.struct.association.apriori.Item;
import com.spuerh.hz.mllib.struct.association.apriori.OrderItemSet;
import com.spuerh.hz.mllib.struct.association.apriori.Sequence;


/**
 * @Describe:apriori频繁项集经典算法,挖掘有序项集
 */
public class OrderApriori implements AprioriAlgori<String, OrderItemSet<String>> {

	private Database<String> db;
	private double minSupportRate = 0.0;
	private int minSupport;
	private double minConfidenceRate = 0.0;
	private int minPattern = 0;
	private int maxPattern = Integer.MAX_VALUE;
	
	/**
	 * Result frequent ItemSets.
	 */
	private Set<OrderItemSet<String>> freItemSet = new TreeSet<OrderItemSet<String>>();

	public OrderApriori(List<String> dataSources, String itemSplitStr, double minSupportRate, double minConfidenceRate,
			int minPattern, int maxPattern) {

		Database<String> db = new Database<String>();

		for (String seqStr : dataSources) {
			List<Item<String>> items = new ArrayList<Item<String>>();

			for (String itemStr : seqStr.split(itemSplitStr)) {
				items.add(new Item<String>(itemStr));
			}

			Sequence<String> seq = new Sequence<String>(items);
			db.addSequence(seq);
		}

		this.db = db;
		this.minSupportRate = minSupportRate;
		this.minSupport = (int) (minSupportRate * dataSources.size());
		this.minConfidenceRate = minConfidenceRate;
		this.minPattern = minPattern;
		this.maxPattern = maxPattern;

	}

	public OrderApriori(List<String> dataSources, String itemSplitStr, double minSupportRate, int minPattern,
			int maxPattern, int minSeqPattern, int maxSeqPattern) {

		Database<String> db = new Database<String>();

		for (String seqStr : dataSources) {
			List<Item<String>> items = new ArrayList<Item<String>>();

			for (String itemStr : seqStr.split(itemSplitStr)) {
				items.add(new Item<String>(itemStr));
			}

			if (items.size() < minSeqPattern || items.size() > maxSeqPattern) {
				continue;
			}

			Sequence<String> seq = new Sequence<String>(items);
			db.addSequence(seq);

		}
		this.db = db;
		this.minSupportRate = minSupportRate;
		this.minSupport = (int) (minSupportRate * dataSources.size());
		this.minPattern = minPattern;
		this.maxPattern = maxPattern;
		/*System.out.println(dataSources.size());
		System.out.println(minSupport);*/

	}

	public OrderApriori(List<String> dataSources, String itemSplitStr, double minSupportRate) {
		Database<String> db = new Database<String>();

		for (String seqStr : dataSources) {
			List<Item<String>> items = new ArrayList<Item<String>>();

			for (String itemStr : seqStr.split(itemSplitStr)) {
				items.add(new Item<String>(itemStr));
			}

			Sequence<String> seq = new Sequence<String>(items);
			db.addSequence(seq);

		}
		this.db = db;
		this.minSupportRate = minSupportRate;
		this.minSupport = (int) (minSupportRate * dataSources.size());

	}

	/**
	 * Find frequent itemsets from database with recursion. Every time execute
	 * recursion, the number of items in itemset which is found this time would
	 * been added 1,
	 * 
	 * Recursive will not been terminated until number of items is greater than
	 * the maxPattern, or return no frequent itemset.
	 * 
	 */
	public void digFreItemSet(Set<OrderItemSet<String>> currentSet, int itemNum) {

		if (itemNum > maxPattern) {
			return;
		}
		Map<OrderItemSet<String>, Integer> tmpMap = new HashMap<OrderItemSet<String>, Integer>();
		if (itemNum == 1) {

			for (Sequence<String> seq : db.getSequences()) {
				Set<OrderItemSet<String>> set = seq.produceOneOrderItemSet();

				for (OrderItemSet<String> itemSet : set) {
					if (tmpMap.containsKey(itemSet)) {
						tmpMap.put(itemSet, tmpMap.get(itemSet) + 1);
					} else {
						tmpMap.put(itemSet, 1);
					}
				}

			}

		} else {
			// long time = 0;
			for (Sequence<String> seq : db.getSequences()) {
				// long startTime = System.currentTimeMillis();
				Set<OrderItemSet<String>> set = seq.produceNextOrderItemSet(currentSet);
				// long endTime = System.currentTimeMillis();
				// time += (endTime-startTime);
				for (OrderItemSet<String> itemSet : set) {
					if (tmpMap.keySet().contains(itemSet)) {
						tmpMap.put(itemSet, tmpMap.get(itemSet) + 1);
					} else {
						tmpMap.put(itemSet, 1);
					}
				}

			}
			/*
			 * System.out.println("level="+itemNum);
			 * System.out.println("currentSet="+currentSet.size());
			 * System.out.println("time=" + time + ",time-avg:" +
			 * (time+0.0)/db.size());
			 */
		}

		// long startTime = System.currentTimeMillis();
		Map<OrderItemSet<String>, Integer> map = new HashMap<OrderItemSet<String>, Integer>();

		for (OrderItemSet<String> itemSet : tmpMap.keySet()) {
			
			if(itemNum==2&&itemSet.getItems().get(0).getContent().equals("2867")&&
					itemSet.getItems().get(1).getContent().equals("5761")){
				System.out.println("$$$$$$$$$$$$$测试频繁度"+tmpMap.get(itemSet));
			}
			
			if (tmpMap.get(itemSet) >= minSupport) {
				itemSet.setFrequence(tmpMap.get(itemSet));
				map.put(itemSet, tmpMap.get(itemSet));
			}

		}
		/*
		 * long endTime = System.currentTimeMillis();
		 * System.out.println("level="+itemNum); System.out.println(
		 * "get frequent itemSet time ="+(endTime-startTime));
		 */

		if (map.keySet().size() == 0) {
			return;
		} else if (itemNum >= minPattern) {
			//System.out.println("level=" + itemNum);
			//System.out.println(map);
			//System.out.println("+++++++++++++++++++++++++++++++++++++");
			freItemSet.addAll(map.keySet());
			digFreItemSet(map.keySet(), ++itemNum);
		} else {
			digFreItemSet(map.keySet(), ++itemNum);
		}
	}

	/**
	 * generate seqIds in every frequent itemset, indicates that the frequent
	 * itemset appear in what sequences.
	 */
	public void digAssociation() {

		for (OrderItemSet<String> itemSet : freItemSet) {

			for (int index = 0; index < db.getSequences().size(); index++) {
				Sequence<String> sequence = db.getSequences().get(index);
				if (containItemSet(sequence, itemSet)) {
					itemSet.getSeqIds().add(index);
				}
			}

		}

	}

	private boolean containItemSet(Sequence<String> sequence, OrderItemSet<String> itemSet) {
		List<Item<String>> items = sequence.getItems();

		for (int index = 0; index < items.size() - itemSet.size(); index++) {
			List<Item<String>> tmpItemList = items.subList(index, index + itemSet.size());
			if (tmpItemList.equals(itemSet.getItems())) {
				return true;
			}
		}

		return false;
	}

	public void excavate() {
		digFreItemSet(null, 1);
		digAssociation();
	}

	public Database<String> getDatabase() {
		return db;
	}

	@Override
	public Set<OrderItemSet<String>> getFreItemSet() {
		return freItemSet;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a,b,c,d,e");
		list.add("c,f,g,t,y");
		list.add("a,b,c,d,e,a,e,r,t,u");
		list.add("c,f,g,t,y,b,t,r,t,u");
		list.add("a,b,c,d,e,r,q,w,i,o");
		list.add("c,f,g,t,y,k,l,i,o");
		List<String> list2 = new ArrayList<String>();
		list.add("c,f,g,t,y");
		list.add("a,b,c,d,e");
		System.out.println(list.containsAll(list2));

		// OrderApriori a = new OrderApriori(list,",",0.4,0,3,6);
		// a.excavate();
		/*
		 * for(ItemSet<String> itemSet: a.freItemSet){
		 * System.out.println(itemSet); }
		 */
	}
}
