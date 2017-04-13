package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Employee {
	private String id;
	private final String firstName;
	private final String lastName;
	private final String age;
	private final String professionalExpereince;
	private final EmployeeStatus status;

	public Employee(String id, String first_name, String last_name, String age, String professional_expereince,
			EmployeeStatus status) {
		this.id = id;
		this.firstName = first_name;
		this.lastName = last_name;
		this.age = age;
		this.professionalExpereince = professional_expereince;
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAge() {
		return age;
	}

	public String getProfessionalExpereince() {
		return professionalExpereince;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", first_name=" + firstName + ", last_name=" + lastName + ", age=" + age
				+ ", professional_expereince=" + professionalExpereince + ", status=" + status + "]";
	}

	public String getId() {
		return id;
	}

	public static List<Comparator<Employee>> getComparatorList(List<EmployeeProperties> properties) {
		List<Comparator<Employee>> employeeComaratorList = new ArrayList<Comparator<Employee>>();
		for (EmployeeProperties ep : properties) {
			employeeComaratorList.add(ep.getComparator());
		}
		return employeeComaratorList;
	}



	public String getPropertyValue(EmployeeProperties propertyName) {
		switch (propertyName) {
		case ID:
			return id;

		case FIRST_NAME:
			return firstName;

		case LAST_NAME:
			return lastName;

		case AGE:
			return age;

		case PROFESSIONAL_EXPERIENCE:
			return professionalExpereince;

		case STATUS:
			return status.toString();

		}
		return null;
	}

}
