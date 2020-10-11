package com.table.analysis.util.exclusive;

public enum TableFieldManage {
	
	FILEDNAME("sqlName",
			"",TableFieldOperate.Attribute.ContentPaste,
			"",TableFieldOperate.Attribute.NoAction),
	JDBCTYPE("jdbcType",
			"",TableFieldOperate.Attribute.ContentPaste,
			"",TableFieldOperate.Attribute.NoAction),
	PRIMARYKEY("primaryKey",
			"PRIMARY KEY",TableFieldOperate.Attribute.NoAction,
			"",TableFieldOperate.Attribute.NoAction),
	NOTNULL("not null",
			"NOT NULL",TableFieldOperate.Attribute.AttributePaste,
			"required",TableFieldOperate.Attribute.ContentAndAttributeNoText),
	COMMENTS("comments",
			"COMMENTS",TableFieldOperate.Attribute.ContentAndAttributeIsText,
			"value",TableFieldOperate.Attribute.ContentAndAttributeIsText),
	DEFAULT("default",
			"default",TableFieldOperate.Attribute.ContentAndAttributeAuto,
			"example",TableFieldOperate.Attribute.ContentAndAttributeIsText),
	JAVATYPE("javaType",
			"",TableFieldOperate.Attribute.NoAction,
			"",TableFieldOperate.Attribute.ContentPaste),
	JAVANAME("javaName",
			"",TableFieldOperate.Attribute.NoAction,
			"",TableFieldOperate.Attribute.ContentPaste);
		
	private String filedName;
	private String sqlAttributes;
	private String swaggerAttributes;
	private TableFieldOperate.Attribute swaggerAttributesOperating;
	private TableFieldOperate.Attribute sqlAttributesOperating;
	
	TableFieldManage(String filedName,
			String sqlAttributes,TableFieldOperate.Attribute sqlAttributesOperating,
			String swaggerAttributes,TableFieldOperate.Attribute swaggerAttributesOperating) {
		
		this.filedName = filedName;
		this.sqlAttributes = sqlAttributes;
		this.swaggerAttributes = swaggerAttributes;
		this.sqlAttributesOperating=sqlAttributesOperating;
		this.swaggerAttributesOperating=swaggerAttributesOperating;
		
	}

	public String getFiledName() {
		return filedName;
	}
	public String getSqlAttributes() {
		return sqlAttributes;
	}
	public TableFieldOperate.Attribute getSqlAttributesOperating() {
		return sqlAttributesOperating;
	}
	public String getSwaggerAttributes() {
		return swaggerAttributes;
	}
	public TableFieldOperate.Attribute getSwaggerAttributesOperating() {
		return swaggerAttributesOperating;
	}
}
