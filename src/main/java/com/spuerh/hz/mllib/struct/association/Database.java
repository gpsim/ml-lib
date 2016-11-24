package com.spuerh.hz.mllib.struct.association;

import java.util.ArrayList;
import java.util.List;


/**
 * @Describe: 一个资料库包含多个序列
 */
public class Database {

	private List<Sequence> sequences;

	public List<Sequence> getSequences() {
		if (null == sequences) {
			sequences = new ArrayList<Sequence>();
		}
		return sequences;
	}

	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}

	public void addSequence(Sequence seq) {
		this.getSequences().add(seq);
	}

	public String toString() {
		String ret = "";
		for (Sequence seq : this.getSequences()) {
			ret += seq.toString() + "\n";
		}
		return ret;
	}

	/**
	 * Number of sequences
	 * @return
	 */
	public int size() {
		return this.getSequences().size();
	}

}
