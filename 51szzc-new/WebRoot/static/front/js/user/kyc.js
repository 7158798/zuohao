var security = {
    submitRealCertificationForm: function () {
        var realname = document.getElementById("bindrealinfo-realname").value;
        var address = document.getElementById("bindrealinfo-address").value;
        var identitytype = document.getElementById("bindrealinfo-identitytype").value;
        var identityno = document.getElementById("bindrealinfo-identityno").value;
        var ckinfo = document.getElementById("bindrealinfo-ckinfo").checked;
        var desc = '';
        // 验证是否同意
        if (!ckinfo) {
            desc = language["certification.error.tips.1"];
            util.showerrortips('certificationinfo-errortips', desc);
            return;
        }
        //验证姓名
        if (realname.length > 32 || realname.trim() == "") {
            desc = language["certification.error.tips.2"];
            util.showerrortips('certificationinfo-errortips', desc);
            return;
        }
        // 验证证件类型
        if (identitytype != 0) {
            desc = language["certification.error.tips.3"];
            util.showerrortips('certificationinfo-errortips', desc);
            return;
        }
        // 验证身份证
        var isIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        var re = new RegExp(isIDCard);
        if (!re.test(identityno)) {
            desc = language["certification.error.tips.4"];
            util.showerrortips('certificationinfo-errortips', language["comm.error.tips.119"]);
            return false;
        }
        // 隐藏错误消息
        util.hideerrortips('certificationinfo-errortips');
        // 提交信息

        var url = "/user/validateIdentity.html?random=" + Math.round(Math.random() * 100);
        var param = {
            realName: realname,
            identityType: identitytype,
            identityNo: identityno
        };
        jQuery.post(url, param, function (data) {
            if (data.code == 0) {

                //first time
                if (isreg == 1) {

                    var id = param.identityNo.substr(0, 3) + '*********' + param.identityNo.substr(-3);

                    userInfo.name = realname;
                    userInfo.identityType = param.identityType;
                    userInfo.id = id;

                    $('.account-name').text(userInfo.name);
                    $('.accountInfo-id').text(userInfo.id);
                    $('#payeeAddr').val(userInfo.name)
                    $('.accountInfo').show();
                    $('#bindrealinfo').modal('hide');

                    setTimeout("$('#addBankDialog').modal('show')", 500)

                    return false;
                }

                util.showerrortips('certificationinfo-errortips', data.msg);
                $('.step1-btn').replaceWith(' <span class="step1-btn disabled">已认证</span>');
                $('.step2-btn').replaceWith('<span class="step2-btn" data-toggle="modal" data-target="#addBankDialog">点击认证</span>');
                $('#bindrealinfo').modal('hide')
                $('#payeeAddr').val(realname);
                setTimeout("$('#addBankDialog').modal('show')", 500);
            } else {
                util.showerrortips('certificationinfo-errortips', data.msg);
            }
        }, "json");
    },
    submitWithdrawCnyAddress: function (type) {
        var payeeAddr = document.getElementById("payeeAddr").value;
        var openBankTypeAddr = $("#openBankTypeAddr").attr('data-id');
        var withdrawAccount = util.trim(document.getElementById("withdrawAccountAddr").value);
        var address = util.trim(document.getElementById("address").value);
        var prov = util.trim(document.getElementById("prov").value);
        var city = util.trim(document.getElementById("city").value);
        var dist = util.trim(document.getElementById("dist").value);
        var totpCode = 0;
        var phoneCode = 0;
        var phone = util.trim(document.getElementById("dialogPhone").value);


        if (payeeAddr == "" || payeeAddr == language["withdraw.error.tips.5"] || payeeAddr == language["withdraw.error.tips.6"]) {
            util.showerrortips("binderrortips", language["comm.error.tips.129"]);
            return;
        }
        if (openBankTypeAddr == -1) {
            util.showerrortips("binderrortips", language["comm.error.tips.70"]);
            return;
        }
        var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
        if (!reg.test(withdrawAccount)) {
            //银行卡号不合法
            util.showerrortips("binderrortips", language["comm.error.tips.134"]);
            return;
        }
        if (withdrawAccount == "" || withdrawAccount.length > 200 || withdrawAccount == language["comm.error.tips.62"]) {
            util.showerrortips("binderrortips", language["comm.error.tips.71"]);
            return;
        }
        // var withdrawAccount2 = util.trim(document.getElementById("withdrawAccountAddr2").value);
        // if (withdrawAccount != withdrawAccount2) {
        // 	util.showerrortips("binderrortips", language["comm.error.tips.72"]);
        // 	return;
        // }
        if ((prov == "" || prov == language["withdraw.error.tips.7"]) || (city == "" || city == language["withdraw.error.tips.7"]) || address == "") {
            util.showerrortips("binderrortips", language["comm.error.tips.73"]);
            return;
        }
        if (address.length > 300) {
            util.showerrortips("binderrortips", language["comm.error.tips.73"]);
            return;
        }

        if (phone) {
            if (!util.checkMobile(phone)) {
                util.showerrortips("binderrortips", language["comm.error.tips.148"]);
                document.getElementById("phone").value = "";
                return;
            }
        }

        if (document.getElementById("addressTotpCode") != null) {
            totpCode = util.trim(document.getElementById("addressTotpCode").value);
            if (!/^[0-9]{6}$/.test(totpCode)) {
                util.showerrortips("binderrortips", language["comm.error.tips.65"]);
                document.getElementById("addressTotpCode").value = "";
                return;
            }
        }

        if (document.getElementById("addressPhoneCode") != null) {
            phoneCode = util.trim(document.getElementById("addressPhoneCode").value);
            if (!/^[0-9]{6}$/.test(phoneCode)) {
                util.showerrortips("binderrortips", language["comm.error.tips.66"]);
                document.getElementById("addressPhoneCode").value = "";
                return;
            }
        }

        $('#withdrawCnyAddrBtn').attr('disabled', true);

        util.hideerrortips("binderrortips");
        var url = "/user/validateBank.html?random=" + Math.round(Math.random() * 100);
        jQuery.post(url, {
            account: withdrawAccount,
            openBankType: openBankTypeAddr,
            phoneCode: phoneCode,
            address: address,
            prov: prov,
            city: city,
            dist: dist,
            payeeAddr: payeeAddr,
            phone: phone
        }, function (result) {
            if (result != null) {
                if (result.code == 0) {

                    location.reload();
                } else {
                    util.showerrortips("binderrortips", result.msg);
                }
            }
            $('#withdrawCnyAddrBtn').attr('disabled', false);

        }, "json");
    },
    submitInfo: function () {
        var identityUrlOn = $('#pic1Url').val();
        var identityUrlOff = $('#pic2Url').val();
        var identityHoldUrl = $('#pic3Url').val();
        var validateVideoUrl = $('#vedioUrl').val();

        if (!identityUrlOn) {
            util.showerrortips("error", '请上传身份证正面照片');
            return false;
        }

        if (!identityUrlOff) {
            util.showerrortips("error", '请上传身份证反面照片');
            return false;
        }

        if (!identityHoldUrl) {
            util.showerrortips("error", '请上传手持身份证照片');
            return false;
        }

        if (!validateVideoUrl) {
            util.showerrortips("error", '请上传认证视频');
            return false;
        }

        var parma = {
            identityUrlOn: identityUrlOn,
            identityUrlOff: identityUrlOff,
            identityHoldUrl: identityHoldUrl,
            validateVideoUrl: validateVideoUrl,
        }

        $.post("/user/validateKyc.html", parma, function (data, textStatus, jqXHR) {

            if (data.code == 0) {
                setTimeout("location.reload()", 500)
            }
            util.showerrortips("error", data.msg);
        }, "json");
    }
}

$(function () {

    isreg = 0;

    $('#openBankTypeAddr').autocomplete({
	    lookup: BANKLIST,
	    autoSelectFirst: true,
	    minChars:0,
	    onSelect: function (suggestion) {
	        $("#openBankTypeAddr").attr('data-id',suggestion.data);
	    }
	});

    //实名认证
    $("#bindrealinfo-Btn").on("click", function () {

        security.submitRealCertificationForm(false);
    });

    $('#withdrawCnyAddrBtn').on('click', function () {

        security.submitWithdrawCnyAddress();
    });


    $(".btn-sendmsg").on("click", function () {
        if (this.id == "unbindphone-newsendmessage") {
            var areacode = $("#unbindphone-newphone-areacode").html();
            var phone = $("#unbindphone-newphone").val();
            if (phone == "") {
                util.showerrortips("unbindphone-errortips", language["comm.error.tips.6"]);
                return;
            }
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
        } else if (this.id == "unbindbank-megbtn") {
            var areacode = $("#bindphone-newphone-areacode").html();
            var phone = $("#unbindBank-phone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "bindphone-sendmessage") {
            var areacode = $("#bindphone-newphone-areacode").html();
            var phone = $("#bindphone-newphone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "bindsendmessage") {
            var areacode = '86';
            var phone = $("#dialogPhone").val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

        } else if (this.id == "retrievePassword-sendmessage") {
            var imgcode = $('#retrievePassword-imgpwd').val();
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone, imgcode);
        } else {
            msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
        }
    });

    //提交
    $('.to_up .s1').on('click', function () {
        security.submitInfo();
    });

})


//上传图片
function uploadImg1() {
    if (checkFileType('pic1', 'img')) {
        fileUpload("/common/upload.html", "4", "pic1", "pic1Url", null, null, imgbakc1, "resultUrl");
    }
}

function imgbakc1(resultUrl) {
    $(".pic1show").css("background-image", "url(" + resultUrl + ")");
    $('label[for="pic1"]').text('重新上传');
    $('.pic1name').text($('#pic1').val().split('\\').pop())
        .siblings().text('已选择');
}

function uploadImg2() {
    if (checkFileType('pic2', 'img')) {
        fileUpload("/common/upload.html", "4", "pic2", "pic2Url", null, null, imgbakc2, "resultUrl");
    }
}

function imgbakc2(resultUrl) {
    $(".pic2show").css("background-image", "url(" + resultUrl + ")");
    $('label[for="pic2"]').text('重新上传');
    $('.pic2name').text($('#pic2').val().split('\\').pop())
       .siblings().text('已选择');
}

function uploadImg3() {
    if (checkFileType('pic3', 'img')) {
        fileUpload("/common/upload.html", "4", "pic3", "pic3Url", null, null, imgbakc3, "resultUrl");
    }
}

function imgbakc3(resultUrl) {
    $(".pic3show").css("background-image", "url(" + resultUrl + ")");
    $('label[for="pic3"]').text('重新上传');
    $('.pic3name').text($('#pic3').val().split('\\').pop())
       .siblings().text('已选择');
}

//认证视频   //2017-05-12修改，传递类型格式错误，修改为video
function uploadVideo() {
    if (checkFileType('vedio','video')) {
        fileUpload("/common/upload.html", "4", "vedio", "vedioUrl", null, null, vedioBack, "resultUrl");
    }
}

function vedioBack() {
    $('label[for="vedio"]').text('重新上传');
    $('.vedioname').text($('#vedio').val().split('\\').pop())
        .parent().show();
}