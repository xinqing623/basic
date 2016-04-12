<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addAutoCodeParamFrm").form('validate')){
			var datas = $("#addSysUserFrm").serializeObject();
			$.ajax({
				url:"${ctx}/autoCode/save",
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
    
    
    <form id="addAutoCodeParamFrm">
    <table>
    	<tr>
    		<td>数据库类型：</td>
    		<td>
    			<select name="dbType" class="easyui-combobox">
    				<option value="mysql">MySql</option>
    				<option value="oracle">Oracle</option>
    				<option value="sqlserver">SQL Server</option>
    			</select>
			</td>
    	</tr>
    	<tr>
    		<td>数据库名称：</td>
    		<td><input type="text" name="dbName"  class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>数据库驱动：</td>
    		<td><input type="text" name="jdbcDriver"  class="easyui-validatebox textbox" required="true"/></td>
    	</tr>
    	<tr>
    		<td>数据库连接：</td>
    		<td><input type="text" name="connUrl"  class="easyui-validatebox textbox"></td>
    	</tr>
    	<tr>
    		<td>数据库登录名：</td>
    		<td><input type="text" name="jdbcUsername"  class="easyui-validatebox textbox"></td>
    	</tr>
    	<tr>
    		<td>数据库密码：</td>
    		<td><input type="text" name="jdbcPassword"  class="easyui-validatebox textbox"></td>
    	</tr>
    	<tr>
    		<td>生成代码存放文件夹：</td>
    		<td><input type="text" name="destPath"  class="easyui-validatebox textbox"></td>
    	</tr>
    </table>
    </form>