<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp" %> 
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta charset="utf-8">
        <title>日志系统登录</title>
		<meta name="keywords" content="日志系统" />
		<meta name="description" content="日志系统" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel="stylesheet" href="${ctx}/css/reset.css">
        <link rel="stylesheet" href="${ctx}/css/supersized.css">
        <link rel="stylesheet" href="${ctx}/css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
		<script type="text/javascript">
			var message = '${msg}';
			if(message.length > 0){
				alert(message);
			}
		</script>
    </head>

    <body>

        <div class="page-container">
            <h1>登录</h1>
            <form action="${ctx}/login" method="post">
                <input type="text" name="userName" class="username" placeholder="用户名">
                <input type="password" name="password" class="password" placeholder="密码">
                <button type="submit">提交</button>
                <div class="error"><span>+</span></div>
            </form>
            <div class="connect">
            </div>
        </div>
		
        <!-- Javascript -->
        <script src="${ctx}/js/jquery-1.8.2.min.js"></script>
        <script src="${ctx}/js/supersized.3.2.7.min.js"></script>
        <script src="${ctx}/js/supersized-init.js"></script>
        <script src="${ctx}/js/scripts.js"></script>

    </body>

</html>


