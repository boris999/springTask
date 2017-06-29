package com.model.expression;

import java.util.Set;
import java.util.function.Function;

public interface ExpressionTreeNode<T> {

	public Double getValue(ReferenceContext<T> context);

	public Set<T> getReferences();

	String toString(Function<T, String> referenceFormatter);
}
