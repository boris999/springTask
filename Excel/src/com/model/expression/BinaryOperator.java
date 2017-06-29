package com.model.expression;

public enum BinaryOperator {
	PLUS("+"),
	MINUS("-"),
	MULTIPLY("*"),
	DIVIDE("/"),
	POWER("^"),
	BRACKET("(");

	private final String sign;

	BinaryOperator(String sign) {
		this.sign = sign;
	}

	public String getOperator() {
		return this.sign;
	}

	public Double calculate(double leftValue, double rightValue) {
		switch (this) {
		case PLUS:
			return leftValue + rightValue;
		case MINUS:
			return leftValue - rightValue;
		case MULTIPLY:
			return leftValue * rightValue;
		case DIVIDE:
			return leftValue / rightValue;
		case POWER:
			return Math.pow(leftValue, rightValue);
		default:
			return null;

		}
	}
}
