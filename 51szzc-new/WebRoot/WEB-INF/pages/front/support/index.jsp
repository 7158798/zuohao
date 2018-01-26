<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("isIndex", "1");
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>

<link rel="stylesheet" href="${oss_url}/static/front/css/support/index.css"
	type="text/css"></link>
</head>
<body>

	<%@include file="../comm/header.jsp"%>



<div class="support">
	<div class="support-banner">
		<img class="support-bannerInner" src="${oss_url}/static/front/images/support/top_banner_text.png" alt="fintech">
	</div>
	<div class="support-strengths">
		<img class="support-strengthsInner" src="${oss_url}/static/front/images/support/product_img.png" alt="strengths">
	</div>
	<div class="support-scene">
		<img class="support-sceneInner" src="${oss_url}/static/front/images/support/scenarios_img.png" alt="strengths">
	</div>
	<div class="support-links">
		<div class="support-tabNavs">
			<div class="container">
				<span><spring:message code="support_tabTitle"/></span>
				<a class="support-tabNav active" href="javascript:;"><spring:message code="support_tab1"/></a>
				<a class="support-tabNav" href="javascript:;"><spring:message code="support_tab2"/></a>
				<a class="support-tabNav" href="javascript:;"><spring:message code="support_tab3"/></a>
				<a class="support-tabNav" href="javascript:;"><spring:message code="support_tab4"/></a>
			</div>
		</div>
		<div class="support-tabItems">
			<img class="support-tabItem active support-tabItem1" src="${oss_url}/static/front/images/support/img_01_p2p@2x.png">
			<img class="support-tabItem support-tabItem2" src="${oss_url}/static/front/images/support/img_02_commodity@2x.png">
			<img class="support-tabItem support-tabItem3" src="${oss_url}/static/front/images/support/img_03_mortgage@2x.png">
			<img class="support-tabItem support-tabItem4" src="${oss_url}/static/front/images/support/img_04_user_kyc@2x.png">
		</div>
	</div>
	<div class="support-footer">
		<img class="support-footerInner" src="${oss_url}/static/front/images/support/bottom_nav_img.png">
	</div>

</div>	



	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/support/index.js"></script>
</body>
</html>
