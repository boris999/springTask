package com.serialize.expression;

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
	public void testMorePriority() {
		BinaryOperatorNode first = new BinaryOperatorNode(new ValueNode(0), new ReferenceNode("A1"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(new ValueNode(5), new ReferenceNode("A6"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode third = new BinaryOperatorNode(new ReferenceNode("A9"), new ValueNode(6), BinaryOperator.DIVIDE);
		BinaryOperatorNode fourth = new BinaryOperatorNode(third, new ValueNode(3), BinaryOperator.MULTIPLY);
		BinaryOperatorNode sixth = new BinaryOperatorNode(fourth, new ValueNode(2), BinaryOperator.MINUS);
		BinaryOperatorNode seventh = new BinaryOperatorNode(sixth, new ValueNode(93), BinaryOperator.PLUS);
		BinaryOperatorNode eight = new BinaryOperatorNode(second, seventh, BinaryOperator.MINUS);
		this.checkParsed(new BinaryOperatorNode(first, eight, BinaryOperator.MINUS), "-A1-5*A6-A9/6*3-2+93");
	}

	@Test
	public void testMoreAndMorePriority() {
		BinaryOperatorNode left = new BinaryOperatorNode(new ValueNode(10), new ReferenceNode("A6"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode rightRight = new BinaryOperatorNode(new ReferenceNode("B3"), new ValueNode(2), BinaryOperator.POWER);
		BinaryOperatorNode right = new BinaryOperatorNode(new ValueNode(3), rightRight, BinaryOperator.DIVIDE);
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
	public void testMoreBrackets() {// (B3-B1)*A4/(B9-2)
		BinaryOperatorNode first = new BinaryOperatorNode(new ReferenceNode("B3"), new ReferenceNode("B1"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ReferenceNode("A4"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode third = new BinaryOperatorNode(new ReferenceNode("B9"), new ValueNode(2), BinaryOperator.MINUS);
		this.checkParsed(new BinaryOperatorNode(second, third, BinaryOperator.DIVIDE), "(B3-B1)*A4/(B9-2)");
	}

	@Test
	public void testSingleUselessBracket() {// (B3)-A4/B9-2
		BinaryOperatorNode first = new BinaryOperatorNode(new ReferenceNode("A4"), new ReferenceNode("B9"),
				BinaryOperator.DIVIDE);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ValueNode(2), BinaryOperator.MINUS);
		this.checkParsed(new BinaryOperatorNode(new ReferenceNode("B3"), second, BinaryOperator.MINUS), "(B3)-A4/B9-2");
	}

	@Test
	public void testMultipleUselessBracket() {// B3
		this.checkParsed(new ReferenceNode("B3"), "(((B3)))");
	}

	@Test
	public void testUnaryPlus() {
		this.checkParsed(new ValueNode(5), "+5");
	}

	@Test
	public void testMixedBinaryPlusUnaryPlus() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(4), new ValueNode(5), BinaryOperator.PLUS), "4 + +5");
	}

	@Test
	public void testMixedBinaryPlusUnaryMinus() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(4), new ValueNode(5), BinaryOperator.MINUS), "4 + -5");
	}

	@Test
	public void testMixedBinaryMinusUnaryPlus() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(4), new ValueNode(5), BinaryOperator.MINUS), "4 - +5");
	}

	@Test
	public void testMixedBinaryMinusUnaryMinus() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(4), new ValueNode(5), BinaryOperator.PLUS), "4 - -5");
	}

	@Test
	public void testMultipleUnaryPlus() {
		this.checkParsed(new ValueNode(5), "++5");
	}

	@Test
	public void testMultipleUnaryMinus() {
		this.checkParsed(new BinaryOperatorNode(new ValueNode(0), new ValueNode(5), BinaryOperator.MINUS), "---5");
	}

	@Test
	public void testMixuedMultipleUnaryOperators() {
		this.checkParsed(new ValueNode(5), "-+-5");
	}

	@Test
	public void testExcessiveBrackets() {// (((B3-B1)*A4)/B9)-2)
		BinaryOperatorNode first = new BinaryOperatorNode(new ReferenceNode("B3"), new ReferenceNode("B1"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(first, new ReferenceNode("A4"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode third = new BinaryOperatorNode(second, new ReferenceNode("B9"), BinaryOperator.DIVIDE);
		ValueNode fourth = new ValueNode(2);
		this.checkParsed(new BinaryOperatorNode(third, fourth, BinaryOperator.MINUS), "((((B3-B1)*A4)/B9)-2)");
	}

	@Test
	public void testStrange() {// ((-A5)/7^A1+(4*B4))/2")
		BinaryOperatorNode first = new BinaryOperatorNode(new ValueNode(0), new ReferenceNode("A5"), BinaryOperator.MINUS);
		BinaryOperatorNode second = new BinaryOperatorNode(new ValueNode(7), new ReferenceNode("A1"), BinaryOperator.POWER);
		BinaryOperatorNode third = new BinaryOperatorNode(first, second, BinaryOperator.DIVIDE);
		BinaryOperatorNode fourth = new BinaryOperatorNode(new ValueNode(4), new ReferenceNode("B4"), BinaryOperator.MULTIPLY);
		BinaryOperatorNode fifth = new BinaryOperatorNode(third, fourth, BinaryOperator.PLUS);
		this.checkParsed(new BinaryOperatorNode(fifth, new ValueNode(2), BinaryOperator.DIVIDE), "((-A5)/7^A1+(4*B4))/2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonClosedBracket() {
		this.checkParsed(new ValueNode(5), "4 * (B1 -3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingOpeningBracket() {
		this.checkParsed(new ValueNode(5), "4 * B1) -3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongBracketsOrder() {
		this.checkParsed(new ValueNode(5), "4 * (B1 - 4)) * (3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongExpressionEnd() {
		this.checkParsed(new ValueNode(5), "4 /");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongExpressionStart() {
		this.checkParsed(new ValueNode(5), "* B1");
	}

	private void checkParsed(ExpressionTreeNode expected, String expression) {
		ExpressionTreeNode parsed = ExpressionTreeFactory.parseExpression(expression);
		Assert.assertEquals(expression, expected, parsed);
	}

}
