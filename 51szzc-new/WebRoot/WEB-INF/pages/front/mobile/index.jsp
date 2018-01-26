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
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/mobile/index.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>

</head>
<body >


<div class="mIndex">
	<div class="mIndex-banner">
		<div>
			<img class="mIndex-bannerPic" src="${oss_url}/static/front/images/mobile/index/banner-1.png" alt="banner">
		</div>
		<div>
			<img class="mIndex-bannerPic" src="${oss_url}/static/front/images/mobile/index/banner-2.png" alt="banner">
		</div>
		<div>
			<img class="mIndex-bannerPic" src="${oss_url}/static/front/images/mobile/index/banner-3.png" alt="banner">
		</div>
	</div>
	<div class="mIndex-data register-before container-fluid">
		<div class="mobile-title">
			<span class="mobile-title-left"></span>
			<span class="mobile-title-text">最新行情概况</span>
			<span class="mobile-title-right"></span>
		</div>
		<div class="mIndex-datas">
			
		</div>

	</div>
	<div class="inviteM-success">
		<h3>
			<img src="${oss_url}/static/front/images/mobile/index/success_icon_default.png" alt="success">
			恭喜您，注册成功啦！
		</h3>
		<p>您的注册手机号码为：</p>
		<span class="inviteM-success-phone"></span>
	</div>
	<div class="inviteM-down">
		<div class="mIndex-phone">
			<div class="mobile-title">
				<span class="mobile-title-left"></span>
				<span class="mobile-title-text">APP下载体验</span>
				<span class="mobile-title-right"></span>
			</div>
		</div>
		<div class="text-center">
			<a target="_blank" href="https://fir.im/51szzc?utm_source=fir&utm_medium=qr" class="mIndex-downBtn">
				<img src="${oss_url}/static/front/images/mobile/index/iOS_icon_default.png" alt="success">
			</a>
			<a target="_blank" href="https://o709clriv.qnssl.com/51szzc.apk" class="mIndex-downBtn">
				<img src="${oss_url}/static/front/images/mobile/index/android_icon_default.png" alt="success">
			</a>
		</div>
		<div class="mIndex-step">
			<div class="mobile-title">
				<span class="mobile-title-left"></span>
				<span class="mobile-title-text">软件体验流程</span>
				<span class="mobile-title-right"></span>
			</div>
			<div class="mobile-iPhone">
				<div class="mIndex-stepTitle">
					IOS用户
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-left">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/ios_step1_image.png" alt="step">
					</div>
					<div class="pull-right mIndex-stepText">
						<p><span>STEP 1</span></p>
						<p>
							打开APP出现图中提示，<br>
							点击取消，返回手机[设置]
						</p>
					</div>
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-right">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/ios_step2_image.png" alt="step">
					</div>
					<div class="pull-left mIndex-stepText">
						<p class="text-right"><span>STEP 2</span></p>
						<p class="text-right">
							打开 [设置] - [通用]<br>
							- [设备管理]
						</p>
					</div>
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-left">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/ios_step3_image.png" alt="step">
					</div>
					<div class="pull-right mIndex-stepText">
						<p><span>STEP 3</span></p>
						<p>
							点击[信任Shanghai Zuohao<br>
							Network Technology Co.ltd]
						</p>
					</div>
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-right">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/ios_step2_image.png" alt="step">
					</div>
					<div class="pull-left mIndex-stepText">
						<p class="text-right"><span>STEP 4</span></p>
						<p class="text-right">
							点击 [信任] 便可<br>
							完成安装.
						</p>
					</div>
				</div>
			</div>
			<div class="mobile-android">
				<div class="mIndex-stepTitle">
					安卓用户
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-left">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/android_step1_image.png" alt="step">
					</div>
					<div class="pull-right mIndex-stepText">
						<p><span>STEP 1</span></p>
						<p>
							从手机顶部下拉通知栏<br>
							点击安装51数字资产APP安装包
						</p>
					</div>
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-right">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/android_step2_image.png" alt="step">
					</div>
					<div class="pull-left mIndex-stepText">
						<p class="text-right"><span>STEP 2</span></p>
						<p class="text-right">
							点击 [安装]<br>
						</p>
					</div>
				</div>
				<div class="mIndex-stepItem clearfix">
					<div class="pull-left">
						<img class="mIndex-stepPic" src="${oss_url}/static/front/images/mobile/index/android_step3_image.png" alt="step">
					</div>
					<div class="pull-right mIndex-stepText">
						<p><span>STEP 3</span></p>
						<p>
							点击 [完成] 即可打开使用<br>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="mIndex-reg register-before">
		<div class="mobile-title">
			<span class="mobile-title-left"></span>
			<span class="mobile-title-text">1步轻松注册</span>
			<span class="mobile-title-right"></span>
		</div>
		<form>
			<input id="register-areaCode" type="hidden" value="86">
			<div class="form-group">
				<input id="register-phone" class="form-control" type="text" placeholder="请输入手机号码" maxlength="11" minlength="11">
			</div>
			<div class="form-group">
				<input id="register-imgcode" autocapitalize="off" class="form-control form-control--small" type="text" placeholder="请输入验证码">
				<img class="btn btn-imgcode register-imgmsg" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
			</div>
			<div class="form-group">
				<input id="register-msgcode" class="form-control form-control--small" type="text" placeholder="请输入短信验证码">
				<button type="button" id="register-sendmessage" data-msgtype="12" data-tipsid="register-errortips" class="btn">获取验证码</button>
			</div>
			<div class="form-group">
				<input id="register-password" class="form-control" type="password" placeholder="请设置登录密码">
			</div>
			<div class="register-confime form-group checkbox text-center">
				<label>
					<input id="agree" checked type="checkbox">
					我已阅读并同意
					<a target="_blank" href="/about/index.html?id=4" class="text-default">《${requestScope.constant['webName']}用户协议》</a>
				</label>
			</div>
			<div class="form-group text-center">
				<span id="register-errortips" class="text-danger"></span>
			</div>
			<div class="">
				<button type="button" id="register-submit" class="btn btn-danger btn-block btn-login">注册</button>
			</div>
		</form>
	</div>
	<div class="mIndex-advantages clearfix">
		<div class="mobile-title">
			<span class="mobile-title-left"></span>
			<span class="mobile-title-text">平台6大优势</span>
			<span class="mobile-title-right"></span>
		</div>
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
				买卖数字货币手续费低于<br/>
				同行费率
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
	<div class="inviteM-tips text-center">
		<div>
			<img src="${oss_url}/static/front/images/mobile/index/m_icon_selected.png" >
			<a  href="/?requestType=1">
				<img src="${oss_url}/static/front/images/mobile/index/pc_icon_default.png" >
			</a>
			<p>服务热线: 400-900-6615</p>
		</div>
	</div>
	<div class="inviteM-footer">
	</div>
</div>
 


<input id="regType" type="hidden" value="0">
<input id="intro_user" type="hidden" value="${introId}">

<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/language.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/mobile/index.js?r=<%=new java.util.Date().getTime() %>"></script>


</body>
</html>