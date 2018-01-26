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


	<div class="container-full">
		<div class="container reset">
			<div class="col-xs-12 ">
				<span class="reset-process">
					<span id="resetprocess1" class="col-xs-3 col-xs-offset-3 active">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">1</span>
						<span class="reset-process-text"><spring:message code="fill_account"/></span>
					</span>
					<span id="resetprocess2" class="col-xs-3 ">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">2</span>
						<span class="reset-process-text"><spring:message code="set_login_pwd"/></span>
					</span>
					<span id="resetprocess3" class="col-xs-3">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">3</span>
						<span class="reset-process-text"><spring:message code="success"/></span>
					</span>
				</span>
			</div>
			<div class="col-xs-12 reset padding-top-30">
				<div class="col-xs-8 col-xs-offset-2 reset-body">
					<div class="col-xs-12">
						<span class="reset-title">
							<spring:message code="you_are_passing"/>
							<span><spring:message code="email_desc"/></span>
							<spring:message code="retrieve_login_pwd"/>
						</span>
					</div>
					
						
						
							<div class="col-xs-7 form-horizontal padding-top-30">
								<div class="form-group ">
									<label for="reset-email" class="col-xs-4 control-label"><spring:message code="email_address"/></label>
									<div class="col-xs-8">
										<input id="reset-email" class="form-control" type="text" autocomplete="off">
									</div>
								</div>
								<div class="form-group ">
									<label for="reset-idcard" class="col-xs-4 control-label"><spring:message code="card_type"/></label>
									<div class="col-xs-8">
										<select class="form-control" id="reset-idcard">
											<option value="1"><spring:message code="id_card"/></option>
										</select>
									</div>
								</div>
								<div class="form-group ">
									<label for="reset-idcardno" class="col-xs-4 control-label"><spring:message code="card_number"/></label>
									<div class="col-xs-8">
										<input id="reset-idcardno" class="form-control" type="text" autocomplete="off" placeholder="${account_cert_desc}">
									</div>
								</div>
								<div class="form-group ">
									<label for="reset-imgcode" class="col-xs-4 control-label"><spring:message code="ver_code"/></label>
									<div class="col-xs-8">
										<input id="reset-imgcode" class="form-control" type="text" autocomplete="off">
										<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=1473326869382"></img>
									</div>
								</div>
								<div class="form-group">
									<label for="reset-errortips" class="col-xs-4 control-label"></label>
									<div class="col-xs-8">
										<span id="reset-errortips" class="text-danger"></span>
									</div>
								</div>
								<div class="form-group ">
									<label for="btn-next" class="col-xs-4 control-label"></label>
									<div class="col-xs-8">
										<button id="btn-email-next" class="btn btn-danger btn-block"><spring:message code="submit"/></button>
									</div>
								</div>
							</div>
						
					
				</div>
			</div>
		</div>
	</div>


<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/reset.js"></script>
</body>
</html>
