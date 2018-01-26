<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/sysOperatorlog.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	     <input  type="hidden" name = "model" value="${model}">
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/sysOperatorlog.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>IP地址：<input type="text" name="keywords"
						value="${keywords}" size="60" /></td>
					<td>
						操作模块：<select type="combox" name ="model">
						         <c:forEach items="${models}" var="modul">
									 <c:if test="${modul==model}">
										 <option value="${modul}" selected="true">${modul}</option>
									 </c:if>
									 <c:if test="${modul!=model}">
										 <option value="${modul}">${modul}</option>
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
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">操作人</th>
				<th width="60">操作时间</th>
				<th width="60">模块</th>
				<th width="60">IP地址</th>
				<th width="60">操作代码</th>
				<th width="60">操作名称</th>
				<th width="60">执行类名</th>
				<th width="60">执行方法名</th>
				<th width="60">请求参数</th>
				<th width="60">答应结果</th>
				<th width="60">操作内容</th>
				<th width="60">操作是否成功</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${syslogList}" var="syslog" varStatus="num">
				<tr target="sid_user" rel="${syslog.id}">
					<td>${num.index +1}</td>
					<td>${syslog.loginName}</td>
					<td>${syslog.operatorDate}</td>
					<td>${syslog.module}</td>
					<td>${syslog.ip}</td>
					<td>${syslog.operatorCode}</td>
					<td>${syslog.operatorName}</td>
					<td>${syslog.className}</td>
					<td>${syslog.methodName}</td>
					<td>${syslog.requestParameters}</td>
					<td>${syslog.responseResult}</td>
					<td>${syslog.operatorContent}</td>
					<td>${syslog.issuccess}</td>
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
