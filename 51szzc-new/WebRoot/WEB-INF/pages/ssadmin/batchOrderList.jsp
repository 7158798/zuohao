<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/batchOrderList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/batchOrderList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>用户手机：<input type="text" id="mobilePhone" name="mobilePhone"
						value="${mobilePhone}" size="60" /></td>
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
					name="ssadmin/addBatchOrder.html">
				<li><a class="add"
					   href="ssadmin/goBatchOrderJsp.html?url=ssadmin/addBatchOrder"
					   height="350" width="800" target="dialog" rel="addBatchOrder" ><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/cancelBatchOrder.html">
				<li>
					<a class="edit"
					   href="ssadmin/cancelBatchOrder.html?uid={sid_user}"
					   target="ajaxTodo" title="确认是否取消订单?" ><span>取消下单</span>
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/cancelUserOrder.html">
				<li>
					<a class="edit"
					   href="#"
					   target="ajaxTodo" title="确认是否取消用户的所有委托订单?" onclick="cancelUserOrder(this)" ><span>取消用户下单</span>
					</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">交易币名称</th>
				<th width="60">订单类型</th>
				<th width="60">交易用户</th>
				<th width="60">金额范围</th>
				<th width="60">数量范围</th>
				<th width="60">订单数</th>
				<th width="60">订单状态</th>
				<th width="60">创建日期</th>
				<th width="60">修改日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${batchOrderList}" var="t" varStatus="num">
			<tr target="sid_user" rel="${t.id}">
				<td>${num.index+1 }</td>
				<td>${t.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
				<td>${t.type_s}</td>
				<td>${t.user.floginName }</td>
				<td>${t.priceScope }</td>
				<td>${t.amountScope }</td>
				<td>${t.orderNum }</td>
				<td>${t.status_s }</td>
				<td>${t.createDate }</td>
				<td>${t.modifiedDate }</td>
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

<script>
	function cancelUserOrder(obj){
		var mobile = $("#mobilePhone").val();
		var href = "ssadmin/cancelUserOrder.html?mobilePhone=" + mobile;
		$(obj).attr("href",href);
	}
</script>