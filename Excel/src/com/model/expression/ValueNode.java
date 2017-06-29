package com.model.expression;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
		return Objects.hash(this.value);
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

		return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
	}

	@Override
	public String toString(Function<T, String> referenceFormatter) {
		return String.valueOf(this.value);
	}

	@Override
	public Double getValue(ReferenceContext<T> context) {
		return this.value;
	}

	@Override
	public Set<T> getReferences() {
		return Collections.emptySet();
	}

}
