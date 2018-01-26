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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/accountassets.css" type="text/css"></link>
</head>
<body>
	



 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			
			<%@include file="../comm/left_menu.jsp" %>
			
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea assets">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="${type == 0 ? 'active' : ''}">
								<a href="/financial/accountbank.html"><spring:message code="bank_card_mngr"/></a>
							</li>
							<li class="${type == 1 ? 'active' : ''}">
								<a href="/financial/accountbank.html?type=1"><spring:message code="alipay_mngr"/></a>
							</li>
							
							<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/financial/accountcoin.html?symbol=${v.fid }">${v.fShortName } <spring:message code="with_mngr"/></a>
								</li>
							</c:forEach>
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
						<c:if test="${type == 0}">
							<c:forEach items="${bankinfos }" var="v">
								<div class="col-xs-4">
									<div class="bank-item item1">
										<div class="bank-item-top">
											<div class="col-xs-2">
											<i class="banklogo banklogo-item${v.fbankType }"></i>
											</div>
											<div class="col-xs-8">
												<p>${v.fname }</p>
												<p>${v.fbankNumber }</p>
											</div>
											<div class="col-xs-2">
												<span class="bank-item-del" data-fid="${v.fid }"></span>
											</div>
										</div>
										<div class="bank-item-bot">
											<div class="col-xs-12">
												<p>${v.fname },${v.fothers }</p>
												<p>${v.faddress }</p>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>	
							<c:if test="${bankinfos.size() < 5}">


							<div class="col-xs-4">
								<div class="bank-add text-center" data-toggle="modal" data-target="#withdrawCnyAddress">
									<span class="icon"></span>
									<br>
									<span><spring:message code="add_bank"/></span>
								</div>
							</div>
							</c:if>
						</c:if>

						<c:if test="${type == 1}">
							<c:forEach items="${alipaybind_list}" var="v">
								<div class="col-xs-4">
									<div class="bank-item alipay">
										<div class="bank-item-top">
											<div class="col-xs-2">
											<i class="banklogo banklogo-item banklogo-alipay"></i>
											</div>
											<div class="col-xs-8">
												
											</div>
											<div class="col-xs-2">
												<span class="alipay-del" data-account="${v.faccount}"></span>
											</div>
										</div>
										<div class="bank-item-bot" style="border-top:1px solid #fff">
											<div class="col-xs-12">
												<p><spring:message code="alipay_account"/> <span class="pull-right"><spring:message code="not_certified"/></span></p>
												<p>${v.faccount}</p>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>	
							<c:if test="${alipaybind_list.size() < 5}">
								<div class="col-xs-4">
									<div class="bank-add text-center" data-toggle="modal" data-target="#addAlipyDiolog">
										<span class="icon"></span>
										<br>
										<span><spring:message code="add_alipay"/></span>
									</div>
								</div>
							</c:if>
						</c:if>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="withdrawCnyAddress" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="add_bank"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="payeeAddr" class="col-xs-3 control-label" ><spring:message code="open_account_name"/></label>
						<div class="col-xs-8">
							<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly"/>
							<span class="help-block text-danger">*<spring:message code="bank_account_desc"/></span>
						</div>
					</div>
					<div class="form-group ">
						<label for="withdrawAccountAddr" class="col-xs-3 control-label"><spring:message code="bank_card_no"/></label>
						<div class="col-xs-8">
							<input id="withdrawAccountAddr" class="form-control" type="" text>
						</div>
					</div>
					<div class="form-group ">
						<label for="openBankTypeAddr" class="col-xs-3 control-label"><spring:message code="open_card_bank"/></label>
						<div class="col-xs-8">
							<select id="openBankTypeAddr" class="form-control">
								<option value="-1">
									<spring:message code="comm.error.tips.70"/>
								</option>
								<c:forEach items="${bankTypes }" var="v">
									<option value="${v.key }">${v.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="prov_city" class="form-group ">
						<label for="prov" class="col-xs-3 control-label"><spring:message code="bank_account_address"/></label>
						<div class="col-xs-8 ">
							<div class="col-xs-4 padding-right-clear padding-left-clear margin-bottom-15">
								<select id="prov" class="form-control">
								</select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="city" class="form-control">
								</select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="dist" class="form-control prov">
								</select>
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
					
					<c:if test="${isBindTelephone == true }">
						<div class="form-group">
							<label for="addressPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
							<div class="col-xs-8">
								<input id="addressPhoneCode" class="form-control" type="text" autocomplete="off">
								<button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
							</div>
						</div>
					</c:if>	
					
					
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
	<!-- 添加支付宝 -->
	<div class="modal modal-custom fade" id="addAlipyDiolog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title"><spring:message code="bind_alipay"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label  class="col-xs-3 control-label"><spring:message code="name_title"/></label>
						<div class="col-xs-6">
							<input id="alipyname" class="form-control" type="text" autocomplete="off" readonly value="${fuser.frealName }">
							<p style="color:#9e9e9e">*<spring:message code="name_consistent"/></p>
						</div>
					</div>
					<div class="form-group ">
						<label for="alipayAccount" class="col-xs-3 control-label"><spring:message code="alipay_account"/></label>
						<div class="col-xs-6">
							<input id="alipayAccount" class="form-control" type="text" autocomplete="off">
							<p class="text-danger">* <spring:message code="regc_desc18"/></p>
						</div>
					</div>		
					<div class="form-group">
						<label for="alipyerrortips" class="col-xs-3 control-label"></label>
						<div class="col-xs-8">
							<span id="alipyerrortips" class="text-danger"></span>
						</div>
					</div>			
					<div class="form-group">
						<label for="addAlipy-confirm" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<button id="addAlipy-confirm" class="btn btn-danger btn-block"><spring:message code="ok_bind"/></button>
							<p class="addAlipy-errortips text-danger"></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.assets.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js"></script>
</body>
</html>
