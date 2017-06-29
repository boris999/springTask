package com.serialize.expression;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.model.table.CellReference;

public class TestCellNameTransformer {

	@Test
	public void test1() {
		testConvertNameToReference(0, 0, "A1");
	}

	@Test
	public void test2() {
		testConvertNameToReference(1, 0, "B1");
	}

	@Test
	public void test3() {
		testConvertNameToReference(25, 9, "Z10");
	}

	@Test
	public void test4() {
		testConvertNameToReference(26, 14, "AA15");
	}

	@Test
	public void test5() {
		testConvertNameToReference(52, 44, "BA45");
	}

	@Test
	public void test6() {
		testConvertNameToReference(701, 1007, "ZZ1008");
	}

	@Test
	public void test7() {
		testConvertNameToReference(702, 155, "AAA156");
	}

	@Test
	public void test17() {
		List<String> columnNames = CellNameTransformer.calculateColumnNames(1999);
		Assert.assertEquals("A", columnNames.get(0));
		Assert.assertEquals("B", columnNames.get(1));
		Assert.assertEquals("Z", columnNames.get(25));
		Assert.assertEquals("AA", columnNames.get(26));
		Assert.assertEquals("BA", columnNames.get(52));
		Assert.assertEquals("ZZ", columnNames.get(701));
		Assert.assertEquals("AAA", columnNames.get(702));
		Assert.assertEquals("AZ", columnNames.get(51));
		Assert.assertEquals("BZ", columnNames.get(77));
		Assert.assertEquals("BDN", columnNames.get(1469));
		Assert.assertEquals("BXW", columnNames.get(1998));

	}

	private static void testConvertNameToReference(int column, int row, String name) {
		Assert.assertEquals(new CellReference(column, row), CellNameTransformer.convertCellNameToReference(name));
	}
}
