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

							<a class="recharge-type alipay active" href="javascript:;"> </a>
							<a class="recharge-type wechat" href="/account/rechargeCny.html?type=4" style="display: none;"></a>
							<a class="recharge-type bank " href="/account/rechargeCny.html?type=0"></a>
						</div>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="recharge-box clearfix ">
								<div class="col-xs-12 padding-clear padding-top-30">
									<div id="rechage1" class="col-xs-7 padding-clear form-horizontal">
									<c:if test="${alipaybind_list.size() == 0}">
										<div class="form-group">
											<div class="col-xs-1"></div>
											<div class="col-xs-4">
												<p class="text-danger"><spring:message code="un_bind_alipay"/></p>
												<button id="addAlipayBtn" class="btn" href="javascript:;" data-toggle="modal" data-target="#addAlipyDiolog">+<spring:message code="bind_alipay"/></button>
											</div>
										</div>
									</c:if>
									<c:if test="${alipaybind_list.size() > 0}">
										<div class="form-group">
											<label for="sbank" class="col-xs-3 control-label"><spring:message code="alipay_account"/></label>
											<div class="col-xs-6">
												<select class="form-control" name="accounts" id="accounts">
												<c:forEach items="${alipaybind_list}" var="v">
													<option value="${v.faccount}">${v.faccount}</option>
												</c:forEach>
												</select>

											<c:if test="${alipaybind_list.size() < 5}">
												<a data-toggle="modal" data-target="#addAlipyDiolog" class="add-alipay" href="javasript:;"><spring:message code="new_add_account"/>&gt;&gt;</a>
											</c:if>
											</div>
											<div class="col-xs-1"></div>
										</div>
										<div class="form-group ">
											<label for="diyMoney" class="col-xs-3 control-label"><spring:message code="transfer_amount"/></label>
											<div class="col-xs-6">
												<input id="diyMoney" class="form-control" type="text" autocomplete="off">
												<input type="hidden" value="0.${randomMoney }" id="random">
												<label for="diyMoney" class="control-label randomtips">.${randomMoney }</label>
											</div>
										</div>
										<%--<div class="form-group">
											<label for="" class="col-xs-3 control-label">到账时间</label>
											<div class="col-xs-7">
												<span class="finishTime">20分钟内</span>
											</div>
										</div>--%>
										
										
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-7">
												<span class="text-danger" id="errortips"></span>
											</div>
										</div>
										<div class="form-group">
											<label for="rechargebtn" class="col-xs-3 control-label"></label>
											<div class="col-xs-7">
												<button id="qrcoderechargebtn" class="btn btn-danger btn-block"><spring:message code="regc_create_order"/></button>
											</div>
										</div>
									</c:if>
									</div>
									<div class="col-xs-5 padding-clear text-center">
										<a target="_blank" href="/about/index.html?id=11&type=7" class="recharge-help"> </a>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30 padding-bottom-30">
								<div class="panel panel-tips">
									<div class="panel-header text-center text-danger">
										<span class="panel-title"><spring:message code="regc_amt_title3"/></span>
									</div>
									<div class="panel-body">
									    <p> ${requestScope.constant['rechageZFBDesc'] }</p>
										<!--<p>&lt 工作时间平台确认汇款信息后30分钟内入账</p>
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
															<c:choose>
																<c:when test="${v.fremittanceType == '支付宝转账' && v.fPayee !='' && v.fPayee.length()>0}">
																	--
																</c:when>
																<c:otherwise>
																	<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
																		<a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
																		<c:if test="${v.fstatus==2 && v.fremittanceType != '支付宝转账'}">
																			&nbsp;|&nbsp;&nbsp;<a class="rechargelook opa-link" href="javascript:void(0);" data-fid="${v.fid }">查看</a>
																		</c:if>
																	</c:if>
																	<c:if test="${(v.fstatus==3 || v.fstatus==4)}">
																		--
																	</c:if>
																</c:otherwise>
															</c:choose>
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

	<!-- 汇款单 -->
	<div class="modal modal-custom fade" id="orderDiolog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title"><spring:message code="alipay_transfer_orders"/></span>
				</div>
				<div class="order">
					<div class="orderInfo clearfix">
						<div class="orderInfo-left pull-left text-center">
							<h3>1，<spring:message code="please_phone_pay"/></h3>
							<p style="margin-bottom:30px"><spring:message code="please_to"/> <a id="ali_form_pay_a" style="cursor: pointer;"><spring:message code="page_alipay"/></a> <spring:message code="comple_pay"/></p>
							<p class="text-danger">
								<spring:message code="transfer_desc"/>
							</p>
						</div>
						<div class="orderInfo-right pull-right">
							<iframe id="qrcode_iframe">


							</iframe>
						</div>
					</div>
					<div class="orderInfo clearfix">
						<div class="orderInfo-left pull-left text-left">
							<h3>2，<spring:message code="order_pay"/></h3>
							<p>
								<span><spring:message code="receipt_account"/>：</span>
								 <span id="ali_account"></span>
							</p>
							<p>
								<span><spring:message code="receiving_unit"/>：</span>
								<span id="company"></span>
							</p>
							<p>
								<span><spring:message code="transfer_amount"/>：</span>
								<span class="order-money text-danger"></span>CNY
							</p>
							<p>
								<span><spring:message code="day_audit_time"/>：</span>
								<span class="text-danger">9:00—19:00</span>
							</p>
							<p>
								<span><spring:message code="arrive_at_time"/>：</span>
								<span class="text-danger"><spring:message code="audit_30_m"/></span>
							</p>
						</div>
					</div>
				</div>
				<div class="orderInfo-footer text-center">
					* <spring:message code="please_customer_service"/> <a onclick="js_method()" href="javascript:;"><spring:message code="customer_service1"/></a>
				</div>
			</div>
		</div>
	</div>

<div id="alipay_form_div">

</div>



 <%@include file="recharge_view.jsp" %>

 <input type="hidden" value="${type }" name="finType" id="finType"></input>
 <input id="minRecharge" value="${minRecharge }" type="hidden">
<!-- 支付宝充值金额上下限 -->
 <input id="minalipayrgc" value="${minalipayrgc }" type="hidden">
 <input id="maxalipayrgc" value="${maxalipayrgc }" type="hidden">
 <!-- 支付宝充值次数 -->
 <input id="alipayrgcnum" value="${alipayrgcnum }" type="hidden">
 <input id="user_alipay_num" value="${user_alipay_num }" type="hidden">

 <input type="hidden" value="0" name="desc" id="desc"></input>
 <input type="hidden" id="refreshid" value="0">
 <%@include file="../comm/footer.jsp" %>
 <script type="text/javascript" src="/static/front/js/plugin/jquery.qrcode.min.js"></script>
 <script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js"></script>

<script>
    (function(m, ei, q, i, a, j, s) {
        m[a] = m[a] || function() {
                (m[a].a = m[a].a || []).push(arguments)
            };
        j = ei.createElement(q),
            s = ei.getElementsByTagName(q)[0];
        j.async = true;
        j.charset = 'UTF-8';
        j.src = i + '?v=' + new Date().getUTCDate();
        s.parentNode.insertBefore(j, s);
    })(window, document, 'script', '//eco-api.meiqia.com/dist/meiqia.js', '_MEIQIA');
    _MEIQIA('entId', 8913);
    _MEIQIA('withoutBtn', true);

    function js_method() {
        _MEIQIA._SHOWPANEL();
    }

</script>

</body>
</html>
