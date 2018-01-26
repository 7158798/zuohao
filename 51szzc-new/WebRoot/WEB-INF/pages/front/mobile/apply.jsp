<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    request.setAttribute("isIndex", "1");
%>

<!doctype html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${requestScope.constant['webinfo'].ftitle }</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
    <meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
    <link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/apply/apply.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>

</head>
<body >
<%--头部图片--%>
<div class="active-top">
    <a href="#" class="active-to">
        <img src="${oss_url}/static/front/images/mobile/e-active/banner.png" alt="banner">
    </a>
</div>
<%--表单--%>
<div class="ap-fo" id="ap-fo">
    <form action="" class="ap-detail" method="post" id="form1">
        <div class="ap-con">
            <label class="ap-text">学校</label>
            <input type="text" id="school" name="school" class="ap-val">
        </div>
        <div class="ap-con">
            <label class="ap-text">姓名</label>
            <input type="text" id="name" name="name" class="ap-val" >
        </div>
        <div class="ap-con">
            <label class="ap-text">联系方式</label>
            <input type="text" id="telephone" name="telephone" class="ap-val">
        </div>
        <div class="ap-con">
            <label class="ap-text">院系</label>
            <input type="text" id="department" name="department" class="ap-val">
        </div>
        <div class="ap-con">
            <label class="ap-text">专业</label>
            <input type="text" id="major" name="major" class="ap-val">
        </div>
        <div class="ap-con">
            <label class="ap-text">年级</label>
            <input type="text" id="grade" name="grade" class="ap-val">
            <%--<div class="text-down"  style="float: left;width:57%;">--%>
                <%--<input type="text" id="grade" name="grade" class="ap-value" style="display:inline-block;width:100%;">--%>
                <%--<div class="down-toggle">--%>
                    <%--<div class="num1">大一</div>--%>
                    <%--<div class="num1">大二</div>--%>
                    <%--<div class="num1">大三</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<input type="button" id="sel" value="请选择" class="ap-sel" onclick="selYear()">--%>
        </div>



        <div class="ap-send">
            <div class="send-top">
                <label class="top-pic">本人照片</label>
                <input type="button" value="重新上传"  class="top-bt">
                <span class="top-send"></span>
            </div>
            <div class="format">
                <label class="for-left"></label>
                <span class="for-right">*支持png/jpg格式,大小控制在5MB以内</span>
            </div>
           <%-- <div id="showIdv">
                <img src="" id="img1" style="width:80%;margin-left:20%;"/>
            </div>--%>
            <div class="send-center" id="pic-con">
                <label for="file"  class="center-add"></label>
                <input type="hidden" value="fileName" id="fileName"/>
                <input type="file"  id="file" accept="image/png, image/jpg"  onchange="uploadImg()" style="display: none;" >
            </div>


            <div class="send-top" style="padding-bottom: 0px;">
                <label class="top-pic">文稿</label>
                <span class="for-right">请将作品发送至邮箱:</span>
            </div>
            <div class="send-top" style="padding-top:0px;">
                <label class="top-pic"></label>
                <span class="for-right" style="color:#55a1fb">hu.yunqian@51szzc.com;</span>
            </div>
            <div class="send-top" style="padding-top:0px;">
                <label class="top-pic"></label>
                <span class="for-right">文档支持word/pdf格式;</span>
            </div>
            <div class="send-top" style="padding-top:0px;">
                <label class="top-pic"></label>
                <span class="for-right">文档请以"<span style="color:#55a1fb">姓名+学号+手机号码</span>"方式命名.</span>
            </div>
        </div>
        <div class="ac-contribute">
            <input type="button" id="sub" name="sub" class="ac-click" value="提交">
        </div>
    </form>
</div>

<%--提交成功--%>
<div class="sub-suc" id="sub-suc">
    <div class="sub-pic">
        <img src="${oss_url}/static/front/images/mobile/e-active/icon(1).png">
    </div>
    <div class="sub-ok">提交成功</div>
    <div class="sub-con">
        <div>请将作品发送至邮箱: <span style="color:#55a1fb;">hu.yunqian@51szzc.com</span></div>
        <div>文档支持word/pdf格式;</div>
        <div>文档请以"<span style="color:#55a1fb">姓名+学号+手机号码</span>"方式命名.</div>
    </div>
</div>

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/language.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>

<script type="text/javascript" src="${oss_url}/static/front/js/mobile/apply.js?r=<%=new java.util.Date().getTime() %>"></script>


</body>
</html>