<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加支付宝帐户信息</h2>


<div class="pageContent">
	
	<form method="post" action="ssadmin/saveSysAlipay.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>开户名称：</dt>
				<dd>
					<input type="text" name="systemBank.fbankName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>应用id：</dt>
				<dd>
					<input type="text" name="systemBank.fownerName" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>私钥：</dt>
				<dd>
					<input type="text" name="systemBank.fbankAddress" maxlength="1000" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>支付宝帐号：</dt>
				<dd>
					<input type="text" name="systemBank.fbankNumber" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
			<dl>
				<dt>AppId：</dt>
				<dd>
					<input type="text" name="systemBank.fappId" maxlength="40" class="required" size="70"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
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
