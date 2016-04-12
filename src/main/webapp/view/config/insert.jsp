<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addSysConfigFrm").form('validate')){
			var datas = $("#addSysConfigFrm").serializeObject();
			$.ajax({
				url:"${ctx}/config/save",
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
			                $('#sysConfigGrid').datagrid('reload');			
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
    
    
    <form id="addSysConfigFrm">
    <table>
    	<tr>
    		<td>配置名：</td>
    		<td><input type="text" name="configName" class="easyui-validatebox textbox" required="true" maxlength="100"></td>
    	</tr>
    	<tr>
    		<td>配置值：</td>
    		<td><input type="text" name="configValue"  class="easyui-validatebox textbox" required="true" maxlength="100"></td>
    	</tr>
    	<tr>
    		<td>备注：</td>
    		<td><input type="text" name="remark"  class="easyui-validatebox textbox" maxlength="100"></td>
    	</tr>
    </table>
    </form>