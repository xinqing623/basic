<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
$(function(){

	$("#sysIconCategoryGrid").datagrid({
		url : "${ctx}/iconCategory/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#sys_icon_category_tb",
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
		title : "图标分类",
		columns : [ [{
			field : 'categoryName',
			title : '分类名',
			width : "40%"
		},
		{
			field : 'createTime',
			title : '添加时间',
			width : "60%",
			formatter:dateFormatter
		} ] ]
	}); 


})


function addSysIconCategory(){
	openDialog("添加系统用户", '${ctx}/iconCategory/preInsert', 400, 300 ,'icon-add');
}

function editSysIconCategory(){
	var item = $("#sysIconCategoryGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	openDialog("修改系统用户", '${ctx}/iconCategory/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delSysIconCategory(){
	var item = $("#sysIconCategoryGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	ajaxDelete("确定要删除此用户吗?", "${ctx}/iconCategory/delete?id=" + item.id, refreshSysIconCategory);
}

function refreshSysIconCategory(){
	$('#sysIconCategoryGrid').datagrid('reload');	
}

function querySysIconCategory(){
	$('#sysIconCategoryGrid').datagrid('reload',$("#querySysIconCategoryFrm").serializeObject());	
}

function clearForm(){
	$("#querySysIconCategoryFrm").form("clear");
}
</script>



<table id="sysIconCategoryGrid"></table>

<div id="sys_icon_category_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSysIconCategory()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editSysIconCategory()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delSysIconCategory()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshSysIconCategory()">刷新</a>
		</div>
		<div>
		<form id="querySysIconCategoryFrm">
			登录名: <input class="easyui-textbox" name="userName" style="width:80px">
			用户昵称: <input class="easyui-textbox" name="nickName" style="width:80px">
			部门: <input name="departmentId" class="easyui-combotree" data-options="url:'${ctx}/org/listAll',method:'get'" />
			电话：<input class="easyui-textbox" name="telephone" style="width:80px">
			邮箱：<input class="easyui-textbox" name="email" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="querySysIconCategory()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
	</div>