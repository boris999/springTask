package com.model.expression;

import java.util.Set;

import com.model.table.Cell;
import com.model.table.CellReference;

public interface ReferenceContext<T> {

	Double getValue(T reference);

	Cell getCell(CellReference reference);

	Cell getCell(int columnIndex, int rowIndex);

	Set<Cell> getCells(Set<Cell> set);
}
