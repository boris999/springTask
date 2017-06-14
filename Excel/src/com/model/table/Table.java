package com.model.table;

import java.util.HashSet;
import java.util.Set;

import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceContext;

public class Table implements ReferenceContext<CellReference> {

	private final int rowCount;
	private final int columnCount;
	private final Cell[][] cells;

	public Table(int columnCount, int rowCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;
		this.cells = new Cell[this.columnCount][this.rowCount];
		for (int i = 0; i < columnCount; i++) {
			for (int j = 0; j < rowCount; j++) {
				this.cells[i][j] = new Cell(new CellReference(i, j));
			}
		}
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public int getColumnCount() {
		return this.columnCount;
	}

	@Override
	public Double getValue(CellReference reference) {
		Cell cell = this.getCell(reference);
		return cell.getValue(this);
	}

	public void setExpression(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cell = this.checkForCircularReference(cellReference, expression);
		// remove old links
		cell.setNodeTree(expression, this);
		cell.calculateValue(this);
		this.clearCellLinks(cellReference);
		// set new links
		Set<CellReference> currentCellDependsOn = cell.getExpression().getTransitiveReferences(this);
		for (CellReference s : currentCellDependsOn) {
			Cell currentCellDependsOnCurrent = this.getCell(s);
			currentCellDependsOnCurrent.getObsevers().add(cell);
			cell.getCellDependsOn().add(currentCellDependsOnCurrent);
		}
	}

	@Override
	public Cell getCell(CellReference reference) {
		return this.cells[reference.getColumnIndex()][reference.getRowIndex()];
	}

	@Override
	public Cell getCell(int columnIndex, int rowIndex) {
		return this.cells[columnIndex][rowIndex];
	}

	@Override
	public Set<Cell> getCells(Set<Cell> set) {
		Set<Cell> cellSet = new HashSet<>();
		for (Cell cell : set) {
			cellSet.add(this.getCell(cell.getCellReference()));
		}
		return cellSet;

	}

	private Cell checkForCircularReference(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cellToAdd = this.getCell(cellReference);
		Set<CellReference> copyOfObservers = new HashSet<>(cellToAdd.getObseverReferences());
		copyOfObservers.retainAll(expression.getTransitiveReferences(this));
		if (!copyOfObservers.isEmpty()) {
			throw new IllegalArgumentException("Circylar reference!");
		}
		return cellToAdd;
	}

	private void clearCellLinks(CellReference cellReference) {
		Cell currentCell = this.getCell(cellReference);
		for (CellReference c : currentCell.getDependanciesReferences(this)) {
			this.getCell(c).getObsevers().remove(currentCell);
		}
		currentCell.getCellDependsOn().clear();
	}

}
