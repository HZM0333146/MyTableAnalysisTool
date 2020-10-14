package hzm.table.analysis.generator.creatsqltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.exclusive.TableGenerator;

public class SqlDdlModelGenerator extends TableGenerator {
	private SqlDdlModelOperate sqlContextOperating;
	private SqlDdlMode[] fieldArray;
	public SqlDdlModelGenerator() {
		fieldArray = new SqlDdlMode[] { 
				SqlDdlMode.FILEDNAME, 
				SqlDdlMode.JDBCTYPE, 
				SqlDdlMode.NOTNULL,
				SqlDdlMode.DEFAULT,
				SqlDdlMode.COMMENTS, 
				SqlDdlMode.PRIMARYKEY 
		};
	}
	@Override
	public List<String> inputFileData(List<Map<String, String>> tableData) {
		return creatSqlText(tableData);
	}

	private List<String> creatSqlText(List<Map<String, String>> tableDataList) {
		List<String> creatSqlText = new ArrayList<>();
		creatSqlText.add("CREATE TABLE table_name (");
		for (int i = 0; i < tableDataList.size(); i++) {
			Map<String, String> tableDataMap = tableDataList.get(i);
			StringBuilder stringBuilder = new StringBuilder();
			sqlContextOperating = new SqlDdlModelOperate(tableDataMap, fieldArray, " ");
			stringBuilder.append(sqlContextOperating.getColumnContext());
			if (i < tableDataList.size() - 1) {
				stringBuilder.append(",");
			}
			creatSqlText.add(stringBuilder.toString());
		}
		creatSqlText.add(");");
		return creatSqlText;
	}

}
