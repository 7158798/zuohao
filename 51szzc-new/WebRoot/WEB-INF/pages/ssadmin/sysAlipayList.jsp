<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/sysAlipayList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/sysAlipayList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>标题：<input type="text" name="keywords" value="${keywords}"
						size="60" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/addSysAlipay.html">
				<li><a class="add"
					href="ssadmin/goSysAlipayJSP.html?url=ssadmin/addSysAlipay"
					height="300" width="800" target="dialog" rel="sysAlipayList"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/forbbinSysAlipay.html?status=1">
				<li><a class="delete"
					href="ssadmin/forbbinSysAlipay.html?uid={sid_user}&status=1"
					target="ajaxTodo" title="确定要禁用吗?"><span>禁用</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/forbbinSysAlipay.html?status=2">
				<li><a class="edit"
					href="ssadmin/forbbinSysAlipay.html?uid={sid_user}&status=2"
					target="ajaxTodo" title="确定要解除禁用吗?"><span>解除禁用</span> </a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">开户名称</th>
				<th width="60">状态</th>
				<th width="60">应用id</th>
				<th width="60">私钥</th>
				<th width="60">支付宝帐号</th>
				<th width="60">AppId</th>
				<th width="60">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${systembankList}" var="systembank" varStatus="num">
				<tr target="sid_user" rel="${systembank.fid}">
					<td>${num.index +1}</td>
					<td>${systembank.fbankName}</td>
					<td>${systembank.fstatus_s}</td>
					<td>${systembank.fownerName}</td>
					<td title="${systembank.fbankAddress}">
						<c:choose>
							<c:when test="${systembank.fbankAddress.length() <= 10}">
								${systembank.fbankAddress}
							</c:when>
							<c:otherwise>
								${systembank.fbankAddress.substring(0,10)}
							</c:otherwise>
						</c:choose>
					</td>
					<td>${systembank.fbankNumber}</td>
					<td>${systembank.fappId}</td>
					<td><fmt:formatDate value="${systembank.fcreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
