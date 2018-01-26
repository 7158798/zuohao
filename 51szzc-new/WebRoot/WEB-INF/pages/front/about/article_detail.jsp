

<!doctype html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GB2312" %>

<html >
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <title>资讯详情</title>
    <link rel="shortcut icon" href="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/favicon.ico"/>
    <style>
        body,html ,div,p{
            margin: 0;
            padding: 0;
        }
        body{
            background: #fafafa;
        }
        .course_share {
            position: relative;
            background-size: cover;
            padding:0 10px 30px;
        }
        img {
            max-width: 80%;
        }
    </style>

<body>

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
        <img src="${farticle.furl}" alt="51数字资产" >
    </p>
    <p></p>
    <div style="text-align: left;">
    <p>${farticle.fcontent}</p>
    </div>
    </p>
</div>

</body>
</html>