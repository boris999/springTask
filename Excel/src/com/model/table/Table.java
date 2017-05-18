package com.model.table;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Table {
	private Map<String, Cell> cellsWithValues;
	private Map<String, Cell> allPopulatedCells;
	private int numberOfRows;
	private int numberOfColums;
	private static final List<String> alphabet;

	private static final Pattern cellNamePattern;
	static {
		cellNamePattern = Pattern.compile("([\\w&&[^\\d]])(\\d)", Pattern.CASE_INSENSITIVE);
		alphabet = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z");
	}

	// (A3-B5)*C8-A2
	public Double calculateValue(Cell cell) throws ScriptException {
		String cellExpression = cell.getExpression();
		String[] cells = cellExpression.split("[+-]");
		for (String s : cells) {
			if (!this.cellsWithValues.keySet().contains(new Cell(s))) {
				return null;
			}
		}
		Matcher matcher = cellNamePattern.matcher(cellExpression);
		while (matcher.find()) {
			cellExpression.replaceFirst(matcher.group(1), String.valueOf(this.cellsWithValues.get(matcher.group(1)).getValue()));
		}
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		return (Double) engine.eval(cellExpression);
	}

	public void addCellToTables(String expression) throws ScriptException {
		String name = expression.split("=")[0];
		String cellExpression = expression.split("=")[1];
		Cell currentCell = new Cell(name, cellExpression);
		Double cellValue = this.calculateValue(currentCell);
		this.allPopulatedCells.put(name, currentCell);
		if (cellValue != null) {
			this.cellsWithValues.put(name, currentCell);
		} else {
			this.cellsWithValues.remove(name);
		}
	}

	public List<String> getColumnNames(int numberOfColumns) {
		List<String> copy = new LinkedList<>(alphabet);
		if (numberOfColumns <= alphabet.size()) {
			while (true) {
				try {
					copy.remove(numberOfColumns);
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			return copy;
		}
		int alphabetIndex = 0;
		for (int i = 0; i < (numberOfColumns - alphabet.size()); i++) {
			alphabetIndex = i / alphabet.size();
			copy.add(alphabet.get(alphabetIndex) + alphabet.get(i % alphabet.size()));

		}
		return copy;

	}
}
