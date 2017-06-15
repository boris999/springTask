package com.writer;

import java.io.PrintWriter;
import java.util.stream.IntStream;

import com.model.table.Table;
import com.serialize.expression.CellNameTransformer;

public class TablePrinter {
	private static final int ROW_NUMBER_COLUMN_WIDTH = 7;

	static final int FREE_SPACE_PER_CELL = 2;

	public void printTable(Table table, PrintWriter writer) {
		int totalLength = this.calculateTotalLength(table);
		int maxCellWidth = this.calculateCellWidth(table);
		this.printHeader(totalLength, table, writer, maxCellWidth);
		for (int i = 0; i < table.getRowCount(); i++) {
			this.printRow(i, table, writer, maxCellWidth);
			writer.println();
			if (i != (table.getRowCount() - 1)) {

				this.printLine("-", totalLength, writer);
				writer.println();
			}
		}
		this.printFooter(totalLength, writer);
		writer.flush();
	}

	private int calculateTotalLength(Table table) {
		int totalLength = ROW_NUMBER_COLUMN_WIDTH;
		int numberOfColumns = table.getColumnCount();
		int maxColumnWidth = this.calculateCellWidth(table);
		totalLength += (maxColumnWidth + FREE_SPACE_PER_CELL + 1) * numberOfColumns;
		return totalLength;
	}

	private void printHeader(int totalLength, Table table, PrintWriter writer, int maxCellWidth) {
		this.printLine("=", totalLength, writer);
		writer.println();
		writer.print("| ROW |");
		CellNameTransformer.calculateColumnNames().stream().limit(table.getColumnCount())
				.forEach(x -> writer.print(this.getFormatedString(x, maxCellWidth) + "|"));
		writer.println();
		this.printLine("=", totalLength, writer);
		writer.println();
	}

	private void printRow(int rowNumber, Table table, PrintWriter writer, int maxCellWidth) {
		writer.print("|");
		writer.print(this.getFormatedRowNumber(rowNumber + 1));
		writer.print("|");
		for (int i = 0; i < table.getColumnCount(); i++) {
			Double value = table.getValue(i, rowNumber);
			writer.print(this.getFormatedString(this.printValue(value, table), maxCellWidth) + "|");
		}
	}

	private void printFooter(int totalLength, PrintWriter writer) {
		this.printLine("=", totalLength, writer);
		writer.println();
	}

	private void printLine(String s, int howLong, PrintWriter writer) {
		IntStream.rangeClosed(1, howLong)
				.mapToObj(String::valueOf)
				.forEach(x -> writer.print(s));

	}

	private String getFormatedRowNumber(int rowNumber) {
		return String.format("%5s", String.valueOf(rowNumber));
	}

	private String formaterForString(int maxCellWidth) {
		return "%" + (maxCellWidth + FREE_SPACE_PER_CELL) + "s";
	}

	private String getFormatedString(String toBeFormated, int maxCellWidth) {
		return String.format(this.formaterForString(maxCellWidth), toBeFormated);
	}

	private int calculateCellWidth(Table table) {
		Double maxValue = 0.0;
		for (int row = 0; row < table.getRowCount(); row++) {
			for (int column = 0; column < table.getColumnCount(); column++) {
				Double value = table.getValue(column, row);
				if (value == null) {
					continue;
				}
				Double currentCellValue = value >= 0 ? value
						: -value;
				if (currentCellValue > maxValue) {
					maxValue = currentCellValue;
				}
			}
		}
		return String.format("%.2f", maxValue).length();
	}

	private String printValue(Double value, Table table) {
		if (value != null) {
			return String.format("%.2f", value);
		} else {
			return "";
		}
	}
}
