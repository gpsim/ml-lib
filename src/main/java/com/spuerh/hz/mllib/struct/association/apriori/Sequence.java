package com.spuerh.hz.mllib.struct.association.apriori;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @Describe: 一个序列包含多个项
 */
public class Sequence<T> {

	//private Set<Integer> ids;
	private List<Item<T>> items;

	public Sequence(List<Item<T>> items) {
		this.items = items;
	}

	/*public Set<Integer> getIds() {
		return ids;
	}

	public void setIds(Set<Integer> ids) {
		this.ids = ids;
	}*/

	public List<Item<T>> getItems() {
		return items;
	}

	public void setItems(List<Item<T>> items) {
		this.items = items;
	}

	@Deprecated
	public boolean containItemSet(ItemSet<T> itemSet) {
		if (items.size() < itemSet.size()) {
			return false;
		}

		for (Item<T> item : itemSet.getItems()) {
			if (!items.contains(item)) {
				return false;
			}
		}

		return true;
	}

	public Set<CommonItemSet<T>> produceOneCommonItemSet() {
		Set<CommonItemSet<T>> oneLevelSet = new HashSet<CommonItemSet<T>>();

		for (Item<T> item : items) {
			oneLevelSet.add(new CommonItemSet<T>(item));
		}

		return oneLevelSet;

	}

	public Set<CommonItemSet<T>> analyzeCommonItemSet(CommonItemSet<T> itemSet) {

		Set<CommonItemSet<T>> nextLevelSet = new HashSet<CommonItemSet<T>>();

		if (items.size() < itemSet.size()) {
			return nextLevelSet;
		}

		if (!items.containsAll(itemSet.getItems())) {
			return nextLevelSet;
		}

		for (Item<T> item : items) {
			Set<Item<T>> newSet = new HashSet<Item<T>>();
			newSet.addAll(itemSet.getItems());
			newSet.add(item);
			if (newSet.size() != itemSet.getItems().size()) {
				nextLevelSet.add(new CommonItemSet<T>(newSet));
			}
		}

		return nextLevelSet;
	}

	public Set<CommonItemSet<T>> produceNextCommonItemSet(Set<CommonItemSet<T>> currentSet) {
		Set<CommonItemSet<T>> result = new HashSet<CommonItemSet<T>>();

		for (CommonItemSet<T> itemSet : currentSet) {
			Set<CommonItemSet<T>> nextLevelSet = analyzeCommonItemSet(itemSet);
			result.addAll(nextLevelSet);
		}

		return result;
	}

	public Set<OrderItemSet<T>> produceOneOrderItemSet() {
		Set<OrderItemSet<T>> oneLevelSet = new HashSet<OrderItemSet<T>>();

		for (Item<T> item : items) {
			oneLevelSet.add(new OrderItemSet<T>(item));
		}

		return oneLevelSet;
	}

	public Set<OrderItemSet<T>> analyzeOrderItemSet(OrderItemSet<T> itemSet) {

		Set<OrderItemSet<T>> nextLevelSet = new HashSet<OrderItemSet<T>>();

		if (items.size() < itemSet.size()) {
			return nextLevelSet;
		}

		if (!items.containsAll(itemSet.getItems())) {
			return nextLevelSet;
		}

		for (int index = 0; index < items.size() - itemSet.size(); index++) {
			List<Item<T>> tmpItemList = items.subList(index, index + itemSet.size());
			if (!tmpItemList.equals(itemSet.getItems())) {
				continue;
			} else {

				for (int j = index + itemSet.size(); j < items.size(); j++) {
					List<Item<T>> shortItemList = new ArrayList<Item<T>>();
					shortItemList.addAll(tmpItemList);
					if (!shortItemList.contains(items.get(j))) {
						shortItemList.add(items.get(j));
						nextLevelSet.add(new OrderItemSet<T>(shortItemList));
					}
				}

			}
		}

		return nextLevelSet;
	}

	public Set<OrderItemSet<T>> produceNextOrderItemSet(Set<OrderItemSet<T>> currentSet) {
		Set<OrderItemSet<T>> result = new HashSet<OrderItemSet<T>>();

		for (OrderItemSet<T> itemSet : currentSet) {
			Set<OrderItemSet<T>> nextLevelSet = analyzeOrderItemSet(itemSet);
			result.addAll(nextLevelSet);
		}

		return result;
	}

}
