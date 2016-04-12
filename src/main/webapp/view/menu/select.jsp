<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>    
<script type="text/javascript">

function addSubMenu(isSub){
	var item = $("#menuTree").treegrid("getSelected");
	if(item == null){
		$.messager.alert("提示","请选择一个菜单","info");
		return ;
	}
	$('#dlg').dialog({
        title: '对话框',
        href:'${ctx}/menu/preInsert?isSub='+ isSub +'&id=' + item.id,
        iconCls: "icon-edit",
        collapsible: true,
        minimizable: true,
        maximizable: true,
        resizable: true,
        width: 400,
        height: 300,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: function () {
            	saveFrm();
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#dlg').dialog('close');
            }
        }]
    });
}

function editMenu(){
	var item = $("#menuTree").treegrid("getSelected");
	if(item == null){
		showAlert("请选择一个字典分类");
		return ;
	}
	$('#dlg').dialog({
        title: '修改菜单',
        href:'${ctx}/menu/preEdit?id=' + item.id,
        iconCls: "icon-edit",
        collapsible: true,
        minimizable: true,
        maximizable: true,
        resizable: true,
        width: 300,
        height: 200,
        modal: true,
        buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: function () {
            	saveFrm();
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#dlg').dialog('close');
            }
        }]
    });
}


function delMenu(){
	var item = $("#menuTree").treegrid("getSelected");
	if(item == null){
		$.messager.alert("提示","请选择一个字典分类","info");
		return ;
	}
	$.messager.confirm("提示","是否要删除此菜单及下属所有菜单？",function(isTrue){
		if(isTrue){
			$.ajax({
				url:'${ctx}/menu/delete?id=' + item.id,
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
		                $('#menuTree').treegrid('reload');	
					}else{
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
					}
				}
			});
		}
	})
}

function refreshMenu(){
	$('#menuTree').treegrid('reload');	
}
</script>


<table id="menuTree" title="系统菜单" class="easyui-treegrid" style="width:100%;height:100%"
			data-options="
				url: '${ctx}/menu/selectPage',
				method: 'get',
				border:0,
				rownumbers: true,
				showFooter: true,
				idField: 'id',
				treeField: 'text',
				toolbar:'#sys_menu_tb'
			">
		<thead frozen="true">
			<tr>
				<th field="text" width="20%">菜单名称</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th field="url"  width="20%" align="right">菜单链接</th>
				<th field="isRedirect"  width="20%" align="right">是否跳转</th>
				<th field="iconCls" width="20%" align="right">菜单图标</th>
				<th field="state" width="10%" align="right">打开状态</th>
				<th field="status" width="10%" align="right">菜单状态</th>
				<th field="createTime" formatter="dateFormatter" width="20%" align="right">创建时间</th>
			</tr>
		</thead>
	</table>
	
	
	<div id="sys_menu_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSubMenu('0')">新增同级菜单</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSubMenu('1')">新增下级菜单</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMenu()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delMenu()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshMenu()">刷新</a>
		</div>
		<div>
			Date From: <input class="easyui-datebox" style="width:80px">
			To: <input class="easyui-datebox" style="width:80px">
			Language: 
			<select class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="java">Java</option>
				<option value="c">C</option>
				<option value="basic">Basic</option>
				<option value="perl">Perl</option>
				<option value="python">Python</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
	</div>	
	
