<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/userKycList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="uid" value="${uid}" />
	     <input type="hidden" name="pageNum"
		value="${currentPage}" /> <input type="hidden" name="numPerPage"
		value="${numPerPage}" /> <input type="hidden" name="orderField"
		value="${param.orderField}" /> <input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/userKycList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="40" />
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
			<shiro:hasPermission name="ssadmin/auditingKyc.html">
				<li>
					<a class="edit"
					   href="ssadmin/auditingKyc.html?uid={sid_user}&status=2&rel=userKycList" target="ajaxTodo"
					   rel="userKycList" title="确定要审核吗?"><span>审核</span>
					</a>
				</li>
			</shiro:hasPermission>

            <shiro:hasPermission name="ssadmin/notPassKyc.html">
				<li>
					<a class="edit"
					   href="ssadmin/goKycJSP.html?uid={sid_user}&url=ssadmin/kyfCancelAuditing"
					   height="320" width="800" target="dialog" title="确定要拒绝吗?"><span>拒绝</span>
					</a>
				</li>
			</shiro:hasPermission>


			<li>
				<a class="edit"
				   href="ssadmin/goKycJSP.html?uid={sid_user}&url=ssadmin/viewUserKyc"
				   height="600" width="800" target="dialog"
				   rel="userKycList" title="确定要拒绝吗?"><span>查看</span>
				</a>
			</li>




	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>

				<th width="60">证件类型</th>
				<th width="60">证件号码</th>

				<th width="60">注册时间</th>
				<th width="60">认证提交时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fvalidateKyclist}" var="kyc" varStatus="num">
				<tr target="sid_user" rel="${kyc.id}">
					<td>${kyc.fuser.fid}</td>
					<td>${kyc.fuser.floginName}</td>
					<td>${kyc.fuser.fnickName}</td>
					<td>${kyc.fuser.frealName}</td>
					<td>${kyc.fuser.fidentityType_s}</td>
					<td>${kyc.fuser.fidentityNo}</td>
					<td>${kyc.fuser.fregisterTime}</td>
					<td>${kyc.createTime}

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
