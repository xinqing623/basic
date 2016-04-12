<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Layout - jQuery EasyUI Demo</title>
	<%@ include file="/inc/common.jsp" %>
	<style>
		.logo{ width:200px; height:66px; background: url("${ctx}/img/logo.png") no-repeat; margin-top:5px; margin-left: 10px;}
		.manage_area{width:300px; height:30px; display: block; position: absolute; right:10px; bottom: 0px; text-align: right;}
		.manage_area a{color:#000000; text-decoration: none;}
	</style>
	<script type="text/javascript">  
    $(document).ready(function(){  
        var height1 = $(window).height();  
        $("#main_layout").attr("style","width:100%;height:"+height1+"px");  
        $("#main_layout").layout("resize",{  
            width:"100%",  
            height:height1+"px"  
        });  
        
        
        $('#mainTreeMenu').tree({
            url: "${ctx}/menu/list",
            loadFilter: function(data){
        		if (data.d){
        			return data.d;
        		} else {
        			return data;
        		}
             },
             onClick: function(node){
            	 var url = node.url;
            	 if(url && url != '/'){
            		 if(url.indexOf("http")== -1){
            			 url = "${ctx}/showPage?menuId=" + node.id 
            		 }
         			addTabs(node.text, url, node.iconCls); 
            	 }
         	}
        });
    });  
      
    function modifyPasswd(){
    	openDialog("修改密码", '${ctx}/sysuser/passwordPage', 400, 300 ,'icon-add');
    }
    
    function logout(){
    	$.messager.confirm('提示','是否要退出系统？', function(data){
    		if(data){
    			location.href="${ctx}/logout";
    		}
    	});
    }
      
    $(window).resize(function(){  
        var height1 = $(window).height()-30;  
        $("#main_layout").attr("style","width:100%;height:"+height1+"px");  
        $("#main_layout").layout("resize",{  
            width:"100%",  
            height:height1+"px"  
        });  
    });   
</script>  
</head>
<body>
	<div id="main_layout" class="easyui-layout">
		<div data-options="region:'north',split:true" style="height:80px">
			<div id="logo" class="logo">
			
			</div>
			<div class="manage_area">
				欢迎您：${user.nickName }
				<a href="javascript:void(0)" onclick="modifyPasswd();">修改密码</a>
				<a href="javascript:void(0)" onclick="logout();">退出</a>
			</div>
		</div>
		<div data-options="region:'south',split:true" style="height:30px; text-align: center;">
			海航航空IT事业部日志管理平台&copy;2016
		</div>
		<!-- <div data-options="region:'east',split:true" title="East" style="width:300px;">
		</div> -->
		<div data-options="region:'west',split:true" title="系统菜单" style="width:260px;">
			<div class="easyui-accordion" data-options="border:0" style="width:100%; height:100%;">
				<div title="管理" iconCls="icon-ok" selected="true" style="overflow: auto; padding: 10px;">					
					
					<ul id="mainTreeMenu">
					</ul>
					
				</div>
				<div title="About easyui" iconCls="icon-reload"
					style="padding: 10px;">easyui help you build your web page
					easily</div>
				<div title="Tree Menu">
					<ul id="tt1" class="easyui-tree">
						<li><span>Folder1</span>
							<ul>
								<li><span>Sub Folder 1</span>
									<ul>
										<li><span>File 11</span></li>
										<li><span>File 12</span></li>
										<li><span>File 13</span></li>
									</ul></li>
								<li><span>File 2</span></li>
								<li><span>File 3</span></li>
							</ul></li>
						<li><span>File2</span></li>
					</ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center',border:0">
			
			<div class="easyui-tabs" id="main_tabs" style="width:100%; height:100%;">
				<div title="主页" style="padding:10px">
					<p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
					<ul>
						<li>easyui is a collection of user-interface plugin based on jQuery.</li>
						<li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
						<li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
						<li>complete framework for HTML5 web page.</li>
						<li>easyui save your time and scales while developing your products.</li>
						<li>easyui is very easy but powerful.</li>
					</ul>
				</div>		
			</div>
			
		</div>
	</div>
 
 	<div id="dlg"></div>
</body>
</html>