<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#EditSysUserFrm").form('validate')){
			var datas = $("#EditSysUserFrm").serializeObject();
			$.ajax({
				url:"${ctx}/user/save",
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
    
    
    <form id="EditSysUserFrm">
    <input type="hidden" name="id" value="${user.id}" />
    <table>
    	<tr>
    		<td>用户名：</td>
    		<td><input type="text" name="userName" class="easyui-validatebox textbox" required="true" value="${user.userName}"></td>
    	</tr>
    	<tr>
    		<td>用户昵称：</td>
    		<td><input type="text" name="nickName"  class="easyui-validatebox textbox" required="true" validType="chinese" value="${user.nickName }"></td>
    	</tr>
    	<tr>
    		<td>所属部门：</td>
    		<td><input name="departmentId" class="easyui-combotree" data-options="url:'${ctx}/org/listAll',method:'get',required:true" value="${user.departmentId }" /></td>
    	</tr>
    	<tr>
    		<td>电话号码：</td>
    		<td><input type="text" name="telephone"  class="easyui-validatebox textbox" required="true" validType="mobile" value="${user.telephone }"></td>
    	</tr>
    	<tr>
    		<td>电子邮箱：</td>
    		<td><input type="text" name="email" class="easyui-validatebox textbox" required="true" validType="email" value="${user.email }"></td>
    	</tr>
    </table>
    </form>