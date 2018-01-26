<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">修改机器挂单配置</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateRobotTrade.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden" name="id" value="${robotTrade.id}" />
			<dl>
				<dt>货币类型：</dt>
				<dd>
					${robotTrade.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					${robotTrade.type_s}
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
					<input type="hidden" name="userLookup.id" value="${robotTrade.fuser.fid}"/>
					<input type="text" class="required" name="userLookup.floginName" value="${robotTrade.fuser.floginName}" suggestFields="id,floginName"
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
