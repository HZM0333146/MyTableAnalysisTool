package com.table.analysis.generator;

import java.util.Map;

import com.table.analysis.util.exclusive.TableContentOperating;
import com.table.analysis.util.exclusive.TableFieldManage;

public class SqlDmlModelFieldOperate extends TableContentOperating{

	public SqlDmlModelFieldOperate(Map<String, String> columnData, TableFieldManage[] fieldNameArray, String interval) {
		super(columnData, fieldNameArray, interval);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String contextOperating(TableFieldManage tableFieldManage, String fieldContent) {
		// TODO Auto-generated method stub
		return null;
	}

}
