package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.ProjectProperties;
import enums.ProjectStatus;

public final class Project {
	private final String id;
	private final String title;
	private final String description;
	private final String customer;
	private final String started;
	private final ProjectStatus status;

	public Project(String id, String title, String description, String customer, String started, ProjectStatus status) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.customer = customer;
		this.started = started;
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCustomer() {
		return this.customer;
	}

	public String getStarted() {
		return this.started;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public String getId() {
		return this.id;
	}

	public static List<Comparator<Project>> getComparatorList(List<ProjectProperties> properties) {
		List<Comparator<Project>> projectComaratorList = new ArrayList<>();
		for (ProjectProperties pp : properties) {
			projectComaratorList.add(pp.getEnumComparator());
		}
		return projectComaratorList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = (prime * result) + ((this.description == null) ? 0 : this.description.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.started == null) ? 0 : this.started.hashCode());
		result = (prime * result) + ((this.status == null) ? 0 : this.status.hashCode());
		result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
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
		Project other = (Project) obj;
		if (this.customer == null) {
			if (other.customer != null) {
				return false;
			}
		} else if (!this.customer.equals(other.customer)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.started == null) {
			if (other.started != null) {
				return false;
			}
		} else if (!this.started.equals(other.started)) {
			return false;
		}
		if (this.status != other.status) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		return true;
	}

}
