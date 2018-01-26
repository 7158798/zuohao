<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/ssadmin/wangEditor/wangEditor.css"/>
<style type="text/css">
	#editor-trigger {
		height: 400px;
		/*max-height: 500px;*/
	}
	.container {
		width: 100%;
		margin: 0 auto;
		position: relative;
	}

	p{
		margin: 0 0 8.5px;
	}

	.course_share{
		font-size: 25px;
		margin-top: 17px;
		margin-bottom: 8.5px;
	}

</style>


<div class="pageContent" style="overflow-y: hidden;">

	<div class="title" style="text-align: center;margin-top: 20px;">
		<p class="p1">${farticle.ftitle}</p>
		<p class="p2" style="font-size: small;">
			<span class="s1">资讯来源：</span>
			<span class="s2">${farticle.forigin}</span>
			<span class="s3" style="margin-left: 26px;">${createDate}</span>
		</p>
	</div>
	<div class="course_share">
		<p style="text-align: center;">
			<img src="${farticle.furl}" alt="51数字资产" />
		</p>
		<p></p>
		<div style="text-align: left;padding-left: 15px;padding-right: 15px;">
			<p>${farticle.fcontent}</p>
		</div>
		</p>
	</div>

</div>

<script>
    $(".dialogContent").css("overflow-y","auto");
</script>



