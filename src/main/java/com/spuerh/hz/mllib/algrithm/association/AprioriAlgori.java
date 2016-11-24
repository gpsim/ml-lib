package com.spuerh.hz.mllib.algrithm.association;

import java.util.Set;

import com.spuerh.hz.mllib.struct.association.apriori.Database;
import com.spuerh.hz.mllib.struct.association.apriori.ItemSet;


/**
 * @Describe: defines general function of Apriori Algorithm
 */
public interface AprioriAlgori<T, IS extends ItemSet<T>> {

	/**
	 * Main process of the algorithm.
	 */
	public void excavate();

	/**
	 * Return the Database of Sequences.
	 */
	public Database<T> getDatabase();

	/**
	 * Return the result of frequent itemsets.
	 */
	public Set<IS> getFreItemSet();
}
