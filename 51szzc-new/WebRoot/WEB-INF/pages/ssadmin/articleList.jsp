<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/articleList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /><input
		type="hidden" name="type" value="${ftype}" /> <input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/articleList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>关键词：<input type="text" name="keywords" value="${keywords}"
						size="40" placeholder="请输入标题"/></td>
					<td></td>
					<td>资讯类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
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
			<shiro:hasPermission name="ssadmin/addArticle.html">
				<li><a class="add"
					href="ssadmin/goArticleJSP.html?url=ssadmin/addArticle"
					height="500" width="900" target="dialog" rel="addArticle"><span>新增</span>
				</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/deleteArticle.html">
				<li><a class="delete"
					href="ssadmin/deleteArticle.html?uid={sid_user}" target="ajaxTodo"
					title="确定要删除吗?"><span>删除</span> </a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/updateArticle.html">
				<li><a class="edit"
					href="ssadmin/goArticleJSP.html?url=ssadmin/updateArticle&uid={sid_user}"
					height="500" width="900" target="dialog" rel="updateArticle"><span>修改</span>
				</a></li>
				<li><a class="edit"
					href="ssadmin/dingArticle.html?uid={sid_user}" target="ajaxTodo"
					title="确定要操作吗?"><span>推荐/取消推荐</span> </a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="20">是否站外文章</th>
				<th width="20">站外链接</th>
				<th width="40">来源</th>
				<th width="100" orderField="ftitle"
					<c:if test='${param.orderField == "ftitle" }'> class="${param.orderDirection}"  </c:if>>标题</th>
				<th width="40" orderField="farticletype.fname"
					<c:if test='${param.orderField == "farticletype.fname" }'> class="${param.orderDirection}"  </c:if>>类型</th>
				<th width="60" orderField="farticletype.ftag"
					<c:if test='${param.orderField == "ftag" }'> class="${param.orderDirection}"  </c:if>>标签</th>
				<th width="30">热门推荐</th>
				<th width="40" orderField="fcreateDate"
					<c:if test='${param.orderField == "flastModifyDate" }'> class="${param.orderDirection}"  </c:if>>最新修改时间</th>
				<th width="30" orderField="fadminByFcreateAdmin.fname"
					<c:if test='${param.orderField == "fadminByFcreateAdmin.fname" }'> class="${param.orderDirection}"  </c:if>>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleList}" var="article" varStatus="num">
				<tr target="sid_user" rel="${article.fid}">
					<td>${num.index +1}</td>
					<td>
						<c:if test="${article.fisout == 'true'}">是</c:if>
						<c:if test="${article.fisout == 'false'}">否</c:if>
					</td>
					<td>${article.foutUrl}</td>
					<td>${article.forigin}</td>
					<td>${article.ftitle}</td>
					<td>${article.farticletype.fname}</td>
					<td>${article.ftag}</td>
					<td>
						<c:if test="${article.fisding == 'true'}">是</c:if>
						<c:if test="${article.fisding == 'false'}">否</c:if>
					</td>
					<td><fmt:formatDate value="${article.flastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${article.fadminByFcreateAdmin.fname}</td>
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
