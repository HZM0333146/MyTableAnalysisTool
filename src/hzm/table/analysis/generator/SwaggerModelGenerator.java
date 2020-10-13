package com.table.analysis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.table.analysis.util.exclusive.TableFieldManage;
import com.table.analysis.util.exclusive.TableGenerator;

public class SwaggerModelGenerator extends TableGenerator {
	private TableFieldManage[] apiModelPropertyKey;
	private TableFieldManage[] javaMemberVariable;
	private SwaggerModelFieldOperate swaggerContextOperating;

	public SwaggerModelGenerator(String filePath, List<Map<String, String>> ls) {
		super(filePath, ls);
	}

	@Override
	public List<String> creatTemplate(List<Map<String, String>> lsMap) {
		List<String> fileContent = new ArrayList<>();
		fileContent.add("@ApiModel(description = \"\")");
		fileContent.add("public class SwaggerModleName {");
		for (Map<String, String> map : lsMap) {
			fileContent.add(getApiModelPropertyText(map));
			fileContent.add(getJavaMemberVariable(map));
		}
		fileContent.add("}");
		return fileContent;
	}

	private String getApiModelPropertyText(Map<String, String> map) {
		apiModelPropertyKey = new TableFieldManage[] { TableFieldManage.COMMENTS, TableFieldManage.NOTNULL,
				TableFieldManage.DEFAULT };
		StringBuffer sb = new StringBuffer();
		sb.append("@ApiModelProperty(");
		swaggerContextOperating = new SwaggerModelFieldOperate(map, apiModelPropertyKey, ",");
		sb.append(swaggerContextOperating.getColumnContext());
		sb.append(")");
		return sb.toString();
	}

	private String getJavaMemberVariable(Map<String, String> map) {
		javaMemberVariable = new TableFieldManage[] { TableFieldManage.JAVATYPE, TableFieldManage.JAVANAME, };
		StringBuffer sb = new StringBuffer();
		sb.append("private ");
		swaggerContextOperating = new SwaggerModelFieldOperate(map, javaMemberVariable, " ");
		sb.append(swaggerContextOperating.getColumnContext());
		sb.append(";");
		return sb.toString();
	}
}
