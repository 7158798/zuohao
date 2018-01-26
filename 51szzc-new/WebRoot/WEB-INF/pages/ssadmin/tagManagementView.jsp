<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<form id="pagerForm" method="post" action="ssadmin/tagManagementView.html">
</form>


<div  class="pageContent">
        <input type="file" name="file" id="txtId" onchange="uploadAttachment()"/>
</div>

<div class="pageContent">
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="20">序号</th>
            <th width="60">上传文档</th>
            <th width="60">创建人</th>
            <th width="60">下载</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tagManage}" var="tagManage"
                   varStatus="num">
            <tr target="sid_user" rel="${tagManage.fid}">
                <td>${num.index +1}</td>
                <td>${tagManage.fname}</td>
                <td><fmt:formatDate value="${tagManage.flastupdatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><a href="${tagManage.furl}" style="color: #985f0d">下载</a></td>
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

<script type="text/javascript">
    function customvalidXxx(element){
        if ($(element).val() == "xxx") return false;
        return true;
    }
</script>

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/tagManage/tagManage.js?r=<%=new java.util.Date().getTime() %>"></script>
