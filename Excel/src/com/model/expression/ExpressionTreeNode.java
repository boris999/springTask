package com.model.expression;

import java.util.Set;

import com.model.table.Cell;

public interface ExpressionTreeNode {
	public boolean hasRight();

	public double getValue();

	// public ExpressionTreeNode getRight();
	//
	// public ExpressionTreeNode getLeft();

	public Set<Cell> getDependingCells();
}
