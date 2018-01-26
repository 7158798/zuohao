<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">交易设置</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/updateTradeSet.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">
            <input type="hidden" name="id" value="${tradeSet.id}" />
            <dl>
                <dt>货币类型：</dt>
                <dd>
                    ${tradeSet.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}
                </dd>
            </dl>
            <%--<dl>
                <dt>数量比例：</dt>
                <dd>
                    <input type="text" name="ratio" class="required number" size="40" value="${tradeSet.ratio}" />
                </dd>
            </dl>--%>
            <dl>
                <dt>每日上限：</dt>
                <dd>
                    <input type="text" name="upperLimit" class="required number" size="40" value="${tradeSet.upperLimit}" />
                </dd>
            </dl>
            <dl>
                <dt>单笔最大交易币数量：</dt>
                <dd>
                    <input type="text" name="singleNum" class="required number" size="40" value="${tradeSet.singleNum}" />
                </dd>
            </dl>

            <dl>
                <dt>挂单帐号：</dt>
                <dd>
                    <input type="hidden" name="userLookup.id" value="${tradeSet.fuser.fid}"/>
                    <input type="text" class="required" name="userLookup.floginName" value="${tradeSet.fuser.floginName}" suggestFields="id,floginName"
                           suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
                    <a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="userLookup">查找带回</a>

                </dd>
            </dl>

            <dl>
                <dt>未成交笔数：</dt>
                <dd>
                    <input type="text" name="unTradeOrderNum" class="required number" size="40" value="${tradeSet.unTradeOrderNum}" />
                </dd>
            </dl>

            <dl>
                <dt>暂停时间：</dt>
                <dd>
                    <input type="text" name="pauseTime" class="required number" size="20" value="${tradeSet.pauseTime}" /> &nbsp;(分)
                </dd>
            </dl>

            <dl>
                <dt>手机号：</dt>
                <dd>
                    <input type="text" name="mobileNumber" class="required mobile" size="40" value="${tradeSet.mobileNumber}" />
                </dd>
            </dl>

            <dl>
                <dt>短信暂停时间：</dt>
                <dd>
                    <input type="text" name="pauseMsgTime" class="required number" size="20" value="${tradeSet.pauseMsgTime}" /> &nbsp;(分)
                </dd>
            </dl>

            <dl>
                <dt>单笔最小数量：</dt>
                <dd>
                    <input type="text" name="minSingleNum" class="required number" size="20" value="${tradeSet.minSingleNum}" /> &nbsp;(分)
                </dd>
            </dl>

        </div>

        <!-- 保存、取消按钮区域-->
        <div class="formBar">
            <ul>
                <li><div class="buttonActive">
                    <div class="buttonContent">
                        <button type="submit">保存</button>
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