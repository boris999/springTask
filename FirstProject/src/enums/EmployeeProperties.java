package enums;

import java.util.Comparator;

import entities.Employee;

public enum EmployeeProperties {
	ID(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}), FIRST_NAME(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getFirstName().compareTo(o2.getFirstName());
		}
	}), LAST_NAME(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getLastName().compareTo(o2.getLastName());
		}
	}), AGE(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getAge().compareTo(o2.getAge());
		}
	}), PROFESSIONAL_EXPERIENCE(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getProfessionalExpereince().compareTo(o2.getProfessionalExpereince());
		}
	}), STATUS(new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getStatus().toString().compareTo(o2.getStatus().toString());
		}
	});

	private Comparator<Employee> comparator;

	EmployeeProperties(Comparator<Employee> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Employee> getComparator() {
		return comparator;
	}
	
}
