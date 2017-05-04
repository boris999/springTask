package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.FriendLinkFinder;
import com.FriendsNetwork;
import com.FriendsNetworkBuilder;
import com.Person;

public class TestFriendLinkFinder {

	@Test
	public void testFriendLinkFinder() {
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Miranda-Donka", "Donka-Tsonka", "Flavia-Ethel", "Flavia-Marta",
				"Miranda-Ethel", "Marta-John", "John-Petkan", "Donka-Kim", "Donka-Monica", "Miranda-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<List<Person>> result = FriendLinkFinder.findFriendsChain(new Person("Flavia"), new Person("Tsonka"), fn);
		List<List<Person>> expected = new ArrayList<>();
		Person[] personArray = { new Person("Flavia"), new Person("Miranda"), new Person("Tsonka") };
		expected.add(Arrays.asList(personArray));
		Assert.assertEquals(expected, result);
	}
}
