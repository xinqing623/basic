<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#generateCodeFrm").form('validate')){
			var tableNames=[];
			var tempFileNames = [];
			$("input[name='dbTableName']:checked").each(function(){
				tableNames.push($(this).val());
			});
			if(tableNames.length == 0){
				showAlert("请至少选择一个表");
				return;
			}
			
			$("input[name='tempFileNames']").each(function(){
				tempFileNames.push($(this).val());
			});
			if(tempFileNames.length == 0){
				showAlert("请至少选择一个模板");
				return;
			}
			$("#tableNames").val(tableNames.join(","));
			$("#tempFile").val(tempFileNames.join(","));
			var datas = $("#generateCodeFrm").serializeObject();
			$.ajax({
				url:"${ctx}/autoCode/generateCode",
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
    
    
    <table id="db_tables_gird" class="easyui-datagrid" style="width:98%;height:auto;" data-options="border:0,rownumbers:true,toolbar:'#select_table_param_tb'">
    	<thead>
    		<tr>
    			<th field="field1" width="20%">选择</th>
    			<th field="field2" width="70%">表名</th>
    		</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="item" items="${tables }">
    		<tr>
    			<td><input type="checkbox" value="${item.tableName }" name="dbTableName"/></td>
    			<td>${item.tableName }</td>
    		</tr>
    	</c:forEach>
    	</tbody>
    </table>

    
    <div id="select_table_param_tb" style="padding:5px;height:auto">
    <form id="generateCodeFrm">
    <input type="hidden" name="id" value="${id}"/>
    <input type="hidden" name="tableName" id="tableNames" value=""/>
    <input type="hidden" name="tempFile" id="tempFile" value=""/>
		<div>
			是否覆盖: <input type="checkbox" name="isCoverOldFile" value="true">
			基础包名: <input class="easyui-textbox" name="basePackage" style="width:80px">
			模板名: <input name="tempFileNames" class="easyui-combobox" style="width:160px" data-options="
					url:'${ctx}/ftlTemplate/listAll',
					method:'get',
					valueField:'id',
					textField:'templateName',
					multiple:true,
					panelHeight:'auto'" />
		</div>
	</form>
	</div>