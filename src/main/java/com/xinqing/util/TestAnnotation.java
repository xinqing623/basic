package com.xinqing.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xinqing.entity.AutoCodeParam;

public class TestAnnotation {

	@RequiredParam(message="参数不能为空")
	private AutoCodeParam param;

	@RequiredParam(message = "姓名不能为空,且长度为12位",regexExpression="\\d{12}")
	private String name;

	@RequiredParam(message = "年龄不能为空")
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public static void main(String[] args) {
		TestAnnotation annotation = new TestAnnotation();
		 annotation.setAge(20);
		 annotation.setName("111111111111");
		AutoCodeParam param = new AutoCodeParam();
		annotation.setParam(param);
		String message = CheckParam(annotation);
		System.out.println(message);
	}

	public static String CheckParam(Object object) {
		if (object == null)
			return null;
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			RequiredParam ann = field.getAnnotation(RequiredParam.class);
			if (ann != null && ann.isRequired()) {
				Type type = field.getGenericType();
				Object value = getFieldValue(object, field.getName());
				if (isComplexType(type)) {
					if(value == null){
						return ann.message();
					}else{
						String message = CheckParam(value);
						if(!StringUtil.isEmpty(message)){
							return message; 
						}
						
					}
				} else {
					if (StringUtil.isEmpty(value)) {
						return ann.message();
					}else{
						if(null != ann.regexExpression() && !"".equals(ann.regexExpression().trim())){
							Pattern pattern = Pattern.compile(ann.regexExpression());
							Matcher matcher = pattern.matcher(String.valueOf(value));
							if(!matcher.matches()){
								return ann.message();
							}
						}
					}
				}
			}
		}
		return null;
	}

	private static Object getFieldValue(Object owner, String fieldName) {
		return invokeMethod(owner, fieldName, null);
	}

	private static Object invokeMethod(Object owner, String fieldName, Object[] args) {
		Class<? extends Object> ownerClass = owner.getClass();
		String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return "";
		}
		try {
			return method.invoke(owner, args);
		} catch (Exception e) {
			return "";
		}
	}

	public static boolean isComplexType(Type objectClass) {
		if (objectClass == boolean.class || objectClass == Boolean.class || objectClass == short.class
				|| objectClass == Short.class || objectClass == byte.class || objectClass == Byte.class
				|| objectClass == int.class || objectClass == Integer.class || objectClass == long.class
				|| objectClass == Long.class || objectClass == float.class || objectClass == Float.class
				|| objectClass == char.class || objectClass == Character.class || objectClass == double.class
				|| objectClass == Double.class || objectClass == String.class) {
			return false;
		}
		return true;
	}

	public AutoCodeParam getParam() {
		return param;
	}

	public void setParam(AutoCodeParam param) {
		this.param = param;
	}

}
