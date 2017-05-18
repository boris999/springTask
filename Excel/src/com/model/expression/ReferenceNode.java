package com.model.expression;

public class ReferenceNode implements ExpressionTreeNode {
	private String cellName;

	public ReferenceNode(String cellName) {
		this.cellName = cellName;
	}

	public String getCellName() {
		return this.cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.cellName == null) ? 0 : this.cellName.hashCode());
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
		ReferenceNode other = (ReferenceNode) obj;
		if (this.cellName == null) {
			if (other.cellName != null) {
				return false;
			}
		} else if (!this.cellName.equals(other.cellName)) {
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
		return this.cellName;
	}

}
