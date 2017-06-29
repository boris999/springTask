package com.model.table;

import java.util.Objects;
import java.util.function.Function;

public class CellReference {
	private final int columnIndex;
	private final int rowIndex;

	public CellReference(int columnIndex, int rowIndex) {
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.columnIndex, this.rowIndex);
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

		return (this.columnIndex == other.columnIndex) &&
				(this.rowIndex == other.rowIndex);
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	public int getRowIndex() {
		return this.rowIndex;
	}

	public String toString(Function<CellReference, String> referenceFormatter) {
		return referenceFormatter.apply(this);
	}

}
