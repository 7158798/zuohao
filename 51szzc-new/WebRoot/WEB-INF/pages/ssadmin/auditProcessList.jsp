<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/auditProcessList.html">
	     <input
		type="hidden" name="uid" value="${uid}" />
	     <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/auditProcessList.html" method="post">
		<div class="searchBar">

			<%--<table class="searchContent">--%>
				<%--<tr>--%>
					<%--<td>会员信息：<input type="text" name="keywords"--%>
						<%--value="${keywords}" size="40" />--%>
					<%--</td>--%>
				<%--</tr>--%>
			<%--</table>--%>
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
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/updateAuditProcess.html">
				<li>
					<a class="edit"
					   href="ssadmin/goAuditProcessJSP.html?uid={sid_user}&url=ssadmin/updateAuditProcess&uid={sid_user}"
					   height="350" width="700" target="dialog" rel="updateAuditProcess"><span>修改</span>
					</a>
				</li>
			</shiro:hasPermission>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>序号</th>
				<th width="60">审核类型</th>
				<th width="60">一级审核</th>
				<th width="60">二级审核</th>
				<th width="60">三级审核</th>
				<th width="60">是否需要确认密码</th>
				<th width="60">最新修改时间</th>
				<th width="60">最新修改人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${auditProcessList}" var="audit" varStatus="num">
				<tr target="sid_user" rel="${audit.fId}">
					<td>${audit.fId}</td>
					<td>${audit.ftype_s}</td>
					<td>${audit.frole1.fname}</td>
					<td>${audit.frole2.fname}</td>
					<td>${audit.frole3.fname}</td>
					<td> <c:if test="${audit.fIsneedPwd == 0}">
						否
					    </c:if>
						<c:if test="${audit.fIsneedPwd == 1}">
							是
						</c:if>
					</td>
					<td>${audit.fLastUpdateTime}</td>
					<td>${audit.fmodify_id.fname}</td>
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
