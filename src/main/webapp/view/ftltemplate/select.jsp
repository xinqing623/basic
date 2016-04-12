<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#ftlTemplateGrid").datagrid({
		url : "${ctx}/ftlTemplate/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#ftl_template_tb",
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
		title : "系统参数",
		columns : [ [
		{
			field : 'id',
			title : '操作',
			width : "20%",
			formatter:ftlTemplateFormatter
		},
		{
			field : 'templateName',
			title : '模板名',
			width : "20%"
		},
		{
			field : 'folder',
			title : '所在文件夹',
			width : "20%"
		}
		,
		{
			field : 'suffix',
			title : '生成文件后缀',
			width : "20%"
		}
		,{
			field : 'remark',
			title : '备注',
			width : "20%"
		}] ]
	}); 


})

function ftlTemplateFormatter(value, row, index){
	var txt = "<a href='javascript:void(0)' onclick='editTempContent(\"" + value + "\")'>编辑内容</a>";
	return txt;
}


function editTempContent(id){
	openDialog("编辑模板内容", '${ctx}/ftlTemplate/editTemplate?id=' + id, 500, 400 ,'icon-edit');
}

function addFtlTemplate(){
	openDialog("添加模板", '${ctx}/ftlTemplate/preInsert', 400, 300 ,'icon-add');
}

function editFtlTemplate(){
	var item = $("#ftlTemplateGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个模板");
		return ;
	}
	openDialog("修改模板", '${ctx}/ftlTemplate/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delFtlTemplate(){
	var item = $("#ftlTemplateGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个模板");
		return ;
	}
	ajaxDelete("确定要删除此模板吗?", "${ctx}/ftlTemplate/delete?id=" + item.id, refreshFtlTemplate);
}

function query(){
	$('#ftlTemplateGrid').datagrid('reload', $("#queryFtlTemplateFrm").serializeObject());	
}

function clearForm(){
	$("#queryFtlTemplateFrm").form('clear');
}

function refreshFtlTemplate(){
	$('#ftlTemplateGrid').datagrid('reload');	
}
</script>



<table id="ftlTemplateGrid"></table>

<div id="ftl_template_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addFtlTemplate()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editFtlTemplate()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delFtlTemplate()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshFtlTemplate()">刷新</a>
		</div>
		<div>
		<form id="queryFtlTemplateFrm">
			配置名: <input class="easyui-textbox" name="configName" style="width:80px">
			配置值: <input class="easyui-textbox" name="configValue" style="width:80px">
			备注: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>