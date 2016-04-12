<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addEmailTemplateFrm").form('validate')){
			var datas = $("#addEmailTemplateFrm").serializeObject();
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
    
    
    <form id="addEmailTemplateFrm">
    <table>
    	<tr>
    		<td>模板名称：</td>
    		<td><input type="text" name="templateName" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>模板内容：</td>
    		<td><textarea name="content" class="easyui-validatebox textarea"  cols="20" rows="8" required="true"></textarea></td>
    	</tr>
    </table>
    </form>