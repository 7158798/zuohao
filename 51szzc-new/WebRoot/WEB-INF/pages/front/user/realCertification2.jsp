<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.user.jsp"></jsp:include>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
</head>
<body>
	

<jsp:include page="../comm/header.jsp"></jsp:include>

	<div class="container-full">
		<div class="container displayFlex">
			
			<jsp:include page="../comm/left_menu.jsp"></jsp:include>
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">		
					

						<div class="col-xs-12 padding-clear padding-top-30 ">
							<div class="panel">
								<div class="panel-body padding-left-clear padding-right-clear ">
								
									<c:if test="${fuser.fstatus == 1 }">
										<div class="user-realCertification-top-icon  ">
												<h3><spring:message code="user_real_status_title"/></h3>
										</div>
									</c:if>
											<h3><spring:message code="real_certification_info"/></h3>
											<table class="table table-user ">
												<tbody><tr>
													<td class="col-xs-3 user-tr">
														<i class="iconlist userface"></i>
														<spring:message code="real_name"/>
													</td>
													<td colspan="" class="col-xs-1 user-tr">${fuser.frealName }</td>
													<td class="col-xs-7 user-tr"></td>
													<td class="col-xs-1 user-tr"></td>
												</tr>
												<tr>
													<td class="col-xs-3 user-tr">
														<i class="iconlist usercertificate"></i>
														<spring:message code="card_type"/>
													</td>
													<td colspan="" class="col-xs-1 user-tr">
														${fuser.fidentityType_s }
													</td>
													<td class="col-xs-7 user-tr"></td>
													<td class="col-xs-1 user-tr"></td>
												</tr>
												<tr>
													<td class="col-xs-3 user-tr">
														<i class="iconlist userid"></i>
														<spring:message code="card_number"/>
													</td>
													<td colspan="" class="col-xs-1 user-tr">${fuser.fidentityNo_s }</td>
													<td class="col-xs-7 user-tr"></td>
													<td class="col-xs-1 user-tr"></td>
												</tr>
											<c:if test="${not empty fuser.fIdentityUrlOn && not empty fuser.fIdentityUrlOff}">
												<tr class="table-tr-renzheng">
													<td class="col-xs-1 user-tr">
														<i class="iconlist userid"></i>
														<spring:message code="card_info"/>：
													</td>
													<td colspan="" class="col-xs-8 user-tr">
														<div class="col-xs-4 text-center">
															<img  src="${fuser.fIdentityUrlOn}" >
															<p><spring:message code="positive_photo"/></p>
														</div>
														<div class="col-xs-4 text-center">
															<img  src="${
															fuser.fIdentityUrlOff
															}" >
															<p><spring:message code="negative_photo"/></p>
														</div>
													</td>
													<td class="col-xs-3 user-tr"></td>
												</tr>
											</c:if>
											</tbody></table>
										
										
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!-- 实名认证 -->
		<div class="modal modal-custom fade" id="bindrealinfo" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title">实名认证</span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="form-group text-center">
							<span class="modal-info-tips ">
								<span class=" "></span>
							<!-- 	<span class="text-danger  Certificationtsimg">认证年龄需满18周岁，最高年龄为60周岁</span> -->
							</span>
						</div>
						<div class="form-group ">
							<label for="bindrealinfo-realname" class="col-xs-3 control-label">真实姓名</label>
							<div class="col-xs-6">
								<input id="bindrealinfo-realname" class="form-control" placeholder="${fill_real_name}" type="text" autocomplete="off">
								<span id="bindrealinfo-realname-errortips " class="text-danger certificationinfocolor">*请填写真实姓名，认证后不能更改，提现时需要与银行等姓名信息保持一致。</span>
							</div>
						</div>
						<div class="form-group ">
							<label for="bindrealinfo-areaCode" class="col-xs-3 control-label">地区/国家</label>
							<div class="col-xs-6">
								<select class="form-control" id="bindrealinfo-address">
									<option value="86" selected>中国大陆(China)</option>
								</select>
							</div>
						</div>
						<div class="form-group ">
							<label for="bindrealinfo-areaCode" class="col-xs-3 control-label">证件类型</label>
							<div class="col-xs-6">
								<select class="form-control" id="bindrealinfo-identitytype">
									<option value="0">身份证</option>
								</select>
							</div>
						</div>
						<div class="form-group ">
							<label for="bindrealinfo-imgcode" class="col-xs-3 control-label">证件号码</label>
							<div class="col-xs-6">
								<input id="bindrealinfo-identityno" class="form-control" type="text" autocomplete="off">
							</div>
						</div>
						<div class="form-group ">
							<label for="bindrealinfo-ckinfo" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<div class="checkbox disabled">
									<label id="bindrealinfo-ckinfolb">
										<input type="checkbox" value="" id="bindrealinfo-ckinfo">
										我保证提交的信息实属本人所有，非盗用他人证件
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="bindrealinfo-errortips" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<span id="certificationinfo-errortips" class="text-danger"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<button id="bindrealinfo-Btn" class="btn btn-danger btn-block">确定提交</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		

		


 


	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/userrealcertification.js"></script>
</body>
</html>
