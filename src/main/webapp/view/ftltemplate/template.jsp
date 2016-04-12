<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<meta charset="UTF-8">
<script>

$(function(){
	$("#dialog_save_btn").click(function(){
		if($("#editFtlTemplateContentFrm").form('validate')){
			var content = $("#tempContent").val();
			content  = content.replace(/\"/g, "&quot;");
			$("#tempContent").val(content);
			var datas = $("#editFtlTemplateContentFrm").serializeObject();
			$.ajax({
				url:"${ctx}/ftlTemplate/saveTemplate",
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
			                $('#ftlTemplateGrid').datagrid('reload');			
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
    
    
    <form id="editFtlTemplateContentFrm" style="width:100%; height:100%">
    <input type="hidden" name="id" value="${id }"/>
    <textarea name="content" id="tempContent"style="width:100%; height:500px">${content }</textarea>
    </form>