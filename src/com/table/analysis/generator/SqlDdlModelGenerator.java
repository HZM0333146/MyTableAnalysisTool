package com.table.analysis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.table.analysis.util.exclusive.TableFieldManage;
import com.table.analysis.util.exclusive.TableGenerator;

public class SqlDdlModelGenerator extends TableGenerator {
	private SqlDdlModelFieldOperate sqlContextOperating;
	private TableFieldManage[] fieldArray;

	public SqlDdlModelGenerator(String filePath, List<Map<String, String>> inputData) {
		super(filePath, inputData);
	}

	@Override
	public List<String> creatTemplate(List<Map<String, String>> tableData) {
		return creatSqlText(tableData);
	}

	List<String> creatSqlText(List<Map<String, String>> tableDataList) {
		fieldArray = new TableFieldManage[] { TableFieldManage.FILEDNAME, TableFieldManage.JDBCTYPE,
				TableFieldManage.NOTNULL, TableFieldManage.DEFAULT, TableFieldManage.COMMENTS,
				TableFieldManage.PRIMARYKEY };
		List<String> creatSqlText = new ArrayList<>();
		creatSqlText.add("CREATE TABLE table_name (");
		for (Map<String, String> tableDataMap : tableDataList) {
			StringBuilder stringBuilder=new StringBuilder();
			sqlContextOperating = new SqlDdlModelFieldOperate(tableDataMap, fieldArray, " ");
			stringBuilder.append(sqlContextOperating.getColumnContext());
			stringBuilder.append(",");
			creatSqlText.add(stringBuilder.toString());
		}
		creatSqlText.add(");");
		return creatSqlText;
	}

}
