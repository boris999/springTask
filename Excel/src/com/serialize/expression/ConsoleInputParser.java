package com.serialize.expression;

import com.model.expression.ExpressionTreeNode;
import com.model.table.Cell;
import com.model.table.CellReference;
import com.model.table.Table;

public class ConsoleInputParser {

	public void addCell(String expression, Table table) {
		String[] array = expression.split("=");
		String cellName = array[0].trim().toUpperCase();
		CellReference reference = CellNameTransformer.convertCellNameToIndex(cellName);
		Cell currentCell = table.getCell(reference);
		if ((array.length == 1)) {
			System.out.println(cellName + "=" + currentCell.getExpression());

		}
		if ((array.length != 2)) {
			throw new IllegalArgumentException("Enter valid argument!");
		}
		String cellValue = array[1].trim().toUpperCase();
		Double value = null;
		ExpressionTreeNode<CellReference> cellNodeTree = null;
		try {
			value = Double.parseDouble(cellValue);
		} catch (NumberFormatException e) {
			cellNodeTree = ExpressionTreeFactory.parseExpression(cellValue);
		}
		if (value == null) {
			table.setExpression(reference, cellNodeTree);
		} else {
			table.setValue(reference, value);
		}

	}

}
