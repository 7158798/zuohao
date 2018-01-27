/**
 * Created by zh on 2017/4/13.
 */

//下拉框
$("#sel").on("click",function () {
    $(".down-toggle").css("display","block");
    $(".num1").each(function () {
        $(this).on("click",function () {
            $("#grade").val($(this).text());
            $(".down-toggle").css("display","none");
        })
    })
})

$('.top-bt').click(function () {
    $('#file').trigger('click');
});

//数据的提交
$("#sub").click(function(){
    var department = $("#department").val();
    var grade = $("#grade").val();
    var major = $("#major").val();
    var name = $("#name").val();
    var school = $("#school").val();
    var telephone= $("#telephone").val();
    var photoUrl = $("#fileName").val();

    $.ajax({
        type: "GET",
        url: "/eassycontest/save.html",
        dataType:"json",
        data: {
            department: department,
            grade: grade,
            major: major,
            name: name,
            school: school,
            telephone: telephone,
            photoUrl: photoUrl
        },
        success: function (data) {
            console.log(data);
            $("#ap-fo").css("display","none");
            $("#sub-suc").css("display","block");
        },
        // sendBefore:checkNull()
    });
    return false;
});

function checkNull(){
    var num=0;
    var str="";
    $("input[type='text']").each(function(n){
        if($(this).val()==""){
            num++;
            str+=$(this).attr("msg")+"不能为空！\r\n";
        }
    });
    if(num>0){
        alert(str);
         return false;
    }
    else{
        return true;
    }
}

//上传图片
function uploadImg() {

    if (checkFileType("file")) {
        fileUpload("/common/upload.html", "4", "file","fileName", null, null, imgbakc, "resultUrl");
    }
}

function imgbakc(resultUrl) {
    $("#pic-con").css("background-image", "url("+resultUrl+")");
    $(".center-add").hide();
}

//上传文件
// function uploaddoc() {
//     if (checkFileType("docfile","dpdf")) {
//         fileUpload("/common/upload.html", "4", "docfile", "docfileUrl", null, null,docBack, "resultUrl");
//     }
// }

function docBack(resultUrl) {
    console.log(resultUrl);
}




