<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>
function saveFrm(){
	var datas = $("#addMenuFrm").serializeObject();
	$.ajax({
		url:"${ctx}/menu/save",
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
	                $('#menuTree').treegrid('reload');			
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
    
    
    <form id="addMenuFrm">
    <c:if test="${isSub eq '1' }">
    	<input type="hidden" name="parentId" value="${stMenu.id }">
    	<input type="hidden" name="pid" value="${stMenu.id }" />
    </c:if>
    <c:if test="${isSub ne '1' }">
    	<input type="hidden" name="parentId" value="${stMenu.parentId }">
    	<input type="hidden" name="pid" value="${stMenu.parentId }" />
    </c:if>
    <table>
    	<tr>
    		<td>菜单名称：</td>
    		<td><input type="text" name="text" value="" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>菜单链接：</td>
    		<td><input type="text" name="url" value="" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>是否跳转：</td>
    		<td>
				<select name="isRedirect" class="easyui-combobox">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
    	</tr>
    	<tr>
    		<td>菜单图标：</td>
    		<td><input type="text" name="iconCls" value="" class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>菜单状态：</td>
    		<td>
				<select name="state" class="easyui-combobox">
					<option value="open">打开</option>
					<option value="closed">关闭</option>
				</select>
			</td>
    	</tr>
    </table>
    </form>