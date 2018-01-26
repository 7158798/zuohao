<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/entrustWarnList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/entrustWarnList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>货币： <select type="combox" name="status">
										<c:forEach items="${typeMap}" var="type">
											<c:if test="${type.key == status}">
												<option value="${type.key}" selected="true">${type.value}</option>
											</c:if>
											<c:if test="${type.key != status}">
												<option value="${type.key}">${type.value}</option>
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
			<shiro:hasPermission
					name="ssadmin/saveEntrustWarn.html">
				<li><a class="add"
					   href="ssadmin/goEntrustWarnJSP.html?url=ssadmin/warn/saveEntrustWarn"
					   height="350" width="800" target="dialog" rel="saveRobotTrade" ><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/updateEntrustWarn.html">
				<li><a class="edit" href="ssadmin/goEntrustWarnJSP.html?url=ssadmin/warn/updateEntrustWarn&uid={sid_user}"
					   height="350" width="700" target="dialog" rel="updateRobotTrade" ><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/startEntrustWarn.html">
				<li><a class="edit"
					   href="ssadmin/entrustWarn.html?uid={sid_user}&status=1"
					   target="ajaxTodo" title="确定要启动预警线程吗?"><span>启动预警线程</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/stopEntrustWarn.html">
			<li><a class="edit"
				   href="ssadmin/entrustWarn.html?uid={sid_user}"
				   target="ajaxTodo" title="确定要停止预警线程吗?" ><span>停止预警线程</span>
			</a>
			</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="60">序号</th>
				<th width="60">货币类型</th>
				<th width="60">类型</th>
				<th width="60">深度数量</th>
				<%--<th width="60">等待分钟</th>--%>
				<th width="60">手机号码</th>
				<th width="60">短信休眠分钟</th>
				<th width="60">创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${entrustWarnList}" var="temp" varStatus="num">
			<tr target="sid_user" rel="${temp.id}">
				<td>${num.index+1}</td>
				<td>${temp.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
				<td>${temp.type_s}</td>
				<td>${temp.mergeAmount}</td>
				<%--<td>${temp.waitMinute}</td>--%>
				<td>${temp.mobileNumber}</td>
				<td>${temp.pauseMsgTime}</td>
				<td>${temp.createDate}</td>
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