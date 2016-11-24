package com.spuerh.hz.mllib.struct.association.fptree;

import java.util.ArrayList;
import java.util.List;

import com.spuerh.hz.mllib.struct.association.apriori.Database;
import com.spuerh.hz.mllib.struct.association.apriori.Item;
import com.spuerh.hz.mllib.struct.association.apriori.Sequence;


/**
 * @Describe:FP-tree
 */
public class FPTree {
	
	private Database<String> db;
	private double minSupportRate = 0.0;
	private int minSupport;

	private int minPattern = 0;
	private int maxPattern = Integer.MAX_VALUE;
	
	
	public FPTree(List<String> dataSources, String itemSplitStr, double minSupportRate,
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
		this.minPattern = minPattern;
		this.maxPattern = maxPattern;

	}
	
}
