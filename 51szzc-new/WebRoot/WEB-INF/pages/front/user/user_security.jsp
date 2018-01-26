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
<body>
	


<%@include file="../comm/header.jsp" %>

 

	<div class="container-full">
		<div class="container displayFlex">
		

<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
						<div class="user-top-icon">
							<div class="col-xs-2 text-center">
								<i class="top-icon"></i>
							</div>
							<div class="col-xs-6 padding-left-clear">
								<div class="h5">
									
										<span class="top-title1">${fuser.frealName}</span>
									
									<span class="top-vip vip${level }">VIP${level }</span>
								</div>
								<div>
									<span class="top-title2">UID:${fuser.fid }</span><span class="top-title3">${loginName}</span>
								</div>
							</div>
							<div class="col-xs-4">
								<h5 class="top-title4">
									<spring:message code="security_title1"/> <span class="top-title6"> ${bindType} </span> <spring:message code="security_title2"/> <span class="top-title6">${8-bindType } </span> <spring:message code="security_title3"/>
								</h5>
								<h5 class="top-title5"><spring:message code="user_bind_title1"/></h5>
							</div>
						</div>

						<div class="col-xs-12 padding-clear">
							<div class="banksWrap col-xs-12 padding-clear">
								<h4 class="banksWrap-title"><spring:message code="user_bank_list"/></h4>
								<h5 class="banksWrap-tips"><spring:message code="user_bank_uplimit"/></h5>
								<div class="bank-items col-xs-12">
									<c:forEach items="${fbankinfoWithdraws}" var="v">
										<div class="bank-item  ">
											<div class="bank-name">
												<img src="${v.url}" >
												${v.fname}
											</div>
											<div class="bank-info">
												<spring:message code="bank_number"/>：${v.fbankNumber}
												<a data-id="${v.fid}" class="pull-right text-link unbindbank-btn" href="javascript:void(0);" data-toggle="modal" data-target="#unbindbank"><spring:message code="unbindbank"/></a>
											</div>
										</div>
									</c:forEach>
									<!-- 少于5张，显示添加 -->
									<c:if test="${fbankinfoWithdraws.size() < bankCount}">
										<a data-toggle="modal" data-target="#addBankDialog" class=" bank-item text-center banksWrap-addBtn"  href="javascript:void(0);">
											<img src="${oss_url}/static/front/images/user/Add@2X.png" >
											<p><spring:message code="add_bank"/></p>
										</a>
									</c:if>
								</div>
							</div>
							<div class="con-items">
								
									
										<div class="title">
											<span><spring:message code="bind_email"/></span>

											<c:if test="${isBindEmail == false}">
												<a class="text-primary text-link bind" href="#" data-toggle="modal" data-target="#bindemail"><spring:message code="bind_text"/>>></a>
											</c:if>

										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="email ${isBindEmail == true?'':'settting' }"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isBindEmail == false?'no':'ok' }">
													<c:choose>
														<c:when test="${isBindEmail == false}">
															<spring:message code="user_no_bind"/>
														</c:when>
														<c:otherwise>
															<spring:message code="user_binded"/>
														</c:otherwise>
													</c:choose>
												</p>
												<c:if test="${isBindEmail == true}">
												<p><spring:message code="user_bind_email"/></p>
												<p>${email}</p>
												</c:if>
											</div>
										</div>
									
									
									
								
							</div>
							<div class="con-items center">
								
									
										<div class="title">
											<span><spring:message code="bind_phone"/></span>
									        <c:if test="${isBindTelephone == false}">
												<a class="text-link bind" href="javascript:void(0);" data-toggle="modal" data-target="#bindphone"><spring:message code="bind_text"/>&gt;&gt;</a>
											</c:if>
											<c:if test="${isBindTelephone == true }">
											<a class="text-link update" href="javascript:void(0);" data-toggle="modal" data-target="#unbindphone"><spring:message code="update"/>&gt;&gt;</a>
											</c:if>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="phone ${isBindTelephone == true?'':'settting' }"></i></span>
											</div>
											
											<div class="content-rt">
												<p class="icon-${isBindTelephone == false?'no':'ok' }">
													<c:choose>
														<c:when test="${isBindTelephone == false}">
															<spring:message code="user_no_bind"/>
														</c:when>
														<c:otherwise>
															<spring:message code="user_binded"/>
														</c:otherwise>
													</c:choose>
												</p>
												<c:if test="${isBindTelephone == true}">
												<p><spring:message code="user_bind_phone"/></p>
												<p>${telNumber}</p>
												</c:if>
											</div>
											
										</div>
								
							</div>
							<c:if test="${fbankinfoWithdraws.size() == 0}">
								<div class="con-items">
									<div class="title">
										<span><spring:message code="bind_bank"/></span>
										
										<c:if test="${fuser.fhasRealValidate}">
										<a class="text-link bind" href="javascript:void(0);" data-toggle="modal" data-target="#addBankDialog"><spring:message code="bind_text"/>&gt;&gt;</a>
										</c:if>

										<c:if test="${!fuser.fhasRealValidate}">
										<a class="text-link bind" href="/user/realCertification.html" >绑定&gt;&gt;</a>
										</c:if>
									</div>
									<div class="content">
										<div class="content-lt">
											<span> <i class="bank-no"></i>
											</span>
										</div>
										<div class="content-rt">
											<p class="icon-no"><spring:message code="bind_text"/></p>
											<p><spring:message code="security_title4"/></p>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${fbankinfoWithdraws.size() != 0}">
								<div class="con-items">
									<div class="title">
										<span><spring:message code="bind_bank"/></span>
										<!--少于5,显示新增-->
										<c:if test="${fbankinfoWithdraws.size() < bankCount }">
											<a class="text-link bind" href="javascript:void(0);" data-toggle="modal" data-target="#addBankDialog"><spring:message code="add"/>&gt;&gt;</a>
										</c:if>
									</div>
									<div class="content">
										<div class="content-lt">
											<span> <i class="bank"></i>
											</span>
										</div>
										<div class="content-rt">
											<p class="icon-ok"><spring:message code="user_binded"/></p>
											<p><spring:message code="security_title5"/>${fbankinfoWithdraws.size()}<spring:message code="security_title6"/> <a class="pull-right usershowbanklist" href="javascript:;"><spring:message code="view_desc"/>&gt;&gt;</a></p>
										</div>
									</div>
								</div>
							</c:if>
							<div class="con-items">
								
										<div class="title">
											<span><spring:message code="bind_google"/></span>
									        <c:choose>
											<c:when test="${isBindGoogle }">
											<a class="text-link update" href="javascript:void(0);" data-toggle="modal" data-target="#unbindgoogle"><spring:message code="view_desc"/>&gt;&gt;</a>

											</c:when>
											<c:otherwise>
											

											<c:if test="${fuser.fhasRealValidate}">
											<a class="text-link bind" href="javascript:void(0);" data-toggle="modal" data-target="#bindgoogle"><spring:message code="bind_text"/>&gt;&gt;</a>
											</c:if>

											<c:if test="${!fuser.fhasRealValidate}">
											<a class="text-link bind " href="/user/realCertification.html" ><spring:message code="bind_text"/>&gt;&gt;</a>
											</c:if>

											</c:otherwise>
											</c:choose>
											
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="google ${isBindGoogle == true?'':'settting' }"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isBindGoogle == false?'no':'ok' }">
													<c:choose>
														<c:when test="${isBindGoogle == false}">
															<spring:message code="user_no_bind"/>
														</c:when>
														<c:otherwise>
															<spring:message code="user_binded"/>
														</c:otherwise>
													</c:choose>
												</p>
												<p><spring:message code="security_title7"/></p>
											</div>
										</div>
							</div>
							<div class="con-items">
								
									
										<div class="title">
											<span><spring:message code="login_password"/></span>
											<a class="text-link update" href="javascript:void(0);" data-toggle="modal" data-target="#unbindloginpass"><spring:message code="update"/>&gt;&gt;</a>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="login"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-ok"><spring:message code="security_title8"/></p>
												<p><spring:message code="signIn"/>${requestScope.constant['webName']}<spring:message code="security_title9"/></p>
											</div>
										</div>
									
									
								
							</div>
							<div class="con-items center">
								
									
										<div class="title">
											<span><spring:message code="trade_password"/></span>
											<c:if test="${isTradePassword == false }">
												

												<c:if test="${fuser.fhasRealValidate}">
												<a class="text-link bind" href="javascript:void(0);" data-toggle="modal" data-target="#bindtradepass"><spring:message code="sys_set"/>>></a>
												</c:if>

												<c:if test="${!fuser.fhasRealValidate}">
												<a class="text-link bind " href="/user/realCertification.html" >设置&gt;&gt;</a>
												</c:if>
											</c:if>
											<c:if test="${isTradePassword == true }">
											    

											    <c:if test="${fuser.fhasRealValidate}">
											    <a class="text-link update" href="javascript:void(0);" data-toggle="modal" data-target="#unbindtradepass"><spring:message code="update"/>&gt;&gt;</a>
											    </c:if>

											    <c:if test="${!fuser.fhasRealValidate}">
											    <a class="text-link update " href="/user/realCertification.html" ><spring:message code="update"/>&gt;&gt;</a>
											    </c:if>
											</c:if>
										</div>
										<div class="content">
											<div class="content-lt">
												<span><i class="trade ${isTradePassword == true?'':'settting' }"></i></span>
											</div>
											<div class="content-rt">
												<p class="icon-${isTradePassword == false?'no':'ok' }">
													<c:choose>
														<c:when test="${isTradePassword == false}">
															<spring:message code="security_title10"/>
														</c:when>
														<c:otherwise>
															<spring:message code="security_title8"/>
														</c:otherwise>
													</c:choose>
												</p>
												<p><spring:message code="trade_account_use"/></p>
											</div>
										</div>
									
									
								
							</div>
							<div class="con-items">
								<div class="title">
									<span><spring:message code="lmenu_real_certification"/></span>
									<c:if test="${kyclevel != 4}">
										<a class="text-link bind" href="/user/realCertification.html">认证&gt;&gt;</a>
									</c:if>
								</div>
								<div class="content">
									<div class="content-lt">
										<span>
											<c:if test="${kyclevel >= 2}">
												<i class="realname"></i>
											</c:if>
											<c:if test="${kyclevel < 2}">
												<i class="con-items-warn"></i>
											</c:if>
										</span>
									</div>
									<div class="content-rt">
										<c:if test="${kyclevel == 2 || kyclevel == 3 || kyclevel == 5}">
												<p class="icon-ok">KYC1</p>
												<p>您已通过KYC1认证，建议进行KYC2认证获得更多权限</p>
										</c:if>
										<c:if test="${kyclevel == 4}">
												<p class="icon-ok">KYC2</p>
												<p>您的账号已通过KYC2认证</p>
										</c:if>
										<c:if test="${kyclevel < 2}">
												<p class="icon-no">立即认证</p>
												<p>您还没有进行实名认证，请立即认证身份</p>
										</c:if>
							
									</div>
								</div>
							</div>
						
					<c:if test="${isQuestionValidate != true}">
						<div class="con-items">
							<div class="title">
								<span>安全问题验证</span>
								<a class="text-link update" href="javascript:void(0);" data-toggle="modal" data-target="#questionSet">设置&gt;&gt;</a>
							</div>
							<div class="content">
								<div class="content-lt">
									<span> <i class="question"></i></i>
									</span>
								</div>
								<div class="content-rt">
									<p class="icon-no">未设置</p>
									<p>设置安全问题，增加账户安全</p>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${isQuestionValidate == true}">
						<div class="con-items">
							<div class="title">
								<span>安全问题验证</span>
								<a class="startConfime-btn text-link update" href="javascript:void(0);">修改&gt;&gt;</a>
							</div>
							<div class="content">
								<div class="content-lt">
									<span> <i class="question active"></i>
									</span>
								</div>
								<div class="content-rt">
									<p class="icon-ok">已设置</p>
									<p>设置安全问题，添加账户安全</p>
								</div>
							</div>
						</div>
					</c:if>

							<!-- <div class="con-items">
								<div class="title">
									<span>实名认证</span>
								</div>
								<div class="content">
									<div class="content-lt">
										<span> <i class="con-items-warn"></i>
										</span>
									</div>
									<div class="content-rt text-danger">
										<p>立即升级</p>
										<p>您的帐号存在风险，请尽快认证升级</p>
									</div>
								</div>
							</div> -->
							<!-- <div class="con-items">
								<div class="title">
									<span>实名认证</span>
								</div>
								<div class="content">
									<div class="content-lt">
										<span> <i class="con-items-error"></i>
										</span>
									</div>
									<div class="content-rt text-danger">
										<p>认证失败</p>
										<p>
											<a href="/user/realCertification.html">查看原因&gt;&gt;</a>
										</p>
									</div>
								</div>
							</div> -->
							<!-- <div class="con-items">
								<div class="title">
									<span>实名认证</span>
								</div>
								<div class="content">
									<div class="content-lt">
										<span> <i class="con-items-update"></i>
										</span>
									</div>
									<div class="content-rt text-primary">
										<p>审核中</p>
										<p>
											信息审核中，请及时关注
										</p>
									</div>
								</div>
							</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 修改绑定手机 -->
	<c:if test="${isBindTelephone ==true}">
			<div class="modal modal-custom fade" id="unbindphone" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="security_title15"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group text-center">
								<span class="modal-info-tips">
									<spring:message code="security_title16"/>
									<span class="text-danger">${loginName}</span>
									<spring:message code="security_title15"/>
								</span>
							</div>
							<div class="form-group ">
								<label for="unbindphone-phone" class="col-xs-3 control-label"><spring:message code="security_title17"/></label>
								<div class="col-xs-6">
									<span id="unbindphone-phone" class="form-control border-fff" type="text" autocomplete="off">${telNumber}</span>
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindphone-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
								<div class="col-xs-6">
									<input id="unbindphone-msgcode" class="form-control" type="text" autocomplete="off">
									<button id="unbindphone-sendmessage" data-msgtype="3" data-tipsid="unbindphone-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindphone-areaCode" class="col-xs-3 control-label"><spring:message code="security_title18"/></label>
								<div class="col-xs-6">
									<select class="form-control" id="unbindphone-areaCode">
										<option value="+86">中国大陆(China)</option>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindphone-newphone" class="col-xs-3 control-label"><spring:message code="change_phone"/></label>
								<div class="col-xs-6">
									<span id="unbindphone-newphone-areacode" class="btn btn-areacode">+86</span>
									<input id="unbindphone-newphone" class="form-control padding-left-92" type="text" autocomplete="off">
								</div>
							</div>
						
						<c:if test="${isBindTelephone }">		
							<div class="form-group ">
								<label for="unbindphone-newmsgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
								<div class="col-xs-6">
									<input id="unbindphone-newmsgcode" class="form-control" type="text" autocomplete="off">
									<button id="unbindphone-newsendmessage" data-msgtype="2" data-tipsid="unbindphone-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
								</div>
							</div>
						</c:if>	
						
						<c:if test="${isBindGoogle ==true}">	
								<div class="form-group">
									<label for="unbindphone-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
									<div class="col-xs-6">
										<input id="unbindphone-googlecode" class="form-control" type="text" autocomplete="off">
									</div>
								</div>
						</c:if>		
							
							<div class="form-group ">
								<label for="unbindphone-imgcode" class="col-xs-3 control-label"><spring:message code="ver_code"/></label>
								<div class="col-xs-6">
									<input id="unbindphone-imgcode" class="form-control" type="text" autocomplete="off">
									<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindphone-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindphone-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindemail-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindphone-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</c:if>	
	
	
		
		
			<!-- 谷歌查看 -->
			<c:if test="${isBindGoogle ==true}">
			<div class="modal modal-custom fade" id="unbindgoogle" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="view_google_code"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<div class="col-xs-12 clearfix">
									<div id="unbindgoogle-code" class="border-fff ">
										<div class="form-qrcode" id="unqrcode"></div>
									</div>
								</div>
							</div>
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<label for="unbindgoogle-key" class="col-xs-3 control-label"><spring:message code="key_desc"/></label>
								<div class="col-xs-6">
									<span id="unbindgoogle-key" class="form-control border-fff"></span>
								</div>
							</div>
							<div class="form-group unbindgoogle-hide-box" style="display:none">
								<label for="unbindgoogle-device" class="col-xs-3 control-label"><spring:message code="device_name"/></label>
								<div class="col-xs-6">
									<span id="unbindgoogle-device" class="form-control border-fff">${device_name }</span>
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-topcode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
								<div class="col-xs-6">
									<input id="unbindgoogle-topcode" class="form-control" type="text" autocomplete="off">
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindgoogle-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group unbindgoogle-show-box">
								<label for="unbindgoogle-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindgoogle-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</c:if>
	
	
		<!-- 邮箱绑定 -->
		<div class="modal modal-custom fade" id="bindemail" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title"><spring:message code="bind_email"/></span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="form-group ">
							<label for="bindemail-email" class="col-xs-3 control-label"><spring:message code="email_address"/></label>
							<div class="col-xs-6">
								<input id="bindemail-email" class="form-control" type="text" autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="bindemail-errortips" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<span id="bindemail-errortips" class="text-danger"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
							<div class="col-xs-6">
								<button id="bindemail-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	
		
			<!-- 绑定手机 -->
			<div class="modal modal-custom fade" id="bindphone" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="bind_phone"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group text-center">
								<span class="modal-info-tips">
									<spring:message code="security_title16"/>
									<span class="text-danger">${loginName}</span>
									<spring:message code="bind_phone"/>
								</span>
							</div>
							<div class="form-group ">
								<label for="bindphone-areaCode" class="col-xs-3 control-label"><spring:message code="security_title18"/></label>
								<div class="col-xs-6">
									<select class="form-control" id="bindphone-areaCode">
										<option value="+86">中国大陆(China)</option>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindphone-newphone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
								<div class="col-xs-6">
									<span id="bindphone-newphone-areacode" class="btn btn-areacode">+86</span>
									<input id="bindphone-newphone" class="form-control padding-left-92" type="text" autocomplete="off">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindphone-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
								<div class="col-xs-6">
									<input id="bindphone-msgcode" class="form-control" type="text" autocomplete="off">
									<button id="bindphone-sendmessage" data-msgtype="2" data-tipsid="bindphone-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
								</div>
							</div>
							
							<c:if test="${isBindGoogle ==true}">
							<div class="form-group">
									<label for="bindphone-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
									<div class="col-xs-6">
										<input id="bindphone-googlecode" class="form-control" type="text" autocomplete="off">
									</div>
								</div>
							</c:if>	
							
							<div class="form-group ">
								<label for="bindphone-imgcode" class="col-xs-3 control-label"><spring:message code="ver_code"/></label>
								<div class="col-xs-6">
									<input id="bindphone-imgcode" class="form-control" type="text" autocomplete="off">
									<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
								</div>
							</div>
							<div class="form-group">
								<label for="bindphone-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="bindphone-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="bindphone-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		
		
	
	
		
			<!-- 谷歌绑定 -->
			<div class="modal modal-custom fade" id="bindgoogle" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="bind_google_code"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<div class="col-xs-12 clearfix">
									<div id="bindgoogle-code" class="form-control border-fff  form-qrcodebox">
										<div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotess"></div>
										<%--<div class="form-qrcodebox-cld">--%>
											<%--<div class="form-qrcode-coded">--%>
												<%--<div class="form-qrcode-title text-center">--%>
													<%--<span>下载谷歌验证器</span>--%>
												<%--</div>--%>
												<%--<div class="form-qrcode">--%>
													<%--<img class="form-qrcode-img" src="${requestScope.constant['googleImages'] }"/>--%>
												<%--</div>--%>
											<%--</div>--%>
											<%--<div class="form-qrcode-tips">--%>
												<%--<span>--%>
													<%--若未安装谷歌验证器请--%>
													<%--<span class="text-danger">扫码下载</span>--%>
													<%--。--%>
												<%--</span>--%>
											<%--</div>--%>
										<%--</div>--%>
										<div class="col-xs-12">
											<div class="form-qrcode-codec">
												<div class="form-qrcode-title text-center">
													<span><spring:message code="bind_google_code"/></span>
												</div>
												<div class="form-qrcode" id="qrcode">
												</div>
											</div>
											<%--<div class="form-qrcode-tips">--%>
												<%--<span>--%>
													<%--请扫码或手工输入密钥，将手机上生成的--%>
													<%--<span class="text-danger">动态验证码</span>--%>
													<%--填到下边输入框。--%>
												<%--</span>--%>
											<%--</div>--%>
										</div>
										<div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotese"></div>
									</div>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-key" class="col-xs-3 control-label"><spring:message code="key_desc"/></label>
								<div class="col-xs-7">
									<span id="bindgoogle-key" class="form-control border-fff"></span>
									<input id="bindgoogle-key-hide" type="hidden">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-device" class="col-xs-3 control-label"><spring:message code="device_name"/></label>
								<div class="col-xs-7">
									<span id="bindgoogle-device" class="form-control border-fff">${device_name }</span>
								</div>
							</div>
							<div class="form-group ">
								<label for="bindgoogle-topcode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
								<div class="col-xs-7">
									<input id="bindgoogle-topcode" class="form-control" type="text" autocomplete="off">
								</div>
							</div>
							<div class="form-group text-center">
								<div class="col-xs-8 col-xs-offset-2"><spring:message code="google_code_desc1"/></div>
							</div>
							<div class="form-group text-center">
								<div class="col-xs-8 col-xs-offset-2"><spring:message code="google_code_desc2"/></div>
							</div>
							<div class="form-group clearfix">
								<div class="col-xs-8 col-xs-offset-2"><a class="pull-right btn" target="_blank" href="/about/index.html?id=43&type=9"><spring:message code="look_help"/></a></div>
							</div>
							<div class="form-group">
								<label for="bindgoogle-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-7">
									<span id="bindgoogle-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindgoogle-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-7">
									<button id="bindgoogle-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		
		
	
	<!-- 登录密码修改 -->
	<div class="modal modal-custom fade" id="unbindloginpass" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title"><spring:message code="update_login_pwd"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="unbindloginpass-oldpass" class="col-xs-3 control-label"><spring:message code="old_login_pwd"/></label>
						<div class="col-xs-6">
							<input id="unbindloginpass-oldpass" class="form-control" type="password" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="unbindloginpass-newpass" class="col-xs-3 control-label"><spring:message code="new_login_pwd"/></label>
						<div class="col-xs-6">
							<input id="unbindloginpass-newpass" class="form-control" type="password" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="unbindloginpass-confirmpass" class="col-xs-3 control-label"><spring:message code="config_login_pwd"/></label>
						<div class="col-xs-6">
							<input id="unbindloginpass-confirmpass" class="form-control" type="password" autocomplete="off">
						</div>
					</div>
				
				<c:if test="${isBindTelephone }">	
					<div class="form-group">
						<label for="unbindloginpass-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
						<div class="col-xs-6">
							<input id="unbindloginpass-msgcode" class="form-control" type="text" autocomplete="off">
							<button id="unbindloginpass-sendmessage" data-msgtype="6" data-tipsid="unbindloginpass-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
						</div>
					</div>
				</c:if>	
				
				<c:if test="${isBindGoogle }">
					<div class="form-group">
						<label for="unbindloginpass-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
						<div class="col-xs-6">
							<input id="unbindloginpass-googlecode" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
				</c:if>	
					
					<div class="form-group">
						<label for="unbindloginpass-errortips" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<span id="unbindloginpass-errortips" class="text-danger "></span>
						</div>
					</div>
					<div class="form-group">
						<label for="unbindloginpass-Btn" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<button id="unbindloginpass-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		
			<!-- 交易密码设置 -->
			<div class="modal modal-custom fade" id="bindtradepass" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="set_trade_pwd"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<label for="bindtradepass-newpass" class="col-xs-3 control-label"><spring:message code="new_trade_pwd"/></label>
								<div class="col-xs-6">
									<input id="bindtradepass-newpass" class="form-control" type="password" autocomplete="off">
								</div>
							</div>
							<div class="form-group ">
								<label for="bindtradepass-confirmpass" class="col-xs-3 control-label"><spring:message code="config_login_pwd"/></label>
								<div class="col-xs-6">
									<input id="bindtradepass-confirmpass" class="form-control" type="password" autocomplete="off">
								</div>
							</div>
							
							<c:if test="${isBindTelephone }">
							<div class="form-group">
									<label for="bindtradepass-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
									<div class="col-xs-6">
										<input id="bindtradepass-msgcode" class="form-control" type="text" autocomplete="off">
										<button id="bindtradepass-sendmessage" data-msgtype="7" data-tipsid="bindtradepass-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
									</div>
								</div>
							</c:if>
							
							<c:if test="${isBindGoogle }">
							<div class="form-group">
									<label for="bindtradepass-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
									<div class="col-xs-6">
										<input id="bindtradepass-googlecode" class="form-control" type="text" autocomplete="off">
									</div>
								</div>	
							</c:if>
							
							<div class="form-group">
								<label for="bindtradepass-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="bindtradepass-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="bindtradepass-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 删除银行卡 -->
			<div class="modal modal-custom fade" id="unbindbank" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="unbind_bank_card"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="formbanks container-fluid">
								<c:forEach items="${fbankinfoWithdraws}" var="v">
										<div data-id="${v.fid}" class="bank-item  ">
											<div class="bank-name">
												<img src="${v.url}" >
												${v.fname}
											</div>
											<div class="bank-info">
												<spring:message code="bank_number"/>：${v.fbankNumber}
												
											</div>
									</div>
								</c:forEach>
							</div>
							<div class="form-group ">
								<label for="bindtradepass-newpass" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
								<div class="col-xs-6">
									<input id="unbindBank-phone" type="text" class="form-control" readonly value="${fuser.ftelephone}">
								</div>
							</div>
							
							<div class="form-group">
									<label for="bindtradepass-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
									<div class="col-xs-6">
										<input id="unbindBank-msgcode" class="form-control" type="text" autocomplete="off">
										<button id="unbindbank-megbtn" data-msgtype="15" data-tipsid="unbindbank-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
									</div>
								</div>							
							
							<div class="form-group">
								<label for="unbindbank-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindbank-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<input id="unbindbankid" type="hidden">
									<button id="confirm-unbindbank" class="btn btn-danger btn-block"><spring:message code="config_unbind"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			

			<!-- 绑银行卡成功-->
			<div  class="modal modal-custom fade" id="bindbank-success" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<span class="modal-title"><spring:message code="unbind_bank_carded"/></span>
						</div>
						<div class="modal-body form-horizontal">

							
									<div class="form-group">
										<img class="col-xs-6 col-xs-offset-3" src="${oss_url}/static/front/images/user/Bank_card@2X.png" >
									</div>
									<div class="form-group text-center">
										<p><spring:message code="bind_bank_card_suc"/></p>
									</div>
									<div class="form-group">
										<label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
										<div class="col-xs-6">
											<input id="unbindbankid" type="hidden">
											<button id="bindbank-success-btn" class="btn btn-danger btn-block"><spring:message code="finish"/></button>
										</div>
									</div>
							</div>
					</div>
				</div>
			</div>

			<!-- 解绑银行卡成功-->
			<div  class="modal modal-custom fade" id="unbindbank-success" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<span class="modal-title"><spring:message code="unbind_bank_card"/></span>
						</div>
						<div class="modal-body form-horizontal">

							
									<div style="padding:20px 0 20px 140px;" class="clearfix">
										<img style="height:80px;" class="pull-left" src="${oss_url}/static/front/images/user/Successful@2X.png" >
										<div style="padding:20px 0 0 20px" class="pull-left">
											<p><spring:message code="unbind_success"/></p>
											<p><spring:message code="unbind_bank_card_suc"/></p>
										</div>
									</div>
									<div class="form-group">
										<label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
										<div class="col-xs-6">
											<input id="unbindbankid" type="hidden">
											<button id="unbindbank-success-btn" class="btn btn-danger btn-block"><spring:message code="finish"/></button>
										</div>
									</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<!-- 交易密码修改 -->
			<c:if test="${isTradePassword ==true}">
			<div class="modal modal-custom fade" id="unbindtradepass" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="update_trade_pwd"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<label for="unbindtradepass-oldpass" class="col-xs-3 control-label"><spring:message code="old_trade_pwd"/></label>
								<div class="col-xs-6">
									<input id="unbindtradepass-oldpass" class="form-control" type="password" autocomplete="off">
								</div>
								<a class="findPassBtn text-danger btn" href="javascript:;"><spring:message code="forgot_password"/></a>
							</div>
							<div class="form-group ">
								<label for="unbindtradepass-newpass" class="col-xs-3 control-label"><spring:message code="new_trade_pwd"/></label>
								<div class="col-xs-6">
									<input id="unbindtradepass-newpass" class="form-control" type="password" autocomplete="off">
								</div>
							</div>
							<div class="form-group ">
								<label for="unbindtradepass-confirmpass" class="col-xs-3 control-label"><spring:message code="config_login_pwd"/></label>
								<div class="col-xs-6">
									<input id="unbindtradepass-confirmpass" class="form-control" type="password" autocomplete="off">
								</div>
							</div>
							
							<c:if test="${isBindTelephone }">
								<div class="form-group">
									<label for="unbindtradepass-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
									<div class="col-xs-6">
										<input id="unbindtradepass-msgcode" class="form-control" type="text" autocomplete="off">
										<button id="unbindtradepass-sendmessage" data-msgtype="7" data-tipsid="unbindtradepass-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
									</div>
								</div>
							</c:if>
							
							<c:if test="${isBindGoogle }">
								<div class="form-group">
									<label for="unbindtradepass-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
									<div class="col-xs-6">
										<input id="unbindtradepass-googlecode" class="form-control" type="text" autocomplete="off">
									</div>
								</div>
							</c:if>	
							
							<div class="form-group">
								<label for="unbindtradepass-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="unbindtradepass-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="unbindtradepass-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="unbindtradepass-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>

		<!-- 交易密码找回 -->
			<c:if test="${isTradePassword ==true}">
			<div class="modal modal-custom fade" id="retrievePassword" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-mark"></div>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span class="modal-title"><spring:message code="look_tarde_pwd"/></span>
						</div>
						<div class="modal-body form-horizontal">
							<div class="form-group ">
								<label for="retrievePassword-userPhone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
								<div class="col-xs-6">
									<input id="retrievePassword-userPhone" class="form-control" readonly type="text" value="${fuser.ftelephone}" autocomplete="off">
								</div>
							</div>
							<div class="form-group ">
								<label for="retrievePassword-imgpwd" class="col-xs-3 control-label"><spring:message code="ver_code"/></label>
								<div class="col-xs-6">
									<input style="width:165px" id="retrievePassword-imgpwd" class="form-control" type="text" autocomplete="off">
									<img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=1488161262601">
								</div>
							</div>
							<div class="form-group">
								<label for="retrievePassword-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
								<div class="col-xs-6">
									<input id="retrievePassword-msgcode" class="form-control" type ="text" autocomplete="off">
									<button id="retrievePassword-sendmessage" data-msgtype="17" data-tipsid="retrievePassword-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
								</div>
							</div>
							<div class="form-group ">
								<label for="retrievePassword-id" class="col-xs-3 control-label"><spring:message code="card_num"/></label>
								<div class="col-xs-6">
									<input id="retrievePassword-id" class="form-control" type="text" autocomplete="off">
								</div>
							</div>
							<div class="form-group">
								<label for="retrievePassword-newpassword" class="col-xs-3 control-label"><spring:message code="new_trade_pwd"/></label>
								<div class="col-xs-6">
									<input id="retrievePassword-newpassword" class="form-control" type="password" autocomplete="new-password">
									<a class="pwdSwitch" href="javascript:;"></a>
								</div>
							</div>
							
							<div class="form-group">
								<label for="retrievePassword-errortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<span id="retrievePassword-errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="retrievePassword-Btn" class="col-xs-3 control-label"></label>
								<div class="col-xs-6">
									<button id="retrievePassword-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	
	<div class="modal modal-custom fade" id="addBankDialog" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" style="display: none;position: absolute;">
		<div class="modal-dialog" role="document">
			<div class="modal-mark" style="height: 565px; width: 575px;"></div>
			<div class="modal-content" style="margin-top: 0px;">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="add_bank"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="accountInfo clearfix">
						<div class="accountInfo-left pull-left">
							账<br>户<br>信<br>息
						</div>
						<div class="accountInfo-right">
							<p><spring:message code="account_name"/>：<span class="account-name"></span><span class="level-icon">VIP1</span></p>
							<p><spring:message code="bind_email"/>：
								<c:choose>
									<c:when test="${email != null && email.length() > 0}">
										${email}
									</c:when>
									<c:otherwise>
										<spring:message code="user_no_bind"/>
									</c:otherwise>
								</c:choose>
							<p><spring:message code="card_num"/>：<span class="accountInfo-id"></span></p>
							<p><spring:message code="bank_card"/>：<spring:message code="wait_bind"/></p>
						</div>
					</div>
					<div class="form-group ">
						<label for="payeeAddr" class="col-xs-3 control-label"><spring:message code="name_title"/></label>
						<div class="col-xs-8">
							<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly">			
						</div>
					</div>
					<div class="form-group ">
						<label for="openBankTypeAddr" class="col-xs-3 control-label"><spring:message code="bank_name"/></label>
						<div class="col-xs-8">
							<input id="openBankTypeAddr" class="form-control" type="text" ></div>
					</div>
					<div class="form-group ">
						<label for="withdrawAccountAddr" class="col-xs-3 control-label"><spring:message code="bank_account"/></label>
						<div class="col-xs-8">
							<input id="withdrawAccountAddr" class="form-control" type="" text="">			
							<div class="help-block text-danger tips"><spring:message code="regc_desc1"/></div>
						</div>
					</div>
				
					<div id="prov_city" class="form-group ">
						<label for="prov" class="col-xs-3 control-label"><spring:message code="bank_account_address"/></label>
						<div class="col-xs-8 ">
							<div class="col-xs-4 padding-right-clear padding-left-clear margin-bottom-15">
								<select id="prov" class="form-control"></select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="city" class="form-control"></select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="dist" class="form-control prov"></select>
							</div>
							<div class="col-xs-12 padding-right-clear padding-left-clear">
								<input id="address" class="form-control" type="text" autocomplete="off" placeholder="${bank_account_address1}"/>
							</div>
						</div>
					</div>
					<div class="form-group ">
						<label for="dialogPhone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
						<div class="col-xs-8">
							<input id="dialogPhone" class="form-control" type="text" <c:if test="${not empty fuser.ftelephone}">readonly</c:if> value="${fuser.ftelephone}">
						</div>
					</div>
					<div class="form-group">
						<label for="addressPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
						<div class="col-xs-8">
							<input id="addressPhoneCode" class="form-control" type="text" autocomplete="off">			
							<button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
						</div>
					</div>

				<div class="form-group">
					<label for="binderrortips" class="col-xs-3 control-label"></label>
					<div class="col-xs-8">
						<span id="binderrortips" class="text-danger"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="withdrawCnyAddrBtn" class="col-xs-3 control-label"></label>
					<div class="col-xs-8">
						<button id="withdrawCnyAddrBtn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- 完成认证 -->
	<div  class="modal modal-custom fade" id="finishDialog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title"><spring:message code="comp_certi"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="dialog-title dialog-title--border">
						<img class="dialog-compImg" src="${oss_url}/static/front/images/user/Certification_coppleted_mascot.png" >
						<spring:message code="congratulations"/><br><spring:message code="comp_certi"/>
						<img class="dialog-exclamationImg" src="${oss_url}/static/front/images/user/Certification_coppleted_exclamatory mark.png" >
					</div>
					<div class="dialog-title">
						<spring:message code="account_info"/>
					</div>
					<div class="form-group finishDialog-info row text-center">
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="account_name"/>：
							</span>
							<span class="col-xs-7 text-left">
								<span class="account-name"></span>
								<span class="level-icon">VIP1</span>
							</span>
						</p>
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="phone"/>：</span>
							<span class="account-phone col-xs-7 text-left">
								<span class="accountInfo-phone"></span>
							</span>
						</p>
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="card_num"/>：</span>
							<span class="col-xs-7 text-left">
								<span class="accountInfo-id"></span>
							</span>
						</p>
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="bank_card"/>：</span>
							<span class="col-xs-7 text-left">
								<span class="accountInfo-account"></span>
							</span>
						</p>
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="email"/>：</span>
							<span class="col-xs-7 text-left">
								<c:choose>
									<c:when test="${email != null && email.length() > 0}">
										${email}
									</c:when>
									<c:otherwise>
										<a class="finishDialog-password" class="finishDialog-password" href="javascript:;">
											<spring:message code="wait_bind"/>
										</a>
									</c:otherwise>
								</c:choose>
							</span>
						</p>
						<p class="col-xs-12">
							<span class="col-xs-5 text-right"><spring:message code="trade_password"/>：</span>
							<span class="col-xs-7 text-left">
								<a class="finishDialog-password" href="javascript:;"><spring:message code="wait_set"/></a>
							</span>
						</p>
					</div>
					<div class="form-group">
						<div class="col-xs-6 col-xs-offset-3">
							<a href="/trade/coin.html" id="finish-all" class="btn btn-danger btn-block"><spring:message code="to_trade_center"/></a>
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
					<span class="modal-title"><spring:message code="lmenu_real_certification"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group text-center">
						<span class="modal-info-tips ">
							<span class=" "></span>
						<!-- 	<span class="text-danger  Certificationtsimg">认证年龄需满18周岁，最高年龄为60周岁</span> -->
						</span>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-realname" class="col-xs-3 control-label"><spring:message code="real_name"/></label>
						<div class="col-xs-6">
							<input id="bindrealinfo-realname" class="form-control" placeholder="${fill_real_name}" type="text" autocomplete="off">
							<span id="bindrealinfo-realname-errortips " class="text-danger certificationinfocolor">*<spring:message code="veri_info_desc"/></span>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-areaCode" class="col-xs-3 control-label"><spring:message code="regional"/></label>
						<div class="col-xs-6">
							<select class="form-control" id="bindrealinfo-address">
								<option value="86" selected>中国大陆(China)</option>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-areaCode" class="col-xs-3 control-label"><spring:message code="card_type"/></label>
						<div class="col-xs-6">
							<select class="form-control" id="bindrealinfo-identitytype">
								<option value="0"><spring:message code="id_card"/></option>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-imgcode" class="col-xs-3 control-label"><spring:message code="card_number"/></label>
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
									<spring:message code="submit_desc"/>
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
							<button id="bindrealinfo-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 注册成功 -->
	<div  class="modal modal-custom fade" id="reg-success" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
				</button>
				<div class="dialog-title">
					<img class="dialog-successImg" src="${oss_url}/static/front/images/user/regist_img_successd@2x.png" >
					<spring:message code="regis_succes"/>
					<h3 class="text-center" style="color:rgb(254,137,104);margin-top:-20px"><spring:message code="integral"/>+500</h3>
				</div>
				<div class="dialog-success-wrap clearfix">
					<div class="modal-body dialog-success-item col-xs-6 form-horizontal pull-left">

						<div class="form-group">
							<img class="col-xs-8 col-xs-offset-2" src="${oss_url}/static/front/images/user/regist_img_xinshou@2x_${locale_language}.png" ></div>
						<div class="form-group">
							<label for="" class="col-xs-3 control-label"></label>
							<div class="col-xs-8 text-center">
								<a href="/about/guide.html" style="width:180px;margin:0 10px;" id="" class="btn btn-danger"><spring:message code="go_now"/></a>
								<a class="dialog-success-switch dialog-success-switch--next pull-right" href="javascript:;"><spring:message code="speed_veri"/>&gt;&gt;</a>
							</div>
						</div>
					</div>
					<div class="modal-body dialog-success-item col-xs-6 form-horizontal pull-left">
						<div class="form-group">
							<img class="col-xs-8 col-xs-offset-2" src="${oss_url}/static/front/images/user/regist_icon_eye_renzheng@2x_${locale_language}.png" ></div>
						<div class="form-group">
							<label for="" class="col-xs-2 control-label"></label>
							<div class="col-xs-8 text-center">
								<a class="dialog-success-switch dialog-success-switch--prev pull-left" href="javascript:;">&lt;&lt;<spring:message code="regise_guide"/></a>
								<a style="width:180px;margin:0 10px;" id="go-bindrealinfo" class="btn btn-danger"><spring:message code="speed_veri"/></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<c:if test="${isQuestionValidate == false}">
	<!-- 安全问题设置 -->
	<div  class="modal modal-custom fade" id="questionSet" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title">安全问题设置</span>
				</div>
				<div class="modal-body form-horizontal questionWrap">
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题1：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question1" id="question1">
									<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案1：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans1" id="answer1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题2：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question2" id="question2">
									<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案2：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans2" id="answer2">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题3：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question3" id="question3">
									<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案3：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans3" id="answer3">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label   class="control-label"></label>
						</div>
						<div class="col-xs-7 text-center">
							<p id="questionSave-errortips" class="text-danger"></p>
							<a class="form-control btn btn-danger submit-question">确认提交</a>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</c:if>
<c:if test="${isQuestionValidate == true}">
	<!-- 安全问题修改 -->
	<div  class="modal modal-custom fade" id="questionUpdate" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title">安全问题修改</span>
				</div>
				<div class="modal-body form-horizontal questionWrap">
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题1：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question1" id="question1">
									<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案1：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans1" id="answer1">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题2：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question2" id="question2">
								<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案2：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans2" id="answer2">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">问题3：</label>
						</div>
						<div class="col-xs-7 text-center">
							<select class="form-control" name="question3" id="question3">
									<option class="default-option" value="" default selected>请选择一个问题</option>
								<c:forEach items="${question_arr}" var="v">
									<option value="${v}">${v}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label class="control-label">答案3：</label>
						</div>
						<div class="col-xs-7 text-center">
							<input class="form-control" type="text" name="ans3" id="answer3">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-4 text-center">
							<label   class="control-label"></label>
						</div>
						<div class="col-xs-7 text-center">
							<p id="questionSave-errortips" class="text-danger"></p>
							<a class="form-control btn btn-danger submit-updataQue">确认提交</a>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</c:if>
	
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



	<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />
	<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
	<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
	<input id="messageType" type="hidden" value="0" />
	<input id="isQuestionValidate" type="hidden" value="${isQuestionValidate}" />





<%@include file="../comm/footer.jsp" %>

<script type="text/javascript">
	var BANKLIST = [];
	var isreg = ${isreg};
	var userInfo = {};

	<c:forEach items="${bankTypes }" var="v">
		BANKLIST.push({data: '${v.key}',value:'${v.value}'});
	</c:forEach>
</script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/jquery.cityselect.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/user.security.js?r=<%=new java.util.Date().getTime() %>"></script>
	<script>
		$(function () {
			$('select').on('change', function () {
				var $this = $(this);
				if ($this.val()) {
					$this.find('.default-option').css('display','block');
				} else {
					$this.find('.default-option').css('display','none');
				}
			});
		})
	</script>
</body>
</html>
