<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>

<link rel="stylesheet" href="${oss_url}/static/front/css/service/service.css"
	type="text/css"></link>
</head>
<body>




	<%@include file="../comm/header.jsp"%>





	<div class="container-full">

		<div class="container service-max">

			<div class="col-xs-9 article-leftbg ">
				<div class="cols-xs-12 text-left">
					<p class="article-title">
						<a href="/">首页</a>&gt;<a
							href="/service/ourService.html?id=${farticle.farticletype.fid }">${farticle.farticletype.fname
							}&gt;</a><span>详情</span>
					</p>
				</div>
				<div class="cols-xs-12">
					<h2 class="text-center ">${farticle.ftitle }</h2>
				</div>
				<div class="cols-xs-12 text-right article-info text-center">
					<span style="color: gray;">发布于 ${farticle.fcreateDate }</span>
					<c:if test="${farticle.forigin != null}">
					<span style="color: gray;">来源 ${farticle.forigin}</span>
					</c:if>
				</div>
				<div class="cols-xs-12  article-content">${farticle.fcontent }
				<c:if test="${farticle.tagList.size() != null}">
					<div class="article-tags row">
					标签: 
					<c:forEach items="${farticle.tagList}" var="t">
						<a class="service-tag">${t}</a>
					</c:forEach>
					</div>
				</c:if>
				</div>
			</div>

			<!-- 右侧 -->
			<div class="service-slideBar pull-right ">
				<div class="service-searchBar">
						<input id="keyword" type="text" placeholder="请输入关键词搜索">	
						<img src="${oss_url}/static/front/images/index/news_btn_search@2x.png" class="service-searchIcon" alt="search">
					</div>
				<div class="service-hot">
					<div class="service-slideBar-title">24H 热门资讯</div>
					<ul class="list-unstyled">
						<c:forEach items="${hotsArticles}" var="v">
							<li class="service-hotItem">
								<a href="/service/article.html?id=${v.fid}">${v.ftitle}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="service-tags">
					<div class="service-slideBar-title">热门标签</div>
					<div class="service-tagWrap">
						<c:forEach items="${articleTag}" var="v">
							<a class="service-tag">${v}</a>
						</c:forEach>
					</div>
				</div>
				<div class="service-follow">
					<div class="service-slideBar-title">推荐关注</div>
					<div class="text-center">
						<img src="${oss_url}/static/front/images/about/help_contact_qqimg@2x.png" alt="qq">			
						<p>
							51数字资产新手交流群：
							<br>582589786</p>
					</div>
					<div class="text-center">
						<img src="${oss_url}/static/front/images/index/help_contact_qqimg@2x.png" alt="qq">			
						<p>
							关注51数字资产公众号
							<br>更多内容等你发现</p>
					</div>
				</div>
			</div>
		</div>

		
	</div>








	<%@include file="../comm/footer.jsp"%>

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
		<script type="text/javascript" src="${oss_url}/static/front/js/service/service.js"></script>
</body>
</html>
