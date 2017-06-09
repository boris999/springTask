package com.model.table;

import com.serialize.expression.CellNameTransformer;

public class CellReference {
	private final int columnIndex;
	private final int rowIndex;

	public CellReference(int columnIndex, int rowIndex) {
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.columnIndex;
		result = (prime * result) + this.rowIndex;
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
		CellReference other = (CellReference) obj;
		if (this.columnIndex != other.columnIndex) {
			return false;
		}
		if (this.rowIndex != other.rowIndex) {
			return false;
		}
		return true;
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	public int getRowIndex() {
		return this.rowIndex;
	}

	@Override
	public String toString() {
		return CellNameTransformer.calculateColumnNames().get(this.columnIndex) + String.valueOf(this.rowIndex + 1);
	}

}
