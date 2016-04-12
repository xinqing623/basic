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
    <input type="hidden" name="id" value="${stMenu.id }">
    <input type="hidden" name="parentId" value="${stMenu.parentId }">
    <table>
    	<tr>
    		<td>菜单名称：</td>
    		<td><input type="text" name="text" value="${stMenu.text}"></td>
    	</tr>
    	<tr>
    		<td>菜单链接：</td>
    		<td><input type="text" name="url" value="${stMenu.url}"></td>
    	</tr>
    	<tr>
    		<td>是否跳转：</td>
    		<td>
				<select name="isRedirect" class="easyui-combobox">
					<option value="0" <c:if test="${stMenu.isRedirect eq '0' }">selected="selected"</c:if>>否</option>
					<option value="1" <c:if test="${stMenu.isRedirect eq '1' }">selected="selected"</c:if>>是</option>
				</select>
			</td>
    	</tr>
    	<tr>
    		<td>菜单图标：</td>
    		<td><input type="text" name="iconCls" value="${stMenu.iconCls}"></td>
    	</tr>
    	<tr>
    		<td>菜单状态：</td>
    		<td>
				<select name="state">
					<option value="open" <c:if test="${stMenu.state eq 'open' }">selected="selected"</c:if>>打开</option>
					<option value="closed" <c:if test="${stMenu.state eq 'closed' }">selected="selected"</c:if>>关闭</option>
				</select>
			</td>
    	</tr>
    </table>
    </form>