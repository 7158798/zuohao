/**
 * Created by zygong on 17-3-8.
 */

//附件上传
function uploadAttachment(){

    $.ajaxFileUpload({
        url: "/ssadmin/saveTagManagement.html",
        type: "post",
        data: {
            "code": "3"
        },
        secureuri: false,
        fileElementId: "txtId",
        dataType: "json",
        success: function (data, status) {
            console.log(data);
            if (data.code == 0) {
                navTabSearch(this);
            }else{
                alert("上传失败");
            }
        },
        error: function (data, status, e) {
            alert("上传错误");
        }
    });
}
