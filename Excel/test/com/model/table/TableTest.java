package com.model.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.ReferenceContext;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;
import com.serialize.expression.ConsoleInputParser;

public class TableTest {

	@Test
	public void testSetExpressionToExpressionCheckValueSimple() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=25", (Table) context);
		parser.addCell("C1=14", (Table) context);
		Assert.assertEquals(new Double(39), new Double(context.getValue(new CellReference(0, 0))));
	}

	@Test
	public void testSetExpressionToExpressionCheckValueAdvanced() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2/B3", (Table) context);
		parser.addCell("C1=C2*C3", (Table) context);
		parser.addCell("C2=15.2", (Table) context);
		parser.addCell("C3=11.9", (Table) context);
		parser.addCell("B3=4.3", (Table) context);
		parser.addCell("B2=76.2", (Table) context);
		Assert.assertEquals(new Double(198.60093023255814), new Double(context.getValue(new CellReference(0, 0))));
	}

	@Test
	public void testSetExpressionToExpressionCheckValueComplex() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2^2", (Table) context);
		parser.addCell("B2=E3+5^A3", (Table) context);
		parser.addCell("E3=F2-6*A2", (Table) context);
		parser.addCell("F2=C3/4+A4", (Table) context);
		parser.addCell("C3=2", (Table) context);
		parser.addCell("A3=2", (Table) context);
		parser.addCell("A2=1", (Table) context);
		parser.addCell("A4=5", (Table) context);
		parser.addCell("C1=14", (Table) context);
		Assert.assertEquals(new Double(614.25), new Double(context.getValue(new CellReference(0, 0))));
	}

	@Test
	public void testSetExpressionToExpressionCheckValueComplexSecond() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2^2", (Table) context);
		parser.addCell("B2=E3+5^A3", (Table) context);
		parser.addCell("E3=F2-6*A2", (Table) context);
		parser.addCell("F2=C3/4+A4", (Table) context);
		parser.addCell("C3=2", (Table) context);
		parser.addCell("A3=2", (Table) context);
		parser.addCell("A2=1", (Table) context);
		parser.addCell("A4=5", (Table) context);
		parser.addCell("C1=14", (Table) context);
		parser.addCell("E3=3", (Table) context);
		parser.addCell("A3=E3-5", (Table) context);
		Assert.assertEquals(new Double(23.2416), new Double(context.getValue(new CellReference(0, 0))));
	}

	@Test
	public void testSetExpressionToExpressionCheckDependancies() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		Cell cell = new Cell();
		CellReference a1 = new CellReference(0, 0);
		ReferenceNode<CellReference> left = new ReferenceNode<>(a1);
		CellReference b1 = new CellReference(1, 0);
		ReferenceNode<CellReference> right = new ReferenceNode<>(b1);
		cell.setExpression(new BinaryOperatorNode<>(left, right, BinaryOperator.PLUS), context);
		Set<CellReference> actual = cell.getDependanciesReferences(context);
		Set<CellReference> expected = new HashSet<>();
		expected.add(a1);
		expected.add(b1);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSetValueToExpressionCheckDependancies() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		Cell cell = new Cell();
		CellReference a1 = new CellReference(0, 0);
		ReferenceNode<CellReference> left = new ReferenceNode<>(a1);
		CellReference b1 = new CellReference(1, 0);
		ReferenceNode<CellReference> right = new ReferenceNode<>(b1);
		cell.setExpression(new BinaryOperatorNode<>(left, right, BinaryOperator.PLUS), context);
		cell.setExpression(new ValueNode<CellReference>(12.45), context);
		Set<CellReference> actual = cell.getDependanciesReferences(context);
		Assert.assertEquals(Collections.EMPTY_SET, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionComplex() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2^2", (Table) context);
		parser.addCell("B2=E3+5^A3", (Table) context);
		parser.addCell("E3=F2-6*A2", (Table) context);
		parser.addCell("F2=C3/4+A4", (Table) context);
		parser.addCell("C3=F2/A1+A4", (Table) context);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionSimple() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("C1=A1", (Table) context);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcheckCircularExpressionAdvanced() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2^2", (Table) context);
		parser.addCell("B2=E3+5^A3", (Table) context);
		parser.addCell("E3=F2-6*A2", (Table) context);
		parser.addCell("F2=B1/4+A4", (Table) context);

	}

	@Test
	public void testcheckCircularExpressionNoCircular() {
		ReferenceContext<CellReference> context = new Table(10, 10);
		ConsoleInputParser parser = new ConsoleInputParser();
		parser.addCell("A1=B1+C1", (Table) context);
		parser.addCell("B1=B2^2", (Table) context);
		parser.addCell("B2=E3+5^A3", (Table) context);
		parser.addCell("E3=F2-6*A2", (Table) context);
		parser.addCell("A1=88", (Table) context);
		parser.addCell("F2=A1/4+A4", (Table) context);
	}

}
