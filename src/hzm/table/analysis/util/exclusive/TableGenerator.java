package hzm.table.analysis.util.exclusive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.universal.FileUtil;

public abstract class TableGenerator extends TableGeneratorBase<List<Map<String, String>>> {

	public abstract List<String> inputFileData(List<Map<String, String>> tableData);

	@Override
	public void write(String filePath, List<Map<String, String>> tableData) {
		try {
			FileUtil.createFile(filePath);
			FileUtil.inDataToFile(filePath, inputFileData(tableData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
