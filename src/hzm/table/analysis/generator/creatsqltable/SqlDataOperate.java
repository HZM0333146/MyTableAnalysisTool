package hzm.table.analysis.generator.creatsqltable;

public class SqlDataOperate {
	public enum Attribute {
		ContentPaste, 
		AttributePaste, 
		ContentAndAttributeIsText, 
		ContentAndAttributeNoText, 
		ContentAndAttributeAuto,
		NoAction;
	}

	public static String contentPaste(String text) {
		return text;
	}

	public static String attributePaste(String attributes, String fieldContent) {
		String text;
		switch (fieldContent) {
		case "true":
			text = attributes;
			break;
		case "TRUE":
			text = attributes;
			break;
		case "True":
			text = attributes;
			break;
		default:
			text = "";
			break;
		}
		return text;
	}

	public static String contentAndAttributeAuto(String attributes, String fieldContent, String contentToBeAdd,
			String interval) {
		if (isNumber(fieldContent) || "true".equals(fieldContent) || "false".equals(fieldContent)) {
			return contentAndAttributeNoText(attributes, fieldContent, interval);
		} else {
			return attributes + interval + contentToBeAdd + fieldContent + contentToBeAdd;
		}
	}

	public static boolean isNumber(String str) {
		String[] strArray = str.split("\\.");
		for (int i = 0; i < strArray.length; i++) {
			char[] charArray = strArray[i].toCharArray();
			for (int j = 0; j < charArray.length; j++) {
				if (charArray[j] < 48 || charArray[j] > 57) {
					return false;
				}
			}
		}
		return true;
	}

	public static String contentAndAttributeIsText(String attributes, String fieldContent, String contentToBeAdd,
			String interval) {
		return attributes + interval + contentToBeAdd + fieldContent + contentToBeAdd;
	}

	public static String contentAndAttributeNoText(String attributes, String fieldContent, String interval) {
		return attributes + interval + fieldContent;
	}
}
