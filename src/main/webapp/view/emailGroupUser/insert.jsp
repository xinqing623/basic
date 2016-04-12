<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addEmailGroupUserFrm").form('validate')){
			var userIds = [];
			var items = $("#emailGroupUserList").tree("getChecked");
			if(null != items){
				for(var i=0; i<items.length; i++){
					userIds.push(items[i].id);
				}
				$("#userIds").val(userIds.join(","));
			}
			
			var ccUserIds = [];
			var ccItems = $("#emailCcGroupUserList").tree("getChecked");
			if(null != ccItems){
				for(var i=0; i< ccItems.length; i++){
					ccUserIds.push(ccItems[i].id);
				}
				$("#ccUserIds").val(ccUserIds.join(","));
			}
			var datas = $("#addEmailGroupUserFrm").serializeObject();
			
			$.ajax({
				url:"${ctx}/emailGroupUser/save",
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
    
    
    <form id="addEmailGroupUserFrm">
    <input name="emailGroupId" type="hidden" value="${emailGroupId}"/>
    <input name="userId" id="userIds" type="hidden" value=""/>
    <input name="ccUserId" id="ccUserIds" type="hidden" value=""/>
    <div style="width:150px; float: left;">
   	<ul id="emailGroupUserList" class="easyui-tree" data-options="url:'${ctx}/emailGroupUser/loadEmailGroupUsers?emailGroupId=${emailGroupId}&isCc=0',method:'get',animate:true,checkbox:true">
   	</ul>
   	</div>
   	<div style="width:150px; float: left;">
   	<ul id="emailCcGroupUserList" class="easyui-tree" data-options="url:'${ctx}/emailGroupUser/loadEmailGroupUsers?emailGroupId=${emailGroupId}&isCc=1',method:'get',animate:true,checkbox:true">
   	</ul>
   	</div>
    </form>