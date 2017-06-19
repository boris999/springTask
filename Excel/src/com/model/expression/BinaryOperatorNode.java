package com.model.expression;

import java.util.HashSet;
import java.util.Set;

public class BinaryOperatorNode<T> implements ExpressionTreeNode<T> {
	private ExpressionTreeNode<T> left;
	private ExpressionTreeNode<T> right;
	private BinaryOperator action;

	public BinaryOperatorNode(ExpressionTreeNode<T> left, ExpressionTreeNode<T> right, BinaryOperator action) {
		this.left = left;
		this.right = right;
		this.action = action;
	}

	public ExpressionTreeNode<T> getLeft() {
		return this.left;
	}

	public void setLeft(ExpressionTreeNode<T> left) {
		this.left = left;
	}

	public ExpressionTreeNode<T> getRight() {
		return this.right;
	}

	public void setRigth(ExpressionTreeNode<T> rigth) {
		this.right = rigth;
	}

	public BinaryOperator getAction() {
		return this.action;
	}

	public void setAction(BinaryOperator action) {
		this.action = action;
	}

	@Override
	public Double getValue(ReferenceContext<T> context) {
		Double leftValue = this.left.getValue(context);
		Double rightValue = this.right.getValue(context);
		if ((leftValue == null) || (rightValue == null)) {
			return null;
		}
		switch (this.action) {
		case PLUS:
			return leftValue + rightValue;
		case MINUS:
			return leftValue - rightValue;
		case MULTIPLY:
			return leftValue * rightValue;
		case DIVIDE:
			return leftValue / rightValue;
		default:
			return Math.pow(leftValue, rightValue);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.action == null) ? 0 : this.action.hashCode());
		result = (prime * result) + ((this.left == null) ? 0 : this.left.hashCode());
		result = (prime * result) + ((this.right == null) ? 0 : this.right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		BinaryOperatorNode<?> other = (BinaryOperatorNode<?>) obj;
		if (this.action != other.action) {
			return false;
		}
		if (this.left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!this.left.equals(other.left)) {
			return false;
		}
		if (this.right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!this.right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String sign = "";
		switch (this.action) {
		case PLUS:
			sign = "+";
			break;
		case DIVIDE:
			sign = "/";
			break;
		case MINUS:
			sign = "-";
			break;
		case MULTIPLY:
			sign = "*";
			break;
		case POWER:
			sign = "^";
			break;
		default:
			break;
		}
		return "(" + this.left + sign + this.right + ")";
	}

	@Override
	public Set<T> getDirectReferences(ReferenceContext<T> context) {
		Set<T> leftSet = this.left.getDirectReferences(context);
		Set<T> rightSet = this.right.getDirectReferences(context);
		if (leftSet.isEmpty()) {
			return rightSet;
		} else if (rightSet.isEmpty()) {
			return leftSet;
		} else {
			Set<T> tempSet = new HashSet<>();
			tempSet.addAll(leftSet);
			tempSet.addAll(rightSet);
			return tempSet;
		}
	}

	public boolean hasRight() {
		if (this.right == null) {
			return false;
		}
		return true;
	}
}
