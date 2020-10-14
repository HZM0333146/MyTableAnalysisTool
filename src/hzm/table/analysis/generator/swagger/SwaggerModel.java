package hzm.table.analysis.generator.swagger;

import hzm.table.analysis.util.exclusive.TableField;

public class SwaggerModel extends TableField {
	private final String dataType;

	public SwaggerModel(String fieldName, String attribute, String dataType) {
		super(fieldName, attribute);
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}
}
