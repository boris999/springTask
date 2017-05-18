package com.excel;

import com.model.expression.BinaryOperator;

//TODO remove
public class Node {
	private String name;
	private double value;
	private Node left;
	private Node right;
	private BinaryOperator action;

	public Node(String name) {
		this.name = name;
	}

	public Node() {
	}

	public double getValue() {
		switch (this.action) {
		case PLUS:
			this.value = this.left.getValue() + this.right.getValue();
			return this.value;
		case MINUS:
			this.value = this.left.getValue() - this.right.getValue();
			return this.value;
		case MULTIPLY:
			this.value = this.left.getValue() * this.right.getValue();
			return this.value;
		case DIVIDE:
			this.value = this.left.getValue() / this.right.getValue();
			return this.value;
		case POWER:
			this.value = Math.pow(this.left.getValue(), this.right.getValue());
			return this.value;
		}

		return this.value;
	}

	public Node getLeft() {
		return this.left;
	}

	public void setLeft(Node one) {
		this.left = one;
	}

	public Node getRight() {
		return this.right;
	}

	public void setRight(Node two) {
		this.right = two;
	}

	public BinaryOperator getAction() {
		return this.action;
	}

	public void setAction(BinaryOperator action) {
		this.action = action;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
