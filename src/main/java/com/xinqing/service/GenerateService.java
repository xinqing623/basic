package com.xinqing.service;

import java.io.File;
import java.io.FileWriter;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinqing.entity.AutoCodeParam;
import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.FtlTemplate;
import com.xinqing.entity.TableColumn;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.DataBaseDailect;
import com.xinqing.util.FileUtils;
import com.xinqing.util.MySqlDialect;
import com.xinqing.util.OracleDialect;
import com.xinqing.util.SpringContextUtil;
import com.xinqing.util.SqlServerDialect;
import com.xinqing.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GenerateService {

	private static Logger logger = LoggerFactory.getLogger(GenerateService.class);
	
	private AutoCodeParam param;

	private DataBaseDailect dataBaseDailect;

	public GenerateService(AutoCodeParam param){
		this.param = param;
		if("mysql".equals(param.getDbType())){
			dataBaseDailect = new MySqlDialect();
		}else if("oracle".equals(param.getDbType())){
			dataBaseDailect = new OracleDialect();
		}else if("sqlserver".equals(param.getDbType())){
			dataBaseDailect = new SqlServerDialect();
		}else{
			dataBaseDailect = new MySqlDialect();
		}
	}
	
	public boolean testConnection(){
		Connection connection =  openConnection();
		if(connection !=null){
			closeConnection(connection);
			return true;
		}
		return false;
	}

	private Connection openConnection() {
		Connection conn = null;

		try {
			Class.forName(param.getJdbcDriver());// 动态加载mysql驱动
			logger.debug("成功加载MySQL驱动程序");
			conn = DriverManager.getConnection(param.getConnUrl(), param.getJdbcUsername(), param.getJdbcPassword());
			return conn;
		} catch (Exception e) {
			logger.error("连接出错", e);
		}
		return conn;
	}

	private void closeConnection(Connection connection) {
		try{
			if (connection != null) {
				connection.close();
			}
		}catch(Exception e){
			logger.error("关闭数据库连接出错",e);
		}
	}

	public List<DataBaseBean> selectTables() {
		String sql = dataBaseDailect.selectTables(param.getDbName());
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

	public void createFileFromFtl(String[] tableNames,String[] tempFiles, String basePackage,boolean isCoverOldFile) throws Exception {
		// 1.创建配置实例Cofiguration
		Configuration cfg = new Configuration();

		String templateDir = ConfigUtils.getSysConfig("temp.dir");

		cfg.setDirectoryForTemplateLoading(new File(templateDir));

		for (String tableName : tableNames) {

			List<TableColumn> columns = selectTableColumns(param.getDbName(), tableName);
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
				FtlTemplate ftlTemplate = ((FtlTemplateService)SpringContextUtil.getBean("ftlTemplateService")).get(temp);
				fileName = ftlTemplate.getFolder() + File.separator + className + ftlTemplate.getSuffix();
//				if (temp.contains("controller")) {
//					fileName = "controller/" + className + "Controller.java";
//				} else if (temp.contains("entity")) {
//					fileName = "entity/" + className + ".java";
//				} else if (temp.contains("impl")) {
//					fileName = "service/impl/" + className + "ServiceImpl.java";
//				} else if (temp.contains("service")) {
//					fileName = "service/" + className + "Service.java";
//				} else if (temp.contains("sqlmap")) {
//					fileName = "mapping/" + className + "Sqlmap.xml";
//				} else if (temp.contains("mapper")) {
//					fileName = "mapping/" + className + "Mapper.java";
//				}
				String filePath = param.getDestPath() + basePackage.replaceAll("[.]", "/") + File.separator + fileName;
				Template template = cfg.getTemplate(ftlTemplate.getTemplateName());
				Writer out = new FileWriter(FileUtils.createNewFile(filePath, isCoverOldFile));
				template.process(root, out);
				out.flush();
			}
		}
	}

	

}
