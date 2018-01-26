<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<title>
    <c:if test="${symbol == 1}">
        BTC(比特币)交易_BTC(比特币)今日价格-51数字资产资讯
    </c:if>
    <c:if test="${symbol == 2}">
        LTC(莱特币)交易_LTC(莱特币)今日价格-51数字资产资讯
    </c:if>
    <c:if test="${symbol == 3}">
        ETH(以太坊)交易_ETH(以太坊)今日价格-51数字资产资讯
    </c:if>
    <c:if test="${symbol == 4}">
        小蚁股交易_小蚁股今日价格-51数字资产资讯
    </c:if>
    <c:if test="${symbol == 5}">
        zcash(零币)交易_zcash(零币)今日价格-51数字资产资讯
    </c:if>
    <c:if test="${symbol == 6}">
        ETC(以太币)交易_ETC(以太币)今日价格-51数字资产资讯
    </c:if>
</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="
    <c:if test="${symbol == 1}">
        51数字资产平台是中国最专业的BTC(比特币)交易平台,每天为您提供BTC(比特币)最新价格和最新信息,为广大BTC爱好者提供最安全、便捷和实时的交易服务
    </c:if>
    <c:if test="${symbol == 2}">
        51数字资产平台是中国最专业的LTC(莱特币)交易平台,每天为您提供LTC(莱特币)最新价格和最新信息,为广大LTC爱好者提供最安全、便捷和实时的交易服务
    </c:if>
    <c:if test="${symbol == 3}">
        51数字资产平台是中国最专业的ETH(以太坊)交易平台,每天为您提供ETH(以太坊)最新价格和最新信息,为广大ETH爱好者提供最安全、便捷和实时的交易服务
    </c:if>
    <c:if test="${symbol == 4}">
        51数字资产平台是中国最专业的小蚁股交易平台,每天为您提供小蚁股最新价格和最新信息,为广大小蚁股爱好者提供最安全、便捷和实时的交易服务
    </c:if>
    <c:if test="${symbol == 5}">
        51数字资产平台是中国最专业的zcash(零币)交易平台,每天为您提供zcash(零币)最新价格和最新信息,为广大zcash爱好者提供最安全、便捷和实时的交易服务
    </c:if>
    <c:if test="${symbol == 6}">
        51数字资产平台是中国最专业的ETC(以太币)交易平台,每天为您提供ETC(以太币)最新价格和最新信息,为广大ETC爱好者提供最安全、便捷和实时的交易服务
    </c:if>
"/>
<meta name="keywords" content="
    <c:if test="${symbol == 1}">
        BTC,比特币,BTC交易,BTC交易平台,比特币今日价格

    </c:if>
    <c:if test="${symbol == 2}">
       LTC,莱特币,LTC交易,LTC交易平台,莱特币今日价格

    </c:if>
    <c:if test="${symbol == 3}">
       ETH,以太坊,ETH交易,ETH交易平台,以太坊今日价格

    </c:if>
    <c:if test="${symbol == 4}">
       小蚁股,小蚁股交易,小蚁股交易平台,小蚁股今日价格

    </c:if>
    <c:if test="${symbol == 5}">
        zcash,零币,zcash交易,zcash交易平台,零币今日价格

    </c:if>
     <c:if test="${symbol == 5}">
       ETC,以太币,ETC交易,ETC交易平台,以太币今日价格

    </c:if>
" />


<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/comm/header.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>