package reactor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorTest {

	public static <T> void main(String[] args) {
		Consumer<String> printString = System.out::println;
		Consumer<Integer> printInteger = System.out::println;
		Consumer<Throwable> printError = e -> e.getMessage();
		Runnable end = () -> System.out.println("SUCCESS");

		Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
		List<String> iterable = Arrays.asList("foo", "bar", "foobar");
		Flux<String> seq2 = Flux.fromIterable(iterable);
		Mono<String> noData = Mono.empty();
		Mono<String> data = Mono.just("foo");
		Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
		// numbersFromFiveToSeven.subscribe(printInteger);
		// seq1.subscribe(printString, printError, end);

		// seq1.subscribe(new BaseSubscriber<String>() {
		//
		// @Override
		// protected void hookOnNext(String arg0) {
		// // TODO Auto-generated method stub
		// this.request(4);
		// System.out.println("Next");
		// }
		//
		// @Override
		// protected void hookOnSubscribe(Subscription arg0) {
		// // TODO Auto-generated method stub
		// this.request(4);
		// System.out.println("Subscribed");
		//
		// }
		//
		// });

		Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
				.handle((i, sink) -> {
					String letter = alphabet(i);
					if (letter != null) {
						sink.next(letter);
					}
				});

		alphabet.subscribe(System.out::println);

		Flux<String> flux = Flux.generate(
				() -> 0,
				(state, sink) -> {
					sink.next("3 x " + state + " = " + (3 * state));
					if (state == 10) {
						sink.complete();
					}
					return state + 1;
				});

		flux.subscribe(new BaseSubscriber<String>() {

			@Override
			protected void hookOnNext(String arg0) {
				System.out.println("Request more " + arg0);
				this.request(1);

			}

			@Override
			protected void hookOnSubscribe(Subscription arg0) {
				System.out.println("Subscribed");
				this.request(1);

			}
		});

	}

	public static String alphabet(int letterNumber) {
		if ((letterNumber < 1) || (letterNumber > 26)) {
			return null;
		}
		int letterIndexAscii = ('A' + letterNumber) - 1;
		return "" + (char) letterIndexAscii;
	}
}
