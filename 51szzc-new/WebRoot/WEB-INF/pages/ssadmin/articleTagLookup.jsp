<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<form id="pagerForm" action="/ssadmin/articleTagLookup.html">
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="ssadmin/articleTagLookup.html" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>资讯标签:</label>
					<input class="textInput" name="keywords" value="" type="text">
				</li>
			</ul>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" multLookup="tagId" warn="请选择标签">选择带回</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
		<tr>
			<th width="30"><input type="checkbox" class="checkboxCtrl" group="tagId" /></th>
			<th orderfield="orgName">标签名称</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${articleTag}" var="articleTag" varStatus="num">
				<tr>
					<td><input type="checkbox" name="tagId" value="{id:'${articleTag.fid}', tagName:'${articleTag.fname}'}"/></td>
					<td>${articleTag.fname}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
	</div>
</div>