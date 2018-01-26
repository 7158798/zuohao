<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<!-- 分页数据，查询条件隐藏部分 -->
<form id="pagerForm" method="post" action="ssadmin/videoList.html">
    <input type="hidden" name="pageNum" value="${currentPage}" />
    <input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<!--查询条件-->
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this)" action="ssadmin/videoList.html" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        <input type="text" name="title" value="${title}" size="60" placeholder="标题/上传人"/>
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

<!--数据展示-->
<div class="pageContent">
    <!--表格上方的新增、修改等操作按钮-->
    <div class="panelBar">
        <ul class="toolBar">
            <shiro:hasPermission name="ssadmin/addVideo.html">
                <li>
                    <a class="add" href="ssadmin/goVideoJsp.html?url=ssadmin/addVideo" height="350" width="800" target="dialog" rel="addVideo">
                        <span>新增</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/updateVideo.html">
                <li>
                    <a class="edit" href="ssadmin/goVideoJsp.html?url=ssadmin/updateVideo&fid={sid_user}" height="350" width="800" target="dialog" rel="updateVideo">
                        <span>修改</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/deleteVideo.html">
                <li>
                    <a class="delete" href="ssadmin/deleteVideo.html?fid={sid_user}"  target="ajaxTodo" title="确定要删除吗?">
                        <span>删除</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/upVideo.html">
                <li>
                    <a class="edit" href="ssadmin/upVideo.html?fid={sid_user}" target="ajaxTodo" title="确定要移动吗?">
                        <span>上移</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/downVideo.html">
                <li>
                    <a class="edit" href="ssadmin/downVideo.html?fid={sid_user}" target="ajaxTodo" title="确定要移动吗?">
                        <span>下移</span>
                    </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>

    <!--数据表格-->
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="20">序号</th>
            <th width="60">类别</th>
            <th width="60">封面</th>
            <th width="60">标题</th>
            <th width="70">简介</th>
            <th width="70">排序</th>
            <th width="70">最新修改时间</th>
            <th width="70">上传人</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${fvideoList}" var="videoEntity" varStatus="num">
            <tr target="sid_user" rel="${videoEntity.fid}">
                <td>${num.index +1}</td>
                <td>${videoEntity.fvideotype.fName}</td>
                <td><img src="${videoEntity.fPictureUrl}" style="width: 40px;height: 40px;"/></td>
                <td>${videoEntity.fTitle}</td>
                <td>${videoEntity.fDescription}</td>
                <td>${videoEntity.fPriority}</td>
                <td><fmt:formatDate value="${videoEntity.fUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${videoEntity.fUpdateUser.fname}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!--下方的分页信息-->
    <div class="panelBar">
        <div class="pages">
            <span>总共: ${totalCount}条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${totalCount}"
             numPerPage="${numPerPage}" pageNumShown="5"
             currentPage="${currentPage}">
        </div>
    </div>

</div>