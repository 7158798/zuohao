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
<%@include file="../comm/link.inc.market.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/market/market.css" type="text/css"></link>
</head>
<body>
	



<%@include file="../comm/header.jsp" %>
 




	<div class="container-full">
		<div class="container padding-clear">
			<div class="col-xs-12 rightarea market">
				<ul id="marketheader" class="nav nav-tabs rightarea-tabs">
					<a style="margin:12px;font-size:14px" class="pull-right" href="/market_new.html"><spring:message code="professional_edition"/>&gt;&gt;</a>
					<c:forEach items="${ftrademappings }" var="v">
						<li class="${v.fid==symbol?'active':'' }">
						    <c:choose>
						    <c:when test="${v.fid==symbol }">
						    <a href="javascript:void(0)">${v.fvirtualcointypeByFvirtualcointype2.fShortName } / ${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>
						    </c:when>
						    <c:otherwise>
						    <a href="/market.html?symbol=${v.fid }">${v.fvirtualcointypeByFvirtualcointype2.fShortName } / ${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>
						    </c:otherwise>
						    </c:choose>
						</li>
					</c:forEach>	
				</ul>
				<div class="col-xs-12 padding-clear">
					<div class="col-xs-12 market-start padding-clear market-left">
						<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart.html?symbol=${symbol }&themename=dark"></iframe>
						<a class="openfullscreen" id="openfullscreen" href="javascript:void(0)" title="${full_screen}" style="display:block;"></a>
						<a class="closefullscreen" id="closefullscreen" href="javascript:void(0)" title="${exit_full_screen}" style="display:none"></a>
					</div>
					<div class="col-xs-3 padding-right-clear market-right hidden">
						<div class="form-horizontal info-box">
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips"><spring:message code="rmb_balance"/></span>
								<span class="col-xs-5 control-label" id="tradecnymoney"> 0 </span>
								<input id="toptradecnymoney" type="hidden" value="0">
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips"><spring:message code="can_buy"/>ETC</span>
								<span class="col-xs-5 control-label" id="getcoin"> 0 </span>
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips">ETC<spring:message code="balance"/></span>
								<span class="col-xs-5 control-label" id="trademtccoin"> 0 </span>
								<input id="toptrademtccoin" type="hidden" value="0">
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips"><spring:message code="can_sell_rmb"/></span>
								<span class="col-xs-5 control-label" id="getcny"> 0 </span>
							</div>
							<div class="form-group ">
								<span class="col-xs-10 col-xs-offset-1 infotips split"></span>
							</div>
							<div class="form-group "></div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear"><spring:message code="price"/></span>
								<div class="col-xs-8">
									<input id="tradebuyprice" class="form-control" type="text" autocomplete="off" value="9.57">
									<span class="infotips-right">CNY</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear"><spring:message code="number"/></span>
								<div class="col-xs-8">
									<input id="tradebuyamount" class="form-control" type="text" autocomplete="off">
									<span class="infotips-right">ETC</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear"><spring:message code="t_amount"/></span>
								<div class="col-xs-8">
									<span id="tradebuyTurnover" class="form-control"></span>
									<span class="infotips-right">CNY</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips"></span>
								<div class="col-xs-8">
									<span id="errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group ">
								<div class="col-xs-5 col-xs-offset-1">
									<button id="buybtn" class="btn btn-success btn-block"><spring:message code="trade_buy"/></button>
								</div>
								<div class="col-xs-5">
									<button id="sellbtn" class="btn btn-danger btn-block"><spring:message code="trade_sell"/></button>
								</div>
							</div>
						</div>
					</div>
				
				</div>
				<div id="delegateinfo" class="col-xs-12 padding-clear padding-top-30">
					<div class="col-xs-12 padding-clear market-left">
						<div class="col-xs-12 padding-clear list-group-title">
							<div class="col-xs-4">
								<span>
									<spring:message code="entrust_info"/>
								</span>
							</div>
							<div class="col-xs-4 market-col" style="text-align: right;">
								<span class="market-re">
									<%--<span class="market-ab" id="tipopen"></span>--%>
									<div class="market-tip" id="tip">
										<div class="market-tip-top">
											<div class="market-tip-close" id="tipclose">×</div>
										</div>
										<div class="market-text">说明</div>
										<div class="market-detail">选择合并深度后同时影响买入委托列表和卖出委托列表</div>
									</div>
									<spring:message code="concat_depth"/> :
								</span>
								<select name="sel" id="sel">
									<option value="0">0.01</option>
									<option value="1">0.1</option>
									<option value="2">1</option>
								</select>
							</div>
							<div class="col-xs-4">
								<span>
									<spring:message code="new_trade"/>
								</span>
							</div>
						</div>
						<div class="col-xs-4 padding-left-clear">
							<ul id="buybox" class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-2 padding-clear">
										<spring:message code="trade_buy"/>
									</span>
									<span class="col-xs-4 padding-clear text-right">
										<spring:message code="buy_price"/>
									</span>
									<span class="col-xs-6 padding-left-clear text-right">
										<spring:message code="entrust_num"/>
									</span>
								</li>
							</ul>
						</div>
						<div class="col-xs-4 padding-left-clear">
							<ul id="sellbox" class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-2 padding-clear">
										<spring:message code="trade_sell"/>
									</span>
									<span class="col-xs-4 padding-clear text-right">
										<spring:message code="sell_price"/>
									</span>
									<span class="col-xs-6 padding-left-clear text-right">
										<spring:message code="entrust_num"/>
									</span>
								</li>
							</ul>
						</div>
						<div class="col-xs-4 padding-left-clear padding-right-clear">
							<ul class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-4 padding-right-clear">
										<spring:message code="trade_success_time"/>
									</span>
									<span class="col-xs-3 padding-clear text-right">
										<spring:message code="trade_price"/>
									</span>
									<span class="col-xs-5 text-right">
										<spring:message code="success_num"/>
									</span>
								</li>
								<div id="logbox"></div>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


<%@include file="../comm/footer.jsp" %>	



	<input type="hidden" id="coinCount1" value="${ftrademapping.fcount1 }">
	<input type="hidden" id="coinCount2" value="${ftrademapping.fcount2 }">
	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<input type="hidden" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }" id="coinshortName">
	<input id="isopen" type="hidden" value="${isopen }">
	<input id="tradeType" type="hidden" value="${tradeType }">
	<input id="login" type="hidden" value="${login }">
	<input id="tradePassword" type="hidden" value="${tradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">
	<script type="text/javascript" src="${oss_url}/static/front/js/market/markt.js?t=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
