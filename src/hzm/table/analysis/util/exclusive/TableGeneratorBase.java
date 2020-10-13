package com.table.analysis.util.exclusive;

public abstract class TableGeneratorBase<T, V> {
	public TableGeneratorBase(String filePath, T inputData) {
		V fileContent = creatTemplate(inputData);
		creatFile(filePath, fileContent);
	}

	public abstract V creatTemplate(T t);

	public abstract void creatFile(String filePath, V v);
}
