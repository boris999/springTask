package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.EmployeeProperties;
import enums.EmployeeStatus;

public final class Employee {
	private String id;
	private final String firstName;
	private final String lastName;
	private final int age;
	private final int professionalExpereince;
	private final EmployeeStatus status;

	public Employee(String id, String first_name, String last_name, int age, int professional_expereince,
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

	public int getAge() {
		return age;
	}

	public int getProfessionalExpereince() {
		return professionalExpereince;
	}

	public EmployeeStatus getStatus() {
		return status;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + professionalExpereince;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (professionalExpereince != other.professionalExpereince)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	
	
}
