<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<script type="text/javascript">
$(function(){

	$("#roleGrid").datagrid({
		url : "${ctx}/role/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#role_tb",
		height:"100%",
		width:"100%",
		border:0,
		loadMsg:"数据加载中,请稍等",
		rownumbers:true,
		pagination : true,//显示分页  
		singleSelect:true,//只允许选择单行
		pageSize : 10,//分页大小  
		pageList : [10, 15, 20, 50, 100 ],//每页的个数  
		fit : false,//自动补全  
		fitColumns : false,
		iconCls : "icon-save",//图标  
		title : "角色管理",
		columns : [ [
		{
			field : 'roleName',
			title : '角色名',
			width : "30%"
		},
		{
			field : 'remark',
			title : '备注',
			width : "30%"
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "16%",
			formatter:dateFormatter
		} ] ]
	}); 


})

function organizationFormatter(value, row, index){
	if(!!row.sysOrganization){
		return row.sysOrganization.groupName;
	}
	return "";
}

function sexFormatter(value, row, index){
	var txt = "";
	if(value == '1'){
		txt = "男";
	}else if(value == '0'){
		txt = "女";
	}
	return txt;
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

function authRoleMenu(){
	var item = $("#roleGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个角色");
		return ;
	}
	openDialog("给角色分配菜单", '${ctx}/roleMenu/preInsert?roleId=' + item.pkId, 400, 300 ,'icon-edit');
}


function addRole(){
	openDialog("添加角色", '${ctx}/role/preInsert', 400, 300 ,'icon-add');
}

function editRole(){
	var item = $("#roleGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个角色");
		return ;
	}
	openDialog("修改角色", '${ctx}/role/preEdit?pkId=' + item.pkId, 400, 300 ,'icon-edit');
}

function delRole(){
	var item = $("#roleGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个角色");
		return ;
	}
	ajaxDelete("确定要删除此角色吗?", "${ctx}/role/delete?pkId=" + item.pkId, refreshRole);
}

function refreshRole(){
	$('#roleGrid').datagrid('reload');	
}

function queryRole(){
	$('#roleGrid').datagrid('reload',$("#queryRoleFrm").serializeObject());	
}

function clearForm(){
	$("#queryRoleFrm").form("clear");
}
</script>



<table id="roleGrid"></table>

<div id="role_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRole()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="authRoleMenu()">分配菜单</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshRole()">刷新</a>
		</div>
		<div>
		<form id="queryRoleFrm">
			登录名: <input class="easyui-textbox" name="userName" style="width:80px">
			用户昵称: <input class="easyui-textbox" name="nickName" style="width:80px">
			部门: <input name="departmentId" class="easyui-combotree" data-options="url:'${ctx}/org/listAll',method:'get'" />
			电话：<input class="easyui-textbox" name="telephone" style="width:80px">
			邮箱：<input class="easyui-textbox" name="email" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryRole()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
	</div>