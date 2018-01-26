<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/robotTradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/robotTradeList.html" method="post">
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
					name="ssadmin/saveRobotTrade.html">
				<li><a class="add"
					   href="ssadmin/goRobotTradeJSP.html?url=ssadmin/robot/saveRobotTrade"
					   height="350" width="800" target="dialog" rel="saveRobotTrade" ><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/updateRobotTrade.html">
				<li><a class="edit" href="ssadmin/goRobotTradeJSP.html?url=ssadmin/robot/updateRobotTrade&uid={sid_user}"
					   height="350" width="700" target="dialog" rel="updateRobotTrade" ><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/cancelRobotTrade.html">
				<li>
					<a class="edit"
					   href="ssadmin/cancelRobotTrade.html?uid={sid_user}"
					   target="ajaxTodo" title="确认是否取消交易配置?" ><span>取消配置</span>
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/startRobotTrade.html">
				<li><a class="edit"
					   href="ssadmin/robotTrade.html?uid={sid_user}&status=1"
					   target="ajaxTodo" title="确定要启动下单线程吗?"><span>启动线程</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/stopRobotTrade.html">
			<li><a class="edit"
				   href="ssadmin/robotTrade.html?uid={sid_user}"
				   target="ajaxTodo" title="确定要停止下单线程吗?" ><span>停止线程</span>
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
				<th width="60">数量</th>
				<th width="60">下单数量</th>
				<th width="60">成本</th>
				<th width="60">状态</th>
				<th width="60">下单用户</th>
				<th width="60">创建日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${robotTradeList}" var="temp" varStatus="num">
			<tr target="sid_user" rel="${temp.id}">
				<td>${num.index+1}</td>
				<td>${temp.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
				<td>${temp.type_s}</td>
				<td>${temp.amount}</td>
				<td>${temp.dealAmount}</td>
				<td>${temp.cost}</td>
				<td>${temp.status_s}</td>
				<td>${temp.fuser.floginName}</td>
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