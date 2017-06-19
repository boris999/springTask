package com.model.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceContext;

class Cell {

	private Double cachedValue;
	private ExpressionTreeNode<CellReference> expression;
	private Set<Cell> obsevers = new HashSet<>();

	public Set<CellReference> getDependanciesReferences(ReferenceContext<CellReference> context) {
		if (this.expression != null) {
			return this.expression.getDirectReferences(context);
		}
		return new HashSet<>();
	}

	public Double getCachedValue() {
		return this.cachedValue;
	}

	public void addObserver(Cell observer) {
		this.obsevers.add(observer);
	}

	public void removeObserver(Cell observer) {
		this.obsevers.remove(observer);
	}

	public Set<Cell> getObservers() {
		return Collections.unmodifiableSet(this.obsevers);

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

}
