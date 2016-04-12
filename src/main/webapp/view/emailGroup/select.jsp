<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#emailGroupGrid").datagrid({
		url : "${ctx}/emailGroup/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#email_group_tb",
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
		title : "邮件组管理",
		columns : [ [{
			field : 'groupName',
			title : '邮件组名称',
			width : "10%"
		},
		{
			field : 'remindTime',
			title : '提醒时间',
			width : "20%"
		}
		,{
			field : 'sendTime',
			title : '汇总发送时间',
			width : "20%"
		},
		{
			field : 'remindTemlpate',
			title : '提醒邮件模板',
			width : "20%",
			formatter:remindTemlpateFormatter
		},
		{
			field : 'sendTemplate',
			title : '汇总邮件模板',
			width : "20%",			
			formatter:sendTemplateFormatter
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
		}] ]
	}); 


})

function remindTemlpateFormatter(){
	return "";
}

function sendTemplateFormatter(){
	return "";
}

function authEmailGroupUser(){
	var item = $("#emailGroupGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个邮件组");
		return ;
	}
	openDialog("添加邮件组用户", '${ctx}/emailGroupUser/preInsert?emailGroupId=' + item.pkId, 400, 300 ,'icon-add');
}


function addemailGroup(){
	openDialog("添加邮件组", '${ctx}/emailGroup/preInsert', 400, 350 ,'icon-add');
}

function editemailGroup(){
	var item = $("#emailGroupGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个邮件组");
		return ;
	}
	openDialog("修改邮件组", '${ctx}/emailGroup/preEdit?pkId=' + item.pkId, 400, 300 ,'icon-edit');
}

function delemailGroup(){
	var item = $("#emailGroupGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个邮件组");
		return ;
	}
	ajaxDelete("确定要删除此邮件组吗?", "${ctx}/emailGroup/delete?pkId=" + item.pkId, refreshemailGroup);
}

function query(){
	$('#emailGroupGrid').datagrid('reload', $("#queryemailGroupFrm").serializeObject());	
}

function clearForm(){
	$("#queryemailGroupFrm").form('clear');
}

function refreshemailGroup(){
	$('#emailGroupGrid').datagrid('reload');	
}
</script>



<table id="emailGroupGrid"></table>

<div id="email_group_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addemailGroup()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editemailGroup()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delemailGroup()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="authEmailGroupUser()">添加成员</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshemailGroup()">刷新</a>
		</div>
		<div>
		<form id="queryemailGroupFrm">
			邮件组名: <input class="easyui-textbox" name="groupName" style="width:80px">
			项目说明: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>