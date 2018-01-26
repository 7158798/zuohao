
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<%--<script type="text/javascript" src="${oss_url}/static/ssadmin/js/kycconfig/kycconfig.js"></script>--%>


<!--数据展示-->
<div class="pageContent">
    <!--表格上方的新增、修改等操作按钮-->
    <div class="panelBar">
        <ul class="toolBar">
            <li>
                <span style="margin-top: 1px;">KYC1权限&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </li>
            <shiro:hasPermission name="ssadmin/kycconfig/save.html">
                <li>
                    <a class="edit" id="kyc1_edit" href="javascript:void(0);">
                        <span>修改</span>
                    </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>

    <!--数据表格-->
    <table class="table"  width="100%" layoutH="" id="kyc1_datatable">
        <thead>
        <tr>
            <th  width="60">权限列表</th>
            <th  width="70">每日提现次数</th>
            <th  width="70">单笔提现额度</th>
            <th  width="70">每日提现额度</th>
            <th  width="60">每月提现额度</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allcoins}" var="coin" varStatus="num">
            <tr>
                <td width="60" rel="${coin.fid}">${coin.fShortName}</td>
                <td width="60">${coin.kyc1_day_count}</td>
                <td width="60">${coin.kyc1_single_limit}</td>
                <td width="60">${coin.kyc1_day_limit}</td>
                <td width="60">${coin.kyc1_month_limit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br/><br/>
    <!--表格上方的新增、修改等操作按钮-->
    <div class="panelBar" style="margin-top: 10px;margin-bottom: 10px;">
        <ul class="toolBar">
            <li>
                <span style="margin-top: 1px;">KYC2权限&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </li>
            <shiro:hasPermission name="ssadmin/kycconfig/save.html">
                <li>
                    <a class="edit" id="kyc2_edit" href="javascript:void(0);">
                        <span>修改</span>
                    </a>
                </li>
            </shiro:hasPermission>
        </ul>
    </div>

    <!--数据表格-->
    <table class="table" width="100%" layoutH="" id="kyc2_datatable">
        <thead>
        <tr>
            <th width="60">权限列表</th>
            <th width="70">每日提现次数</th>
            <th width="70">单笔提现额度</th>
            <th width="70">每日提现额度</th>
            <th width="60">每月提现额度</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allcoins}" var="coin" varStatus="num">
            <tr>
                <td width="60" rel="${coin.fid}">${coin.fShortName}</td>
                <td width="60">${coin.kyc2_day_count}</td>
                <td width="60">${coin.kyc2_single_limit}</td>
                <td width="60">${coin.kyc2_day_limit}</td>
                <td width="60">${coin.kyc2_month_limit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<script type="text/javascript" src="${oss_url}/static/ssadmin/js/systemargs/KycConfigList.js"></script>

