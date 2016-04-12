package com.xinqing.util;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

public class StringUtil {
	
	public static String toCamelCase(String oldName){
		String[] parts = oldName.split("_");
		for(int i = 1; i< parts.length; i++){
			parts[i] = upperCaseFirstLetter(parts[i]);
		}
		return joinStr(parts, "");
	}
	
	public static String upperCaseFirstLetter(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String joinStr(String[] strArr){
		return joinStr(strArr, ",");
	}	

	public static String joinStr(String[] strArr, String joinChar){
		if(strArr == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< strArr.length; i++){
			if(i != strArr.length - 1){
				sb.append(strArr[i]).append(joinChar);
			}else{
				sb.append(strArr[i]);
			}
		}
		return sb.toString();
	}
	
	public static String joinStr(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	
	
	
	public static void main(String[] args) {
//		List<String> list=new ArrayList<String>();
//        list.add("aaa");
//        list.add("bbb");
//        list.add("ccc");
//        System.out.println(joinStr(list));
		System.out.println(getRandomStr(6));
	}

	public static String convertDataType(String dataType) {
		String dataTypeNew = null;
		dataType = dataType.toLowerCase();
		if(dataType.equalsIgnoreCase("varchar")){
			dataTypeNew = "String";
		}else if(dataType.contains("int")){
			dataTypeNew = "Integer";
		}else if(dataType.contains("date")){
			dataTypeNew = "Date";
		}else if(dataType.contains("time")){
			dataTypeNew = "Date";
		}else if(dataType.equalsIgnoreCase("double")){
			dataTypeNew = "Double";
		}else if(dataType.equalsIgnoreCase("float")){
			dataTypeNew = "Float";
		}else if(dataType.contains("blob")){
			dataTypeNew = "String";
		}else if(dataType.contains("text")){
			dataTypeNew = "String";
		}
		return dataTypeNew;
	}

	public static boolean isEmpty(Object value) {
		return value == null || value.toString().length() == 0;
	}

	public static String getRandomStr(int count) {		
		return RandomStringUtils.randomAlphanumeric(count);
	}

	public static String getRandomNum(int count) {		
		return RandomStringUtils.randomNumeric(count);
	}

}
