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

<link rel="stylesheet" href="${oss_url}/static/front/css/market/market.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>


<style>
	html,body {
		overflow: hidden !important;
		background: rgb(35, 35, 35);
		height: 100%;
	}	
</style>

</head>
<body> 

<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?bc2df6bdc3696056c26c7406a98b6570";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>


<div class="marketNew">
	
	<div class="marketNew-header clearfix">
		<a href="/">
			<img class="marketNew-logo pull-left" src="${oss_url}/static/front/images/market/logo_icon.png" alt="logo">
		</a>
		<ul class="marketNew-navs pull-left list-inline">
		  <!-- <li class="marketNew-nav active">BTC/CNY 行情</li>
		  <li class="marketNew-nav">LTC/CNY 行情</li>
		  <li class="marketNew-nav">ETC/CNY 行情</li> -->
		  <c:forEach items="${ftrademappings }" var="v">
		  	<li class="marketNew-nav ${v.fid==symbol?'active':'' }">
		  	    <c:choose>
		  	    <c:when test="${v.fid==symbol }">
		  	    <a href="javascript:void(0)">${v.fvirtualcointypeByFvirtualcointype2.fShortName } / ${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>
		  	    </c:when>
		  	    <c:otherwise>
		  	    <a href="/market_new.html?symbol=${v.fid }">${v.fvirtualcointypeByFvirtualcointype2.fShortName } / ${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a>
		  	    </c:otherwise>
		  	    </c:choose>
		  	</li>
		  </c:forEach>	
		</ul>
		<a class="marketNew-prev pull-right" href="/market.html"><spring:message code="back_simple"/> &gt;&gt;</a>
		<c:if test="${sessionScope.login_user == null }">
			<div class="marketNew-login pull-right clearfix">
				<a href="/user/login.html"><spring:message code="signIn"/></a>
				<span> | </span>
				<a href="/user/register.html"><spring:message code="regist"/></a>
			</div>
		</c:if>

		<c:if test="${sessionScope.login_user != null }">
			<div class="marketNew-login pull-right clearfix">
				<a href="/user/logout.html"><spring:message code="sign_out"/></a>
			</div>
			<a class="marketNew-userCenter pull-right" href="/financial/index.html"><spring:message code="hello"/>，${login_user.fnickName}</a>
		</c:if>
	</div>
	<div class="marketNew-statistics clearfix">
		<div class="marketNew-chart">
			<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart.html?symbol=${symbol }&themename=dark"></iframe>
			<a class="openfullscreen" id="openfullscreen" href="javascript:void(0)" title="${full_screen}" style="display:block;"></a>
			<a class="closefullscreen" id="closefullscreen" href="javascript:void(0)" title="${exit_full_screen}" style="display:none"></a>
		</div>
		<div class="marketNew-datas">
			<div class="marketNew-dataItem clearfix">
				<div class="marketNew-dataTitle clearfix">
					<span> </span>
					<span><spring:message code="price"/>（CNY）</span>
					<span><spring:message code="number"/></span>
				</div> 
				<div class="marketNew-dataInner clearfix">
					<div class="marketNew-price marketNew-price--sell">
						<!-- <div class="marketNew-priceItem">
							<span class="marketNew-blue">买1</span>
							<span>7819.89</span>
							<span>7819.89</span>
						</div> -->
					</div>
					<div class="marketNew-current">
						<span></span>
						<span style="text-align: left;font-size: 12px;color:red;"></span>
						<select name="sel" id="sel" style="color:#000;">
							<option value="0">0.01</option>
							<option value="1">0.1</option>
							<option value="2">1</option>
						</select>
					</div>
					<div class="marketNew-price marketNew-price--buy">
					</div>
				</div>
			</div>
			<div class="marketNew-dataItem">
				<div class="marketNew-dataTitle clearfix">
					<span><spring:message code="time"/> </span>
					<span><spring:message code="price"/>（CNY）</span>
					<span><spring:message code="number"/></span>
				</div> 
				<div class="marketNew-price marketNew-price--all">
					<!-- <div class="marketNew-priceItem">
						<span>08:17:28</span>
						<span class="marketNew-red">7819.89</span>
						<span>1.9029</span>
					</div> -->
				</div>
			</div>
		</div>
	</div>
	<div class="marketNew-panel">
		<div class="marketNew-record clearfix">
			<div class="marketNew-recordTitle clearfix">
				 <span class="marketNew-recordType active pull-left" href="javascript:;"><spring:message code="limit_entrust"/></span>
				 <span class="marketNew-recordType pull-left" href="javascript:;"><spring:message code="history_entrust"/></span>
			<c:if test="${sessionScope.login_user != null }">
				 <a class="marketNew-moreRecord active pull-right" href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=0"><spring:message code="more_record"/>&gt;&gt;</a>
				 <a class="marketNew-moreRecord pull-right" href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=1"><spring:message code="more_record"/>&gt;&gt;</a>
			</c:if>	
			<c:if test="${sessionScope.login_user == null }">
				<a class="marketNew-moreRecord active pull-right" href="javascript:;"><spring:message code="more_record"/>&gt;&gt;</a>
				<a class="marketNew-moreRecord pull-right" href="javascript:;"><spring:message code="more_record"/>&gt;&gt;</a>
			</c:if>	
			</div>
		    <c:if test="${sessionScope.login_user != null }">
				<div class="marketNew-tableWrap">
					<table class="table marketNew-entrust active marketNew-entrust--current text-center">
						<thead>
							<tr>
								<th><spring:message code="entrust_time_title"/></th>
								<th><spring:message code="entrutst_type_title"/></th>
								<th><spring:message code="price"/></th>
								<th><spring:message code="number"/></th>
								<th><spring:message code="success_num"/></th>
								<th><spring:message code="success_amount_title"/></th>
								<th><spring:message code="success_avg_price"/></th>
								<th><spring:message code="status_title"/></th>
								<th><spring:message code="operation_title"/></th>
								<th>
									<a class="marketNew-multiCancel btn"  role="button"><spring:message code="batch_operation"/></a>
								</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<table class="table marketNew-entrust marketNew-entrust--history text-center">
						<thead>
							<tr>
								<th><spring:message code="entrust_time_title"/></th>
								<th><spring:message code="entrutst_type_title"/></th>
								<th><spring:message code="price"/></th>
								<th><spring:message code="number"/></th>
								<th><spring:message code="success_num"/></th>
								<th><spring:message code="success_amount_title"/></th>
								<th><spring:message code="fees_title"/></th>
								<th><spring:message code="success_avg_price"/></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
		    </c:if>
		    <c:if test="${sessionScope.login_user == null }">
			    <div class="marketNew-unLogin text-center">
			    	<a class="btn btn-danger" href="/user/login.html"><spring:message code="signIn"/></a>
			    	<spring:message code="or_desc"/>
			    	<a class="btn btn-success" href="/user/register.html"><spring:message code="regist"/></a>
			    </div>
		    </c:if>
		</div>
		<div class="marketNew-buysell clearfix">
			<div class="marketNew-buy">
				<p>
					<spring:message code="available"/>：
					<span class="marketNew-blue" id="toptradecnymoney"></span>
					<span class="">${coin1.fShortName }</span>
				</p>
				<p>
					<spring:message code="can_buy"/>：
					<span class="canBuy"></span>
					<span>${coin2.fShortName }</span>
				</p>
				<div class="marketNew-inputWrap">
					<label for="tradebuyprice"><spring:message code="buy_price1"/> ${coin1.fShortName }</label>
					<input id="tradebuyprice" type="text" value="${recommendPrizebuy }" ${sessionScope.login_user == null ? 'readonly' : '' } >
				</div>
				<div class="marketNew-inputWrap">
					<label for="tradebuyamount"><spring:message code="buy_num1"/> ${coin2.fShortName }</label>
					<input id="tradebuyamount" type="text" ${sessionScope.login_user == null ? 'readonly' : '' }>
				</div>
				<div class="clearfix">
					<div class="clearfix">
						<span class="trade-tips padding-right-clear">
							<span id="buyBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete" data-param-type="0"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0"></div>
										<div class="proportioncircle proportion1" data-points="25"></div>
										<div class="proportioncircle proportion2" data-points="50"></div>
										<div class="proportioncircle proportion3" data-points="75"></div>
										<div class="proportioncircle proportion4" data-points="100"></div>
									</div>
								</div>
							</span>
						</span>
					</div>
				</div>
				<p>
					<spring:message code="est_trade_amount"/>：
					<span class="pull-right" id="tradebuyTurnover"></span>
					<span>${coin1.fShortName }</span>
				</p>
				<div>
					<button ${sessionScope.login_user == null ? 'disabled' : '' } id="buybtn" class="btn btn-danger marketNew-buyBtn"><spring:message code="trade_buy"/></button>
				</div>
				<span id="buy-errortips" class="text-danger trade-error"></span>
			</div>
			<div class="marketNew-sell">
				<p>
					<spring:message code="available"/>：
					<span class="marketNew-blue" id="toptrademtccoin"></span>
					<span class="">${coin2.fShortName }</span>
				</p>
				<p>
					<spring:message code="be_sell"/>：
					<span class="canSell"></span>
					<span class="">${coin1.fShortName }</span>
				</p>
				<div class="marketNew-inputWrap">
					<label for="tradesellprice"><spring:message code="sell_price1"/> ${coin1.fShortName }</label>
					<input id="tradesellprice" type="text" ${sessionScope.login_user == null ? 'readonly' : '' }>
				</div>
				<div class="marketNew-inputWrap">
					<label for="tradesellamount"><spring:message code="sell_num1"/> ${coin2.fShortName }</label>
					<input type="password" style="display:none">
					<input id="tradesellamount" autocomplete="off" class="tradesellamount" type="text" ${sessionScope.login_user == null ? 'readonly' : '' }>
				</div>
				<div class="clearfix">
					<div class=" clearfix">
						<span class="trade-tips padding-right-clear">
							<span id="sellBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete" data-param-type="0"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0"></div>
										<div class="proportioncircle proportion1" data-points="25"></div>
										<div class="proportioncircle proportion2" data-points="50"></div>
										<div class="proportioncircle proportion3" data-points="75"></div>
										<div class="proportioncircle proportion4" data-points="100"></div>
									</div>
								</div>
							</span>
						</span>
					</div>
				</div>
				<p>
					<spring:message code="est_trade_amount"/>：
					<span class="pull-right" id="tradesellTurnover"></span>
					<span>${coin1.fShortName }</span>
				</p>
				<div>
					<button ${sessionScope.login_user == null ? 'disabled' : '' } id="sellbtn" class="btn btn-success marketNew-buyBtn"><spring:message code="trade_sell"/></button>
				</div>
				<span id="sell-errortips" class="text-danger trade-error"></span>
			</div>
		</div>
	</div>
	

	<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" >
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="config_trade_pwd"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label for="tradePwd" class="col-xs-5 control-label"><spring:message code="comm.error.tips.79"/></label>
						<div class="col-xs-7">
							<input type="password" autocomplete="off" class="form-control" id="tradePwd" placeholder="${comm_error_tips_79}"/>
						</div>
					</div>
					<div class="form-group">
						
					</div>	
				</div>
				<div class="modal-footer clearfix">
					<button id="tradepass-close" type="button" class="btn pull-left marketNew-cancelBtn "><spring:message code="cancel_desc1"/></button>
					<button id="modalbtn" type="button" class="btn btn-danger pull-right"><spring:message code="ok_desc"/></button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal modal-custom fade" id="tradeCancelDialog" tabindex="-1" role="dialog" >
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="trade_cancel"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<h2 class="text-center"><spring:message code="config_cancel_trade"/></h2>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<button id="tradeCancelDialog-confime" type="button" class="btn btn-cancel pull-left"><spring:message code="ok_desc"/></button>
					<button id="tradeCancelDialog-cancel" type="button" class="btn pull-right marketNew-cancelBtn "><spring:message code="cancel_desc1"/></button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal modal-custom fade" id="tradeMutiCancel" tabindex="-1" role="dialog" >
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="batch_cancel"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						 <div class="checkbox col-xs-4">
						     <label>
						       <input value="0" name="cancelType" type="radio"><spring:message code="all_cancel"/>
						     </label>
						  </div>
						  <div class="checkbox col-xs-4">
						     <label>
						       <input value="1" name="cancelType" type="radio"> <spring:message code="cancel_buy"/>
						     </label>
						  </div>
						  <div class="checkbox col-xs-4">
						     <label>
						       <input value="2" name="cancelType" type="radio"> <spring:message code="cancel_sell"/>
						     </label>
						  </div>
					</div>
				</div>
				<div class="modal-footer clearfix">
					<button id="tradeMutiCancel-confime" type="button" class="btn btn-danger pull-left"><spring:message code="confirm_cancel"/></button>
					<button id="tradeMutiCancel-cancel" type="button" class="btn pull-right marketNew-cancelBtn "><spring:message code="cancel_desc1"/></button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal modal-custom fade" id="tradeTips" tabindex="-1" role="dialog" >
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="trade_point"/></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<h2 class="text-center"><spring:message code="trade.error.tips.8"/></h2>
					</div>
				</div>
				<div class="modal-footer text-center clearfix">
					<button id="tradeTips-close" type="button" class="btn btn-danger pull-left"><spring:message code="ok_desc"/></button>
				</div>
			</div>
		</div>
	</div>

</div>


	<input id="userid" type="hidden" value="${login_user.fid}">	
	<input type="hidden" id="coinCount1" value="${ftrademapping.fcount1 }">
	<input type="hidden" id="coinCount2" value="${ftrademapping.fcount2 }">
	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<input type="hidden" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }" id="coinshortName">
	<input id="minBuyCount" type="hidden" value="<ex:DoubleCut value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }" />">
    <input id="limitedType" type="hidden" value="0">
    <input id="lastprice" type="hidden" value="${last_price}">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="${tradeType }">
	<input id="login" type="hidden" value="${login }">
	<input id="tradePassword" type="hidden" value="${isTradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">

	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/language.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js"></script>


	<script type="text/javascript" src="${oss_url}/static/front/js/market/markt_new.js?t=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
