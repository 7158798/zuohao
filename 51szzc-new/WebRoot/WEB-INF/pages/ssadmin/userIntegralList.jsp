<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/userIntegralList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="flevel" value="${flevel}" /><input
		type="hidden" name="uid" value="${uid}" /><input
		type="hidden" name="startDate" value="${startDate}" />  <input
		type="hidden" name="troNo" value="${troNo}" />
	    <input type="hidden" name="integral1" value="${integral1}" />
	    <input type="hidden" name="integral2" value="${integral2}" />
	    <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/userIntegralList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="40" />
					</td>
					<td>会员等级： <select type="combox" name="flevel">

						       <option value="0" selected="true">全部</option>
						        <option value="1">VIP1</option>
						<option value="2">VIP2</option>
						<option value="3">VIP3</option>
						<option value="4">VIP4</option>
						<option value="5">VIP5</option>

					</select></td>
					<td>积分：<input type="text" name="integral1"
									value="${keywords}" size="5" />~<input type="text" name="integral2"
																			value="${keywords}" size="5" />
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
		<li>
			<shiro:hasPermission name="ssadmin/updateIntegral.html">
			<li><a class="edit"
				   href="ssadmin/goIntegralJSP.html?url=ssadmin/updateUserIntegral&uid={sid_user}"
				   height="400" width="700" target="dialog" rel="updateUserIntegral"><span>修改积分</span>
			</a></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="ssadmin/integralDetail.html">

				<li><a class="edit"
					   href="ssadmin/integralDetail.html?userId={sid_user}"
					   target="navTab" rel="integralDetail"><span>积分记录</span>
				</a></li>
			</shiro:hasPermission>
			<%--<li><a class="icon" href="ssadmin/userExport.html"--%>
				   <%--target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出</span>--%>
			<%--</a></li>--%>
		</ul>

	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60">手机</th>
				<th width="60">邮箱</th>
				<th width="60">真实姓名</th>
				<th width="60" orderField="fscore.flevel"
					<c:if test='${param.orderField == "fscore.flevel" }'> class="${param.orderDirection}"  </c:if>>会员等级</th>
				<th width="60" orderField="integral"
					<c:if test='${param.orderField == "integral" }'> class="${param.orderDirection}"  </c:if>>总积分</th>

				<th width="60">登录积分</th>
				<th width="60">充值人民币积分</th>
				<th width="60">充值数字货币积分</th>
				<th width="60">交易积分</th>
				<th width="60">每日净资产积分</th>
				<th width="60">邀请好友积分</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userlist}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td>${user.fid}</td>
					<td>${user.ftelephone}</td>
					<td>${user.femail}</td>
					<td>${user.frealName}</td>
					<td>${user.fscore.flevel}</td>
					<td>${user.integral}</td>

					<td>${user.integralArray[0]}</td>
					<td>${user.integralArray[1]}</td>
					<td>${user.integralArray[2]}</td>
					<td>${user.integralArray[3]}</td>
					<td>${user.integralArray[4]}</td>
					<td>${user.integralArray[5]}</td>

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
