<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addRoleFrm").form('validate')){
			var datas = $("#addRoleFrm").serializeObject();
			$.ajax({
				url:"${ctx}/role/save",
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
			                $('#roleGrid').datagrid('reload');			
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
    
    
    <form id="addRoleFrm">
    <table>
    	<tr>
    		<td>角色名：</td>
    		<td><input type="text" name="roleName" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark"  class="easyui-validatebox textbox"></td>
    	</tr>
    </table>
    </form>