package hzm.table.analysis.generator.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.exclusive.TableGenerator;

public class SwaggerModelGenerator extends TableGenerator {
	private SwaggerModel[]	swaggerModelArray;
	private SwaggerModel[] 	javaModelArray;
	public SwaggerModelGenerator(){
		swaggerModelArray=new SwaggerModel[] {
				new SwaggerModel("","access","string"),
				new SwaggerModel("","allowableValues","string"),
				new SwaggerModel("","dataType","string"),
				new SwaggerModel("default","example","string"),
				new SwaggerModel("","hidden","boolean"),
				new SwaggerModel("","name","string"),
				new SwaggerModel("","notes","string"),
				new SwaggerModel("","position","int"),
				new SwaggerModel("","readOnly","boolean"),
				new SwaggerModel("","reference","string"),
				new SwaggerModel("not null","required","boolean"),
				new SwaggerModel("comments","value","string"),
			};
		javaModelArray=new SwaggerModel[] {
				new SwaggerModel("javaName","javaName","text"),
				new SwaggerModel("javaType","javaType","text")
		};
	}
	@Override
	public List<String> inputFileData(List<Map<String, String>> lsMap) {
		List<String> fileContent = new ArrayList<>();
		fileContent.add("@ApiModel(description = \"\")");
		fileContent.add("public class SwaggerModleName {");
		for (Map<String, String> map : lsMap) {
			fileContent.add(getApiModelPropertyText(map));
			fileContent.add(getJavaMemberVariable(map));
		}
		fileContent.add("}");
		return fileContent;
	}

	private String getApiModelPropertyText(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("@ApiModelProperty(");
		sb.append(getApiModelPropertyContext(map));
		sb.append(")");
		return sb.toString();
	}
	private String getApiModelPropertyContext(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for(SwaggerModel filed: swaggerModelArray) {
			String filedName=filed.getFieldName();
			if(isTextNull(filedName)) {
				continue;
			}
			if(map.containsKey(filedName)) {
				sb.append(filed.getAttribute());
				sb.append(":");
				sb.append(getContent(map.get(filedName),filed.getDataType()));
			}
		}
		return sb.toString();
	}
	private String getContent(String fildContent,String dataType) {
		String conten="";
		switch (dataType) {
			case "string":
				conten="\""+fildContent+"\"";
				break;
			case "int":
				conten=fildContent;
				break;
			case "boolean":
				conten=fildContent;
				break;
			default:
				break;
		}
		return conten;
	}
	private boolean isTextNull(String text) {
		if(text== null||"null".equals(text)||"".equals(text)) {
			return true;
		}
		return false;
	}
	private String getJavaMemberVariable(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("private ");
		sb.append(getJavaModelContent(map));
		sb.append(";");
		return sb.toString();
	}
	private String getJavaModelContent(Map<String, String> map) {
		StringBuffer content=new StringBuffer();
		for(SwaggerModel filed: javaModelArray) {
			String filedName=filed.getFieldName();
			if(isTextNull(filedName)) {
				continue;
			}
			if(map.containsKey(filedName)) {
				content.append(map.get(filedName));
				content.append(" ");
			}
		}
		return content.toString();
	}
}
