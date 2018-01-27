var certification = {
	submitRealCertificationForm : function() {
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
		if (realname.length > 6 || realname.trim() == "") {
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
			realName : realname,
			identityType : identitytype,
			identityNo : identityno
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				util.showerrortips('certificationinfo-errortips', data.msg);
				window.location.reload(false);
			} else {
				util.showerrortips('certificationinfo-errortips', data.msg);
			}
		}, "json");

	},
	submitID : function () {
		
		var code = $('#idPhoneCode').val();
		
		if (code.indexOf(" ") > -1 || code.length != 6 || !/^[0-9]{6}$/.test(code)) {
			desc = language["comm.error.tips.124"];
			util.showerrortips('binderrortips', desc);
			return false;
		}

		var param = {
			fIdentityUrlOn: $('#updateIdPic-front').val(),
			fIdentityUrlOff: $('#updateIdPic-back').val(),
			code: code
		}

		$.post("/user/validateGoUp.html?random=" + Math.round(Math.random() * 100), param, function(data, textStatus, xhr) {

			if(data.code == 0) {
				
				$('#updateId').modal('hide');
				window.location.reload();
			} else {
				util.showerrortips('binderrortips', data.msg);
			}

		},'json');

	}
};

$(function() {
	$("#bindrealinfo-Btn").on("click", function() {
		certification.submitRealCertificationForm(false);
	});

	//msg
	$('#bindsendmessage').on('click', function() {
	
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
	});

	//图片上传
	// $('.updateIdPic input').on('change', function() {

	// });


	//detail
	$('.imgExample1').on('click', function() {
		$('.imgDetail1').show();
	});
	$('.imgExample2').on('click', function() {
		$('.imgDetail2').show();
	});
	
	$('.imgDetail1 button,.imgDetail2 button').on('click', function() {
		$(this).parent().hide();
	});

	//submit
	$('.user-id-submit').on('click', function() {
		
		certification.submitID();
	});
});
function uploadImg(index) {

	var $form = $('.updateIdPic').eq(index);
	var index = $form.index();
	$.ajaxFileUpload({
		url: '/user/uploadFile.html',
		secureuri: false,
		fileElementId: $form.find('input').attr('id'), //file标签的id
		dataType: 'json', //返回数据的类型  
		type: 'post',
		success: function(data, status) {
			var imgUrl = data.img;

			if(index == 0) {
				$('#updateIdPic-front').val(imgUrl);
			} else {
				$('#updateIdPic-back').val(imgUrl);
			}

			$form.addClass('active').find('p').text(language["re_upload"]);
			$form.css('background-image', 'url('+imgUrl+')');
		},
		error: function(data, status, e) {
			
		}
	});	
}