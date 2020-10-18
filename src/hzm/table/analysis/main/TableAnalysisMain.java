package hzm.table.analysis.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.generator.creatsqltable.SqlDdlModelGenerator;
import hzm.table.analysis.generator.mybatis.SqlDmlModelGenerator;
import hzm.table.analysis.generator.swagger.SwaggerModelGenerator;
import hzm.table.analysis.util.exclusive.CsvFileTableAnalysisExtract;

public class TableAnalysisMain {
	private static String inputFilePath = "test_data/test_tb/tb.csv";
	private static String outFilePath = "test_data/test_tb/";

	public static void main(String[] args) throws IOException {
		CsvFileTableAnalysisExtract tae = new CsvFileTableAnalysisExtract(inputFilePath);
		List<Map<String, String>> tableInformation = tae.getTableInformation();
		new SwaggerModelGenerator().write(outFilePath + "SwaggerModleName.java", tableInformation);
		new SqlDdlModelGenerator().write(outFilePath + "SQLModelName.txt", tableInformation);
		new SqlDmlModelGenerator().write(outFilePath + "MyBatisSql.txt", tableInformation);
	}

}
