<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#addSysIconCategoryFrm").form('validate')){
			var datas = $("#addSysIconCategoryFrm").serializeObject();
			$.ajax({
				url:"${ctx}/iconCategory/save",
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
			                $('#sysIconCategoryGrid').datagrid('reload');			
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
    
    
    <form id="addSysIconCategoryFrm">
    <table>
    	<tr>
    		<td>分类名：</td>
    		<td><input type="text" name="categoryName" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    </table>
    </form>