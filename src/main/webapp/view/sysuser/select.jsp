<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<script type="text/javascript">
$(function(){

	$("#sysUserGrid").datagrid({
		url : "${ctx}/sysuser/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#sys_user_tb",
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
		title : "系统用户",
		columns : [ [
		{
			field : 'nickName',
			title : '用户姓名',
			width : "14%"
		},
		{
			field : 'department',
			title : '所属部门',
			width : "14%",
			formatter:organizationFormatter
		}
		,{
			field : 'telephone',
			title : '电话',
			width : "14%"
		},
		{
			field : 'email',
			title : '邮箱',
			width : "14%"
		},
		{
			field : 'sex',
			title : '性别',
			width : "14%",
			formatter:sexFormatter
		},
		{
			field : 'userRole',
			title : '用户角色',
			width : "14%",
			formatter:userRoleFormatter
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

function authRole2User(){
	var item = $("#sysUserGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	openDialog("给用户添加角色", '${ctx}/roleUser/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function addSysUser(){
	openDialog("添加系统用户", '${ctx}/sysuser/preInsert', 400, 300 ,'icon-add');
}

function editSysUser(){
	var item = $("#sysUserGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	openDialog("修改系统用户", '${ctx}/sysuser/preEdit?id=' + item.id, 400, 300 ,'icon-edit');
}

function delSysUser(){
	var item = $("#sysUserGrid").datagrid("getSelected");
	if(item == null){
		showAlert("请选择一个用户");
		return ;
	}
	ajaxDelete("确定要删除此用户吗?", "${ctx}/sysuser/delete?id=" + item.id, refreshSysUser);
}

function refreshSysUser(){
	$('#sysUserGrid').datagrid('reload');	
}

function querySysUser(){
	$('#sysUserGrid').datagrid('reload',$("#querySysUserFrm").serializeObject());	
}

function clearForm(){
	$("#querySysUserFrm").form("clear");
}
</script>



<table id="sysUserGrid"></table>

<div id="sys_user_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSysUser()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editSysUser()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delSysUser()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="authRole2User()">授权</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshSysUser()">刷新</a>
		</div>
		<div>
		<form id="querySysUserFrm">
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