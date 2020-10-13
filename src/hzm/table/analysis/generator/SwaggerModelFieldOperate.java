package com.table.analysis.generator;

import java.util.Map;

import com.table.analysis.util.exclusive.TableContentOperating;
import com.table.analysis.util.exclusive.TableFieldManage;
import com.table.analysis.util.exclusive.TableFieldOperate;

public class SwaggerModelFieldOperate extends TableContentOperating {

	public SwaggerModelFieldOperate(Map<String, String> columnData, TableFieldManage[] fieldNameArray, String interval) {
		super(columnData, fieldNameArray, interval);
	}

	@Override
	public String contextOperating(TableFieldManage tableFieldManage, String fieldContent) {
		String text = "";
		String swaggerAttributes = tableFieldManage.getSwaggerAttributes();
		switch (tableFieldManage.getSwaggerAttributesOperating()) {
		case ContentPaste:
			text = TableFieldOperate.contentPaste(fieldContent);
			break;
		case AttributePaste:
			text = TableFieldOperate.attributePaste(swaggerAttributes, fieldContent, "true");
			break;
		case ContentAndAttributeIsText:
			text = TableFieldOperate.contentAndAttributeIsText(swaggerAttributes, fieldContent, "\"", ":");
			break;
		case ContentAndAttributeNoText:
			text = TableFieldOperate.contentAndAttributeNoText(swaggerAttributes, fieldContent, " : ");
			break;
		case ContentAndAttributeAuto:
			text = TableFieldOperate.contentAndAttributeAuto(swaggerAttributes, fieldContent, "\"", ":");
			break;
		case NoAction:
			break;
		}
		return text;
	}
}
