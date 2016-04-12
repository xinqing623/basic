package com.xinqing.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.TableColumn;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MysqlDemo {

	private static Logger logger = LoggerFactory.getLogger(MysqlDemo.class);
	
	private DataBaseDailect dataBaseDailect;
	
	public MysqlDemo(String dbType){
		if(dbType.equalsIgnoreCase("mysql")){
			dataBaseDailect = new MySqlDialect();
		}else if(dbType.equalsIgnoreCase("oracle")){
			dataBaseDailect = new OracleDialect();
		}else if(dbType.equalsIgnoreCase("sqlserver")){
			dataBaseDailect = new SqlServerDialect();
		}
	}
	
	public MysqlDemo(){
		dataBaseDailect = new MySqlDialect();
	}

	private Connection openConnection() {
		Connection conn = null;

		try {
			String driver = ConfigUtils.getProperty("jdbc.driver");
			String url = ConfigUtils.getProperty("jdbc.url");
			String userName = ConfigUtils.getProperty("jdbc.username");
			String password = ConfigUtils.getProperty("jdbc.password");

			Class.forName(driver);// 动态加载mysql驱动
			logger.debug("成功加载MySQL驱动程序");
			conn = DriverManager.getConnection(url, userName, password);
			return conn;
		} catch (Exception e) {
			logger.error("连接出错", e);
		}
		return conn;
	}

	private void closeConnection(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public List<DataBaseBean> selectTables(String dbName) throws SQLException {
		String sql = dataBaseDailect.selectTables(dbName);
		List<DataBaseBean> dataBaseBeans = new ArrayList<DataBaseBean>();
		Connection conn = null;
		try {
			conn = openConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			paraseResult(rs, dataBaseBeans);

		} catch (Exception e) {
			logger.error("查询出错", e);
		} finally {
			closeConnection(conn);
		}
		return dataBaseBeans;
	}

	public List<TableColumn> selectTableColumns(String dbName, String tableName) throws SQLException {
		String sql = dataBaseDailect.selectTableColumns(dbName, tableName);
		List<TableColumn> tableColumns = new ArrayList<TableColumn>();
		Connection conn = null;
		try {
			conn = openConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			paraseColumnResult(rs, tableColumns);

		} catch (Exception e) {
			logger.error("查询出错", e);
		} finally {
			closeConnection(conn);
		}
		return tableColumns;
	}

	private void paraseColumnResult(ResultSet rs, List<TableColumn> tableColumns) throws SQLException {
		TableColumn column = null;
		while (rs.next()) {
			column = new TableColumn();
			column.setTableName(rs.getString(1));
			column.setClassName(StringUtil.upperCaseFirstLetter(StringUtil.toCamelCase(rs.getString(1))));
			column.setColumnName(rs.getString(2));
			column.setFieldName(StringUtil.toCamelCase(rs.getString(2)));
			column.setUpperFieldName(StringUtil.upperCaseFirstLetter(column.getFieldName()));
			column.setDataType(rs.getString(3));
			column.setDataTypeClass(StringUtil.convertDataType(rs.getString(3)));
			column.setColumnComment(rs.getString(4));
			column.setKeyType(rs.getString(5));
			tableColumns.add(column);
		}

	}

	private void paraseResult(ResultSet rs, List<DataBaseBean> dataBaseBeans) throws SQLException {
		DataBaseBean dataBaseBean = null;
		while (rs.next()) {
			dataBaseBean = new DataBaseBean();
			dataBaseBean.setTableName(rs.getString(1));
			dataBaseBeans.add(dataBaseBean);
		}
	}
	
	

	public static void createFileFromFtl() throws Exception {
		// 1.创建配置实例Cofiguration
		Configuration cfg = new Configuration();

		String templateDir = ConfigUtils.getProperty("temp.dir");
		String tableNameStr = ConfigUtils.getProperty("tableName");
		String[] tableNames = tableNameStr.split(",");
		String basePackage = ConfigUtils.getProperty("basePackage");
		String templates = ConfigUtils.getProperty("templates");
		String destDir = ConfigUtils.getProperty("destDir");
		String dbName = ConfigUtils.getProperty("dbName");
		String[] tempFiles = templates.split("[|]");
		String isCoverOldFile = ConfigUtils.getProperty("isCoverOldFile");

		cfg.setDirectoryForTemplateLoading(new File(templateDir));

		MysqlDemo demo = new MysqlDemo();

		for (String tableName : tableNames) {

			List<TableColumn> columns = demo.selectTableColumns(dbName, tableName);
			TableColumn idColumn = new MySqlDialect().getPkColumn(columns);
			if (idColumn == null) {
				throw new Exception("没有定义主键");
			}

			String className = StringUtil.upperCaseFirstLetter(StringUtil.toCamelCase(tableName));
			// 建立数据模型（Map）
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("basePackage", basePackage);
			root.put("table_name", tableName);
			root.put("tableName", StringUtil.toCamelCase(tableName));
			root.put("pkColumnName", idColumn.getColumnName());
			root.put("pkFieldName", idColumn.getFieldName());
			root.put("pkFieldNameUpperCase", StringUtil.upperCaseFirstLetter(idColumn.getFieldName()));
			root.put("pkDataTypeClass", idColumn.getDataTypeClass());
			root.put("columnList", columns);
			root.put("className", className);

			for (String temp : tempFiles) {
				String fileName = "";
				if(temp.contains("controller")){
					fileName = "controller/" + className + "Controller.java";
				}else if(temp.contains("entity")){
					fileName = "entity/" + className + ".java";
				}else if(temp.contains("impl")){
					fileName = "service/impl/" + className + "ServiceImpl.java";
				}else if(temp.contains("service")){
					fileName = "service/" + className + "Service.java";
				}else if(temp.contains("sqlmap")){
					fileName = "mapping/" + className + "Sqlmap.xml";
				}else if(temp.contains("mapper")){
					fileName = "mapping/" + className + "Mapper.java";
				}
				String filePath = destDir + basePackage.replaceAll("[.]", "/") + File.separator + fileName;
				Template template = cfg.getTemplate(temp);
				Writer out = new FileWriter(createNewFile(filePath,isCoverOldFile));
				template.process(root, out);
				out.flush();
			}
		}
	}
	
	
	private static File createNewFile(String fileName,String isCoverOldFile) throws IOException{
		File f = new File(fileName);
		if(!f.isDirectory()){
			File parent = f.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			if("true".equals(isCoverOldFile)){
				if(f.exists()){
					f.delete();
				}
				f.createNewFile();
			}else{
				if(!f.exists()){
					f.createNewFile();
				}
			}
		}
		return f;
	}

	

	public static void main(String[] args) throws Exception {

		createFileFromFtl();

	}

}