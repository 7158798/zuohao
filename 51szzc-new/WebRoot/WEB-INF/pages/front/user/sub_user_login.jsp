<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>
</head>
<body >


<%@include file="../comm/header.jsp" %>


	<div class="container-full login-body-full">
		<div class="container ">
			<div class="login js_login">
				<div class="login-body">
					<div class="login-header">
						<span><spring:message code="signIn"/>${requestScope.constant['webName']}</span>
					</div>
					<div class="form-group ">
						<label class="ipt-icon acc" for="login-account"></label>
						<input id="login-account" class="form-control login-ipt" placeholder="${login_desc1}" type="text" autocomplete="off">
					</div>
					<div class="form-group ">
						<label class="ipt-icon pas" for="login-password"></label>
						<input id="login-password" class="form-control login-ipt" placeholder="${login_pwd}" type="password" autocomplete="off">
					</div>
					<div class="form-group ">
							<span id="login-errortips" class="text-danger"></span>
						</div>
					<div class="login-reset clearfix">
						<div class="checkbox">
						 <!--  <label>
						    <input type="checkbox" checked value="">
						    记住密码
						  </label> -->
						  <a class="pull-right" href="/validate/reset.html"><spring:message code="forgot_password"/></a>
						</div>
					</div>
					<div class="form-group ">
						<button id="login-submit" class="btn btn-danger btn-block btn-login"><spring:message code="signIn"/></button>
					</div>
					
					<div class="login-other">
						<!--<span> <a class="qq" href="/link/qq/call.html">QQ登录</a><i class="split"></i>
						</span>
						<span><a class="weixin" href="javascript:weixin();">微信登录</a> <i class="split"></i></span>-->
						<span><spring:message code="login_desc2"/>？<a href="user/register.html"><spring:message code="to_regist"/>&gt;&gt;</a></span>
					</div>
				</div>
			</div>
			<div class="google_login login">
				<div class="login-body">
					<div class="login-header">
						<span><spring:message code="signIn"/>${requestScope.constant['webName']}</span>
					</div>
					<div class="form-group google_code">
						<label class="ipt-icon pas" for="google_password"></label>
						<input id="google_password" class="form-control login-ipt" placeholder="${comm_error_tips_59}" type="text" maxlength="6" onkeyup="this.value=this.value.replace(/\D/gi,'')">
					</div>
					<div class="form-group " style="height: 20px;margin-top: -20px;">
						<span id="google_err2" class="text-danger"></span>
					</div>
					<div class="form-group ">
						<button id="to_sign" class="btn btn-danger btn-block btn-login"><spring:message code="signIn"/></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input id="forwardUrl" type="hidden" value="${forwardUrl }">


<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/user/login.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
