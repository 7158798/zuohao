<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/gradeSetList.html">
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
		action="ssadmin/gradeSetList.html" method="post">
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
			<shiro:hasPermission name="ssadmin/updateGradeSet.html">
				<li>
					<a class="edit"
					   href="ssadmin/goGradeJSP.html?uid={sid_user}&url=ssadmin/updateGrade"
					   target="dialog" rel="updateGradeSet"><span>修改</span>
					</a>
				</li>
			</shiro:hasPermission>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>序号</th>
				<th width="60">会员等级</th>
				<th width="60">所需积分</th>
				<th width="60">提现手续费</th>
				<th width="60">人民币充值手续费</th>
				<th width="60">数字货币充值手续费</th>
				<th width="60">交易手续费</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gradelist}" var="grade" varStatus="num">
				<tr target="sid_user" rel="${grade.fid}">
					<td>${grade.fid}</td>
					<td>${grade.ftitle}</td>
					<td>${grade.fneedintegral}</td>
					<td>${grade.foutfee}</td>
					<td>${grade.fcapitalinfee}</td>
					<td>${grade.virtualinfee}</td>
					<td>${grade.tradefee}</td>
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
