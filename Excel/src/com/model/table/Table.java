package com.model.table;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
				this.cells[i][j] = new Cell();
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
		return this.getValue(reference.getColumnIndex(), reference.getRowIndex());
	}

	public Double getValue(int columnIndex, int rowIndex) {
		return this.getCell(columnIndex, rowIndex).getCachedValue();

	}

	public void setExpression(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cell = this.checkForCircularReference(cellReference, expression);
		// remove old links
		this.clearCellLinks(cellReference);
		// set new links
		cell.setExpression(expression, this);
		Set<CellReference> currentCellDependsOn = cell.getExpression().getDirectReferences(this);
		for (CellReference s : currentCellDependsOn) {
			Cell currentCellDependsOnCurrent = this.getCell(s);
			currentCellDependsOnCurrent.addObserver(cell);
		}
		cell.calculateValue(this);
	}

	public ExpressionTreeNode<CellReference> getExpression(CellReference reference) {
		return this.getCell(reference).getExpression();

	}

	private Cell getCell(CellReference reference) {
		return this.getCell(reference.getColumnIndex(), reference.getRowIndex());
	}

	private Cell getCell(int columnIndex, int rowIndex) {
		return this.cells[columnIndex][rowIndex];
	}

	private Cell checkForCircularReference(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cellToAdd = this.getCell(cellReference);
		Set<Cell> transitiveReferenceSet = this.getTransitiveDependancies(expression, new HashSet<Cell>());
		Set<Cell> observers = cellToAdd.getObservers();
		if (this.containsAny(transitiveReferenceSet, observers)) {
			throw new IllegalArgumentException("Circylar reference!");
		}
		return cellToAdd;
	}

	private void clearCellLinks(CellReference cellReference) {
		Cell currentCell = this.getCell(cellReference);
		for (CellReference c : currentCell.getDependanciesReferences(this)) {
			this.getCell(c).removeObserver(currentCell);
		}
	}

	private boolean containsAny(Set<Cell> first, Set<Cell> toCheckIfAnyElementIsContainedInFirstSet) {
		for (Cell c : toCheckIfAnyElementIsContainedInFirstSet) {
			if (first.contains(c)) {
				return true;
			}
		}
		return false;
	}

	private Set<Cell> getTransitiveDependancies(ExpressionTreeNode<CellReference> expression, Set<Cell> dependancies) {
		if (expression != null) {
			Set<Cell> cells = expression.getDirectReferences(this).stream().map(e -> this.getCell(e)).collect(Collectors.toSet());
			dependancies.addAll(cells);
			for (Cell c : cells) {
				if (c.getExpression() != null) {
					this.getTransitiveDependancies(c.getExpression(), dependancies);
				}
			}
		}
		return dependancies;
	}
}
