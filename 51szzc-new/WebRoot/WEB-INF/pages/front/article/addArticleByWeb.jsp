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
<link rel="stylesheet" href="/static/front/css/plugin/wangEditor.css" type="text/css">
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>

</head>
<body class="gray-bg">
 <div class="account-publishBar">
    <div class="wrapper">
        
        <div class="account-publish-title">
            <label>标题:</label>
            <input placeholder="请输入标题" id="title" type="text" class="" maxlength="30">
        </div>
        <div id="editor-trigger">
        </div>
        <form class="form-horizontal">
            <div class="form-group" style="margin-top:20px">
                <label for="origin" class="col-sm-2 control-label">来源</label>
                <div class="col-sm-10">
                    <input class="form-control" type="text" id="origin" >
                </div>
            </div>
            <div class="form-group">
                <label for="type" class="col-sm-2 control-label">类型</label>
                <div class="col-sm-10">
                    <select name="type" id="type" class="form-control">
                        <c:forEach items="${articleTypeList}" var="v">
                            <option value="${v.fid}">${v.fname}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
        <div class="clearfix">
            <div class="account-publish-thumbnail pull-left">
                <label for="headfile">点击上传缩略图</label>
                <input type="file" id="headfile">
            </div>
            <div class="account-publish-keyword pull-right clearfix">
                <input placeholder="请选择标签" id="keyword" type="text" disabled="" class="form-control pull-left">
                <button class="btn pull-right open-keywordBar">选择</button>
            </div>
        </div>
        
        <div class="account-publish-btns text-right">
            <button id="publishId" class="btn account-publist-btn">发布</button>
            <button id="draftId" class="btn btn-default">存为草稿</button>
        </div>
        <div class="account-publish-keywordBar">
            <h4>选择关键词 <span class="close-keywordBar pull-right">x</span></h4>
            <div class="account-keyword-wrap">
            </div>
            <p class="text-center">
                <button id="sureSelected" class="btn btn-primary">确认</button>
            </p>
        </div>
    </div>
</div>

    <input type="hidden" id="fid" value="">

	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/article/login.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/wangEditor.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/article/edit.js"></script>
       
</body>
</html>