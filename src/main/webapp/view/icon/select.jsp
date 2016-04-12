<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>    
<script>
var styles='';
$(function(){
	loadCss();	
})

function loadCss(){
	var styleArr = styles.split(",");
	var txt = "";
	for(var i=0; i< styleArr.length; i++){
		txt += "<div class='icon_block easyui-tooltip " + styleArr[i] + "' title='" + styleArr[i] + "' onclick=copyToClipBoard(\'" + styleArr[i] +"\')></div>";
	}
	$("#pan").append(txt);
}

function copyToClipBoard(text){
	document.execCommand('copy');
	alert("复制成功");
}

</script>

<div id="pan">

</div>