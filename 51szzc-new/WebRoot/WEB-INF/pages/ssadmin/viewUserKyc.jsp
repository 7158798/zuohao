<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">视频上传</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/updateVideo.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">

            <dl>
                <dt>视频文件：</dt>
                <dd>
                    <video controls id="videoShow" style="width: 350px;height: 300px;" src="${fvalidatekyc.validateVideoUrl}">
                        您的浏览器不支持音频播放
                    </video>
                </dd>
            </dl>
            <dl>
                <dt>身份证正\反面：</dt>
                <dd id="imgShowDivOn">
                   <img src="${fvalidatekyc.identityUrlOn}" style="width: 350px;height: 300px;" /><br/><br/>
                    <img src="${fvalidatekyc.identityUrlOff}" style="width: 350px;height: 300px;"/>
                </dd>
            </dl>
            <dl>
                <dt>手持证件照：</dt>
                <dd id="imgShowDivOff">
                    <img src="${fvalidatekyc.identityHoldUrl}" style="width: 350px;height: 400px;"/>
                </dd>
            </dl>


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
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/video/videoCheck.js"></script>
