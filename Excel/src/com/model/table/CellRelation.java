package com.model.table;

import java.util.HashSet;
import java.util.Set;

public class CellRelation {

	private final Cell cell;
	private Set<Cell> dependingOnThisCell = new HashSet<>();
	private Set<Cell> cellDependsOn = new HashSet<>();

	public CellRelation(Cell cell) {
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
		CellRelation other = (CellRelation) obj;
		if (this.cell == null) {
			if (other.cell != null) {
				return false;
			}
		} else if (!this.cell.equals(other.cell)) {
			return false;
		}
		return true;
	}

	public Set<Cell> addCellToDependingOnThisCell(Cell cell) {
		this.dependingOnThisCell.add(cell);
		return this.dependingOnThisCell;
	}

	public Set<Cell> addCellSetToDependingOnThisCell(Set<Cell> cellSet) {
		this.dependingOnThisCell.addAll(cellSet);
		return this.dependingOnThisCell;
	}

	public Set<Cell> addCellToCellDependsOn(Cell cell) {
		this.cellDependsOn.add(cell);
		return this.cellDependsOn;
	}

	public Set<Cell> addCellSetToCellDependsOn(Set<Cell> cellSet) {
		this.cellDependsOn.addAll(cellSet);
		return this.cellDependsOn;
	}

	public Set<Cell> getDependingOnThisCell() {
		return this.dependingOnThisCell;
	}

	public Set<Cell> getCellDependsOn() {
		return this.cellDependsOn;
	}

}
