package printers;

import java.util.Arrays;
import java.util.List;

import entities.Employee;

public class EmployeePrinter extends ObjectPrinter<Employee> {
	private static final String ID = "Id";
	private static final String FIRST_NAME = "First name";
	private static final String LAST_NAME = "Last name";
	private static final String AGE = "Age";
	private static final String PROFESSIONAL_EXPERIENCE = "Professional experience";
	private static final String STATUS = "Status";


	public EmployeePrinter(List<Employee> entities) {
		super(entities);
		
	}

	@Override
	protected List<String> getColumns() {
		return Arrays.asList(ID, FIRST_NAME, LAST_NAME, AGE, PROFESSIONAL_EXPERIENCE, STATUS);
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
			return String.valueOf(item.getAge());

		case "Professional experience":
			return String.valueOf(item.getProfessionalExpereince());

		case "Status":
			return item.getStatus().toString();

		}
		return null;
	}
}
