package hzm.table.analysis.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.generator.creatsqltable.SqlDdlModelGenerator;
import hzm.table.analysis.generator.creatsqltable.SqlDmlModelGenerator;
import hzm.table.analysis.generator.swagger.SwaggerModelGenerator;
import hzm.table.analysis.util.exclusive.TableAnalysisExtract;

public class TableAnalysisMain {
	private static String inputFilePath = "res/test_tb/";
	private static String inputFileName = "tb.csv";
	private static String outFilePath = "res/test_tb/";

	public static void main(String[] args) throws IOException {
		TableAnalysisExtract tae = new TableAnalysisExtract(inputFilePath+inputFileName);
		List<Map<String, String>> tableInformation = tae.getTableInformation();
		new SwaggerModelGenerator().write(outFilePath + "SwaggerModleName.java", tableInformation);
		new SqlDdlModelGenerator().write(outFilePath + "SQLModelName.txt", tableInformation);
		new SqlDmlModelGenerator().write(outFilePath + "MyBatisSql.txt", tableInformation);
	}

}
