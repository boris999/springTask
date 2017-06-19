package com.serialize.expression;

import org.junit.Assert;
import org.junit.Test;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;
import com.model.table.CellReference;

public class ExpressionTreeFactoryTest {

	@Test
	public void testValue() {
		this.checkParsed(new ValueNode<CellReference>(5), "5");
	}

	@Test
	public void testNegativeValue() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(0), new ValueNode<CellReference>(5), BinaryOperator.MINUS), "-5");
	}

	@Test
	public void testReference() {
		this.checkParsed(new ReferenceNode<>(new CellReference(0, 0)), "A1");
	}

	@Test
	public void testBinarySum() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(5), new ValueNode<CellReference>(81), BinaryOperator.PLUS),
				"5 + 81");
	}

	@Test
	public void testBinarySumOfFive() {
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ValueNode<CellReference>(5), new ValueNode<CellReference>(81),
				BinaryOperator.PLUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(first, new ReferenceNode<>(new CellReference(0, 2)),
				BinaryOperator.PLUS);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(second, new ReferenceNode<>(new CellReference(0, 7)),
				BinaryOperator.PLUS);
		this.checkParsed(new BinaryOperatorNode<>(third, new ValueNode<CellReference>(7), BinaryOperator.PLUS), "5 + 81+A3+A8+7");
	}

	@Test
	public void testBinarySubstract() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(0, 0)), new ValueNode<CellReference>(8),
						BinaryOperator.MINUS),
				"A1 - 8");
	}

	@Test
	public void testBinaryDivide() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(0, 0)), new ValueNode<CellReference>(8),
						BinaryOperator.DIVIDE),
				"A1 / 8");
	}

	@Test
	public void testBinaryMultiply() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(8), new ReferenceNode<>(new CellReference(0, 0)),
						BinaryOperator.MULTIPLY),
				"8 * A1");
	}

	@Test
	public void testBinaryPower() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(8), new ReferenceNode<>(new CellReference(0, 0)),
						BinaryOperator.POWER),
				"8 ^ A1");
	}

	@Test
	public void testSimplePriority() {
		BinaryOperatorNode<CellReference> right = new BinaryOperatorNode<>(new ValueNode<CellReference>(5),
				new ReferenceNode<>(new CellReference(0, 5)),
				BinaryOperator.MULTIPLY);
		this.checkParsed(
				new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(0, 0)), right, BinaryOperator.MINUS),
				"A1-5*A6");
	}

	@Test
	public void testMorePriority() {
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ValueNode<CellReference>(0),
				new ReferenceNode<>(new CellReference(0, 0)),
				BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(new ValueNode<CellReference>(5),
				new ReferenceNode<>(new CellReference(0, 5)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(0, 8)),
				new ValueNode<CellReference>(6),
				BinaryOperator.DIVIDE);
		BinaryOperatorNode<CellReference> fourth = new BinaryOperatorNode<>(third, new ValueNode<CellReference>(3),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> sixth = new BinaryOperatorNode<>(fourth, new ValueNode<CellReference>(2),
				BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> seventh = new BinaryOperatorNode<>(sixth, new ValueNode<CellReference>(93),
				BinaryOperator.PLUS);
		BinaryOperatorNode<CellReference> eight = new BinaryOperatorNode<>(second, seventh, BinaryOperator.MINUS);
		this.checkParsed(new BinaryOperatorNode<>(first, eight, BinaryOperator.MINUS), "-A1-5*A6-A9/6*3-2+93");
	}

	@Test
	public void testMoreAndMorePriority() {
		BinaryOperatorNode<CellReference> left = new BinaryOperatorNode<>(new ValueNode<CellReference>(10),
				new ReferenceNode<>(new CellReference(0, 5)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> rightRight = new BinaryOperatorNode<>(
				new ReferenceNode<>(new CellReference(1, 2)),
				new ValueNode<CellReference>(2),
				BinaryOperator.POWER);
		BinaryOperatorNode<CellReference> right = new BinaryOperatorNode<>(new ValueNode<CellReference>(3), rightRight,
				BinaryOperator.DIVIDE);
		this.checkParsed(new BinaryOperatorNode<>(left, right, BinaryOperator.MINUS), "10 * A6 - 3 / B3 ^ 2");
	}

	@Test
	public void testBrackets() {// (B3-B1)*A4/B9-2
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(1, 2)),
				new ReferenceNode<>(new CellReference(1, 0)), BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(first,
				new ReferenceNode<>(new CellReference(0, 3)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(second,
				new ReferenceNode<>(new CellReference(1, 8)),
				BinaryOperator.DIVIDE);
		ValueNode<CellReference> fourth = new ValueNode<>(2);
		this.checkParsed(new BinaryOperatorNode<>(third, fourth, BinaryOperator.MINUS), "(B3-B1)*A4/B9-2");
	}

	@Test
	public void testMoreBrackets() {// (B3-B1)*A4/(B9-2)
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(1, 2)),
				new ReferenceNode<>(new CellReference(1, 0)), BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(first,
				new ReferenceNode<>(new CellReference(0, 3)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(1, 8)),
				new ValueNode<CellReference>(2),
				BinaryOperator.MINUS);
		this.checkParsed(new BinaryOperatorNode<>(second, third, BinaryOperator.DIVIDE), "(B3-B1)*A4/(B9-2)");
	}

	@Test
	public void testSingleUselessBracket() {// (B3)-A4/B9-2
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(0, 3)),
				new ReferenceNode<>(new CellReference(1, 8)),
				BinaryOperator.DIVIDE);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(first, new ValueNode<CellReference>(2),
				BinaryOperator.MINUS);
		this.checkParsed(
				new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(1, 2)), second, BinaryOperator.MINUS),
				"(B3)-A4/B9-2");
	}

	@Test
	public void testMultipleUselessBracket() {// B3
		this.checkParsed(new ReferenceNode<>(new CellReference(1, 2)), "(((B3)))");
	}

	@Test
	public void testUnaryPlus() {
		this.checkParsed(new ValueNode<CellReference>(5), "+5");
	}

	@Test
	public void testMixedBinaryPlusUnaryPlus() {
		this.checkParsed(new BinaryOperatorNode<>(new ValueNode<CellReference>(4), new ValueNode<CellReference>(5), BinaryOperator.PLUS),
				"4 + +5");
	}

	@Test
	public void testMixedBinaryPlusUnaryMinus() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(4), new ValueNode<CellReference>(5), BinaryOperator.MINUS),
				"4 + -5");
	}

	@Test
	public void testMixedBinaryMinusUnaryPlus() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(4), new ValueNode<CellReference>(5), BinaryOperator.MINUS),
				"4 - +5");
	}

	@Test
	public void testMixedBinaryMinusUnaryMinus() {
		this.checkParsed(new BinaryOperatorNode<>(new ValueNode<CellReference>(4), new ValueNode<CellReference>(5), BinaryOperator.PLUS),
				"4 - -5");
	}

	@Test
	public void testMultipleUnaryPlus() {
		this.checkParsed(new ValueNode<CellReference>(5), "++5");
	}

	@Test
	public void testMultipleUnaryMinus() {
		this.checkParsed(
				new BinaryOperatorNode<>(new ValueNode<CellReference>(0), new ValueNode<CellReference>(5), BinaryOperator.MINUS),
				"---5");
	}

	@Test
	public void testMixuedMultipleUnaryOperators() {
		this.checkParsed(new ValueNode<CellReference>(5), "-+-5");
	}

	@Test
	public void testUnaryOperatorWithBrackets() {
		this.checkParsed(new ValueNode<CellReference>(5), "-(4 * A1 - B2 + C3 ^ 3) - 6");
	}

	@Test
	public void testExcessiveBrackets() {// (((B3-B1)*A4)/B9)-2)
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ReferenceNode<>(new CellReference(1, 2)),
				new ReferenceNode<>(new CellReference(1, 0)), BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(first,
				new ReferenceNode<>(new CellReference(0, 3)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(second,
				new ReferenceNode<>(new CellReference(1, 8)),
				BinaryOperator.DIVIDE);
		ValueNode<CellReference> fourth = new ValueNode<>(2);
		this.checkParsed(new BinaryOperatorNode<>(third, fourth, BinaryOperator.MINUS), "((((B3-B1)*A4)/B9)-2)");
	}

	@Test
	public void testStrange() {// ((-A5)/7^A1+(4*B4))/2")
		BinaryOperatorNode<CellReference> first = new BinaryOperatorNode<>(new ValueNode<CellReference>(0),
				new ReferenceNode<>(new CellReference(0, 4)),
				BinaryOperator.MINUS);
		BinaryOperatorNode<CellReference> second = new BinaryOperatorNode<>(new ValueNode<CellReference>(7),
				new ReferenceNode<>(new CellReference(0, 0)),
				BinaryOperator.POWER);
		BinaryOperatorNode<CellReference> third = new BinaryOperatorNode<>(first, second, BinaryOperator.DIVIDE);
		BinaryOperatorNode<CellReference> fourth = new BinaryOperatorNode<>(new ValueNode<CellReference>(4),
				new ReferenceNode<>(new CellReference(1, 3)),
				BinaryOperator.MULTIPLY);
		BinaryOperatorNode<CellReference> fifth = new BinaryOperatorNode<>(third, fourth, BinaryOperator.PLUS);
		this.checkParsed(new BinaryOperatorNode<>(fifth, new ValueNode<CellReference>(2), BinaryOperator.DIVIDE),
				"((-A5)/7^A1+(4*B4))/2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonClosedBracket() {
		this.checkParsed(new ValueNode<CellReference>(5), "4 * (B1 -3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingOpeningBracket() {
		this.checkParsed(new ValueNode<CellReference>(5), "4 * B1) -3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongBracketsOrder() {
		this.checkParsed(new ValueNode<CellReference>(5), "4 * (B1 - 4)) * (3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongExpressionEnd() {
		this.checkParsed(new ValueNode<CellReference>(5), "4 /");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongExpressionStart() {
		this.checkParsed(new ValueNode<CellReference>(5), "* B1");
	}

	private void checkParsed(ExpressionTreeNode<CellReference> expected, String expression) {
		ExpressionTreeNode<CellReference> parsed = ExpressionTreeFactory.parseExpression(expression);
		Assert.assertEquals(expression, expected, parsed);
	}

}
