

$().ready(function(){

    //给单选按钮绑定事件
    $("input[name='img_choolse']").click(function(){
        var type = $(this).val();
        //上传图片
        if(type == "0"){
            $("#file").css("display","block");
            $("#imgUrl2").css("display","none");
            //输入url
        }else if(type == "1"){
            $("#file").css("display","none");
            $("#imgUrl2").css("display","block");
        }
    });


});


//
/**
 * 保存banner
 * @param type  0存草稿，1发布，2修改
 */
function saveBanner(oprType) {

    if(oprType == 0 || oprType == 1) {
        $("#fstatus").val(oprType);
    }

    var type = $("input[name='img_choolse']:checked").val();
    var imgurl = $("#fimgurl").val() || '';
    if (type == 0 && !imgurl) { //上传
        var uploadFileUrl = $("#file").val() || '';
        if (uploadFileUrl == '') {
            alert("请选择图片");
            return;
        }
    } else if(type == 1 && !imgurl){  //输入地址
        var imgUrl2 = $("#imgUrl2").val() || '';
        if (imgUrl2 == '') {
            alert("请输入图片url");
            return;
        }
    }

    if(type == 1) {
        var url = $("#imgUrl2").val();
        $("#fimgurl").val(url);
    }

    //验证时间
    var startDate_str = $("#startDate_str").val();
    var endDate_str = $("#endDate_str").val();
    if(startDate_str == endDate_str ) {
        alert("开始时间和结束时间不能一样");
        return;
    }

    var d1 = new Date(startDate_str);
    var d2 = new Date(endDate_str);
    if(d1 > d2) {
        alert("开始时间不能大于结束时间");
        return;
    }




    $("#dataForm").submit();

}


function uploadBanner() {
    if (checkFileType("file")) {
        fileUpload("/common/upload.html", "4", "file", "fimgurl", "imgShow", "imgShowDiv");
    }
}