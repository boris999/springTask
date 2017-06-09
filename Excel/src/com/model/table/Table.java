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
		this.clearCellLinks(cellReference);

		cell.setNodeTree(expression, this);
		// set new links
		Set<CellReference> currentCellDependsOn = cell.getExpression().getTransitiveReferences(this);
		for (CellReference s : currentCellDependsOn) {
			Cell currentCellDependsOnCurrent = this.getCell(s);
			currentCellDependsOnCurrent.getObsevers().add(cell.getCellReference());
			cell.getCellDependsOn().add(currentCellDependsOnCurrent.getCellReference());
		}
		this.notifyOtherCellsAboutChangeInThisCell(cellReference);
	}

	public void setValue(CellReference cellReference, Double value) {
		Cell currentCell = this.getCell(cellReference);
		currentCell.setValue(value, this);
		this.clearCellLinks(cellReference);
		this.notifyOtherCellsAboutChangeInThisCell(cellReference);
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
	public Set<Cell> getCells(Set<CellReference> set) {
		Set<Cell> cellSet = new HashSet<>();
		for (CellReference reference : set) {
			cellSet.add(this.getCell(reference));
		}
		return cellSet;

	}

	private Cell checkForCircularReference(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cellToAdd = this.getCell(cellReference);
		Set<CellReference> copyOfObservers = new HashSet<>(cellToAdd.getObsevers());
		copyOfObservers.retainAll(expression.getTransitiveReferences(this));
		if (!copyOfObservers.isEmpty()) {
			throw new IllegalArgumentException("Circylar reference!");
		}
		return cellToAdd;
	}

	private void clearCellLinks(CellReference cellReference) {
		Cell currentCell = this.getCell(cellReference);
		for (CellReference c : currentCell.getDependingRefferenceNodes(this)) {
			this.getCell(c).getObsevers().remove(currentCell);
		}
		currentCell.getCellDependsOn().clear();
	}

	private void notifyOtherCellsAboutChangeInThisCell(CellReference cellReference) {
		for (CellReference currentReference : this.getCell(cellReference).getObsevers()) {
			this.getCell(currentReference).calculateValue(this);
		}
	}

}
