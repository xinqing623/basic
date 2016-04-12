<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
function saveFrm(){
	var datas = $("#editMenuFrm").serializeObject();
	$.ajax({
		url:"${ctx}/org/save",
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
	                $('#orgTree').treegrid('reload');			
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}
</script>
    
    
    <form id="editMenuFrm">
    <input type="hidden" name="id" value="${organization.id }">
    <input type="hidden" name="parentId" value="${organization.parentId }">
    <table>
    	<tr>
    		<td>机构名称：</td>
    		<td><input type="text" name="groupName" class="easyui-validatebox textbox" required="true" value="${organization.groupName}"></td>
    	</tr>
    	<tr>
    		<td>机构图标：</td>
    		<td><input type="text" name="iconCls" class="easyui-validatebox textbox" required="true" value="${organization.iconCls}"></td>
    	</tr>
    </table>
    </form>