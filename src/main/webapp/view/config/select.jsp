<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#sysConfigGrid").datagrid({
		url : "${ctx}/config/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#sys_config_tb",
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
		columns : [ [{
			field : 'configName',
			title : '配置名',
			width : "40%"
		},
		{
			field : 'configValue',
			title : '配置值',
			width : "30%"
		}
		,{
			field : 'remark',
			title : '备注',
			width : "30%"
		}] ]
	}); 


})


function addSysConfig(){
	openDialog("添加系统配置", '${ctx}/config/preInsert', 400, 300 ,'icon-add');
}

function editSysConfig(){
	var item = $("#sysConfigGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个系统配置");
		return ;
	}
	openDialog("修改系统配置", '${ctx}/config/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delSysConfig(){
	var item = $("#sysConfigGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个配置");
		return ;
	}
	ajaxDelete("确定要删除此配置吗?", "${ctx}/config/delete?id=" + item.id, refreshSysConfig);
}

function query(){
	$('#sysConfigGrid').datagrid('reload', $("#querySysConfigFrm").serializeObject());	
}

function clearForm(){
	$("#querySysConfigFrm").form('clear');
}

function refreshSysConfig(){
	$('#sysConfigGrid').datagrid('reload');	
}
</script>



<table id="sysConfigGrid"></table>

<div id="sys_config_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSysConfig()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editSysConfig()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delSysConfig()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshSysConfig()">刷新</a>
		</div>
		<div>
		<form id="querySysConfigFrm">
			配置名: <input class="easyui-textbox" name="configName" style="width:80px">
			配置值: <input class="easyui-textbox" name="configValue" style="width:80px">
			备注: <input class="easyui-textbox" name="remark" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">查询</a>
		</form>
		</div>
	</div>