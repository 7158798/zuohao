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
<%@include file="../comm/link.inc.financial.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css" type="text/css"></link>
<style>
    body {
        background: rgb(0, 74, 124);
    }
    .megWrap {
        margin: 40px auto;
        padding: 30px;
        width: 500px;
        text-align: center;
        font-size: 14px;
        background: #fff;
    }
    .megWrap h3 {
        font-size: 18px;
    }
</style>

</head>
<body>


 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
        <c:if test="${code == 0}">
            <div class="megWrap megWrap--success">
                <img src="${oss_url}/static/front/images/Shape2.png" alt="success">
                <h3>成功！</h3>
                <p>提现申请已成功提交，请耐心等待到账</p>
            </div>
        </c:if>
        <c:if test="${code == -1}">
            <div class="megWrap megWrap--fail">
                <img src="${oss_url}/static/front/images/Shape.png" alt="fail">
                <p>${msg}</p>
            </div>
        </c:if>
	</div>


			

 
<%@include file="../comm/footer.jsp" %>	
</body>
</html>
