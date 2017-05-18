package com.serialize.expression;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;

public class ExpressionTreeFactoryTest {

	@Test
	public void testValue() {
		this.checkParsed(new ValueNode(5), "5");
	}

	@Test
	public void testNegativeValue() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(0), new ValueNode(5), BinaryOperator.MINUS), "-5");
	}

	@Test
	public void testReference() {
		this.checkParsed(new ReferenceNode("A1"), "A1");
	}

	@Test
	public void testBinarySum() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(5), new ValueNode(81), BinaryOperator.PLUS), "5 + 81");
	}

	@Test
	public void testBinarySumOfFive() {
		BinaryOperatorNode first = new BinaryOperatorNode(new ValueNode(5), new ValueNode(81), BinaryOperator.PLUS);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ReferenceNode("A3"), BinaryOperator.PLUS);
		BinaryOperatorNode third = new BinaryOperatorNode(second, new ReferenceNode("A8"), BinaryOperator.PLUS);
		this.checkParsed(new BinaryOperatorNode(third, new ValueNode(7), BinaryOperator.PLUS), "5 + 81+A3+A8+7");
	}

	@Test
	public void testBinarySubstract() {
		this.checkParsed(new BinaryOperatorNode(new ReferenceNode("A1"), new ValueNode(8), BinaryOperator.MINUS), "A1 - 8");
	}

	@Test
	public void testBinaryDivide() {
		this.checkParsed(new BinaryOperatorNode(new ReferenceNode("A1"), new ValueNode(8), BinaryOperator.DIVIDE), "A1 / 8");
	}

	@Test
	public void testBinaryMultiply() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(8), new ReferenceNode("A1"), BinaryOperator.MULTIPLY), "8 * A1");
	}

	@Test
	public void testBinaryPower() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(8), new ReferenceNode("A1"), BinaryOperator.POWER), "8 ^ A1");
	}

	@Test
	public void testSimplePriority() {
		BinaryOperatorNode right = new BinaryOperatorNode(new ValueNode(5), new ReferenceNode("A6"), BinaryOperator.MULTIPLY);
		this.checkParsed(new BinaryOperatorNode(new ReferenceNode("A1"), right, BinaryOperator.MINUS), "A1-5*A6");
	}

	@Test
	public void testMoreAndMorePriority() {
		BinaryOperatorNode left = new BinaryOperatorNode(new ValueNode(10), new ReferenceNode("A6"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode rightLeft = new BinaryOperatorNode(new ValueNode(3), new ReferenceNode("B3"), BinaryOperator.DIVIDE);
		BinaryOperatorNode right = new BinaryOperatorNode(rightLeft, new ValueNode(2), BinaryOperator.POWER);
		this.checkParsed(new BinaryOperatorNode(left, right, BinaryOperator.MINUS), "10 * A6 - 3 / B3 ^ 2");
	}

	@Test
	public void testBrackets() {// (B3-B1)*A4/B9-2
		BinaryOperatorNode first = new BinaryOperatorNode(new ReferenceNode("B3"), new ReferenceNode("B1"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ReferenceNode("A4"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode third = new BinaryOperatorNode(second, new ReferenceNode("B9"), BinaryOperator.DIVIDE);
		ValueNode fourth = new ValueNode(2);
		this.checkParsed(new BinaryOperatorNode(third, fourth, BinaryOperator.MINUS), "(B3-B1)*A4/B9-2");
	}

	@Test
	public void testExcessiveBrackets() {// (((B3-B1)*A4)/B9)-2)
		BinaryOperatorNode first = new BinaryOperatorNode(new ReferenceNode("B3"), new ReferenceNode("B1"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ReferenceNode("A4"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode third = new BinaryOperatorNode(second, new ReferenceNode("B9"), BinaryOperator.DIVIDE);
		ValueNode fourth = new ValueNode(2);
		this.checkParsed(new BinaryOperatorNode(third, fourth, BinaryOperator.MINUS), "(((B3-B1)*A4)/B9)-2)");
	}

	private void checkParsed(ExpressionTreeNode expected, String expression) {
		ExpressionTreeNode parsed = ExpressionTreeFactory.parseExpression(expression);
		Assert.assertEquals(expression, expected, parsed);
	}

	@Test
	public void testSplitter() {
		List<String> list = ExpressionTreeFactory.removeOuterBrackets("4+((A8-4)*6)*(9+(9-(2+1)*3))");
		@SuppressWarnings("unused")
		String a = "";
	}
}
