<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>


<form id="pagerForm" method="post" action="ssadmin/integralactivityList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/addIntegralactivity.html">
				<li><a class="add"
					   href="ssadmin/goIntegralactivityJSP.html?url=ssadmin/addIntegralactivity"
					   height="300" width="800" target="dialog" rel="addIntegralactivity"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateIntegralactivity.html">
				<li><a class="edit"
					   href="ssadmin/goIntegralactivityJSP.html?url=ssadmin/updateIntegralactivity&uid={sid_user}"
					   height="300" width="800" target="dialog" rel="updateIntegralactivity"><span>修改</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteIntegralactivity.html">
				<li><a class="delete"
					   href="ssadmin/deleteIntegralactivity.html?uid={sid_user}" target="ajaxTodo"
					   title="确定要删除吗?"><span>删除</span> </a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/startOrShutDown.html">
				<li><a class="edit"
					   href="ssadmin/startOrShutDown.html?uid={sid_user}&startOrShutDown=start" target="ajaxTodo"><span>开启</span> </a>
				</li>
				<li><a class="edit"
					   href="ssadmin/startOrShutDown.html?uid={sid_user}&startOrShutDown=shutdown" target="ajaxTodo"><span>暂停</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
		<tr>
			<th width="20">序号</th>
			<th width="60" orderField="type"
					<c:if test='${param.orderField == "type" }'> class="${param.orderDirection}"  </c:if>>操作行为</th>
			<th width="60" orderField="integral"
					<c:if test='${param.orderField == "integral" }'> class="${param.orderDirection}"  </c:if>>增加积分</th>
			<th width="60">说明</th>
			<th width="60">充值金额</th>
			<th width="60" orderField="dayMax"
					<c:if test='${param.orderField == "dayMax" }'> class="${param.orderDirection}"  </c:if>>每日获取上限</th>
			<th width="60" orderField="status"
					<c:if test='${param.orderField == "status" }'> class="${param.orderDirection}"  </c:if>>状态</th>
			<th width="60" orderField="updateTime"
					<c:if test='${param.orderField == "updateTime" }'> class="${param.orderDirection}"  </c:if>>活动时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${integralactivityList}" var="integralactivity"
				   varStatus="num">
			<tr target="sid_user" rel="${integralactivity.id}">
				<td>${num.index +1}</td>
				<td>${integralactivity.typeName}</td>
				<td>${integralactivity.integral}</td>
				<td>${integralactivity.content}</td>
				<td>${integralactivity.needAmount}</td>
				<td>${integralactivity.dayMax}</td>
				<td>${integralactivity.status_str}</td>
				<td><fmt:formatDate value="${integralactivity.beginTime}" pattern="yyyy-MM-dd"/> — <fmt:formatDate value="${integralactivity.endTime}" pattern="yyyy-MM-dd"/></td>
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
</head>
