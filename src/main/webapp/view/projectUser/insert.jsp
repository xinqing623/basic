<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addProjectUserFrm").form('validate')){
			var userIds = [];
			var items = $("#projectUserList").tree("getChecked");
			if(null != items){
				for(var i=0; i<items.length; i++){
					userIds.push(items[i].id);
				}
				$("#userIds").val(userIds.join(","));
			}
			var datas = $("#addProjectUserFrm").serializeObject();
			
			$.ajax({
				url:"${ctx}/projectUser/save",
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





</script>
    
    
    <form id="addProjectUserFrm">
    <input name="projectId" type="hidden" value="${projectId}"/>
    <input name="userId" id="userIds" type="hidden" value=""/>
   	<ul id="projectUserList" class="easyui-tree" data-options="url:'${ctx}/projectUser/loadProjectUsers?projectId=${projectId}',method:'get',animate:true,checkbox:true">
   	</ul>
    </form>