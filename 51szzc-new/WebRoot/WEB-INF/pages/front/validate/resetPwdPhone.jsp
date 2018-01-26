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
					<span class="col-xs-3 col-xs-offset-3 " id="resetprocess1">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">1</span>
						<span class="reset-process-text"><spring:message code="fill_account"/></span>
					</span>
					<span class="col-xs-3 active" id="resetprocess2">
						<span class="reset-process-line"></span>
						<span class="reset-process-icon">2</span>
						<span class="reset-process-text"><spring:message code="set_login_pwd"/></span>
					</span>
					<span class="col-xs-3" id="resetprocess3">
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
							<span><spring:message code="phone1"/></span>
							<spring:message code="retrieve_login_pwd"/>
						</span>
					</div>
					
						
							<div class="col-xs-7 form-horizontal padding-top-30" id="secondstep">
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-loginname"><spring:message code="sign_account"/></label>
									<div class="col-xs-8">
										<span class="form-control border-fff" id="reset-loginname">${fuser.ftelephone }</span>
									</div>
								</div>
								
								
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-newpass"><spring:message code="new_pwd"/></label>
									<div class="col-xs-8">
										<input type="password" autocomplete="off" class="form-control" id="reset-newpass">
									</div>
								</div>
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-confirmpass"><spring:message code="confirm_pwd"/></label>
									<div class="col-xs-8">
										<input type="password" autocomplete="off" class="form-control" id="reset-confirmpass">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-4 control-label" for="reset-success-errortips"></label>
									<div class="col-xs-8">
										<span class="text-danger" id="reset-success-errortips"></span>
									</div>
								</div>
								<div class="form-group ">
									<label class="col-xs-4 control-label" for="reset-imgcode"></label>
									<div class="col-xs-8">
										<button class="btn btn-danger btn-block" id="btn-email-success"><spring:message code="next"/></button>
									</div>
								</div>
							</div>
							<div style="display:none" class="col-xs-12 padding-top-30 successstep text-center" id="successstep">
								<div>
									<i class="successstep-icon"></i>
									<spring:message code="reset_pwd_sucs"/>
								</div>
								<a class="btn btn-danger btn-find" href="/user/login.html"><spring:message code="now_login"/></a>
							</div>
						
						
					
				</div>
			</div>
		</div>
	</div>
	${isQuestionValidate}
	<!-- 安全问题验证 -->
<c:if test="${isQuestionValidate == true}">
	<div  class="modal modal-custom fade" id="questionreComfime" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
						<a class="close btn-modal" href="/validate/resetPhone.html">
							<span aria-hidden="true">×</span>
						</a>				
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
							<input data-id="${fid}" value="${question}" id="randomQuestion" type="text" class="form-control" readonly>
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
</c:if>

<input type="hidden" id="fid" value="${fuser.fid}"/>
<input type="hidden" id="ev_id" value="0"/>
<input type="hidden" id="newuuid" value="0"/>
<input type="hidden" id="mtype" value="1"/>
<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/reset.js"></script>
</body>
</html>
