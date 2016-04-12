package com.xinqing.entity;

public class TableColumn {
	private String tableName;
	private String className;
	private String columnName;
	private String fieldName;
	private String upperFieldName;
	private String dataType;
	private String dataTypeClass;
	private String columnComment;
	private String keyType;
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDataTypeClass() {
		return dataTypeClass;
	}
	public void setDataTypeClass(String dataTypeClass) {
		this.dataTypeClass = dataTypeClass;
	}
	public String getUpperFieldName() {
		return upperFieldName;
	}
	public void setUpperFieldName(String upperFieldName) {
		this.upperFieldName = upperFieldName;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
}
