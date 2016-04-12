<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#sysProjectGrid").datagrid({
		url : "${ctx}/sysProject/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#sys_project_tb",
		height:"100%",
		width:"100%",
		border:0,
		loadMsg:"数据加载中,请稍等...",
		rownumbers:true,
		pagination : true,//显示分页  
		singleSelect:true,
		pageSize : 10,//分页大小  
		pageList : [10, 15, 20, 50, 100 ],//每页的个�?  
		fit : false,//自动补全  
		fitColumns : false,
		iconCls : "icon-save",//图标  
		title : "项目管理",
		columns : [ [{
			field : 'projectName',
			title : '项目名称',
			width : "10%"
		},
		{
			field : 'remark',
			title : '项目说明',
			width : "20%"
		}
		,{
			field : 'leaderId',
			title : '负责人',
			width : "30%",
			formatter:leaderFormatter
		},
		{
			field : 'startTime',
			title : '开始时间',
			width : "20%",
			formatter:dateFormatter
		},
		{
			field : 'endTime',
			title : '预计结束时间',
			width : "20%",			
			formatter:dateFormatter
		},
		{
			field : 'status',
			title : '状态',
			width : "10%"
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "20%",
			formatter:dateFormatter
		},
		{
			field : 'jiraUrl',
			title : 'JIRA地址',
			width : "20%"
		}] ]
	}); 


})


function leaderFormatter(value, row, index){
	if(!!row.sysUser){
		return row.sysUser.nickName;
	}
	return "";
}

function authProject2User(){
	var item = $("#sysProjectGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个项目");
		return ;
	}
	openDialog("添加项目用户", '${ctx}/projectUser/preInsert?projectId=' + item.pkId, 400, 300 ,'icon-add');
}


function addsysProject(){
	openDialog("添加项目", '${ctx}/sysProject/preInsert', 400, 350 ,'icon-add');
}

function editsysProject(){
	var item = $("#sysProjectGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个项目");
		return ;
	}
	openDialog("修改系统配置", '${ctx}/sysProject/preEdit?pkId=' + item.pkId, 400, 300 ,'icon-edit');
}

function delsysProject(){
	var item = $("#sysProjectGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个项目");
		return ;
	}
	ajaxDelete("确定要删除此项目吗?", "${ctx}/sysProject/delete?pkId=" + item.pkId, refreshsysProject);
}

function query(){
	$('#sysProjectGrid').datagrid('reload', $("#querysysProjectFrm").serializeObject());	
}

function clearForm(){
	$("#querysysProjectFrm").form('clear');
}

function refreshsysProject(){
	$('#sysProjectGrid').datagrid('reload');	
}
</script>



<table id="sysProjectGrid"></table>

<div id="sys_project_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addsysProject()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editsysProject()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delsysProject()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="authProject2User()">授权</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshsysProject()">刷新</a>
		</div>
		<div>
		<form id="querysysProjectFrm">
			项目名称: <input class="easyui-textbox" name="projectName" style="width:80px">
			项目说明: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>