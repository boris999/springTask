package com.writer;

import java.io.PrintWriter;
import java.util.stream.IntStream;

import com.model.table.Table;

public class TablePrinter {
	static final int FREE_SPACE_PER_CELL = 2;
	private final Table table;
	private final PrintWriter writer;
	private int totalLength;

	public TablePrinter(Table table, PrintWriter writer) {
		this.table = table;
		this.writer = writer;
		this.totalLength = this.calculateTotalLength();
	}

	public void printTable() {
		this.totalLength = this.calculateTotalLength();
		this.printHeader();
		for (int i = 1; i <= this.table.getNumberOfRows(); i++) {
			this.printRow(i);
			this.writer.println();
			if (i != this.table.getNumberOfRows()) {

				this.printLine("-", this.totalLength);
				this.writer.println();
			}
		}
		this.printFooter();
		this.writer.flush();
	}

	private int calculateTotalLength() {
		int totalLength = 7;
		int numberOfColumns = this.table.getNumberOfColumns();
		int maxColumnWidth = this.table.calculateCellWidth();
		totalLength += (maxColumnWidth + FREE_SPACE_PER_CELL + 1) * numberOfColumns;
		return totalLength;
	}

	private void printHeader() {
		this.printLine("=", this.totalLength);
		this.writer.println();
		this.writer.print("| ROW |");
		this.table.getColumnNames().stream().forEach(x -> this.writer.print(this.getFormatedString(x) + "|"));
		this.writer.println();
		this.printLine("=", this.totalLength);
		this.writer.println();
	}

	private void printRow(int rowNumber) {
		this.writer.print("|");
		this.writer.print(this.getFormatedRowNumber(rowNumber));
		this.writer.print("|");
		for (String s : this.table.getRowValues(rowNumber)) {
			this.writer.print(this.getFormatedString(s));
			this.writer.print("|");
		}

	}

	private void printFooter() {
		this.printLine("=", this.totalLength);
		this.writer.println();
	}

	private void printLine(String s, int howLong) {
		IntStream.rangeClosed(1, howLong)
				.mapToObj(i -> String.valueOf(s))
				.forEach(x -> this.writer.print(x));

	}

	private String getFormatedRowNumber(int rowNumber) {
		return String.format("%5s", String.valueOf(rowNumber));
	}

	private String formaterForString() {
		return "%" + (this.table.calculateCellWidth() + FREE_SPACE_PER_CELL) + "s";
	}

	private String getFormatedString(String toBeFormated) {
		return String.format(this.formaterForString(), toBeFormated);
	}
}
