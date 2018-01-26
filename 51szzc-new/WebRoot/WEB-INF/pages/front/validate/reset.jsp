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
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/user/reset.css" type="text/css"></link>
</head>
<body>
	




 


<%@include file="../comm/header.jsp" %>


	<div class="container-full ">
		<div class="container reset">
			<div class="col-xs-12 ">
				<p class="choose-title"><spring:message code="choose_retrieve"/></p>
			</div>
			<div class="col-xs-12 reset padding-top-30">
				<div class="col-xs-5 col-xs-offset-1 text-center">
					<span class="choose-body">
						<span class="choose-icon choose-icon-email"></span>
						<h3><spring:message code="retrieve_via_email"/></h3>
						<h4><spring:message code="login_email_retr_pwd"/></h4>
						<a href="validate/resetEmail.html" class="btn btn-danger btn-find"><spring:message code="click_here"/></a>
					</span>
				</div>
				<div class="col-xs-5  text-center">
					<span class="choose-body">
						<span class="choose-icon choose-icon-phone"></span>
						<h3 class=""><spring:message code="retrieve_via_phone"/></h3>
						<h4><spring:message code="need_phone_code"/></h4>
						<a href="validate/resetPhone.html" class="btn btn-danger btn-find"><spring:message code="click_here"/></a>
					</span>
				</div>
			</div>
		</div>
	</div>

	<!-- 安全问题验证 -->
	<div  class="modal modal-custom fade" id="questionreComfime" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title">安全问题验证</span>
				</div>
				<div class="modal-body form-horizontal questionWrap">
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2">
							请回答如下安全问题:
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2 text-center">
							<input id="randomQuestion" type="text" class="form-control" readonly>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2">
							请输入该问题答案:
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2 text-center">
							<input id="randomQuestionAns" type="text" class="form-control">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-8 col-xs-offset-2 text-center">
							<p class="text-danger" id="randomQuestion-errortips"></p>
							<a class="form-control btn btn-danger questionComfime-btn">确认提交</a>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<%@include file="../comm/footer.jsp" %>	
	
	<script>
		
	</script>
</body>
</html>
