/**
 * Created by zygong on 17-3-13.
 */
var editor;
$(document).on("change", "input", function () {
    if ($(this).attr('id') == 'file-upload') {
        uploadFile();
    }
    if ($(this).attr('id') == 'headfile') {
        uploadThumbnail();
    }
});




function initEditor() {
    editor = new wangEditor('editor-trigger');
    editor.config.menus = [
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'eraser',
        '|', //   '|' 是菜单组的分割线
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
    ]; // 自定义菜单

    editor.config.customUpload = true; // 设置自定义上传的开关
    editor.config.hideLinkImg = true; // 关闭网路图片
    //editor.config.pasteFilter = true;
    //editor.config.pasteText = true; // 只支持纯文本
    editor.create();
}

initEditor();

function uploadThumbnail() {
    $.ajaxFileUpload({
        url: "/common/upload.html",
        type: "post",
        data: {
            "code": "2"
        },
        secureuri: false,
        fileElementId: "headfile",
        dataType: "json",
        success: function (data, status) {

            if (data.code == 0) {
                $('.account-publish-thumbnail label').attr('data-url', data.resultUrl).css('background-image', 'url(' + data.resultUrl + ')');

            } else {
                if (data.code) {
                    alert(data.code);
                } else {
                    alert("上传失败");
                }
            }
        },
        error: function (data, status, e) {
            alert("上传错误");
        }
    });

}

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
                editor.command(null, 'insertHtml', '<img src="' + data.resultUrl + '" alt="' + '51数字资产' + '" style="max-width:100%; auto;"/>');
            } else {
                if (data.code) {
                    alert(data.code);
                } else {
                    alert("上传失败");
                }
            }
        },
        error: function (data, status, e) {
            alert("上传错误");
        }
    });
};

function addArticle(fstatus) {
    var articleLookupid = $('#type').val();
    var fcontent = editor.$txt.html();
    var fid = $('#fid').val();
    var forigin = $('#origin').val();
    var fstatus = fstatus;
    var ftitle = $('#title').val();
    var furl = $('.account-publish-thumbnail label').attr('data-url');
    var tagName = $('#keyword').attr('data-id');

    var params = {
        articleLookupid: articleLookupid,
        fcontent: fcontent,
        fid: fid,
        forigin: forigin,
        fstatus: fstatus,
        ftitle: ftitle,
        furl: furl,
        tagName: tagName
    };

    $.post("/external/user/addArticleByWeb.html", params, function (data, textStatus, jqXHR) {

        if (data.code == 200) {
            window.location.href = '/external/user/articleListByWeb.html';
        } else {
            alert(data.msg);
        }
    }, "json");
}

function getArticle() {
    var id = util.getUrlParam().id;

    if (!id) {
        return false;
    }

    $('#fid').val(id);

    $.post("/external/user/articleDetailByWeb.html", {
        fid: id
    }, function (data, textStatus, jqXHR) {

        if (data.code == 0) {

            $('#type').val();
            editor.$txt.html(data.content);
            $('#fid').val(id);
            $('#origin').val(data.origin);
            $('#title').val(data.title);
            $('.account-publish-thumbnail label').css('background-image', 'url(' + data.imgurl + ')').attr('data-url', data.imgurl);
            $('#keyword').attr('data-id', data.tag.join(','));
            console.log(data)
            $.each($('#type option'), function (i, t) { 
                if($(t).val() == data.article_type_id) {
                    $(t).attr('selected',true);
                }
            });

            var names = [];
            $('.account-keyword-wrap input:checkbox').each(function (i, t) {

                if (data.tag.indexOf($(t).attr('data-name')) != -1) {

                    $(t).attr('checked', true);
                    names.push($(t).attr('data-name'));
                }
                $('#keyword').val(names.join(','));
            });
        } else {
            alert('获取文章失败！');
        }
    }, "json");
}

$(function () {


    $.post("/external/user/articleTagLookup.html", {}, function (data, textStatus, jqXHR) {

        $.each(data.articleTag, function (i, t) {
            var html = '<div class="account-keyword-item"><label><input data-name="' +
                t.fname + '" name="check" type="checkbox" value="' +
                t.fid + '">' +
                t.fname + '</label> </div>';
            $('.account-keyword-wrap').append(html);
        });
        getArticle();
    }, 'json');

    // 获取复选框被选中值
    $("#sureSelected").click(function () {
        var $items = $('.account-keyword-wrap input:checkbox:checked');
        var text = '';
        var ids = [];
        var tags = [];

        $.each($items, function (i, t) {
            text = text + $(t).attr('data-name') + ' ';
            ids.push($(t).val());
            tags.push($(t).attr('data-name'));
        });

        $('#keyword').val(text).attr('data-id', tags.join(','));


        $('.account-publish-keywordBar').hide();
    });
    $('.open-keywordBar').click(function (event) {
        $('.account-publish-keywordBar').show();
    });
    $('.close-keywordBar').click(function () {
        $('.account-publish-keywordBar').hide();
    });

    //发布
    $('#publishId').click(function () {
        addArticle(1);
    });

    //草稿
    $('#draftId').click(function () {
        addArticle(0);
    });
})