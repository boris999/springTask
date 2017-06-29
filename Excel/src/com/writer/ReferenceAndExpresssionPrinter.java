package com.writer;

import com.model.expression.ExpressionTreeNode;
import com.model.table.CellReference;
import com.serialize.expression.CellNameTransformer;

public interface ReferenceAndExpresssionPrinter {
	static String printExpression(ExpressionTreeNode<CellReference> expression) {
		return expression.toString(CellNameTransformer::getCellName);
	}
}
