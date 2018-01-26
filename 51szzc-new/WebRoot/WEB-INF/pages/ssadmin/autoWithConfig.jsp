
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<%--<script type="text/javascript" src="${oss_url}/static/ssadmin/js/kycconfig/kycconfig.js"></script>--%>

<!--数据展示-->
<div class="pageContent">
    <!--表格上方的新增、修改等操作按钮-->
    <div class="panelBar">
        <ul class="toolBar">
            <shiro:hasPermission name="ssadmin/updateautowith.html">
                <li>
                    <a class="edit" id="a_edit" href="javascript:void(0);">
                        <span>修改</span>
                    </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>

    <!--数据表格-->
    <table class="table" width="100%" layoutH="138" id="datatable">
        <thead>
        <tr>
            <th width="60">币种</th>
            <th  width="70">单笔提现额度</th>
            <th width="70">每日提现额度</th>
            <th width="70">每日提现次数</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allcoins}" var="coin" varStatus="num">
            <tr>
                <td width="60" rel="${coin.fid}">${coin.fShortName}</td>
                <td width="60">${coin.auto_single_limit}</td>
                <td width="60">${coin.auto_day_limit}</td>
                <td width="60">${coin.auto_day_count}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<script type="text/javascript" src="${oss_url}/static/ssadmin/js/systemargs/autoWithConfigList.js"></script>


