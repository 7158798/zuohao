<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
	<div class="pageFormContent" layoutH="20">

		<fieldset>
			<legend>会员信息</legend>
			<dl>
				<dt>全站总会员数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${totalUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>全站已认证会员数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${totalValidateUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>今日新增会员数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${todayTotalUser}</span>
				</dd>
			</dl>
			<dl>
				<dt>今日已认证会员数：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${todayValidateUser}</span>
				</dd>
			</dl>
		</fieldset>

		<fieldset>
			<legend>充值信息</legend>
			<c:forEach items="${amountInMap }" var="v">
			<dl>
				<dt >今日${v.key }充值金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${v.value}</span>
				</dd>
			</dl>
			</c:forEach>
			 <dl>
				<dt>今日手工充值金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${todayOpAmt}</span>
				</dd>
			</dl>
			<c:forEach items="${totalAmountInMap }" var="v">
			<dl>
				<dt >累计${v.key }充值金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${v.value}</span>
				</dd>
			</dl>
			</c:forEach>
			<dl>
				<dt>累计手工充值金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit">${totalOpAmt}</span>
				</dd>
			</dl>
		</fieldset>

		<fieldset>
			<legend>平台钱包信息</legend>
			<c:forEach items="${virtualQtyList}" var="virtual">
				<dl>
					<dt>全站${virtual.fName}：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtual.totalQty}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>含冻结：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtual.frozenQty}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>

		<fieldset>
			<legend>当前委托信息(不包含系统配置过滤账户)</legend>
			<c:forEach items="${entrustBuyMap}" var="entrustBuy">
				<dl>
					<dt>买入${entrustBuy.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustBuy.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustSellMap}" var="entrustSell">
				<dl>
					<dt>卖出${entrustSell.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>


		<fieldset>
			<legend>当前累计待提现信息</legend>
			<dl>
				<dt>人民币金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutWaitingMap.totalAmount}" pattern="#0.######" />
					</span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutWaitingMap}" var="virtualOutWaiting">
				<dl>
					<dt>${virtualOutWaiting.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutWaiting.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>


		<fieldset>
			<legend>虚拟币充值信息</legend>
			<c:forEach items="${virtualInMap}" var="virtualIn">
				<dl>
					<dt>今日充值${virtualIn.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualIn.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		<fieldset>
			<legend>累计虚拟币充值信息</legend>
			<c:forEach items="${total_virtualInMap}" var="total_virtualIn">
				<dl>
					<dt>累计充值${total_virtualIn.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${total_virtualIn.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>

		<fieldset>
			<legend>今日提现信息</legend>
			<dl>
				<dt>今日提现人民币金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutMap.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<dl>
				<dt>今日提现人民币手续费：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutFeeMap1.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<dl>
				<dt>今日提现人民币费用合计：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutFeeMap1.totalAmount+amountOutMap.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<!--换行-->
			<dl>
				<dt></dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"> </span>
				</dd>
			</dl>
			<c:forEach items="${virtualOutSuccessMap}" var="virtualOutSuccess">
				<dl>
					<dt>今日提现${virtualOutSuccess.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>今日提现${virtualOutSuccess.fName}手续费：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${virtualOutSuccess.fees}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>今日提现${virtualOutSuccess.fName}费用合计：</dt>
					<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${virtualOutSuccess.amount+virtualOutSuccess.fees}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<!--换行-->
				<dl>
					<dt></dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>累计提现信息</legend>
			<dl>
				<dt>累计提现人民币金额：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutMap1.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<dl>
				<dt>累计提现人民币手续费：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutFeeMap2.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<dl>
				<dt>累计提现人民币费用合计：</dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${amountOutFeeMap2.totalAmount+amountOutMap1.totalAmount}" pattern="#0.######" /> </span>
				</dd>
			</dl>
			<!--换行-->
			<dl>
				<dt></dt>
				<dd style="color:red;font-weight:bold;">
					<span class="unit"> </span>
				</dd>
			</dl>
			<c:forEach items="${total_virtualOutSuccessMap}" var="total_virtualOutSuccess">
				<dl>
					<dt>累计提现${total_virtualOutSuccess.fName}数量：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${total_virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>累计提现${total_virtualOutSuccess.fName}手续费：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${total_virtualOutSuccess.fees}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<dl>
					<dt>累计提现${total_virtualOutSuccess.fName}费用合计：</dt>
					<dd style="color:red;font-weight:bold;">
					<span class="unit"><fmt:formatNumber
							value="${total_virtualOutSuccess.fees+total_virtualOutSuccess.amount}" pattern="#0.######" /> </span>
					</dd>
				</dl>
				<!--换行-->
				<dl>
					<dt></dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
		
		<fieldset>
			<legend>交易手续费信息</legend>
			<c:forEach items="${entrustSellFeesMap1}" var="entrustSell">
				<dl>
					<dt>今日卖出${entrustSell.fName}：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustSellFeesMap2}" var="entrustSell">
				<dl>
					<dt>累计卖出${entrustSell.fName}：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustBuyFeesMap1}" var="entrustSell">
				<dl>
					<dt>今日买入${entrustSell.fName}：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
			<c:forEach items="${entrustBuyFeesMap2}" var="entrustSell">
				<dl>
					<dt>累计买入${entrustSell.fName}：</dt>
					<dd style="color:red;font-weight:bold;">
						<span class="unit"><fmt:formatNumber
								value="${entrustSell.total}" pattern="#0.######" /> </span>
					</dd>
				</dl>
			</c:forEach>
		</fieldset>
	</div>
</body>
</html>

