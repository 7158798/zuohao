<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">视频上传</h2>


<div class="pageContent">
    <form method="post" action="ssadmin/updateVideo.html"
          class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent nowrap" layoutH="97">

            <dl>
                <dt>视频类别：</dt>
                <dd>
                    <select type="combox" name="fvideoType_id">
                        <c:forEach items="${fvideotypes}" var="type">
                            <option value="${type.fid}" <c:if test="${fvideo.fvideotype.fid == type.fid}">selected </c:if>>${type.fName}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt>视频标题：</dt>
                <dd>
                    <input type="text" name="fTitle" class="required" size="40" value="${fvideo.fTitle}" maxlength="100"/>
                </dd>
            </dl>
            <dl>
                <dt>视频简介：</dt>
                <dd>
                    <textarea name="fDescription" class="required"  rows="6" cols="50" maxlength="200">${fvideo.fDescription}</textarea>
                </dd>
            </dl>

            <dl>
                <dt>视频文件：</dt>
                <dd>
                    <video controls id="videoShow" style="width: 300px;height: 300px;" src="${fvideo.fVideoUrl}">
                        您的浏览器不支持音频播放
                    </video>
                </dd>
            </dl>
            <dl>
                <dt>视频上传：</dt>
                <dd>
                    <input type="hidden" name="fVideoUrl" id="fVideoUrl" value="${fvideo.fVideoUrl}"/>
                    <input type="file" id="video" onchange="uploadVideo()"/><br/>
                    支持：.mp4、.avi、.wmv、.flv、.mkv、.mov 大小200M以内
                </dd>
            </dl>
            <dl>
                <dt>封面预览：</dt>
                <dd id="imgShowDiv">
                   <img src="${fvideo.fPictureUrl}" style="width: 120px;height: 100px;" id="imgShow"/>
                </dd>
            </dl>
            <dl>
                <dt>封面上传：</dt>
                <dd>
                    <input type="hidden" name="fPictureUrl" id="fPictureUrl" value="${fvideo.fPictureUrl}"/>
                    <input type="file" id="firstImg" onchange="uploadVideoFirsrImg()"/><br/>
                    支持：jpg、.jpeg、.png、.bmp，大小2M以内，建议尺寸600*900
                </dd>
            </dl>

        </div>

        <!-- 保存、取消按钮区域-->
        <div class="formBar">
            <input type="hidden" name="fid" value="${fvideo.fid}"/>
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

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/video/videoCheck.js"></script>
