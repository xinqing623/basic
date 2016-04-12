<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<script type="text/javascript">
$(function(){

	$("#userLogGrid").datagrid({
		url : "${ctx}/userLog/selectPage",//加载的URL  
		isField : "pkId",
		sortName:"create_time",
		sortOrder:"desc",
		toolbar:"#user_log_tb",
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
		title : "用户日志",
		columns : [ [
		{
			field : 'userName',
			title : '用户姓名',
			width : "14%",
			formatter:userNameFormatter
		},
		{
			field : 'projectName',
			title : '项目名称',
			width : "14%",
			formatter:projectNameFormatter
		}
		,{
			field : 'content',
			title : '工作内容',
			width : "14%"
		},
		{
			field : 'question',
			title : '遇到的问题',
			width : "14%"
		},
		{
			field : 'progress',
			title : '工作进度',
			width : "14%"
		},
		{
			field : 'spendTime',
			title : '花费时长',
			width : "14%"
		},
		{
			field : 'relateJiraNo',
			title : '关联的Jira',
			width : "14%"
		},
		{
			field : 'logDate',
			title : '日志时间',
			width : "16%",
			formatter:dateFormatter
		},
		{
			field : 'createTime',
			title : '填写时间',
			width : "16%",
			formatter:dateFormatter
		} ] ]
	}); 


})

function userNameFormatter(value, row, index){
	if(!!row.sysUser){
		return row.sysUser.nickName;
	}
	return "";
}

function projectNameFormatter(value, row, index){
	if(!!row.sysProject){
		return row.sysProject.projectName;
	}
	return "";
}

function userRoleFormatter(value, row, index){
	var txt = row[index];
	if(value == 'ADMIN'){
		txt = "管理员";
	}else if(value == "EMP"){
		txt = "普通职员";
	}
	return txt;
}


function addUserLog(){
	openDialog("添加日志", '${ctx}/userLog/preInsert', 400, 320 ,'icon-add');
}

function editUserLog(){
	var item = $("#userLogGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选一条日志");
		return ;
	}
	var logCreateDate = new Date(item.createTime);
	var today = new Date();
	today = today.Format("yyyy-MM-dd") + " 00:00:00";
	today = new Date(today);
	if(logCreateDate.getTime() < today.getTime()){
		showAlert("不能修改以前的日志");
		return ;
	}	
	openDialog("修改日志", '${ctx}/userLog/preEdit?pkId=' + item.pkId, 400, 320 ,'icon-edit');
}

function delUserLog(){
	var item = $("#userLogGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一条日志");
		return ;
	}	
	
	var logCreateDate = new Date(item.createTime);
	var today = new Date();
	today = today.Format("yyyy-MM-dd") + " 00:00:00";
	today = new Date(today);
	if(logCreateDate.getTime() < today.getTime()){
		showAlert("不能删除以前的日志");
		return ;
	}	
	ajaxDelete("确定要删除这条日志吗?", "${ctx}/userLog/delete?pkId=" + item.pkId, refreshUserLog);
}

function refreshUserLog(){
	$('#userLogGrid').datagrid('reload');	
}

function queryUserLog(){
	$('#userLogGrid').datagrid('reload',$("#queryUserLogFrm").serializeObject());	
}

function clearForm(){
	$("#queryUserLogFrm").form("clear");
}
</script>



<table id="userLogGrid"></table>

<div id="user_log_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUserLog()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUserLog()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delUserLog()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshUserLog()">刷新</a>
		</div>
		<div>
		<form id="queryUserLogFrm">
			用户姓名: <input name="userId" class="easyui-combobox" data-options="url:'${ctx}/sysuser/selectUsers',valueField:'id',textField:'nickName',method:'get'">
			项目名称: <input name="projectId" class="easyui-combobox" data-options="url:'${ctx}/sysProject/selectProjects',valueField:'pkId',textField:'text',method:'get'" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryUserLog()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
	</div>