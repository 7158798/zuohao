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
<%@include file="../comm/link.inc.jsp" %>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
<link href="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/css/plugin/video-js.css" rel="stylesheet">
<link rel="stylesheet" href="${oss_url}/static/front/css/about/addDownLoad.css" type="text/css"></link>
</head>
<body >


<%@include file="../comm/header.jsp" %>

<div class="addDownLoad container-full ${locale_language}">
	<div class="addDownLoad-banner">
		<div class="container">
			<a target="_blank" class="iphone-btn" href="https://fir.im/51szzc?utm_source=fir&utm_medium=qr">
				<img src="${oss_url}/static/front/images/about/addDownLoad/ios_icon_default_${locale_language}.png">
			</a>
			<a target="_blank" class="android-btn" href="/download.html">
				<img src="${oss_url}/static/front/images/about/addDownLoad/android_icon_default_${locale_language}.png">
			</a>
			<img class="addDownLoad-qr" src="${oss_url}/static/front/images/index/qr_downloadApp.png">
			<img class="addDownLoad-phone" src="${oss_url}/static/front/images/about/addDownLoad/banner_1.png">
		</div>
	</div>
	<div class="addDownLoad-steps">
		<div class="addDownLoadTitle text-center">
			<h1>安装步骤</h1>
			<p>Installation Instructions</p>
		</div>
		<div class="iphone-step">
			<div class="container">
				<div class="stepSubTitle">iPhone用户</div>
				<div class="step-navs step-navs--iphone">
					<span class="step-dot step-dot--iphone active">1</span>
					<span class="step-dot step-dot--iphone">2</span>
					<span class="step-dot step-dot--iphone">3</span>
					<span class="step-dot step-dot--iphone">4</span>
					<span class="step-dot step-dot--iphone">5</span>
					<span class="step-dot step-dot--iphone">6</span>
					<span class="step-dot step-dot--iphone">7</span>
					<span class="step-dot step-dot--iphone">8</span>
					<span class="step-dot step-dot--iphone">9</span>
				</div>
				<div class="step-stages step-stages--iphone">
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 1</span>
							<p>扫描开始下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-1.png" class="step-stage-pic">
						<img class="step-stage-qr" src="${oss_url}/static/front/images/index/qr_downloadApp.png">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 2</span>
							<p>点击立即下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-2.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 3</span>
							<p>根据提示，点击右上角...</p>
							<p>选择 "在Safari中打开"</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-3.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 4</span>
							<p>再次点击立即下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-4.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 5</span>
							<p>点击安装</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-5.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 6</span>
							<p>安装结束，打开APP时</p>
							<p>出现图中提示时点"取消"</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-6.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 7</span>
							<p>打开手机[设置]-[通用]</p>
							<p>-[设备管理]</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-7.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 8</span>
							<p>点击[信任Shanghai Zuohao</p>
							<p>Network Technology Co.ltd]</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-8.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 9</span>
							<p>点击[信任]，软件</p>
							<p>安装成功，打开即可正常使用</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/iphone_step-9.png" class="step-stage-pic">
					</div>
				</div>
			</div>
		</div>
		<div class="android-step">
			<div class="container">
				<div class="stepSubTitle">Android</div>
				<div class="step-navs step-navs--android">
					<span class="step-dot step-dot--android active">1</span>
					<span class="step-dot step-dot--android">2</span>
					<span class="step-dot step-dot--android">3</span>
					<span class="step-dot step-dot--android">4</span>
					<span class="step-dot step-dot--android">5</span>
					<span class="step-dot step-dot--android">6</span>
					<span class="step-dot step-dot--android">7</span>
					<span class="step-dot step-dot--android">8</span>
				</div>
				<div class="step-stages step-stages--android">
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 1</span>
							<p>扫描开始下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-1.png" class="step-stage-pic">
						<img class="step-stage-qr" src="${oss_url}/static/front/images/index/qr_downloadApp.png">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 2</span>
							<p>点击立即下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-2.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 3</span>
							<p>根据提示，点击右上角</p>
							<p>选择 "在浏览器中打开"</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-3.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 4</span>
							<p>再次点击立即下载</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-4.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 5</span>
							<p>点击确定</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-5.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 6</span>
							<p>从手机顶部下拉通知栏</p>
							<p>点击安装51数字资产APP安装包</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-6.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 7</span>
							<p>点击安装</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-7.png" class="step-stage-pic">
					</div>
					<div class="step-stage">
						<div class="step-stage-tips">
							<span>STEP 8</span>
							<p>点击[完成]即可打开使用</p>
						</div>
						<img src="${oss_url}/static/front/images/about/addDownLoad/android_step-8.png" class="step-stage-pic">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="addDownLoad-needs">
		<div class="container">
			<div class="addDownLoadTitle text-center">
				<h1>功能涵盖你的交易所需</h1>
				<p>Functions that meeting your needs basically</p>
			</div>
			<div class="addDownLoad-needInfo clearfix">
				<img class="addDownLoad-needPic pull-left" src="${oss_url}/static/front/images/about/addDownLoad/banner_2.png" >
				<div class="addDownLoad-needItems pull-left">
					<div class="addDownLoad-needItem">
						<img class="pull-left" src="${oss_url}/static/front/images/about/addDownLoad/trade_icon_default.png" >
						<h3>交易</h3>
						<p>51数字资产交易系统无缝对接</p>
						<p>实时操盘更自由</p>
					</div>
					<div class="addDownLoad-needItem">
						<img class="pull-left" src="${oss_url}/static/front/images/about/addDownLoad/tra_icon_default.png" >
						<h3>行情</h3>
						<p>即时行情信息一目了然</p>
						<p>紧跟市场动态</p>
					</div>
					<div class="addDownLoad-needItem">
						<img class="pull-left" src="${oss_url}/static/front/images/about/addDownLoad/new_icon_default.png" >
						<h3>资讯</h3>
						<p>汇集行业新鲜资讯</p>
						<p>眼光先人一步</p>
					</div>
					<div class="addDownLoad-needItem">
						<img class="pull-left" src="${oss_url}/static/front/images/about/addDownLoad/fin_icon_default.png" >
						<h3>财务</h3>
						<p>充值、充币、提现、提币....</p>
						<p>集成人民币-数字资产账户财务功能，效率倍增</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../comm/contact.jsp" %>
</div>

<%@include file="../comm/footer.jsp" %>
<script src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/js/plugin/video.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/about/appDownLoad.js"></script>

</body>
</html>