package com.model.expression;

import java.util.Map;
import java.util.Set;

import com.model.table.Cell;

public interface ExpressionTreeNode {
	public boolean hasRight();

	public Set<Cell> getDependingCells();

	public Double getValue(Map<String, Cell> map);
}
