<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<div class="szzc-contact">
	<div class="container clearfix text-center">
		<div class="row">
			<div class="szzc-contactItem col-xs-4">
				<img src="${oss_url}/static/front/images/about/addDownLoad/se_icon_default.png" >
				<h3>热线时间 (9:00-19:00)</h3>
				<p>专业客服用心服务</p>
				<a href="javascript:;">400-900-6615</a>
			</div>
			<div class="szzc-contactItem col-xs-4">
				<img src="${oss_url}/static/front/images/about/addDownLoad/group_icon_default.png" >
				<h3>新手交流群</h3>
				<p>理财经验实时分享</p>
				<a href="javascript:;">582589786</a>
			</div>
			<div class="szzc-contactItem col-xs-4">
				<img src="${oss_url}/static/front/images/about/addDownLoad/on_icon_default.png" >
				<h3>在线咨询</h3>
				<p>悉心解答你的疑惑</p>
				<a onclick="js_method()" href="javascript:;">点击咨询</a>
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
