package com.model.expression;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.model.table.Cell;

public class ReferenceNode implements ExpressionTreeNode {
	private Cell cell;

	public ReferenceNode(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return this.cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.cell == null) ? 0 : this.cell.hashCode());
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
		if (this.cell == null) {
			if (other.cell != null) {
				return false;
			}
		} else if (!this.cell.equals(other.cell)) {
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
		return this.cell.getName();
	}

	@Override
	public Double getValue(Map<String, Cell> map) {
		return map.get(this.cell.getName()).getValue(map);
	}

	@Override
	public Set<Cell> getDependingCells() {
		Set<Cell> tempSet = new HashSet<>();
		tempSet.add(this.cell);
		return tempSet;
	}

}
