<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/helpList.html">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="pageNum" value="${currentPage}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="model" value="${model}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/helpList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td><input type="text" name="keywords" value="${keywords}"
						size="40" placeholder="请输入标题"/></td>
					<td></td>
					<td>帮助类型： <select type="combox" name="fhelptype">
						<c:forEach items="${typeMap}" var="type">
							<c:if test="${type.key == fhelptype}">
								<option value="${type.key}" selected="true">${type.value}</option>
							</c:if>
							<c:if test="${type.key != fhelptype}">
								<option value="${type.key}">${type.value}</option>
							</c:if>
						</c:forEach>
					</select>
					</td>
					<td><input type="text" name="beginTimeStr" class="date"
							   readonly="true" value="${beginTimeStr}"/>～<input type="text" name="endTimeStr" class="date"
																				readonly="true" value="${endTimeStr}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/addHelp.html">
				<li><a class="add"
					href="ssadmin/goHelpJSP.html?url=ssadmin/addHelp"
					height="500" width="800" target="dialog" rel="addHelp"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteHelp.html">
				<li><a class="delete"
					href="ssadmin/deleteHelp.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateHelp.html">
				<li><a class="edit"
					href="ssadmin/goHelpJSP.html?url=ssadmin/updateHelp&uid={sid_user}"
					height="500" width="800" target="dialog" rel="updateHelp"><span>修改</span>
				</a></li>
				<li><a class="edit"
					href="ssadmin/dingHelp.html?uid={sid_user}" target="ajaxTodo"
					title="确定要操作吗?"><span>推荐/取消推荐</span> </a></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="ssadmin/upAndDownHelp.html">
				<li>
					<a class="edit" href="ssadmin/upHelp.html?fid={sid_user}" target="ajaxTodo">
						<span>上移</span>
					</a>
				</li>
				<li>
					<a class="edit" href="ssadmin/downHelp.html?fid={sid_user}" target="ajaxTodo">
						<span>下移</span>
					</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10">序号</th>
				<th width="40">标题</th>
				<th width="40">帮助类型</th>
				<th width="10">推荐</th>
				<th width="20">最新修改时间</th>
				<th width="30">创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${helpList}" var="list" varStatus="num">
				<tr target="sid_user" rel="${list.fid}">
					<td>${num.index +1}</td>
					<td>${list.ftitle}</td>
					<td>${list.fhelptype.fname}</td>
					<td>
						<c:if test="${list.fisding == 'true'}">是</c:if>
						<c:if test="${list.fisding == 'false'}">否</c:if>
					</td>
					<td><fmt:formatDate value="${list.fupdatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${list.fcreateuser.fname}</td>
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
