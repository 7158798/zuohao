<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">批量下单</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/addBatchOrder.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">
            <dl>
                <dt>货币类型：</dt>
                <dd>
                    <select type="combox" name="vid">
                        <c:forEach items="${ftrademappings}" var="type">
                            <c:if test="${type.fid == vid}">
                                <option value="${type.fid}" selected="true">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
                            </c:if>
                            <c:if test="${type.fid != vid}">
                                <option value="${type.fid}">${type.fvirtualcointypeByFvirtualcointype2.fname}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt>交易类型：</dt>
                <dd>
                    <select type="combox" name="type">
                        <option value="0" selected="true">买入</option>
                        <option value="1">卖出</option>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt>挂单帐号：</dt>
                <dd>
                    <input type="hidden" name="userLookup.id" value="${batchOrder.user.fid}"/>
                    <input type="text" class="required" name="userLookup.floginName" value="${batchOrder.user.floginName}" suggestFields="id,floginName"
                           suggestUrl="ssadmin/userLookup.html" lookupGroup="userLookup" readonly="readonly"/>
                    <a class="btnLook" href="ssadmin/userLookup.html" lookupGroup="userLookup">查找带回</a>

                </dd>
            </dl>
            <dl>
                <dt>单价范围：</dt>
                <dd>
                    <input type="text" name="priceScope" class="required" size="40" value="${batchOrder.priceScope}" />  注:10,20;整数用英文逗号隔开
                </dd>
            </dl>
            <dl>
                <dt>数量范围：</dt>
                <dd>
                    <input type="text" name="amountScope" class="required" size="40" value="${batchOrder.amountScope}" /> 注:和单价范围规则一致
                </dd>
            </dl>
            <dl>
                <dt>数量小数位：</dt>
                <dd>
                    <input type="text" name="ratio" class="required" size="40" value="${batchOrder.ratio}" /> 注:随机数量保留的小数位
                </dd>
            </dl>
            <dl>
                <dt>订单数量：</dt>
                <dd>
                    <input type="text" name="orderNum" class="required number" size="40" value="${batchOrder.orderNum}"  />
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