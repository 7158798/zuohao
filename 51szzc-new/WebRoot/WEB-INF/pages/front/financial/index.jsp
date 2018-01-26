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

	<link rel="stylesheet" href="${oss_url}/static/front/css/finance/index.css" type="text/css"></link>
</head>
<body>

<%@include file="../comm/header.jsp" %>

<div class="container-full">
	<div class="container displayFlex">

		<%@include file="../comm/left_menu.jsp" %>

		<div class="col-xs-10 padding-right-clear">
			<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
				<div class="col-xs-12 rightarea-con ">
					<div class="index-top-icon clearfix">
						<div class="col-xs-7 top-icon">
							<div class="col-xs-12">
								<span class="assets-icon"><i class="gross"></i><spring:message code="este_total_amount"/></span>
							</div>
							<div class="col-xs-12">
									<span class="text-danger font-size-40"> ￥<ex:DoubleCut
											value="${totalCapitalTrade }" pattern="##.##"
											maxIntegerDigits="15" maxFractionDigits="2" />
									</span>
							</div>
							<span class="split"></span>
						</div>
						<%-- <div class="col-xs-4 top-icon">
                            <div class="col-xs-12">
                                <span class="assets-icon"><i class="net"></i>净资产(￥)</span>
                            </div>
                            <div class="col-xs-12">
                                <span class="text-danger font-size-40">￥<ex:DoubleCut
                                    value="${totalCapitalTrade }" pattern="##.##"
                                    maxIntegerDigits="15" maxFractionDigits="4" />
                                </span>
                            </div>
                            <span class="split"></span>
                        </div> --%>
						<div class="col-xs-4 text-center top-btn">
							<a href="/account/withdrawCny.html" class="btn "><spring:message code="lmenu_withdrawCny"/></a>
							<a href="/account/rechargeCny.html" class="btn btn-re"><spring:message code="lmenu_rechargeCny"/></a>
						</div>
					</div>
					<div class="col-xs-12 padding-clear" style="margin-top: -30px">
						<div class="titleDiv">
							<div class="titleDivOne"></div>
							<div class="titleDivOneLeft"><spring:message code="cny_total_amount"/></div>
						</div>
						<div class="titleDivOneContent">
							<span class="titleDivOneContentSpan0"><span class="titleContentColor"><spring:message code="total"/>CNY：</span><ex:DoubleCut value="${requestScope.fwallet.ftotal+requestScope.fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></span>
							<span class="titleDivOneContentSpan"><span class="titleContentColor"><spring:message code="available"/>CNY：</span><ex:DoubleCut value="${requestScope.fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></span>
							<span class="titleDivOneContentSpan"><span class="titleContentColor"><spring:message code="frozen"/>CNY：</span><ex:DoubleCut value="${requestScope.fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/></span>
						</div>
					</div>
					<div class="col-xs-12 padding-clear padding-top-30">
						<div class="titleDiv">
							<div class="titleDivOne"></div>
							<div class="titleDivOneLeft"><spring:message code="digital_money_assets"/></div>
						</div>
					<div style="margin-bottom: 30px" class="col-xs-12 padding-clear padding-top-30">
									<table style="text-align: center;width: 100%;">
										<tr style="background-color: #f5f5f5;height: 40px;">
									<th style="text-align: center"><spring:message code="cncy_name"/></th>
									<th style="text-align: center"><spring:message code="user_total_cncy_count"/></th>
									<th style="text-align: center"><spring:message code="available_cncy_count"/></th>
									<th style="text-align: center"><spring:message code="frozen_count"/></th>
									<th style="text-align: center"><spring:message code="current_market_price"/></th>
									<th style="text-align: center"><spring:message code="total_market_price"/></th>
									<th style="text-align: center"><spring:message code="cost_price"/></th>
									<th style="text-align: center"><spring:message code="profit_and_loss"/></th>
									<th style="text-align: center"><spring:message code="profit_loss_rate"/></th>
							</tr>
							<c:forEach items="${fvirtualwalletVoList }" var="v" varStatus="vs" begin="0">
								<tr>
									<td class="border-top-clear">
										<c:if test="${requestScope.locale_language=='zh_CN'}">${v.fviName }</c:if>
										<c:if test="${requestScope.locale_language=='en'}">${v.shortName }</c:if>
									</td>
									<td class="border-top-clear">${v.ftotal }</td>
									<td class="border-top-clear">${v.fable }</td>
									<td class="border-top-clear">${v.ffrozen }</td>
									<td class="border-top-clear">${v.fprice }</td>
									<td class="border-top-clear">${v.ftotalPrice }</td>
									<td class="border-top-clear">${v.fcostPrice }</td>
									<td class="border-top-clear">${v.fprofitLoss }</td>
									<td class="border-top-clear">
										<c:choose>
											<c:when test="${v.fprofitLossRate > 0  || v.fprofitLossRate <0 }">
												${v.fprofitLossRate }%
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					</div>
					<div class="padding-top-30" style="margin-left: 20px;margin-top: 80px"><spring:message code="asset_desc"/></div>
				</div>
		</div>
	</div>
</div>




<%@include file="../comm/footer.jsp" %>

</body>
</html>