package com.table.analysis.util.exclusive;

import java.util.Map;

public abstract class TableContentOperating {
	public String columnContext;
	public TableContentOperating(Map<String, String> columnData, TableFieldManage[] fieldNameArray,
			String interval){
		 columnContext=outColumnContext(columnData,fieldNameArray,interval);
	}
	public String getColumnContext() {
		return columnContext;
	}
	private String outColumnContext(Map<String, String> columnData, TableFieldManage[] fieldNameArray,
			String interval) {
		StringBuffer sb = new StringBuffer();
		for (TableFieldManage fieldName : fieldNameArray) {
			String fieldNameKey = fieldName.getFiledName();
			if (columnData.containsKey(fieldNameKey) && null != columnData.get(fieldNameKey)
					&& !"".equals(columnData.get(fieldNameKey))) {
				String text = contextOperating(fieldName, columnData.get(fieldNameKey));
				sb.append(text);
				sb.append(interval);
			}
			
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public abstract String contextOperating(TableFieldManage tableFieldManage, String fieldContent);
		
}
