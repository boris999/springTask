package com.model.expression;

import java.util.HashSet;
import java.util.Set;

import com.model.table.Cell;

public class BinaryOperatorNode implements ExpressionTreeNode {
	// TODO visibility
	private ExpressionTreeNode left;
	private ExpressionTreeNode right;
	private BinaryOperator action;

	public BinaryOperatorNode(ExpressionTreeNode left, ExpressionTreeNode right, BinaryOperator action) {
		this.left = left;
		this.right = right;
		this.action = action;
	}

	public ExpressionTreeNode getLeft() {
		return this.left;
	}

	public void setLeft(ExpressionTreeNode left) {
		this.left = left;
	}

	public ExpressionTreeNode getRight() {
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

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Cell> getDependingCells() {
		Set<Cell> tempSet = new HashSet<>();
		Set<Cell> leftSet = this.left.getDependingCells();
		Set<Cell> rightSet = this.right.getDependingCells();
		if (leftSet != null) {
			tempSet.addAll(leftSet);
		}
		if (rightSet != null) {
			tempSet.addAll(rightSet);
		}
		return tempSet;
	}

}
