package com.model.table;

import static com.model.expression.BinaryOperator.DIVIDE;
import static com.model.expression.BinaryOperator.MINUS;
import static com.model.expression.BinaryOperator.MULTIPLY;
import static com.model.expression.BinaryOperator.PLUS;
import static com.model.expression.BinaryOperator.POWER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;

public class TableTest {

	@Test
	public void testOrderGraph1() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), refNode(1, 0));// a1=b1
		table.setExpression(new CellReference(1, 0), refNode(2, 0));// b1=c1
		List<CellReference> actual = new ArrayList<>();
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(0));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(1));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(2));
		Assert.assertEquals(Arrays.asList(new CellReference(2, 0), new CellReference(1, 0), new CellReference(0, 0)), actual);
	}

	@Test
	public void testOrderGraph2() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0),
				biNode(biNode(refNode(1, 0), refNode(2, 0), PLUS), refNode(3, 1), MINUS));// a1=b1+c1-d2
		table.setExpression(new CellReference(1, 0), biNode(refNode(3, 0), new ValueNode<>(8), PLUS));// b1=d1+8
		table.setExpression(new CellReference(3, 0), biNode(refNode(3, 1), new ValueNode<>(4), DIVIDE));// d1=d2/4
		setValue(table, 3, 1, 14);// d2=14
		List<CellReference> actual = new ArrayList<>();
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(0));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(1));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(2));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(3));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(4));
		Assert.assertEquals(Arrays.asList(
				new CellReference(2, 0),
				new CellReference(3, 1),
				new CellReference(3, 0),
				new CellReference(1, 0),
				new CellReference(0, 0)), actual);

	}

	@Test
	public void testOrderGraph3() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0),
				biNode(biNode(refNode(1, 0), refNode(2, 0), PLUS), refNode(3, 1), MINUS));// a1=b1+c1-d2
		table.setExpression(new CellReference(1, 0), biNode(refNode(3, 0), new ValueNode<>(8), PLUS));// b1=d1+8
		table.setExpression(new CellReference(3, 0), biNode(refNode(3, 1), new ValueNode<>(4), DIVIDE));// d1=d2/4
		table.setExpression(new CellReference(0, 1),
				biNode(biNode(refNode(0, 0), refNode(0, 2), DIVIDE), refNode(0, 3), MINUS));// a2=a1/a3-a4
		table.setExpression(new CellReference(0, 2), biNode(refNode(3, 0), refNode(2, 0), POWER));// a3=d1^c1
		table.setExpression(new CellReference(0, 3), biNode(refNode(1, 0), new ValueNode<>(5), MULTIPLY));// a4=b1*5
		List<CellReference> actual = new ArrayList<>();
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(0));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(1));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(2));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(3));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(4));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(5));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(6));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(7));
		Assert.assertEquals(Arrays.asList(
				new CellReference(2, 0),
				new CellReference(3, 1),
				new CellReference(3, 0),
				new CellReference(0, 2),
				new CellReference(1, 0),
				new CellReference(0, 0),
				new CellReference(0, 3),
				new CellReference(0, 1)), actual);
	}

	@Test
	public void testOrderGraph4() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), refNode(1, 0));// a1=b1
		table.setExpression(new CellReference(1, 0),
				biNode(biNode(refNode(2, 0), refNode(3, 0), PLUS), refNode(4, 0), MINUS));// b1=c1+d1-E1
		setValue(table, 2, 0, 1324);// c1=1324
		table.setExpression(new CellReference(0, 0), biNode(refNode(1, 0), refNode(1, 1), PLUS));// a1=b1+b2
		table.setExpression(new CellReference(4, 0), biNode(refNode(1, 1), new ValueNode<>(6), MINUS));// e1=B2-6
		setValue(table, 1, 1, 11);// b2=11
		List<CellReference> actual = new ArrayList<>();
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(0));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(1));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(2));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(3));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(4));
		actual.add(table.getReferenceGraph().getCellReferenceFromFormulaTable(5));
		Assert.assertEquals(Arrays.asList(
				new CellReference(1, 1),
				new CellReference(2, 0),
				new CellReference(3, 0),
				new CellReference(4, 0),
				new CellReference(1, 0),
				new CellReference(0, 0)), actual);
	}

	@Test
	public void testSetExpressionToExpressionCheckValueSimple() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), biNode(refNode(1, 0), refNode(2, 0), PLUS));
		setValue(table, 1, 0, 25);
		setValue(table, 2, 0, 14);
		Assert.assertEquals(new Double(39), new Double(this.getValue(table, 0, 0)));
	}

	@Test
	public void testSetExpressionCheckValue() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), biNode(refNode(1, 0), refNode(2, 0), PLUS));
		Assert.assertNull(this.getValue(table, 0, 0));
		table.setExpression(new CellReference(1, 0), biNode(refNode(1, 1), refNode(1, 2), DIVIDE));
		Assert.assertNull(this.getValue(table, 1, 0));
		table.setExpression(new CellReference(2, 0), biNode(refNode(2, 1), refNode(2, 2), MULTIPLY));
		Assert.assertNull(this.getValue(table, 2, 0));
		setValue(table, 2, 1, 15.2);
		Assert.assertNull(this.getValue(table, 2, 0));
		Assert.assertNull(this.getValue(table, 0, 0));
		setValue(table, 2, 2, 11.9);
		Assert.assertEquals(new Double(180.88), new Double(this.getValue(table, 2, 0)));
		Assert.assertNull(this.getValue(table, 0, 0));
		setValue(table, 1, 2, 4.3);
		Assert.assertNull(this.getValue(table, 1, 0));
		Assert.assertNull(this.getValue(table, 0, 0));
		setValue(table, 1, 1, 76.2);
		Assert.assertEquals(new Double(17.720930232558143), new Double(this.getValue(table, 1, 0)));
		Assert.assertEquals(new Double(198.60093023255814), new Double(this.getValue(table, 0, 0)));

	}

	@Test
	public void testSetExpressionToExpressionCheckValueComplex() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), biNode(refNode(1, 0), refNode(2, 0), PLUS));
		Assert.assertNull(this.getValue(table, 0, 0));
		table.setExpression(new CellReference(1, 0), biNode(refNode(1, 1), new ValueNode<>(2), POWER));
		Assert.assertNull(this.getValue(table, 1, 0));
		table.setExpression(new CellReference(1, 1),
				biNode(refNode(4, 2), biNode(new ValueNode<>(5), refNode(0, 2), POWER), PLUS));
		Assert.assertNull(this.getValue(table, 1, 1));
		setValue(table, 0, 1, 1);
		Assert.assertEquals(new Double(1), new Double(this.getValue(table, 0, 1)));
		setValue(table, 0, 3, 5);
		Assert.assertEquals(new Double(5), new Double(this.getValue(table, 0, 3)));
		setValue(table, 2, 2, 2);
		Assert.assertEquals(new Double(2), new Double(this.getValue(table, 2, 2)));
		table.setExpression(new CellReference(4, 2),
				biNode(refNode(5, 1), biNode(new ValueNode<>(6), refNode(0, 1), MULTIPLY), MINUS));
		Assert.assertNull(this.getValue(table, 4, 2));
		table.setExpression(new CellReference(5, 1),
				biNode(biNode(refNode(2, 2), new ValueNode<>(4), DIVIDE), refNode(0, 3), PLUS));
		Assert.assertEquals(new Double(5.5), new Double(this.getValue(table, 5, 1)));
		Assert.assertEquals(new Double(-0.5), new Double(this.getValue(table, 4, 2)));
		Assert.assertNull(this.getValue(table, 1, 1));
		Assert.assertNull(this.getValue(table, 1, 0));
		Assert.assertNull(this.getValue(table, 0, 0));
		setValue(table, 0, 2, 2);
		Assert.assertEquals(new Double(5.5), new Double(this.getValue(table, 5, 1)));
		Assert.assertEquals(new Double(-0.5), new Double(this.getValue(table, 4, 2)));
		Assert.assertEquals(new Double(24.5), new Double(this.getValue(table, 1, 1)));
		Assert.assertEquals(new Double(600.25), new Double(this.getValue(table, 1, 0)));
		Assert.assertNull(this.getValue(table, 0, 0));
		setValue(table, 2, 0, 14);
		Assert.assertEquals(new Double(5.5), new Double(this.getValue(table, 5, 1)));
		Assert.assertEquals(new Double(-0.5), new Double(this.getValue(table, 4, 2)));
		Assert.assertEquals(new Double(24.5), new Double(this.getValue(table, 1, 1)));
		Assert.assertEquals(new Double(600.25), new Double(this.getValue(table, 1, 0)));
		Assert.assertEquals(new Double(614.25), new Double(this.getValue(table, 0, 0)));
	}

	@Test
	public void testSetExpressionToExpressionCheckDependancies() {
		Table table = new Table(10, 10);
		Cell cell = new Cell();
		CellReference a1 = new CellReference(0, 0);
		ReferenceNode<CellReference> left = new ReferenceNode<>(a1);
		CellReference b1 = new CellReference(1, 0);
		ReferenceNode<CellReference> right = new ReferenceNode<>(b1);
		cell.setExpression(new BinaryOperatorNode<>(left, right, PLUS), table);
		Set<CellReference> actual = cell.getReferences();
		Set<CellReference> expected = new HashSet<>();
		expected.add(a1);
		expected.add(b1);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSetValueToExpressionCheckDependancies() {
		Table table = new Table(10, 10);
		Cell cell = new Cell();
		cell.setExpression(new BinaryOperatorNode<>(refNode(0, 0), refNode(1, 0),
				BinaryOperator.PLUS), table);
		cell.setExpression(new ValueNode<>(12.45), table);
		Set<CellReference> actual = cell.getReferences();
		Assert.assertEquals(Collections.emptySet(), actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionComplex() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), new BinaryOperatorNode<>(refNode(1, 0), refNode(2, 0), PLUS));
		table.setExpression(new CellReference(1, 0), new BinaryOperatorNode<>(refNode(1, 1), new ValueNode<>(2), POWER));
		table.setExpression(new CellReference(1, 1),
				biNode(refNode(4, 2), biNode(new ValueNode<>(5), refNode(0, 2), POWER), PLUS));
		table.setExpression(new CellReference(4, 2),
				biNode(refNode(5, 1), biNode(new ValueNode<>(6), refNode(0, 1), MULTIPLY), MINUS));
		table.setExpression(new CellReference(5, 1),
				biNode(biNode(refNode(2, 2), new ValueNode<>(4), DIVIDE), refNode(0, 3), PLUS));
		table.setExpression(new CellReference(2, 2),
				biNode(biNode(refNode(5, 1), refNode(0, 0), DIVIDE), refNode(0, 3), PLUS));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionSimple() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), new BinaryOperatorNode<>(refNode(1, 0), refNode(2, 0), PLUS));
		table.setExpression(new CellReference(2, 0), refNode(0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionAdvanced() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), new BinaryOperatorNode<>(refNode(1, 0), refNode(2, 0), PLUS));
		table.setExpression(new CellReference(1, 0), new BinaryOperatorNode<>(refNode(1, 1), new ValueNode<>(2), POWER));
		table.setExpression(new CellReference(1, 1),
				biNode(refNode(4, 2), biNode(new ValueNode<>(5), refNode(0, 2), POWER), PLUS));
		table.setExpression(new CellReference(4, 2),
				biNode(refNode(5, 1), biNode(new ValueNode<>(6), refNode(0, 1), MULTIPLY), MINUS));
		table.setExpression(new CellReference(5, 1),
				biNode(biNode(refNode(0, 0), new ValueNode<>(4), DIVIDE), refNode(0, 3), PLUS));
	}

	@Test
	public void testcheckCircularExpressionNoCircular() {
		Table table = new Table(10, 10);
		table.setExpression(new CellReference(0, 0), new BinaryOperatorNode<>(refNode(1, 0), refNode(2, 0), PLUS));
		table.setExpression(new CellReference(1, 0), new BinaryOperatorNode<>(refNode(1, 1), new ValueNode<>(2), POWER));
		table.setExpression(new CellReference(1, 1),
				biNode(refNode(4, 2), biNode(new ValueNode<>(5), refNode(0, 2), POWER), PLUS));
		table.setExpression(new CellReference(4, 2),
				biNode(refNode(5, 1), biNode(new ValueNode<>(6), refNode(0, 1), MULTIPLY), MINUS));
		setValue(table, 0, 0, 88);
		table.setExpression(new CellReference(5, 1),
				biNode(biNode(refNode(0, 0), new ValueNode<>(4), DIVIDE), refNode(0, 3), PLUS));
	}

	@Test
	public void testGetColumnCount() {
		Table table = new Table(10, 15);
		Assert.assertEquals(10, table.getColumnCount());
	}

	@Test
	public void testGetRowCount() {
		Table table = new Table(10, 15);
		Assert.assertEquals(15, table.getRowCount());
	}

	@Test
	public void testExpression() {
		Table table = new Table(10, 15);
		table.setExpression(new CellReference(5, 5), new ValueNode<>(1234));
		Assert.assertEquals(new ValueNode<>(1234), table.getExpression(new CellReference(5, 5)));
	}

	private Double getValue(Table table, int columm, int row) {
		return table.getValue(new CellReference(columm, row));
	}

	private static ReferenceNode<CellReference> refNode(int column, int row) {
		return new ReferenceNode<>(cell(column, row));
	}

	private static BinaryOperatorNode<CellReference> biNode(ExpressionTreeNode<CellReference> left, ExpressionTreeNode<CellReference> right,
			BinaryOperator operator) {
		return new BinaryOperatorNode<>(left, right, operator);
	}

	private static CellReference cell(int column, int row) {
		return new CellReference(column, row);
	}

	private static void setValue(Table table, int column, int row, double value) {
		table.setExpression(new CellReference(column, row), new ValueNode<CellReference>(value));
	}
}
