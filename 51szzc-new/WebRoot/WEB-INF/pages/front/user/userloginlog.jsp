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
<%@include file="../comm/link.inc.user.jsp" %>

</head>
<body>
	

 
<%@include file="../comm/header.jsp" %>




	<div class="container-full">
		<div class="container displayFlex">
			
			<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="active">
								<a href="javascript:void(0)"><spring:message code="login_record"/></a>
							</li>
							<li>
								<a href="/user/userloginlog.html?type=2"><spring:message code="safety_set_record"/></a>
							</li>
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped">
								<tr class="bg-gary">
									<td class="col-xs-2 text-center"><spring:message code="login_time"/></td>
									<td class="col-xs-8 text-center"><spring:message code="login_address"/></td>
									<td class="col-xs-2 text-center"><spring:message code="login_ip"/></td>
								</tr>
								
								<c:forEach items="${logs }" var="v">
									<tr>
										<td class="text-center">
											<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td class="text-center">${v.address }</td>
										<td class="text-center">${v.fkey3 }</td>
									</tr>
								</c:forEach>
								
								<c:if test="${fn:length(logs)==0 }">
								<tr>
										<td colspan="2" class="no-data-tips">
											<span> <spring:message code="no_records"/> </span>
										</td>
									</tr>
								</c:if>	
							</table>
							
								<div class="text-right">
									${pagin }
								</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	


<%@include file="../comm/footer.jsp" %>	


</body>
</html>