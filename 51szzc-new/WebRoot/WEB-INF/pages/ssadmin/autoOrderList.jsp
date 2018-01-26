<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/autoOrderList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/autoOrderList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<%--<tr>
					<td>用户手机：<input type="text" id="mobile" name="mobile"
						value="${mobile}" size="60" /></td>
				</tr>
				<tr>
					<td>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：<input type="text" id="amount" name="amount"
									value="${amount}" size="60" /></td>
				</tr>
				<tr>
					<td>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：<input type="text" id="price" name="price"
									value="${price}" size="60" /></td>
				</tr>--%>
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
					name="ssadmin/addAutoOrder.html">
				<li><a class="add"
					   href="ssadmin/goAutoOrderJSP.html?url=ssadmin/addAutoOrder"
					   height="350" width="800" target="dialog" rel="addAutoOrder"><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/updateAutoOrder.html">
			<li><a class="edit"
				   href="ssadmin/goAutoOrderJSP.html?url=ssadmin/updateAutoOrder&uid={sid_user}"
				   target="dialog" height="350" width="800" rel="updateAutoOrder" ><span>修改</span> </a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/startAutoOrder.html">
			<li><a class="edit"
				   href="ssadmin/autoOrder.html?uid={sid_user}&status=1"
				   target="ajaxTodo" title="确定要启动下单线程吗?"><span>启动线程</span>
			</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/stopAutoOrder.html">
			<li><a class="edit"
				   href="ssadmin/autoOrder.html?uid={sid_user}"
				   target="ajaxTodo" title="确定要停止下单线程吗?" ><span>停止线程</span>
			</a>
			</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20" >序号</th>
				<th width="60" >货币类型</th>
				<th width="60" >挂单数量</th>
				<th width="60" >数量比例</th>
				<th width="60" >取消订单(分钟)</th>
				<th width="60" >挂单帐号</th>
				<th width="60" >备选帐号</th>
				<th width="60" >人民币</th>
				<th width="60" >虚拟币</th>
				<th width="60" >成本单价</th>
				<th width="60" >随机单价区间</th>
				<th width="60" >下单休眠时间</th>
				<th width="60" >请求休眠时间</th>
				<th width="60" >创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${autoOrderList}" var="autoOrder" varStatus="num">
				<tr target="sid_user" rel="${autoOrder.id}">
					<td>${num.index +1}</td>
					<td>${autoOrder.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
					<td>${autoOrder.random}</td>
					<td>${autoOrder.ratio}</td>
					<td>${autoOrder.cancelTime}</td>
					<td>${autoOrder.user.floginName}</td>
					<td>${autoOrder.reserveUser.floginName}</td>
					<td>${autoOrder.allCny}</td>
					<td>${autoOrder.allXnb}</td>
					<td>${autoOrder.costPrice}</td>
					<td>${autoOrder.priceScope}</td>
					<td>${autoOrder.sleepTime}</td>
					<td>${autoOrder.reqSleepTime}</td>
					<td>${autoOrder.createDate}</td>
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