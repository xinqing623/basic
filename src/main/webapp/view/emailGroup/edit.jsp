<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#editEmailGroupFrm").form('validate')){
			var datas = $("#editEmailGroupFrm").serializeObject();
			$.ajax({
				url:"${ctx}/emailGroup/save",
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
			                $('#emailGroupGrid').datagrid('reload');			
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
    
    
    <form id="editEmailGroupFrm">
    <input type="hidden" name="pkId" value="${emailGroup.pkId }" />
    <table>
    	<tr>
    		<td>邮件组名：</td>
    		<td><input type="text" name="groupName" class="easyui-validatebox textbox" required="true" value="${emailGroup.groupName }"></td>
    	</tr>
    	<tr>
    		<td>提醒邮件时间：</td>
    		<td><input type="text" name="remindTime" class="easyui-validatebox textbox" required="true" value="${emailGroup.remindTime }"></td>
    	</tr>
    	<tr>
    		<td>汇总邮件时间：</td>
    		<td><input type="text" name="sendTime" class="easyui-validatebox textbox" required="true" value="${emailGroup.sendTime }"></td>
    	</tr>
    	<tr>
    		<td>提醒邮件模板：</td>
    		<td><input type="text" name="remindTemplateId" class="easyui-validatebox textbox" required="true" value="${emailGroup.remindTemplateId }"></td>
    	</tr>
    	<tr>
    		<td>汇总邮件模板：</td>
    		<td><input type="text" name="sendTemplateId" class="easyui-validatebox textbox" required="true" value="${emailGroup.sendTemplateId }"></td>
    	</tr>
    	<tr>
    		<td>状态：</td>
    		<td>
				<input type="text" name="status" class="easyui-combobox" required="true" value="${emailGroup.status }" data-options="data:[{'id':1,'text':'正常'},{'id':2,'text':'暂停'}],valueField:'id',textField:'text'" />
			</td>
    	</tr>
    </table>
    </form>