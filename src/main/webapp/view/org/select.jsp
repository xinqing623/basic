<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>    
<script type="text/javascript">

function addSubOrg(isSub){
	var item = $("#orgTree").treegrid("getSelected");
	if(item == null){
		showAlert("请选择一个组织机构");
		return ;
	}
	$('#dlg').dialog({
        title: '新增组织机构',
        href:'${ctx}/org/preInsert?isSub='+ isSub +'&id=' + item.id,
        iconCls: "icon-add",
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

function editOrg(){
	var item = $("#orgTree").treegrid("getSelected");
	if(item == null){
		$.messager.alert("提示","请选择一个组织机构","info");
		return ;
	}
	$('#dlg').dialog({
        title: '修改组织机构',
        href:'${ctx}/org/preEdit?id=' + item.id,
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


function delOrg(){
	var item = $("#orgTree").treegrid("getSelected");
	if(item == null){
		showAlert("请选择一个组织机构");
		return ;
	}
	$.messager.confirm("提示","是否要删除此组织机构及下属所有组织机构？",function(isTrue){
		if(isTrue){
			$.ajax({
				url:'${ctx}/org/delete?id=' + item.id,
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
		                $('#orgTree').treegrid('reload');	
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

function refreshOrg(){
	$('#orgTree').treegrid('reload');
}

function queryOrgChart(){
	$('#orgTree').treegrid('reload',$("#queryOrgChartFrm").serializeObject());
}

function clearForm(){
	$("#queryOrgChartFrm").form('clear');
}
</script>


<table id="orgTree" title="公司架构图" class="easyui-treegrid" style="width:100%;height:100%"
			data-options="
				url: '${ctx}/org/selectPage',
				method: 'post',
				border:0,
				rownumbers: true,
				showFooter: true,
				singleSelect:true,
				idField: 'id',
				treeField: 'groupName',
				toolbar:'#org_chart_tb'
			">
		<thead>
			<tr>
				<th field="groupName"  width="40%" align="left">机构名称</th>				
				<th field="iconCls" width="30%" align="left">机构图标</th>
				<th field="createTime" formatter="dateFormatter" width="30%" align="center">创建时间</th>
			</tr>
		</thead>
	</table>
	
	
	<div id="org_chart_tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSubOrg('0')">新增同级机构</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSubOrg('1')">新增下级机构</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOrg()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delOrg()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshOrg()">刷新</a>
		</div>
		<div>
		<form id="queryOrgChartFrm">
			机构名称: <input class="easyui-textbox" name="groupName" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryOrgChart()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
	</div>	
	
