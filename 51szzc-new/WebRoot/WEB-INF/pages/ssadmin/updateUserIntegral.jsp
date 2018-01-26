<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">
	修改会员积分<font color="red">${fuser.floginName}</font>
</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateIntegeral.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<input type="hidden" name="uid" value="${fuser.fid}" />
				<dt>UID：</dt>
				<dd>
					${fuser.fid}
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					${fuser.fid}
				</dd>
			</dl>
			<dl>
				<dt>邮箱：</dt>
				<dd>
					${fuser.femail}
				</dd>
			</dl>
			<dl>
				<dt>真实姓名：</dt>
				<dd>
					${fuser.frealName}
				</dd>
			</dl>
			<dl>
				<dt>当前总积分：</dt>
				<dd>
					${fuser.integral}
				</dd>
			</dl>

			<dl>
				<dt>改动积分类型：</dt>
				<dd>
					<select type="combox" name="type">
						<c:forEach items="${map}" var="type">
							<option value="${type.key}" selected="true">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>

			<dl>
				<dt>改动积分数值：</dt>
				<dd>
					<input type="text" name="integral" class="required number" size="20" />
				</dd>
			</dl>

			<dl>
				<dt>改动缘由：</dt>
				<dd>
					<select type="combox" name="remark">
						<option value="刷分" selected="true">刷分</option>
						<option value="补偿" selected="true">补偿</option>
						<option value="奖励" selected="true">奖励</option>
						<option value="其他" selected="true">其他</option>
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
