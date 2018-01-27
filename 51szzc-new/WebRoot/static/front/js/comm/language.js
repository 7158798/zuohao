
/***************************读取系统message配置文件 start*****************************/
//全局存储message，类型map
var i18nMsgMap = {};
var _locale_language = "zh_CN";

$().ready(function(){
    _locale_language = $("#locale_language").val() || "zh_CN";
});

//从全局的message Map中获取提示信息
function i18nMsg(msgKey) {
    //未读取配置文件
    if(i18nMsgMap[_locale_language] == null) {
        loadi18nPro();
    }
    return i18nMsgMap[_locale_language][msgKey];
}

//读取配置文件
function loadi18nPro() {
    $.ajax({
        url: "/i18npro.html",
        type: "POST",
        dataType: "json",
        async: false,
        success: function (data) {
            i18nMsgMap[_locale_language] = {}; //置为空
            for (var key in data.i18n_map) {
                var value = data.i18n_map[key];
                i18nMsgMap[_locale_language][key] = value;
            }
        }
    });

}


/***************************读取系统message配置文件 end*****************************/