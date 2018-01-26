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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css" type="text/css"></link>
</head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawcny'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawcny'] }">
	


  <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea withdraw">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="active">
								<a href="/account/withdrawCny.html"><spring:message code="rmb_with_desc"/></a>
							</li>
							<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/withdrawBtc.html?symbol=${v.fid }">${v.fShortName } <spring:message code="lmenu_withdrawCny"/></a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="col-xs-8 padding-clear form-horizontal">
							    <div class="form-group ">
									<label for="withdrawAmount" class="col-xs-3 control-label"><spring:message code="account_amount"/></label>
									<div class="col-xs-6">
										<span class="form-control border-fff">￥<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></span>
									</div>
								</div>
								<div class="form-group ">
									<label for="diyMoney" class="col-xs-3 control-label"><spring:message code="withdraw_card"/></label>
									<div class="col-xs-6">
										<select id="withdrawBlank" class="form-control">
											 <c:forEach items="${fbankinfoWithdraws}" var="v">
													<option value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="tail_num"/>${v.fbankNumber }</option>
											</c:forEach>		
										</select>
									<c:if test="${fbankinfoWithdraws.size() < 5}">

										<a href="#" class="text-primary addtips" data-toggle="modal" data-target="#withdrawCnyAddress"><spring:message code="go_add_page"/>>></a>
									</c:if>
									</div>
								</div>
								<div class="form-group ">
									<label for="withdrawBalance" class="col-xs-3 control-label"><spring:message code="withdraw_amount"/></label>
									<div class="col-xs-6">
										<input id="withdrawBalance" class="form-control" type="text" autocomplete="off">
										<span class="amounttips">
											<span>
												<spring:message code="fees_title"/>&nbsp;&nbsp;&nbsp;
												<span id="free">0</span>
												<span>CNY</span>
												<span class="text-danger" id="lessAmount"></span>
											</span>
											<span>
												<spring:message code="real_withdraw_amount"/>
												<span id="amount" class="text-danger">0</span>
												<span class="text-danger">CNY</span>
											</span>
										</span>
									</div>
								</div>
								<div class="form-group ">
									<label for="tradePwd" class="col-xs-3 control-label"><spring:message code="trade_password"/></label>
									<div class="col-xs-6">
										<input autocomplete="new-password" id="tradePwd" class="form-control" type="password" autocomplete="off">
									</div>
								</div>
							
							<c:if test="${isBindTelephone == true }">		
									<div class="form-group">
										<label for="withdrawPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
										<div class="col-xs-6">
											<input id="withdrawPhoneCode" class="form-control" type="text" autocomplete="off">
											<button id="withdrawsendmessage" data-msgtype="4" data-tipsid="withdrawerrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
										</div>
									</div>
							</c:if>		
								
							<c:if test="${isBindGoogle ==true}">	
									<div class="form-group">
										<label for="withdrawTotpCode" class="col-xs-3 control-label">谷歌验证码</label>
										<div class="col-xs-6">
											<input id="withdrawTotpCode" class="form-control" type="text" autocomplete="off">
										</div>
									</div>
							</c:if>		
								
							
								<div class="form-group">
									<label for="withdrawCnyButton" class="col-xs-3 control-label"></label>
									<div class="col-xs-6">
										<button id="withdrawCnyButton" class="btn btn-danger btn-block"><spring:message code="with_desc2"/></button>
									</div>
									<div class="col-xs-3">
										<span style="line-height:30px;white-space: nowrap;" id="withdrawerrortips" class="text-danger">
											
										</span>
									</div>
								</div>
								<div class="form-group kycTip-1" style="display:none">
									<label for="" class="col-xs-3 control-label"></label>
									<div class="col-xs-9">
										如需获取更高额度请参考 <a target="_blank" href="https://www.51szzc.com/about/index.html?id=3">额度说明</a>并 <a target="_blank" href="/user/realCertification.html">前往认证</a>
									</div>
								</div>
								<div class="form-group kycTip-2" style="display:none">
									<label for="" class="col-xs-3 control-label"></label>
									<div class="col-xs-9">
										如需获取更高额度请参考 <a target="_blank" href="https://www.51szzc.com/about/index.html?id=3">额度说明</a>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title"><spring:message code="withdraw_title1"/></span>
									</div>
									<div class="panel-body">
									    <p> ${requestScope.constant['withdrawCNYDesc'] }</p>
										<!--<p>&lt 最低提现金额为${requestScope.constant['minwithdrawcny'] }元。</p>
										<p>&lt 提现时会收取一定的手续费。</p>
										<p>&lt 正常24小时内到账，具体到账时间因收款银行略有不同，节假日到账时间略有延迟。</p>
										<p>&lt 为了您的帐户安全，每次人民币提现的最高限额为￥${requestScope.constant['maxwithdrawcny'] }，如果您有更高的需求，请与网站客服联系。</p>-->
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger"><spring:message code="with_cny_record"/></span>
										<span class="pull-right recordtitle" data-type="0" data-value="0"><spring:message code="div_close"/> -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td>
													<spring:message code="with_time"/>
												</td>
												<td>
													<spring:message code="with_type"/>
												</td>
												<td>
													<spring:message code="with_amount"/>
												</td>
												<td>
													<spring:message code="fees_title"/>
												</td>
												<td>
													<spring:message code="with_account"/>
												</td>
												<td>
													<spring:message code="remark_num"/>
												</td>
												<td>
													<spring:message code="status_title"/>
												</td>
											</tr>
											
											<c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
														<tr>
															<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
															<td><spring:message code="bank_card"/></td>
															<td>￥${v.famount }</td>
															<td>￥${v.ffees }</td>
															<td><spring:message code="open_bank"/>: ${v.fBank }<br>
																<spring:message code="name_title"/>: ${v.fPayee }<br>
																<spring:message code="account"/>:${v.fAccount }</td>
															<td><font color="red">${v.fid }</font></td>
															<td  class="opa-link">
																<span title="">
																<%--<c:choose>
																	<c:when test="${v.fstatus==2}">
																		<span title="'+<spring:message code="with_desc"/>+'">
																	</c:when>
																	<c:otherwise>
																		<span title="">
																	</c:otherwise>
																</c:choose>--%>
																<%--<span title="${v.fstatus==2?'平台已将款汇往您的账户，如暂时没有收到款项，是银行系统延时，请稍作等待。':'' }">--%>
															${v.fstatus_f }
															<c:if test="${v.fstatus==1 }">
															&nbsp;|&nbsp;
															<a class="cancelWithdrawcny opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
															</c:if>
															</span></td>
														</tr>
											</c:forEach>
											<c:if test="${fn:length(fcapitaloperations)==0 }">
													<tr>
														<td colspan="7" class="no-data-tips">
															<span>
																<spring:message code="no_with_desc"/>
															</span>
														</td>
													</tr>
											</c:if>
										</table>
										
										<input type="hidden" value="${cur_page }" name="currentPage" id="currentPage"></input>
											<div class="text-right">
												${pagin }
											</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="withdrawCnyAddress" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" style="display: none;">
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
							<div class="form-group ">
								<label for="payeeAddr" class="col-xs-3 control-label"><spring:message code="name_title"/></label>
								<div class="col-xs-8">
									<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly">			
									<!-- <div class="help-block text-danger tips">			
									*银行卡账号名必须与您的实名认证姓名一致
									<span>[?]</span>
									<div class="tips-area">
										为了响应国家相关政策号召,保障您的资金安全,51数字资产实现实名充值流程，请您务必确保银行充值账号开户姓名与51数字资产认证证件姓名一致，对于非实名充值的资金，我们会在3个工作日内原路退回您的充值银行卡。
									</div>
								</div>
								-->
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
								<input id="dialogPhone" class="form-control" type="text" 
								<c:if test="${not empty fuser.ftelephone}">readonly</c:if>
								value="${fuser.ftelephone}"></div>
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

	<input id="cnyfeeLimit" type="hidden" value="${requestScope.constant['cnyfeeLimit']}">
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	<input id="isbindemali" type="hidden" value="${fuser.fisMailValidate}">
	


<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript">
		var BANKLIST = [];

		<c:forEach items="${bankTypes }" var="v">
			BANKLIST.push({data: '${v.key}',value:'${v.value}'});
		</c:forEach>
	</script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.withdraw.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js"></script>
</body>
</html>

<style>
	.tips {
		position: relative;
		display: inline-block;
	}
	.tips span:hover + .tips-area {
		display: block;
	}
	.tips-area {
		display: none;
		position: absolute;
		left: 100%;
		bottom: 120%;
		margin-left: -30px;
		padding: 15px;
		width: 250px;
		border:1px solid #999;
		color: #333;
		transform: translateY(-10px);
		background: #fff;
	}
	.tips-area::before{
		content: '';
		position: absolute;
		top: 100%;
		left: 10px;
		display: block;
		height: 15px;
		width: 15px;
		background: #fff;
		border-left: 1px solid #999;
		border-bottom: 1px solid #999;
		transform: translateY(-7px) rotate(-45deg);
	}
</style>
