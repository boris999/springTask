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
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public int getAge() {
		return this.age;
	}

	public int getProfessionalExpereince() {
		return this.professionalExpereince;
	}

	public EmployeeStatus getStatus() {
		return this.status;
	}

	public String getId() {
		return this.id;
	}

	public static List<Comparator<Employee>> getComparatorList(List<EmployeeProperties> properties) {
		List<Comparator<Employee>> employeeComaratorList = new ArrayList<>();
		for (EmployeeProperties ep : properties) {
			employeeComaratorList.add(ep.getEnumComparator());
		}
		return employeeComaratorList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.age;
		result = (prime * result) + ((this.firstName == null) ? 0 : this.firstName.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.lastName == null) ? 0 : this.lastName.hashCode());
		result = (prime * result) + this.professionalExpereince;
		result = (prime * result) + ((this.status == null) ? 0 : this.status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		if (this.age != other.age) {
			return false;
		}
		if (this.firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!this.firstName.equals(other.firstName)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!this.lastName.equals(other.lastName)) {
			return false;
		}
		if (this.professionalExpereince != other.professionalExpereince) {
			return false;
		}
		if (this.status != other.status) {
			return false;
		}
		return true;
	}

}
