package com.model.expression;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class ReferenceNode<T> implements ExpressionTreeNode<T> {
	private final T reference;

	public ReferenceNode(T reference) {
		this.reference = reference;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.reference);
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
		return Objects.equals(this.reference, other.reference);
	}

	@Override
	public Set<T> getReferences() {
		return Collections.singleton(this.reference);
	}

	@Override
	public Double getValue(ReferenceContext<T> context) {
		return context.getValue(this.reference);
	}

	@Override
	public String toString(Function<T, String> referenceFormatter) {
		return referenceFormatter.apply(this.reference);
	}

}
