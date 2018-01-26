<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/integralDetail.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="uid" value="${uid}" /><input
		type="hidden" name="startDate" value="${startDate}" />  <input
		type="hidden" name="troNo" value="${troNo}" />
	    <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/integralDetail.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<input type="hidden"  name="userId" value="${userId}" />
					<td>起止日期 <input type="text" name="start" class="date"
										readonly="true" value="${startDate}" />~

						<input type="text" name="end" class="date"
							   readonly="true" value="${endDate}" />
					</td>
					<td>操作类型： <select type="combox" name="type">
						<c:forEach items="${typeMap}" var="type">
							<c:if test="${type.key == 0}">
								<option value="${type.key}" selected="true">全部</option>
							</c:if>
							<c:if test="${type.key != 0}">
								<option value="${type.key}" >${type.value}</option>
							</c:if>
						</c:forEach>
					</select>
					</td>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="ssadmin/userExport.html"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出</span>
			</a></li>
		</ul>

	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="60">时间</th>
				<th width="60">积分</th>
				<th width="60">操作</th>
				<th width="60">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${detaillist}" var="integral" varStatus="num">
				<tr target="sid_user" rel="${integral.id}">
					<td>${integral.createDate}</td>
					<td>${integral.operateAmount}</td>
					<td><c:forEach items="${typeMap}" var="type">
						<c:if test="${type.key == integral.type}">
							${type.value}
						</c:if>
					</c:forEach>
					</td>
					<td>${integral.remark}</td>
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
