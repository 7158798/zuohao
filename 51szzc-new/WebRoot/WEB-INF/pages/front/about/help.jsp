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
<%@include file="../comm/link.inc.help.jsp" %>

<link href="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/css/plugin/video-js.css" rel="stylesheet">
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/about/help.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>
</head>
<body>



<%@include file="../comm/header.jsp" %>

<div class="help container-full">
	<div class="help-banner">
		<div class="container">
			<h1 class="help-issues-title text-center"><spring:message code="help_title"/></h1>
			
			<div class="help-issues">
				<c:forEach items="${objList}" var="v">
				<div class="help-issue clearfix">
					<c:forEach items="${v}" var="vv">
					<div class="help-issue-item col-xs-2">
						<c:forEach items="${vv}" var="item">

						<h3>
							${item.key}
						</h3>
							<ul class="list-unstyled">
							<c:forEach items="${item.value}" var="t">
								<li>
									<a target="_blank" href="/about/index.html?id=${t.key}&type=${t.type}">${t.value}</a>
								</li>
							</c:forEach>
							</ul>
						</c:forEach>
					</div>
					</c:forEach>
				</div>
				</c:forEach>
			</div>
			<a class="help-issues-more " href="/about/index.html?id=0&type=2"><spring:message code="see_more"/></a>
		</div>
	</div>
	<div class="help-class">
		<div class="container">
			<div class="help-classTitle">
				<img src="${oss_url}/static/front/images/about/help/def.png" >
				<spring:message code="video_course"/>
			</div>
			<div class="help-nav-wrap">
				<c:if test="${fvideotypeList.size() > 6}">
					<a class="help-nav-prev" href="javascript:;">
						<img src="${oss_url}/static/front/images/about/help/icon_left_default.png" alt="">
					</a>
					<a class="help-nav-next" href="javascript:;">
						<img src="${oss_url}/static/front/images/about/help/icon_right_default.png" alt="">
					</a>
				</c:if>
				<div class="help-nav">
					<c:forEach items="${fvideotypeList}" var="v">
						<div><span data-id="${v.fid}" class="help-nav-tiem">${v.fName}</span></div>
					</c:forEach>
				</div>
			</div>
			<div class="player-wrap">
				<video id="player" class="video-js vjs-big-play-centered" controls preload="auto" width="1100" height="692"
					 poster="${oss_url}/static/front/images/about/audio_default_img@2x.png" data-setup="{}">
						<source src="" type='video/mp4'> 
						<p class="vjs-no-js">
							To view this video please enable JavaScript, and consider upgrading to a web browser that supports HTML5 video</a>
						</p>
				</video>
				<h1 class="video-title"></h1>
			</div>
			<div class="help-classInfo">
				<spring:message code="course_introduction"/>:
				<span class="help-classDesc"></span>
			</div>
			<div class="help-classList clearfix">
			</div>
		</div>
	</div>
	<div class="szzc-contact">
		<div class="container clearfix text-center">
			<div class="row">
				<div class="szzc-contactItem col-xs-4">
					<img src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/images/about/addDownLoad/se_icon_default.png">
					<h3>热线时间 (9:00-19:00)</h3>
					<p>专业客服用心服务</p>
					<a href="javascript:;">400-900-6615</a>
				</div>
				<div class="szzc-contactItem col-xs-4">
					<img style="margin-top:10px" src="${oss_url}/static/front/images/about/help_contact_weixinimg@2x.png">
					<h3>微信公众号</h3>
					<p>更多内容等你发现</p>
				</div>
				<div class="szzc-contactItem col-xs-4">
					<img src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/images/about/addDownLoad/on_icon_default.png">
					<h3>在线咨询</h3>
					<p>悉心解答你的疑惑</p>
					<a onclick="js_method()" href="javascript:;">点击咨询</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
        (function(m, ei, q, i, a, j, s) {
            m[a] = m[a] || function() {
                    (m[a].a = m[a].a || []).push(arguments)
                };
            j = ei.createElement(q),
                s = ei.getElementsByTagName(q)[0];
            j.async = true;
            j.charset = 'UTF-8';
            j.src = i + '?v=' + new Date().getUTCDate();
            s.parentNode.insertBefore(j, s);
        })(window, document, 'script', '//eco-api.meiqia.com/dist/meiqia.js', '_MEIQIA');
        _MEIQIA('entId', 8913);
        _MEIQIA('withoutBtn', true);

        function js_method() {
            _MEIQIA._SHOWPANEL();
        }

	</script>

<%@include file="../comm/footer.jsp" %>	
<script src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/js/plugin/video.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/about/help.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
