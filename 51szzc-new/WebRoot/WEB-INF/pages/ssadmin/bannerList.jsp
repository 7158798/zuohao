<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<!-- 分页数据，查询条件隐藏部分 -->
<form id="pagerForm" method="post" action="ssadmin/bannerList.html">
    <input type="hidden" name="pageNum" value="${currentPage}" />
    <input type="hidden" name="numPerPage" value="${numPerPage}" />
    <input type="hidden" name="fseat" value="${fseat}"/>
    <input type="hidden" name="fstatus" value="${fstatus}"/>
    <input type="hidden" name="startDate" value="${startDate}"/>
    <input type="hidden" name="endDate" value="${endDate}"/>
</form>

<!--查询条件-->
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this)" action="ssadmin/bannerList.html" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                       投放端：<c:forEach items="${bannerSeatEnumList}" var="seat">

                        <input type="radio" name="fseat" value="${seat.code}"  ${seat.code == fseat ? "checked" : ""}>${seat.name}
                    </c:forEach>
                    </td>
                    <td>
                        启用状态：<select name="fstatus" type="combox">
                        <option value="">全部</option>
                        <c:forEach items="${bannerStatusEnumList}" var="status">
                            <option value="${status.code}" ${status.code == fstatus ? "selected" : ""}>${status.name}</option>
                        </c:forEach>
                    </select>
                    </td>
                    <td>
                        创建时间：<input type="text" name="startDate" value="${startDate}" class="date textInput readonly" readonly="readonly"/>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input type="text" name="endDate" value="${endDate}" class="date textInput readonly" readonly="readonly"/>
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
            <shiro:hasPermission name="ssadmin/addBanner.html">
                <li>
                    <a class="add" href="ssadmin/goBannerJsp.html?url=ssadmin/addBanner" height="350" width="800" target="dialog" rel="addVideo">
                        <span>新增</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/updateBanner.html">
                <li>
                    <a class="edit" href="ssadmin/goBannerJsp.html?url=ssadmin/updateBanner&fid={sid_user}" height="350" width="800" target="dialog" rel="updateVideo">
                        <span>修改</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/disabBanner.html">
                <li>
                    <a class="edit" href="ssadmin/updateBannerStatus.html?fid={sid_user}&fstatus=2" target="ajaxTodo" title="确定要停用吗?">
                        <span>停用</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/useBanner.html">
                <li>
                    <a class="edit" href="ssadmin/updateBannerStatus.html?fid={sid_user}&fstatus=1" target="ajaxTodo" title="确定要启用吗?">
                        <span>启用</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/delBanner.html">
                <li>
                    <a class="delete" href="ssadmin/updateStatus.html?fid={sid_user}&fstatus=3" target="ajaxTodo" title="确定要删除吗?">
                        <span>删除</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/upBanner.html">
                <li>
                    <a class="edit" href="ssadmin/upBanner.html?fid={sid_user}&orderType=up" target="ajaxTodo" title="确定要移动吗?">
                        <span>上移</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="ssadmin/downBanner.html">
                <li>
                    <a class="edit" href="ssadmin/upBanner.html?fid={sid_user}&orderType=down" target="ajaxTodo" title="确定要移动吗?">
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
            <th width="60">投放端</th>
            <th width="60">缩略图</th>
            <th width="60">链接</th>
            <th width="30">排序</th>
            <th width="70">投放时间</th>
            <th width="70">创建时间</th>
            <th width="70">创建人</th>
            <th width="70">状态</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${fbannerList}" var="banner" varStatus="num">
            <tr target="sid_user" rel="${banner.fid}">
                <td>${num.index +1}</td>
                <td>${banner.fseatName}</td>
                <td><img src="${banner.fimgurl}" style="width: 40px;height: 40px;"/></td>
                <td>${banner.flinkurl}</td>
                <td>${banner.fpriority}</td>
                <td>${banner.fvalidate}</td>
                <td><fmt:formatDate value="${banner.fcreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${banner.fcreateUser.fname}</td>
                <td>
                    <c:choose>
                        <c:when test="${banner.fstatus == 0}">
                            <div style="color: goldenrod;">${banner.fstatusName}</div>
                        </c:when>
                        <c:when test="${banner.fstatus == 1}">
                            <div style="color: green;">${banner.fstatusName}</div>
                        </c:when>
                        <c:when test="${banner.fstatus == 2}">
                            <div style="color: grey;">${banner.fstatusName}</div>
                        </c:when>
                        <c:otherwise>
                            ${banner.fstatusName}
                        </c:otherwise>
                    </c:choose>
                </td>
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