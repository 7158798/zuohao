<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<form id="pagerForm" method="post" action="ssadmin/hostRecommendedList.html">

</form>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <shiro:hasPermission name="ssadmin/updateHostRecommended.html">
                <li><a class="edit"
                       href="ssadmin/gohostRecommendedJSP.html?url=ssadmin/updateHostRecommended"
                       height="300" width="800" target="dialog" rel="updateHostRecommended"><span>修改</span>
                </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="20">序号</th>
            <th width="60">项目</th>
            <th width="60">资讯数量上限</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hostRecommended}" var="host"
                   varStatus="num">
            <tr target="sid_user" rel="${host.fid}">
                <td>${num.index +1}</td>
                <td>${host.fName}</td>
                <td>${host.fNumber}</td>
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

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
