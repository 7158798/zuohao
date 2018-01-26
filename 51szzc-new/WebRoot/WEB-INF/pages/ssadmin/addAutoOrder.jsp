<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改自动挂单配置</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveAutoOrder.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>币种：</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${allType}" var="type">
							<c:if test="${type.fid == vid}">
								<option value="${type.fid}" selected="true">${type.fname}</option>
							</c:if>
							<c:if test="${type.fid != vid}">
								<option value="${type.fid}">${type.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>挂单数量：</dt>
				<dd>
					<input type="text" name="random" class="required number" size="70" value="${autoOrder.random}" />
				</dd>
			</dl>
			<dl>
				<dt>挂单比例：</dt>
				<dd>
					<input type="text" name="ratio" class="required" size="70" value="${autoOrder.ratio}" />
				</dd>
			</dl>
			<dl>
				<dt>取消订单时间（分钟）：</dt>
				<dd>
					<input type="text" name="cancelTime" class="required" size="70" value="${autoOrder.cancelTime}" />
				</dd>
			</dl>
			<dl>
				<dt>挂单帐号：</dt>
				<dd>
					<%--<input type="text" name="sellUserId" class="required" size="70" value="${autoOrder.sellUserId}" />--%>
					<input type="hidden" name="userLookup.id" value="${autoOrder.user.fid}"/>
					<input type="text" class="required" name="userLookup.floginName" value="${autoOrder.user.floginName}" suggestFields="id,floginName"
						   suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
					<a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>备用帐号：</dt>
				<dd>
					<%--<input type="text" name="buyUserId" class="required" size="70" value="${autoOrder.buyUserId}" />--%>
					<input type="hidden" name="reserveUserLookup.id" value="${autoOrder.reserveUser.fid}"/>
					<input type="text" class="required" name="reserveUserLookup.floginName" value="${autoOrder.reserveUser.floginName}" suggestFields="id,floginName"
						   suggestUrl="ssadmin/userLookup.html" lookupGroup="reserveUserLookup" readonly="readonly"/>
					<a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="reserveUserLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>账户中人民币：</dt>
				<dd>
					<input type="text" name="allCny" class="required number"
						   size="70" value="<fmt:formatNumber value="${autoOrder.allCny}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="2"/>" />
				</dd>
			</dl>
			<dl>
				<dt>账户中虚拟币：</dt>
				<dd>
					<input type="text" name="allXnb" class="required number"
						   size="70" value="<fmt:formatNumber value="${autoOrder.allXnb}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/>" />
				</dd>
			</dl>
			<dl>
				<dt>释放金额/货币分钟：</dt>
				<dd>
					<input type="text" name="releaseTime" class="required" size="70" value="${autoOrder.releaseTime}" />
				</dd>
			</dl>
			<dl>
				<dt>下单休眠时间（毫秒）：</dt>
				<dd>
					<input type="text" name="sleepTime" class="required" size="70" value="${autoOrder.sleepTime}" />
				</dd>
			</dl>
			<dl>
				<dt>请求休眠时间（毫秒）：</dt>
				<dd>
					<input type="text" name="reqSleepTime" class="required" size="70" value="${autoOrder.reqSleepTime}" />
				</dd>
			</dl>
			<dl>
				<dt>数据源api(多个逗号隔开)：</dt>
				<dd>
					<input type="text" name="urls" class="required" size="70" value="${autoOrder.urls}" />
				</dd>
			</dl>
			<dl>
				<dt>api切换时间（分钟）：</dt>
				<dd>
					<input type="text" name="toggleTime" class="required" size="70" value="${autoOrder.toggleTime}" />
				</dd>
			</dl>
			<dl>
				<dt>调整价格：</dt>
				<dd>
					<input type="text" name="adjustPrice" class="required" size="70" value="${autoOrder.adjustPrice}" />
				</dd>
			</dl>
			<dl>
				<dt>成本单价：</dt>
				<dd>
					<input type="text" name="costPrice"  size="70" value="${autoOrder.costPrice}" />
				</dd>
			</dl>
			<dl>
				<dt>通知手机号码：</dt>
				<dd>
					<input type="text" name="mobilePhone" class="required" size="70" value="${autoOrder.mobilePhone}" />
				</dd>
			</dl>
			<dl>
				<dt>短信暂停时间：</dt>
				<dd>
					<input type="text" name="pauseMsgTime" class="required number" size="20" value="${autoOrder.pauseMsgTime}" /> &nbsp;(分)
				</dd>
			</dl>
			<dl>
				<dt>随机价格区间：</dt>
				<dd>
					<input type="text" name="priceScope" class="required" size="50" value="${autoOrder.priceScope}" /> &nbsp;(用于成本单价)
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>
