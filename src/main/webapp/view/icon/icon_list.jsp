<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>    
<c:forEach var="item" items="${iconList}" varStatus="status">
	<div class="icon_block easyui-tooltip ${item.iconCls}" title="${item.iconCls}" onclick="copyToClipBoard('${item.iconCls}')"></div>
</c:forEach>
