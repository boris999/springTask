package com.model.table;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.model.expression.ExpressionTreeNode;
import com.serialize.expression.ExpressionTreeFactory;

public class Cell {

	private final String name;
	private Double value;
	private int row;
	private String column;
	private ExpressionTreeNode nodeTree;
	public static final Pattern cellNamePattern;
	private Set<Cell> cellsDependingOnThisCell = new HashSet<>();
	private Set<Cell> cellDependsOn = new HashSet<>();

	static {
		cellNamePattern = Pattern.compile("([\\w&&[^\\d]]{1,3}+)(\\d{1,6}+)", Pattern.CASE_INSENSITIVE);
	}

	public Cell(String cellName, String expression) {
		this.name = cellName;
		this.nodeTree = ExpressionTreeFactory.parseExpression(expression);
	}

	public Cell(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public Cell(String cellName) {
		this.name = cellName;

	}

	public Cell(String name, Double value, ExpressionTreeNode nodeTree) {
		this.name = name;
		this.value = value;
		this.nodeTree = nodeTree;
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

	private void notifyOtherCellsAboutChangeInThisCell(Map<String, Cell> cellMap) {
		for (Cell c : this.cellsDependingOnThisCell) {
			c.calculateAndReturnValue(cellMap);
			c.notifyOtherCellsAboutChangeInThisCell(cellMap);
		}
	}

	private void calculateAndReturnValue(Map<String, Cell> cellMap) {
		try {
			this.value = this.nodeTree.getValue(cellMap);
		} catch (NullPointerException e) {
			this.value = null;
		}
	}

	public Set<Cell> getDependingRefferenceNodes() {
		return this.nodeTree.getDependingCells();
	}

	public String getName() {
		return this.name;
	}

	public Double getValue(Map<String, Cell> cellMap) {
		if (this.value == null) {
			if (this.nodeTree == null) {
				return null;
			} else {
				this.calculateAndReturnValue(cellMap);
			}
		}
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

	public ExpressionTreeNode getNodeTree() {
		return this.nodeTree;
	}

	public Set<Cell> getCellsDependingOnThisCell() {
		return this.cellsDependingOnThisCell;
	}

	public Set<Cell> getCellDependsOn() {
		return this.cellDependsOn;
	}

	public void setNodeTree(ExpressionTreeNode nodeTree, Map<String, Cell> cellMap) {
		this.nodeTree = nodeTree;
		this.value = nodeTree.getValue(cellMap);
		this.notifyOtherCellsAboutChangeInThisCell(cellMap);
	}

	public void setValue(Double value, Map<String, Cell> cellMap) {
		this.value = value;
		this.nodeTree = null;
		this.notifyOtherCellsAboutChangeInThisCell(cellMap);
	}

}
