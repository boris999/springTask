package other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Test {

	public static void main(String[] args) {
		Function<Employee, String> funcEmpToString = (e) -> {
			return e.getName();
		};
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45),
				new Employee("Harry Major", 25),
				new Employee("Ethan Hardy", 65),
				new Employee("Nancy Smith", 15),
				new Employee("Deborah Sprightly", 29));
		Function<String, String> initialFunction = (String s) -> s.substring(0, 1);
		List<String> empNameListInitials = convertEmpListToNamesList(employeeList,
				funcEmpToString.compose(makeYounger).andThen(initialFunction));
		empNameListInitials.forEach(str -> {
			System.out.print(" " + str);
		});
	}

	public static List<String> convertEmpListToNamesList(List<Employee> employeeList, Function<Employee, String> funcEmpToString) {
		List<String> empNameList = new ArrayList<>();
		for (Employee emp : employeeList) {
			empNameList.add(funcEmpToString.apply(emp));
		}
		return empNameList;
	}

	// public static String initialFunction(String name) {
	// return name.substring(0, 1);
	// }

	static Function<Employee, Employee> makeYounger = (Employee e) -> {
		if (e.getAge() > 30) {
			e.setAge(e.getAge() - 5);
		}
		return e;
	};

}
