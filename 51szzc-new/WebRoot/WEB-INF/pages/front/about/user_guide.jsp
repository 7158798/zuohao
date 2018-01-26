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
<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
<link href="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/css/plugin/video-js.css" rel="stylesheet">
</head>
<body class="${locale_language}">


<%@include file="../comm/header.jsp" %>
 
<div class="container-full userGuide-banner">
	<a class="userGuide-learnBtn" href="/about/guide.html#userGuideMedia"></a>
</div>
<div name="userGuideMedia" id="userGuideMedia" class="container-full userGuide-media">
	<div class="userGuide-inner">	
		<img class="userGuide-title1" src="${oss_url}/static/front/images/user/guide_title_video@2x_${locale_language}.png" alt="vedio">
		<div class="userGuide-videos">
			<span class="videos-prev"></span>
			<span class="videos-next"></span>
			<div class="slider center">
				<div>
					<div data-src="http://hy51szzc.oss-cn-shanghai.aliyuncs.com/video/01_dig_coin.mp4" class="userGuide-video">
						<img class="userGuide-video-post" src="${oss_url}/static/front/images/about/audio_list_01@2x.png" alt="poster">
					</div>
				</div>
				<div>
					<div data-src="http://hy51szzc.oss-cn-shanghai.aliyuncs.com/video/02_register.mp4" class="userGuide-video">
						<img class="userGuide-video-post" src="${oss_url}/static/front/images/about/audio_list_02@2x.png" alt="poster">
					</div>
				</div>
				<div>
					<div data-src="http://hy51szzc.oss-cn-shanghai.aliyuncs.com/video/03_deal.mp4" class="userGuide-video">
						<img class="userGuide-video-post" src="${oss_url}/static/front/images/about/audio_list_03@2x.png" alt="poster">
					</div>
				</div>
				<div>
					<div data-src="http://hy51szzc.oss-cn-shanghai.aliyuncs.com/video/04_what_bit.mp4" class="userGuide-video">
						<img class="userGuide-video-post" src="${oss_url}/static/front/images/about/audio_list_04@2x.png" alt="poster">
					</div>
				</div>
			</div>
		</div>
		<div class="userGuide-videoNavs">
			<span class="userGuide-videoNav active" href="javascript:;" data-href="https://www.51szzc.com/about/index.html?id=73">什么是数字货币</span>
			<span class="userGuide-videoNav" href="javascript:;" data-href="https://www.51szzc.com/about/index.html?id=61">如何手机注册</span>
			<span class="userGuide-videoNav" href="javascript:;" data-href="https://www.51szzc.com/about/index.html?id=44">如何回款充值</span>
			<span class="userGuide-videoNav" href="javascript:;" data-href="https://www.51szzc.com/about/index.html?id=72">什么是比特币</span>
		</div>
		<a class="userGuide-textBtn" href="https://www.51szzc.com/about/index.html?id=73">我要看文字教程</a>
	</div>
</div>


<div class="container-full userGuide-group">
	<div class="userGuide-inner">
		<img class="userGuide-title2" src="${oss_url}/static/front/images/user/guide_title_communication@2x_${locale_language}.png" alt="group">
		<div class="clearfix">
			<div class="userGuide-groupleft">
				<img src="${oss_url}/static/front/images/user/guide_qq_img@2x_${locale_language}.png" alt="group">
			</div>
			<div class="userGuide-groupright">
				<img src="${oss_url}/static/front/images/user/guide_weixin_img@2x.png" alt="g roup">
				<p>扫码关注51数字资产微信公众号<br/>更多内容等你发现</p>
			</div>
		</div>
	</div>
</div>

<div  class="modal  fade" id="videoDialog" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document" style="width: 800px;height: 450px">

		<div class="modal-content">
			<div class="">
				
			</div>
			<div class="modal-body form-horizontal">
				<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
					<span style="font-size:30px" aria-hidden="true">&times;</span>
				</button>
				<video id="video" class="video-js vjs-big-play-centered" controls preload="auto" width="740" height="417.5"
				 poster="${oss_url}/static/front/images/about/audio_default_img@2x.png" data-setup="{}">
					<source src="" type='video/mp4'> 
					<p class="vjs-no-js">
						To view this video please enable JavaScript, and consider upgrading to a web browser that supports HTML5 video</a>
					</p>
				</video>
				
			</div>
		</div>
	</div>
</div>

<%@include file="../comm/footer.jsp" %>
<script src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/js/plugin/video.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/user.guide.js"></script>

</body>
</html>