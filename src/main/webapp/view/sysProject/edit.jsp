<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#editSysProjectFrm").form('validate')){
			var datas = $("#editSysProjectFrm").serializeObject();
			$.ajax({
				url:"${ctx}/sysProject/save",
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
			                $('#sysProjectGrid').datagrid('reload');			
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
    
    
    <form id="editSysProjectFrm">
    <input type="hidden" name="pkId" value="${sysProject.pkId }"/>
    <table>
    	<tr>
    		<td>项目名称：</td>
    		<td><input type="text" name="projectName" class="easyui-validatebox textbox" required="true" value="${sysProject.projectName }"></td>
    	</tr>
    	<tr>
    		<td>项目说明：</td>
    		<td><input type="text" name="remark" class="easyui-validatebox textbox" required="true" value="${sysProject.remark}"></td>
    	</tr>
    	<tr>
    		<td>负责人：</td>
    		<td><input type="text" name="leaderId" class="easyui-combobox" required="true" data-options="url:'${ctx}/sysuser/selectUsers',valueField:'id',textField:'nickName',method:'get'" value="${sysProject.leaderId}"></td>
    	</tr>
    	<tr>
    		<td>JIRA地址：</td>
    		<td><input type="text" name="jiraUrl" class="easyui-validatebox textbox" value="${sysProject.jiraUrl}"></td>
    	</tr>
    	<tr>
    		<td>开始时间：</td>
    		<td><input type="text" name="startTime" class="easyui-datebox textbox" required="true" value="${sysProject.startTime}"></td>
    	</tr>
    	<tr>
    		<td>截止时间：</td>
    		<td><input type="text" name="endTime" class="easyui-datebox textbox" required="true" value="${sysProject.endTime}"></td>
    	</tr>
    	<tr>
    		<td>状态：</td>
    		<td>
    			<select class="easyui-combobox" name="status">
    				<option value="1" <c:if test="${sysProject.status eq '1'}">selected</c:if>>正常</option>
    				<option value="2" <c:if test="${sysProject.status eq '2'}">selected</c:if>>暂停</option>
    				<option value="3" <c:if test="${sysProject.status eq '3'}">selected</c:if>>停止</option>
    			</select>
    	</tr>
    </table>
    </form>