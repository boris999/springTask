package com.model.expression;

import java.util.Set;

import com.model.table.Cell;

public class ValueNode implements ExpressionTreeNode {
	private double value;

	public ValueNode(double value) {
		this.value = value;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.value);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
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
		ValueNode other = (ValueNode) obj;
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasRight() {
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override
	public Set<Cell> getDependingCells() {
		return null;
	}

}
