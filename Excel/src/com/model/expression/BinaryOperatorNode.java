package com.model.expression;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
		return this.action.calculate(leftValue, rightValue);
	}

	@Override
	public String toString(Function<T, String> referenceFormatter) {
		String sign = this.action.getOperator();
		return "(" + this.left.toString(referenceFormatter) + " " + sign + " " + this.right.toString(referenceFormatter) + ")";
	}

	@Override
	public Set<T> getReferences() {
		Set<T> leftSet = this.left.getReferences();
		Set<T> rightSet = this.right.getReferences();
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

	@Override
	public int hashCode() {
		return Objects.hash(this.action, this.left, this.right);
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

		return Objects.equals(this.action, other.action) && Objects.equals(this.left, other.left) && Objects.equals(this.right, other.right);
	}

}
