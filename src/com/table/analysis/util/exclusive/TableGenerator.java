package com.table.analysis.util.exclusive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.table.analysis.util.universal.FileUtil;

public abstract class TableGenerator extends TableGeneratorBase<List<Map<String, String>>, List<String>> {
	public TableGenerator(String filePath, List<Map<String, String>> ls) {
		super(filePath, ls);

	}

	@Override
	public abstract List<String> creatTemplate(List<Map<String, String>> k);

	@Override
	public void creatFile(String filePath, List<String> lsString) {
		try {
			FileUtil.createFile(filePath);
			FileUtil.inDataToFile(filePath, lsString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
