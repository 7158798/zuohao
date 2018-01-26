<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">快速交易设置</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/updateFastTrade.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">
            <input type="hidden" name="id" value="${fastTrade.id}" />
            <dl>
                <dt>货币类型：</dt>
                <dd>
                    ${fastTrade.ftrademapping.fvirtualcointypeByFvirtualcointype2.fname}
                </dd>
            </dl>
            <dl>
                <dt>挂单帐号：</dt>
                <dd>
                    <input type="hidden" name="userLookup.id" value="${fastTrade.user.fid}"/>
                    <input type="text" class="required" name="userLookup.floginName" value="${fastTrade.user.floginName}" suggestFields="id,floginName"
                           suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
                    <a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="userLookup">查找带回</a>

                </dd>
            </dl>
            <dl>
                <dt>随机数量：</dt>
                <dd>
                    <input type="text" name="randomNum" class="required" size="40" value="${fastTrade.randomNum}" />
                </dd>
            </dl>
            <dl>
                <dt>休眠时间：</dt>
                <dd>
                    <input type="text" name="randomTime" class="required" size="40" value="${fastTrade.randomTime}" />
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