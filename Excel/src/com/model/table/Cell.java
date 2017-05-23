package com.model.table;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.expression.ExpressionTreeNode;
import com.serialize.expression.ExpressionTreeFactory;

public class Cell {

	private final String name;
	private double value;
	private int row;
	private String column;
	ExpressionTreeNode nodeTree;
	private static Pattern cellNamePattern;

	static {
		cellNamePattern = Pattern.compile("([\\w&&[^\\d]])(\\d)", Pattern.CASE_INSENSITIVE);
	}

	Cell(String cellName, String expression) {
		this.name = cellName;
		this.nodeTree = ExpressionTreeFactory.parseExpression(expression);
	}

	public Cell(String cellName) {
		this.name = cellName;

	}

	public Set<Cell> getDependingRefferenceNodes() {
		return this.nodeTree.getDependingCells();
	}

	public String getName() {
		return this.column + this.row;
	}

	public double getValue() {
		return this.value;
	}

	public int getRow() {
		this.initializeRowAndColumn();
		return this.row;
	}

	public String getColumn() {
		this.initializeRowAndColumn();
		return this.column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
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
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	private void initializeRowAndColumn() {
		Matcher matcher = cellNamePattern.matcher(this.name);
		if (matcher.matches()) {
			this.column = matcher.group(1);
			this.row = Integer.parseInt(matcher.group(2));
		}
	}

}
