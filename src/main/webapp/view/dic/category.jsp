<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
function saveFrm(){
	var datas = $("#addCategoryFrm").serializeObject();
	$.ajax({
		url:"${ctx}/dic/saveCategory",
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
	                $('#dicCategoryGrid').datagrid('reload');	
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
    
    
    <form id="addCategoryFrm">
    <table>
    	<tr>
    		<td>字典名称：</td>
    		<td><input type="text" name="categoryName" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>字典编码：</td>
    		<td><input type="text" name="categoryCode" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    </table>
    </form>