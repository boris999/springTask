package enums;

import java.util.Comparator;

import entities.Employee;

public enum EmployeeProperties {
	ID(Comparator.comparing(Employee::getId)),
	FIRST_NAME(Comparator.comparing(Employee::getFirstName)),
	LAST_NAME(Comparator.comparing(Employee::getLastName)),
	AGE(Comparator.comparing(Employee::getAge)),
	PROFESSIONAL_EXPERIENCE(Comparator.comparing(Employee::getProfessionalExpereince)),
	STATUS(Comparator.comparing(Employee::getStatus));

	private final Comparator<Employee> comparator;

	EmployeeProperties(Comparator<Employee> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Employee> getEnumComparator() {
		return this.comparator;
	}

}
