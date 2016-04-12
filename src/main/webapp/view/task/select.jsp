<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#autoTaskGrid").datagrid({
		url : "${ctx}/autoTask/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#auto_task_tb",
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
		title : "自动任务管理",
		columns : [ [
		{
			field : 'id',
			title : '操作',
			width : "16%",
			formatter:autoTaskFormatter
		},
		{
			field : 'jobName',
			title : '作业名',
			width : "14%"
		},
		{
			field : 'jobGroupName',
			title : '作业组',
			width : "14%"
		},
		{
			field : 'triggerName',
			title : '触发器名',
			width : "14%"
		},
		{
			field : 'triggerGroupName',
			title : '触发器组',
			width : "14%"
		},
		{
			field : 'connExpression',
			title : '时间表达式',
			width : "20%",
		},
		{
			field : 'autoStart',
			title : '自动启动',
			width : "10%",
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "14%",
			formatter:dateFormatter
		}
		,{
			field : 'remark',
			title : '备注',
			width : "14%"
		}] ]
	}); 


})

function autoTaskFormatter(value, row, index){
	var txt = "";
	txt += "<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-add'\" onclick='startJob(\"" + value +"\")'>启动</a>";
	txt += "<a href='javascript:void(0)' onclick='stopJob(\"" + value +"\")'>停止</a>&nbsp;&nbsp;";
	return txt;
}

function startJob(id){
	ajaxWithoutRemind('${ctx}/autoTask/startJob?id=' + id, null);
}

function stopJob(id){
	ajaxWithoutRemind('${ctx}/autoTask/stopJob?id=' + id, null);
}


function addAutoTask(){
	openDialog("添加系统配置", '${ctx}/autoTask/preInsert', 400, 300 ,'icon-add');
}

function editAutoTask(){
	var item = $("#autoTaskGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个系统配置");
		return ;
	}
	openDialog("修改系统配置", '${ctx}/autoTask/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delAutoTask(){
	var item = $("#autoTaskGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个配置");
		return ;
	}
	ajaxDelete("确定要删除此配置吗?", "${ctx}/autoTask/delete?id=" + item.id, refreshAutoTask);
}

function query(){
	$('#autoTaskGrid').datagrid('reload', $("#queryAutoTaskFrm").serializeObject());	
}

function clearForm(){
	$("#queryAutoTaskFrm").form('clear');
}

function refreshAutoTask(){
	$('#autoTaskGrid').datagrid('reload');	
}
</script>



<table id="autoTaskGrid"></table>

<div id="auto_task_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAutoTask()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAutoTask()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delAutoTask()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshAutoTask()">刷新</a>
		</div>
		<div>
		<form id="queryAutoTaskFrm">
			配置名: <input class="easyui-textbox" name="configName" style="width:80px">
			配置值: <input class="easyui-textbox" name="configValue" style="width:80px">
			备注: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>