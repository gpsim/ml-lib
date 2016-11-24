package com.spuerh.hz.mllib.struct.cluster;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * 单个聚类集合
 */
public class ClusterSet implements Comparable<ClusterSet> {

	private double radius;
	private int certerId;
	private Set<Integer> memberIds;

	public ClusterSet(int certerId) {
		this.certerId = certerId;
		this.memberIds = new TreeSet<Integer>();
		this.memberIds.add(certerId);
	}

	public ClusterSet(int certerId, Collection<Integer> collection) {
		this.certerId = certerId;
		this.memberIds = new TreeSet<Integer>(collection);
	}

	public ClusterSet(double radius, int centerId, Collection<Integer> collection) {
		this.radius = radius;
		this.certerId = centerId;
		this.memberIds = new TreeSet<Integer>(collection);
	}

	@Override
	public int compareTo(ClusterSet o) {

		int size = memberIds.size();
		int osize = o.memberIds.size();

		if (size == osize) {
			// There are no same centerId in a sample space.
			return this.certerId - o.certerId > 0 ? 1 : -1;
		}

		return size - osize;
	}

	public int getCerterId() {
		return certerId;
	}

	public void setCerterId(int centerId) {
		this.certerId = centerId;
	}

	public Set<Integer> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(Set<Integer> memberIds) {
		this.memberIds = memberIds;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
