package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objectBuilders.EmployeeObjectBuilder;
import objectBuilders.ObjectBuilder;
import objectBuilders.ProjectObjectBuilder;
import printers.EmployeePrinter;
import printers.ObjectPrinter;
import printers.ProjectPrinter;

public class Print {

	private static final String PROJECT = "project";
	private static final String EMPLOYEE = "employee";
	private static File directory;
	private static String typeOfTheFile;
	private static List<String> sortBy = new ArrayList<String>();

	public static <T> void main(String[] args) {
		try {
			checkArguments(args);
			boolean isEmployee = EMPLOYEE.equalsIgnoreCase(typeOfTheFile);
			boolean isProject = PROJECT.equalsIgnoreCase(typeOfTheFile);
			TxtFileReader txtReaderObject = new TxtFileReader();
			if (isEmployee) {
				EmployeeObjectBuilder converter = new EmployeeObjectBuilder();
				EmployeePrinter ep = new EmployeePrinter(txtReaderObject);
				printEntities(converter, sortBy, txtReaderObject, ep);
			}
			if (isProject) {
				ProjectObjectBuilder converter = new ProjectObjectBuilder();
				ProjectPrinter pp = new ProjectPrinter(txtReaderObject);
				printEntities(converter, sortBy, txtReaderObject, pp);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	private static void checkArguments(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException("Please provide enough parameters to run the program");
		}
		directory = new File(args[0]);
		if (!(directory.isDirectory() && directory.exists())) {
			throw new IllegalArgumentException("There is no such directory");
		}
		typeOfTheFile = args[1];
		if (!(typeOfTheFile.equalsIgnoreCase(EMPLOYEE) || typeOfTheFile.equalsIgnoreCase(PROJECT))) {
			throw new IllegalArgumentException("Please provide proper type for the files to read");
		}
		sortBy = new ArrayList<String>();
		for (int i = 2; i < args.length; i++) {
			sortBy.add(args[i]);
		}
	}

	private static <T> void printEntities(ObjectBuilder<T> converter, List<String> sortBy,
			TxtFileReader txtReaderObject, ObjectPrinter<T> objectPrinter) {
		CompoundComparator<T> cComparator = new CompoundComparator<>(converter.getComparator(sortBy));
		List<T> entities = txtReaderObject.readDirectory(directory, converter);
		Collections.sort(entities, cComparator);
		try (PrintWriter pw = new PrintWriter(System.out)) {
			objectPrinter.printObjects(entities, pw);
		}
	}

}
