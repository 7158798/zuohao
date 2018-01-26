<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	修改:<font color="red">${fauditprocess.ftype_s}</font> 审核流程
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateAuditProcess.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap"  layoutH="90">

			<dl>
				<input type="hidden" name="uid" value="${fauditprocess.fId}" />
				<dt>审核类型：</dt>
				<dd>
					${fauditprocess.ftype_s}
				</dd>
			</dl>
			<dl>
				<dt>一级审核：</dt>
				<dd>
					<select
						type="combox" name="role1">
						<c:forEach items="${roleMap}" var="role">
							<c:if test="${role.key == fauditprocess.frole1.fid}">
								<option value="${role.key}" selected="true">${role.value}</option>
							</c:if>
							<c:if test="${role.key != fauditprocess.frole1.fid}">
								<option value="${role.key}">${role.value}</option>
							</c:if>
						</c:forEach>
						<option value="0">空</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>二级级审核：</dt>
				<dd>
					<select
							type="combox" name="role2">
						<c:if test="${fauditprocess.frole2.fid == null}">
							<option value="0" selected="true">空</option>
						</c:if>
						<c:forEach items="${roleMap}" var="role">
							<c:if test="${role.key == fauditprocess.frole2.fid}">
								<option value="${role.key}" selected="true">${role.value}</option>
							</c:if>
							<c:if test="${role.key != fauditprocess.frole2.fid}">
								<option value="${role.key}">${role.value}</option>
							</c:if>
						</c:forEach>
						<option value="0">空</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>三级级审核：</dt>
				<dd>
					<select
							type="combox" name="role3">
						<c:if test="${fauditprocess.frole3.fid == null}">
							<option value="0" selected="true">空</option>
						</c:if>
						<c:forEach items="${roleMap}" var="role">

							<c:if test="${role.key == fauditprocess.frole3.fid}">
								<option value="${role.key}" selected="true">${role.value}</option>
							</c:if>
							<c:if test="${role.key != fauditprocess.frole3.fid}">
								<option value="${role.key}">${role.value}</option>
							</c:if>
						</c:forEach>
						<option value="0">空</option>

					</select>

				</dd>
			</dl>
			<dl>
				<dt>是否需要确认密码：</dt>
				<dd>
					<select
							type="combox" name="fIsneedPwd">

							<c:if test="${fauditprocess.fIsneedPwd == 0}">
								<option value="0" selected="true">否</option>
								<option value="1" >是</option>
							</c:if>
							<c:if test="${fauditprocess.fIsneedPwd == 1}">
								<option value="1" selected="true">是</option>
								<option value="0" >否</option>
							</c:if>
					</select>
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
