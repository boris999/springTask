package com.model.expression;

public interface ReferenceContext<T> {

	Double getValue(T reference);

	Double getValue(int columnIndex, int rowIndex);
}
