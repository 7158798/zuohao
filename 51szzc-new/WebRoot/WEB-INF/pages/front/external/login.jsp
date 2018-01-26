<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<%@include file="../comm/include.inc.jsp"%>
<title><spring:message code="information_title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="/static/front/css/user/login.css" type="text/css">
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>

</head>
<body class="gray-bg">

	<div class="container-full login-body-full">
		<div class="container ">
			<div class="login js_login">
				<div class="login-body">
					<div class="login-header">
						<span>登录51数字资产</span>
					</div>
					<div class="form-group ">
						<label class="ipt-icon acc" for="login-account"></label>
						<input id="login-account" class="form-control login-ipt" placeholder="请输入登录名" type="text" autocomplete="off">
					</div>
					<div class="form-group ">
						<label class="ipt-icon pas" for="login-password"></label>
						<input id="login-password" class="form-control login-ipt" placeholder="密码" type="password" autocomplete="off">
					</div>
					<div class="form-group ">
							<span id="login-errortips" class="text-danger"></span>
						</div>
					<div class="form-group ">
						<button id="login-submit" class="btn btn-danger btn-block btn-login">登录</button>
					</div>
					
					<div class="login-other">
						<!--<span> <a class="qq" href="/link/qq/call.html">QQ登录</a><i class="split"></i>
						</span>
						<span><a class="weixin" href="javascript:weixin();">微信登录</a> <i class="split"></i></span>-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/article/login.js"></script>
</body>
</html>