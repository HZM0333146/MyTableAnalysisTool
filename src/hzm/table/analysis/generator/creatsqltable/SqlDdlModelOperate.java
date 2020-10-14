package hzm.table.analysis.generator.creatsqltable;

import java.util.Map;

import hzm.table.analysis.util.exclusive.TableContentOperating;
import hzm.table.analysis.util.universal.WordDataOperate;

public class SqlDdlModelOperate extends TableContentOperating {

	public SqlDdlModelOperate(Map<String, String> columnData, SqlDdlMode[] fieldNameArray, String interval) {
		super(columnData, fieldNameArray, interval);
	}
	@Override
	public String contextOperating(SqlDdlMode tableFieldManage, String fieldContent) {
		String text = "";
		switch (tableFieldManage.getSqlAttributesOperating()) {
		case ContentPaste:
			text = WordDataOperate.contentPaste(fieldContent);
			break;
		case AttributePaste:
			text = WordDataOperate.attributePaste( tableFieldManage.getSqlAttributes(),"true", fieldContent);
			break;
		case ContentAndAttributeIsText:
			text = WordDataOperate.contentAndAttributeIsText(tableFieldManage.getSqlAttributes(), fieldContent,
					"\'", " ");
			break;
		case ContentAndAttributeNoText:
			text = WordDataOperate.contentAndAttributeNoText(tableFieldManage.getSqlAttributes(), fieldContent,
					" ");
			break;
		case ContentAndAttributeAuto:
			text = WordDataOperate.contentAndAttributeAuto(tableFieldManage.getSqlAttributes(), fieldContent, "\'", " ");
			break;
		case NoAction:
			break;
		}
		return text;
	}
}
