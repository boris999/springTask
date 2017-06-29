package com.writer;

import java.io.PrintWriter;

import com.model.expression.ExpressionTreeNode;
import com.model.table.AbstractTable;
import com.model.table.CellReference;
import com.model.table.FormulaTable;
import com.serialize.expression.CellNameTransformer;

public class FormulaPrinter extends Printer {
	private static final int ROW_NUMBER_COLUMN_WIDTH = 12;

	@Override
	public void printHeader(PrintWriter writer, int maxCellWidth, int columnCount) {
		writer.print("|      Cell|");
		writer.println(Printer.getFormatedString("Expression", maxCellWidth) + "|");
	}

	@Override
	int getFirstColumnWidth() {
		return ROW_NUMBER_COLUMN_WIDTH;
	}

	@Override
	int calculateCellWidth(AbstractTable formulaTable, int columnCount, int rowCount) {
		int minWidth = 10;
		for (int i = 0; i < rowCount; i++) {
			@SuppressWarnings("unchecked")
			ExpressionTreeNode<CellReference> value = (ExpressionTreeNode<CellReference>) formulaTable.getTableCellValue(1, i);
			if (value != null) {
				int expressionLength = value.toString(CellNameTransformer::getCellName).length();
				minWidth = minWidth >= expressionLength ? minWidth : expressionLength;
			}
		}
		return minWidth;
	}

	@SuppressWarnings("unchecked")
	@Override
	<T> String printValue(T value) {
		return "=" + ((ExpressionTreeNode<CellReference>) value).toString(CellNameTransformer::getCellName);
	}

	@Override
	void printFirstColumnValue(PrintWriter writer, AbstractTable table, int row) {
		writer.print(Printer.getFormatedString(table.getFirstColumnValue(row), 8));
	}

	@Override
	AbstractTable getTable(AbstractTable table) {
		return table.getReferenceGraph();
	}

	@Override
	void printAllRows(PrintWriter writer, AbstractTable table, int rowCount, int columnCount, int maxCellWidth, int totalLength) {
		FormulaTable formulaTable = (FormulaTable) table;
		for (int i = 0; i < rowCount; i++) {
			ExpressionTreeNode<CellReference> currentExpression = formulaTable.getExpression(formulaTable.getCellReferenceFromFormulaTable(i));
			if (currentExpression == null) {
				continue;
			}
			this.printRow(i, maxCellWidth, columnCount - 1, formulaTable, writer, rowCount, totalLength);

		}
	}

	@Override
	int getColumnsForTotalLengthCalculation(int columnCount) {
		return columnCount - 1;
	}
}
