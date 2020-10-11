package com.table.analysis.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.table.analysis.generator.SqlDdlModelGenerator;
import com.table.analysis.generator.SqlDmlModelGenerator;
import com.table.analysis.generator.SwaggerModelGenerator;
import com.table.analysis.util.exclusive.TableAnalysisExtract;

public class TableAnalysisMain {
	private static String inputFilePath = "res/test_tb/";
	private static String inputFileName = "tb.csv";
	private static String outFilePath = "res/test_tb/";

	public static void main(String[] args) throws IOException {
		TableAnalysisExtract tae = new TableAnalysisExtract(inputFilePath+inputFileName);
		List<Map<String, String>> tableInformation = tae.getTableInformation();
		new SwaggerModelGenerator(outFilePath + "SwaggerModleName.java", tableInformation);
		new SqlDdlModelGenerator(outFilePath + "SQLModelName.text", tableInformation);
		new SqlDmlModelGenerator(outFilePath + "MyBatisSql.text", tableInformation);
	}

}
