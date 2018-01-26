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

</head>
<body>


<c:choose>
    <c:when test="${trade_status == 'TRADE_SUCCESS'}">
        <spring:message code="pay_title1"/>
    </c:when>
    <c:otherwise>
        支付失败，支付状态是：${trade_status}
    </c:otherwise>
</c:choose>


</body>
</html>
