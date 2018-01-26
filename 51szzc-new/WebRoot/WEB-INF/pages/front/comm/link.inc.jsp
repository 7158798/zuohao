<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<title>
    <c:choose>
        <c:when test="${id==2 }">
            比特币最新信息_数字货币资讯-51数字资产
        </c:when>
        <c:when test="${id==3 }">
            BTC(比特币)_LTC(莱特币)_ETH(以太坊)_zcash(零币)最新资讯
        </c:when>
        <c:when test="${id==4 }">
            BTC(比特币)公告_BTC（比特币）最新信息-51数字资产
        </c:when>
        <c:when test="${id==5 }">
            区块链信息_区块链是什么_区块链资讯-51数字资产
        </c:when>
        <c:when test="${id==6 }">
            51数字资产交易平台
        </c:when>
        <c:otherwise>
            比特币最新信息_数字货币资讯-51数字资产
        </c:otherwise>
    </c:choose>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="
        <c:choose>
            <c:when test="${id==5 }">
                51数字资产专注于区块链资讯。51数字资产正在广泛应用比特币,莱特币,以太坊,小蚁股最新相关信息
            </c:when>
            <c:when test="${id==6 }">
               51数字资产最纯粹的数字资产交易平台，网站信息干净透明、无杠杆无融资融币,不动用用户的任何沉淀资金，同时坚守“透明”“公开”的底线。
            </c:when>
            <c:otherwise>
                51数字资产平台是中国最专业的数字货币交易平台,每天为您提供BTC(比特币),LTC(莱特币),ETH(以太坊),zcash(零币)最新价格和最新信息,为广大爱好者提供最安全、便捷和实时的交易服务
            </c:otherwise>
        </c:choose>
"/>
<meta name="keywords" content="
    <c:choose>
        <c:when test="${id==2 }">
            比特币,数字货币,51数字资产,比特币信息,数字资产

        </c:when>
        <c:when test="${id==3 }">
           BTC,比特币,LTC,莱特币,ETH,以太坊,zcash

        </c:when>
        <c:when test="${id==4 }">
           BTC,比特币,比特币信息,比特币公告,数字资产

        </c:when>
        <c:when test="${id==5 }">
            区块链,区块链是什么,区块链信息,数字资产

        </c:when>
        <c:when test="${id==6 }">
            数字资产交易平台，比特币交易，莱特币交易，数字资产服务，虚拟货币交易

        </c:when>
        <c:otherwise>
            比特币,数字货币,51数字资产,比特币信息,数字资产

        </c:otherwise>
    </c:choose>
" />


<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/header.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>