package com.model.expression;

import java.util.HashSet;
import java.util.Set;

public class ReferenceNode<T> implements ExpressionTreeNode<T> {
	private final T reference;

	public ReferenceNode(T reference) {
		this.reference = reference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.reference == null) ? 0 : this.reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		ReferenceNode<?> other = (ReferenceNode<?>) obj;
		if (this.reference == null) {
			if (other.reference != null) {
				return false;
			}
		} else if (!this.reference.equals(other.reference)) {
			return false;
		}
		return true;
	}

	@Override
	public Set<T> getTransitiveReferences(ReferenceContext<T> context) {
		Set<T> tempSet = new HashSet<>();
		tempSet.add(this.reference);
		return tempSet;
	}

	@Override
	public Double getValue(ReferenceContext<T> context) {
		return context.getValue(this.reference);
	}

	@Override
	public String toString() {
		return this.reference.toString();
	}

}
