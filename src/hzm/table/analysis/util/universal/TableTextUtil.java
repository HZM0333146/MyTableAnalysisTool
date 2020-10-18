package hzm.table.analysis.util.universal;

import java.util.Map;

public class TableTextUtil {
	public static boolean isFieldDataOfNotNull(Map<String, String> map,String filedName) {
		if(isTextNull(filedName)) {
			return false;
		}
		if(!map.containsKey(filedName)) {
			return false;
		}
		String findContent=map.get(filedName);
		if(isTextNull(findContent)) {
			return false;
		}
		return true;
	}
	public static boolean isTextNull(String text) {
		if(text== null||"null".equals(text)||"".equals(text)) {
			return true;
		}
		return false;
	}
}
