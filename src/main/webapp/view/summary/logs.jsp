<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>  
<html>
<head>
<meta charset="UTF-8">
<title>日志列表</title>
<style>
*{font-size:12px;}
.logTable{width:1050px; border-top:1px solid #cccccc; border-left:1px solid #cccccc}
.logTable td{height:30px; border-bottom:1px solid #cccccc; border-right:1px solid #cccccc}
.logTable a{color:#000000; text-decoration: none;}
.state_title{background:#FFFFCC}
.state_content{background:#FFFFCC}
.state_span td{border-left:none !important;}
</style>
</head>
<body>
<table class="logTable" align="center" border="0" rowspacing="0" cellspacing="0">
<c:forEach var="map" items="${logs}">
	<tr class="state_title">
		<td colspan="7">${map.key.nickName }</td>
	</tr>	
	<c:forEach var="value" items="${map.value}">
	<tr class="state_content">
		<td width="10%"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${value.createTime}"/></td>
		<td width="10%">${value.sysProject.projectName}</td>
		<td width="25%">${value.content}</td>
		<td width="10%">${value.progress}%</td>
		<td width="25%">${value.question}</td>
		<td width="10%">${value.spendTime}</td>
		<td width="10%"><a href="${value.sysProject.jiraUrl}/${value.relateJiraNo}" target="_blank">${value.relateJiraNo}</a></td>
	</tr>
	</c:forEach>
	<tr class="state_span">
		<td colspan="7" height="30"></td>
	</tr>
</c:forEach>
</table>
</body>
</html>