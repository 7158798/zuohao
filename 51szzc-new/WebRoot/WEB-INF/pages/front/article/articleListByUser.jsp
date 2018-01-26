<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<%@include file="../comm/include.inc.jsp"%>
<title><spring:message code="information_title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="/static/front/css/article/article.css" type="text/css">
<link rel="stylesheet" href="/static/front/css/article/wangEditor.css" type="text/css">
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>

</head>
<body class="gray-bg">

<div class="wrapper">
	<div id="account-sectionHeaderDiv" class="account-sectionHeader clearfix">
		<div class="account-sectionHeader-item pull-left">已发布资讯: <span class="account-sectionHeader-keyWord">${articleList.size()}</span>篇</div>
		<a class="account-sectionHeader-btn account-sectionHeader-btn--green pull-right" href="/external/user/initAddArticle.html">发布资讯</a>
	</div>
	<table class="account-table table">
		<thead>
			<tr id="dratfID">
				<th>序号</th>
				<th>标题</th>
				<th>来源</th>
				<th>最新修改时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleList}" var="v" varStatus="vs">
				<tr>
					<td>${vs.index + 1}</td>
					<td>${v.ftitle}</td>
					<td>${v.forigin}</td>
					<td><fmt:formatDate value="${v.flastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${v.fstatus_s}</td>
					<td> 
						<c:if test="${v.fstatus == 0}">
							<a href="/external/user/initAddArticle.html?id=${v.fid}" class="text-danger">编辑</a>
						</c:if>
						<c:if test="${v.fstatus == 2}">
							<a target="_blank" href="/service/article.html?id=${v.fid}" class="text-success">查看</a>
						</c:if>
						<c:if test="${v.fstatus == 4}">
							<a target="_blank" href="/service/article.html?id=${v.fid}" class="text-success">查看</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="text-right" style="padding-right:30px">${pagin}</div>
	<div class="pageContainer_tiezi diff_nav"></div>
	<div class="pageContainer_gong diff_nav"></div>
	<div class="pageContainer_cao diff_nav"></div>
</div>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/article/login.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/article/wangEditor.js"></script>
</body>
</html>