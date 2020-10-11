package com.table.analysis.generator;

import java.util.Map;

import com.table.analysis.util.exclusive.TableContentOperating;
import com.table.analysis.util.exclusive.TableFieldManage;
import com.table.analysis.util.exclusive.TableFieldOperate;

public class SqlDdlModelFieldOperate extends TableContentOperating {

	public SqlDdlModelFieldOperate(Map<String, String> columnData, TableFieldManage[] fieldNameArray, String interval) {
		super(columnData, fieldNameArray, interval);
	}
	@Override
	public String contextOperating(TableFieldManage tableFieldManage, String fieldContent) {
		String text = "";
		switch (tableFieldManage.getSqlAttributesOperating()) {
		case ContentPaste:
			text = TableFieldOperate.contentPaste(fieldContent);
			break;
		case AttributePaste:
			text = TableFieldOperate.attributePaste( tableFieldManage.getSqlAttributes(),"true", fieldContent);
			break;
		case ContentAndAttributeIsText:
			text = TableFieldOperate.contentAndAttributeIsText(tableFieldManage.getSqlAttributes(), fieldContent,
					"\'", " ");
			break;
		case ContentAndAttributeNoText:
			text = TableFieldOperate.contentAndAttributeNoText(tableFieldManage.getSqlAttributes(), fieldContent,
					" ");
			break;
		case ContentAndAttributeAuto:
			text = TableFieldOperate.contentAndAttributeAuto(tableFieldManage.getSqlAttributes(), fieldContent, "\'", " ");
			break;
		case NoAction:
			break;
		}
		return text;
	}
}
