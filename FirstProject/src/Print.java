import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import exceptions.DirectoryDoesNotExistsException;
import exceptions.NotEnoughParametersException;
import exceptions.WrongFileTypeException;

import objectBuilders.ObjectBuilder;

public class Print {

	private static final String PROJECT = "project";
	private static final String EMPLOYEE = "employee";
	private static List<Employee> employees;
	private static List<Project> projects;
	private static File directory;
	private static String typeOfTheFile;
	private static List<String> sortBy = new ArrayList<String>();

	public static <T> void main(String[] args) {
			try {
				checkArguments(args);
				boolean isEmployee = EMPLOYEE.equalsIgnoreCase(typeOfTheFile);
				boolean isProject = PROJECT.equalsIgnoreCase(typeOfTheFile);
				if (isEmployee) {
					printEntities(new EmployeeObjectBuilder(), sortBy);
				}
				if (isProject) {
					printEntities(new ProjectObjectBuilder(), sortBy);;
				}
	
			} catch (NotEnoughParametersException | DirectoryDoesNotExistsException | WrongFileTypeException e) {
				e.printStackTrace();
			}
	
	}

	private static void checkArguments(String[] args)
			throws NotEnoughParametersException, DirectoryDoesNotExistsException, WrongFileTypeException {
		if (args.length < 3) {
			throw new NotEnoughParametersException("Please provide enough parameters to run the program");
		}
		directory = new File(args[0]);
		if (!(directory.isDirectory() && directory.exists())) {
			throw new DirectoryDoesNotExistsException("There is no such directory");
		}
		typeOfTheFile = args[1];
		if (!(typeOfTheFile.equalsIgnoreCase(EMPLOYEE) || typeOfTheFile.equalsIgnoreCase(PROJECT))) {
			throw new WrongFileTypeException("Please provide proper type for the files to read");
		}
		sortBy = new ArrayList<String>();
		for (int i = 2; i < args.length; i++) {
			sortBy.add(args[i]);
		}
	}

	private static <T> void printEntities(ObjectBuilder<T> converter, List<String> sortBy) {
		TxtFileReader txtReaderObject = new TxtFileReader();
		List<T> entities = txtReaderObject.readDirectory(directory, converter);
		Map<?, Integer> wordLengthMap = txtReaderObject.getWordLengthMap();
		//Map<entities.get(0).getStatus(), Integer> test
		CompoundComparator<T> cComparator = new CompoundComparator<>(converter.getComparator(sortBy));
		Collections.sort(entities, cComparator);
		converter.printObjects(entities, wordLengthMap);

	}

}
