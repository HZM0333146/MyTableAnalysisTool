package hzm.table.analysis.util.exclusive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.universal.FileUtil;

public class CsvFileTableAnalysisExtract {
	private String filePath;
	private String splitText;
	private String replaceText;
	private List<Map<String, String>> tableInformation;
	private boolean isFieldNameOfJavaName = true;
	private String sqlFiledName="sqlName";
	public CsvFileTableAnalysisExtract(String filePath) throws IOException {
		this(filePath, "");
	}

	public CsvFileTableAnalysisExtract(String filePath, String replaceText) throws IOException {
		this(filePath, ",", replaceText);
	}

	public CsvFileTableAnalysisExtract(String filePath, String splitText, String replaceText) throws IOException {
		this.filePath = filePath;
		this.splitText = splitText;
		this.replaceText = replaceText;
		this.tableInformation = tableInformationAnalysisExtract();
	}

	public List<Map<String, String>> getTableInformation() {
		return tableInformation;
	}

	private List<Map<String, String>> tableInformationAnalysisExtract() throws IOException {
		List<String[]> tableFileInfoStringArray = readTableFileInformation();
		if (replaceText != null && !"".equals(replaceText)) {
			replaceTextTableInfoArray(tableFileInfoStringArray);
		}
		return fieldNameAndFieldContextComToMap(tableFileInfoStringArray);
	}

	private List<String[]> readTableFileInformation() throws IOException {
		List<String[]> tableInfoArray = new ArrayList<>();
		List<String> tableInfo = FileUtil.readFileData(filePath);
		for (String tableInfoString : tableInfo) {
			String[] fileContent = tableInfoString.split(splitText);
			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = fileContent[i].trim();
			}
			tableInfoArray.add(fileContent);
		}
		return tableInfoArray;
	}

	private void replaceTextTableInfoArray(List<String[]> tableInfoArray) {
		for (String[] rowFieldContext : tableInfoArray) {
			for (String text : rowFieldContext) {
				text=text.replace(replaceText, ",");
			}
		}
	}

	private List<Map<String, String>> fieldNameAndFieldContextComToMap(List<String[]> tableInfoArray) {
		List<Map<String, String>> fieldNameAndContextToMap = new ArrayList<>();
		String[] fieldName = tableInfoArray.get(0);
		for (int i = 1; i < tableInfoArray.size(); i++) {
			Map<String, String> field = new HashMap<>();
			String[] fieldContext = tableInfoArray.get(i);
			for (int j = 0; j < fieldContext.length; j++) {
				field.put(fieldName[j], fieldContext[j]);
			}
			if (isFieldNameOfJavaName)
				addSqlNameToJavaName(field);
			fieldNameAndContextToMap.add(field);
		}
		return fieldNameAndContextToMap;
	}

	private void addSqlNameToJavaName(Map<String, String> field) {
		String fieldName = field.get(sqlFiledName);
		String[] fieldNameArray = fieldName.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fieldNameArray.length; i++) {
			String lowFieldName = fieldNameArray[i].toLowerCase();
			if (i > 0) {
				char[] arr = lowFieldName.toCharArray();
				arr[0] = Character.toUpperCase(arr[0]);
				lowFieldName = String.valueOf(arr);
			}
			sb.append(lowFieldName);
		}
		field.put("javaName", sb.toString());
	}
}
