<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/sysLoginLog.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/sysLoginLog.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>IP地址：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="80%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="50">登录人</th>
				<th width="80">登录时间</th>
				<th width="80">IP地址</th>
				<th width="60">登陆时长</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sysLoginLogList}" var="list" varStatus="num">
				<tr target="sid_user" rel="${syslog.id}">
					<td>${num.index +1}</td>
					<td>${list.fkey2}</td>
					<td><fmt:formatDate value="${list.fcreateTime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
					<td>${list.fkey3}</td>
					<td>${list.fkey5}</td>
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
