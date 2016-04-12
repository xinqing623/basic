<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#EditSysUserFrm").form('validate')){
			var userRoles=[];
			$("input[name='roles']").each(function(){
				userRoles.push($(this).val());
			})
			$("#roleIds").val(userRoles.join(","));
			var datas = $("#EditSysUserFrm").serializeObject();
			$.ajax({
				url:"${ctx}/sysuser/save",
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
    <input type="hidden" name="id" value="${sysUser.id}" />
    <table>
    	<tr>
    		<td>用户姓名：</td>
    		<td><input type="text" name="nickName" class="easyui-validatebox textbox" value="${sysUser.nickName }" required="true"></td>
    	</tr>
    	<tr>
    		<td>所属部门：</td>
    		<td><input name="department" class="easyui-combotree" value="${sysUser.department }" data-options="url:'${ctx}/org/listAll',method:'get',required:true"/></td>
    	</tr>
    	<tr>
    		<td>电话号码：</td>
    		<td><input type="text" name="telephone" value="${sysUser.telephone }"  class="easyui-validatebox textbox" required="true" validType="mobile"></td>
    	</tr>
    	<tr>
    		<td>电子邮箱：</td>
    		<td><input type="text" name="email" value="${sysUser.email }" class="easyui-validatebox textbox" required="true" validType="email"></td>
    	</tr>
    	<tr>
    		<td>用户角色：</td>
    		<td>
    		<input type="hidden" name="roleIds" id="roleIds" value="${roleIds }" />
    		<input name="roles" class="easyui-combobox" required="true" value="${roleIds }" data-options="url:'${ctx}/role/selectRoles',valueField:'pkId',textField:'roleName',multiple:true,panelHeight:'auto',method:'get'"/>
    		</td>
    	</tr>
    </table>
    </form>