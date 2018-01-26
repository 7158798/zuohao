<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" reload="no" method="post" action="ssadmin/fastTradeList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/fastTradeList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
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
				</tr>
				<tr>
					<td>跳过撮合：<input id="skipPair" type="checkbox" value="1"></td>
				</tr>
		<%--		<tr>
					<td>随机数量：<input type="text" id="num" name="num" value="${num}" size="60" />注：线程使用（2,10）2-10之间的随机,不包含10</td>
				</tr>
				<tr>
					<td>休眠时间：<input type="text" id="time" name="time"	value="${price}" size="60" />注：单位分钟,线程使用（2,10）2-10之间的随机,不包含10</td>
				</tr>
--%>
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
					name="ssadmin/addFastTrade.html">
				<li><a class="add"
					   href="ssadmin/goFastTradeJsp.html?url=ssadmin/addFastTrade"
					   height="350" width="800" target="dialog" rel="addFastTrade" ><span>新增</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
					name="ssadmin/updateFastTrade.html">
				<li><a class="edit" href="ssadmin/goFastTradeJsp.html?url=ssadmin/updateFastTrade&uid={sid_user}"
					   height="350" width="800" target="dialog" rel="updateFastTrade" ><span>修改</span>
				</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission
				name="ssadmin/addAmount.html">
			<li><a class="edit"
				   href="#"
				   target="ajaxTodo" title="确认是否增加交易量?" uid="{sid_user}" onclick="addAmount(this)"><span>增加交易量</span>
			</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission
				name="ssadmin/startAddAmount.html">
			<li><a class="edit"
				   href="ssadmin/autoAddAmount.html?uid={sid_user}&status=1"
				   target="ajaxTodo" title="确认是否启动线程?" uid="{sid_user}" onclick="autoAddAmount(this,'1')"><span>启动线程</span>
			</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission
				name="ssadmin/stopAddAmount.html">
			<li><a class="edit"
				   href="ssadmin/autoAddAmount.html?uid={sid_user}&status=0"
				   target="ajaxTodo" title="确认是否停止线程?" uid="{sid_user}" onclick="autoAddAmount(this,'0')"><span>停止线程</span>
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
				<th width="60">交易用户</th>
				<th width="60">随机数量</th>
				<th width="60">休眠时间</th>
				<th width="60">创建日期</th>
				<th width="60">修改日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${fastTradeList}" var="t" varStatus="num">
			<tr target="sid_user" rel="${t.id}">
				<td>${num.index+1 }</td>
				<td>${t.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
				<td>${t.user.floginName }</td>
				<td>${t.randomNum }</td>
				<td>${t.randomTime }</td>
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
	function addAmount(obj){
		var uid = $(obj).attr("uid");
		var href = "ssadmin/addAmount.html?uid=" + uid;
		var skipPair = $("#skipPair:checked").val();
		var mobile = $("#mobile").val();
		var amount = $("#amount").val();
		var price = $("#price").val();
		href = href + "&mobile=" + mobile + "&amount=" + amount + "&price=" + price + "&skipPair=" + skipPair;
		$(obj).attr("href",href);
	}
</script>