<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<script type="text/javascript">
var dicCategoryGrid; 
$(function(){
	dicCategoryGrid = $("#dicCategoryGrid").datagrid({
		view: detailview,
		url : "${ctx}/dic/selectPage",//加载的URL  
		isField : "id",
		toolbar:"#tb",
		height:"100%",
		width:"100%",
		border:0,
		rownumbers:true,
		pagination : true,//显示分页  
		singleSelect:true,
		pageSize : 10,//分页大小  
		pageList : [ 5, 10, 15, 20 ],//每页的个数  
		fit : false,//自动补全  
		fitColumns : false,
		iconCls : "icon-save",//图标  
		title : "字典分类",
		columns : [ [{
			field : 'categoryName',
			title : '分类名',
			width : "30%"
		},{
			field : 'categoryCode',
			title : '字典编码',
			width : "40%"
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : "30%",
			formatter:dateFormatter
		} ] ],
		detailFormatter:function(index,row){//注意2  
            return '<div style="padding:2px"><table id="ddv-' + row.id + '"></table></div>';  
        },
		onExpandRow:function(index, row){
			$('#ddv-'+row.id).datagrid({  
				url : "${ctx}/dicItems/selectItems?categoryId=" +(row.id),//加载的URL  
				isField : "id",
				height:'auto',
				width:"100%",
				border:"0",
				rownumbers:true,
				fit : false,//自动补全  
				fitColumns : false,
				iconCls : "icon-save",//图标  
				title : "字典项",
				columns : [ [{
					field : 'op',
					title : '操作',
					width : "20%",
					formatter:dicItemOperator
				},{
					field : 'itemName',
					title : '字典名',
					width : "30%"
				},
				{
					field : 'itemValue',
					title : '字典值',
					width : "47%"
				}] ]
			}); 
		}
	}); 
	
	
})

function dicItemOperator(value, row, index){
	var txt = "";
	if(row.id){
		txt += '<a class="icon-edit easyui-tooltip sub-icon" title="修改" onclick="editDicItem(\'' + row.id+'\')">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
		txt += '<a class="icon-remove easyui-tooltip sub-icon" title="删除" onclick="delDicItem(\'' + row.categoryId+'\',\'' + row.id+'\')">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
	}
	return txt;
}


function editDicItem(id){
	openDialog("修改字典项的值", '${ctx}/dicItems/preEdit?id=' + id, 400, 300 ,'icon-edit');
}

function delDicItem(categoryId,id){
	ajaxDelete("确定要删除此字典项吗?", "${ctx}/dicItems/delete?id=" + id, function(){
		 $('#ddv-'+categoryId).datagrid('reload');
	});
}

function addCategory(){
	$('#dlg').dialog({
        title: '新增字典分类',
        href:'${ctx}/dic/preInsertCategory',
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


function delCategory(){
	var item = $("#dicCategoryGrid").datagrid("getSelected");
	if(item == null){
		$.messager.alert("提示","请选择一个字典分类","info");
		return ;
	}
	$.messager.confirm("提示","是否要删除此字典分类及其字典项？",function(isTrue){
		if(isTrue){
			$.ajax({
				url:'${ctx}/dic/delCategory?id=' + item.id,
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
		                $('#dicCategoryGrid').datagrid('reload');	
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



function addItems(){
	var item = $("#dicCategoryGrid").datagrid("getSelected");
	if(item == null){
		$.messager.alert("提示","请选择一个字典分类","info");
		return ;
	}	
	openDialog("修改系统配置", '${ctx}/dicItems/preInsert?categoryId=' + item.id, 400, 300 ,'icon-add');
}
function refreshCategory(){
	$('#dicCategoryGrid').datagrid('reload');
}

function queryDicCategory(){
	$('#dicCategoryGrid').datagrid('reload',$("#queryDicCategoryFrm").serializeObject());	
}

function clearForm(){
	$("#queryDicCategoryFrm").form("clear");
}

</script>


<table id="dicCategoryGrid">

</table>


<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCategory()">新增字典分类</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addItems()">新增字典项</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCategory()">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delCategory()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshCategory()">刷新</a>
		</div>
		<div>
		<form id="queryDicCategoryFrm">
			分类名: <input class="easyui-textbox" name="categoryName" style="width:80px">
			字典编码: <input class="easyui-textbox" name="categoryCode" style="width:80px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryDicCategory()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">清除</a>
		</form>
		</div>
</div>
	
