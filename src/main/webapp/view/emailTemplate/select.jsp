<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#emailTemplateGrid").datagrid({
		url : "${ctx}/emailTemplate/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#email_template_tb",
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
		title : "邮件模板",
		columns : [ [
		{
			field : 'pkId',
			title : '模板ID',
			width : "10%"
		},
		{
			field : 'templateName',
			title : '项目名称',
			width : "10%"
		},
		{
			field : 'content',
			title : '项目说明',
			width : "60%"
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "20%",
			formatter:dateFormatter
		}
		] ]
	}); 


})


function addEmailTemplate(){
	openDialog("添加邮件模板", '${ctx}/emailTemplate/preInsert', 400, 350 ,'icon-add');
}

function editEmailTemplate(){
	var item = $("#emailTemplateGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个邮件模板");
		return ;
	}
	openDialog("修改邮件模板", '${ctx}/emailTemplate/preEdit?pkId=' + item.pkId, 400, 300 ,'icon-edit');
}

function delEmailTemplate(){
	var item = $("#emailTemplateGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个邮件模板");
		return ;
	}
	ajaxDelete("确定要删除此邮件模板吗?", "${ctx}/emailTemplate/delete?pkId=" + item.pkId, refreshEmailTemplate);
}

function query(){
	$('#emailTemplateGrid').datagrid('reload', $("#queryEmailTemplateFrm").serializeObject());	
}

function clearForm(){
	$("#queryEmailTemplateFrm").form('clear');
}

function refreshEmailTemplate(){
	$('#emailTemplateGrid').datagrid('reload');	
}
</script>



<table id="emailTemplateGrid"></table>

<div id="email_template_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addEmailTemplate()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editEmailTemplate()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delEmailTemplate()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshEmailTemplate()">刷新</a>
		</div>
		<div>
		<form id="queryEmailTemplateFrm">
			项目名称: <input class="easyui-textbox" name="projectName" style="width:80px">
			项目说明: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>