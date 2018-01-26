<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("isIndex", "2");
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/invite/invite.css" type="text/css"></link>

<script>
	var introId = ${introId};
	var url = window.location.href;
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {

		if(introId) {

			window.location = '/invite/invite.html?' + 'r=' + ${introId} + '&type=m';
		} else {
			window.location = '/invite/invite.html' + '?type=m';
		}
	}
</script>
</head>
<body class="${locale_language}">


<%@include file="../comm/header.jsp" %>
 
<div class="container-full invite">
	<div class="invite-banner">
		<div class="login register ">
			<div class="login-body">
				
				<div class="login-header">
					<span class="register-tab active" data-show="register-phone"
						data-hide="register-email" data-type="0">手机注册
					</span> <span class="register-tab" data-show="register-email"
						data-hide="register-phone" data-type="1">邮箱注册</span>
				</div>

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
	</div>
	<div class="invite-trade">
		<h3 class="invite-tradeTitle text-center">交易行情</h3>
		<table class="invite-currency table table-bordered">
			<thead>
				<tr>
					<th>币种</th>
					<th>最低价</th>
					<th>最高价</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${trapmaps}" var="v">
				<tr>
					<td class="col-xs-4">
						<img src="${v.url}" alt="currency">
						<span>${v.vName}</span>
					</td>
					<td class="col-xs-4">￥${v.lowPrice}</td>
					<td class="col-xs-4">￥${v.hiPrice}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="invite-advantages">
		<div class="invite-title">
			<h1>我们的优势</h1>
			<p>五大理由 选择我们</p>
		</div>
		<div class="container claerfix text-center">
			<div class="invite-advantageItem">
				<img src="${oss_url}/static/front/images/invite/invite1.png" alt="advantage">
				<h3>安全</h3>
				<p>
					纯粹的交易平台、十多年
					<br>
					金融风控经验的安全团队
				</p>
			</div>
			<div class="invite-advantageItem">
				<img src="${oss_url}/static/front/images/invite/invite2.png" alt="advantage">
				<h3>可靠</h3>
				<p>
					专业级高速撮合引擎与分布
					<br>
					式集群技术分布全球数据中心
				</p>
			</div>
			<div class="invite-advantageItem">
				<img src="${oss_url}/static/front/images/invite/invite3.png" alt="advantage">
				<h3>用户至上</h3>
				<p>
					7*24小时客服服务
					<br>
				</p>
			</div>
			<div class="invite-advantageItem">
				<img src="${oss_url}/static/front/images/invite/invite4.png" alt="advantage">
				<h3>超低手续费</h3>
				<p>
					买卖数字货币手续费超低
					<br>
				</p>
			</div>
			<div class="invite-advantageItem">
				<img src="${oss_url}/static/front/images/invite/invite5.png" alt="advantage">
				<h3>快速到账</h3>
				<p>
					充值提现快速到账
					<br>
					资金快速流转
				</p>
			</div>
		</div>
	</div>

	<div class="invite-rule">
		<div class="invite-title">
			<h1>活动规则</h1>
		</div>
		<div class="invite-ruleInner">
			<p>1.好友通过此邀请链接成功注册并实名认证，您即可获得${inviteIntegral}个积分（邀请好友无上限，积分奖励无上限）；</p>
			<p>2.好友注册获得丰厚的任务积分奖励，即新手积分奖励；</p>
			<p>3.积分可用于提升用户等级，等级越高将享受越多的手续费减免优惠政策；</p>
			<p>4.本活动公平公正，真实有效，如存在任何恶意刷奖等违规行为，一经查实，所有奖励不予承兑；</p>
			<p>5.积分不可提现；</p>
			<p>6.本活动最终解释权归51数字资产所有。</p>
			<div class="invite-point">+${inviteIntegral}积分</div>
		</div>
	</div>
</div>

<input id="regType" type="hidden" value="0">
<input id="intro_user" type="hidden" value="${introId}">

<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/register.js"></script>

</body>
</html>