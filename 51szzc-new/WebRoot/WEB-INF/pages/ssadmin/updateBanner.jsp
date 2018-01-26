<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加广告</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/updateBanner.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);" id="dataForm">
        <div class="pageFormContent nowrap" layoutH="97">

            <dl>
                <dt>投放端：</dt>
                <dd>
                    <select name="fseat" type="combox" style="width: 200px;">
                        <c:forEach items="${bannerSeatEnumList}" var="seat">
                            <option value="${seat.code}" ${seat.code == fbanner.fseat ? 'selected':''}>${seat.name}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl id="imgShowDiv">
                <dt>图片预览：</dt>
                <dd>
                    <img src="${fbanner.fimgurl}" style="width: 120px;height: 100px;" id="imgShow"/>
                </dd>
            </dl>
            <dl>
                <dt>图片：</dt>
                <dd>
                    <input type="radio" name="img_choolse" value="0" checked="checked"/>上传图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="img_choolse" value="1"/>输入地址
                </dd>
            </dl>
            <dl>
                <dt>&nbsp;</dt>
                <dd>
                    <input type="hidden" name="fimgurl" id="fimgurl" value="${fbanner.fimgurl}"/>
                    <input type="file" name="file" id="file" onchange="uploadBanner()"/>
                    <input type="text" name="imgUrl2" id="imgUrl2" value="${fbanner.fimgurl}" style="display: none;"/>
                </dd>
            </dl>
            <dl>
                <dt>链接地址：</dt>
                <dd>
                    <input type="text" name="flinkurl"  size="40" value="${fbanner.flinkurl}">
                </dd>
            </dl>
            <dl>
                <dt>开始时间：</dt>
                <dd>
                    <input type="text" name="startDate_str" id="startDate_str" class="date textInput readonly required" readonly="readonly" size="20"
                    value="<fmt:formatDate value="${fbanner.fstartDate}" pattern="yyyy-MM-dd"/>" >
                </dd>
            </dl>
            <dl>
                <dt>结束时间：</dt>
                <dd>
                    <input type="text" name="endDate_str" id="endDate_str" class="date textInput readonly required" readonly="readonly" size="20"
                           value="<fmt:formatDate value="${fbanner.fendDate}" pattern="yyyy-MM-dd"/>">
                </dd>
            </dl>


        </div>

        <!-- 保存、取消按钮区域-->
        <div class="formBar">
            <input type="hidden" name="fid" value="${fbanner.fid}"/>
            <ul>
                <li><div class="buttonActive">
                    <div class="buttonContent">
                        <button type="button" onclick="saveBanner(1)">保存</button>
                    </div>
                </div></li>
                <li><div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">取消</button>
                    </div>
                </div></li>
            </ul>
        </div>
    </form>
</div>


<script type="text/javascript">
    function customvalidXxx(element){
        if ($(element).val() == "xxx") return false;
        return true;
    }
</script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/systemargs/banner.js?r=<%=new java.util.Date().getTime() %>"></script>
