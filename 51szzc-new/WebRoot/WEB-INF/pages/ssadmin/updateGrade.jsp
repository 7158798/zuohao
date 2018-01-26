<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	修改<font color="red">${fusergrade.ftitle}</font>福利
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateGrade.html"
		  class="pageForm required-validate"
		  onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>所需积分：</dt>
				<dd>
					<input type="hidden" name="fid" value="${fusergrade.fid}" />
					<input type="text" class="required" name="fneedintegral" maxlength="20" value="${fusergrade.fneedintegral}"/>
				</dd>
			</dl>

			<dl>
				<dt>提现手续费：</dt>
				<dd>
					<input type="text" class="required" name="foutfee" maxlength="20" value="${fusergrade.foutfee}"/>%
				</dd>
			</dl>
			<dl>
				<dt>人民币充值：</dt>
				<dd>
					<input type="text" class="required" name="fcapitalinfee" maxlength="20" value="${fusergrade.fcapitalinfee}"/>%
				</dd>
			</dl>
			<dl>
				<dt>数字货币充值：</dt>
				<dd>
					<input type="text" class="required" name="virtualinfee" maxlength="20" value="${fusergrade.virtualinfee}"/>%
				</dd>
			</dl>
			<dl>
				<dt>交易手续费：</dt>
				<dd>
					<input type="text" class="required" name="tradefee" maxlength="20" value="${fusergrade.tradefee}"/>%
				</dd>
			</dl>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">保存</button>
					</div>
				</div>
				</li>
				<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">取消</button>
					</div>
				</div>
				</li>
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
