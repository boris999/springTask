package com.model.expression;

public class BinaryOperatorNode implements ExpressionTreeNode {
	// TODO visibility
	private ExpressionTreeNode left;
	private ExpressionTreeNode right;
	private BinaryOperator action;

	public BinaryOperatorNode(ExpressionTreeNode left, ExpressionTreeNode rigth, BinaryOperator action) {
		this.left = left;
		this.right = rigth;
		this.action = action;
	}

	public ExpressionTreeNode getLeft() {
		return this.left;
	}

	public void setLeft(ExpressionTreeNode left) {
		this.left = left;
	}

	public ExpressionTreeNode getRigth() {
		return this.right;
	}

	public void setRigth(ExpressionTreeNode rigth) {
		this.right = rigth;
	}

	public BinaryOperator getAction() {
		return this.action;
	}

	public void setAction(BinaryOperator action) {
		this.action = action;
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
		BinaryOperatorNode other = (BinaryOperatorNode) obj;
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
	public boolean hasRight() {
		return this.right == null ? false : true;
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

}
