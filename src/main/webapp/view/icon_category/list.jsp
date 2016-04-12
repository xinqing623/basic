<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>  
<script>
$(function(){
	$('#sys_icon_tabs').tabs({tabPosition:"left",border:false}); 
})
</script>

<div id="sys_icon_tabs" class="easyui-tabs" style="width:100%;height:100%;">
		<c:forEach var="item" items="${categoryList }">
		<div title="${item.categoryName }" style="padding:10px" href="${ctx}/icon/selectCategoryIcon?categoryId=${item.id}">
			
		</div>
		</c:forEach>
</div>