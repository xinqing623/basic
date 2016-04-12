<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addItemsFrm").form('validate')){
			var datas = $("#addItemsFrm").serializeObject();
			var categoryId = $("#categoryId").val();
			$.ajax({
				url:"${ctx}/dicItems/save",
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
			                $('#ddv-'+categoryId).datagrid('reload');			
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
    
    
    <form id="addItemsFrm">
    <input type="hidden" id="categoryId" name="categoryId" value="${categoryId }"/>
    <table>
    	<tr>
    		<td>字典名：</td>
    		<td><input type="text" name="itemName" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>字典值：</td>
    		<td><input type="text" name="itemValue" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    </table>
    </form>