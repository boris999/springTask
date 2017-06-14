package com.model.table;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceContext;

public class Cell {

	private final CellReference cellReference;
	private Double cachedValue;
	private ExpressionTreeNode<CellReference> expression;
	private Set<Cell> obsevers = new HashSet<>();
	private Set<Cell> dependencies = new HashSet<>();

	Cell(CellReference cellReference) {
		this.cellReference = cellReference;
	}

	public Set<CellReference> getDependanciesReferences(ReferenceContext<CellReference> context) {
		if (this.expression != null) {
			return this.expression.getTransitiveReferences(context);
		}
		return new HashSet<>();
	}

	public Double getValue(ReferenceContext<CellReference> context) {
		if (this.cachedValue == null) {
			if (this.expression == null) {
				return null;
			} else {
				this.calculateValue(context);
			}
		}
		return this.cachedValue;
	}

	public Set<Cell> getObsevers() {
		return this.obsevers;
	}

	public Set<CellReference> getObseverReferences() {
		return this.obsevers.stream().map(e -> e.getCellReference()).collect(Collectors.toSet());

	}

	public Set<Cell> getCellDependsOn() {
		return this.dependencies;
	}

	public ExpressionTreeNode<CellReference> getExpression() {
		return this.expression;
	}

	public void setNodeTree(ExpressionTreeNode<CellReference> expression, ReferenceContext<CellReference> context) {
		this.expression = expression;
		this.cachedValue = expression.getValue(context);
	}

	public void setValue(Double value, ReferenceContext<CellReference> context) {
		this.cachedValue = value;
		this.expression = null;
	}

	void calculateValue(ReferenceContext<CellReference> context) {
		if (this.expression != null) {
			this.cachedValue = this.expression.getValue(context);
			for (Cell observer : this.obsevers) {
				context.getCell(observer.getCellReference()).calculateValue(context);
			}
		}
	}

	public CellReference getCellReference() {
		return this.cellReference;
	}

	public Set<Cell> getTransitiveObservers(ReferenceContext<CellReference> context, Set<Cell> cells) {
		for (Cell current : context.getCells(this.obsevers)) {
			if (cells.contains(current)) {
				return cells;
			} else {
				cells.add(current);
				return this.getTransitiveObservers(context, cells);
			}
		}
		return cells;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.cellReference == null) ? 0 : this.cellReference.hashCode());
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
		Cell other = (Cell) obj;
		if (this.cellReference == null) {
			if (other.cellReference != null) {
				return false;
			}
		} else if (!this.cellReference.equals(other.cellReference)) {
			return false;
		}
		return true;
	}

}
