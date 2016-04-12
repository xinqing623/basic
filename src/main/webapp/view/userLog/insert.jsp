<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addUserLogFrm").form('validate')){
			var datas = $("#addUserLogFrm").serializeObject();
			$.ajax({
				url:"${ctx}/userLog/save",
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
			                $('#userLogGrid').datagrid('reload');			
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
    
    
    <form id="addUserLogFrm">
    <table>
    	<tr>
    		<td>项目名称：</td>
    		<td><input type="text" name="projectId" class="easyui-combobox" data-options="url:'${ctx}/sysProject/selectUserProjects',valueField:'pkId',textField:'text',method:'get'" required="true"></td>
    	</tr>
    	<tr>
    		<td>工作内容：</td>
    		<td><input type="text" name="content" class="easyui-textbox" style="height:60px" data-options="multiline:true" required="true"/></td>
    	</tr>
    	<tr>
    		<td>遇到的问题：</td>
    		<td><input type="text" name="question" class="easyui-textbox" style="height:60px" data-options="multiline:true"></td>
    	</tr>
    	<tr>
    		<td>工作进度：</td>
    		<td><input type="text" name="progress" class="easyui-numberbox textbox" required="true" data-options="min:0,max:100,precision:0"></td>
    	</tr>
    	<tr>
    		<td>花费时长：</td>
    		<td><input type="text" name="spendTime" class="easyui-numberbox textbox" required="true" data-options="min:0,max:10,precision:2"></td>
    	</tr>
    	<tr>
    		<td>关联jira：</td>
    		<td><input type="text" name="relateJiraNo" class="easyui-validatebox textbox"></td>
    	</tr>
    </table>
    </form>