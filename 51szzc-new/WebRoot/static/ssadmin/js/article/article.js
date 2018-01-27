/*文本编辑器加载初始化=========开始*/
var editor;
$(document).on("change", "input", function () {
    if($(this).attr('id') == 'file-upload'){
        uploadFile();
    }
});

function  initEditor(){
    editor = new wangEditor('editor-trigger');
    editor.config.menus = [
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'eraser',
        '|',  //   '|' 是菜单组的分割线
        'alignleft',
        'aligncenter',
        'alignright',
        '|',
        'quote',
        'fontsize',
        'head',
        '|',
        'link',
        'img',
        '|',
        'undo',
        'redo',
        'fullscreen'
    ];     // 自定义菜单

    editor.config.customUpload = true;  // 设置自定义上传的开关
    editor.config.customUploadInit = uploadInit;
    editor.config.hideLinkImg = true; // 关闭网路图片
    //editor.config.pasteFilter = true;
    //editor.config.pasteText = true; // 只支持纯文本
    editor.create();
}

initEditor();

function uploadInit() {
}

/*文本编辑器加载初始化=========结束*/

var uploadFile = function () {

    $.ajaxFileUpload({
        url: "/common/upload.html",
        type: "post",
        data: {
            "code": "2"
        },
        secureuri: false,
        fileElementId: "file-upload",
        dataType: "json",
        success: function (data, status) {
            console.log(data);
            if (data.code == 0) {
                //style="max-width:450px;max-height:
                editor.command(null, 'insertHtml', '<img src="' +  data.resultUrl + '" alt="' + '51数字资产' + '" style="max-width:100%; auto;"/>');
            }else{
                if(data.code){
                    alert(data.code);
                }else{
                    alert("上传失败");
                }
            }
        },
        error: function (data, status, e) {
            alert("上传错误");
        }
    });

};

