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

	public Double getCachedValue(ReferenceContext<CellReference> context) {
		return this.cachedValue;
	}

	public void addObserver(Cell observer) {
		this.obsevers.add(observer);
	}

	public void removeObserver(Cell observer) {
		this.obsevers.remove(observer);
	}

	public Set<CellReference> getObseverReferences() {
		return this.obsevers.stream().map(e -> e.getCellReference()).collect(Collectors.toSet());

	}

	public void addDependancy(Cell dependancy) {
		this.dependencies.add(dependancy);
	}

	public void clearDependancies() {
		this.dependencies.clear();
	}

	public ExpressionTreeNode<CellReference> getExpression() {
		return this.expression;
	}

	public void setExpression(ExpressionTreeNode<CellReference> expression, ReferenceContext<CellReference> context) {
		this.expression = expression;
		this.calculateValue(context);
	}

	void calculateValue(ReferenceContext<CellReference> context) {
		if (this.expression != null) {
			this.cachedValue = this.expression.getValue(context);
			for (Cell observer : this.obsevers) {
				observer.calculateValue(context);
			}
		}
	}

	public CellReference getCellReference() {
		return this.cellReference;
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
