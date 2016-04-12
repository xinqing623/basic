<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>


	function saveFrm() {
		var datas = $("#addOrgFrm").serializeObject();
		$.ajax({
			url : "${ctx}/org/save",
			type : "post",
			dataType : "json",
			data : datas,
			success : function(data) {
				if (data.success) {
					$('#dlg').dialog('close');
					$.messager.show({
						title : '提示',
						msg : data.message
					});
					$('#orgTree').treegrid('reload');
				} else {
					$.messager.show({
						title : '提示',
						msg : data.message
					});
				}
			}
		});
	}
</script>
    
    
    <form id="addOrgFrm">
    <c:if test="${isSub eq '1' }">
    	<input type="hidden" name="parentId" value="${chart.id }">
    	<input type="hidden" name="pid" value="${chart.id }" />
    </c:if>
    <c:if test="${isSub ne '1' }">
    	<input type="hidden" name="parentId" value="${chart.parentId }">
    	<input type="hidden" name="pid" value="${chart.parentId }" />
    </c:if>
    <table>
    	<tr>
    		<td>组织机构名称：</td>
    		<td><input type="text" name="groupName"  class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>组织机构图标：</td>
    		<td><input type="text" name="iconCls"  class="easyui-validatebox textbox" required="true"></td>
    	</tr>
    	<tr>
    		<td>组织机构图标1：</td>
    		<td><select id="cc" class="easyui-combogrid"></select></td>
    	</tr>
    </table>
    </form>
    
    <script>
	$("#cc").combogrid({
		panelWidth : 400,
		idField : "id",
		textField : "categoryName",
		url : "${ctx}/dic/selectCategory",
		columns : [ [ {
			field : 'categoryName',
			title : '分类名',
			width : "30%"
		}, {
			field : 'categoryCode',
			title : '字典编码',
			width : "40%"
		} ] ]
	});
    
    </script>