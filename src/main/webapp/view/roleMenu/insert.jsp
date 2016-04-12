<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	
	initData();
	
	$("#dialog_save_btn").click(function(){		
		if($("#addRoleMenuFrm").form('validate')){
			var menuIds = [];
			var nodes = $('#roleMenuTree').tree('getChecked',  ['checked']);
			if(null != nodes){
				for(var i=0; i<nodes.length; i++){
					menuIds.push(nodes[i].id);
				}
				$("#menuIds").val(menuIds.join(","));
			}			
			var datas = $("#addRoleMenuFrm").serializeObject();
			
			$.ajax({
				url:"${ctx}/roleMenu/save",
				type:"post",
				dataType:"json",
				data: datas,
				success:function(data){
					if(data.success){
						 $('#dlg').dialog('close');
			                $.messager.show({
			                    title: '提示',
			                    msg: data.message
			                });
			               // $('#sysProjectGrid').datagrid('reload');			
					}else{
						$.messager.show({
		                    title: '提示',
		                    msg: data.message
		                });
					}
				}
			});
		}
	});
		
})



function initData(){
	var txt = '${menus}';
	txt = eval('(' + txt + ')');
	$('#roleMenuTree').tree({
        data: txt,
        checkbox:true
	}); 
	
	var selectedMenus = '${roleMenus}';
	selectedMenus =  eval('(' + selectedMenus + ')');
	var indexs = [];	
	for(var i=0; i<selectedMenus.length; i++){
		var node = $('#roleMenuTree').tree('find', selectedMenus[i].menuId);
		$('#roleMenuTree').tree('check', node.target);
	} 
}


</script>
    
    
    <form id="addRoleMenuFrm">
    <input name="roleId" type="hidden" value="${roleId}"/>
    <input name="menuIds" id="menuIds" type="hidden" value=""/>
    <ul id="roleMenuTree">    
    </ul>
    </form>