<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<!-- 分页数据，查询条件隐藏部分 -->
<form id="pagerForm" method="post" action="ssadmin/tradeSetList.html">
    <input type="hidden" name="pageNum" value="${currentPage}" />
    <input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<!--查询条件-->
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this)" action="ssadmin/tradeSetList.html" method="post">
        <div class="searchBar">
            <table class="searchContent">

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
            <shiro:hasPermission
                    name="ssadmin/saveTradeSet.html">
            <li>
                <a class="add" href="ssadmin/goTradeSetJsp.html?url=ssadmin/addTradeSet" height="350" width="800" target="dialog" rel="addTradeSet">
                    <span>新增</span>
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission
                    name="ssadmin/updateTradeSet.html">
            <li>
                <a class="edit" href="ssadmin/goTradeSetJsp.html?url=ssadmin/updateTradeSet&uid={sid_user}" height="350" width="800" target="dialog" rel="addTradeSet">
                    <span>修改</span>
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission
                    name="ssadmin/startTrade.html">
            <!-- 启动测试线程-->
            <li><a class="edit"
                   href="ssadmin/trade.html?uid={sid_user}&status=1"
                   target="ajaxTodo" title="确定要启动自动交易线程吗?"><span>启动线程</span>
            </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission
                    name="ssadmin/stopTrade.html">
            <li><a class="edit"
                   href="ssadmin/trade.html?uid={sid_user}"
                   target="ajaxTodo" title="确定要停止自动交易线程吗?" ><span>停止线程</span>
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
                <th width="60">货币类型</th>
                <th width="60">每日上限</th>
                <th width="60">单笔最大交易币数量</th>
                <th width="70">挂单帐号</th>
                <th width="70">未成交笔数</th>
                <th width="70">暂停时间(分)</th>
                <th width="70">手机号</th>
                <th width="70">短信暂停时间(分)</th>
                <th width="70">创建时间</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${tradeSetList}" var="tradeSet" varStatus="num">
                <tr target="sid_user" rel="${tradeSet.id}">
                    <td>${num.index +1}</td>
                    <td>${tradeSet.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}</td>
                    <td>${tradeSet.upperLimit}</td>
                    <td>${tradeSet.singleNum}</td>
                    <td>${tradeSet.fuser.floginName}</td>
                    <td>${tradeSet.unTradeOrderNum}</td>
                    <td>${tradeSet.pauseTime}</td>
                    <td>${tradeSet.mobileNumber}</td>
                    <td>${tradeSet.pauseMsgTime}</td>
                    <td>${tradeSet.createDate}</td>
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