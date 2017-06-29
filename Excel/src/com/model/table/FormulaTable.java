package com.model.table;

import java.util.ArrayList;
import java.util.List;

import com.model.expression.ExpressionTreeNode;
import com.serialize.expression.CellNameTransformer;

public class FormulaTable extends AbstractTable {
	private static final int ROW_NUMBER_COLUMN_WIDTH = 7;
	private List<CellReference> formulas = new ArrayList<>();
	private final Table table;

	public FormulaTable(Table table, List<CellReference> result) {
		super(2);
		this.table = table;
		this.formulas = result;
	}

	@Override
	public int getRowCount() {
		return this.formulas.size();
	}

	int getRowNumberColumnWidth() {
		return ROW_NUMBER_COLUMN_WIDTH;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getTableCellValue(int column, int row) {
		return (T) this.table.getExpression(this.formulas.get(row));
	}

	public CellReference getCellReferenceFromFormulaTable(int index) {
		return this.formulas.get(index);
	}

	@Override
	public ExpressionTreeNode<CellReference> getExpression(CellReference reference) {
		return this.table.getExpression(reference);
	}

	@Override
	public FormulaTable getReferenceGraph() {
		return this;
	}

	@Override
	public String getFirstColumnValue(int row) {
		return this.formulas.get(row).toString(CellNameTransformer::getCellName);
	}
}
