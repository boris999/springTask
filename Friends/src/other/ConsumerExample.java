package other;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {
	public static void main(String args[]) {
		Consumer<Integer> consumer = i -> System.out.print(" " + i);
		Consumer<Integer> consumerWithAndThen = consumer.andThen(i -> System.out.print("(printed " + i + ")"));
		List<Integer> integerList = Arrays.asList(new Integer(1),
				new Integer(10), new Integer(200),
				new Integer(101), new Integer(-10),
				new Integer(0));
		printList(integerList, consumerWithAndThen);
	}

	public static void printList(List<Integer> listOfIntegers, Consumer<Integer> consumer) {
		for (Integer integer : listOfIntegers) {
			consumer.accept(integer);
		}
	}
}
