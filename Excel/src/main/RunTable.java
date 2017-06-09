package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.model.table.Table;
import com.serialize.expression.ConsoleInputParser;
import com.writer.TablePrinter;

public class RunTable {

	public static void main(String[] args) throws IOException {
		int numberOfRows = Integer.parseInt(args[0]);
		int numberOfColumns = Integer.parseInt(args[1]);
		Table table = new Table(numberOfRows, numberOfColumns);
		TablePrinter tp = new TablePrinter();
		ConsoleInputParser cip = new ConsoleInputParser();
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Value");
			String s = br.readLine();
			try {
				cip.addCell(s, table);
				tp.printTable(table, new PrintWriter(System.out));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println("Invalid cell reference");
			}

		}
	}

}
