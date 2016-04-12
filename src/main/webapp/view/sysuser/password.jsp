<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#userPasswordFrm").form('validate')){
			if($("#newPassword1").val() != $("#newPassword").val()){
				showAlert("新密码与确认密码不一致");
				return false;
			}
			var datas = $("#userPasswordFrm").serializeObject();
			$.ajax({
				url:"${ctx}/sysuser/modifyPassword",
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
			                $('#sysUserGrid').datagrid('reload');			
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
    
    
    <form id="userPasswordFrm">
    <table>
    	<tr>
    		<td>旧密码：</td>
    		<td><input type="password" name="oldPassword" class="easyui-validatebox textbox" required="true" minlength="6" maxlength="20"></td>
    	</tr>
    	<tr>
    		<td>新密码：</td>
    		<td><input type="password" name="newPassword" id="newPassword" class="easyui-validatebox textbox" required="true" minlength="6" maxlength="20"/></td>
    	</tr>
    	<tr>
    		<td>确认密码：</td>
    		<td><input type="password" id="newPassword1" name="newPassword1"  class="easyui-validatebox textbox" minlength="6" maxlength="20" required="true"></td>
    	</tr>
    </table>
    </form>