<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">修改委托预警</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateEntrustWarn.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden" name="id" value="${entrustWarn.id}" />
			<dl>
				<dt>货币类型：</dt>
				<dd>
					${entrustWarn.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					${entrustWarn.type_s}
				</dd>
			</dl>
			<dl>
				<dt>深度数量：</dt>
				<dd>
					<input type="text" name="mergeAmount" class="required number" size="70" value="${entrustWarn.mergeAmount}" />
				</dd>
			</dl>
			<%--<dl>
				<dt>等待分钟：</dt>
				<dd>
					<input type="text" name="waitMinute" class="required number" size="70" value="${entrustWarn.waitMinute}" />
				</dd>
			</dl>--%>
			<dl>
				<dt>手机号码：</dt>
				<dd>
					<input type="text" name="mobileNumber" class="required" size="70" value="${entrustWarn.mobileNumber}" /> 注：多个手机号码以逗号分隔
				</dd>
			</dl>
			<dl>
				<dt>短信休眠时间(分钟)：</dt>
				<dd>
					<input type="text" name="pauseMsgTime" class="required number" size="70" value="${entrustWarn.pauseMsgTime}" />
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
