<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/videoTypeList.html">
    <input type="hidden" name="status" value="${param.status}"> <input
        type="hidden" name="keywords" value="${keywords}" /> <input
        type="hidden" name="pageNum" value="${currentPage}" /> <input
        type="hidden" name="numPerPage" value="${numPerPage}" /> <input
        type="hidden" name="orderField" value="${param.orderField}" /><input
        type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);"
          action="ssadmin/videoTypeList.html" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td><input type="text" name="keywords" value="${keywords}"
                                   size="60" placeholder="类别名称" /></td>
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
            <shiro:hasPermission name="ssadmin/addVideoType.html">
                <li><a class="add"
                       href="ssadmin/goVideoTypeJsp.html?url=ssadmin/addVideoType"
                       height="300" width="800" target="dialog" rel="addVideoType"><span>新增</span>
                </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/updateVideoType.html">
            <li><a class="edit"
                   href="ssadmin/goVideoTypeJsp.html?url=ssadmin/updateVideoType&fid={sid_user}"
                   height="300" width="800" target="dialog" rel="updateVideoType"><span>修改</span>
            </a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/deleteVideoType.html">
                <li><a class="delete"
                       href="ssadmin/deleteVideoType.html?uid={sid_user}" target="ajaxTodo"
                       title="确定要删除吗?"><span>删除</span> </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="20">序号</th>
            <th width="60" orderField="fName"
                    <c:if test='${param.orderField == "fName" }'> class="${param.orderDirection}"  </c:if>>名称</th>
            <th width="60" orderField="fDescription"
                    <c:if test='${param.orderField == "fDescription" }'> class="${param.orderDirection}"  </c:if>>描述</th>
            <th width="60" orderField="fUpdateTime"
                    <c:if test='${param.orderField == "fUpdateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${videoTypeList}" var="videoType"
                   varStatus="num">
            <tr target="sid_user" rel="${videoType.fid}">
                <td>${num.index +1}</td>
                <td>${videoType.fName}</td>
                <td>${videoType.fDescription}</td>
                <td><fmt:formatDate value="${videoType.fUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
