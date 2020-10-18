package hzm.table.analysis.generator.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hzm.table.analysis.util.exclusive.TableField;
import hzm.table.analysis.util.exclusive.TableGenerator;
import hzm.table.analysis.util.universal.TableTextUtil;

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
				new SwaggerModel("javaType","javaType","text"),
				new SwaggerModel("javaName","javaName","text")
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
		sb.append(getApiModelPropertyContext(map,swaggerModelArray));
		sb.append(")");
		return sb.toString();
	}
	private String getApiModelPropertyContext(Map<String, String> map,SwaggerModel[] swaggerModelArraay) {
		StringBuffer content = new StringBuffer();
		for(SwaggerModel filed: swaggerModelArraay) {
			String filedName=filed.getFieldName();
			if(TableTextUtil.isFieldDataOfNotNull(map,filedName)) {
				content.append(filed.getAttribute());
				content.append("=");
				content.append(getContent(map.get(filedName),filed.getDataType()));
				content.append(",");
			}
		}
		if(content.length()>0) {
			content.deleteCharAt(content.length()-1);
		}
		return content.toString();
	}
	
	private String getJavaMemberVariable(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("private ");
		sb.append(getJavaModelContent(map,javaModelArray));
		sb.append(";");
		return sb.toString();
	}
	private String getJavaModelContent(Map<String, String> map,SwaggerModel[] swaggerModelArraay) {
		StringBuffer content=new StringBuffer();
		for(SwaggerModel filed: swaggerModelArraay) {
			String filedName=filed.getFieldName();
			if(TableTextUtil.isFieldDataOfNotNull(map,filedName)) {
				content.append(map.get(filedName));
				content.append(" ");
			}
		}
		if(content.length()>0) {
			content.deleteCharAt(content.length()-1);
		}
		return content.toString();
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
				if("true".equals(fildContent.toLowerCase()))
					conten=fildContent;
				break;
			default:
				break;
		}
		return conten;
	}
	
	class SwaggerModel extends TableField {
		private final String dataType;

		public SwaggerModel(String fieldName, String attribute, String dataType) {
			super(fieldName, attribute);
			this.dataType = dataType;
		}

		public String getDataType() {
			return dataType;
		}
	}
}
