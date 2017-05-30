package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.model.table.Table;
import com.writer.TablePrinter;

public class RunTable {

	public static void main(String[] args) throws IOException {
		int numberOfRows = Integer.parseInt(args[0]);
		int numberOfColumns = Integer.parseInt(args[1]);
		Table table = new Table(numberOfRows, numberOfColumns);
		TablePrinter tp = new TablePrinter(table, new PrintWriter(System.out));
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Value");
			String s = br.readLine();
			try {
				table.addCell(s);
				tp.printTable();
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
