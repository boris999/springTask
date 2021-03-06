package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.model.table.Table;
import com.serialize.expression.ConsoleInputParser;
import com.writer.FormulaPrinter;
import com.writer.Printer;
import com.writer.TablePrinter;

public class RunTable {

	public static void main(String[] args) throws IOException {
		int numberOfRows = Integer.parseInt(args[0]);
		int numberOfColumns = Integer.parseInt(args[1]);
		Table table = new Table(numberOfRows, numberOfColumns);
		Printer tablePrinter = new TablePrinter();
		Printer formPrinter = new FormulaPrinter();
		ConsoleInputParser parser = new ConsoleInputParser();
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Value");
			String s = br.readLine();
			if ("?".equals(s)) {
				formPrinter.printTable(table, new PrintWriter(System.out));
			} else {
				try {
					String expressionToPrint = parser.addCell(s, table);
					tablePrinter.printTable(table, new PrintWriter(System.out));
					if (expressionToPrint != null) {
						System.out.println(expressionToPrint);
					}
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Invalid cell reference");
				}
			}

		}
	}

}
