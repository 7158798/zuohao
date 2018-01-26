<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">更新积分获取途径</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateIntegralactivity.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>操作行为：</dt>
				<dd>
					<input type="hidden" name="id" value="${integralactivity.id}"/>
					<select type="combox" name="type">
						<c:forEach items="${typeNameList}" var="typeName">
							<c:if test="${typeName.key == integralactivity.type}">
								<option value="${typeName.key}" selected="true">${typeName.value}</option>
							</c:if>
							<c:if test="${typeName.key != integralactivity.type}">
								<option value="${typeName.key}">${typeName.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>增加积分：</dt>
				<dd>
					<input type="text" name="integral" maxlength="100" class="required number" size="70" value="${integralactivity.integral}"/>
				</dd>
			</dl>
			<dl>
				<dt>每日获取上线：</dt>
				<dd>
					<input type="text" name="dayMax" maxlength="200" class="number" size="70" value="${integralactivity.dayMax}"/>
				</dd>
			</dl>
			<dl>
				<dt>充值金额：</dt>
				<dd>
					<input type="text" name="needAmount" maxlength="200" class="required number" size="70" value="${integralactivity.needAmount}"/>
				</dd>
			</dl>
			<dl>
				<dt>说明：</dt>
				<dd>
					<textarea type="text" name="content" maxlength="200"  size="70" >${integralactivity.content}</textarea>
				</dd>
			</dl>
			<dl>
				<dt>开始日期： </dt>
				<dd>
					<input type="text" name="beginTimeStr" class="date required"
										 readonly="true" value="<fmt:formatDate value="${integralactivity.beginTime}" pattern="yyyy-MM-dd"/>"/>
				</dd>
				<dt>结束日期： </dt>
				<dd>
					<input type="text" name="endTimeStr" class="date required"
						   readonly="true" value="<fmt:formatDate value="${integralactivity.endTime}" pattern="yyyy-MM-dd"/>"/>
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
