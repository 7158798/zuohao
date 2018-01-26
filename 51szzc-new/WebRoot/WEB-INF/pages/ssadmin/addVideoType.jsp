<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加视频类型</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/saveVideoType.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">

            <dl>
                <dt>类别名称：</dt>
                <dd>
                    <input type="text" name="fName" class="required" size="40"/>
                </dd>
            </dl>
            <dl>
                <dt>描述：</dt>
                <dd>
                    <textarea name="fDescription" class="required"  rows="6" cols="50" />
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