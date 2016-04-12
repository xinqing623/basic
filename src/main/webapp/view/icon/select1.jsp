<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %>   
<table>
	<thead>
		<th field="icon1"></th>
		<th field="icon2"></th>
		<th field="icon3"></th>
		<th field="icon4"></th>
		<th field="icon5"></th>
		<th field="icon6"></th>
		<th field="icon7"></th>
		<th field="icon8"></th>
		<th field="icon9"></th>
		<th field="icon10"></th>
		<th field="icon11"></th>
		<th field="icon12"></th>
		<th field="icon13"></th>
		<th field="icon14"></th>
		<th field="icon15"></th>
		<th field="icon16"></th>
		<th field="icon17"></th>
		<th field="icon18"></th>
		<th field="icon19"></th>
		<th field="icon20"></th>
	<thead>
	<tbody>
		<script>
		$.ajax({
			url:'${ctx}/icon/selectAll',
			type:'get',
			dataTye:'json',
			success:function(result){
				var len = result.length;
				var line = len/20;
				alert(len + "," + line);
			}
		})
		</script>		
	</tbody>
</table>