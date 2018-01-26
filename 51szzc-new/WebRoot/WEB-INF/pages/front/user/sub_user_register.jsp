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
				<div class="login register ">
					<div class="login-body">
						
						<div class="login-header">
							<span class="register-tab active" data-show="register-phone"
								data-hide="register-email" data-type="0"><spring:message code="phone_regist"/>
							</span> <span class="register-tab" data-show="register-email"
								data-hide="register-phone" data-type="1"><spring:message code="email_regist"/></span>
						</div>

						<div class="form-group register-phone">
							<span class="register-form-type"><spring:message code="area"/></span>
							<select class="form-control login-ipt" id="register-areaCode">
								<option value="86">中国大陆(China)</option>
							</select>
						</div>
						<div class="form-group register-phone">
							<span class="register-form-type"><spring:message code="phone"/></span>
							<input id="register-phone" class="form-control login-ipt padding-left-92" type="text" autocomplete="off" placeholder="${comm_error_tips_6}">
							<span id="register-error-phone" class="register-error"></span>
						</div>
						<div class="form-group register-email">
							<span class="register-form-type"><spring:message code="email_desc1"/></span>
							<input id="register-email" class="form-control login-ipt" type="text" autocomplete="off" placeholder="${email_address}">
							<span id="register-error-email" class="register-error "></span>
						</div>
						<div class="form-group ">
							<span class="register-form-type"><spring:message code="ver_code"/></span>
							<input id="register-imgcode" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="${ver_code}">
							<img class="btn btn-imgcode register-imgmsg" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
							<span id="register-error-imgmsg" class="register-error "></span>
						</div>
						<div class="form-group register-phone">
							<span class="register-form-type"><spring:message code="sms_ver_code"/></span>
							<input maxlength="6" id="register-msgcode" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="${sms_ver_code}">
							<button id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips" class="btn btn-sendmsg register-msg"><spring:message code="send_ver_code"/></button>
							<span id="register-error-msg" class="register-error"></span>
						</div>						
						<div class="form-group register-email">
							<span class="register-form-type"><spring:message code="email_ver_code"/></span>
							<input id="register-email-code" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="${email_ver_code}">
							<button id="register-sendemail" data-msgtype="3" data-tipsid="register-errortips" class="btn btn-sendemailcode btn-sendmsg register-msg"><spring:message code="get_ver_code"/></button>
							<span id="register-error-emailcode" class="register-error "></span>
						</div>
						<div class="form-group ">
							<span class="register-form-type"><spring:message code="set_pwd"/></span>
							<input id="register-password" class="form-control login-ipt" type="password" autocomplete="off" placeholder="${pwd_desc1}">
							<a class="register-pwdStatus"></a>
							<span id="register-error-password" class="register-error "></span>
						</div>
						<!-- <div class="form-group ">
						<label class="ipt-icon pas" for="register-imgcode"></label>
							<input id="register-confirmpassword" class="form-control login-ipt" type="password" autocomplete="off" placeholder="确认密码">
						</div>-->
						<div class="form-group " style="display: none;">
						  <label class="ipt-icon code" for="register-imgcode"></label>
							<input id="register-intro" class="form-control login-ipt" type="text" autocomplete="off" value="${intro }" placeholder="${invitation_code}(${requestScope.constant['isMustIntrol']==1?'${required_desc}':'${optional_desc}'})">
						</div>

						<div class="register-confime checkbox">
							<label>
								<input id="agree" checked type="checkbox">
								<spring:message code="read_agree"/>
								<a target="_blank" href="/about/index.html?id=4" class="text-danger">《${requestScope.constant['webName']}<spring:message code="user_agreement"/>》</a>
							</label>
						</div>
						<div class="form-group text-center">
							<span id="register-errortips" class="text-danger"></span>
						</div>
						<div class="form-group ">
							<button id="register-submit" class="btn btn-danger btn-block btn-login"><spring:message code="regist"/></button>
						</div>
						<div class="login-other">
							<spring:message code="account_already"/>
							<a href="/user/login.html" class="text-danger"><spring:message code="to_login"/>&gt;&gt;</a>
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