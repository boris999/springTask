import java.util.Objects;

public class Pair<T> {
	private final T first;
	private final T second;

	Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}
	
	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(first) ^ Objects.hashCode(second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?> other = (Pair<?>) obj;

		return (Objects.equals(first, other.first) && Objects.equals(second, other.second))
				|| (Objects.equals(second, other.first) && Objects.equals(first, other.second));
	}

}
