package printers;

import java.util.Arrays;
import java.util.List;

import entities.Project;

public class ProjectPrinter extends ObjectPrinter<Project> {

	private static final String ID = "Id";
	private static final String TITLE = "Title";
	private static final String DESCRIPTION = "Description";
	private static final String CUSTOMER = "Customer";
	private static final String STARTED = "Started";
	private static final String STATUS = "Status";

	public ProjectPrinter(List<Project> entities) {
		super(entities);
	}

	@Override
	protected List<String> getColumns() {
		return Arrays.asList(ID, TITLE, DESCRIPTION, CUSTOMER, STARTED, STATUS);
	}

	@Override
	protected String getValue(String column, Project item) {
		switch (column) {
		case "Id":
			return item.getId();

		case "Title":
			return item.getTitle();

		case "Description":
			return item.getDescription();

		case "Customer":
			return item.getCustomer();

		case "Started":
			return item.getStarted();

		case "Status":
			return item.getStatus().toString();

		}
		return null;
	}
}
