package hzm.table.analysis.generator.creatsqltable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.exclusive.TableGenerator;

public class SqlDmlModelGenerator extends TableGenerator {

	@Override
	public List<String> inputFileData(List<Map<String, String>> k) {
		// TODO Auto-generated method stub
		List<String> sqlDmlTextList = new ArrayList<>();
		sqlDmlTextList.add("sqlNameText");
		sqlNameText(sqlDmlTextList, k);
		sqlDmlTextList.add("\n");
		sqlDmlTextList.add("javaNameText");
		javaNameText(sqlDmlTextList, k);
		sqlDmlTextList.add("\n");
		sqlDmlTextList.add("resultsText");
		resultsText(sqlDmlTextList, k);
		sqlDmlTextList.add("\n");
		sqlDmlTextList.add("resultMapText");
		resultMapText(sqlDmlTextList, k);
		sqlDmlTextList.add("\n");
		sqlDmlTextList.add("upDataSetText");
		upDataSetText(sqlDmlTextList, k);
		return sqlDmlTextList;
	}

	/*
	 * sql Name
	 */
	void sqlNameText(List<String> sqlText, List<Map<String, String>> tableContont) {
		String sqlName = SqlDdlMode.FILEDNAME.getFiledName();
		StringBuffer inputText = new StringBuffer();
		for (int i = 0; i < tableContont.size(); i++) {
			Map<String, String> tableRowMap = tableContont.get(i);
			if (!tableRowMap.containsKey(sqlName)) {
				continue;
			}
			inputText.append(tableRowMap.get(sqlName));
			inputText.append(",");
			if (i > 0 && i % 5 == 0) {
				inputText.append("\n");
			}
		}
		inputText.deleteCharAt(inputText.length() - 1);
		sqlText.add(inputText.toString());
	}

	/*
	 * (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
	 */
	void javaNameText(List<String> sqlText, List<Map<String, String>> tableContont) {
		String liItemNameString = "item";
		String javaName = SqlDdlMode.JAVANAME.getFiledName();
		StringBuffer inputText = new StringBuffer();
		inputText.append("(");
		for (int i = 0; i < tableContont.size(); i++) {
			Map<String, String> tableRowMap = tableContont.get(i);
			if (!tableRowMap.containsKey(javaName)) {
				continue;
			}
			inputText.append("#{");
			inputText.append(liItemNameString);
			inputText.append(".");
			inputText.append(tableRowMap.get(javaName));
			inputText.append("},");
			if (i > 0 && i % 5 == 0) {
				inputText.append("\n");
			}
		}
		inputText.deleteCharAt(inputText.length() - 1);
		inputText.append(")");
		sqlText.add(inputText.toString());
	}

	/*
	 * @Results({
	 * 
	 * @Result(column="info_id",property="infoId",javaType=Integer.class),
	 * 
	 * @Result(column="nickName",property="nickName",javaType=String.class),
	 * 
	 * @Result(column="email",property="email",javaType=String.class) })
	 */
	void resultsText(List<String> sqlText, List<Map<String, String>> tableContont) {
		String sqlName = SqlDdlMode.FILEDNAME.getFiledName();
		String javaName = SqlDdlMode.JAVANAME.getFiledName();
		String javaType = SqlDdlMode.JAVATYPE.getFiledName();
		StringBuffer inputText = new StringBuffer();
		inputText.append("@Results({\n");
		inputText.append("@Result(id=true,column=\"id\",property=\"id\",javaType=Integer.class),\n");
		for (int i = 0; i < tableContont.size(); i++) {
			Map<String, String> tableRowMap = tableContont.get(i);
			if (!tableRowMap.containsKey(sqlName) || !tableRowMap.containsKey(javaName)
					|| !tableRowMap.containsKey(javaType)) {
				continue;
			}
			inputText.append("@Result(");
			inputText.append("column=\"");
			inputText.append(tableRowMap.get(sqlName));
			inputText.append("\",");
			inputText.append("property=\"");
			inputText.append(tableRowMap.get(javaName));
			inputText.append("\",");
			inputText.append("javaType=");
			inputText.append(tableRowMap.get(javaType));
			inputText.append(".class");
			inputText.append("),");
			inputText.append("\n");
		}
		inputText.deleteCharAt(inputText.length() - 2);
		inputText.append("})");
		sqlText.add(inputText.toString());
	}

	/*
	 * <resultMap id="" type=""> <id property="" column="" /> <result
	 * column=""property=""/> <result property="" column=""/> </resultMap>
	 */
	void resultMapText(List<String> sqlText, List<Map<String, String>> tableContont) {
		String sqlName = SqlDdlMode.FILEDNAME.getFiledName();
		String javaName = SqlDdlMode.JAVANAME.getFiledName();
		StringBuffer inputText = new StringBuffer();
		inputText.append("<resultMap id=\"\" type=\"\">\n<id property=\"\" column=\"\" />\n");

		for (int i = 0; i < tableContont.size(); i++) {
			Map<String, String> tableRowMap = tableContont.get(i);
			if (!tableRowMap.containsKey(sqlName) || !tableRowMap.containsKey(javaName)) {
				continue;
			}
			inputText.append("<result  ");
			inputText.append("property=\"");
			inputText.append(tableRowMap.get(javaName));
			inputText.append("\"");
			inputText.append(",");
			inputText.append("column=\"");
			inputText.append(tableRowMap.get(sqlName));
			inputText.append("\"");
			inputText.append("/>");
			inputText.append("\n");
		}
		inputText.append("</resultMap>");
		sqlText.add(inputText.toString());
	}

	/*
	 * xml <set > <if test="dutyrealUid != null" > DUTYREAL_UID =
	 * #{dutyrealUid,jdbcType=DECIMAL}, </if> </set>
	 */
	void upDataSetText(List<String> sqlText, List<Map<String, String>> tableContont) {
		String sqlName = SqlDdlMode.FILEDNAME.getFiledName();
		String javaName = SqlDdlMode.JAVANAME.getFiledName();
		String javaType = SqlDdlMode.JAVATYPE.getFiledName();
		StringBuffer inputText = new StringBuffer();
		inputText.append("<set >\n");
		for (int i = 0; i < tableContont.size(); i++) {
			Map<String, String> tableRowMap = tableContont.get(i);
			if (!tableRowMap.containsKey(sqlName) || !tableRowMap.containsKey(javaName)
					|| !tableRowMap.containsKey(javaType)) {
				continue;
			}
			inputText.append("<if test=");
			inputText.append("\"");
			inputText.append(tableRowMap.get(javaName));
			inputText.append("!= null");
			inputText.append("\"");
			inputText.append(">");
			inputText.append("\n");
			inputText.append(tableRowMap.get(sqlName));
			inputText.append("=");
			inputText.append("#{");
			inputText.append(tableRowMap.get(javaName));
			inputText.append(",jdbcType=");
			inputText.append(tableRowMap.get(javaType));
			inputText.append("},");
			inputText.append("\n");
			inputText.append("</if>");
			inputText.append("\n");
		}
		inputText.append("</set>");
		sqlText.add(inputText.toString());
	}
}
