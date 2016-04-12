<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addFtlTemplateFrm").form('validate')){
			var datas = $("#addFtlTemplateFrm").serializeObject();
			$.ajax({
				url:"${ctx}/ftlTemplate/save",
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
			                $('#ftlTemplateGrid').datagrid('reload');			
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
    
    
    <form id="addFtlTemplateFrm">
    <table>
    	<tr>
    		<td>模板名：</td>
    		<td><input type="text" name="templateName" class="easyui-validatebox textbox" required="true" maxlength="100"></td>
    	</tr>
    	<tr>
    		<td>文件夹：</td>
    		<td><input type="text" name="folder"  class="easyui-validatebox textbox" required="true" maxlength="100"></td>
    	</tr>
    	<tr>
    		<td>文件后缀：</td>
    		<td><input type="text" name="suffix"  class="easyui-validatebox textbox" maxlength="100"></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark"  class="easyui-validatebox textbox" maxlength="100"></td>
    	</tr>
    </table>
    </form>