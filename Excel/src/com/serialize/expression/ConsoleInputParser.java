package com.serialize.expression;

import com.model.expression.ExpressionTreeNode;
import com.model.table.CellReference;
import com.model.table.Table;

public class ConsoleInputParser {

	public String addCell(String expression, Table table) {
		String[] array = expression.split("=");
		String cellName = array[0].trim().toUpperCase();
		CellReference reference = CellNameTransformer.convertCellNameToIndex(cellName);
		if ((array.length == 1)) {
			return cellName + "=" + table.getExpression(reference);
		}
		if ((array.length != 2)) {
			throw new IllegalArgumentException("Enter valid argument!");
		}
		String cellValue = array[1].trim().toUpperCase();
		ExpressionTreeNode<CellReference> cellNodeTree = ExpressionTreeFactory.parseExpression(cellValue);
		table.setExpression(reference, cellNodeTree);
		return null;
	}

}
