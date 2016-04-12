<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<style>
.block{width:500px; margin-top:10px; padding-bottom:10px; border-bottom:1px dashed #ccc}
</style>
<script type="text/javascript">
function lowFareSearch(){
	$("#commonfig1").html($("#config").html());
	var params =$("#lowFareSearch").serializeObject();
	$.ajax({
		url:"${ctx}/web/lowFareSearch",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				var tmpUid = data.obj;
				$("#tmpUid").val(tmpUid);
				$("#tmpUid").val(tmpUid);
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}


function calculateTrip(){
	$("#commonfig2").html($("#config").html());
	var params =$("#calculateTrip").serializeObject();
	$.ajax({
		url:"${ctx}/web/calculateTrip",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				var tripId = data.obj;
				$("#tripId").val(tripId);
				$("#tmpUid1").val(tripId);
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}

function verifyTrips(){
	$("#commonfig3").html($("#config").html());
	var params =$("#verifyTrips").serializeObject();
	$.ajax({
		url:"${ctx}/web/verifyTrips",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				alert(data.obj);
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}

function createReservation(){
	$("#commonfig4").html($("#config").html());
	var params =$("#createReservation").serializeObject();
	$.ajax({
		url:"${ctx}/web/createReservation",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				var code = data.obj;
				$("#reservationCode").val(code);
				$("#reservationCode1").val(code);
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}

function documentReservation(){
	$("#commonfig5").html($("#config").html());
	var params =$("#documentReservation").serializeObject();
	$.ajax({
		url:"${ctx}/web/documentReservation",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				var obj = data.obj;
				alert(obj);
			}else{
				$.messager.show({
                    title: '提示',
                    msg: data.message
                });
			}
		}
	});	
}

function getReservation(){
	$("#commonfig6").html($("#config").html());
	var params =$("#getReservation").serializeObject();
	$.ajax({
		url:"${ctx}/web/getReservation",
		type:"post",
		dataType:"json",
		data: params,
		success:function(data){
			if(data.success){
				var obj = data.obj;
				//alert(obj);
				$("#orderContent").val(obj);
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


<div>
	<div class="block">
		通用参数:
		<form id="config">		
		ota编码：<input type="text" class="easyui-validatebox textbox" name="otaCodeStr" value="GXAIRLINES_120" required="true"/><br/>
		密钥：<input type="text" class="easyui-validatebox textbox" name="keyStr" value="69CDDG7D0042E4C672F46F0BACGAE3H2" required="true"/><br/>
		后缀：<input type="text" class="easyui-validatebox textbox" name="suffix" value=".json" required="true"/><br/>
		</form>
	</div>
	<div class="block">
		第一步： 查询最低票价
		<form id="lowFareSearch">
			出发地三字码：<input type="text" class="easyui-validatebox textbox"  name="orgin" required="true" /><br/>
			目的地三字码：<input type="text" class="easyui-validatebox textbox"  name="destination" required="true" /><br/>
			出发日期：<input type="text" class="easyui-datebox"  name="date1" required="true" /><br/>
			返回日期：<input type="text" class="easyui-datebox"  name="date2"/><br/>
			旅客类型1：<input type="text" class="easyui-validatebox textbox"  name="traveler1Type" required="true" /><br/>
			旅客类型2<input type="text" class="easyui-validatebox textbox"  name="traveler2Type"/><br/>
			<div id="commonfig1" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="lowFareSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>
	</div>
	
	<div class="block">
		第二步：计算税费
		<form id="calculateTrip">
			<input type="text" class="easyui-validatebox textbox" id="tmpUid"  name="tmpUid" required="true" /><br/>
			<div id="commonfig2" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="calculateTrip()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>		
	</div>

	<div class="block">
		第三步：验证票价是否合理
		<form id="verifyTrips">
			<input type="text" class="easyui-validatebox textbox" id="tripId" name="tripId" required="true" /><br/>
			<div id="commonfig3" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="verifyTrips()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>		
	</div>
	
	<div class="block">
		第四步：创建订单
		<form id="createReservation">
			TripId:<input type="text" class="easyui-validatebox textbox" id="tmpUid1" name="tmpUid" required="true" /><br/>
			旅客1类型：<input type="text" class="easyui-validatebox textbox" id="traveler1Type" name="traveler1Type" required="true" /><br/>
			旅客1姓：<input type="text" class="easyui-validatebox textbox" id="traveler1lName" name="traveler1lName" required="true" /><br/>
			旅客1名：<input type="text" class="easyui-validatebox textbox" id="traveler1fName" name="traveler1fName" required="true" /><br/>
			旅客1证件类型：<input type="text" class="easyui-validatebox textbox" id="traveler1DocType" name="traveler1DocType" value="2.DOC" required="true" /><br/>
			旅客1证件号：<input type="text" class="easyui-validatebox textbox" id="traveler1DocNo" name="traveler1DocNo" required="true" /><br/>
			旅客1证件国家：<input type="text" class="easyui-validatebox textbox" id="traveler1DocCountry" name="traveler1DocCountry" value="CN" required="true" /><br/>
			旅客1手机号：<input type="text" class="easyui-validatebox textbox" id="traveler1PhoneNo" name="traveler1PhoneNo" required="true" /><br/>
			旅客1邮箱：<input type="text" class="easyui-validatebox textbox" id="traveler1Email" name="traveler1Email" required="true" /><br/>
			<div id="commonfig4" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="createReservation()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>		
	</div>
	
	<div class="block">
		第五步：出票
		<form id="documentReservation">
			订单号：<input type="text" class="easyui-validatebox textbox" id="reservationCode" name="reservationCode" required="true" /><br/>
			金额：<input type="text" class="easyui-validatebox textbox" id="orderAmount" name="amount" required="true" /><br/>
			<div id="commonfig5" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="documentReservation()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>		
	</div>

	<div  class="block">
		第六步：查询订单
		<form id="getReservation">
			订单号：<input type="text" class="easyui-validatebox textbox" id="reservationCode1" name="reservationCode" required="true" /><br/>
			<div id="commonfig6" style="display:none"></div>
			<a id="btn" href="javascript:void(0)" onclick="getReservation()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br/>
		</form>	
		
		<textarea id="orderContent" style="width:400px; height:200px"></textarea>
	</div>
</div>

