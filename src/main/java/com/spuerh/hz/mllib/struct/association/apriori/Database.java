package com.spuerh.hz.mllib.struct.association.apriori;

import java.util.ArrayList;
import java.util.List;


/**
 * @Describe: 一个资料库包含多个序列
 */
public class Database<T> {

	private List<Sequence<T>> sequences;

	public List<Sequence<T>> getSequences() {
		if (null == sequences) {
			sequences = new ArrayList<Sequence<T>>();
		}
		return sequences;
	}

	public void setSequences(List<Sequence<T>> sequences) {
		this.sequences = sequences;
	}

	public void addSequence(Sequence<T> seq) {
		this.getSequences().add(seq);
	}

	public String toString() {
		String ret = "";
		for (Sequence<T> seq : sequences) {
			ret += seq.toString() + "\n";
		}
		return ret;
	}

	/**
	 * Number of sequences
	 * @return
	 */
	public int size() {
		return sequences.size();
	}

}
