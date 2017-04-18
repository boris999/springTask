package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CompoundComparator<T> implements Comparator<T> {

	private List<Comparator<T>> comparatorsList = new ArrayList<Comparator<T>>();

	public CompoundComparator(List<Comparator<T>> listOfComparators) {
		this.comparatorsList = listOfComparators;
	}

	@Override
	public int compare(T arg0, T arg1) {
		int result = 0;
		for (Comparator<T> comp : comparatorsList) {
			result = comp.compare(arg0, arg1);
			if (result != 0) {
				break;
			}

		}
		return result;
	}

	

	
	
	
}
