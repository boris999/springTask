package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.CommonFriendsFinder;
import com.FriendsNetwork;
import com.FriendsNetworkBuilder;

public class TestCommonFriendsFinder {
	@Test
	public void testGetCommonFriends() {
		String[] expectedArray = { "Flavia and Penka common friends are -> []", "Flavia and Tsonka common friends are -> []",
				"Flavia and Miranda common friends are -> []", "Flavia and Ganka common friends are -> [Penka, Tsonka]",
				"Penka and Flavia common friends are -> []", "Penka and Tsonka common friends are -> [Flavia, Ganka]",
				"Penka and Miranda common friends are -> [Flavia]", "Penka and Ganka common friends are -> []",
				"Tsonka and Flavia common friends are -> []", "Tsonka and Penka common friends are -> [Flavia, Ganka]",
				"Tsonka and Miranda common friends are -> [Flavia]", "Tsonka and Ganka common friends are -> []",
				"Miranda and Flavia common friends are -> []", "Miranda and Penka common friends are -> [Flavia]",
				"Miranda and Tsonka common friends are -> [Flavia]", "Miranda and Ganka common friends are -> []",
				"Ganka and Flavia common friends are -> [Penka, Tsonka]", "Ganka and Penka common friends are -> []",
				"Ganka and Tsonka common friends are -> []", "Ganka and Miranda common friends are -> []" };
		List<String> expected = Arrays.asList(expectedArray);
		String[] array = { "Flavia-Miranda", "Miranda-Flavia", "Flavia-Penka", "Penka-Ganka", "Ganka-Tsonka", "Flavia-Tsonka" };
		List<String> list = Arrays.asList(array);
		FriendsNetwork fn = new FriendsNetworkBuilder().build(list);
		List<String> result = CommonFriendsFinder.getCommonFriends(fn);
		Assert.assertEquals(expected, result);
	}
}
