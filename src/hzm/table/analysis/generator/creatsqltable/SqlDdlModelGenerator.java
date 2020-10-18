package hzm.table.analysis.generator.creatsqltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.exclusive.TableGenerator;
import hzm.table.analysis.util.universal.TableTextUtil;

public class SqlDdlModelGenerator extends TableGenerator {
	 enum SqlDdlMode {
			FILEDNAME("sqlName", "fildName", SqlDataOperate.Attribute.ContentPaste),
			JDBCTYPE("jdbcType", "jdbcType", SqlDataOperate.Attribute.ContentPaste),
			NOTNULL("not_null", "NOT NULL", SqlDataOperate.Attribute.AttributePaste),
			COMMENTS("comments", "COMMENT", SqlDataOperate.Attribute.ContentAndAttributeIsText),
			DEFAULT("default", "default", SqlDataOperate.Attribute.ContentAndAttributeAuto),
			PRIMARYKEY("primaryKey", "PRIMARY KEY", SqlDataOperate.Attribute.NoAction),
			JAVATYPE("javaType", "javaType",SqlDataOperate.Attribute.NoAction),
			JAVANAME("javaName","javaName",SqlDataOperate.Attribute.NoAction);

			private String filedName;
			private String sqlAttributes;
			private SqlDataOperate.Attribute sqlAttributesOperating;

			SqlDdlMode(String filedName, String sqlAttributes, SqlDataOperate.Attribute sqlAttributesOperating) {
				this.filedName = filedName;
				this.sqlAttributes = sqlAttributes;
				this.sqlAttributesOperating = sqlAttributesOperating;
			}

			public String getFiledName() {
				return filedName;
			}

			public String getSqlAttributes() {
				return sqlAttributes;
			}

			public SqlDataOperate.Attribute getSqlAttributesOperating() {
				return sqlAttributesOperating;
			}
		}
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
			stringBuilder.append(getCreatSqlFlied(tableDataMap, fieldArray));
			if (i < tableDataList.size() - 1) {
				stringBuilder.append(",");
			}
			creatSqlText.add(stringBuilder.toString());
		}
		creatSqlText.add(");");
		return creatSqlText;
	}
	private String getCreatSqlFlied(Map<String, String> columnData, SqlDdlMode[] fieldNameArray) {
		StringBuffer stringBuffer=new StringBuffer();
		for(SqlDdlMode sqlDdlMode:fieldNameArray) {
			String filedName=sqlDdlMode.getFiledName();
			if(TableTextUtil.isFieldDataOfNotNull(columnData, filedName)) {
				String sqlContext=getSqlContext(columnData.get(filedName),sqlDdlMode);
				stringBuffer.append(sqlContext);
				stringBuffer.append(" ");
			}
		}
		if(stringBuffer.length()>0) {
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}
	public String getSqlContext(String fieldContent,SqlDdlMode tableFieldManage) {
		String text = "";
		switch (tableFieldManage.getSqlAttributesOperating()) {
		case ContentPaste:
			text = SqlDataOperate.contentPaste(fieldContent);
			break;
		case AttributePaste:
			text = SqlDataOperate.attributePaste( tableFieldManage.getSqlAttributes(), fieldContent);
			break;
		case ContentAndAttributeIsText:
			text = SqlDataOperate.contentAndAttributeIsText(tableFieldManage.getSqlAttributes(), fieldContent,
					"\'", " ");
			break;
		case ContentAndAttributeNoText:
			text = SqlDataOperate.contentAndAttributeNoText(tableFieldManage.getSqlAttributes(), fieldContent,
					" ");
			break;
		case ContentAndAttributeAuto:
			text = SqlDataOperate.contentAndAttributeAuto(tableFieldManage.getSqlAttributes(), fieldContent, "\'", " ");
			break;
		case NoAction:
			break;
		}
		return text;
	}
}
