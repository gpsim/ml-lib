package com.spuerh.hz.mllib.struct.association.apriori;


/**
 * @Describe: 项
 */
public class Item<T> {

	/**
	 * the main content of this item, according the Class Generic <T>
	 */
	private T content;
	
	/**
	 * the unique id of this item
	 */
	private Occurrence occurrence;
	
	private class Occurrence {
		public int seqId;
		public int itemSetId;
		public int itemId;
		private Occurrence(int seqId, int itemSetId, int itemId) {
			this.itemId = itemId;
			this.seqId = seqId;
			this.itemSetId = itemSetId;
		}
	}
	
	public Item(T content){
		this.setContent(content);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		return this.content.equals(((Item<T>)o).getContent());
	}
	
	@Override
	public int hashCode(){
		return content.toString().length();
	}
	
	public String toString() {
		return content.toString();
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
