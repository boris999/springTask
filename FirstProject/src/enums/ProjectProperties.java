package enums;

import java.util.Comparator;
import java.util.function.Function;

import entities.Project;

public enum ProjectProperties {
	ID(Project::getId), 
	TITLE(Project::getTitle),
	DESCRIPTION(Project::getDescription), 
	CUSTOMER(Project::getCustomer), 
	STARTED(Project::getStarted), 
	STATUS(Comparator.comparing(Project::getStatus));

	private Comparator<Project> comparator;

	ProjectProperties(Function<? super Project, ? extends String> keyExtractor) {
		this.comparator = Comparator.comparing(keyExtractor);
	}

	ProjectProperties(Comparator<Project> comp) {
		this.comparator = comp;
	}
	public Comparator<Project> getEnumComparator() {
		return comparator;
	}

}
