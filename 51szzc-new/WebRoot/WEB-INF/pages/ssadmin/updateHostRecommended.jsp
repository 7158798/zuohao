<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改推荐上限</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateHostRecommended.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>热门推荐：</dt>
				<dd>
					<input type="hidden" name="hostNumberId" value="${hostNumberId}" />
					<input type="text" name="hostNumber" maxlength="30"
						class="required digits" size="70" value="${hostNumber}" />
				</dd>
			</dl><dl>
				<dt>单个资讯类型推荐：</dt>
				<dd>
					<input type="hidden" name="singleNumberId" value="${singleNumberId}" />
					<input type="text" name="singleNumber" maxlength="30"
						class="required digits" size="70" value="${singleNumber}" />
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
