package enums;

import java.util.Comparator;

import entities.Project;

public enum ProjectProperties {
	ID((p1, p2) -> p1.getId().compareTo(p2.getId())), 
	TITLE((p1, p2) -> p1.getTitle().compareTo(p2.getTitle())),
	DESCRIPTION((p1, p2) -> p1.getDescription().compareTo(p2.getDescription())), 
	CUSTOMER((p1, p2) -> p1.getCustomer().compareTo(p2.getCustomer())), 
	STARTED((p1, p2) -> p1.getStarted().compareTo(p2.getStarted())), 
	STATUS((p1, p2) -> (p1.getStatus().toString()).compareTo((p2.getStatus().toString())));

	private Comparator<Project> comparator;

	ProjectProperties(Comparator<Project> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Project> getComparator() {
		return comparator;
	}

}
