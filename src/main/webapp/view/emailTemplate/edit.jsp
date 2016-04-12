<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#editEmailTemplateFrm").form('validate')){
			var datas = $("#editEmailTemplateFrm").serializeObject();
			$.ajax({
				url:"${ctx}/emailTemplate/save",
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
			                $('#emailTemplateGrid').datagrid('reload');			
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
    
    
    <form id="editEmailTemplateFrm">
    <input type="hidden" name="pkId" value="${emailTemplate.pkId }"/>
    <table>
    	<tr>
    		<td>模板名称：</td>
    		<td><input type="text" name="templateName" class="easyui-validatebox textbox" required="true" value="${emailTemplate.templateName }"></td>
    	</tr>
    	<tr>
    		<td>模板内容：</td>
    		<td><textarea name="content" class="easyui-validatebox textarea"  cols="20" rows="8" required="true">${emailTemplate.content }</textarea></td>
    	</tr>
    </table>
    </form>