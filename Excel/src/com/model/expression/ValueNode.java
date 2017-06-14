package com.model.expression;

import java.util.HashSet;
import java.util.Set;

public class ValueNode<T> implements ExpressionTreeNode<T> {
	private double value;

	public ValueNode(double value) {
		this.value = value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.value);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
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
		ValueNode<?> other = (ValueNode<?>) obj;
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override
	public Double getValue(ReferenceContext<T> context) {
		return this.value;
	}

	@Override
	public Set<T> getTransitiveReferences(ReferenceContext<T> context) {
		return new HashSet<T>();
	}

}
