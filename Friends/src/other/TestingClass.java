package other;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestingClass {

	public static void main(String[] args) {
		Predicate<String> test = s -> s.length() > 10;
		Supplier<String> sup1 = () -> "Boris";
		Supplier<String> supTime = () -> String.valueOf(System.currentTimeMillis());
		Function<String, String> eatLast = s -> s.substring(0, s.length() - 1);
		Function<String, String> addDate = s -> s + supTime.get();
		// Consumer<String> print = s -> System.out.println(s);
		work(sup1, eatLast.andThen(eatLast).compose(addDate), System.out::println, test);
		Employee e = new Employee("Pesho", 22);

	}

	public static void work(Supplier<String> sup1, Function<String, String> eatLast, Consumer<String> print, Predicate<String> test) {
		final String name = sup1.get();
		if (test.negate().test(name)) {

			print.accept(eatLast.apply(name));
		}
	}
}
