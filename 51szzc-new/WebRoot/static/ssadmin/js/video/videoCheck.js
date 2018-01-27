

//视频封面
function uploadVideoFirsrImg() {
    if (checkFileType("firstImg")) {
        fileUpload("/common/upload.html", "1", "firstImg", "fPictureUrl", "imgShow", "imgShowDiv");
    }
}


//视频
function uploadVideo() {
    if (checkFileType("video", "video")) {
        fileUpload("/common/upload.html", "2", "video", "fVideoUrl", null, null, uploadVideoCall, "resultUrl");
    }
}


//上传视频的回调
function uploadVideoCall(videoUrl) {
    if(!videoUrl) {
        return;
    }

    $("#videoShow").attr("src", videoUrl);

    $("#videoShow").load();
}