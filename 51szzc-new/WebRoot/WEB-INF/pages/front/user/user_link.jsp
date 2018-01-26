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
<title>比特币交易_数字货币交易平台-51数字资产
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="51数字资产交易中心,为您提供比特币、莱特币、以太坊、zcash等现货交易,快速买卖,数字货币交易策略以及融资融币

"/>
<meta name="keywords" content="比特币交易,比特币交易平台,数字货币教育平台,数字资产,数字货币交易
" />


<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/header.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/jquery.fullPage.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.fullPage.min.js"></script>

<script>
	$(document).ready(function() {
	    $('#user_link').fullpage({
	    	navigation : true,
	    	navigationColor: 'red',
	    	lazyLoading : true,

	    });
	});
</script>

<style type="text/css">
	#allheader {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		background: #000;
	}
	.user_link {
		position: relative;
	}
	.section {
		position: relative;
	}
	.section-banner {
		height: 100%;
		background: no-repeat center center;
		background-size: cover;
		overflow: hidden;
	}
	.section:nth-child(1) .section-banner {
		background-image: url('${oss_url}/static/front/images/user/Trading_center_banner01_@2X.jpg');
	}
	.section:nth-child(2) .section-banner {
		background-image: url('${oss_url}/static/front/images/user/Trading_center_banner02_@2X.jpg');
	}
	.section:nth-child(3) .section-banner {
		background-image: url('${oss_url}/static/front/images/user/Trading_center_banner03_@2X.jpg');
	}
	.section:nth-child(4) .section-banner {
		background: url('${oss_url}/static/front/images/user/Trading_center_banner04_@2X.jpg') no-repeat center top;
		background-size: auto 100%;
	}

	
	.user_link_trade_img {
		display: block;
		margin: 10% auto 0;
		width: 50%;
	}
	.user_link_safe_img {
		position: absolute;
		top: 40%;
		right: 0;
		width: 50%;
	}
	.user_link_items {
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex;
		-webkit-justify-content: space-around;
		    -ms-flex-pack: distribute;
		        justify-content: space-around;
		-webkit-align-items: center;
		    -ms-flex-align: center;
		        align-items: center;
		height: 100%;
	}
	.user_link_items img{
		width: 25%;
	}
	#fp-nav {
		top: 60%;
	}
	#fp-nav ul li a span {
		background: #b3b3b3;
		width: 14px;
		height: 14px;
		border: none;
	}
	#fp-nav ul li a.active span {
		background: #fff;
	}
	#allFooter {
		position: absolute !important;
		width: 100%;
		bottom: 0;
	}
	.user-link-navs {
		position: absolute;
		right: 60px;
		bottom: 120px;
		height: 60px;
		line-height: 60px;
		z-index: 100;
	}
	.user-link-navs a {
		color: #fff;
		margin-right: 20px;
		font-size: 12px;
	}
	.user-link-navs a:hover {
		text-decoration: none;
	}
	.user-link-navs a:last-child {
		text-decoration: underline;
	}
	.user-link-navs a:hover {
		color: #fff;
	}
	.user-link-navs img {
		height: 40px;
	}
	.user_link_help_img {
		position: absolute;
		top: 0;
		left: 50%;
		width: 40%;
		transform: translateX(-50%);
	}


	
</style>

</head>
<body class="${locale_language}">


<%@include file="../comm/header.jsp" %>
 
<div class="user-link-navs">
	<a href="/user/login.html">
		<img class="user_link_login_img" src="${oss_url}/static/front/images/user/login_button_${locale_language}.png" alt="safe">
	</a>
	<a href="/about/guide.html"><spring:message code="regise_guide"/></a>
</div>
<div id="user_link" class="${locale_language}">	
    <div class="section">
    	<div class="section-banner">
    		<img class="user_link_safe_img" src="${oss_url}/static/front/images/user/Banner_01_documents_${locale_language}.png" alt="safe">
    	</div>
    </div>
    <div class="section">
    	<div class="section-banner">
    		<div class="user_link_items">
    			<img src="${oss_url}/static/front/images/user/Banner_02_documents_01_${locale_language}.png">
    			<img src="${oss_url}/static/front/images/user/Banner_02_documents_02_${locale_language}.png">
    			<img src="${oss_url}/static/front/images/user/Banner_02_documents_03_${locale_language}.png">
    		</div>
    	</div>
    </div>
    <div class="section">
    	<div class="section-banner">
			<img class="user_link_trade_img" src="${oss_url}/static/front/images/user/Banner_03_documents_02_${locale_language}.png">
			
    	</div>
    </div>
     <div class="section">
    	<div class="section-banner">
    		<img class="user_link_help_img" src="${oss_url}/static/front/images/user/user_link_help_img_${locale_language}.png">
			<%@include file="../comm/footer.jsp" %>	
    	</div>
    </div>
</div>




	


</body>
</html>