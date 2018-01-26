<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	添加银行卡<font color="red">${fuser.floginName}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/addBankAccount.html"
		  class="pageForm required-validate"
		  onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>开户姓名：</dt>
				<dd>
					<input type="hidden" name="fid" value="${fuser.fid}" /> <input
						type="text" name="payeeAddr" maxlength="20" value="${fuser.frealName}" readonly="readonly"/>
				</dd>
			</dl>
			<dl>
				<dt>银行卡号：</dt>
				<dd>
					 <input
						type="text" name="account"  class="required" maxlength="20"  />
				</dd>
			</dl>
			<dl>
				<dt>开户银行：</dt>
				<dd>
					<select name="openBankType" class="form-control ">
						<option value="-1">
							请选择银行类型
						</option>
						<c:forEach items="${bankTypes }" var="v">
							<option value="${v.key }">${v.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>开户城市/地区：</dt>
				<dd>
					<input
							type="text" name="city" class="required" maxlength="20"  />
				</dd>
			</dl>
			<dl>
				<dt>开户详细地址：</dt>
				<dd>
					<input
							type="text" name="address" class="required" maxlength="100"  />
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
