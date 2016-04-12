<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script
<div class="login">
	<div class="logo"></div> 
</div>
<form id="loginFrm" class="form form-horizontal" action="${ctx}/login" method="post">
<div class="login-content">
  <div class="login-pic"> 
  	<div class="login-box">
  	<div class="login-title">请您登录</div>
    <div class="login-input">
    	<ul>
        	<li><input  type="text" name="userName" id="uName" placeholder="手机号"></li>
            <li><input  type="password" name="password" id="uPasswd" placeholder="密码"></li>
        </ul>
    </div>
    <c:if test="${isReg eq '1' }">
	    <div class="login-btn" onclick="submitFrm()">登录</div>
	    <div class="regist-btn" onclick="showLogin()">注册</div>
    </c:if>
    <c:if test="${isReg ne '1' }">
    	<div class="login1-btn" onclick="submitFrm()">登录</div>
    </c:if>
  </div>
</div>
</div>
</form>


<div id="dlg" style="display: none;">
	<form id="regForm" name="regForm">
        <table>
        	<tr>
        		<td>手 机 号：</td>
        		<td><input name="telephone" id="telephone" type="text" maxlength="20"/></td>
        	</tr>
        	<tr>
        		<td>用户姓名：</td>
        		<td><input name="nickName" id="nickName" type="text" maxlength="20"/></td>
        	</tr>
        	<tr>
        		<td>登录密码：</td>
        		<td><input name="password" id="password1" type="password" maxlength="20"/></td>
        	</tr>
        	<tr>
        		<td>确认密码：</td>
        		<td><input name="password2" id="password2" type="password" maxlength="20"/></td>
        	</tr>
        	<tr>
        		<td>部门名称：</td>
        		<td><input name="department" type="text" id="department" maxlength="20"/></td>
        	</tr>
        </table>
    </form>
</div>

