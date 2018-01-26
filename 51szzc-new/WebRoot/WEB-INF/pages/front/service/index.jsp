<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("isIndex", "0");
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
		
	</div>

	<div class="container-full">
		<div class="container service-max">
			<div class="col-xs-9 service-leftbg ">
				<div class="service-newsnav">
					<div class="service-newsnav-title text-center">
						<spring:message code="article_center"/>
						<img src="${oss_url}/static/front/images/index/news_icon_news@2x.png" alt="" class="service-newsnav-icon">
					</div>
					<div>
						<ul>
						<c:forEach items="${articletypes }" var="v">
							<li class="service-newsnav-item list-unstyled text-center ${id==v.fid?'active':''}">
								<a href="/service/ourService.html?id=${v.fid }">${v.fname }</a>
							</li>
						</c:forEach>	
						</ul>

					</div>
				</div>
				<!-- 新闻列表 -->
				<div class="service-newscontent">
					<div class="service-searchBar">
						<input id="keyword" type="text" placeholder="${fill_word}" value="${keyword != null ? keyword : tag}">
						<img src="${oss_url}/static/front/images/index/news_btn_search@2x.png" class="service-searchIcon" alt="search">
					</div>
					<c:forEach items="${farticles }" var="v">
						<div class="snc-max clearfix">
							<div class="col-xs-3 snc-left">

								<a href="${v.url }" target="_blank">

								<img src="${v.furl }" class="snc-newsimg" /></a>

							</div>
							<div class="col-xs-9 snc-right">
								<a href="${v.url }" target="_blank">

								<h3 class="snc-newscontent">
									<c:if test="${v.fisding}">
										<span class="service-tag--red"><spring:message code="recommend"/></span>
									</c:if>
									${v.ftitle }
								</h3>
								</a>
								<p>${v.fcontent_m }</p>
								<div class="col-xs-12 snc-newsinfo">
									<%-- <div class="col-xs-2 sncnc-lookinfo ">
										<p>${v.fadminByFcreateAdmin.fname }</p>
									</div> --%>
									<div class="col-xs-4 ">
										<p><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd HH:mm "/></p>
									</div>
									<div class="col-xs-8 ">
										<c:forEach items="${v.tagList}" var="t">
											<a class="service-tag ${tag == t? 'active' : ''}">${t}</a>
										</c:forEach>
									</div>
									<%-- <div class="col-xs-2 sncnc-lookinfo pull-right">
										<p>
											阅读：<span>${v.fcount }</span>
										</p>
									</div> --%>
								</div>
							</div>
						</div>
					</c:forEach>



					<div class="text-right">${pagin }</div>
				</div>
			</div>
			<!-- 右侧 -->
			<div class="service-slideBar pull-right service-leftbg">
				<div class="service-hot">
					<div class="service-slideBar-title"><spring:message code="24h_top_information"/></div>
					<ul class="list-unstyled">
					<c:forEach items="${hotsArticles}" var="v">
						<li class="service-hotItem">
							<a href="/service/article.html?id=${v.fid}">${v.ftitle}</a>
						</li>
					</c:forEach>
					</ul>
				</div>
				<div class="service-tags">
					<div class="service-slideBar-title"><spring:message code="popular_tags"/></div>
					<div class="service-tagWrap">
					<c:forEach items="${articleTag}" var="v">
						<a class="service-tag ${tag == v? 'active' : ''}">${v}</a>
					</c:forEach>
					</div>
				</div>
				<div class="service-follow">
					<div class="service-slideBar-title"><spring:message code="recom_attention"/></div>
					<div class="text-center">
						<img src="${oss_url}/static/front/images/about/help_contact_qqimg@2x.png" alt="qq">
						<p><spring:message code="proejct_desc1"/>：<br>582589786</p>
					</div>
					<div class="text-center">
						<img src="${oss_url}/static/front/images/index/help_contact_qqimg@2x.png" alt="qq">
						<p><spring:message code="project_desc2"/></p>
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
