<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("isIndex", "1");
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${requestScope.constant['webinfo'].ftitle }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="email=no" />
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/header.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/invite/invite.css" type="text/css"></link>

</head>
<body >


<div class="container-fluid inviteM">
	<div class="inviteM-banner row"></div>
	<div class="inviteM-register row">
		<h3 class="inviteM-title">立即注册</h3>
		<div class="login register ">
			<div class="login-body">
				
				<div class="form-group register-phone">
					<span class="register-form-type">地&nbsp;区</span>
					<select class="form-control login-ipt" id="register-areaCode">
						<option value="86">中国大陆(China)</option>
					</select>
				</div>
				<div class="form-group register-phone">
					<span class="register-form-type">手机号</span>
					<input id="register-phone" class="form-control login-ipt padding-left-92" type="text" autocomplete="off" placeholder="请输入手机号码">
					<span id="register-error-phone" class="register-error"></span>
				</div>
				<div class="form-group register-email">
					<span class="register-form-type">邮&nbsp;箱</span>
					<input id="register-email" class="form-control login-ipt" type="text" autocomplete="off" placeholder="邮箱地址">
					<span id="register-error-email" class="register-error "></span>
				</div>
				<div class="form-group ">
					<span class="register-form-type">验证码</span>
					<input id="register-imgcode" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="验证码">
					<img class="btn btn-imgcode register-imgmsg" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
					<span id="register-error-imgmsg" class="register-error "></span>
				</div>
				<div class="form-group register-phone">
					<span class="register-form-type">短信验证码</span>
					<input maxlength="6" id="register-msgcode" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="短信验证码">
					<button id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips" class="btn btn-sendmsg register-msg">发送验证码</button>
					<span id="register-error-msg" class="register-error"></span>
				</div>						
				<div class="form-group register-email">
					<span class="register-form-type">邮箱验证码</span>
					<input id="register-email-code" class="form-control login-ipt login-ipt--small" type="text" autocomplete="off" placeholder="邮箱验证码">
					<button id="register-sendemail" data-msgtype="3" data-tipsid="register-errortips" class="btn btn-sendemailcode btn-sendmsg register-msg">获取验证码</button>
					<span id="register-error-emailcode" class="register-error "></span>
				</div>
				<div class="form-group ">
					<span class="register-form-type">设置密码</span>
					<input id="register-password" class="form-control login-ipt" type="password" autocomplete="off" placeholder="8~12个字符，数字和字母的组合">
					<a class="register-pwdStatus"></a>
					<span id="register-error-password" class="register-error "></span>
				</div>
				<!-- <div class="form-group ">
				<label class="ipt-icon pas" for="register-imgcode"></label>
					<input id="register-confirmpassword" class="form-control login-ipt" type="password" autocomplete="off" placeholder="确认密码">
				</div>-->
				<div class="form-group " style="display: none;">
				  <label class="ipt-icon code" for="register-imgcode"></label>
					<input id="register-intro" class="form-control login-ipt" type="text" autocomplete="off" value="${intro }" placeholder="邀请码(${requestScope.constant['isMustIntrol']==1?'必填':'选填'})">
				</div>

				<div class="register-confime checkbox">
					<label>
						<input id="agree" checked type="checkbox">
						我已阅读并同意
						<a target="_blank" href="/about/index.html?id=4" class="text-danger">《${requestScope.constant['webName']}用户协议》</a>
					</label>
				</div>
				<div class="form-group text-center">
					<span id="register-errortips" class="text-danger"></span>
				</div>
				<div class="form-group ">
					<button id="register-submit" class="btn btn-danger btn-block btn-login">注册</button>
				</div>
				<div class="login-other">
					已有帐号？
					<a href="/user/login.html" class="text-danger">直接登录>></a>
				</div>
			</div>
		</div>
		<div class="inviteM-success text-center">
			<h3>
				<img class="inviteM-success-right" src="${oss_url}/static/front/images/invite/m/m_success_icon_default@3x.png" alt="success"/>
				恭喜您，注册成功了！
			</h3>
			<p>
				您的注册手机号码为：<br/>
				<span class="inviteM-success-phone"></span>
			</p>
			<img class="inviteM-success-icon " src="${oss_url}/static/front/images/invite/m/m_success_icon@3x.png">
		</div>
	</div>
	<div class="inviteM-advantages row">
		<h3 class="inviteM-title">平台优势</h3>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/safe_icon_default@3x.png" alt="advantage">
			<h3>安全</h3>
			<p>
				十多年金融风险<br/>
				经验的专业团队
			</p>
		</div>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/kekao_icon_default@3x.png" alt="advantage">
			<h3>可靠</h3>
			<p>
				专业级高速撮合引擎与分布<br/>
				式集群技术分布全球数据中心
			</p>
		</div>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/user_icon_default@3x.png" alt="advantage">
			<h3>用户至上</h3>
			<p>
				7*24小时客服服务<br/>
			</p>
		</div>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/cost_icon_default@3x.png" alt="advantage">
			<h3>超低手续费</h3>
			<p>
				买卖数字货币手续费超低<br/>
			</p>
		</div>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/fast_icon_default@3x.png" alt="advantage">
			<h3>快速到账</h3>
			<p>
				充值提现快速到账<br/>
				资金快速流转
			</p>
		</div>
		<div class="col-xs-6 inviteM-advantage">
			<img src="${oss_url}/static/front/images/invite/m/expressive-_icon_default@3x.png" alt="advantage">
			<h3>提现提币</h3>
			<p>
				遵循合法合规的原则上<br/>
				赢得市场
			</p>
		</div>
	</div>
	<div class="inviteM-down">
		<div class="pull-left">
			<img class="pull-left" src="${oss_url}/static/front/images/invite/m/logo@3x.png" alt="logo">
			<h3>51数字资产APP</h3>
			<p>贴心交易管家</p>
		</div>
		<a target="_blank" class="inviteM-downBtn btn pull-right" href="/download.html">下载APP</a>
	</div>
	<div class="inviteM-footer row"></div>
</div>
 


<input id="regType" type="hidden" value="0">
<input id="intro_user" type="hidden" value="${introId}">

<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/invite/invite_m.js"></script>

</body>
</html>