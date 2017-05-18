package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.excel.ForTest;

public class TestSimple {

	@Test
	public void test() {
		ForTest obj = new ForTest();
		String testString = "((A1+5)/2+(A8-B3))*4";
		String anotherString = "((A1+5)/2)+(((A8-B3))*4)";
		int result = obj.countBrackets(anotherString);
		Assert.assertEquals(new Integer(3), new Integer(result));

	}

	@Test
	public void testRemoveBrackets() {
		ForTest obj = new ForTest();
		String testString = "((A1+5)/2+(A8-B3))*4";
		String secondString = "((A1+5)/2)+(A8-B3)*4";
		String third = "5+A1-B2*(A8-A4)";
		String four = "A9^3-((B3/7)+4/2)^2";

		List<String> result = obj.removeBrackets(four);
		String ab = "";

	}

}
