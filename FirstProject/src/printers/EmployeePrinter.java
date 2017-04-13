package printers;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import main.Employee;
import main.EmployeeProperties;
import main.TxtFileReader;

public class EmployeePrinter extends ObjectPrinter<Employee> {

	public EmployeePrinter(TxtFileReader textFileReader) {
		super(textFileReader);
	}

	@Override
	protected List<String> getColumns() {
		return Arrays.asList("Id", "First name", "Last name", "Age", "Professional experience", "Status");
	}

	@Override
	protected String getValue(String column, Employee item) {
		switch (column) {
		case "Id":
			return String.format(formatString(EmployeeProperties.ID), item.getId());

		case "First name":
			return String.format(formatString(EmployeeProperties.FIRST_NAME), item.getFirstName());

		case "Last name":
			return String.format(formatString(EmployeeProperties.LAST_NAME), item.getLastName());

		case "Age":
			return String.format(formatString(EmployeeProperties.AGE), item.getAge());

		case "Professional experience":
			return String.format(formatString(EmployeeProperties.PROFESSIONAL_EXPERIENCE), item.getProfessionalExpereince());

		case "Status":
			return String.format(formatString(EmployeeProperties.STATUS), item.getStatus().toString());

		}
		return null;
	}

	@Override
	public void printObject(int totalLength, Employee t, PrintWriter writer) {
		writer.print("|");
		for (String s : getColumns()) {
			writer.print(getValue(s, t));
			writer.print("|");
		}
		writer.println();
		printLine("-", totalLength, writer);
		writer.println();
		
	}

	
	@Override
	protected List<String> getColumnValues() {
		return Arrays.asList(String.format(formatString(EmployeeProperties.ID), "Id"),
				String.format(formatString(EmployeeProperties.FIRST_NAME), "Title"),
				String.format(formatString(EmployeeProperties.FIRST_NAME), "Description"),
				String.format(formatString(EmployeeProperties.AGE), "Customer"),
				String.format(formatString(EmployeeProperties.PROFESSIONAL_EXPERIENCE), "Started"),
				String.format(formatString(EmployeeProperties.STATUS), "Status"));
	}

	private String formatString(EmployeeProperties pp) {
		return "%" + (getTextFileReader().getWordLengthMap().get(pp) + FREE_SPACE_PER_CELL) + "s";
	}
}
