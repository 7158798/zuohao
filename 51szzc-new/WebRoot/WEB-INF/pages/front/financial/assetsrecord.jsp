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
<%@include file="../comm/link.inc.financial.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/assetsrecord.css" type="text/css"></link>
</head>
<body>

 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea assetsrecord">
					<div class="col-xs-12 rightarea-con">
					
					<ul class="nav nav-tabs rightarea-tabs">
							<li class="active">
								<a href="javascript:void(0)"><spring:message code="lmenu_assets_record"/></a>
							</li>
						</ul>
					
						<div class="col-xs-12 padding-clear padding-top-30">
							<table class="table table-striped text-center">
								<tbody>
								<tr class="bg-gary">
									<td><spring:message code="date"/></td>
									<td><spring:message code="entrutst_type_title"/></td>
									<td><spring:message code="rmb_desc"/>(￥)</td>
									<c:forEach var="v" varStatus="vs" items="${fvirtualcointypes }">
										<td>
											<c:if test="${requestScope.locale_language=='zh_CN'}">${v.fname }</c:if>
											<c:if test="${requestScope.locale_language=='en'}">${v.fShortName }</c:if>
											(${v.fSymbol })</td>
									</c:forEach>
									<td><spring:message code="este_total_amount"/></td>
								</tr>
								
								<c:forEach items="${list }" var="v" varStatus="vs">
									<tr>
										<td>
											<span class="second borderright">
												<fmt:formatDate value="${v.flastupdatetime }" pattern="yyyy-MM-dd"/>
											</span>
										</td>
										<td>
											<span class="borderbottom borderright"><spring:message code="available"/></span>
											<span class="borderright"><spring:message code="frozen"/></span>
										</td>
										<c:forEach var="vv" varStatus="vvs" items="${v.list }">
											<td>
												<span class="borderbottom borderright">
													<fmt:formatNumber value="${vv.value1 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
												</span>
												<span class="borderbottom borderright">
													<fmt:formatNumber value="${vv.value2 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
												</span>
											</td>
										</c:forEach>
										<td>
											<span class="second">
												<fmt:formatNumber value="${v.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
											</span>
										</td>
									</tr>
								
								</c:forEach>
								<c:if test="${fn:length(list) == 0 }">			
									<tr>
										<td colspan="${fn:length(fvirtualcointypes)+4 }" class="no-data-tips">
											<span>
												<spring:message code="no_record"/>
											</span>
										</td>
									</tr>
								</c:if>	
								
								
							</tbody></table>
							
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