package main;

import java.util.Comparator;

public enum ProjectProperties {
	ID(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}), TITLE(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getTitle().compareTo(o2.getTitle());
		}
	}), DESCRIPTION(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getDescription().compareTo(o2.getDescription());
		}
	}), CUSTOMER(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getCustomer().compareTo(o2.getCustomer());
		}
	}), STARTED(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getStarted().compareTo(o2.getStarted());
		}
	}), STATUS(new Comparator<Project>() {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.getStatus().toString().compareTo(o2.getStatus().toString());
		}
	});

	private Comparator<Project> comparator;

	ProjectProperties(Comparator<Project> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Project> getComparator() {
		return comparator;
	}
	
}
