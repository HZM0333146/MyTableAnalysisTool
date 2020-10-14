package hzm.table.analysis.util.exclusive;

public class TableField {
	private final String fieldName;
	private final String attribute;
	
	public TableField(String fieldName,String attribute){
		this.fieldName=fieldName;
		this.attribute=attribute;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public String getAttribute() {
		return attribute;
	}
}
