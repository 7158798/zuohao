<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.trade.jsp"></jsp:include>
<link href="${oss_url}/static/front/css/trade/trade.css" rel="stylesheet" type="text/css" media="screen, projection" />
</head>
<body>
<style>
.trade-amount .datatime-sel, .trade-amount .datatime:hover {
    background: #41c7f9 none repeat scroll 0 0;
    color: #fff;
}
.trade-amount .databtn {
    cursor: pointer;
    display: inline-block;
    margin: 0 10px;
    padding: 5px;
    height: 55px;
    width:40px;
}
</style>	

<jsp:include page="../comm/header.jsp"></jsp:include>


	<div class="container-full">
		<div class="container displayFlex">
			
			<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea trade">
					
					<c:if test="${isTradePassword == false}">
						<div class="col-xs-12 rightarea-top">
							<span class="col-xs-5"> <span><spring:message code="trade_desc1"/></span> <a
								href="/user/security.html" class="text-danger"><spring:message code="trade_desc2"/></a> </span> <span
								class="pull-right"> <span class="trade-process active">
									<i class="icon">1</i> <span><spring:message code="trade_desc2"/></span> <i class="arrow"></i> </span>
								<span class="trade-process "> <i class="icon">2</i> <span><spring:message code="lmenu_rechargeCny"/></span>
									<i class="arrow"></i> </span> <span class="trade-process"> <i
									class="icon">3</i> <span><spring:message code="trade_desc3"/></span> <i class="arrow"></i> </span> </span>
						</div>
					</c:if>
				
				<div class="col-xs-12 rightarea-con padding-right-clear padding-left-clear">
					<div class="col-xs-8" style="background:#f9f9f9">
						<div class="col-xs-6 paneltitle"><spring:message code="trade_buy"/></div>
						<div class="col-xs-6 paneltitle paneltitle--green"><spring:message code="trade_sell"/></div>
						<div class="col-xs-6 trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-12">
									<span><spring:message code="available"/></span>
									<span class="">${coin1.fShortName }:</span>
									<span id="toptradecnymoney" class="redtips">
										0
									</span>
									<c:choose>
										<c:when test="${coin1.ftype==0 }">
											<a href="/account/rechargeCny.html" class="pull-right"><spring:message code="lmenu_rechargeCny"/></a>
										</c:when>
										<c:otherwise>
											<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="pull-right"><spring:message code="lmenu_rechargeCny"/></a>
										</c:otherwise>
									</c:choose>
								</span>
								
							<!-- 	<span class="col-xs-4 text-center">
									<span class="databtn datatime datatime-sel" data-type="1">限价</span>
									<span class="databtn datatime" data-type="2">市价</span>
								</span> -->
								
								<!-- <span class="col-xs-4 text-right">
									<span>冻结</span>
									<span id="toptradelevercny">0.00</span>
									<span>${coin1.fShortName }</span>
									<i></i>
								</span> -->
							</div>
							<div class="max-width clearfix" id="buypricediv">
								<div class="col-xs-12">
									<div class="form-group">
										<label for="tradebuyType" class="trade-inputtips"><spring:message code="trade_type"/>:</label>
										<select name="tradebuyType" id="tradebuyType" class="form-control trade-input">
											<option value="0"><spring:message code="trade_limit"/></option>
											<option value="1"><spring:message code="trade_market"/></option>
										</select>
									</div>
									
								</div>
								<div class="col-xs-12 buyarea active">
									<div class="form-group">
										<label for="tradebuyprice" class="trade-inputtips"><spring:message code="buy_price"/>:</label>
										<input id="tradebuyprice" class="form-control trade-input" type="text" autocomplete="off" placeholder="" value="${recommendPrizebuy }">
										<span class="trade-currency">${coin1.fShortName }</span>
									</div>
									<div class="form-group">
										<span class="form-control trade-tips padding-right-clear">
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
									<div class="form-group">
										<label for="tradebuyamount" class="trade-inputtips"><spring:message code="num_str1"/>&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="num_str2"/>:</label>
										<input id="tradebuyamount" class="form-control trade-input" type="text" autocomplete="off" value="" >
										<span class="trade-currency">
											${coin2.fShortName }
										</span>
									</div>
									
									<div class="form-group">
										<label class="text-left padding-right-clear"><spring:message code="trade_total_amount"/></label>
										<span class="padding-right-clear redtips">
											<span id="tradebuyTurnover">0</span>
											${coin1.fShortName }
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group buyarea">
										<label for="tradebuymoney" class="trade-inputtips"><spring:message code="buy_amount"/>:</label>
										<input id="tradebuymoney" class="form-control trade-input" type="text" autocomplete="off" value="">
										<span class="trade-currency">
											${coin1.fShortName }
										</span>
									</div>
									<div class="form-group">
										<button id="buybtn" class="btn btn-danger btn-block"><spring:message code="trade_buy"/>${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="buy-errortips" class="text-danger trade-error"></span>
									</div>
								</div>
							</div>
							<div class="max-width clearfix" id="buymarketdiv" style="display: none;">
								<div class="col-xs-12">
									<div class="form-group">
											<span class="trade-tips padding-right-clear"> <span id="buyBar" class="col-xs-12 buysellbar">
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
								<div class="col-xs-12">
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-12 padding-right-clear ">
												<label for="tradebuyprice2" class="trade-inputtips"><spring:message code="trade_amount"/>/${coin1.fShortName }</label>
												<input id="tradebuyprice2" class="form-control" value="0" type="text" autocomplete="off">
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="buy-errortips2" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<button id="buybtn2" class="btn btn-danger btn-block"><spring:message code="trade_buy"/>${coin2.fShortName }</button>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-12">
									<span><spring:message code="available"/></span>
									<span class="">${coin2.fShortName }:</span>
									<span id="toptrademtccoin" class="greentips">
										0
									</span>
									<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="pull-right"><spring:message code="lmenu_rechargeCny"/></a>
								</span>
								
								<!-- <span class="col-xs-4 text-center">
									<span class="databtn datatime datatime-sel" data-type="3">限价</span>
									<span class="databtn datatime" data-type="4">市价</span>
								</span> -->
								
								<!-- <span class="col-xs-4 text-right">
									<span>冻结</span>
									<span id="toptradelevercoin">0.0</span>
									<span>${coin2.fShortName }</span>
									<i></i>
								</span> -->
							</div>
							<div class="max-width clearfix" id="sellpricediv">
								<div class="col-xs-12">
									<div class="form-group">
										<label for="tradesellType" class="trade-inputtips"><spring:message code="trade_type"/>:</label>
										<select name="tradesellType" id="tradesellType" class="form-control trade-input">
											<option value="0"><spring:message code="trade_limit"/></option>
											<option value="1"><spring:message code="trade_market"/></option>
										</select>
									</div>
								</div>
								<div class="col-xs-12 sellarea active">
									<div class="form-group">
										<label for="tradesellprice" class="trade-inputtips"><spring:message code="sell_price"/>:</label>
										<input id="tradesellprice" class="form-control trade-input" type="text" autocomplete="off" value="${recommendPrizesell }">
										<span class="trade-currency">
											${coin1.fShortName }
										</span>
									</div>
									<div class="form-group">
										<span class="form-control trade-tips padding-right-clear">
											<span id="sellBar" class="col-xs-12 buysellbar">
												<div class="buysellbar-box">
													<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker" data-param-complete="complete sell-complete" data-param-type="1"></div>
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
									<div class="form-group">
										<label for="tradesellamount" class="trade-inputtips"><spring:message code="num_str1"/>&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="num_str2"/></label>
										<input id="tradesellamount" class="form-control trade-input" type="text" autocomplete="off" value="" >
										<span class="trade-currency">
											${coin2.fShortName }
										</span>
									</div>
									<div class="form-group">
										<label class="text-left padding-right-clear"><spring:message code="trade_total_amount"/></label>
										<span class="padding-right-clear redtips">
											<span id="tradesellTurnover">0</span>
											${coin1.fShortName }
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group sellarea">
										<label for="tradesellnum" class="trade-inputtips"><spring:message code="sell_num"/>:</label>
										<input id="tradesellnum" class="form-control trade-input" type="text" autocomplete="off" value="" >
										<span class="trade-currency">
											${coin2.fShortName }
										</span>
									</div>
									<div class="form-group">
										<button id="sellbtn" class="btn btn-success btn-block"><spring:message code="trade_sell"/>${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="sell-errortips" class="text-danger trade-error"></span>
									</div>
								</div>
							</div>
							
							<div class="max-width clearfix" id="sellmarketdiv" style="display: none;">
								<div class="col-xs-12">
									<div class="form-group">
											<span class="form-control trade-tips padding-right-clear"> <span id="sellBar" class="col-xs-12 buysellbar">
													<div class="buysellbar-box">
														<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker" data-param-complete="complete sell-complete" data-param-type="1"></div>
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
								<div class="col-xs-12">
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-12 padding-right-clear ">
												<label for="tradesellprice2" class="trade-inputtips"><spring:message code="trade_num"/>/${coin2.fShortName }</label>
												<input id="tradesellprice2" class="form-control" value="0" type="text" autocomplete="off">
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="sell-errortips2" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<button id="sellbtn2" class="btn btn-success btn-block"><spring:message code="trade_sell"/>${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="text-success"><spring:message code="regc_desc17"/><i class="arrow-icon-small green"></i></a>
									</div>
								</div>
							</div>
							
						</div>
					</div>
					<div id="coinBoxbuybtc" class="col-xs-4">

					    <span class="trade-depth">
					           <spring:message code="new_price"/>： <span class=""> <span
								id="lastprice"> </span> <span id="lastpriceicon" class="arrow-icon-big red"></span></span>
					    </span>
                    	<div class="entrustTitle">
                    		<span style="width:100px"><spring:message code="buy_sell"/></span>
                    		<span style="width:85px"><spring:message code="price"/>(${coin1.fShortName })</span>
                    		<span><spring:message code="entrust"/>(${coin2.fShortName })</span>
                    	</div>
						<ul id="sellbox" class="list-group first-child">
						</ul>
						<ul id="buybox" class="list-group ">
						</ul>
						<span class="trade-depth"> <spring:message code="recent_success_entrutst_record"/></span>
						<ul id="logbox" class="list-group ">
						</ul>
					</div>
				</div>
				<div id="entrustInfo">
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="comm.error.tips.107"/></span>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="password" autocomplete="off" class="form-control" id="tradePwd" placeholder=" "+<spring:message code="zc.error.tips.8"/>+" ">
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalbtn" type="button" class="btn btn-primary"><spring:message code="comm.error.tips.40"/></button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="entrustsdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="lmenu_entrust_record"/></span>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="comm.error.tips.38"/></button>
				</div>
			</div>
		</div>
	</div>
	<input id="coinshortName" type="hidden" value="${coin2.fShortName }">
	<input id="symbol" type="hidden" value="${ftrademapping.fid }">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="0">
	<input id="userid" type="hidden" value="${userid }">
	
	<input id="tradePassword" type="hidden" value="${isTradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">
	
	
    <input id="coinCount1" type="hidden" value="${ftrademapping.fcount1 }">
    <input id="coinCount2" type="hidden" value="${ftrademapping.fcount2 }">
    <input id="minBuyCount" type="hidden" value="<ex:DoubleCut value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }" />">
    <input id="limitedType" type="hidden" value="0">

    <input id="buyType" type="hidden" value="0">
    <input id="sellType" type="hidden" value="0">

 
	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trade.js?r=<%=new java.util.Date().getTime() %>"></script>
 	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trademarket.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>

