package com.spuerh.hz.mllib.struct.association.apriori;

import java.util.Collection;


/**
 * @Describe: 一个项集包含多个项
 */
public interface ItemSet<T> {

	public boolean equals(Object o);
	
	public int hashCode();

	public Collection<Item<T>> getItems();

	public void addItem(Item<T> item);

	public void setItems(Collection<Item<T>> items);

	/**
	 * Number of items
	 * @return
	 */
	public int size();
}
