package com.model.expression;

import java.util.Set;

import com.model.table.Cell;
import com.model.table.CellReference;

public interface ReferenceContext<T> {

	Double getValue(T reference);

	public Cell getCell(CellReference reference);

	public Cell getCell(int columnIndex, int rowIndex);

	public Set<Cell> getCells(Set<CellReference> set);
}
