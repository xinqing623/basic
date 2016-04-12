<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#autoCodeParamGrid").datagrid({
		url : "${ctx}/autoCode/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#auto_code_param_tb",
		height:"100%",
		width:"100%",
		border:0,
		loadMsg:"数据加载中,请稍等",
		rownumbers:true,
		pagination : true,//显示分页  
		singleSelect:true,
		pageSize : 10,//分页大小  
		pageList : [10, 15, 20, 50, 100 ],//每页的个数  
		fit : false,//自动补全  
		fitColumns : false,
		iconCls : "icon-save",//图标  
		title : "数据库连接参数",
		columns : [ [
		{
			field : 'id',
			title : '操作',
			width : "11%",
			formatter:autoCodeFormatter
		},
		{
			field : 'dbType',
			title : '数据库类型',
			width : "11%"
		},
		{
			field : 'dbName',
			title : '数据库名称',
			width : "11%"
		}
		,{
			field : 'jdbcDriver',
			title : '数据库驱动',
			width : "11%"
		},
		{
			field : 'connUrl',
			title : '数据库连接',
			width : "11%"
		},
		{
			field : 'jdbcUsername',
			title : '数据库登录名',
			width : "11%"
		},
		{
			field : 'jdbcPassword',
			title : '数据库密码',
			width : "11%"
		},
		{
			field : 'destPath',
			title : '存放文件夹',
			width : "11%"
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "12%",
			formatter:dateFormatter
		} ] ]
	}); 


})

function autoCodeFormatter(value, row, index){
	var txt = "";
	txt += "<a href='javascript:void(0)' onclick='testConnection(\"" + value + "\")'>测试连接</a>";
	txt += "<a href='javascript:void(0)' onclick='generateCode(\"" + value + "\")'>生成代码</a>";
	return txt;
}

function departmentFormatter(value, row, index){
	if(row.sysOrganization){
		return row.sysOrganization.groupName;
	}
	return "";
}


function testConnection(id){
	ajaxWithoutRemind("${ctx}/autoCode/testConnection?id=" + id, null);
}

function generateCode(id){
	openDialog("所有的表", '${ctx}/autoCode/viewTables?id=' + id, 500, 400 ,'icon-view');
}

function addAutoCodeParam(){
	openDialog("添加参数", '${ctx}/autoCode/preInsert', 400, 300 ,'icon-add');
}

function editAutoCodeParam(){
	var item = $("#autoCodeParamGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	openDialog("修改参数", '${ctx}/autoCode/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delAutoCodeParam(){
	var item = $("#autoCodeParamGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	ajaxDelete("确定要删除此用户吗?", "${ctx}/autoCode/delete?id=" + item.id, refreshAutoCodeParam);
}

function refreshAutoCodeParam(){
	$('#autoCodeParamGrid').datagrid('reload');	
}

function queryAutoCodeParam(){
	$('#autoCodeParamGrid').datagrid('reload',$("#queryAutoCodeParamFrm").serializeObject());	
}

function clearForm(){
	$("#queryAutoCodeParamFrm").form("clear");
}
</script>



<table id="autoCodeParamGrid"></table>

<div id="auto_code_param_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAutoCodeParam()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAutoCodeParam()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delAutoCodeParam()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshAutoCodeParam()">刷新</a>
		</div>
		<div>
		<form id="queryAutoCodeParamFrm">
			登录名: <input class="easyui-textbox" name="userName" style="width:80px">
			用户昵称: <input class="easyui-textbox" name="nickName" style="width:80px">
			部门: <input name="departmentId" class="easyui-combotree" data-options="url:'${ctx}/org/listAll',method:'get'" />
			电话：<input class="easyui-textbox" name="telephone" style="width:80px">
			邮箱：<input class="easyui-textbox" name="email" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="querySysUser()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
	</div>