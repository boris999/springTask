package test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.FriendsNetwork;
import com.FriendsNetworkBuilder;
import com.Person;

public class TestFriendsNetwork {

	@Test
	public void testGetFriendsOf() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Flavia-Penka", "Penka-Ganka", "Ganka-Tsonka", "Flavia-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		Set<Person> expected = new HashSet<>();
		expected.add(new Person("Miranda"));
		expected.add(new Person("Penka"));
		expected.add(new Person("Tsonka"));
		Set<Person> result = fn.getFriendsOf(new Person("Flavia"));
		Assert.assertEquals(expected, result);
	}

}
