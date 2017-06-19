package com.writer;

import java.io.PrintWriter;
import java.util.stream.IntStream;

import com.model.table.Table;
import com.serialize.expression.CellNameTransformer;

public class TablePrinter {
	private static final int ROW_NUMBER_COLUMN_WIDTH = 7;

	static final int FREE_SPACE_PER_CELL = 2;

	public void printTable(Table table, PrintWriter writer) {
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		int totalLength = this.calculateTotalLength(table, columnCount, rowCount);
		int maxCellWidth = this.calculateCellWidth(table, columnCount, rowCount);
		this.printHeader(totalLength, writer, maxCellWidth, columnCount);
		for (int i = 0; i < rowCount; i++) {
			this.printRow(i, table, writer, maxCellWidth, columnCount);
			writer.println();
			if (i != (rowCount - 1)) {

				this.printLine("-", totalLength, writer);
				writer.println();
			}
		}
		this.printFooter(totalLength, writer);
		writer.flush();
	}

	private int calculateTotalLength(Table table, int columnCount, int rowCount) {
		int totalLength = ROW_NUMBER_COLUMN_WIDTH;
		int maxColumnWidth = this.calculateCellWidth(table, columnCount, rowCount);
		totalLength += (maxColumnWidth + FREE_SPACE_PER_CELL + 1) * columnCount;
		return totalLength;
	}

	private void printHeader(int totalLength, PrintWriter writer, int maxCellWidth, int columnCount) {
		this.printLine("=", totalLength, writer);
		writer.println();
		writer.print("| ROW |");
		CellNameTransformer.calculateColumnNames().stream().limit(columnCount)
				.forEach(x -> writer.print(this.getFormatedString(x, maxCellWidth) + "|"));
		writer.println();
		this.printLine("=", totalLength, writer);
		writer.println();
	}

	private void printRow(int rowCount, Table table, PrintWriter writer, int maxCellWidth, int columnCount) {
		writer.print("|");
		writer.print(this.getFormatedrowCount(rowCount + 1));
		writer.print("|");
		for (int i = 0; i < columnCount; i++) {
			Double value = table.getValue(i, rowCount);
			writer.print(this.getFormatedString(this.printValue(value), maxCellWidth) + "|");
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

	private String getFormatedrowCount(int rowCount) {
		return String.format("%5s", String.valueOf(rowCount));
	}

	private String formaterForString(int maxCellWidth) {
		return "%" + (maxCellWidth + FREE_SPACE_PER_CELL) + "s";
	}

	private String getFormatedString(String toBeFormated, int maxCellWidth) {
		return String.format(this.formaterForString(maxCellWidth), toBeFormated);
	}

	private int calculateCellWidth(Table table, int columnCount, int rowCount) {
		Double maxValue = 0.0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
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

	private String printValue(Double value) {
		if (value != null) {
			return String.format("%.2f", value);
		} else {
			return "";
		}
	}
}
