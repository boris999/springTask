package com.model.table;

import com.model.expression.ExpressionTreeNode;

public abstract class AbstractTable {

	private final int columnCount;

	public AbstractTable(int columnCount) {
		this.columnCount = columnCount;
	}

	public abstract int getRowCount();

	public int getColumnCount() {
		return this.columnCount;
	}

	public abstract <T> T getTableCellValue(int column, int row);

	public abstract ExpressionTreeNode<CellReference> getExpression(CellReference cellName);

	public abstract FormulaTable getReferenceGraph();

	public abstract String getFirstColumnValue(int row);

}
