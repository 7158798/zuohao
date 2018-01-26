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
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/about.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>
<link href="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/css/plugin/video-js.css" rel="stylesheet">
</head>
<body>



<%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container about">
			<div class="col-xs-12 padding-top-30">
				<div class="col-xs-2 padding-left-clear">
					<span class="title"><spring:message code="help_type"/></span>
					<div class="panel-group" role="tablist">
						<!-- 视频课程 start -->
							<!-- <div class="panel panel-default">
								<div class="panel-heading" role="tab" id="collapseListGroupHeading2">
									<h4 class="panel-title">
										<span id="icon500" class="item-icon">${type == 2 ?'-':'+'}</span>
										<a class="collapsed" role="button" data-toggle="collapse" href="#collapse500" aria-expanded="${type == 2?'true':'false'}">视频课程</a>
									</h4>
								</div>
								<div id="collapse500" data-icon="icon2"
									 class="panel-collapse collapse ${type == 2?'in':''}" role="tabpanel">
									<ul class="list-group">
										<c:forEach items="${fvideotypeList }" var="videoType">
											<li class="list-group-item ${videoTypeFid==videoType.fid?'active':'' }">
												<span class="item-icon"> </span>
												<a href="/about/index.html?id=${videoType.fid }&type=2">${videoType.fName }</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div> -->
						<!-- 视频课程 end -->
						<c:forEach items="${fhelptypeList}" var="v" varStatus="vs">
							<div class="panel panel-default">
								<div class="panel-heading ${type == v.fid?'active':''} ${v.fhelp_index.size() == 0 ? 'no-item' : ''}" role="tab" id="collapseListGroupHeading1" data-toggle="collapse" href="#collapse${vs.index}" aria-expanded="${type == v.fid?'true':'false'}">
									<h4 class="panel-title">
										<span id="icon${vs.index}" class="item-icon"></span>
										<a class="collapsed" role="button" > ${v.fname } </a>
									</h4>
								</div>
								<div id="collapse${vs.index}" data-icon="icon${vs.index}"
								 class="panel-collapse collapse ${type == v.fid ?'in':''}" role="tabpanel">
									<ul class="list-group">
										<c:forEach items="${v.fhelp_index}" var="vv">
											<li class="list-group-item ${id == vv.fid?'active':'' }">
												
												<a href="/about/index.html?id=${vv.fid }&type=${v.fid}">${vv.ftitle }</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="col-xs-10 padding-bottom-50">
					<div class="col-xs-12 bg-white split">
						<h4>${fhelp.ftitle}</h4>
					</div>
					<div class="col-xs-12 bg-white">
					${fhelp.fcontent}
					</div>
				</div>
			</div>
		</div>
	</div>
	

 <div class="about-contact">
	<div class="container clearfix text-center">
		<div class="row">
			<div class="col-xs-4 col-xs-offset-2">
				<img src="${oss_url}/static/front/images/about/qq_icon.png">
				<h3>新手交流群</h3>
				<p>582589786</p>
			</div>
			<div class="col-xs-4">
				<img src="${oss_url}/static/front/images/about/help_contact_weixinimg@2x.png">
				<h3>微信公众号</h3>
				<p>更多内容等你发现</p>
			</div>
		</div>
	</div>
</div>
<%@include file="../comm/footer.jsp" %>	
<script src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/js/plugin/video.js"></script>

<script type="text/javascript" src="${oss_url}/static/front/js/about/index.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
