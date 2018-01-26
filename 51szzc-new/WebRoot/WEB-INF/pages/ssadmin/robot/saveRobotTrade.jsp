<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">新建机器挂单配置</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveRobotTrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>货币类型：</dt>
				<dd>
					<select type="combox" name="vid">
						<c:forEach items="${ftrademappings}" var="type">
							<c:if test="${type.fid == vid}">
								<option value="${type.fid}" selected="true">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
							</c:if>
							<c:if test="${type.fid != vid}">
								<option value="${type.fid}">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<select type="combox" name="type">
						<c:forEach items="${typeEnumList}" var="tType">
							<c:if test="${tType.code == type}">
								<option value="${tType.code}" selected="true">${tType.desc}</option>
							</c:if>
							<c:if test="${tType.code != type}">
								<option value="${tType.code}">${tType.desc}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>成本金额：</dt>
				<dd>
					<input type="text" name="cost" class="required number" size="70" value="${robotTrade.cost}" />
				</dd>
			</dl>
			<dl>
				<dt>数量：</dt>
				<dd>
					<input type="text" name="amount" class="required number" size="70" value="${robotTrade.amount}" />
				</dd>
			</dl>
			<dl>
				<dt>挂单帐号：</dt>
				<dd>
					<input type="hidden" name="userLookup.id" value="${robotTrade.user.fid}"/>
					<input type="text" class="required" name="userLookup.floginName" value="${robotTrade.user.floginName}" suggestFields="id,floginName"
						   suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
					<a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="userLookup">查找带回</a>
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
