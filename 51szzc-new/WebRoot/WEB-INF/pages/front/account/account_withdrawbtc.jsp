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
	<input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
	<input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">


 <%@include file="../comm/header.jsp" %>

	<div class="container-full">
		<div class="container displayFlex">
			
<%@include file="../comm/left_menu.jsp" %>

			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea withdraw">
					<div class="col-xs-12 rightarea-con">
						<ul class="nav nav-tabs rightarea-tabs">
							<li class="">
								<a href="/account/withdrawCny.html"><spring:message code="rmb_with_desc"/></a>
							</li>
							<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
								<li class="${v.fid==symbol?'active':'' }">
									<a href="/account/withdrawBtc.html?symbol=${v.fid }">${v.fShortName } <spring:message code="lmenu_withdrawCny"/></a>
								</li>
							</c:forEach>
							
						</ul>
						<div class="col-xs-12 padding-clear padding-top-30">
							<div class="col-xs-7 padding-clear form-horizontal">
								<div class="form-group ">
									<label for="withdrawAmount" class="col-xs-3 control-label"><spring:message code="account_amount"/></label>
									<div class="col-xs-6">
										<span class="form-control border-fff"><ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
									</div>
								</div>
								<div class="form-group ">
									<label for="withdrawAddr" class="col-xs-3 control-label"><spring:message code="btc_withdraw_address"/></label>
									<div class="col-xs-9">
										<select id="withdrawAddr" class="form-control">
											<c:forEach items="${fvirtualaddressWithdraws }" var="v">
												<option value="${v.fid }">${v.fremark}-${v.fadderess }</option>
											</c:forEach>
										</select>
										<a href="#" class="text-primary addtips" data-toggle="modal" data-target="#address"><spring:message code="go_add_page"/>>></a>
									</div>
								</div>
								<div class="form-group ">
									<label for="withdrawAmount" class="col-xs-3 control-label"><spring:message code="withdraw_num"/></label>
									<div class="col-xs-6">
										<input id="withdrawAmount" class="form-control" type="text" autocomplete="off">
									</div>
								</div>
								<div class="form-group ">
										<label for="btcfee" class="col-xs-3 control-label">${fvirtualcointype.fShortName }<spring:message code="network_fee"/></label>
										<div class="col-xs-6">
											<select id="btcfee" class="form-control">
											<c:forEach items="${withdrawFees }" var="v">
											<c:if test="${v.flevel ==5 }">
											<option value="${v.flevel}" selected="selected"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
											</c:if>
											<c:if test="${v.flevel !=5 }">
											<option value="${v.flevel}"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
											</c:if>
											</c:forEach>
											</select>
											<c:if test="${fvirtualcointype.fdescription != null && fvirtualcointype.fdescription != ''}">
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p class="text-danger" style="position: absolute;width: 443px;top:35px;">${fvirtualcointype.fdescription }</p>
											</c:if>
										</div>
									</div>
								<div class="form-group ">
									<label for="password" class="col-xs-3 control-label"><spring:message code="trade_password"/></label>
									<div class="col-xs-6">
										<input autocomplete="new-password" id="tradePwd" class="form-control" type="password" autocomplete="off">
									</div>
								</div>
							
							
									<div class="form-group">
										<label for="withdrawPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
										<div class="col-xs-6">
											<input id="withdrawPhoneCode" class="form-control" type="text" autocomplete="off">
											<button id="withdrawsendmessage" data-msgtype="5" data-tipsid="withdrawerrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
										</div>
									</div>
							
								
								<c:if test="${isBindGoogle ==true}">
									<div class="form-group">
										<label for="withdrawTotpCode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
										<div class="col-xs-6">
											<input id="withdrawTotpCode" class="form-control" type="text" autocomplete="off">
										</div>
									</div>
								</c:if>	
								

								<div class="form-group">
									<label for="diyMoney" class="col-xs-3 control-label"></label>
									<div class="col-xs-6">
										<button id="withdrawBtcButton" class="btn btn-danger btn-block"><spring:message code="with_desc2"/></button>
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
									    <p> ${requestScope.constant['withdrawCOINDesc'] }</p>
										<%--<p>&lt 最小提币数量为${fvirtualcointype.fminqty }个,最大提币数量为${fvirtualcointype.fmaxqty }个。</p>--%>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30">
								<div class="panel border">
									<div class="panel-heading">
										<span class="text-danger">${fvirtualcointype.fname }<spring:message code="with_record"/></span>
										<span class="pull-right recordtitle" data-type="0" data-value="0"><spring:message code="div_close"/> -</span>
									</div>
									<div  id="recordbody0" class="panel-body">
										<table class="table">
											<tr>
												<td>
													<spring:message code="with_time"/>
												</td>
												<td>
													<spring:message code="btc_withdraw_address"/>
												</td>
												<td>
													<spring:message code="withdraw_num"/>
												</td>
												<td>
													<spring:message code="fees_title"/>
												</td>
												<td>
													<spring:message code="with_status"/>
												</td>
												<td>
													<spring:message code="remark_num"/>
												</td>
											</tr>
											
						<c:forEach items="${fvirtualcaptualoperations }" varStatus="vs" var="v">
							<tr>
								<td width="170"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td width="300">${v.withdraw_virtual_address }
								<br/>
								<c:choose>
									<c:when test="${fvirtualcointype.fid==1 }">
										<a href="http://qukuai.com/search/address/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
									<c:when test="${fvirtualcointype.fid==3 }">
										<a href="http://qukuai.com/search/ltc/address/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
									<c:when test="${fvirtualcointype.fid==4 }">
										<a href="https://etherscan.io/address/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
									<c:when test="${fvirtualcointype.fShortName=='ANS' }">
										<a href="http://antcha.in/address/info/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
									<c:when test="${fvirtualcointype.fShortName=='ZEC' }">
										<a href="https://explorer.zcha.in/accounts/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
									<c:when test="${fvirtualcointype.fShortName=='ETC' }">
										<a href="https://block.bitbank.com/address/etc/${v.withdraw_virtual_address }" target="blank"><spring:message code="view_desc"/></a>
									</c:when>
								</c:choose>
								</td>
								<td width="120"><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>${fvirtualcointype.fSymbol}</td>
								<td width="120"><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/>${fvirtualcointype.fSymbol}</td>
								<td width="180"  class="opa-link">${v.fstatus_f }
									<c:if test="${v.fstatus==1 }">
									&nbsp;|&nbsp;
										<a class="cancelWithdrawBtc opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="cancel_desc1"/></a>
									</c:if>
									<c:if test="${v.fstatus==5 }">
										/ <a data-id="${v.fid }" class="sendAgain-btn" href="javascript:;">再次获取</a>
									</c:if>
								</td>
								<td width="100">
									${v.fid }
								</td>
							</tr>
						</c:forEach>
						
						<c:if test="${fn:length(fvirtualcaptualoperations)==0 }">
								<tr>
									<td colspan="6" class="no-data-tips">
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
	<div class="modal modal-custom fade" id="address" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="btc_withdraw_address"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="withdrawBtcAddr" class="col-xs-3 control-label"><spring:message code="btc_withdraw_address"/></label>
						<div class="col-xs-8">
							<input id="withdrawBtcAddr" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="withdrawBtcRemark" class="col-xs-3 control-label"><spring:message code="remark"/></label>
						<div class="col-xs-8">
							<input id="withdrawBtcRemark" class="form-control" type="" text>
						</div>
					</div>
					<div class="form-group ">
						<label for="withdrawBtcPass" class="col-xs-3 control-label"><spring:message code="trade_password"/></label>
						<div class="col-xs-8">
							<input id="withdrawBtcPass" class="form-control" type="password" autocomplete="off">
						</div>
					</div>
					
					<c:if test="${isBindTelephone == true }">
						<div class="form-group">
							<label for="withdrawBtcAddrPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
							<div class="col-xs-8">
								<input id="withdrawBtcAddrPhoneCode" class="form-control" type="text" autocomplete="off">
								<button id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
							</div>
						</div>
					</c:if>	
					
					
					<div class="form-group">
						<label for="diyMoney" class="col-xs-3 control-label"></label>
						<div class="col-xs-8">
							<span id="binderrortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="diyMoney" class="col-xs-3 control-label"></label>
						<div class="col-xs-8">
							<button id="withdrawBtcAddrBtn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div  class="modal modal-custom fade" id="addEmailDialog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title">温馨提醒</span>
				</div>
				<div class="modal-body form-horizontal" style="padding:40px 80px;">
					<div>为了进一步保障资金安全，加快提现到账速度，推荐您通过邮箱验证的方式进行提现</div>
					<div class="text-center" style="margin-top:60px">
						<a data-dismiss="modal" aria-label="Close" style="margin-right:20px;font-size:14px;line-height:26px;border-radius: 2px;width:170px;height:36px;color:#fff;background:#C7C7C7" class="unBindEmail-btn btn" href="javascript:;">跳过</a>
						<a style="font-size:14px;line-height:26px;border-radius: 2px;width:170px;height:36px;color:#fff;background:#2A85FF" class="bindEmail-btn btn" href="/user/security.html">绑定</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div  class="modal modal-custom fade" id="sendEmailDialog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title">提交成功</span>
				</div>
				<div class="modal-body form-horizontal" style="padding:40px 80px;">
					<div class="font-size-14">验证邮件已发送至您的 <span style="color: #003700;" id="email-show-span"></span>邮箱，请点击邮件中的链接完成提现申请</div>
					<div class="text-center" style="margin-top:60px">
						<a onclick="document.location.reload(true)" style="font-size:14px;line-height:26px;border-radius: 2px;width:170px;height:36px;color:#fff;background:#2A85FF" class="bindEmail-btn btn" >确定</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">
	<input id="isbindemali" type="hidden" value="${fuser.fisMailValidate}">

 
<%@include file="../comm/footer.jsp" %>	
	<script>
		var BANKLIST = []
	</script>
		<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.withdraw.js"></script>
</body>
</html>
