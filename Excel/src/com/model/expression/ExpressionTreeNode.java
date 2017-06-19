package com.model.expression;

import java.util.Set;

public interface ExpressionTreeNode<T> {

	public Double getValue(ReferenceContext<T> context);

	public Set<T> getDirectReferences(ReferenceContext<T> context);

}
