<!doctype html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GB2312" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no minimal-ui">
        <title>
            51数字资产App下载
        </title>
        <script type="text/javascript">
            //app下载地址
            var downloadUrl = "https://o709clriv.qnssl.com/51szzc.apk";
            //ios设备跳转页面
            var iosUrl = "https://fir.im/51szzc?utm_source=fir&utm_medium=qr"; 
            FIR = {};
            var ua=navigator.userAgent.toLowerCase();
            FIR.isIOS = false;
            FIR.isMobile = ua.indexOf('mobile')>0;
            FIR.isWeixin = (/micromessenger/.test(ua)) ? true : false ;
            FIR.isQQ = (/qq\//.test(ua)) ? true : false ;
            FIR.isIOS = ua.indexOf('(ip')>0 && FIR.isMobile;
            download();
            function download(){
            if(FIR.isWeixin || FIR.isQQ){
                return;
            }
            else if(FIR.isIOS){
                window.location = iosUrl;
                return;
            }
            else{
                window.location = downloadUrl;
                return;
            }
            }
        </script>
        <style type="text/css">
            body,html {
                background-color: #fffbf1;
                color: #black;
                margin: 0;
                padding: 0;
                text-align: center;
            }

            h1 {
                font-weight: 300;
                margin: 0;
                padding: 0;
            }

            p {
                line-height: 20px;
                margin: 0;
                padding: 0;
                font-family:"微软雅黑", "黑体";
            }

            .tips {
                position: relative;
                z-index: 4;
            }

            .tips .content {
                background-color: #c3beb1;
                border-radius:10px;
                box-sizing: border-box;
                color: #015366;
                font-size: 12px;
                letter-spacing: 0.6px;
                margin: 10 10;
                padding: 24px 0;
                text-align: center;
            }

            .tips .content img {
                vertical-align: middle;
                margin-left: 10px;
            }

            .baseinfo {
                background-color: #fffbf1;
                padding-top: 60px;
                width: 100%;
                padding-bottom: 60px;
            }

            .baseinfo h1 {
                font-size: 20px;
                margin-top: 40px;
            }

            .icon {
                background-image: url('https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/images/index/downLoad_logo.png');
                background-position: center center;
                background-repeat: no-repeat;
                background-size: 160px 200px;
                width: 160px;
                height: 200px;
            }

            .img img {
                width: 100%;
            }
        </style>
    </head>
    <body>
        <div class="tips">
            <div class="content">
                <p>
                    <span>点击右上角菜单，在浏览器中打开并安装</span><img src="https://hy51szzc.oss-cn-shanghai.aliyuncs.com/static/front/images/index/jiantou.png" width="24">
                </p>
            </div>
        </div>
        <div class="baseinfo">
            <div class="title">
                <div style="display:inline-block;">
                    <div class="icon" id="icon"></div>
                </div>
            </div>
        </div>
    </body>
</html>
