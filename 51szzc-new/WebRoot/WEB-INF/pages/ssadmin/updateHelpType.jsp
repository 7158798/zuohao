<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改帮助类型信息</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateHelpType.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>类型名称：</dt>
				<dd>
					<input type="hidden" name="fid" value="${fhelptype.fid}">
					<input type="text" name="fname" maxlength="30"
						class="required" size="30" value="${fhelptype.fname}"/>
				</dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd>
					<textarea name="fdescription" style="width: 200px; height: 150px;" maxlength="30">${fhelptype.fdescription}</textarea><span style="color: red;">(最多输入30字)</span>
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
