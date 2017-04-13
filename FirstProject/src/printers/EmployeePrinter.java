package printers;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import main.Employee;
import main.TxtFileReader;

public class EmployeePrinter extends ObjectPrinter<Employee> {

	public EmployeePrinter(TxtFileReader textFileReader) {
		super(textFileReader);
	}

	@Override
	protected List<String> getColumnValues() {
		return Arrays.asList("Id", "First name", "Last name", "Age", "Professional experience", "Status");
	}

	@Override
	protected String getValue(String column, Employee item) {
		switch (column) {
		case "Id":
			return item.getId();

		case "First name":
			return item.getFirstName();

		case "Last name":
			return item.getLastName();

		case "Age":
			return item.getAge();

		case "Professional experience":
			return item.getProfessionalExpereince();

		case "Status":
			return item.getStatus().toString();

		}
		return null;
	}

	@Override
	public void printObject(int totalLength, Employee t, PrintWriter writer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<String> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

}
