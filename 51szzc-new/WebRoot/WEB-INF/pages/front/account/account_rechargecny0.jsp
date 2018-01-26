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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/recharge.css" type="text/css"></link>

</head>
<body class="${locale_language}">
	




 
 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			
<%@include file="../comm/left_menu.jsp" %>
			<div class="modal modal-custom fade" id="addBankDialog" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" style="display: none;">
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
									<input id="address" class="form-control" type="text" autocomplete="off" placeholder="${bank_account_address1}" />
								</div>
							</div>
						</div>
						<div class="form-group ">
							<label for="dialogPhone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
							<div class="col-xs-8">
								<input id="dialogPhone" class="form-control" type="text" <c:if test="${not empty fuser.ftelephone}">readonly</c:if> value="${fuser.ftelephone}"></div>
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

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea recharge">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="active">
								<a href="/account/rechargeCny.html?type=1"><spring:message code="cny_regc"/></a>
							</li>
							<c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fShortName } <spring:message code="lmenu_rechargeCny"/></a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-40">
							<a class="recharge-type alipay" href="/account/rechargeCny.html?type=3"> </a>
							<a class="recharge-type wechat" href="/account/rechargeCny.html?type=4" style="display: none;"></a>
							<a class="recharge-type bank active" href="javascript:;"></a>
						</div>
						
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="recharge-box clearfix padding-top-30">
								<span class="recharge-process">
									<span id="rechargeprocess1" class="col-xs-6 active">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">1</span>
										<span class="recharge-process-text"><spring:message code="regc_amt_title1"/></span>
									</span>
									<span id="rechargeprocess2" class="col-xs-6">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">2</span>
										<span class="recharge-process-text"><spring:message code="regc_amt_title2"/></span>
									</span>
									<!-- <span id="rechargeprocess4" class="col-xs-3">
										<span class="recharge-process-line"></span>
										<span class="recharge-process-icon">3</span>
										<span class="recharge-process-text">完成汇款</span>
									</span> -->
								</span>
								<div class="col-xs-12 padding-clear padding-top-30">
									<div id="rechage1" class="col-xs-7 padding-clear form-horizontal">
										<div class="form-group">
											<label  class="col-xs-3 control-label"><spring:message code="regc_user_name"/></label>
											<div class="col-xs-6">
												<label class="col-xs-12 control-label text-left">${fuser.frealName }</label>
											</div>
										</div>
										<div class="form-group">
											<label  class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
											<div class="col-xs-6">
												<label class="col-xs-3 control-label">${fuser.ftelephone}</label>
											</div>
										</div>
										<div class="form-group ">
											<label for="bank" class="col-xs-3 control-label"><spring:message code="choose_bank_card"/></label>
											<div class="col-xs-6">
												<select  id="sbank" class="form-control">
													<c:forEach items="${fbankinfoWithdraws}" var="v">
														<option data-bank="${v.fname }" data-card="${v.fbankNumber }" value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="tail_num"/>${v.fbankNumber }</option>
													</c:forEach>
												</select>
											<c:if test="${fbankinfoWithdraws.size() < 5}">
												<label class="addBankTips control-label pull-right" for=""><a href="#" class="text-primary" data-toggle="modal" data-target="#addBankDialog"><spring:message code="go_add_page"/>&gt;&gt;</a></label>
											</c:if>
											</div>
										</div>
										<div class="form-group ">
											<label for="diyMoney" class="col-xs-3 control-label"><spring:message code="regc_amt"/></label>
											<div class="col-xs-6">
												<input id="diyMoney" class="form-control" type="text" autocomplete="off">
												<input type="hidden" value="0.${randomMoney }" id="random">
												<label for="diyMoney" class="control-label randomtips">.${randomMoney }</label>
											</div>
										</div>
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-6">
												<button id="rechargebtn" class="btn btn-danger btn-block"><spring:message code="regc_create_order"/></button>
												<p id="rechargebtn-erorr" class="text-danger"></p>
											</div>
										</div>
									</div>
									<div id="rechage2" class="col-xs-6 padding-clear form-horizontal" style="display:none;">
										<div class="form-group">
											<span><spring:message code="regc_desc2"/></span>
										</div>
										<div class="form-group">
										<div class="recharge-infobox">
												<div class="form-group">
													<span><spring:message code="regc_desc3"/>：</span> <span id="fownerName">xx</span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc4"/>：</span> <span id="fbankNumber">--</span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc5"/>：</span> <span id="fbankAddress">--</span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc6"/>：</span> <span id="bankMoney" class="text-danger font-size-16">--</span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc7"/>：</span> <span id="bankInfo" class="text-danger font-size-16">--</span>
												</div>
												<div class="form-group">
													<span class="control-label text-left text-danger rechage-tips margin-bottom-clear" style="border-top-color:#fff0e4;line-height: 18px;padding-left: 0;display: inline-block;">
													<i class="icon"></i> <spring:message code="regc_desc8"/> <span id="bankInfotips">--</span>

													</span>
													<div class="tips text-danger" style="position:absolute">
														*<spring:message code="regc_desc9"/>
													</div>
												</div>
												<div style="margin:30px -15px 10px; height:4px;background:#9cc2fc"></div>
												<div class="form-group">
													<span><spring:message code="regc_user_name"/>：</span>
													<span>${fuser.frealName }</span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc10"/>：</span>
													<span class="target-bank"></span>
												</div>
												<div class="form-group">
													<span><spring:message code="regc_desc11"/>：</span>
													<span class="target-card"></span>
												</div>
											</div>
										 </div>	
										<div class="form-group">
										<label for="diyMoney" class="col-xs-3 control-label"></label>
											<div class="col-xs-6 padding-left-clear">
												<!-- <button id="rechargenextbtn" class="btn btn-danger btn-block">我已完成充值，下一步</button> -->
											</div>
										</div>
									</div>
									<div id="rechage3" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
										<div class="form-group ">
											<label for="fromBank" class="col-xs-3 control-label"><spring:message code="regc_desc12"/></label>
											<div class="col-xs-6">
												<select id="fromBank" class="form-control">
													<option value="-1">
														<spring:message code="comm.error.tips.70"/>
													</option>
													<c:forEach items="${bankTypes }" var="v">
														<option value="${v.key }">${v.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group ">
											<label for="fromAccount" class="col-xs-3 control-label"><spring:message code="regc_desc13"/></label>
											<div class="col-xs-6">
												<input id="fromAccount" class="form-control" type="text" autocomplete="off">
											</div>
										</div>
										<div class="form-group ">
											<label for="fromPayee" class="col-xs-3 control-label"><spring:message code="regc_desc14"/></label>
											<div class="col-xs-6">
												<input id="fromPayee" class="form-control" type="text" autocomplete="off">
											</div>
										</div>
										<div class="form-group ">
											<label for="fromPhone" class="col-xs-3 control-label">手机号码</label>
											<div class="col-xs-6">
												<input id="fromPhone" class="form-control" type="text" autocomplete="off" value="">
											</div>
										</div>
										<div class="form-group">
											<label for="rechargesuccessbtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-6">
												<button id="rechargesuccessbtn" class="btn btn-danger btn-block">提交</button>
											</div>
										</div>
									</div>
									<div id="rechage4" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
										<span class="rechare-success">
											<span class="success-icon"><spring:message code="submit_success"/></span>
											<a href="/account/rechargeCny.html?type=0"><spring:message code="go_on_regc"/> >></a>
											<p><spring:message code="regc_desc"/></p>
										</span>
									</div>
									<div class="col-xs-5 padding-clear text-center">
										<a target="_blank"  href="/about/index.html?id=11&type=7" class="recharge-help"> </a>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title"><spring:message code="regc_amt_title3"/></span>
									</div>
									<div class="panel-body">
									    <p> ${requestScope.constant['rechageBankDesc'] }</p>
										<!--
										<p>&lt 工作时间平台确认汇款信息后30分钟内入账</p>
										<p>&lt 目前仅支持 ${minRecharge }元以上汇款, ${minRecharge }元以下汇款不予处理。</p>-->
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger"><spring:message code="regc_amt_record"/></span>
										<span class="pull-right recordtitle" data-type="0" data-value="0"><spring:message code="comm.error.tips.48"/> -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td><spring:message code="order_no"/></td>
												<td><spring:message code="regc_time"/></td>
												<td><spring:message code="regc_type"/></td>
												<td><spring:message code="regc_amount"/></td>
												<td><spring:message code="regc_status"/></td>
												<td><spring:message code="operation_title"/></td>
											</tr>
											 <c:forEach items="${list}" var="v">
													<tr>
														<td class="gray">${v.fid }</td>
														<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td>${v.fremittanceType }</td>
														<td>${v.famount }</td>
														<td>${v.fstatus_f }</td>
														<td class="opa-link">
														<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
															<c:choose>
																<c:when test="${v.fremittanceType == '支付宝转账' && v.fPayee !='' && v.fPayee.length()>0}">
																	--
																</c:when>
																<c:otherwise>
																	<a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
																</c:otherwise>
															</c:choose>
															<c:if test="${v.fstatus==2 && v.fremittanceType != '支付宝转账'}">
																&nbsp;|&nbsp;&nbsp;<a class="rechargelook opa-link" href="javascript:void(0);" data-fid="${v.fid }">查看</a>
															</c:if>
														<c:if test="${v.fstatus==1}">
														&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link" href="javascript:void(0);" data-fid="${v.fid }">提交充值</a>
														</c:if>
														</c:if>
														<c:if test="${(v.fstatus==3 || v.fstatus==4)}">
														--
														</c:if>
														</td>
									                 </tr>
									          </c:forEach>
											  <c:if test="${fn:length(list)==0 }">
												<tr>
													<td colspan="6" class="no-data-tips" align="center">
														<span>
															<spring:message code="no_regc_data"/>
														</span>
													</td>
												</tr>
											  </c:if>
										</table>
										
											
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
	<input type="hidden" value="${currentPage }" name="currentPage" id="currentPage">
	<input type="hidden" value="${type }" name="finType" id="finType">
	<input id="minRecharge" value="${minRecharge }" type="hidden">
	<input type="hidden" value="0" name="desc" id="desc">

 <%@include file="recharge_view.jsp" %>

<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript">
	var BANKLIST = [];

	<c:forEach items="${bankTypes }" var="v">
		BANKLIST.push({data: '${v.key}',value:'${v.value}'});
	</c:forEach>
</script>

<script type="text/javascript" src="/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="/static/front/js/finance/city.min.js"></script>
<script type="text/javascript" src="/static/front/js/finance/jquery.cityselect.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>

