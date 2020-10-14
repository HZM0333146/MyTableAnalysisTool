package hzm.table.analysis.generator.creatsqltable;

import hzm.table.analysis.util.universal.WordDataOperate;

public enum SqlDdlMode {

	FILEDNAME("sqlName", "fildName", WordDataOperate.Attribute.ContentPaste),
	JDBCTYPE("jdbcType", "jdbcType", WordDataOperate.Attribute.ContentPaste),
	PRIMARYKEY("primaryKey", "PRIMARY KEY", WordDataOperate.Attribute.NoAction),
	NOTNULL("not null", "NOT NULL", WordDataOperate.Attribute.AttributePaste),
	COMMENTS("comments", "COMMENT", WordDataOperate.Attribute.ContentAndAttributeIsText),
	DEFAULT("default", "default", WordDataOperate.Attribute.ContentAndAttributeAuto),
	JAVANAME("javaName","javaName",WordDataOperate.Attribute.NoAction),
	JAVATYPE("javaType", "javaType",WordDataOperate.Attribute.NoAction);

	private String filedName;
	private String sqlAttributes;
	private WordDataOperate.Attribute sqlAttributesOperating;

	SqlDdlMode(String filedName, String sqlAttributes, WordDataOperate.Attribute sqlAttributesOperating) {

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

	public WordDataOperate.Attribute getSqlAttributesOperating() {
		return sqlAttributesOperating;
	}
}
