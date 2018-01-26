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
			
			<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
					<c:if test="${fuser.fstatus == 3 }">
						<div class="user-realCertification-top-icon wran ">
								<h3 class="text-danger"><spring:message code="account_risk"/></h3>
								<a data-toggle="modal" data-target="#updateId" class="user-realCertification-update warn btn"><spring:message code="upgrade_immediately"/></a>
						</div>
					</c:if>

					<c:if test="${fuser.fstatus == 5 }">
						<div class="user-realCertification-top-icon error  ">
								<h3 class="text-danger"><spring:message code="audit_not_passed"/></h3>
								<p class="text-danger"><spring:message code="cert_desc"/> <a href="javascript:;" data-toggle="modal" data-target="#idExample"><spring:message code="view_example"/></a></p>
								<a data-toggle="modal" data-target="#updateId" class="user-realCertification-update btn"><spring:message code="re_certification"/></a>
						</div>
					</c:if>
					<c:if test="${fuser.fstatus == 4 }">
						<div class="user-realCertification-top-icon audit ">
								<h3><spring:message code="audit"/></h3>
								<p><spring:message code="upgrade_cert"/></p>
						</div>
					</c:if>
					<c:if test="${!fuser.fhasRealValidate}" >
						<div class="user-realCertification-top-icon user-realCerification-top-icon-no ">

							<h3>
								<spring:message code="no_cert"/>
								<a class="text-primary text-link" href="#" data-toggle="modal" data-target="#bindrealinfo"><spring:message code="cert_id"/></a>
							</h3>
							<h5><spring:message code="real_certification_title3"/></h5>

						</div>
					</c:if>
					

						<div class="col-xs-12 padding-clear padding-top-30 ">
							<div class="panel">
								<div class="panel-body padding-left-clear padding-right-clear ">
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
											<c:if test="${fuser.fstatus == 1 && fuser.fIdentityUrlOn && fuser.fIdentityUrlOn}">
												<tr class="table-tr-renzheng">
													<td class="col-xs-1 user-tr">
														<i class="iconlist userid"></i>
														<spring:message code="card_info"/>：
													</td>
													<td colspan="" class="col-xs-8 user-tr">
														<div class="col-xs-4 text-center">
															<img  src="${oss_url}/static/front/images/user/personal_upload_modo_01@2x.png" >
															<p><spring:message code="positive_photo"/></p>
														</div>
														<div class="col-xs-4 text-center">
															<img  src="${oss_url}/static/front/images/user/personal_upload_modo_01@2x.png" >
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

		<!-- 提交成功 -->
		<div class="modal modal-custom fade" id="idsuccess" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document" >
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header" style="background: none">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body form-horizontal">
						<div class="container-fluid">
							<div class="row text-center">
								<img style="height:45px;width:42px" src="${oss_url}/static/front/images/user/pic_icon@2x.jpg" alt="">
							</div>
							<div class="row">
								<p style="color:#278BFF;font-size:16px"><spring:message code="submit_success"/></p>
							</div>
							<div class="row">
								<p><spring:message code="submit_desc1"/></p>
							</div>
							<div class="row">
								<p><spring:message code="customer_service"/>：400-990-6615</p>
							</div>
							<div class="row">
								<button class=""><spring:message code="comm.error.tips.38"/></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 身份证示例 -->
		<div class="modal modal-custom fade" id="idExample" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document" style="width:800px !important">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header" style="background: none">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body form-horizontal">
						<div class="container-fluid">
							<div class="row">
								<img class="col-xs-12" src="${oss_url}/static/front/images/user/person-renzhegn-shili-f@2x_${locale_language}.jpg" alt="">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		

		
		<!-- 身份证更新 -->
		<div class="modal modal-custom fade" id="updateId" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document" style>
				<div class="modal-mark"></div>
				<div class="imgDetail1">
					<button type="button" class="close btn-modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<img src="${oss_url}/static/front/images/user/personal_upload_modo_01@2x.png" alt="">
				</div>
				<div class="imgDetail2">
					<button type="button" class="close btn-modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<img src="${oss_url}/static/front/images/user/personal_upload_modo_02@2x.png" alt="">
				</div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title">实名认证升级</span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="container-fluid">
							<div class="form-group ">
								<label class="col-xs-3 control-label">手持身份证照片：</label>
								<div class="col-xs-8">
									<form action="" target="framFile" enctype="multipart/form-data" class="updateIdPic col-xs-5 text-center">
										<label for="img1">
											<img src="${oss_url}/static/front/images/user/personal_upload_add@2x.png" alt="">
											<p>上传正面照片</p>
										</label>
										<input accept="image/jpg,image/bmp,image/png" onchange="uploadImg(0)" id="img1" name="filedata"  type="file">
									</form>
									<form class="updateIdPic col-xs-5 col-xs-offset-1 text-center">
										<label for="img2">
											<img accept="image/jpg,image/bmp,image/png" src="${oss_url}/static/front/images/user/personal_upload_add@2x.png" alt="">
											<p>上传反面照片</p>
										</label>
										<input onchange="uploadImg(1)" id="img2" name="filedata"   type="file">
									</form>
									<div class="col-xs-12 help-block text-danger tips">
										*仅支持jpg、bmp、png格式，大小限制2M以内。
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="addressPhoneCode" class="col-xs-3 control-label">短信验证码</label>
								<div class="col-xs-8">
									<input id="idPhoneCode" class="form-control" type="text" autocomplete="off">							
									<button id="bindsendmessage" data-msgtype="16" data-tipsid="binderrortips" class="btn btn-sendmsg">发送验证码</button>
								</div>
							</div>
							<div class="form-group">
								<label for="binderrortips" class="col-xs-3 control-label"></label>
								<div class="col-xs-8">
									<span id="binderrortips" class="text-danger"></span>
								</div>
							</div>
							<input type="hidden" id="updateIdPic-back">
							<input type="hidden" id="updateIdPic-front">
							<div class="form-group">
								<span id="binderrortips" class="col-xs-12 text-danger"></span>
								<a class="col-xs-2 col-xs-offset-5 btn user-id-submit " href="javascript:;">提交</a>
							</div>
							<div class="row">
								<label class="col-xs-12" for="">样例 <small>（点击查看大图）：</small></label>
								<div class="col-xs-4"></div>
								<div class="col-xs-4 row text-center">
									<a class="imgExample1" href="javascript:;">
										<img class="col-xs-12" src="${oss_url}/static/front/images/user/personal_upload_modo_01@2x.png" >
									</a>
									<p>正面照</p>
								</div>
								<div class="col-xs-4 row text-center">
									<a class="imgExample2" href="javascript:;">
										<img class="col-xs-12" src="${oss_url}/static/front/images/user/personal_upload_modo_02@2x.png" >
									</a>
									<p>反面照</p>
								</div>
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
