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
<link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
</head>
<body >


<%@include file="../comm/header.jsp" %>

<div class="container-full login-body-full">
		<div class="container ">
			<div class="col-xs-12 padding-top-30">
				<div class="col-xs-5 login register padding-left-clear padding-right-clear">
					<div class="login-body">
						
						<div class="form-group login-header">
							<span class="register-tab active" data-show="register-phone"
								data-hide="register-email" data-type="0"><i
								class="register-icon icon-phone"></i>手机注册<i class="split"></i>
							</span> <span class="register-tab" data-show="register-email"
								data-hide="register-phone" data-type="1"><i
								class="register-icon icon-email"></i>邮箱注册</span>
						</div>

						<div class="form-group register-phone">
							<select class="form-control login-ipt" id="register-areaCode">
								<option value="86">中国大陆(China)</option>
							</select>
						</div>
						<div class="form-group register-phone">
							<span id="register-phone-areacode" class="btn btn-areacode register-areacode">+86</span>
							<input id="register-phone" class="form-control login-ipt padding-left-92" type="text" autocomplete="off" placeholder="手机号码">
						</div>
						<div class="form-group register-email">
						<label class="ipt-icon pas" for="register-imgcode"></label>
							<input id="register-email" class="form-control login-ipt" type="text" autocomplete="off" placeholder="邮箱地址">
						</div>
						<div class="form-group ">
						<label class="ipt-icon code" for="register-imgcode"></label>
							<input id="register-imgcode" class="form-control login-ipt" type="text" autocomplete="off" placeholder="验证码">
							<img class="btn btn-imgcode register-imgmsg" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
						</div>
						<div class="form-group register-phone">
						<label class="ipt-icon code" for="register-imgcode"></label>
							<input id="register-msgcode" class="form-control login-ipt" type="text" autocomplete="off" placeholder="短信验证码">
							<button id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips" class="btn btn-sendmsg register-msg">发送验证码</button>
						</div>						
						<div class="form-group register-email">
						<label class="ipt-icon code" for="register-imgcode"></label>
							<input id="register-email-code" class="form-control login-ipt" type="text" autocomplete="off" placeholder="邮箱验证码">
							<button id="register-sendemail" data-msgtype="3" data-tipsid="register-errortips" class="btn btn-sendemailcode register-msg">发送验证码</button>
						</div>
						<div class="form-group ">
						<label class="ipt-icon pas" for="register-imgcode"></label>
							<input id="register-password" class="form-control login-ipt" type="password" autocomplete="off" placeholder="密码">
						</div>
						<div class="form-group ">
						<label class="ipt-icon pas" for="register-imgcode"></label>
							<input id="register-confirmpassword" class="form-control login-ipt" type="password" autocomplete="off" placeholder="确认密码">
						</div>
						<div class="form-group ">
						<label class="ipt-icon code" for="register-imgcode"></label>
							<input id="register-intro" class="form-control login-ipt" type="text" autocomplete="off" value="${intro }" placeholder="邀请码(${requestScope.constant['isMustIntrol']==1?'必填':'选填'})">
						</div>
						<div class="checkbox">
							<label>
								<input id="agree" type="checkbox">
								阅读并同意
								<a target="_blank" href="/about/index.html?id=4" class="text-danger">《${requestScope.constant['webName']}用户协议》</a>
							</label>
						</div>
						<div class="form-group ">
							<span id="register-errortips" class="text-danger"></span>
						</div>
						<div class="form-group ">
							<button id="register-submit" class="btn btn-danger btn-block btn-login">注册</button>
						</div>
						<div class="form-group">
							<span class="text-white">已有帐号？</span>
							<a href="/user/login.html" class="text-danger">直接登录>></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	


<%@include file="../comm/footer.jsp" %>	

	<input id="regType" type="hidden" value="0">
	<input id="intro_user" type="hidden" value="${intro }">
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/register.js"></script>
</body>
</html>