<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">修改虚拟币类型信息</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/updateVirtualCoinType.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
		    <dl>
				<dt>图标链接：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>币种名称：</dt>
				<dd>
					<input type="hidden" name="fid" value="${virtualCoinType.fid}" />
					<input type="text" name="fname" class="required"
						size="70" value="${virtualCoinType.fname}" />
				</dd>
			</dl>
			<dl>
				<dt>币种简称：</dt>
				<dd>
					<input type="text" name="fShortName"
						class="required" size="70" value="${virtualCoinType.fShortName}" />
				</dd>
			</dl>
			<dl>
				<dt>符号：</dt>
				<dd>
					<input type="text" name="fSymbol" class="required"
						size="40" value="${virtualCoinType.fSymbol}" />
				</dd>
			</dl>
			<dl>
				<dt>ACCESS_KEY：</dt>
				<dd>
					<input type="password" name="faccess_key"
						class="required" size="70" value="${virtualCoinType.faccess_key}" />
				</dd>
			</dl>
			<dl>
				<dt>SECRT_KEY：</dt>
				<dd>
					<input type="password" name="fsecrt_key"
						class="required" size="70" value="${virtualCoinType.fsecrt_key}" />
				</dd>
			</dl>
			<dl>
				<dt>IP地址：</dt>
				<dd>
					<input type="text" name="fip" class="required"
						size="70" value="${virtualCoinType.fip}" />
				</dd>
			</dl>
			<dl>
				<dt>端口号：</dt>
				<dd>
					<input type="text" name="fport"
						class="required number" size="30" value="${virtualCoinType.fport}" />
				</dd>
			</dl>
			<dl>
				<dt>最小提现数量：</dt>
				<dd>
					<input type="text" name="fminqty" class="required number"
						size="70" value="<fmt:formatNumber value="${virtualCoinType.fminqty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/>" />
				</dd>
			</dl>
			<dl>
				<dt>最大提现数量：</dt>
				<dd>
					<input type="text" name="fmaxqty" class="required number"
						size="70" value="<fmt:formatNumber value="${virtualCoinType.fmaxqty}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="4"/>" />
				</dd>
			</dl>
			<dl>
				<dt>是否可以充值：</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.fisrecharge}">
							<input type="checkbox" name="fisrecharge" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisrecharge" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>是否可以提现：</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.FIsWithDraw}">
							<input type="checkbox" name="FIsWithDraw" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="FIsWithDraw" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			
			<dl>
				<dt>是否以太坊：</dt>
				<dd>
				<c:choose>
						<c:when test="${virtualCoinType.fisEth}">
							<input type="checkbox" name="fisEth" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisEth" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>以太坊汇总地址：</dt>
				<dd>
					<input type="text" name="mainAddr"
						class="" size="70" value="${virtualCoinType.mainAddr }"/>
				</dd>
			</dl>
			<dl>
				<dt>充值是否自动到账：</dt>
				<dd>
					<c:choose>
						<c:when test="${virtualCoinType.fisauto}">
							<input type="checkbox" name="fisauto" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisauto" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>手续费说明：</dt>
				<dd>
				<textarea rows="3" cols="80" name="fdescription">${virtualCoinType.fdescription}</textarea>
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
