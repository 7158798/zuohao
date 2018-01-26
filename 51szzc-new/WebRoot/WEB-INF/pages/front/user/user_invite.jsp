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

<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
</head>
<body class="${locale_language}">
	


<%@include file="../comm/header.jsp" %>

 

	<div class="container-full">
		<div class="container displayFlex">
		

<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user userInvite">
					<div class="userInvite-banner row">
						<span>+ ${inviteIntegral}<spring:message code="integral"/></span>
						<p>
							<spring:message code="invite_desc1"/>${inviteIntegral}<spring:message code="invite_desc2"/><br/>
							<spring:message code="invite_desc3"/><br/>
							<spring:message code="invite_desc4"/>！
						</p>
					</div>
					<div class="userInvite-link">
						<p class="paneltitle">
							<spring:message code="active_link"/>
						</p>
						<div>
							<spring:message code="copy_invite"/>
							<input id="link" type="text" readonly value="${spreadLink}">
							<button class="userInvite-linkCopy btn btn-danger" data-clipboard-target="#link"><spring:message code="copy_link"/></button>
							<span class="userInvite-linktips"><spring:message code="copy_success"/></span>
						</div>
					</div>
					<div class="userInvite-info">
						<p class="paneltitle">
							<spring:message code="invite_desc5"/>
						</p>
						<div class="userInvite-data clearfix">
							<div class="userInvite-dataMember">
								<p class="text-primary"><spring:message code="verif_user_num"/></p>
								<p>${realAccount}<spring:message code="person"/></p>
							</div>
							<div class="userInvite-dataPoint">
								<p class="text-primary"><spring:message code="integral_get"/></p>
								<p>${allIntegral}<spring:message code="fraction"/></p>
							</div>
						</div>
						<table class="userInvite-list table table-bordered text-center">
							<thead>
								<tr>
									<th class="text-center"><spring:message code="time"/></th>
									<th class="text-center"><spring:message code="invite_friend"/></th>
									<th class="text-center"><spring:message code="lmenu_real_certification"/></th>
									<th class="text-center"><spring:message code="integral_get"/></th>
								</tr>
							</thead>
							<tbody>

							<c:forEach items="${fusers}" var="v" varStatus="vs">
								<tr>
									<td>${v.createTime}</td>
									<td>${v.logName}</td>
									<td>${v.isRealValid}</td>
									<td>${v.integral}</td>
								</tr>
							</c:forEach>
							<c:if test="${fusers.size() == 0}">
								<tr>
									<td colspan="4"><spring:message code="no_invite"/></td>
								</tr>
							</c:if>
							</tbody>
						</table>
						<div style="padding-right:80px" class="text-right">
							${pagin}	
						</div>			
					</div>
					<div class="userInvite-rules">
						<p class="paneltitle"><spring:message code="active_desc"/></p>
						<div class="userInvite-ruleWrap">
							<p class="userInvite-rule"><spring:message code="invite_desc6"/>${inviteIntegral}<spring:message code="invite_desc7"/></p>
							<p class="userInvite-rule"><spring:message code="invite_desc8"/></p>
							<p class="userInvite-rule"><spring:message code="invite_desc9"/></p>
							<p class="userInvite-rule"><spring:message code="invite_desc10"/></p>
							<p class="userInvite-rule"><spring:message code="invite_desc11"/></p>
							<p class="userInvite-rule"><spring:message code="invite_desc12"/></p>
							<div class="userInvite-ruleTips">+ ${inviteIntegral}<spring:message code="integral"/></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	

	<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />
	<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
	<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
	<input id="messageType" type="hidden" value="0" />
	




<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/clipboard.min.js"></script>
	
	<script>
		var clipboard = new Clipboard('.userInvite-linkCopy');
		clipboard.on('success', function(e) {
		   	$('.userInvite-linktips').show();
		    e.clearSelection();
		});

	</script>
</body>
</html>
