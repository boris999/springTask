package com.model.table;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.model.expression.ExpressionTreeNode;
import com.serialize.expression.ExpressionTreeFactory;

public class Table {

	private int numberOfRows;
	private int numberOfColumns;
	private static final List<String> alphabet;
	private Map<String, Cell> cellMap = new HashMap<>();
	private final Set<String> cellNames;
	private final List<String> columnNames;

	static {
		alphabet = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z");
	}

	public Table(int numberOfRows, int numberOfColums) {
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColums;
		this.cellNames = this.fillCellNamesSetAndCellMap();
		this.columnNames = this.calculateColumnNames();
	}

	public Map<String, Cell> addCell(String expression) {
		String[] array = expression.split("=");
		String cellName = array[0].trim().toUpperCase();
		if ((array.length == 1) && this.cellNames.contains(cellName)) {
			System.out.println(cellName + "=" + this.cellMap.get(cellName).getNodeTree());
			return this.cellMap;
		}
		if ((array.length != 2) || !this.cellNames.contains(cellName)) {
			throw new IllegalArgumentException("Enter valid argument!");
		}
		String cellValue = array[1].trim().toUpperCase();
		Double value = null;
		ExpressionTreeNode cellNodeTree = null;
		try {
			value = Double.parseDouble(cellValue);
		} catch (NumberFormatException e) {
			cellNodeTree = ExpressionTreeFactory.parseExpression(cellValue);
		}
		Cell currentCell = this.cellMap.get(cellName);
		if (currentCell == null) {
			currentCell = new Cell(cellName);
		}
		if (value == null) {// Duplicate?
			// remove old links
			this.clearCellLinks(currentCell);
			currentCell.setNodeTree(cellNodeTree, this.cellMap);
			// set new links
			Set<Cell> currentCellDependsOn = currentCell.getNodeTree().getDependingCells();
			for (Cell c : currentCellDependsOn) {
				this.cellMap.get(c.getName()).getCellsDependingOnThisCell().add(currentCell);
				c.getCellsDependingOnThisCell().add(currentCell);
			}
			currentCell.getCellDependsOn().addAll(currentCellDependsOn);
		} else {
			currentCell.setValue(value, this.cellMap);
			this.clearCellLinks(currentCell);
		}
		this.cellMap.put(cellName, currentCell);
		return this.cellMap;
	}

	private void clearCellLinks(Cell currentCell) {
		for (Cell c : currentCell.getCellDependsOn()) {
			c.getCellsDependingOnThisCell().remove(currentCell);
			this.cellMap.get(c.getName()).getCellsDependingOnThisCell().remove(currentCell);
		}
		currentCell.getCellDependsOn().clear();
	}

	private List<String> calculateColumnNames() {
		List<String> copy = new LinkedList<>(alphabet);
		if (this.numberOfColumns <= alphabet.size()) {
			while (true) {
				try {
					copy.remove(this.numberOfColumns);
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			return copy;
		}
		int alphabetIndex = 0;
		for (int i = 0; i < (this.numberOfColumns - alphabet.size()); i++) {
			alphabetIndex = i / alphabet.size();
			copy.add(alphabet.get(alphabetIndex) + alphabet.get(i % alphabet.size()));

		}
		return Collections.unmodifiableList(copy);
	}

	// TODO with stream()
	private Set<String> fillCellNamesSetAndCellMap() {
		Set<String> cellNames = new HashSet<>();
		List<String> columnNames = this.calculateColumnNames();
		for (int i = 1; i <= this.numberOfRows; i++) {
			for (String s : columnNames) {
				String currentCellName = s + String.valueOf(i);
				cellNames.add(currentCellName);
				this.cellMap.put(currentCellName, new Cell(currentCellName, null, null));
			}
		}
		return Collections.unmodifiableSet(cellNames);
	}

	// TODO with stream()
	public int calculateCellWidth() {
		Double maxValue = 0.0;
		for (Entry<String, Cell> entry : this.cellMap.entrySet()) {
			if ((entry.getValue().getValue(this.cellMap) != null) && (entry.getValue().getValue(this.cellMap) > maxValue)) {
				maxValue = entry.getValue().getValue(this.cellMap);
			}
		}
		return String.format("%.2f", maxValue).length();
	}

	public List<String> getRowValues(int rowNumber) {
		return this.cellMap.entrySet().stream()
				.filter(x -> (x.getValue().getRow() == rowNumber))
				.sorted((e1, e2) -> this.compareCellsInSameRow(e1.getKey(), e2.getKey()))
				.map(e -> this.printDouble(e.getValue().getValue(this.cellMap)))
				.collect(Collectors.toList());
	}

	private String printDouble(Double d) {
		if (d == null) {
			return "";
		} else {
			return String.format("%.2f", d);
		}
	}

	public int getNumberOfRows() {
		return this.numberOfRows;
	}

	public int getNumberOfColumns() {
		return this.numberOfColumns;
	}

	public Set<String> getCellNames() {
		return this.cellNames;
	}

	public List<String> getColumnNames() {
		return this.columnNames;
	}

	private int compareCellsInSameRow(String cellName1, String cellName2) {
		if (cellName1.length() != cellName2.length()) {
			return cellName1.length() - cellName2.length();
		} else {
			return cellName1.compareTo(cellName2);
		}
	}
}
