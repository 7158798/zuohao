<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	实名认证<font color="red">${fuser.floginName}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/validateIdentity.html"
		  class="pageForm required-validate"
		  onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>真实姓名：</dt>
				<dd>
					<input type="hidden" name="fid" value="${fuser.fid}" /> <input
						type="text" class="required" name="realName" maxlength="20"/>
				</dd>
			</dl>
			<dl>
				<dt>身份证号码：</dt>
				<dd>
					 <input
						type="text" class="required" name="identityNo" maxlength="20"  />
				</dd>
			</dl>
			<dl>
				<dt>手机号码：</dt>
				<dd>
					<input
							type="text" name="phone" maxlength="20"  />
				</dd>
			</dl>
			<dl>
				<dt>交易密码：</dt>
				<dd>
					<input
							type="text" name="tradePwd" maxlength="20"  />
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
