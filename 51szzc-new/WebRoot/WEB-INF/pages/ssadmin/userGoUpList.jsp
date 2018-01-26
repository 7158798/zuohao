<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/userGoUpList.html">
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
		action="ssadmin/userGoUpList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords"
						value="${keywords}" size="40" />
					</td>
					<td>状态：
						<select type="combox" name="fstatus">
							<option value="0" ${status == 0 ? 'selected':''}>全部</option>
							<option value="2" ${status == 4 ? 'selected':''}>处理中</option>
							<option value="10" ${status == 10 ? 'selected':''}>二级审核中</option>
							<option value="11" ${status == 11 ? 'selected':''}>三级审核中</option>
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
			<shiro:hasPermission name="ssadmin/userGoUpValidate.html">
				<li>
					<c:if test="${isneedPwd  == 1}">
					<li><a class="edit"
						   href="ssadmin/goUserJSP.html?uid={sid_user}&url=ssadmin/userGoUpValidate"
						   height="320" width="800"  rel="auditVCInlog" target="dialog"><span>审核</span>
					</a>
					</c:if>
					<c:if test="${isneedPwd == 0}">
						<a class="edit"
						   href="ssadmin/userGoUpValidate.html?uid={sid_user}&rel=userGoUpList" target="ajaxTodo"
						   rel="userGoUpList" title="确定要审核吗?"><span>审核</span>
						</a>
					</c:if>
				</li>

				<%--<li><a title="确定要全部通过吗?" target="selectedTodo" rel="ids"--%>
					   <%--postType="delete" href="ssadmin/userGoUpValidateAll.html?status=1"--%>
					   <%--class="edit"><span>全部通过审核</span> </a>--%>
				<%--</li>--%>
				<li><a title="确定要全部不通过吗?" target="selectedTodo" rel="ids"
					   postType="delete" href="ssadmin/userGoUpValidateAll.html?status=5"
					   class="edit"><span>全部不通过审核</span> </a>
				</li>
			</shiro:hasPermission>

	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
									  class="checkboxCtrl">
				</th>
				<th width="40" orderField="fid"
					<c:if test='${param.orderField == "fid" }'> class="${param.orderDirection}"  </c:if>>会员UID</th>
				<th width="60" orderField="floginName"
					<c:if test='${param.orderField == "floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60">昵称</th>
				<th width="60">真实姓名</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>会员状态</th>
				<th width="60">证件类型</th>
				<th width="60">证件号码</th>
				<th width="60">冻结原因</th>


				<th width="60" orderField="fregisterTime"
					<c:if test='${param.orderField == "fregisterTime" }'> class="${param.orderDirection}"  </c:if>>注册时间</th>

				<th width="60" orderField="fgoUpValidateDate"
						<c:if test='${param.orderField == "fgoUpValidateDate" }'> class="${param.orderDirection}"  </c:if>>认证提交时间</th>
			    <th width="60">证件照片</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="num">
				<tr target="sid_user" rel="${user.fid}">
					<td><input name="ids" value="${user.fid}"
							   type="checkbox">
					</td>
					<td>${user.fid}</td>
					<td>${user.floginName}</td>
					<td>${user.fnickName}</td>
					<td>${user.frealName}</td>
					<td>${user.fstatus_s}</td>

					<td>${user.fidentityType_s}</td>
					<td>${user.fidentityNo}</td>
					<td>${user.frozenReason}</td>

					<td>${user.fregisterTime}</td>
					<td>${user.fgoUpValidateDate}</td>
					<td><a href="ssadmin/goUserJSP.html?url=ssadmin/userImg&img=${user.fIdentityUrlOn}" target="dialog"  height="640" width="510" >正面jpg</a>
						<a href="ssadmin/goUserJSP.html?url=ssadmin/userImg&img=${user.fIdentityUrlOff}" target="dialog"  height="640" width="510" >反面jpg</a> </td>

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
