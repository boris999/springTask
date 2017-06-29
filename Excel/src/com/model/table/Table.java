package com.model.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceContext;

public class Table extends AbstractTable implements ReferenceContext<CellReference> {
	private final int rowCount;
	private final Cell[][] cells;
	private final Map<Cell, CellReference> cellsToIndex;

	public Table(int columnCount, int rowCount) {
		super(columnCount);
		this.rowCount = rowCount;
		this.cellsToIndex = new HashMap<>();
		this.cells = new Cell[this.getColumnCount()][this.rowCount];
		for (int i = 0; i < columnCount; i++) {
			for (int j = 0; j < rowCount; j++) {
				Cell cell = new Cell();
				this.cells[i][j] = cell;
				this.cellsToIndex.put(cell, new CellReference(i, j));
			}
		}

	}

	@Override
	public Double getValue(CellReference reference) {
		return this.getTableCellValue(reference.getColumnIndex(), reference.getRowIndex());
	}

	public Double getValue(int columnIndex, int rowIndex) {
		return this.getCell(columnIndex, rowIndex).getCachedValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getTableCellValue(int columnIndex, int rowIndex) {
		return (T) this.getCell(columnIndex, rowIndex).getCachedValue();
	}

	public void setExpression(CellReference cellReference, ExpressionTreeNode<CellReference> expression) {
		Cell cell = this.checkForCircularReference(cellReference, expression);
		this.clearCellLinks(cellReference);
		cell.setExpression(expression, this);
		Set<CellReference> currentCellDependsOn = cell.getExpression().getReferences();
		for (CellReference s : currentCellDependsOn) {
			Cell currentCellDependsOnCurrent = this.getCell(s);
			currentCellDependsOnCurrent.addObserver(cell);
		}
		cell.calculateValue(this);
	}

	@Override
	public ExpressionTreeNode<CellReference> getExpression(CellReference reference) {
		return this.getCell(reference).getExpression();

	}

	@Override
	public FormulaTable getReferenceGraph() {
		List<CellReference> result = new ArrayList<>();
		Set<CellReference> visited = new HashSet<>();
		Queue<CellReference> queue = new LinkedList<>();
		for (int col = 0; col < this.cells.length; col++) {
			for (int row = 0; row < this.cells[col].length; row++) {
				Cell cell = this.cells[col][row];
				ExpressionTreeNode<CellReference> expression = cell.getExpression();
				if (((expression != null) && expression.getReferences().isEmpty()) ||
						((expression == null) && !cell.getObservers().isEmpty())) {
					queue.add(new CellReference(col, row));
				}
			}
		}
		while (!queue.isEmpty()) {
			CellReference ref = queue.remove();
			if (visited.contains(ref)) {
				continue;
			}

			Cell cell = this.getCell(ref);
			ExpressionTreeNode<CellReference> currentCellDependencies = cell.getExpression();
			if ((currentCellDependencies == null) || visited.containsAll(currentCellDependencies.getReferences())) {
				visited.add(ref);
				result.add(ref);

				for (Cell observer : cell.getObservers()) {
					queue.add(this.cellsToIndex.get(observer));
				}
			} else {
				queue.add(ref);
			}

		}
		return new FormulaTable(this, result);
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
		for (CellReference c : currentCell.getReferences()) {
			this.getCell(c).removeObserver(currentCell);
		}
	}

	private <E> boolean containsAny(Set<E> first, Set<E> toCheckIfAnyElementIsContainedInFirstSet) {
		for (E e : toCheckIfAnyElementIsContainedInFirstSet) {
			if (first.contains(e)) {
				return true;
			}
		}
		return false;
	}

	private Set<Cell> getTransitiveDependancies(ExpressionTreeNode<CellReference> expression, Set<Cell> dependancies) {
		if (expression != null) {
			Set<Cell> cells = expression.getReferences().stream().map(e -> this.getCell(e)).collect(Collectors.toSet());
			dependancies.addAll(cells);
			for (Cell c : cells) {
				if (c.getExpression() != null) {
					this.getTransitiveDependancies(c.getExpression(), dependancies);
				}
			}
		}
		return dependancies;
	}

	private Cell getCell(CellReference reference) {
		return this.getCell(reference.getColumnIndex(), reference.getRowIndex());
	}

	private Cell getCell(int columnIndex, int rowIndex) {
		return this.cells[columnIndex][rowIndex];
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	// @Override
	// public <T> T getValue(int column, int row) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public String getFirstColumnValue(int row) {
		return String.valueOf(row + 1);
	}

}
